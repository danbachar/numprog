import java.util.Arrays;

public class Test_Interpolation {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //testNewton();
        //testSplines();
        //testSplineOneUnknown();
        testWithFive();
    }

    private static void testNewton() {

        double[] x = { -1, 1, 3 };
        double[] y = { -3, 1, -3 };
        NewtonPolynom p = new NewtonPolynom(x, y);

        System.out.println(p.evaluate(0) + " sollte sein: 0.0");
        System.out.println("-------------------------------");
    }

    public static void testSplines() {
        CubicSpline spl = new CubicSpline();
        double[] y = { 2, 0, 2, 3 };
        spl.init(-1, 2, 3, y);
        spl.setBoundaryConditions(9, 0);
        System.out.println(Arrays.toString(spl.getDerivatives())
                + " sollte sein: [9.0, -3.0, 3.0, 0.0].");
    }

    public static void testSplineOneUnknown(){
        CubicSpline spl = new CubicSpline();
        double[] y = { -3.0, 0.0, -1.0};
        spl.init(-1, 1, 2, y);
        spl.setBoundaryConditions(1, 2);
        System.out.println(Arrays.toString(spl.getDerivatives()));
    }

    public static void testWithFive(){
        CubicSpline spl = new CubicSpline();
        double[] y = {1.0, -1.0, 0.3333333333333333, 7.0, 5.0};
        spl.init(-1, 7, 4, y);
        System.out.println(Arrays.toString(spl.getDerivatives()));
    }
}
