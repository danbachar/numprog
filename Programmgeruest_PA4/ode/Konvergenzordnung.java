package ode;
import java.util.Arrays;

/**
 * Numerische Berechnung von Konvergenzordnungen gegebener ODE-Solver.
 *
 * @author dietrich
 *
 */
public class Konvergenzordnung {

    /**
     * Festgelegte Differentialgleichung, an der der Konvergenztest durchgeführt wird.
     */
    private final ODE testODE;
    /**
     * Startwert bei der Lösung der Differentialgleichung
     */
    private final double[] y0;
    /**
     * Wert der Lösung der Differentialgleichung nach T Sekunden.
     */
    private final double[] ystar;
    /**
     * Zeitpunkt bis zu dem integriert werden soll.
     */
    private final double T;

    /**
     *
     * @param testODE Test-Differentialgleichung
     * @param y0 Test-Startwert
     * @param ystar Exakter Wert nach Zeit T
     * @param T Zeitpunkt, bis zu dem integriert werden soll
     */
    public Konvergenzordnung(ODE testODE, double[] y0, double[] ystar, double T)
    {
        this.testODE = testODE;
        this.y0 = y0;
        this.ystar = ystar;
        this.T = T;
    }

    /**
     * Integriert die Testgleichung bis zum Zeitpunkt T mit dem gegebenen Verfahren.
     *
     * @param verfahren
     * @param schrittweite
     * @return y_k(T), berechnet mit dem Einschrittverfahren und der Schrittweite.
     */
    private double[] integrate(Einschrittverfahren verfahren, double schrittweite)
    {
        double[] y_end = Arrays.copyOf(y0, y0.length);
        double t = 0;
        while(t < T)
        {
            y_end = verfahren.nextStep(y_end, t, schrittweite, testODE);
            t += schrittweite;
        }

        return y_end;
    }

    /**
     * Berechnet den Fehler in der 2-Norm zwischen der numerischen Lösung yh und der exakten Lösung ystar.
     *
     * @return e_h = ||yh - yexact||_2
     */
    private double error(double[] yh)
    {
        double e = 0.0;

        for(int i=0; i<yh.length; i++)
        {
            e += (ystar[i]-yh[i])*(ystar[i]-yh[i]);
        }

        return Math.sqrt(e);
    }

    private void printArr(double[] arr) {
        int length = arr.length;
        
        System.out.print("[ ");
        for (int i = 0; i < length; i++) {
            double elem = arr[i];
            System.out.print(elem);
            if (i == length-1) {
                System.out.println(" ]");
            } else {
                System.out.print(", ");
            }
        }
        System.out.println();

    }

    private double[] calculate_yh(Einschrittverfahren verfahren, double[] y, double h) {

        // call nextStep as long as t <= T, the final return value is the final y array
        double[] y_end = Arrays.copyOf(y, y.length);
        double t = 0;
        
        while(t < T)
        {
            y_end = verfahren.nextStep(y_end, t, h, testODE);
            t += h;
        }

        return y_end;
    }

    /**
     * Diese Methode schätzt die Konvergenzordnung des gegebenen Verfahrens ab.
     *
     * @param verfahren das zu testende Verfahren
     * @param h Die Schrittweite h, für die die Abschätzung der Ordnung durchgeführt werden soll.
     * @return Ordnung p
     */
    public double order(Einschrittverfahren verfahren, double h)
    {
        // p ~~ ln(e_h1/e_h2) / ln(h1/h2)

        double[] yh_1 = calculate_yh(verfahren, y0, h);
        double e_h1 = error(yh_1);

        double h2 = h / 2;
        double[] yh_2 = calculate_yh(verfahren, y0, h2);
        double e_h2 = error(yh_2);

        double errors_ln = Math.log(e_h1/e_h2);
        double distances_ln = Math.log(h/h2);

        return errors_ln / distances_ln;
    }
}
