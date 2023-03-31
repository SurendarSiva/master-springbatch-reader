package com.example.helloworldbatch.config;

import com.example.helloworldbatch.listener.HWJobExecutionListener;
import com.example.helloworldbatch.listener.HWStepExecutionListener;
import com.example.helloworldbatch.model.Product;
import com.example.helloworldbatch.processor.InMemItemProcessor;
import com.example.helloworldbatch.reader.InMemReader;
import com.example.helloworldbatch.reader.ProductServiceAdapter;
import com.example.helloworldbatch.service.ProductService;
import com.example.helloworldbatch.writer.ConsoleItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {

    @Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager transactionManager;

    @Autowired
    private HWJobExecutionListener hwJobExecutionListener;

    @Autowired
    private HWStepExecutionListener hwStepExecutionListener;

    @Autowired
    private InMemItemProcessor inMemItemProcessor;

    @Autowired
    private InMemReader inMemReader;

    @Autowired
    private ConsoleItemWriter consoleItemWriter;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductServiceAdapter productServiceAdapter;

    @Bean
    public Step step1(){
        return new StepBuilder("step1",jobRepository)
                .listener(hwStepExecutionListener)
                .tasklet(helloWorldTasklet(),transactionManager)
                .build();
    }

    @StepScope
    @Bean
    public StaxEventItemReader xmlItemReader(
            @Value("#{jobParameters['inputFile']}")
            FileSystemResource inputFile) {

        StaxEventItemReader reader = new StaxEventItemReader();
        reader.setResource(inputFile);
        reader.setFragmentRootElementName("product");
        reader.setUnmarshaller(new Jaxb2Marshaller(){
            {
                setClassesToBeBound(Product.class);
            }
        });

        return reader;
    }

    @StepScope
    @Bean
    public FlatFileItemReader flatFileItemReader(
            @Value("#{jobParameters['inputFile']}")
            FileSystemResource inputFile){

        FlatFileItemReader reader = new FlatFileItemReader();
        reader.setResource(inputFile);
        reader.setLineMapper(
                new DefaultLineMapper<Product>(){
                    {
                        setLineTokenizer(new DelimitedLineTokenizer(){
                            {
                                setNames(new String[]{"productId","productName","productDesc","price","unit"});
                                setDelimiter("|");
                            }
                        });
                        setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                            {
                                setTargetType(Product.class);
                            }
                        });
                    }
                }
        );
        reader.setLinesToSkip(1);
        return reader;
    }

    @StepScope
    @Bean
    public FlatFileItemReader flatFixFileItemReader(
            @Value("#{jobParameters['inputFile']}")
            FileSystemResource inputFile){

        FlatFileItemReader reader = new FlatFileItemReader();
        reader.setResource(inputFile);
        reader.setLineMapper(
                new DefaultLineMapper<Product>(){
                    {
                        setLineTokenizer(new FixedLengthTokenizer(){
                            {
                                setNames(new String[]{"productId","prodName","prodDesc","price","unit"});
                                setColumns(
                                        new Range(1,15),
                                        new Range(16,30),
                                        new Range(31,50),
                                        new Range(51,59),
                                        new Range(60,67)
                                );
                            }
                        });
                        setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                            {
                                setTargetType(Product.class);
                            }
                        });
                    }
                }
        );
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public JdbcCursorItemReader jdbcCursorItemReader(){
        JdbcCursorItemReader jdbcCursorItemReader = new JdbcCursorItemReader();
        jdbcCursorItemReader.setDataSource(this.dataSource);
        jdbcCursorItemReader.setSql("select * from products");
        jdbcCursorItemReader.setRowMapper(new BeanPropertyRowMapper(){
            {
                setMappedClass(Product.class);
            }
        });

        return jdbcCursorItemReader;
    }

    @Bean
    @StepScope
    public JsonItemReader jsonItemReader(
            @Value("#{jobParameters['inputFile']}")
            FileSystemResource inputFile){
        JsonItemReader jsonItemReader = new JsonItemReader(inputFile,new JacksonJsonObjectReader(Product.class));
        return jsonItemReader;
    }

    @Bean
    public ItemReaderAdapter serviceItemReader(){
        ItemReaderAdapter itemReaderAdapter = new ItemReaderAdapter();
        itemReaderAdapter.setTargetObject(productServiceAdapter);
        itemReaderAdapter.setTargetMethod("nextProduct");

        return itemReaderAdapter;
    }

    @Bean
    public Step step2(){
        return new StepBuilder("step2", jobRepository)
                .<Integer,Integer>chunk(3,transactionManager)
               // .reader(flatFileItemReader(null))
               // .processor(inMemItemProcessor)
               // .reader(xmlItemReader(null))
               // .reader(flatFixFileItemReader(null))
               // .reader(jdbcCursorItemReader())
               // .reader(jsonItemReader(null))
                .reader(serviceItemReader())
                .writer(consoleItemWriter)
                .build();

    }

    @Bean
    public Tasklet helloWorldTasklet() {
        return ((contribution, chunkContext) -> {
            System.out.println("Hello Spring Batch World");
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Job helloWorldJob(){
        return new JobBuilder("job1",jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(hwJobExecutionListener)
                .start(step1())
                .next(step2())
                .build();
    }
}
