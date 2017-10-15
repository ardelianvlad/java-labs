package lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StorageBuilder {
    private List<Product> products;
    private String name;

    private final String NAME_PATTERN = "^[A-Z].{0,100}$";

    public StorageBuilder() {
        this.products = new ArrayList<Product>();
        this.name = "Storage";
    }

    public StorageBuilder setProducts(List<Product> products) {
        this.products = products;
        return this;
    }

    public StorageBuilder addProduct(Product product) {
        products.add(product);
        return this;
    }

    public StorageBuilder setName(String name) {
        if (!Pattern.matches(NAME_PATTERN, name)) {
            throw new IllegalArgumentException("Too long name");
        }
        this.name = name;
        return this;
    }

    public Storage createStorage() {
        return new Storage(products);
    }
}