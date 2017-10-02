package lab2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductBuilder {
    private String name;
    private Product.Category category;
    private String productionDate;
    private int expirationDays;
    private double price;

    public ProductBuilder() {
        this.name = "";
        this.category = Product.Category.OTHER;
        this.productionDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        this.expirationDays = 0;
        this.price = 0;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setCategory(Product.Category category) {
        this.category = category;
        return this;
    }

    public ProductBuilder setProductionDate(String productionDate) {
        this.productionDate = productionDate;
        return this;
    }

    public ProductBuilder setExpirationDays(int expirationDays) {
        this.expirationDays = expirationDays;
        return this;
    }

    public ProductBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public Product createProduct() {
        return new Product(name, category, productionDate, expirationDays, price);
    }
}