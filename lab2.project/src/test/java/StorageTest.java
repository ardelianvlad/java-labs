import lab2.Product;
import lab2.ProductBuilder;
import lab2.Storage;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.testng.Assert.assertEquals;

public class StorageTest {

    private String oldDate;

    @BeforeSuite
    public void beforeDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -30);
        oldDate = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
    }

    @Test(dataProvider = "expiredProductsProvider")
    public void expiredProductsTest(Storage storage1, Storage storage2) {
        assertEquals(storage1.expiredProducts(), storage2.getProducts());
    }

    @DataProvider
    public Object [][] expiredProductsProvider() {
        Product p1 = new ProductBuilder().setExpirationDays(30).createProduct();
        Product p2 = new ProductBuilder().setExpirationDays(10).createProduct();
        Product p3 = new ProductBuilder().setProductionDate(oldDate).setExpirationDays(10).createProduct();
        Product p4 = new ProductBuilder().setProductionDate(oldDate).setExpirationDays(20).createProduct();
        return new Object [][] {
                {new Storage().addProduct(p1).addProduct(p2), new Storage().addProduct(p1).addProduct(p2)},
                {new Storage().addProduct(p1).addProduct(p3), new Storage().addProduct(p1)},
                {new Storage().addProduct(p2).addProduct(p3).addProduct(p4), new Storage().addProduct(p2)}
        };
    }

    @Test(dataProvider = "inPriceCategoryProvider")
    public void inPriceCategoryTest (Storage storage, int left, int right, Storage expected) {
        assertEquals(storage.inPriceCategory(left, right), expected.getProducts());
    }

    @DataProvider
    public Object[][] inPriceCategoryProvider () {
        Storage storage = new Storage();
        for (int i = 10; i <= 50; i+=10) {
            storage.addProduct(new ProductBuilder().setPrice(i).createProduct());
        }
        return new Object[][] {
                {storage, 5, 15, new Storage().addProduct(storage.getProduct(0))},
                {storage, 15, 35, new Storage().addProduct(storage.getProduct(1)).addProduct(storage.getProduct(2))},
                {storage, 5, 100, storage},
                {storage, 1, 5, new Storage()}
        };
    }

    @Test(dataProvider = "inCategoryProvider")
    public void inCategoryTest(Storage storage, Product.Category category, Storage expected) {
        assertEquals(storage.inCategory(category), expected.getProducts());
    }

    @DataProvider
    public Object[][] inCategoryProvider() {
        Product p1 = new ProductBuilder().setCategory(Product.Category.DRINKS).createProduct();
        Product p2 = new ProductBuilder().setCategory(Product.Category.FRUIT).createProduct();
        Product p3 = new ProductBuilder().setCategory(Product.Category.OTHER).createProduct();
        Storage storage = new Storage().addProduct(p1).addProduct(p2).addProduct(p2).addProduct(p3);
        return new Object[][] {
                {storage, Product.Category.DRINKS, new Storage().addProduct(p1)},
                {storage, Product.Category.FRUIT, new Storage().addProduct(p2).addProduct(p2)},
                {storage, Product.Category.DAIRY, new Storage()}
        };
    }

    @Test(dataProvider = "decayAfterProvider")
    public void decayAfterTest(Storage storage, int days, Storage expected) {
        assertEquals(storage.decayAfter(days), expected.getProducts());
    }


    @DataProvider
    public Object[][] decayAfterProvider() {
        Product p1 = new ProductBuilder().setProductionDate(oldDate).setExpirationDays(10).createProduct();
        Product p2 = new ProductBuilder().setProductionDate(oldDate).setExpirationDays(40).createProduct();
        Product p3 = new ProductBuilder().setProductionDate(oldDate).setExpirationDays(60).createProduct();
        Storage storage = new Storage().addProduct(p1).addProduct(p2).addProduct(p3);
        return new Object[][] {
                {storage, 0, new Storage()},
                {storage, 20, new Storage().addProduct(p2)},
                {storage, 30, new Storage().addProduct(p2).addProduct(p3)}
        };
    }

}
