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
        double[] euler = ode.auswerten(t, y_k);
        double[] k1 = multScalar(euler, delta_t);

        double[] addPosHalfK1 = addVectors(y_k, multScalar(k1, 0.5));
        double[] eulerHalfStepK2 = ode.auswerten(t+0.5*delta_t, addPosHalfK1);
        double[] k2 = multScalar(eulerHalfStepK2, delta_t);

        double[] addPosHalfK2 = addVectors(y_k, multScalar(k2, 0.5));
        double[] eulerHalfStepK3 = ode.auswerten(t+0.5*delta_t, addPosHalfK2);
        double[] k3 = multScalar(eulerHalfStepK3, delta_t);

        double[] addPosK3 = addVectors(y_k, k3);
        double[] eulerStepK3 = ode.auswerten(t+delta_t, addPosK3);
        double[] k4 = multScalar(eulerStepK3, delta_t);

        double[] twiceK2 = multScalar(k2, 2.0);
        double[] twiceK3 = multScalar(k3, 2.0);
        double[] sumDoubles = addVectors(twiceK2, twiceK3);
        double[] sumSingles = addVectors(k1, k4);
        double[] sumSums = addVectors(sumDoubles, sumSingles);
        double[] sixthSumCoefficients = multScalar(sumSums, 1.0/6.0);

        return addVectors(y_k, sixthSumCoefficients);
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
