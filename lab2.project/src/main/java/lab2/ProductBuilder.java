package lab2;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ProductBuilder {
    private String name;
    private Product.Category category;
    private LocalDate productionDate;
    private LocalDate expiration;
    private double price;

    private final String NAME_PATTERN = "^[A-Z].{0,100}$";

    public ProductBuilder() {
        this.name = "";
        this.category = Product.Category.OTHER;
        this.productionDate = LocalDate.now();
        this.expiration = LocalDate.now();
        this.price = 0;
    }

    public ProductBuilder setName(String name) {
        if (!Pattern.matches(NAME_PATTERN, name)) {
            throw new IllegalArgumentException("Too long name");
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

    public ProductBuilder setExpiration(int expirationDays) throws IllegalArgumentException {
        if (expirationDays < 0) {
            throw new IllegalArgumentException("Wrong expiration");
        }
        this.expiration = productionDate.plusDays(expirationDays);
        return this;
    }

    public ProductBuilder setExpirationDate(LocalDate expiration) throws IllegalArgumentException {
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

    public ProductBuilder fromString(String input) {
        this.setName(RegexHelper.getRegexGroup(input, RegexHelper.NAME_REGEX));
        this.setPrice(Double.parseDouble(RegexHelper.getRegexGroup(input, RegexHelper.PRICE_REGEX)));
        this.setCategory(Product.Category.valueOf(RegexHelper.getRegexGroup(input, RegexHelper.CATEGORY_REGEX)));
        return this;
    }

    public Product createProduct() {
        return new Product(name, category, productionDate, expiration, price);
    }
}