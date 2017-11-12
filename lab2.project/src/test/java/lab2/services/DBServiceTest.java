package lab2.services;

import lab2.Product;
import lab2.ProductBuilder;
import lab2.Storage;
import lab2.StorageBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static lab2.Product.Category.DAIRY;
import static org.testng.Assert.assertEquals;

public class DBServiceTest {
    Storage storage;
    Product p1;

    @BeforeClass
    void setup() throws SQLException, ClassNotFoundException {
        p1 = new ProductBuilder().setName("Product11").setProductionDate(2017, 11, 11).setExpirationDays(30).build();
        Product p2 = new ProductBuilder().setName("Product12").setProductionDate(2017, 10, 1).setExpirationDays(30).build();
        Product p3 = new ProductBuilder().setName("Product13").setProductionDate(2017, 10, 15).setExpirationDays(100).build();
        storage = new StorageBuilder().setName("Storage11").addProduct(p2).addProduct(p3).build();
    }

    @Test
    public void testGetNewConnection() throws Exception {
        DBService.getConnection();
    }

    @Test(priority = 1)
    public void testAddProduct() throws Exception {
        DBService.addProduct(p1, 1);
    }

    @Test(priority = 1)
    public void testAddStorage() throws Exception {
        DBService.addStorage(storage);
    }

    @Test(priority = 2)
    public void testGetProduct() throws Exception {
        p1 = DBService.getProduct("Product11");
    }

    @Test(priority = 2)
    public void testGetStorage() throws Exception {
        storage = DBService.getStorage("Storage11");
    }

    @Test(priority = 2)
    public void testGetProducts() throws Exception {
        DBService.getProducts();
    }

    @Test(priority = 2)
    public void testGetStorages() throws Exception {
        DBService.getStorages();
    }

    @Test(priority = 3)
    public void testUpdateProduct() throws Exception {
        p1.setCategory(DAIRY);
        DBService.updateProduct(p1);
        assertEquals(DBService.getProduct("Product11").getCategory(), DAIRY);
    }

    @Test(priority = 3)
    public void testUpdateStorage() throws Exception {
        storage.setName("Name0");
        DBService.updateStorage(storage);
        assertEquals(DBService.getStorage(storage.getId()).getName(), "Name0");
    }

    @Test(priority = 4)
    public void testDeleteStorage() throws Exception {
        DBService.deleteStorage(storage.getId());
        assertEquals(DBService.getStorage(storage.getId()), null);
    }

    @Test(priority = 5)
    public void testDeleteProduct() throws Exception {
        DBService.deleteProduct(p1.getId());
        assertEquals(DBService.getProduct(p1.getId()), null);
    }

    @Test(priority = 6)
    public void negativeTestGetProduct() throws Exception {
        assertEquals(DBService.getProduct(222), null);
    }

    @Test(priority = 6)
    public void negativeTestGetStorage() throws Exception {
        assertEquals(DBService.getStorage(111), null);
    }

    @Test(priority = 6, expectedExceptions = Exception.class)
    public void negativeTestUpdateProduct() throws Exception {
        p1.setId(1321);
        DBService.updateProduct(p1);
    }

    @Test(priority = 6, expectedExceptions = Exception.class)
    public void negativeTestUpdateStorage() throws Exception {
        storage.setId(1321);
        DBService.updateStorage(storage);
    }

    @Test(priority = 6, expectedExceptions = Exception.class)
    public void negativeTestDeleteStorage() throws Exception {
        DBService.deleteStorage(555);
    }

    @Test(priority = 6, expectedExceptions = Exception.class)
    public void negativeTestDeleteProduct() throws Exception {
        DBService.deleteProduct(555);
    }

    @Test(priority = 7)
    public void expiredProductsTest() throws SQLException, ClassNotFoundException {
        DBService.expiredProducts();
    }

}