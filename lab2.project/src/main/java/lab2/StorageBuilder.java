package lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StorageBuilder {
    private List<Product> products;
    private String name;
    private int id;

    private final String NAME_PATTERN = "^[A-Z].{0,100}$";

    public StorageBuilder() {
        this.products = new ArrayList<Product>();
        this.name = "Storage";
        this.id = 0;
    }

    public StorageBuilder setId(int id) {
        this.id = id;
        return this;
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

    public StorageBuilder fromString(String input) {
        this.name = RegexHelper.getRegexGroup(input, RegexHelper.NAME_REGEX);
        this.products = new ArrayList<>();
        String[] productsArray = RegexHelper.getRegexGroup(input, RegexHelper.PRODUCTS_REGEX).split("/");
        for (String product : productsArray) {
            if (!product.isEmpty())
                products.add(new ProductBuilder().fromString(product).build());
        }
        return this;
    }

    public Storage build() {
        return new Storage(name, products, id);
    }
}