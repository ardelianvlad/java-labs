package lab2;

import java.util.ArrayList;

public class Storage {

    private ArrayList<Product> products;

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
        return "lab2.Storage{" +
                "products=" + products +
                '}';
    }
}
