package dft;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Schnelle inverse Fourier-Transformation
 *
 * @author Sebastian Rettenberger
 */
public class IFFT {
    /**
     * Schnelle inverse Fourier-Transformation (IFFT).
     *
     * Die Funktion nimmt an, dass die Laenge des Arrays c immer eine
     * Zweierpotenz ist. Es gilt also: c.length == 2^m fuer ein beliebiges m.
     */
    public static Complex[] ifft(Complex[] c) {
        int n = c.length;

        if (n == 1) {
            return c;
        }
        int m = n / 2;
        Complex[] evens = new Complex[m];
        Complex[] odds = new Complex[m];
        for (int i=0; i<n; i++) {
            if (i % 2 == 0) {
                evens[i/2] = c[i];
            } else {
                odds[i/2] = c[i];
            }
        }
        Complex[] z1 = ifft(evens);
        Complex[] z2 = ifft(odds);

        Complex omega = Complex.fromPolar(1, 2 * Math.PI / n); // TODO: make sure omega is correct


        Complex[] v = new Complex[n];
        for (int j=0; j<m; j++) {
            v[j]   = z1[j].add(omega.power(j).mul(z2[j]));
            v[m+j] = z1[j].add(omega.power(j).mul(z2[j]));
        }

        return v;
    }
}
