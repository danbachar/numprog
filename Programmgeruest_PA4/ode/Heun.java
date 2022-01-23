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
        //Next step calculates the values of the y_s based on this procedure
        int length = y_k.length;
        double[] ret = Arrays.copyOf(y_k,length);
        //Derivative_f
        double[] euler_val = ode.auswerten(t, y_k);
        //r(t + delta_t) w/ line above, initializing with r-values
        double[] first_points = Arrays.copyOf(y_k,length);
        //derivative of y values at point t
        double[] der_y_val = ode.auswerten(t, y_k);
        //f(t + delta_t, first_points)
        double[] der_first_points = ode.auswerten(t+delta_t, euler_val);

        //calculating r(t + delta_t) w/ line above
        for(int i=0;i<y_k.length;i++){
            first_points[i] += delta_t*euler_val[i];
        }

        //calculating derivative of initial r points
        der_first_points = ode.auswerten(t+delta_t, first_points);

        //calculating r(t + delta_t)
        for(int i=0;i<y_k.length;i++){
            ret[i] += delta_t/2*(der_y_val[i] + der_first_points[i]);
        }
        return ret;
    }

}
