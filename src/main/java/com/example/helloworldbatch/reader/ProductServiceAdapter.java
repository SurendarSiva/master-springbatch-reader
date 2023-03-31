package com.example.helloworldbatch.reader;

import com.example.helloworldbatch.model.Product;
import com.example.helloworldbatch.service.ProductService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ProductServiceAdapter implements InitializingBean {

    @Autowired
    private ProductService productService;

    private ArrayList<Product> products;

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.products=productService.getProducts();
    }

    public Product nextProduct(){
        if(products.size() > 0){
            return products.remove(0);
        } else {
            return null;
        }
    }

}
