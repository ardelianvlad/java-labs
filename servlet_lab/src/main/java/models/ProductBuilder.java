package models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import services.LocalDateSerializer;

import java.time.LocalDate;
import java.util.regex.Pattern;


@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
public class ProductBuilder {
    private String name;
    private Product.Category category;
    private LocalDate productionDate;
    private LocalDate expiration;
    private double price;
    private int id;

    public final static String NAME_PATTERN = "([A-Z][A-Za-z1-9 ]{0,100}?)";

    public ProductBuilder() {
        this.name = "";
        this.category = Product.Category.OTHER;
        this.productionDate = LocalDate.now();
        this.expiration = LocalDate.now();
        this.price = 0;
        this.id = 0;
    }

    public ProductBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setName(String name) {
        if (!Pattern.matches(NAME_PATTERN, name)) {
            throw new IllegalArgumentException("Wrong name");
        }
        this.name = name;
        return this;
    }

    public ProductBuilder setCategory(Product.Category category) {
        this.category = category;
        return this;
    }

    public ProductBuilder setProductionDate(int year, int month, int day) throws IllegalArgumentException {
        LocalDate date = LocalDate.of(year, month, day);
        if(date.isAfter(LocalDate.now())) throw new IllegalArgumentException("Wrong date. Production must be before today");
        this.productionDate = date;
        return this;
    }

    public ProductBuilder setProductionDate(LocalDate date) throws IllegalArgumentException {
        if(date.isAfter(LocalDate.now())) throw new IllegalArgumentException("Wrong date. Production must be before today");
        this.productionDate = date;
        return this;
    }

    public ProductBuilder setExpirationDays(int expirationDays) throws IllegalArgumentException {
        if (expirationDays < 0) {
            throw new IllegalArgumentException("Wrong expiration");
        }
        this.expiration = productionDate.plusDays(expirationDays);
        return this;
    }

    public ProductBuilder setExpiration(LocalDate expiration) throws IllegalArgumentException {
        if (expiration.isBefore(productionDate)) {
            throw new IllegalArgumentException("Wrong expiration");
        }
        this.expiration = expiration;
        return this;
    }

    public ProductBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public Product build() {
        return new Product(name, category, productionDate, expiration, price, id);
    }
}