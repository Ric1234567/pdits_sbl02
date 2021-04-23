package pdits_sbl02;

import java.security.MessageDigest;

/**
 * Aufgabe 5 "EasyHash" der "Praxis der IT-Sicherheit"-SBL02. Brute-Force
 * Angriff.
 */
public class EasyHash {

	private static final String SERIENNUMMER_HASH = "B349D25C31E475799488E842797FBC36A367A0EBAE7415867DEA0796ED6F2B08";
	private static final String EMPTY_HEX_SERIENNUMMER = "00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00";

	public static void main(String[] args) {
		System.out.println("Start EasyHash!");

		String tempHexSeriennummer = "";

		boolean foundSeriennummer = false;

		// iterate bytes
		for (int i = 0; i < 16; i++) {

			// iterate 01, 02, ... FF
			for (int counter = 1; counter < 256; counter++) {
				// reset
				tempHexSeriennummer = "";

				// string building
				String hexIterator = String.format("%02X", counter);

				// set in hexIterator at current byte
				String[] newString = EMPTY_HEX_SERIENNUMMER.split(":");// TODO geht iwie effizienter
				newString[i] = hexIterator;

				// create seriennummer to test hash
				for (int j = 0; j < newString.length; j++) {
					tempHexSeriennummer += newString[j];

					// no ":" at last index
					if (j != newString.length - 1) {
						tempHexSeriennummer += ":";
					}
				}

				MessageDigest hasher;
				try {
					hasher = MessageDigest.getInstance("SHA-256");

					// hash seriennummer
					byte[] tempHashSeriennummer = hasher.digest(tempHexSeriennummer.getBytes());

					// create hex representation of hash
					String tempHexHash = "";
					for (int j = 0; j < tempHashSeriennummer.length; j++) {
						tempHexHash += String.format("%02X", tempHashSeriennummer[j]);
					}

					// end condition; found the seriennummer
					if (SERIENNUMMER_HASH.equalsIgnoreCase(tempHexHash)) {
						// tempHexSeriennummer is correct
						foundSeriennummer = true;
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// end condition
			if (foundSeriennummer) {
				break;
			}
		}
		System.out.println("Hash: " + SERIENNUMMER_HASH);
		System.out.println("Seriennummer: " + tempHexSeriennummer);
	}
}
