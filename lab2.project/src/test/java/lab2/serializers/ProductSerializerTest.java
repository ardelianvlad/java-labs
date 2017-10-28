package lab2.serializers;

import lab2.Product;
import lab2.ProductBuilder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

public class ProductSerializerTest {
    Product p1, p2;
    ArrayList<Product> products;

    @BeforeTest
    public void setup() {
        p1 = new ProductBuilder().setName("Product1").setProductionDate(LocalDate.now()).setExpirationDays(10).setPrice(10.56).build();
        p2 = new ProductBuilder().setName("Product2").setProductionDate(LocalDate.now()).setExpirationDays(15).setPrice(64.23).build();
        products = new ArrayList<>();
        products.add(p1);
        products.add(p2);
    }

    @DataProvider
    public Object[][] serializationProvider() {
        return new Object[][]{{new ProductJSONSerializer(), "test.json"},
                {new ProductXMLSerializer(), "test.xml"},
                {new ProductTextSerializer(), "test.txt"}};
    }

    @Test(priority = 1, dataProvider = "serializationProvider")
    public void testSerialize(Serializer<Product> s, String fileName) throws Exception {
        s.serialize(p1, new PrintWriter(new File(fileName)));
    }

    @Test(priority = 2, dataProvider = "serializationProvider")
    public void testDeserialize(Serializer<Product> s, String fileName) throws Exception {
        assertEquals(s.deserialize(new FileInputStream(new File(fileName))), p1);
    }

    @Test(priority = 3, dataProvider = "serializationProvider")
    public void testSerializeCollection(Serializer<Product> s, String fileName) throws Exception {
        s.serializeCollection(products, new PrintWriter(new File(fileName)));
    }

    @Test(priority = 4, dataProvider = "serializationProvider")
    public void testDeserializeCollection(Serializer<Product> s, String fileName) throws Exception {
        assertEquals(s.deserializeCollection(new FileInputStream(new File(fileName))), products);
    }

}