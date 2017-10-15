import lab2.Product;
import lab2.ProductBuilder;
import lab2.Storage;
import lab2.StorageBuilder;
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
        Product p1 = new ProductBuilder().setExpiration(30).createProduct();
        Product p2 = new ProductBuilder().setExpiration(10).createProduct();
        Product p3 = new ProductBuilder().setProductionDate(oldDate).setExpiration(10).createProduct();
        Product p4 = new ProductBuilder().setProductionDate(oldDate).setExpiration(20).createProduct();
        return new Object [][] {
                {new StorageBuilder().addProduct(p1).addProduct(p2).createStorage(), new StorageBuilder().addProduct(p1).addProduct(p2).createStorage()},
                {new StorageBuilder().addProduct(p1).addProduct(p3).createStorage(), new StorageBuilder().addProduct(p1).createStorage()},
                {new StorageBuilder().addProduct(p2).addProduct(p3).addProduct(p4).createStorage(), new StorageBuilder().addProduct(p2).createStorage()}
        };
    }

    @Test(dataProvider = "inPriceCategoryProvider")
    public void inPriceCategoryTest (Storage storage, int left, int right, Storage expected) {
        assertEquals(storage.inPriceCategory(left, right), expected.getProducts());
    }

    @DataProvider
    public Object[][] inPriceCategoryProvider () {
        Storage storage = new StorageBuilder().createStorage();
        for (int i = 10; i <= 50; i+=10) {
            storage.addProduct(new ProductBuilder().setPrice(i).createProduct());
        }
        return new Object[][] {
                {storage, 5, 15, new StorageBuilder().addProduct(storage.getProduct(0)).createStorage()},
                {storage, 15, 35, new StorageBuilder().addProduct(storage.getProduct(1)).addProduct(storage.getProduct(2)).createStorage()},
                {storage, 5, 100, storage},
                {storage, 1, 5, new StorageBuilder().createStorage()}
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
        Storage storage = new StorageBuilder().addProduct(p1).addProduct(p2).addProduct(p2).addProduct(p3).createStorage();
        return new Object[][] {
                {storage, Product.Category.DRINKS, new StorageBuilder().addProduct(p1).createStorage()},
                {storage, Product.Category.FRUIT, new StorageBuilder().addProduct(p2).addProduct(p2).createStorage()},
                {storage, Product.Category.DAIRY, new StorageBuilder().createStorage()}
        };
    }

    @Test(dataProvider = "decayAfterProvider")
    public void decayAfterTest(Storage storage, int days, Storage expected) {
        assertEquals(storage.decayAfter(days), expected.getProducts());
    }


    @DataProvider
    public Object[][] decayAfterProvider() throws ParseException {
        Product p1 = new ProductBuilder().setProductionDate(oldDate).setExpiration(10).createProduct();
        Product p2 = new ProductBuilder().setProductionDate(oldDate).setExpiration(40).createProduct();
        Product p3 = new ProductBuilder().setProductionDate(oldDate).setExpiration(60).createProduct();
        Storage storage = new StorageBuilder().addProduct(p1).addProduct(p2).addProduct(p3).createStorage();
        return new Object[][] {
                {storage, 0, new StorageBuilder().createStorage()},
                {storage, 20, new StorageBuilder().addProduct(p2).createStorage()}
        };
    }

}
