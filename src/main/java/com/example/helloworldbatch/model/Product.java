package com.example.helloworldbatch.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD) //Field name mismatches sol.
public class Product {

    private Integer productId;
    @XmlElement(name = "productName") //Field name mismatches sol.
    private String productName;
    private BigDecimal price;
    private Integer unit;
    @XmlElement(name = "productDesc") //Field name mismatches sol.
    private String prodDesc;

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
        return prodDesc;
    }

    public void setProductDesc(String productDesc) {
        this.prodDesc = productDesc;
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
                ", productDesc='" + prodDesc + '\'' +
                ", price=" + price +
                ", unit=" + unit +
                '}';
    }
}
