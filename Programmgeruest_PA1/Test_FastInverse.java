import javax.swing.JFrame;

/**
 * @author Christoph Riesinger (riesinge@in.tum.de)
 * @author Jürgen Bräckle (braeckle@in.tum.de)
 * @author Sebastian Rettenberger (rettenbs@in.tum.de)
 * @since November 02, 2012
 * @version 1.1
 * 
 *          This class just contains a main() method to use the FastMath class
 *          and to invoke the plotter.
 */
public class Test_FastInverse {

    /** Beispielwerte fuer IEEE Standard mit 32 Bits */
    // private static int MAGIC_NUMBER = 0x5f3759df; //converted to decimal: 1 597
    // 463 007 0b1011111001101110101100111011111
    private static int MAGIC_NUMBER = 0x5f3759df; // converted to decimal: 1 597 463 007
                                                  // 0b1011111001101110101100111011111
    private static int anzBitsExponent = 8;
    private static int anzBitsMantisse = 24;

    /**
     * Uses the FastMath class and invokes the plotter. In a logarithmically scaled
     * system, the exakt solutions of 1/sqrt(x) are shown in green, and the absolute
     * errors of the Fast Inverse Square Root in red. Can be used to test and debug
     * the own implementation of the fast inverse square root algorithm and to play
     * while finding an optimal magic number.
     * 
     * @param args args is ignored.
     */
    public static void main(String[] args) {

        Gleitpunktzahl.setSizeExponent(anzBitsExponent);
        Gleitpunktzahl.setSizeMantisse(anzBitsMantisse);
        FastMath.setMagic(MAGIC_NUMBER);

        // Gleitpunktzahl num;
        // Gleitpunktzahl gleitref;
        // Gleitpunktzahl gleiterg;

        System.out.println("Test 1/sqrt(POSITIVE_INFINITY)");
        Gleitpunktzahl num = new Gleitpunktzahl(Double.POSITIVE_INFINITY);
        Gleitpunktzahl gleitref = new Gleitpunktzahl(0);
        gleitref.setNull();

        Gleitpunktzahl gleiterg = FastMath.invSqrt(num);
        // Test, ob Ergebnis korrekt
        if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
            System.out.println("Failed in 1/sqrt(infinity) test, result is " + gleiterg.toDouble() + " should be " + gleitref.toDouble());
        } else {
            System.out.println(" Richtiges Ergebnis\n");
        }

        System.out.println("Test 1/sqrt(0)");
        num = new Gleitpunktzahl(0);
        gleitref = new Gleitpunktzahl(0);
        gleitref.setNaN();

