import lab2.Product;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import lab2.Product.Category;

import static org.testng.Assert.assertEquals;

public class ProductTest {

    @Test(dataProvider = "isCategoryProvider")
    void isCategoryTest (Category category, boolean expected) {
        Product product = new Product();
        product.setCategory(category);
        assertEquals(product.isCategory(category), expected);
    }

    @DataProvider
    Object [][] isCategoryProvider () {
        return new Object[][] {
                {Category.FRUIT, true},
                {Category.drinks, true},
                {Category.dairy, true}
        };
    }

    @Test(dataProvider = "isExpiredProvider")
    void isExpiredTest (String date, int daysExpired, boolean expected) {
        assertEquals(new Product("", Category.other, date, daysExpired, 0.0).isExpired(), expected);
    }

    @DataProvider
    Object[][] isExpiredProvider() {
        return new Object[][] {
                {"01-09-2017", 30, true},
                {"01-09-2017", 10, false},
        };
    }



}
