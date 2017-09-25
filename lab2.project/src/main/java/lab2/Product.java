package lab2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Product {

    public enum Category {
        FRUIT, vegetables, dairy, meat, fish, drinks, other
    };

    private String name;
    private Category category;
    private Date productionDate;
    private Date expiration;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (!Pattern.matches("^.{0,100}$", name)) {
            throw new IllegalArgumentException("Too long name");
        }
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) throws IllegalArgumentException {
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.setProductionDate(ft.parse(productionDate));
        }catch (ParseException e) {
            throw new IllegalArgumentException("Wrong date. Pattern is 'dd-mm-yyyy'");
        }

    }

    public void setProductionDate(Date productionDate) {
        if (new Date().before(productionDate)) {
            throw new IllegalArgumentException("Wrong date. Production must be before today");
        }
        this.productionDate = productionDate;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.setExpiration(ft.parse(expiration));
        }catch (ParseException e) {
            throw new IllegalArgumentException("Wrong date. Pattern is 'dd-mm-yyyy'");
        }
    }

    public void setExpiration(Date expiration) {
        if (expiration.before(this.productionDate)) {
            throw new IllegalArgumentException("Expiration must be after production");
        }
        this.expiration = expiration;
    }

    public void setExpiration(int days) throws IllegalArgumentException {
        if (days < 0) {
            throw new IllegalArgumentException("Wrong expiration");
        }
        this.expiration = new Date(new Date().getTime() + days*24*3600*1000);
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
     * @return expired
     */
    public boolean isExpired() {
        return (new Date().after(this.expiration));
    }


    public Product(String name, Category category, String productionDate, int expirationDays, double price) {
        this.setName(name);
        this.category = category;
        this.setProductionDate(productionDate);
        this.setExpiration(expirationDays);
        this.setPrice(price);
    }

    public Product() {
        this.name = "";
        this.category = Category.other;
        this.productionDate = new Date();
        this.expiration = this.productionDate;
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
                "name='" + name + '\'' +
                ", category=" + category +
                ", productionDate=" + productionDate +
                ", expiration=" + expiration +
                ", price=" + String.format("%.2f", price) +
                '}';
    }

    public static void main(String [] args) {
        Product product = new Product();
        product.setExpiration(10);
        System.out.println(product.getExpiration());
    }


}
