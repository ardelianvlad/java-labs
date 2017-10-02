import lab2.Product;
import lab2.ProductBuilder;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import lab2.Product.Category;

import static org.testng.Assert.assertEquals;

public class ProductTest {

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
    void isExpiredTest (String date, int daysExpired, boolean expected) {
        System.out.println(new ProductBuilder().setProductionDate(date).setExpirationDays(daysExpired).createProduct());
        assertEquals(new ProductBuilder().setProductionDate(date).setExpirationDays(daysExpired).createProduct().isExpired(), expected);
    }

    @DataProvider
    Object[][] isExpiredProvider() {
        return new Object[][] {
                {"01-09-2017", 40, true},
                {"01-09-2017", 10, false},
        };
    }



}
