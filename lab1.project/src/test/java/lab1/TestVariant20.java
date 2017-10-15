package lab1;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static lab1.Variant20.Zodiac;
import lab1.Variant20.TwoDoubles;

import java.util.Arrays;

public class TestVariant20 {

    @Test(dataProvider = "distanceProvider")
    public void distanceTest(double x1, double y1, double x2, double y2, double expected) {
        assertEquals(new Variant20().distanceBetweenTwoPoints(x1, y1, x2, y2), expected);
    }

    @DataProvider
    public Object[][] distanceProvider() {
        return new Object[][] { {0,0,0,0,0f},
                                {0,0,1,0,1},
                                {0,0,1,1,Math.sqrt(2)} };
    }

    @Test(dataProvider = "secondsProvider")
    public void secondToHoursTest(int seconds, int hours) {
        assertEquals(new Variant20().hoursFromSeconds(seconds), hours);
    }

    @DataProvider
    public Object[][] secondsProvider(){
        return new Object[][] { {3600, 1}, {90000, 25}, {100, 0}, {-4000, -1} };
    }

    @Test(dataProvider = "differentNumbersProvider")
    public void differentNumbersTest(int number, boolean expected) {
        assertEquals(new Variant20().areAllTheNumbersDifferent(number), expected);
    }

    @DataProvider
    public Object[][] differentNumbersProvider() {
        return new Object[][] {{123, true}, {111, false}, {525, false}};
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void differentNumbersTest() {
        new Variant20().areAllTheNumbersDifferent(42);
    }

    @Test(dataProvider = "closestPointProvider")
    public void closestPointTest(double A, double B, double C, TwoDoubles expected) {
        assertEquals(new Variant20().whichPointIsClosest(A, B, C), expected);
    }

    @DataProvider
    public Object[][] closestPointProvider() {
        return new Object[][] {{0, 2, 3, new TwoDoubles(2.0, 2.0)},
                {5, -2, 20, new TwoDoubles(-2.0, 7.0)},
                {1.5, 0, 10, new TwoDoubles(0.0, 1.5)}};
    }

    @Test(dataProvider = "zodiacSignProvider")
    public void zodiacSignTest(int d, int m, Zodiac expected) {
        assertEquals(new Variant20().zodiac(d, m), expected);
    }

    @DataProvider
    public Object[][] zodiacSignProvider() {
        return new Object[][] {
                {15, 1, Zodiac.CAPRICORN},
                {18, 8, Zodiac.LEO},
                {8, 9, Zodiac.VIRGO},
        };
    }

    @Test(dataProvider = "factorialSumProvider")
    public void factorialSumTest(int n, double exp) {
        assertEquals(new Variant20().factorialSum(n), exp);
    }

    @DataProvider
    public Object[][] factorialSumProvider() {
        return new Object[][] { {1, 1.0}, {2, 3.0}, {10, 4037913.0} };
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void factorialSumTest() {
        new Variant20().factorialSum(-3);
    }

    @Test(dataProvider = "doesItContain2Provider")
    public void doesItContain2Test(int number, boolean expexted) {
        assertEquals(new Variant20().doesItContain2(number), expexted);
    }

    @DataProvider
    public Object[][] doesItContain2Provider() {
        return new Object[][] { {2, true}, {-2, true}, {0, false}, {10, false}, {2555, true} };
    }

    @Test(dataProvider = "array20Provider")
    public void array20Test(double [] arr, int exp) {
        assertEquals(new Variant20().array20(arr), exp);
    }

    @DataProvider
    public Object[][] array20Provider() {
        return new Object[][] {{new double[]{1,2,3}, 1},
                {new double[]{1,2,-1}, 1},
                {new double[]{1,2,-1,0}, 2},
                {new double[]{1,2,3,-3, -10, 0, 2, 4, 5, 4}, 2}};
    }

    @Test(dataProvider = "matrix20Provider")
    public void matrix20Test(double [][] matr, double [][] exp) {
        assertEquals(Arrays.deepEquals(new Variant20().matrix20(matr), exp), true);
    }

    @DataProvider
    public Object[][] matrix20Provider() {
        return new Object[][] {
                {new double[][] {{1,2,3},
                                {4,5,6},
                                {7,8,9}},
                        new double[][] {{1,2,3},
                                        {4,5,6},
                                        {7,8,9}}},
                {new double[][] {{1,-1,3},
                                {-5,-1,-6},
                                {1,-2,3}},
                        new double[][] {{1,3},
                                        {-5,-6},
                                        {1,3}}},
                {new double[][] {{-1,-5,3},
                                {-6,-8,9},
                                {-9,-8,-3}},
                        new double[][] {{-1,3},
                                        {-6,9},
                                        {-9,-3}}}
        };
    }

}
