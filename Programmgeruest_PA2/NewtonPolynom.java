import java.sql.Array;
import java.util.Arrays;

/**
 * Die Klasse Newton-Polynom beschreibt die Newton-Interpolation. Die Klasse
 * bietet Methoden zur Erstellung und Auswertung eines Newton-Polynoms, welches
 * uebergebene Stuetzpunkte interpoliert.
 *
 * @author braeckle
 *
 */
public class NewtonPolynom implements InterpolationMethod {

    /** Stuetzstellen xi */
    double[] x;

    /**
     * Koeffizienten/Gewichte des Newton Polynoms p(x) = a0 + a1*(x-x0) +
     * a2*(x-x0)*(x-x1)+...
     */
    double[] a;

    /**
     * die Diagonalen des Dreiecksschemas. Diese dividierten Differenzen werden
     * fuer die Erweiterung der Stuetzstellen benoetigt.
     */
    double[] f;

    /**
     * leerer Konstruktore
     */
    public NewtonPolynom() {
    };

    /**
     * Konstruktor
     *
     * @param x
     *            Stuetzstellen
     * @param y
     *            Stuetzwerte
     */
    public NewtonPolynom(double[] x, double[] y) {
        this.init(x, y);
    }

    /**
     * {@inheritDoc} Zusaetzlich werden die Koeffizienten fuer das
     * Newton-Polynom berechnet.
     */
    @Override
    public void init(double a, double b, int n, double[] y) {
        x = new double[n + 1];
        double h = (b - a) / n;

        for (int i = 0; i < n + 1; i++) {
            x[i] = a + i * h;
        }
        computeCoefficients(y);
    }

    /**
     * Initialisierung der Newtoninterpolation mit beliebigen Stuetzstellen. Die
     * Faelle "x und y sind unterschiedlich lang" oder "eines der beiden Arrays
     * ist leer" werden nicht beachtet.
     *
     * @param x
     *            Stuetzstellen
     * @param y
     *            Stuetzwerte
     */
    public void init(double[] x, double[] y) {
        this.x = Arrays.copyOf(x, x.length);
        computeCoefficients(y);
    }

    /**
     * computeCoefficients belegt die Membervariablen a und f. Sie berechnet zu
     * uebergebenen Stuetzwerten y, mit Hilfe des Dreiecksschemas der
     * Newtoninterpolation, die Koeffizienten a_i des Newton-Polynoms. Die
     * Berechnung des Dreiecksschemas soll dabei lokal in nur einem Array der
     * Laenge n erfolgen (z.B. spaltenweise Berechnung). Am Ende steht die
     * Diagonale des Dreiecksschemas in der Membervariable f, also f[0],f[1],
     * ...,f[n] = [x0...x_n]f,[x1...x_n]f,...,[x_n]f. Diese koennen spaeter bei
     * der Erweiterung der Stuetzstellen verwendet werden.
     *
     * Es gilt immer: x und y sind gleich lang.
     */
    private void computeCoefficients(double[] y) {

        int n=y.length;
        int numCoeff = 0;

        //finding number of coefficients
        for(int i=0;i<n;i++){
            numCoeff+=n-i;
        }

        //setting the length of f
        f = new double[numCoeff];

        //the coefficient Schema
        double[][] coefficients = new double[n][n];

        //setting ci,0 to the y values
        for(int i=0;i<n;i++){
            coefficients[i][0]=y[i];
        }        

        //setting the rest of the coefficients to the correct values
        for(int i=0;i<n;i++){
            for(int k=0; k<n;k++){
                if(i<n-1 && k-1>=0 && (i+k)<n && (x[i+k]-x[i])!=0){
                     coefficients[i][k] = (coefficients[i+1][k-1]-coefficients[i][k-1])/(x[i+k] - x[i]);
                }
            }
        }

        //setting the value of a
        a = new double[n];
        for(int i=0;i<n;i++){
            a[i] = coefficients[0][i];
        }

        /*
        setting the value of of the diagonal, 
        the diagonal is then used for computing the new coefficients for a newly added point

        Am Ende steht die
        Diagonale des Dreiecksschemas in der Membervariable f, 
        also f[0],f[1],...,f[n] = [x0...x_n]f,[x1...x_n]f,...,[x_n]f.
        */
        f = new double[n];
        for(int i=0;i<n;i++){
            f[i] = coefficients[n-1-i][i];
        }
    }


    /**
     * Gibt die Koeffizienten des Newton-Polynoms a zurueck
     */
    public double[] getCoefficients() {
        return a;
    }

    /**
     * Gibt die Dividierten Differenzen der Diagonalen des Dreiecksschemas f
     * zurueck
     */
    public double[] getDividedDifferences() {
        return f;
    }

    /**
     * addSamplintPoint fuegt einen weiteren Stuetzpunkt (x_new, y_new) zu x
     * hinzu. Daher werden die Membervariablen x, a und f vergoessert und
     * aktualisiert . Das gesamte Dreiecksschema muss dazu nicht neu aufgebaut
     * werden, da man den neuen Punkt unten anhaengen und das alte
     * Dreiecksschema erweitern kann. Fuer diese Erweiterungen ist nur die
     * Kenntnis der Stuetzstellen und der Diagonalen des Schemas, bzw. der
     * Koeffizienten noetig. Ist x_new schon als Stuetzstelle vorhanden, werden
     * die Stuetzstellen nicht erweitert.
     *
     * @param x_new
     *            neue Stuetzstelle
     * @param y_new
     *            neuer Stuetzwert
     */
    public void addSamplingPoint(double x_new, double y_new) {

        //adding x_new to the array
        double[] arr = new double[x.length + 1];
        System.arraycopy(x, 0, arr, 0, x.length);


        //creating a new array for the coefficients
        double[] coeff = new double[x.length + 1];
        System.arraycopy(a, 0, coeff, 0, a.length);


        //calculating the new diagonal
        double[] diagonal = new double[f.length+1];

        diagonal[0] = y_new;

        for(int i=1;i<f.length+1;i++){
            //x_i+k is always going to stay constant, equalling x_new. Due to the fact that the indexes are on the diagonal
            diagonal[i] = (diagonal[i-1] - f[i-1])/(x_new - x[x.length-1-i]);
        }
        
        //setting the new coefficient in the coefficient array
        coeff[(f.length+1)-1] = diagonal[(f.length+1)-1];
        arr = coeff;

        //setting the f value
        f = diagonal;
    }

    /**
     * {@inheritDoc} Das Newton-Polynom soll effizient mit einer Vorgehensweise
     * aehnlich dem Horner-Schema ausgewertet werden. Es wird davon ausgegangen,
     * dass die Stuetzstellen nicht leer sind.
     */
    @Override
    public double evaluate(double z) {
        /* TODO: diese Methode ist zu implementieren */

        return 0.0;
    }
}
