package com.springbatch.testing.model;



import java.math.BigDecimal;

 //Field name mismatches sol.
public class Product {

    private Integer productId;
    //Field name mismatches sol.
    private String productName;
    private String productDesc;
    private BigDecimal price;
    private Integer unit;

     public Product(Integer productId, String productName, String productDesc, BigDecimal price, Integer unit) {
         this.productId = productId;
         this.productName = productName;
         this.productDesc = productDesc;
         this.price = price;
         this.unit = unit;
     }

     //Field name mismatches sol.

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", price=" + price +
                ", unit=" + unit +
                '}';
    }
}
