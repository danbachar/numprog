package ode;

import java.util.Arrays;

/**
 * Das Einschrittverfahren von Heun
 *
 * @author braeckle
 *
 */
public class Heun implements Einschrittverfahren {

    @Override
    /**
     * {@inheritDoc}
     * Nutzen Sie dabei geschickt den Expliziten Euler.
     */
    public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {
        int length = y_k.length;
        double[] ret = Arrays.copyOf(y_k,length);
        double[] euler_value = ode.auswerten(t, y_k);
        double[] euler_value_tstar = ode.auswerten(t+delta_t, euler_value);
        
        for (int i = 0; i < length; i++) {
            double elem = ret[i];
            double euler_elem = elem + delta_t * euler_value[i];
            double euler_tstar_elem = euler_value_tstar[i];
            ret[i] = elem + 0.5 * delta_t * (euler_elem + euler_tstar_elem);
        }

        return ret;
    }

}
