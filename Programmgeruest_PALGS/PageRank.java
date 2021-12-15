import java.util.Arrays;
import java.util.Comparator;

public class PageRank {

    /**
     * Diese Methode erstellt die Matrix A~ fuer das PageRank-Verfahren
     * PARAMETER:
     * L: die Linkmatrix (s. Aufgabenblatt)
     * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
     *      zufaellig irgendeine Seite zu besuchen
     */
    public static double[][] buildProbabilityMatrix(int[][] L, double rho) {
        //Die Linkmatrix ist die Matrix, die die Informationen enthaelt, welche 
        //Links existieren.
        //The array maps to the matrix exactly: A[i][j]
        //The link matrix include in L[i][j] = 1 if j points to 1

        //size of matrix
        int n = L.length;

        double[][] matrix = new double[n][n];
        
        //Initializing the modified matrix with the p value.
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                matrix[i][j] = rho/n;
            }
        }

        //adding the modified probabilities to the matrix, reversing the indexes j and i in the loops
        for(int j=0;j<n;j++){
            //counting the number of links
            int numLinks = 0;
            for(int i=0;i<n;i++){
                if(L[i][j]==1)
                    numLinks++;
            }
            //from the above line, you get the number of absolute links
            for(int i=0;i<n;i++){
                //adding the probabilities to the matrix array
                if(L[i][j]==1)
                    matrix[i][j] += (1-rho)*(1/numLinks);
            }
        }

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
        //TODO: Diese Methode ist zu implementieren
        return new double[2];
    }

    /**
     * Diese Methode erstellt eine Rangliste der uebergebenen URLs nach
     * absteigendem PageRank.
     * PARAMETER:
     * urls: Die URLs der betrachteten Seiten
     * L: die Linkmatrix (s. Aufgabenblatt)
     * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
     *      zufaellig irgendeine Seite zu besuchen
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
}
