import java.util.Arrays;
/**
 * Die Klasse CubicSpline bietet eine Implementierung der kubischen Splines. Sie
 * dient uns zur effizienten Interpolation von aequidistanten Stuetzpunkten.
 *
 * @author braeckle
 *
 */
public class CubicSpline implements InterpolationMethod {

    /** linke und rechte Intervallgrenze x[0] bzw. x[n] */
    double a, b;

    /** Anzahl an Intervallen */
    int n;

    /** Intervallbreite */
    double h;

    /** Stuetzwerte an den aequidistanten Stuetzstellen */
    double[] y;

    /** zu berechnende Ableitunge an den Stuetzstellen */
    double[] yprime;

    /**
     * {@inheritDoc} Zusaetzlich werden die Ableitungen der stueckweisen
     * Polynome an den Stuetzstellen berechnet. Als Randbedingungen setzten wir
     * die Ableitungen an den Stellen x[0] und x[n] = 0.
     */
    @Override
    public void init(double a, double b, int n, double[] y) {
        this.a = a;
        this.b = b;
        this.n = n;
        h = ((double) b - a) / (n);

        this.y = Arrays.copyOf(y, n + 1);

        /* Randbedingungen setzten */
        yprime = new double[n + 1];
        yprime[0] = 0;
        yprime[n] = 0;

        /* Ableitungen berechnen. Nur noetig, wenn n > 1 */
        if (n > 1) {
            computeDerivatives();
        }
    }

    /**
     * getDerivatives gibt die Ableitungen yprime zurueck
     */
    public double[] getDerivatives() {
        return yprime;
    }

    /**
     * Setzt die Ableitungen an den Raendern x[0] und x[n] neu auf yprime0 bzw.
     * yprimen. Anschliessend werden alle Ableitungen aktualisiert.
     */
    public void setBoundaryConditions(double yprime0, double yprimen) {
        yprime[0] = yprime0;
        yprime[n] = yprimen;
        if (n > 1) {
            computeDerivatives();
        }
    }

    /**
     * Berechnet die Ableitungen der stueckweisen kubischen Polynome an den
     * einzelnen Stuetzstellen. Dazu wird ein lineares System Ax=c mit einer
     * Tridiagonalen Matrix A und der rechten Seite c aufgebaut und geloest.
     * Anschliessend sind die berechneten Ableitungen y1' bis yn-1' in der
     * Membervariable yprime gespeichert.
     *
     * Zum Zeitpunkt des Aufrufs stehen die Randbedingungen in yprime[0] und yprime[n].
     * Speziell bei den "kleinen" Faellen mit Intervallzahlen n = 2
     * oder 3 muss auf die Struktur des Gleichungssystems geachtet werden. Der
     * Fall n = 1 wird hier nicht beachtet, da dann keine weiteren Ableitungen
     * berechnet werden muessen.
     */
    public void computeDerivatives() {
        /* TODO: diese Methode ist zu implementieren */
        double[] sols = new double[n-1];
        if(n == 2){
            sols[n-2] = 3 * (y[n] - y[n-2] - (h * yprime[n] / 3)) / h - yprime[0];
        } else sols[n-2] = 3 * (y[n] - y[n-2] - (h * yprime[n] / 3)) / h;
        if(n > 3){
            // Case 3: more than 2 unknowns
            for(int i = 1; i < n - 2; i ++){
                sols[i] = (y[i+2] - y[i]) * 3/h;
            }
        }
        if(n > 2){
            sols[0] = (3 / h) * (y[2] - y[0] - (h / 3) * yprime[0]);
        }


        double[] lowerD = new double[n-2];
        double[] middle = new double[n-1];
        double[] upperD = new double[n-2];
        Arrays.fill(lowerD, 1);
        Arrays.fill(middle, 4);
        Arrays.fill(upperD, 1);
        TridiagonalMatrix matrix = new TridiagonalMatrix(lowerD, middle, upperD);
        double[] answers = matrix.solveLinearSystem(sols);
        System.arraycopy(answers, 0, yprime, 1, answers.length);
    }

    /**
     * {@inheritDoc} Liegt z ausserhalb der Stuetzgrenzen, werden die
     * aeussersten Werte y[0] bzw. y[n] zurueckgegeben. Liegt z zwischen den
     * Stuetzstellen x_i und x_i+1, wird z in das Intervall [0,1] transformiert
     * und das entsprechende kubische Hermite-Polynom ausgewertet.
     */
    @Override
    public double evaluate(double z) {
        if(z > b){
            return y[n];
        }

        if(z < a){
            return y[0];
        }

        int lowerPointInd = 0;
        //i) Find the interval
        for(int i = 0; i < n; i++){
            if(z >= a + h * i && z < a + h * (i+1)){
                lowerPointInd = i;
                break;
            }
        }
        double transformed = (z - (a + h * lowerPointInd)) / h;
        return q(transformed, lowerPointInd);
    }

    private double q(double t, int intervalBottom){

        double H_0 = 1 - 3 * Math.pow(t, 2) + 2 * Math.pow(t, 3);
        double H_1 = 3 * Math.pow(t, 2) - 2 * Math.pow(t, 3);
        double H_2 = t - 2 * Math.pow(t, 2) + Math.pow(t, 3);
        double H_3 = -1 * Math.pow(t, 2) + Math.pow(t, 3);

        return y[intervalBottom] * H_0 +
                y[intervalBottom + 1] * H_1 +
                h * yprime[intervalBottom] * H_2 +
                h * yprime[intervalBottom + 1] * H_3;
    }
}
