package ode;

import java.util.Arrays;

/**
 * Das Einschrittverfahren "Expliziter Euler"
 *
 * @author braeckle
 *
 */
public class ExpliziterEuler implements Einschrittverfahren {


    public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {
        int length = y_k.length;
        double[] ret = Arrays.copyOf(y_k,length);
        double[] newY_k = ode.auswerten(t, y_k);
        
        for (int i = 0; i < length; i++) {
            double elem = ret[i];
            double new_elem = newY_k[i];
            
            ret[i] = elem + delta_t * new_elem;
        }

        return ret;
    }

}