        gleiterg = FastMath.invSqrt(num);
        // Test, ob Ergebnis korrekt
        if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
            System.out.println("Failed in 1/sqrt(0) test, result is " + gleiterg.toDouble() + " should be " + gleitref.toDouble());
        } else {
            System.out.println(" Richtiges Ergebnis\n");
        }

        // System.out.println("Test 1/sqrt(9)");
        // num = new Gleitpunktzahl(9);
        // gleitref = new Gleitpunktzahl(0.3333333333333);

        // gleiterg = FastMath.invSqrt(num);
        // // Test, ob Ergebnis korrekt
        // if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen !=
        // gleitref.vorzeichen) {
        // printInvSqrt(num);
        // printErg(gleiterg.toDouble(), gleitref.toDouble());
        // } else {
        // System.out.println(" Richtiges Ergebnis\n");
        // }

        // System.out.println("Test 1/sqrt(16)");
        // num = new Gleitpunktzahl(16);
        // gleitref = new Gleitpunktzahl(0.25);

        // gleiterg = FastMath.invSqrt(num);
        // // Test, ob Ergebnis korrekt
        // if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen !=
        // gleitref.vorzeichen) {
        // printInvSqrt(num);
        // printErg(gleiterg.toDouble(), gleitref.toDouble());
        // } else {
        // System.out.println(" Richtiges Ergebnis\n");
        // }

        // System.out.println("Test 1/sqrt(25)");
        // num = new Gleitpunktzahl(25);
        // gleitref = new Gleitpunktzahl(0.2);

        // gleiterg = FastMath.invSqrt(num);
        // // Test, ob Ergebnis korrekt
        // if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen !=
        // gleitref.vorzeichen) {
        // printInvSqrt(num);
        // printErg(gleiterg.toDouble(), gleitref.toDouble());
        // } else {
        // System.out.println(" Richtiges Ergebnis\n");
        // }

        // System.out.println("Test 1/sqrt(36)");
        // num = new Gleitpunktzahl(36);
        // gleitref = new Gleitpunktzahl(0.16666666666);

        // gleiterg = FastMath.invSqrt(num);
        // // Test, ob Ergebnis korrekt
        // if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen !=
        // gleitref.vorzeichen) {
        // printInvSqrt(num);
        // printErg(gleiterg.toDouble(), gleitref.toDouble());
        // } else {
        // System.out.println(" Richtiges Ergebnis\n");
        // }

        // System.out.println("Test 1/sqrt(49)");
        // num = new Gleitpunktzahl(49);
        // gleitref = new Gleitpunktzahl(0.14285714285);

        // gleiterg = FastMath.invSqrt(num);
        // // Test, ob Ergebnis korrekt
        // if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen !=
        // gleitref.vorzeichen) {
        // printInvSqrt(num);
        // printErg(gleiterg.toDouble(), gleitref.toDouble());
        // } else {
        // System.out.println(" Richtiges Ergebnis\n");
        // }

        // System.out.println("Test 1/sqrt(64)");
        // num = new Gleitpunktzahl(64);
        // gleitref = new Gleitpunktzahl(0.125);

        // gleiterg = FastMath.invSqrt(num);
        // // Test, ob Ergebnis korrekt
        // if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen !=
        // gleitref.vorzeichen) {
        // printInvSqrt(num);
        // printErg(gleiterg.toDouble(), gleitref.toDouble());
        // } else {
        // System.out.println(" Richtiges Ergebnis\n");
        // }

        // System.out.println("Test 1/sqrt(81)");
        // num = new Gleitpunktzahl(81);
        // gleitref = new Gleitpunktzahl(0.11111111111);

        // gleiterg = FastMath.invSqrt(num);
        // // Test, ob Ergebnis korrekt
        // if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen !=
        // gleitref.vorzeichen) {
        // printInvSqrt(num);
        // printErg(gleiterg.toDouble(), gleitref.toDouble());
        // } else {
        // System.out.println(" Richtiges Ergebnis\n");
        // }

        // System.out.println("Test 1/sqrt(100)");
        // num = new Gleitpunktzahl(100);
        // gleitref = new Gleitpunktzahl(0.1);

        // gleiterg = FastMath.invSqrt(num);
        // // Test, ob Ergebnis korrekt
        // if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen !=
        // gleitref.vorzeichen) {
        // printInvSqrt(num);
        // printErg(gleiterg.toDouble(), gleitref.toDouble());
        // } else {
        // System.out.println(" Richtiges Ergebnis\n");
        // }

        int numOfSamplingPts = 1001;
        float[] xData = new float[numOfSamplingPts];
        float[] yData = new float[numOfSamplingPts];
        float x = 0.10f;

        /* calculate data to plot */
        for (int i = 0; i < numOfSamplingPts; i++) {
            xData[i] = x;
            Gleitpunktzahl y = new Gleitpunktzahl(x);
            yData[i] = (float) FastMath.absInvSqrtErr(y);

            x *= Math.pow(100.0d, 1.0d / numOfSamplingPts);
        }

        /* initialize plotter */
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            frame.add(new Plotter(xData, yData));
        } catch (InstantiationException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
        frame.setSize(960, 720);
        frame.setLocation(0, 0);
        frame.setVisible(true);
    }

    private static void findGoodMagicNumber() {

        int numberAbs = 1024;
        FastMath.setMagic(numberAbs);
        Gleitpunktzahl.setSizeExponent(4);
        Gleitpunktzahl.setSizeMantisse(8);

        Gleitpunktzahl reallyBig = new Gleitpunktzahl((1.875 * 127));

        double cur_rel_err;
        int bestMagicNumber = -1;
        double sumOfRelativeErrors = 0;
        double smallestSum = Double.POSITIVE_INFINITY;
        for (int magicNumber = 0; magicNumber < 4000; magicNumber += 1) {
            FastMath.setMagic(magicNumber);

            sumOfRelativeErrors = 0;
            for (double num = 1; num < reallyBig.toDouble(); num += 0.125) {
                Gleitpunktzahl test = new Gleitpunktzahl(num);
                cur_rel_err = FastMath.relInvSqrtErr(test);

                if (!Double.isNaN(cur_rel_err)) {
                    sumOfRelativeErrors += cur_rel_err;
                }
            }

            if (sumOfRelativeErrors < smallestSum) {
                smallestSum = sumOfRelativeErrors;
                bestMagicNumber = magicNumber;
            }
        }

        System.out.println("Best magic number for bereich 0-238 is " + bestMagicNumber);
        System.out.println("Sum of relative errors: " + smallestSum);
    }
}
