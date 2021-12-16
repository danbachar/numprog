import java.util.Arrays;
import java.util.Comparator;

public class PageRank {

    /**
     * Diese Methode erstellt die Matrix A~ fuer das PageRank-Verfahren
     * PARAMETER:
     * L: die Linkmatrix (s. Aufgabenblatt)
     * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
     * zufaellig irgendeine Seite zu besuchen
     */
    public static double[][] buildProbabilityMatrix(int[][] L, double rho) {
        // Die Linkmatrix ist die Matrix, die die Informationen enthaelt, welche
        // Links existieren.
        // The array maps to the matrix exactly: A[i][j]
        // The link matrix include in L[i][j] = 1 if j points to 1

        // size of matrix
        int n = L.length;

        double[][] matrix = new double[n][n];

        // Initializing the modified matrix with the p value.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = rho / n;
            }
        }

        // adding the modified probabilities to the matrix, reversing the indexes j and
        // i in the loops
        for (int j = 0; j < n; j++) {
            // counting the number of links
            int numLinks = 0;
            for (int i = 0; i < n; i++) {
                if (L[i][j] == 1)
                    numLinks++;
            }
            // from the above line, you get the number of absolute links
            for (int i = 0; i < n; i++) {
                // adding the probabilities to the matrix array
                if (L[i][j] == 1){
                    matrix[i][j] += (1 - rho) * (((double) 1) / numLinks);
                }
            }
        }

        //the block above should add the probabilities 1/2 to each one

        //TODO: remove this line afterwards, printing the probability matrix

        // System.out.println("Printing the prob. matrix:");

        // for(int i=0;i<n;i++){
        //     System.out.print("[");
        //     for(int j=0;j<n;j++){
        //         System.out.print(matrix[i][j] + " ");
        //     }
        //     System.out.println("]");
        // }
        return matrix;
    }

    /**
     * Diese Methode berechnet die PageRanks der einzelnen Seiten,
     * also das Gleichgewicht der Aufenthaltswahrscheinlichkeiten.
     * (Entspricht dem p-Strich aus der Angabe)
     * Die Ausgabe muss dazu noch normiert sein.
     * PARAMETER:
     * L: die Linkmatrix (s. Aufgabenblatt)
     * rho: Wahrscheinlichkeit, zufaellig irgendeine Seite zu besuchen
     * ,anstatt einem Link zu folgen.
     *
     */
    public static double[] rank(int[][] L, double rho) {
        // Multiply the probability matrix with the vector of probabilities
        // until the vector of probabilities doesn't change anymore
        // Das Gleichgewicht: A~ * p = p.
        int n = L.length;
        double[] p = new double[n];

        // initializing the p to initial values
        for (int i = 0; i < n; i++) {
            p[i] = ((double)1) / n;
        }
        // Matrix A~
        double[][] a = buildProbabilityMatrix(L, rho);

        double[] temp_p = new double[n];
        double[] prev_p = new double[n];

        // multiplying a with temp_p while the probabilities change
        while (!compareArray(prev_p, p)) {
            // copying the p array into the prev_p
            copyArray(prev_p, p);
            for (int i = 0; i < n; i++) {
                // resetting the temporary probability to zero
                temp_p[i] = 0;
                for (int j = 0; j < n; j++) {
                    temp_p[i] += a[i][j] * p[j];
                }
            }
            // copying the new probabilities into p
            copyArray(p, temp_p);
        }

        // normalizing the probability vector
        {
            double lambda = 0;
            for (int i = 0; i < n; i++) {
                lambda += p[i];
            }
            lambda = 1 / lambda;

            for (int i = 0; i < n; i++) {
                p[i] *= lambda;
            }
        }

        //return a 2d array with the corresponding probabilities

        return p;
    }

    // copies the arrays and returns true if the values are the same
    private static boolean compareArray(double[] arr1, double[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i])
                return false;
        }
        return true;
    }

    // copies array 2 into array 1
    private static void copyArray(double[] arr1, double[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = arr2[i];
        }
    }

    /**
     * Diese Methode erstellt eine Rangliste der uebergebenen URLs nach
     * absteigendem PageRank.
     * PARAMETER:
     * urls: Die URLs der betrachteten Seiten
     * L: die Linkmatrix (s. Aufgabenblatt)
     * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
     * zufaellig irgendeine Seite zu besuchen
     */
    public static String[] getSortedURLs(String[] urls, int[][] L, double rho) {
        int n = L.length;

        double[] p = rank(L, rho);

        RankPair[] sortedPairs = new RankPair[n];
        for (int i = 0; i < n; i++) {
            sortedPairs[i] = new RankPair(urls[i], p[i]);
        }

        Arrays.sort(sortedPairs, new Comparator<RankPair>() {

            @Override
            public int compare(RankPair o1, RankPair o2) {
                return -Double.compare(o1.pr, o2.pr);
            }
        });

        String[] sortedUrls = new String[n];
        for (int i = 0; i < n; i++) {
            sortedUrls[i] = sortedPairs[i].url;
        }

        return sortedUrls;
    }

    /**
     * Ein RankPair besteht aus einer URL und dem zugehoerigen Rang, und dient
     * als Hilfsklasse zum Sortieren der Urls
     */
    private static class RankPair {
        public String url;
        public double pr;

        public RankPair(String u, double p) {
            url = u;
            pr = p;
        }
    }

    //TODO: remove this line afterwards
    // public static void main(String[] args){
    //     //testing page rank here
    //     //page rank matrix with rho = 0.0

    //     //test 1
    //     int[][] l = new int[][]{{1,0,1},{0,1,1},{1,0,1}};
    //     double rho = 0.5;
    //     double[] p = rank(l, rho);
    //     for(int i=0;i<p.length;i++){
    //         System.out.print(p[i] + " ");
    //     }

    //     //test 2
    //     System.out.println("Test 2:");
    //     System.out.println();
    //     l = new int[][]{{1,1},{1,1}};
    //     rho = 0.0;
    //     p = rank(l, rho);
    //     for(int i=0;i<p.length;i++){
    //         System.out.print(p[i] + " ");
    //     }

    //     //test 3
    //     System.out.println("Test 3: ");
    //     l = new int[][]{{1,0,1,0},{0,1,0,0},{1,1,1,1},{0,0,1,1}};
    //     rho = 0.15;
    //     p = rank(l, rho);
    //     for(int i=0;i<p.length;i++){
    //         System.out.print(p[i] + " ");
    //     }

    // }
}
