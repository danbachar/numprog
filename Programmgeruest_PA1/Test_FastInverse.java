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
	private static int MAGIC_NUMBER = 0x5f3759df; //converted to decimal: 1 597 463 007 0b1011111001101110101100111011111
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
		// first trying out manually, setting the magic number to 1000
		//
		//FastMath.setMagic(1597463007);
		Gleitpunktzahl.setSizeExponent(2); //changed from 4 to 2
		Gleitpunktzahl.setSizeMantisse(8);
		//FastMath.setMagic(0);
		// findGoodMagicNumber()

		Gleitpunktzahl.setSizeExponent(anzBitsExponent);
		Gleitpunktzahl.setSizeMantisse(anzBitsMantisse);
		FastMath.setMagic(MAGIC_NUMBER);

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
		int numberRel = 1024;
		FastMath.setMagic(numberAbs);
		Gleitpunktzahl.setSizeExponent(4);
		Gleitpunktzahl.setSizeMantisse(8);
		Gleitpunktzahl test = new Gleitpunktzahl(1000.0);
		double min_abs_error = FastMath.absInvSqrtErr(test);
		double min_rel_error = FastMath.relInvSqrtErr(test);
		double cur_abs_err;
		double cur_rel_err;
		for (int step = 1; step < 502; step++) {
			FastMath.setMagic(1024 + 2 * step);

			cur_abs_err = FastMath.absInvSqrtErr(test);
			cur_rel_err = FastMath.relInvSqrtErr(test);

			if (cur_abs_err < min_abs_error) {
				numberAbs = 1024 + 2 * step;
				min_abs_error = cur_abs_err;
			}
			if (cur_rel_err < min_rel_error) {
				numberRel = 1024 + 2 * step;
				min_rel_error = cur_rel_err;
			}
		}
		for (int step = 1; step < 502; step++) {
			FastMath.setMagic(1024 - 2 * step);
			cur_abs_err = FastMath.absInvSqrtErr(test);
			cur_rel_err = FastMath.relInvSqrtErr(test);

			if (cur_abs_err < min_abs_error) {
				numberAbs = 1024 - 2 * step;
				min_abs_error = cur_abs_err;
			}
			if (cur_rel_err < min_rel_error) {
				numberRel = 1024 - 2 * step;
				min_rel_error = cur_rel_err;
			}
		}
		System.out.println("The smallest rel error is " + min_rel_error + ", the magic number is " + numberRel);
		System.out.println("The smallest abs error is " + min_abs_error + ", the magic number is " + numberAbs);
	}
}
