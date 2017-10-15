package lab1;

public class Variant20 {

    public enum Zodiac {
        AQUARIUS, PISCES, ARIES, TAURUS, GEMINI, CANCER,
        LEO, VIRGO, LIBRA, SCORPIO, SAGITTARIUS, CAPRICORN
    };

    public static class TwoDoubles {
        private double a;
        private double b;

        @Override
        public String toString() {
            return "TwoDoubles{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TwoDoubles that = (TwoDoubles) o;

            if (Double.compare(that.a, a) != 0) return false;
            return Double.compare(that.b, b) == 0;
        }

        public TwoDoubles(double a, double b) {
            this.a = a;
            this.b = b;
        }

        public double getB() {
            return b;
        }

        public void setB(double b) {
            this.b = b;
        }

        public double getA() {
            return a;
        }

        public void setA(double a) {
            this.a = a;
        }
    }

    /**
     *
     * @param x1 is coordinate of first point
     * @param y1 is coordinate of first point
     * @param x2 is coordinate of second point
     * @param y2 is coordinate of second point
     * @return distance
     */
    public double distanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /**
     *
     * @param seconds
     * @return number of full hours
     */
    public int hoursFromSeconds(int seconds) {
        return seconds / 3600;
    }

    /**
     * @param number
     * @return true if all the numbers are different
     */
    public boolean areAllTheNumbersDifferent(int number) throws IllegalArgumentException {
        if(number < 100 || number > 999) {
            throw new IllegalArgumentException("Number must be > 100 and < 1000, given " + number + ".");
        }
        int a = number % 10;
        int b = (number % 100) / 10;
        int c = (number % 1000) / 100;
        return !(a == b || a == c || b == c);
    }

    /**
     *
     * @param A
     * @param B
     * @param C
     * print the closest point to A and distance
     */
    public TwoDoubles whichPointIsClosest(double A, double B, double C) {
        double AB = Math.abs(A- B);
        double AC = Math.abs(A -C);
        if (AB < AC) return  new TwoDoubles(B, AB);
        else return new TwoDoubles(C, AC);
    }

    /**
     *
     * @param d is day
     * @param m is month
     * @return zodiac sign
     */
    public Zodiac zodiac(int d, int m) {
        if (d < 1 || d > 31 || m < 1 || m > 12) {
            throw new IllegalArgumentException("Wrong date.");
        }
        switch (m) {
            case 1: if (d <= 19) return Zodiac.CAPRICORN;
                    else return Zodiac.AQUARIUS;
            case 2: if (d <= 18) return Zodiac.AQUARIUS;
                    else return Zodiac.PISCES;
            case 3: if (d <= 20) return Zodiac.PISCES;
                    else return Zodiac.ARIES;
            case 4: if (d <= 19) return Zodiac.ARIES;
                    else return Zodiac.TAURUS;
            case 5: if (d <= 20) return Zodiac.TAURUS;
                    else return Zodiac.GEMINI;
            case 6: if (d <= 20) return Zodiac.GEMINI;
                    else return Zodiac.CANCER;
            case 7: if (d <= 22) return Zodiac.CANCER;
                    else return Zodiac.LEO;
            case 8: if (d <= 22) return Zodiac.LEO;
                    else return Zodiac.VIRGO;
            case 9: if (d <= 22) return Zodiac.VIRGO;
                    else return Zodiac.LIBRA;
            case 10: if (d <= 22) return Zodiac.LIBRA;
                    else return Zodiac.SCORPIO;
            case 11: if (d <= 21) return Zodiac.SCORPIO;
                    else return Zodiac.SAGITTARIUS;
            case 12: if (d <= 21) return Zodiac.SAGITTARIUS;
                    else return Zodiac.CAPRICORN;
        }
        return Zodiac.CAPRICORN;
    }

    /**
     *
     * @param n
     * @return n!
     */
    private double factorial(double n) {
        if (n == 1 || n == 0) return 1.0;
        else return n * factorial(n - 1);
    }

    /**
     *
     * @param n
     * @return 1! + 2! + ... + n!
     */
    public double factorialSum(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be positive, given " + n + ".");
        double sum = 0;
        for (double i = 1; i <= n; i+=1) {
            sum += factorial(i);
        }
        return sum;
    }

    /**
     *
     * @param n
     * @return true if number contains 2
     */
    public boolean doesItContain2(int n) {
        n = Math.abs(n);
        while(n > 0) {
            if(n % 10 == 2) return true;
            n /= 10;
        }
        return false;
    }

    /**
     *
     * @param arr
     * @return
     */
    public int array20(double [] arr) {
        int count = 0, i = 0;
        while (i < arr.length-1) {
            if(arr[i] <= arr[i+1]) {
                count++;
                while(i < arr.length-1 && arr[i] <= arr[i+1]) {
                    i++;
                }
            }
            i++;
        }
        return count;
    }

    /**
     *
     * @param matr
     * @return
     */
    public double[][] matrix20(double [][] matr) {
        if(matr.length == 0){
            return new double[][]{};

        }
        int n = matr.length;
        int m = matr[0].length;
        int index = -1;
        for (int j = 0; j < m; j++) {
            boolean flag = true;
            for (int i = 0; i < n; i++) {
                if (matr[i][j] >= 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                index = j;
            }
        }
        double [][] matr2;
        if(index == -1) {
            matr2 = new double[n][m];
        }
        else {
            matr2 = new double[n][m-1];
        }
        for (int i = 0; i < n; i++) {
            int k = 0;
            for (int j = 0; j < m; j++) {
                if (j != index) {
                    matr2[i][k] = matr[i][j];
                    k++;
                }
            }
        }
        return matr2;
    }

}
