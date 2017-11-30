package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Storage {

    private String name;
    private List<Product> products;
    private int id;

    Storage(String name, List<Product> products, int id) {
        setName(name);
        setProducts(products);
        setId(id);
    }

    Storage() {
        products = new ArrayList<>();
        name = "Storage";
        id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("products")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @JsonIgnore
    public Product getProduct(int index) {
        return products.get(index);
    }

    /**
     *
     * @param product
     * @return
     */
    public boolean addProduct(Product product) {
        return products.add(product);
    }

    /**
     *
     * @param product
     * @return
     */
    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    /**
     *
     * @param criteria
     * @return filtered list
     */
    private List<Product> filter(Predicate<Product> criteria) {
        return products.stream().filter(criteria).collect(Collectors.toList());
    }

    /**
     *
     * @return isExpired products
     */
    public List<Product> expiredProducts() {
        return this.filter(x -> x.isExpired());
    }

    /**
     *
     * @param left border
     * @param right border
     * @return products with price more than left and less than right
     */
    public List<Product> inPriceCategory(double left, double right) throws IllegalArgumentException {
        if (left < 0 || left > right) {
            throw new IllegalArgumentException("left must be >= 0 and left < right");
        }
        return this.filter(x -> x.getPrice() >= left && x.getPrice() <= right);
    }

    /**
     *
     * @param category
     * @return products in category
     */
    public List<Product> inCategory(Product.Category category) {
        return this.filter(x -> x.getCategory() == category);
    }

    /**
     *
     * @param days (x)
     * @return products decayed after x days
     */
    public List<Product> decayAfter(int days) {
        if (days < 0) {
            throw new IllegalArgumentException("");
        }
        return this.filter(product ->
            product.isExpired() && LocalDate.now().plusDays(days).isAfter(product.getExpiration())
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Storage)) return false;

        Storage storage = (Storage) o;

        return products != null ? products.equals(storage.products) : storage.products == null;
    }

    @Override
    public int hashCode() {
        return products != null ? products.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "name='" + name + '\'' +
                ", products=" + products +
                '}';
    }
}
