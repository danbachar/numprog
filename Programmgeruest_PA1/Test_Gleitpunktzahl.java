import java.util.Arrays;

public class Test_Gleitpunktzahl {
	/**
	 * Testklasse, ob alles funktioniert!
	 */

	public static void main(String[] argv) {
		test_Gleitpunktzahl();
		test_eigentests();
	}

	public static void test_eigentests() {
		Gleitpunktzahl x;
		Gleitpunktzahl y;
		Gleitpunktzahl gleitref = new Gleitpunktzahl();
		Gleitpunktzahl gleiterg;

        // Test: 1.9999999 + 2.9999999 = 
        System.out.println("Test 1.9999999 + 2.9999999");
		x = new Gleitpunktzahl(1.9999999);
		y = new Gleitpunktzahl(2.9999999);

		gleitref = new Gleitpunktzahl((1.9999999 + 2.9999999));
		gleiterg = x.add(y);
		// Test, ob Ergebnis korrekt
		if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
			printAdd(x.toString(), y.toString());
			printErg(gleiterg.toString(), gleitref.toString());
		} else {
			System.out.println("    Richtiges Ergebnis\n");
		}

		// Test: 1.5 + 2.25 = 3.75
        System.out.println("Test 1.5 + 2.25");
		x = new Gleitpunktzahl(1.5);
		y = new Gleitpunktzahl(2.25);

