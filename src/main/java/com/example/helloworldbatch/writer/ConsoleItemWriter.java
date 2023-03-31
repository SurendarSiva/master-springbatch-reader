package com.example.helloworldbatch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleItemWriter extends AbstractItemStreamItemWriter {
    @Override
    public void write(Chunk chunk) throws Exception {
        chunk.getItems().forEach(System.out::println);
        System.out.println("***************************** Writing each chunk *****************************");
    }
}
