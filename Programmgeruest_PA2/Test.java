import java.util.Arrays;
import dft.DFT;
import dft.IFFT;
import dft.Complex;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        testNewton();
        testNewtonAddingSample();
        // testSplines();
        // testFFT();
    }

    private static void testNewton() {
        double[] x = { -1, 1, 3 };
        double[] y = { -3, 1, -3 };
        NewtonPolynom p = new NewtonPolynom(x, y);

        System.out.println(p.evaluate(0) + " sollte sein: 0.0");
        System.out.println("-------------------------------");

        //personal test
        double[] x_2 = { 0, 1, 2 };
        double[] y_2 = { 3, 0, 1 };
        NewtonPolynom p_2 = new NewtonPolynom(x_2, y_2);
        System.out.println("The coefficients are: " + p_2.a[0] + " " + p_2.a[1] + " " + p_2.a[2] + " ");
        System.out.println("Personal test: " + p_2.evaluate(0) + " sollte sein: 3.0");
        System.out.println("-------------------------------");

        //
    }

    private static void testNewtonAddingSample(){
        System.out.println("Adding new sampling point:");
        double[] x_2 = { 0, 1, 2 };
        double[] y_2 = { 3, 0, 1 };
        NewtonPolynom p_2 = new NewtonPolynom(x_2, y_2);
        p_2.addSamplingPoint(1.5, 0);
        System.out.println("The coefficients are: " + p_2.a[0] + " " + p_2.a[1] + " " + p_2.a[2] + " " + p_2.a[3]);
        System.out.println("Personal test: " + p_2.evaluate(0) + " sollte sein: 3.0");
        System.out.println("-------------------------------");

    }

    public static void testSplines() {
        CubicSpline spl = new CubicSpline();
        double[] y = { 2, 0, 2, 3 };
        spl.init(-1, 2, 3, y);
        spl.setBoundaryConditions(9, 0);
        System.out.println(Arrays.toString(spl.getDerivatives()) + " sollte sein: [9.0, -3.0, 3.0, 0.0].");
    }

    public static void testFFT() {
        System.out.println("Teste Fast Fourier Transformation");

        double[] v = new double[4];
        for (int i = 0; i < 4; i++)
            v[i] = i + 1;
        Complex[] c = dft.DFT.dft(v);
        Complex[] v2 = dft.IFFT.ifft(c);

        for (int i = 0; i < 4; i++) {
            System.out.println(v2[i]);
        }
        System.out.println("Richtig waeren gerundet: Eigene Beispiele ueberlegen");

        System.out.println("*************************************\n");
    }
}
