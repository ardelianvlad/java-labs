package lab1;

public class Variant20 {

    public static String [] zodiacSigns = { "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer",
            "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn" };

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
    public String zodiacSign(int d, int m) {
        if (d < 1 || d > 31 || m < 1 || m > 12) {
            throw new IllegalArgumentException("Wrong date.");
        }
        switch (m) {
            case 1: if (d <= 19) return zodiacSigns[11];
            else return zodiacSigns[0];
            case 2: if (d <= 18) return zodiacSigns[0];
            else return zodiacSigns[1];
            case 3: if (d <= 20) return zodiacSigns[1];
            else return zodiacSigns[2];
            case 4: if (d <= 19) return zodiacSigns[2];
            else return zodiacSigns[3];
            case 5: if (d <= 20) return zodiacSigns[3];
            else return zodiacSigns[4];
            case 6: if (d <= 20) return zodiacSigns[4];
            else return zodiacSigns[5];
            case 7: if (d <= 22) return zodiacSigns[5];
            else return zodiacSigns[6];
            case 8: if (d <= 22) return zodiacSigns[6];
            else return zodiacSigns[7];
            case 9: if (d <= 22) return zodiacSigns[7];
            else return zodiacSigns[8];
            case 10: if (d <= 22) return zodiacSigns[8];
            else return zodiacSigns[9];
            case 11: if (d <= 21) return zodiacSigns[9];
            else return zodiacSigns[10];
            case 12: if (d <= 21) return zodiacSigns[10];
            else return zodiacSigns[11];
        }
        return zodiacSigns[11];
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

    public static void main(String... strings) {
        System.out.println("Start of first lab");
        System.out.println("Done!!!");
    }

}