		gleitref = new Gleitpunktzahl((1.5 + 2.25));
		gleiterg = x.add(y);
		// Test, ob Ergebnis korrekt
		if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
			printAdd(x.toString(), y.toString());
			printErg(gleiterg.toString(), gleitref.toString());
		} else {
			System.out.println("    Richtiges Ergebnis\n");
		}
		// Test: 1 - 0 = 1
        System.out.println("Test 1 - 0 = 1");
		x = new Gleitpunktzahl(1);
		y = new Gleitpunktzahl(0);

		gleitref = new Gleitpunktzahl((1 - 0));
		gleiterg = x.sub(y);
		// Test, ob Ergebnis korrekt
		if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
			printSub(x.toString(), y.toString());
			printErg(gleiterg.toString(), gleitref.toString());
		} else {
			System.out.println("    Richtiges Ergebnis\n");
		}

		// Test: num + Infinity = Infinity
        System.out.println("Test 1 + Infinity = Infinity");
		x = new Gleitpunktzahl(1);
		y = new Gleitpunktzahl(0);
		y.setInfinite(false);

		gleitref = new Gleitpunktzahl(0);
		gleitref.setInfinite(false);

		gleiterg = x.add(y);
		// Test, ob Ergebnis korrekt
		if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
			printAdd(x.toString(), y.toString());
			printErg(gleiterg.toString(), gleitref.toString());
		} else {
			System.out.println("    Richtiges Ergebnis\n");
		}

        // Test: num - (+Infinity) = -Infinity
        System.out.println("Test 1 - (+Infinity) = -Infinity");
		x = new Gleitpunktzahl(1);
		y = new Gleitpunktzahl(0);
		y.setInfinite(false);

		gleitref = new Gleitpunktzahl(0);
		gleitref.setInfinite(true);

		gleiterg = x.sub(y);
		// Test, ob Ergebnis korrekt
		if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
			printAdd(x.toString(), y.toString());
			printErg(gleiterg.toString(), gleitref.toString());
		} else {
			System.out.println("    Richtiges Ergebnis\n");
		}

        // Test: num + (+Infinity) = Infinity
        System.out.println("Test 1 + (+Infinity) = Infinity");
		x = new Gleitpunktzahl(1);
		y = new Gleitpunktzahl(0);
		y.setInfinite(false);

		gleitref = new Gleitpunktzahl(0);
		gleitref.setInfinite(false);

		gleiterg = x.add(y);
		// Test, ob Ergebnis korrekt
		if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
			printAdd(x.toString(), y.toString());
			printErg(gleiterg.toString(), gleitref.toString());
		} else {
			System.out.println("    Richtiges Ergebnis\n");
		}

        // Test: num + (-Infinity) = -Infinity
        System.out.println("Test 1 + (-Infinity) = -Infinity");

		x = new Gleitpunktzahl(1);
		y = new Gleitpunktzahl(0);
		y.setInfinite(true);

		gleitref = new Gleitpunktzahl(0);
		gleitref.setInfinite(true);

		gleiterg = x.add(y);
		// Test, ob Ergebnis korrekt
		if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
			printAdd(x.toString(), y.toString());
			printErg(gleiterg.toString(), gleitref.toString());
		} else {
			System.out.println("    Richtiges Ergebnis\n");
		}

		// Test: 0 - num = -num
        System.out.println("Test: 0 - 1 = -1");
		x = new Gleitpunktzahl(0);
		y = new Gleitpunktzahl(1);

		gleitref = new Gleitpunktzahl((0 - 1));
		gleiterg = x.sub(y);
		// Test, ob Ergebnis korrekt
		if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
			printSub(x.toString(), y.toString());
			printErg(gleiterg.toString(), gleitref.toString());
		} else {
			System.out.println("    Richtiges Ergebnis\n");
		}

		// Test: num + NaN = NaN
        System.out.println("Test: 1 + NaN = NaN");
		x = new Gleitpunktzahl(1);
		y = new Gleitpunktzahl(0);
		y.setNaN();

		gleitref = new Gleitpunktzahl(0);
		gleitref.setNaN();

		gleiterg = x.add(y);
		// Test, ob Ergebnis korrekt
		if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
			printAdd(x.toString(), y.toString());
			printErg(gleiterg.toString(), gleitref.toString());
		} else {
			System.out.println("    Richtiges Ergebnis\n");
		}

		// Test: num - NaN = NaN
        System.out.println("Test: 1 - NaN = NaN");
		x = new Gleitpunktzahl(1);
		y = new Gleitpunktzahl(0);
		y.setNaN();

		gleitref = new Gleitpunktzahl(0);
		gleitref.setNaN();

		gleiterg = x.sub(y);
		// Test, ob Ergebnis korrekt
		if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
			printSub(x.toString(), y.toString());
			printErg(gleiterg.toString(), gleitref.toString());
		} else {
			System.out.println("    Richtiges Ergebnis\n");
		}

		
		/*
		Test, Fall: ABRUNDEN und SETZEN der Groesse der Mantisse
		Groesse der Mantisse: 2

		testet das Resultat von 12 + 0.5:
		1.1*2^3 + 1.0*2^-1 =
		(1.1*2^4 + 1.0)*2^-1 =
		(11000 + 1.0)*2^-1 =
		(11001)*2^-1 =
		1,1*2^3
		 */
		x = new Gleitpunktzahl(12);
		y = new Gleitpunktzahl(0.5);

		x.setSizeMantisse(2);
		y.setSizeMantisse(2);

		gleiterg.setDouble(12);

		if(x.add(y).compareAbsTo(gleiterg)==0){
			System.out.println("    Richtiges Ergebnis\n");
		}else{
			System.out.println("    Fehler!\n      Es wurde gerechnet:            " + x + " + " + y);
			System.out.println("      Die Mantisse im Test ist auf Groesse 2 gesetzt.");
			System.out.println("      Das korrekte Ergebniss lautet: " + gleiterg + "\n");
		}

		/*
		Test, Fall: AUFRUNDEN und SETZEN der Groesse der Mantisse
		Groesse der Mantisse: 2
		testet das Resultat von 12 + 2:
		1.1*2^3 + 1.0*2^1 =
		(110 + 1)*2^1 =
		(111)*2^1 =
		(1.11)*2^3 =
		(1.11 + 0.1)*2^3 =    //+0.1 wegen der Rundung
		(1,0)*2^4
		 */
		x = new Gleitpunktzahl(12);
		y = new Gleitpunktzahl(2);

		x.setSizeMantisse(2);
		y.setSizeMantisse(2);

		gleiterg.setDouble(16);

		if(x.add(y).compareAbsTo(gleiterg)==0){
			System.out.println("    Richtiges Ergebnis\n");
		}else{
			System.out.println("    Fehler!\n      Es wurde gerechnet:            " + x + " + " + y);
			System.out.println("      Die Mantisse im Test ist auf Groesse 2 gesetzt.");
			System.out.println("      Das korrekte Ergebniss lautet: " + gleiterg + "\n");
		}


		//todo: weitere Tests mit Setzen der Groesse der Mantisse auf andere Zahlen
		//todo: Testfall wenn der Exponent zu klein ist
	}

	public static void test_Gleitpunktzahl() {

		/**********************************/
		/* Test der Klasse Gleitpunktzahl */
		/**********************************/

		System.out.println("-----------------------------------------");
		System.out.println("Test der Klasse Gleitpunktzahl");

		/*
		 * Verglichen werden die BitFelder fuer Mantisse und Exponent und das Vorzeichen
		 */
		Gleitpunktzahl.setSizeMantisse(4);
		Gleitpunktzahl.setSizeExponent(2);

		Gleitpunktzahl x;
		Gleitpunktzahl y;
		Gleitpunktzahl gleitref = new Gleitpunktzahl();
		Gleitpunktzahl gleiterg;

		/* Test von setDouble */
		System.out.println("Test von setDouble");
		try {
			// Test: setDouble
			x = new Gleitpunktzahl(0.5);

			// Referenzwerte setzen
			gleitref = new Gleitpunktzahl(0.5);

			// Test, ob Ergebnis korrekt
			if (x.compareAbsTo(gleitref) != 0 || x.vorzeichen != gleitref.vorzeichen) {
				printErg("" + x.toDouble(), "" + gleitref.toDouble());
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}

			/*************
			 * Eigene Tests einfuegen
			 */

		} catch (Exception e) {
			System.out.print("Exception bei der Auswertung des Ergebnis!!\n");
		}

		/* Addition */
		System.out.println("Test der Addition mit Gleitpunktzahl");
		try {
			// Test: Addition
			System.out.println("Test: Addition  x + y");
			x = new Gleitpunktzahl(3.25);
			y = new Gleitpunktzahl(0.5);

			// Referenzwerte setzen
			gleitref = new Gleitpunktzahl(3.25 + 0.5);

			// Berechnung
			gleiterg = x.add(y);

			// Test, ob Ergebnis korrekt
			if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
				printAdd(x.toString(), y.toString());
				printErg(gleiterg.toString(), gleitref.toString());
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}

		} catch (Exception e) {
			System.out.print("Exception bei der Auswertung des Ergebnis!!\n");
		}

		/* Subtraktion */
		try {
			System.out.println("Test der Subtraktion mit Gleitpunktzahl");

			// Test: Subtraktion
			System.out.println("Test: Subtraktion  x - y");
			x = new Gleitpunktzahl(3.25);
			y = new Gleitpunktzahl(2.75);

			// Referenzwerte setzen
			gleitref = new Gleitpunktzahl((3.25 - 2.75));

			// Berechnung
			gleiterg = x.sub(y);

			// Test, ob Ergebnis korrekt
			if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
				printSub(x.toString(), y.toString());
				printErg(gleiterg.toString(), gleitref.toString());
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}

		} catch (Exception e) {
			System.out.print("Exception bei der Auswertung des Ergebnis!!\n");
		}

		/* Sonderfaelle */
		System.out.println("Test der Sonderfaelle mit Gleitpunktzahl");

		try {
			// Test: Sonderfaelle
			// 0 - inf
			System.out.println("Test: Sonderfall 0 - Inf");
			x = new Gleitpunktzahl(0.0);
			y = new Gleitpunktzahl(1.0 / 0.0);

			// Referenzwerte setzen
			gleitref.setInfinite(true);

			// Berechnung mit der Methode des Studenten durchfuehren
			gleiterg = x.sub(y);

			// Test, ob Ergebnis korrekt
			if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
				printSub(x.toString(), y.toString());
				printErg(gleiterg.toString(), gleitref.toString());
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}

            // 0 + inf
			System.out.println("Test: Sonderfall 0 + Inf");
			x = new Gleitpunktzahl(0.0);
			y = new Gleitpunktzahl(1.0 / 0.0);

			// Referenzwerte setzen
			gleitref.setInfinite(false);

			// Berechnung mit der Methode des Studenten durchfuehren
			gleiterg = x.add(y);

			// Test, ob Ergebnis korrekt
			if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
				printAdd(x.toString(), y.toString());
				printErg(gleiterg.toString(), gleitref.toString());
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}

            // inf + inf
			System.out.println("Test: Sonderfall inf + inf");
			x = new Gleitpunktzahl(1.0 / 0.0);
			y = new Gleitpunktzahl(1.0 / 0.0);

			// Referenzwerte setzen
			gleitref.setInfinite(false);

			// Berechnung mit der Methode des Studenten durchfuehren
			gleiterg = x.add(y);

			// Test, ob Ergebnis korrekt
			if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
				printAdd(x.toString(), y.toString());
				printErg(gleiterg.toString(), gleitref.toString());
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}

            // -inf + (-inf)
			System.out.println("Test: Sonderfaelle");
			x = new Gleitpunktzahl(-1.0 / 0.0);
			y = new Gleitpunktzahl(-1.0 / 0.0);

			// Referenzwerte setzen
			gleitref.setInfinite(true);

			// Berechnung mit der Methode des Studenten durchfuehren
			gleiterg = x.add(y);

			// Test, ob Ergebnis korrekt
			if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
				printSub(x.toString(), y.toString());
				printErg(gleiterg.toString(), gleitref.toString());
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}

            // inf - inf
			System.out.println("Test: Sonderfall inf - inf");
			x = new Gleitpunktzahl(1337); 
            x.setInfinite(false);
			y = new Gleitpunktzahl(0); 
            y.setInfinite(false);

			// Referenzwerte setzen
			gleitref.setNaN();

			// Berechnung mit der Methode des Studenten durchfuehren
			gleiterg = y.sub(y);

			// Test, ob Ergebnis korrekt
			if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
				printSub(x.toString(), y.toString());
				printErg(gleiterg.toString(), gleitref.toString());
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}

            // -inf - (-inf)
			System.out.println("Test: Sonderfaelle");
			x = new Gleitpunktzahl(-1.0 / 0.0);
			y = new Gleitpunktzahl(-1.0 / 0.0);

			// Referenzwerte setzen
			gleitref.setNaN();

			// Berechnung mit der Methode des Studenten durchfuehren
			gleiterg = x.sub(y);

			// Test, ob Ergebnis korrekt
			if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
				printSub(x.toString(), y.toString());
				printErg(gleiterg.toString(), gleitref.toString());
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}

            // inf - 0
			System.out.println("Test: Sonderfaelle");
			x = new Gleitpunktzahl(1.0 / 0.0);
			y = new Gleitpunktzahl(0.0);

			// Referenzwerte setzen
			gleitref.setInfinite(false);

			// Berechnung mit der Methode des Studenten durchfuehren
			gleiterg = x.sub(y);

			// Test, ob Ergebnis korrekt
			if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
				printSub(x.toString(), y.toString());
				printErg(gleiterg.toString(), gleitref.toString());
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}

            // inf + 0
			System.out.println("Test: Sonderfaelle");
			x = new Gleitpunktzahl(1.0 / 0.0);
			y = new Gleitpunktzahl(0.0);

			// Referenzwerte setzen
			gleitref.setInfinite(false);

			// Berechnung mit der Methode des Studenten durchfuehren
			gleiterg = x.add(y);

			// Test, ob Ergebnis korrekt
			if (gleiterg.compareAbsTo(gleitref) != 0 || gleiterg.vorzeichen != gleitref.vorzeichen) {
				printAdd(x.toString(), y.toString());
				printErg(gleiterg.toString(), gleitref.toString());
			} else {
				System.out.println("    Richtiges Ergebnis\n");
			}
		} catch (Exception e) {
			System.out.print("Exception bei der Auswertung des Ergebnis in der Klasse Gleitpunktzahl!!\n");
		}

	}

	private static void printAdd(String x, String y) {
		System.out.println("    Fehler!\n      Es wurde gerechnet:            " + x + " + " + y);
	}

	private static void printSub(String x, String y) {
		System.out.println("    Fehler!\n      Es wurde gerechnet:            " + x + " - " + y + " = \n");
	}

	private static void printErg(String erg, String checkref) {
		System.out.println("      Ihr Ergebnis lautet:           " + erg + "\n      Das Korrekte Ergebnis lautet:  "
				+ checkref + "\n");
	}
}

// SONDERFAELLE
// 1 ist 1.0 * 2^0
// 2^-o ist [0.5- 1.0) * 2^-127,
// < 2^-o ist 0