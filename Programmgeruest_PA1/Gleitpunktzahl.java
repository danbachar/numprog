public class Gleitpunktzahl {

	/**
	 * Update by
	 * 
	 * @author Juergen Braeckle (braeckle@in.tum.de)
	 * @author Sebastian Rettenberger (rettenbs@in.tum.de)
	 * @since Oktober 22, 2014
	 * @version 1.2
	 * 
	 *          Diese Klasse beschreibt eine Form von Gleitpunktarithmetik
	 */

	/********************/
	/* Membervariablen: */
	/********************/

	/* Vorzeichen, Mantisse und Exponent der Gleitpunktzahl */
	public boolean vorzeichen; /* true = "-1" */
	public int exponent;
	public int mantisse;

	/*
	 * Anzahl der Bits fuer die Mantisse: einmal gesetzt, soll sie nicht mehr
	 * veraendert werden koennen
	 */
	private static int sizeMantisse = 32;
	private static boolean sizeMantisseFixed = false;

	/*
	 * Anzahl der Bits fuer dem Exponent: einmal gesetzt, soll sie nicht mehr
	 * veraendert werden koennen. Maximale Groesse: 32
	 */
	private static int sizeExponent = 8;
	private static boolean sizeExponentFixed = false;

	/*
	 * Aus der Anzahl an Bits fuer den Exponenten laesst sich der maximale Exponent
	 * und der Offset berechnen
	 */
	private static int maxExponent = (int) Math.pow(2, sizeExponent) - 1;
	// Der Offset von dem Exponent. Z.B. bei 8 bit lang Exponent ist der expOffset
	// 127,
	private static int expOffset = (int) Math.pow(2, sizeExponent - 1) - 1;

	/**
	 * Falls die Anzahl der Bits der Mantisse noch nicht gesperrt ist, so wird sie
	 * auf abm gesetzt und gesperrt
	 */
	public static void setSizeMantisse(int abm) {
		/*
		 * Falls sizeMantisse noch nicht gesetzt und abm > 0 dann setze auf abm und
		 * sperre den Zugriff
		 */
		if (!sizeMantisseFixed & (abm > 0)) {
			sizeMantisse = abm;
			sizeMantisseFixed = true;
		}
	}

	/**
	 * Falls die Anzahl der Bits des Exponenten noch nicht gesperrt ist, so wird sie
	 * auf abe gesetzt und gesperrt. maxExponent und expOffset werden festgelegt
	 */
	public static void setSizeExponent(int abe) {
		if (!sizeExponentFixed & (abe > 0)) {
			sizeExponent = abe;
			sizeExponentFixed = true;
			maxExponent = (int) Math.pow(2, abe) - 1;
			expOffset = (int) Math.pow(2, abe - 1) - 1;
		}
	}

	/** Liefert die Anzahl der Bits der Mantisse */
	public static int getSizeMantisse() {
		return sizeMantisse;
	}

	/** Liefert die Anzahl der Bits des Exponenten */
	public static int getSizeExponent() {
		return sizeExponent;
	}

	/**
	 * erzeugt eine Gleitpunktzahl ohne Anfangswert. Die Bitfelder fuer Mantisse und
	 * Exponent werden angelegt. Ist die Anzahl der Bits noch nicht gesetzt, wird
	 * der Standardwert gesperrt
	 */
	Gleitpunktzahl() {
		sizeMantisseFixed = true;
		sizeExponentFixed = true;
	}

	/** erzeugt eine Kopie der reellen Zahl r */
	Gleitpunktzahl(Gleitpunktzahl r) {

		/* Vorzeichen kopieren */
		this.vorzeichen = r.vorzeichen;
		/*
		 * Kopiert den Inhalt der jeweiligen Felder aus r
		 */
		this.exponent = r.exponent;
		this.mantisse = r.mantisse;
	}

	/**
	 * erzeugt eine reelle Zahl mit der Repraesentation des Double-Wertes d. Ist die
	 * Anzahl der Bits fuer Mantisse und Exponent noch nicht gesetzt, wird der
	 * Standardwert gesperrt
	 */
	Gleitpunktzahl(double d) {

		this();
		this.setDouble(d);
	}

	/**
	 * setzt dieses Objekt mit der Repraesentation des Double-Wertes d.
	 */
	public void setDouble(double d) {

		/* Abfangen der Sonderfaelle */
		if (d == 0) {
			this.setNull();
			return;
		}
		if (Double.isInfinite(d)) {
			this.setInfinite(d < 0);
			return;
		}
		if (Double.isNaN(d)) {
			this.setNaN();
			return;
		}

		/* Falls d<0 -> Vorzeichen setzten, Vorzeichen von d wechseln */
		if (d < 0) {
			this.vorzeichen = true;
			d = -d;
		} else
			this.vorzeichen = false;

		/*
		 * Exponent exp von d zur Basis 2 finden d ist danach im Intervall [1,2)
		 */
		int exp = 0;
		while (d >= 2) {
			d = d / 2;
			exp++;
		}
		while (d < 1) {
			d = 2 * d;
			exp--;
		} /* d in [1,2) */

		this.exponent = exp + expOffset;

		/*
		 * Mantisse finden; fuer Runden eine Stelle mehr als noetig berechnen
		 */
		double rest = d;
		this.mantisse = 0;
		for (int i = 0; i <= sizeMantisse; i++) { //
			this.mantisse <<= 1; // 2
			if (rest >= 1) {// 1
				rest = rest - 1;// 0
				this.mantisse |= 1; // this.mantisse = this.mantisse | 1 3 = 0b 11
			}
			rest = 2 * rest;
		}
		this.exponent -= 1; /* Mantisse ist um eine Stelle groesser! */

		/*
		 * normalisiere uebernimmt die Aufgaben des Rundens
		 */
		this.normalisiere();
	}

	/** liefert eine String-Repraesentation des Objekts */
	public String toString() {
		if (this.isNaN())
			return "NaN";
		if (this.isNull())
			return "0";

		StringBuffer s = new StringBuffer();
		if (this.vorzeichen)
			s.append('-');
		if (this.isInfinite())
			s.append("Inf");
		else {
			for (int i = 32 - Integer.numberOfLeadingZeros(this.mantisse) - 1; i >= 0; i--) {
				if (i == sizeMantisse - 2)
					s.append(',');
				if (((this.mantisse >> i) & 1) == 1)
					s.append('1');
				else
					s.append('0');
			}
			s.append(" * 2^(");
			s.append(this.exponent);
			s.append("-");
			s.append(expOffset);
			s.append(")");
		}
		return s.toString();
	}

	/** berechnet den Double-Wert des Objekts */
	public double toDouble() {
		/*
		 * Wenn der Exponent maximal ist, nimmt die Gleitpunktzahl einen der speziellen
		 * Werte an
		 */
		if (this.exponent == maxExponent) {
			/*
			 * Wenn die Mantisse Null ist, hat die Zahl den Wert Unendlich oder -Unendlich
			 */
			if (this.mantisse == 0) {
				if (this.vorzeichen)
					return -1.0 / 0.0;
				else
					return 1.0 / 0.0;
			}
			/* Ansonsten ist der Wert NaN */
			else
				return 0.0 / 0.0;
		}
		double m = this.mantisse;
		if (this.vorzeichen)
			m *= (-1);
		return m * Math.pow(2, (this.exponent - expOffset) - (sizeMantisse - 1));
	}

	/**
	 * Sonderfaelle abfragen
	 */
	/** Liefert true, wenn die Gleitpunktzahl die Null repraesentiert */
	public boolean isNull() {
		return (!this.vorzeichen && this.mantisse == 0 && this.exponent == 0);
	}

	/**
	 * Liefert true, wenn die Gleitpunktzahl der NotaNumber Darstellung entspricht
	 */
	public boolean isNaN() {
		return (this.mantisse != 0 && this.exponent == maxExponent);
	}

	/** Liefert true, wenn die Gleitpunktzahl betragsmaessig unendlich gross ist */
	public boolean isInfinite() {
		return (this.mantisse == 0 && this.exponent == maxExponent);
	}

	/**
	 * vergleicht betragsmaessig den Wert des aktuellen Objekts mit der reellen Zahl
	 * r
	 */
	public int compareAbsTo(Gleitpunktzahl r) {
		/*
		 * liefert groesser gleich 1, falls |this| > |r| 0, falls |this| = |r| kleiner
		 * gleich -1, falls |this| < |r|
		 */

		/* Exponenten vergleichen */
		int expVergleich = this.exponent - r.exponent;

		if (expVergleich != 0)
			return expVergleich;

		/* Bei gleichen Exponenten: Bitweisses Vergleichen der Mantissen */
		return this.mantisse - r.mantisse;
	}

	/**
	 * normalisiert und rundet das aktuelle Objekt auf die Darstellung r =
	 * (-1)^vorzeichen * 1,r_t-1 r_t-2 ... r_1 r_0 * 2^exponent. Die 0 wird zu
	 * (-1)^0 * 0,00...00 * 2^0 normalisiert WICHTIG: Es kann sein, dass die Anzahl
	 * der Bits nicht mit sizeMantisse uebereinstimmt. Das Ergebnis soll aber eine
	 * Mantisse mit sizeMantisse Bits haben. Deshalb muss evtl. mit Bits aufgefuellt
	 * oder Bits abgeschnitten werden. Dabei muss das Ergebnis nach Definition
	 * gerundet werden.
	 * 
	 * Beispiel: Bei 3 Mantissenbits wird die Zahl 10.11 * 2^-1 zu 1.10 * 2^0
	 */
	public void normalisiere() {
		/*
		 * TODO: hier ist die Operation normalisiere zu implementieren. Beachten Sie,
		 * dass die Groesse (Anzahl der Bits) des Exponenten und der Mantisse durch
		 * sizeExponent bzw. sizeMantisse festgelegt ist. Achten Sie auf Sonderfaelle!
		 */
		if (!sizeMantisseFixed || !sizeExponentFixed) {
			throw new IllegalStateException();
		}

		// TODO: make sure mantisse has 1 in front of it

		// zu größe Zahl
		while (this.mantisse > Math.pow(2, sizeMantisse) - 1) {
			this.exponent++;
			if (this.mantisse % 2 == 1) {
				this.mantisse += 2;

			}
			this.mantisse >>= 1;
		}

		while (this.mantisse < Math.pow(2, sizeMantisse - 1)) {
			if (exponent > 0) {
				this.mantisse <<= 1;
				exponent--;
			} else { // exponent = 0, eig exponent = 0-o = -o
				if (this.mantisse < Math.pow(2, sizeMantisse - 2)) { // 1 Stelle in der Mantisse - Vorkommazahl, zweite
																		// - erste Nachkommazahl - 0.5
					this.setNull();
				} else {
					this.mantisse = 1; // 1 * 2^(-o)
				}
				return;
			}
		}
	}

	/**
	 * denormalisiert die betragsmaessig goessere Zahl, so dass die Exponenten von a
	 * und b gleich sind. Die Mantissen beider Zahlen werden entsprechend erweitert.
	 * Denormalisieren wird fuer add und sub benoetigt.
	 */
	public static void denormalisiere(Gleitpunktzahl a, Gleitpunktzahl b) {
		/*
		 * TODO: hier ist die Operation denormalisiere zu implementieren.
		 */
		if (a.exponent > b.exponent) {
			a.mantisse = a.mantisse * (int) Math.pow(2, a.exponent - b.exponent);
			a.exponent = b.exponent;
		} else {
			b.mantisse = b.mantisse * (int) Math.pow(2, b.exponent - a.exponent);
			b.exponent = a.exponent;
		}
	}

	/**
	 * addiert das aktuelle Objekt und die Gleitpunktzahl r. Dabei wird zuerst die
	 * betragsmaessig groessere Zahl denormalisiert und die Mantissen beider zahlen
	 * entsprechend vergroessert. Das Ergebnis wird in einem neuen Objekt
	 * gespeichert, normiert, und dieses wird zurueckgegeben.
	 */
	public Gleitpunktzahl add(Gleitpunktzahl r) {

		// 1. Find out larger number
		// 2. Denormalise it (bring to same exponent)
		// 3. Add them together
		// 4. Normalise again

		// Wenn Vorzeichen nicht gleich führe substraktion mit entsprechenden Vorzeichen
		// aus
		Gleitpunktzahl result = detectExceptionalSt(r, false);
		if (result != null) {
			return result;
		}
		if (this.vorzeichen != r.vorzeichen) {
			// TODO: think about Vorzeichen, when to flip what
			boolean vorzeichen;
			// this > r
			if (this.exponent > r.exponent || (this.exponent == r.exponent && this.mantisse > r.mantisse)) {
				vorzeichen = this.vorzeichen;
				this.vorzeichen = false;
				r.vorzeichen = false;
				result = this.sub(r);
				result.vorzeichen = vorzeichen;

			} else {
				// r >= this
				vorzeichen = r.vorzeichen;
				this.vorzeichen = false;
				r.vorzeichen = false;
				result = r.sub(this);
				result.vorzeichen = vorzeichen;
			}
			return result;
		}
		// todo, Sonderfaelle
		result = new Gleitpunktzahl();
		denormalisiere(this, r);

		result.mantisse = this.mantisse + r.mantisse;

		// den Vorzeichen nicht vergessen, vorzeichen true bedeutet negative zahl
		result.vorzeichen = this.vorzeichen;
		result.normalisiere();
		return result;
	}

    // +Inf+(+Inf) = Inf
    // +Inf+(-Inf) = NaN
    // +Inf-(+Inf) = NaN
    // +Inf-(-Inf) = Inf
    // -Inf+(+Inf) = NaN
    // -Inf+(-Inf) = -Inf
    // -Inf-(+Inf) = -Inf
    // -Inf-(-Inf) = NaN
    // Inf+0 = Inf
    // Inf-0 = Inf
    // 0-Inf = -Inf
    // 0+Inf = +Inf
	private Gleitpunktzahl detectExceptionalSt(Gleitpunktzahl r, boolean isSubstraction) {
        // code could have been shortened, leaving it like this for readability
		if (r.isInfinite()) {
            // r is infinity
            if (this.isInfinite()) {
                if (this.vorzeichen != r.vorzeichen) {
                    if (isSubstraction) {
                        // -Inf-(+Inf) = -Inf
                        // +Inf-(-Inf) = Inf
                    } else {
                        // underschiedliche Vorzeichen, addition, both infinity
                        // special case -> NaN
                        // +Inf+(-Inf) = NaN
                        // -Inf+(+Inf) = NaN
                        this.setNaN();
                    }
                    return this;
                } else {
                    // same sign -> either + or -, but check 
                    if (this.vorzeichen) {
                        // this and r are both negative, if substraction -> NaN
                        if (isSubstraction) {
                            // -Inf-(-Inf) = NaN
                            this.setNaN();
                        } else {
                            // -Inf+(-Inf) = -Inf
                        }
                        return this;
                    } else {
                        // this and r are both positive, if substraction -> NaN
                        if (isSubstraction) {
                            // +Inf-(+Inf) = NaN
                            this.setNaN();
                        } else {
                            // +Inf+(+Inf) = Inf
                        }
                    }
                }
            } else if (this.isNull()) {
                if (isSubstraction) {
                    r.vorzeichen = !r.vorzeichen;
                }
            }
            return r;
		} else if (r.isNull()) {
            return this;
        } else if (r.isNaN()) {
            return r;
        } else if (this.isNaN()) {
            return this;
        }

		return null;
	}

	/**
	 * subtrahiert vom aktuellen Objekt die Gleitpunktzahl r. Dabei wird zuerst die
	 * betragsmaessig groessere Zahl denormalisiert und die Mantissen beider zahlen
	 * entsprechend vergroessert. Das Ergebnis wird in einem neuen Objekt
	 * gespeichert, normiert, und dieses wird zurueckgegeben.
	 */
	public Gleitpunktzahl sub(Gleitpunktzahl r) {
		/*
		 * TODO: hier ist die Operation sub zu implementieren. Verwenden Sie die
		 * Funktionen normalisiere und denormalisiere. Achten Sie auf Sonderfaelle!
		 */

		Gleitpunktzahl result = detectExceptionalSt(r, true);
		if (result != null) {
			return result;
		}
		if (this.vorzeichen != r.vorzeichen) {
			// TODO: think about Vorzeichen, when to flip what
			boolean vorzeichen;
			// this > r
			if (this.exponent > r.exponent || (this.exponent == r.exponent && this.mantisse > r.mantisse)) {
				// 5 - (-3) = 5 + 3 = 8 -> korrekt
				// -5 - (+3) = - (5 + 3) = -8 -> korrekt
				vorzeichen = this.vorzeichen;
			} else {
				// r >= this
				vorzeichen = r.vorzeichen;
			}
			this.vorzeichen = false;
			r.vorzeichen = false;
			result = r.add(this);
			result.vorzeichen = vorzeichen;
			return result;
		}

		result = new Gleitpunktzahl();
		denormalisiere(this, r);
		// r = m2, this = m1
		if(this.mantisse > r.mantisse) {
			result.mantisse = this.mantisse - r.mantisse;
			result.vorzeichen = this.vorzeichen;
		} else{
			result.mantisse = r.mantisse - this.mantisse;
			result.vorzeichen = !r.vorzeichen;
		}
        result.exponent = this.exponent;
		result.normalisiere();
		return result;
	}

	/**
	 * Setzt die Zahl auf den Sonderfall 0
	 */
	public void setNull() {
		this.vorzeichen = false;
		this.exponent = 0;
		this.mantisse = 0;
	}

	/**
	 * Setzt die Zahl auf den Sonderfall +/- unendlich
	 */
	public void setInfinite(boolean vorzeichen) {
		this.vorzeichen = vorzeichen;
		this.exponent = maxExponent;
		this.mantisse = 0;
	}

	/**
	 * Setzt die Zahl auf den Sonderfall NaN
	 */
	public void setNaN() {
		this.vorzeichen = false;
		this.exponent = maxExponent;
		this.mantisse = 1;
	}
}
