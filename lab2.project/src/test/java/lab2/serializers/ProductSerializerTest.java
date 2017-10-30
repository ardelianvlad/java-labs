package lab2.serializers;

import com.fasterxml.jackson.databind.JsonMappingException;
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

    @DataProvider
    public Object[][] negativeDataProvider() {
        String[] files = {"test1.json", "test2.json","test3.json", "test4.json",
                        "test1.xml", "test2.xml", "test3.xml", "test4.xml",
                        "test1.txt", "test2.txt", "test3.txt", "test4.txt"};
        Object [][] objects = new Object[12][];
        for (int i=0; i<4; i++) {
            objects[i] = new Object[]{new ProductJSONSerializer(), files[i]};
        }
        for (int i=4; i<8; i++) {
            objects[i] = new Object[]{new ProductXMLSerializer(), files[i]};
        }
        for (int i=8; i<12; i++) {
            objects[i] = new Object[]{new ProductXMLSerializer(), files[i]};
        }
        return objects;
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

    @Test(dataProvider = "negativeDataProvider", expectedExceptions = Exception.class)
    public void testDeserializeCollectionFail2(Serializer<Product> s, String fileName) throws Exception {
        s.deserializeCollection(new FileInputStream(new File(fileName)));
    }

}