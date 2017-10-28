package lab2.serializers;

import lab2.Product;
import lab2.ProductBuilder;
import lab2.Storage;
import lab2.StorageBuilder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

public class StorageSerializerTest {
    Product p1, p2;
    ArrayList<Product> products;
    Storage s1, s2;
    ArrayList<Storage> storages;

    @BeforeTest
    public void setup() {
        p1 = new ProductBuilder().setName("Product1").setProductionDate(LocalDate.now()).setExpirationDays(10).setPrice(10.56).build();
        p2 = new ProductBuilder().setName("Product2").setProductionDate(LocalDate.now()).setExpirationDays(15).setPrice(64.23).build();
        products = new ArrayList<>();
        products.add(p1);
        products.add(p2);
        s1 = new StorageBuilder().setName("Storage1").addProduct(p1).addProduct(p2).build();
        s2 = new StorageBuilder().setName("Storage2").addProduct(p1).build();
        storages = new ArrayList<>();
        storages.add(s1);
        storages.add(s2);
    }

    @DataProvider
    public Object[][] serializationProvider() {
        return new Object[][]{{new StorageJSONSerializer(), "test.json"},
                {new StorageXMLSerializer(), "test.xml"},
                {new StorageTextSerializer(), "test.txt"}};
    }

    @Test(priority = 1, dataProvider = "serializationProvider")
    public void testSerialize(Serializer<Storage> s, String fileName) throws Exception {
        s.serialize(s1, new PrintWriter(new File(fileName)));
    }

    @Test(priority = 2, dataProvider = "serializationProvider")
    public void testDeserialize(Serializer<Storage> s, String fileName) throws Exception {
        assertEquals(s.deserialize(new FileInputStream(new File(fileName))), s1);
    }

    @Test(priority = 3, dataProvider = "serializationProvider")
    public void testSerializeCollection(Serializer<Storage> s, String fileName) throws Exception {
        s.serializeCollection(storages, new PrintWriter(new File(fileName)));
    }

    @Test(priority = 4, dataProvider = "serializationProvider")
    public void testDeserializeCollection(Serializer<Storage> s, String fileName) throws Exception {
        assertEquals(s.deserializeCollection(new FileInputStream(new File(fileName))), storages);
    }

}