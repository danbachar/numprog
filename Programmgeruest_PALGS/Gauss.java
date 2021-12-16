import java.util.Arrays;

public class Gauss {

    private static int findIndexOfFirstNonNull(double[][] R, int from, int col) {
        for (int i = from; i < R.length; i++) {
            if (R[i][col] != 0) {
                return i;
            }
        }
        
        return -1; // theoretically shouldn't be possible because matrix is in obere dreicksform, which means there is inherently one non-zero to be found, otherwise matrix is not invertible
    }

    // this method returns the sum of the array elements, multiplied with their respective coefficient values
    // this method assumes arr.length == coefficients.length
    private static double sumWithCoeffs(double[] arr, double[] coefficients) {
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += coefficients[i] * arr[i];
        }
        return sum;
    }

    private static int findIndexOfGreatestElement(double[][] arr, int from, int col) {
        double max = Math.abs(arr[from][col]);
        int index = from;
        for (int row = from; row <arr.length; row++) {
            double currElem = Math.abs(arr[row][col]);
            if (currElem > max) {
                max = currElem;
                index = row;
            }
        }

        return index;

    }
    
    // this method switches row j and k in the matrix a
    private static double[][] switchRows(double[][] a, double[] b, int j, int k) {
        double[] keep = a[j];
        a[j] = a[k];
        a[k] = keep;

        double bKeep = b[j];
        b[j] = b[k];
        b[k] = bKeep;
        
        return a;
    }
    
    /**
     * Diese Methode soll die Loesung x des LGS R*x=b durch
     * Rueckwaertssubstitution ermitteln.
     * PARAMETER:
     * R: Eine obere Dreiecksmatrix der Groesse n x n
     * b: Ein Vektor der Laenge n
     */
    public static double[] backSubst(double[][] R, double[] b) {
        double x[] = new double[b.length];
        for (int i=R.length-1; i>=0; i--) {
            double[] arr = R[i];
            double elem = arr[i];
            if (elem == 0) {
                // if elem is zero, switch with first non-null to avoid null division
                // TODO: test if this can happen, see exam test with Nulldivision that doesn't pass and is commented out
                int indexOfRowForSwap = findIndexOfFirstNonNull(R, i+1 ,i);
                double[] keep = R[i];
                R[i] = R[indexOfRowForSwap];
                R[indexOfRowForSwap] = keep;
                double bKeep = b[i];
                b[i] = b[indexOfRowForSwap];
                b[indexOfRowForSwap] = bKeep;
                arr = R[i];
                elem = arr[i];
                bKeep = x[i];
                x[i] = x[indexOfRowForSwap];
            }
            double bElem = b[i];
            x[i] = bElem / elem;
            double sum = sumWithCoeffs(arr, x);

            x[i] -= sum / elem - x[i];
        }
        
        return x;
    }

    /**
     * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination mit
     * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden.
     * PARAMETER: A:
     * Eine regulaere Matrix der Groesse n x n
     * b: Ein Vektor der Laenge n
     */
    public static double[] solve(double[][] A, double[] b) {
        int length = A.length;
        int k=0;
        for (k = 0; k < length-1; k++) {
            int j = findIndexOfGreatestElement(A, k+1, k);
            if (j != k && Math.abs(A[j][k]) > Math.abs(A[k][k])) {
                A = switchRows(A, b, j, k);
            }
            // at this point A[x][k] is the greatest (absolute value wise) element for all x
            int i = k+1;
            for(; i<length; i++) { // deduct the current row from all the next rows 
                if (A[i][k] != 0) {
                    double factor = A[i][k] / A[k][k];
                    for (j = k; j < A.length; j++) { // all columns before k have already been processed
                        A[i][j] -= factor*A[k][j];
                    }
                    b[i] -= factor * b[k];
                }
            }
        }
    
        // if k is last row (and last column), assume that all previous columns have been processed, solve using backwards substitution
        return backSubst(A, b);
    }

    private static void switch_lines(double[][] A, int a, int b){
        double tmp;
        for( int i = 0; i < A[0].length; i++){
            tmp = A[a][i];
            A[a][i] = A[b][i];
            A[b][i] = tmp;
        }
    }
    private static void subtracteLine(double[][] A, int baseLine, int toSubtract, double koef){
        for(int i = 0; i < A[baseLine].length; i++) {
            A[toSubtract][i] -= A[baseLine][i] * koef;
        }
    }
    /**
     * Diese Methode soll eine Loesung p!=0 des LGS A*p=0 ermitteln. A ist dabei
     * eine nicht invertierbare Matrix. A soll dabei nicht veraendert werden.
     *
     * Gehen Sie dazu folgendermassen vor (vgl.Aufgabenblatt):
     * -Fuehren Sie zunaechst den Gauss-Algorithmus mit Spaltenpivotisierung
     *  solange durch, bis in einem Schritt alle moeglichen Pivotelemente
     *  numerisch gleich 0 sind (d.h. <1E-10)
     * -Betrachten Sie die bis jetzt entstandene obere Dreiecksmatrix T und
     *  loesen Sie Tx = -v durch Rueckwaertssubstitution
     * -Geben Sie den Vektor (x,1,0,...,0) zurueck
     *
     * Sollte A doch intvertierbar sein, kann immer ein Pivot-Element gefunden werden(>=1E-10).
     * In diesem Fall soll der 0-Vektor zurueckgegeben werden.
     * PARAMETER:
     * A: Eine singulaere Matrix der Groesse n x n
     */
    public static double[] solveSing(double[][] A) {
        int firstZeroLine = -1;

        if(A.length==0){
            return new double[0];
        }
        double[][] A_copy = new double[A.length][A[0].length];
        for(int i = 0; i < A_copy.length; i++){
            System.arraycopy(A[i], 0, A_copy[i], 0, A[i].length);
        }
        for(int i = 0; i < A_copy.length; i++){
            int lineWithMaxVal = i;
            for(int line = i+1; line< A_copy.length; line++){
                if(A_copy[line][i] < A_copy[lineWithMaxVal][i] && A_copy[line][i] != 0){
                    lineWithMaxVal = line;
                }
            }
            if(Math.abs(A_copy[lineWithMaxVal][i])< Math.pow(10, -10)){
                firstZeroLine = i;
                break;
            }
            if(lineWithMaxVal != i){
                switch_lines(A_copy, i, lineWithMaxVal);
            }
            for(int j = i + 1; j < A_copy.length; j++){
                subtracteLine(A_copy, i, j, A_copy[j][i]/A_copy[i][i]);
            }
        }
        double[] output = new double[A_copy[0].length];
        if(firstZeroLine == -1){
            return output;
        }

        double[][] T = new double[firstZeroLine][firstZeroLine];
        for(int i = 0; i < firstZeroLine; i++){
            for(int j = 0; j < firstZeroLine; j++){
                T[i][j] = A_copy[i][j];
            }
        }
        double[] v_neg = new double[firstZeroLine];
        for (int i = 0; i < firstZeroLine; i++){
            v_neg[i] = -1 * A_copy[i][firstZeroLine];
        }
        double[] x = backSubst(T, v_neg);
        System.arraycopy(x, 0, output, 0, x.length);
        output[x.length] = 1.0;
        return output;
    }

    /**
     * Diese Methode berechnet das Matrix-Vektor-Produkt A*x mit A einer nxm
     * Matrix und x einem Vektor der Laenge m. Sie eignet sich zum Testen der
     * Gauss-Loesung
     */
    public static double[] matrixVectorMult(double[][] A, double[] x) {
        int n = A.length;
        int m = x.length;

        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            y[i] = 0;
            for (int j = 0; j < m; j++) {
                y[i] += A[i][j] * x[j];
            }
        }

        return y;
    }
}
