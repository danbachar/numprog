package ode;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Der klassische Runge-Kutta der Ordnung 4
 *
 * @author braeckle
 *
 */
public class RungeKutta4 implements Einschrittverfahren {

    @Override
    /**
     * {@inheritDoc}
     * Bei der Umsetzung koennen die Methoden addVectors und multScalar benutzt werden.
     */
    public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {
        double[] ret = Arrays.copyOf(y_k,y_k.length);
        double[] y_1 = ode.auswerten(t, y_k);
        double[] y_2 = new double[y_k.length];
        double[] y_3 = new double[y_k.length];
        double[] y_4 = new double[y_k.length];

        //Calculating value of y_1
        y_1 = multScalar(y_1, delta_t);

        //calculating y_2
        y_2 = multScalar(ode.auswerten(t + 1/2*delta_t, addVectors(y_k, multScalar(y_1, 1/2))), delta_t);

        //calculating y_3
        y_3 = multScalar(ode.auswerten(t + 1/2*delta_t, addVectors(y_k, multScalar(y_2, 1/2))), delta_t);

        //calculating y_4
        y_4 = multScalar(ode.auswerten(t + delta_t, addVectors(y_k, y_3)), delta_t);

        //calculating r(t + delta_t)
        ret = addVectors(ret, multScalar(addVectors(y_1, addVectors(multScalar(y_2, 2), addVectors(multScalar(y_3, 2), y_4))), 1/6));

        ret = multScalar(ret, 2);
        //Why does multiplying with 2 give the result of: [-1,3] to [-4, -12]
        return ret;
    }

    /**
     * addiert die zwei Vektoren a und b
     */
    private double[] addVectors(double[] a, double[] b) {
        double[] erg = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            erg[i] = a[i] + b[i];
        }
        return erg;
    }

    /**
     * multipliziert den Skalar scalar auf den Vektor a
     */
    private double[] multScalar(double[] a, double scalar) {
        double[] erg = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            erg[i] = scalar * a[i];
        }
        return erg;
    }

}
