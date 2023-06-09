package com.invoice.api.dto;

public class ProductDto
{
    private Integer stock;
    private String gtin;
    private double price;
    private Integer product_id;
    private String product;
    private String description;

    private Integer category_id;
    private Integer status;

    public String getProduct()
    {
        return product;
    }

    public void setProduct(String product)
    {
        this.product = product;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getCategory_id()
    {
        return category_id;
    }

    public void setCategory_id(Integer category_id)
    {
        this.category_id = category_id;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getProduct_id()
    {
        return product_id;
    }

    public void setProduct_id(Integer product_id)
    {
        this.product_id = product_id;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }


    public Integer getStock()
    {
        return stock;
    }

    public void setStock(Integer stock)
    {
        this.stock = stock;
    }

    public String getGTIN()
    {
        return gtin;
    }

    public void setGTIN(String gtin)
    {
        this.gtin = gtin;
    }

    public boolean isStockAvailable(Integer whatImrequesting)
    {
        return stock <= whatImrequesting;
    }

    public void updateStock(Integer whatImrequesting)
    {
        this.stock -= whatImrequesting;
    }
}
