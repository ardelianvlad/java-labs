import lab2.Product;
import lab2.ProductBuilder;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import lab2.Product.Category;

import java.text.ParseException;
import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

public class ProductTest {

    private LocalDate oldDate;

    @BeforeSuite
    public void beforeDate(){
        oldDate = LocalDate.now().minusDays(30);
    }

    @Test(dataProvider = "isCategoryProvider")
    void isCategoryTest (Category category, boolean expected) {
        Product product = new ProductBuilder().createProduct();
        product.setCategory(category);
        assertEquals(product.isCategory(category), expected);
    }

    @DataProvider
    Object [][] isCategoryProvider () {
        return new Object[][] {
                {Category.FRUIT, true},
                {Category.DRINKS, true},
                {Category.DAIRY, true}
        };
    }

    @Test(dataProvider = "isExpiredProvider")
    void isExpiredTest (LocalDate date, int daysExpired, boolean expected) throws ParseException {
        assertEquals(new ProductBuilder().setProductionDate(date).setExpiration(daysExpired).createProduct().isExpired(), expected);
    }

    @DataProvider
    Object[][] isExpiredProvider() {
        return new Object[][] {
                {oldDate, 40, true},
                {oldDate, 10, false},
        };
    }



}
