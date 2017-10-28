package lab2;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

public class StorageTest {

    private LocalDate oldDate;

    @BeforeSuite
    public void beforeDate(){
        oldDate = LocalDate.now().minusDays(30);
    }

    @Test(dataProvider = "expiredProductsProvider")
    public void expiredProductsTest(Storage storage1, Storage storage2) {
        assertEquals(storage1.expiredProducts(), storage2.getProducts());
    }

    @DataProvider
    public Object [][] expiredProductsProvider() throws ParseException {
        Product p1 = new ProductBuilder().setExpirationDays(30).build();
        Product p2 = new ProductBuilder().setExpirationDays(10).build();
        Product p3 = new ProductBuilder().setProductionDate(oldDate).setExpirationDays(10).build();
        Product p4 = new ProductBuilder().setProductionDate(oldDate).setExpirationDays(20).build();
        return new Object [][] {
                {new StorageBuilder().addProduct(p1).addProduct(p2).build(), new StorageBuilder().addProduct(p1).addProduct(p2).build()},
                {new StorageBuilder().addProduct(p1).addProduct(p3).build(), new StorageBuilder().addProduct(p1).build()},
                {new StorageBuilder().addProduct(p2).addProduct(p3).addProduct(p4).build(), new StorageBuilder().addProduct(p2).build()}
        };
    }

    @Test(dataProvider = "inPriceCategoryProvider")
    public void inPriceCategoryTest (Storage storage, int left, int right, Storage expected) {
        assertEquals(storage.inPriceCategory(left, right), expected.getProducts());
    }

    @DataProvider
    public Object[][] inPriceCategoryProvider () {
        Storage storage = new StorageBuilder().build();
        for (int i = 10; i <= 50; i+=10) {
            storage.addProduct(new ProductBuilder().setPrice(i).build());
        }
        return new Object[][] {
                {storage, 5, 15, new StorageBuilder().addProduct(storage.getProduct(0)).build()},
                {storage, 15, 35, new StorageBuilder().addProduct(storage.getProduct(1)).addProduct(storage.getProduct(2)).build()},
                {storage, 5, 100, storage},
                {storage, 1, 5, new StorageBuilder().build()}
        };
    }

    @Test(dataProvider = "inCategoryProvider")
    public void inCategoryTest(Storage storage, Product.Category category, Storage expected) {
        assertEquals(storage.inCategory(category), expected.getProducts());
    }

    @DataProvider
    public Object[][] inCategoryProvider() {
        Product p1 = new ProductBuilder().setCategory(Product.Category.DRINKS).build();
        Product p2 = new ProductBuilder().setCategory(Product.Category.FRUIT).build();
        Product p3 = new ProductBuilder().setCategory(Product.Category.OTHER).build();
        Storage storage = new StorageBuilder().addProduct(p1).addProduct(p2).addProduct(p2).addProduct(p3).build();
        return new Object[][] {
                {storage, Product.Category.DRINKS, new StorageBuilder().addProduct(p1).build()},
                {storage, Product.Category.FRUIT, new StorageBuilder().addProduct(p2).addProduct(p2).build()},
                {storage, Product.Category.DAIRY, new StorageBuilder().build()}
        };
    }

    @Test(dataProvider = "decayAfterProvider")
    public void decayAfterTest(Storage storage, int days, Storage expected) {
        assertEquals(storage.decayAfter(days), expected.getProducts());
    }


    @DataProvider
    public Object[][] decayAfterProvider() throws ParseException {
        Product p1 = new ProductBuilder().setProductionDate(oldDate).setExpirationDays(10).build();
        Product p2 = new ProductBuilder().setProductionDate(oldDate).setExpirationDays(40).build();
        Product p3 = new ProductBuilder().setProductionDate(oldDate).setExpirationDays(60).build();
        Storage storage = new StorageBuilder().addProduct(p1).addProduct(p2).addProduct(p3).build();
        return new Object[][] {
                {storage, 0, new StorageBuilder().build()},
                {storage, 20, new StorageBuilder().addProduct(p2).build()}
        };
    }

}
