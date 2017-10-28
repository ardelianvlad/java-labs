package lab2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lab2.serializers.LocalDateSerializer;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;


@JsonDeserialize(builder = ProductBuilder.class)
@XmlRootElement
public class Product {

    public enum Category {
        FRUIT, VEGETABLES, DAIRY, MEAT, FISH, DRINKS, OTHER
    };

    private String name;
    private Category category;
    private LocalDate productionDate;
    private LocalDate expiration;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws IllegalArgumentException {
        if (price < 0) {
            throw new IllegalArgumentException("Price must be non negative");
        }
        this.price = price;
    }


    /**
     *
     * @param category
     * @return belonging to category
     */
    public boolean isCategory(Category category) {
        return (this.category == category);
    }



    /**
     *
     * @return is isExpired
     */
    @JsonIgnore
    public boolean isExpired() {
        return (LocalDate.now().isBefore(this.expiration));
    }

    public Product(String name, Category category, LocalDate productionDate, LocalDate expiration, double price) {
        this.setName(name);
        this.category = category;
        this.setProductionDate(productionDate);
        this.setExpiration(expiration);
        this.setPrice(price);
    }

    public Product() {
        this.name = "";
        this.category = Product.Category.OTHER;
        this.productionDate = LocalDate.now();
        this.expiration = LocalDate.now();
        this.price = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return name != null ? name.equals(product.name) : product.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {

        return "lab2.Product{" +
                "\nname='" + name + '\'' +
                ", \ncategory=" + category +
                ", \nproductionDate=" + productionDate +
                ", \nexpiration=" + expiration +
                ", \nprice=" + String.format("%.2f", price) +
                "\n}";
    }


}
