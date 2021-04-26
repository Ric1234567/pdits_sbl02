package pdits_sbl02;

import java.security.MessageDigest;

/**
 * Aufgabe 5 "EasyHash" der "Praxis der IT-Sicherheit" - SBL02.
 * Brute-Force Angriff.
 */
public class EasyHash {

	private static final String SERIENNUMMER_HASH = "B349D25C31E475799488E842797FBC36A367A0EBAE7415867DEA0796ED6F2B08";
	private static final String EMPTY_HEX_SERIENNUMMER = "00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00";


	/**
	 * Create Seriennummer with given byte position and hex value
	 */
	private static String createSeriennummerString(int pos, String hexValue){
		char[] charArrayHexSeriennummer = EMPTY_HEX_SERIENNUMMER.toCharArray();

		charArrayHexSeriennummer[3 * pos] = hexValue.toCharArray()[0];
		charArrayHexSeriennummer[3 * pos + 1] = hexValue.toCharArray()[1];

		return new String(charArrayHexSeriennummer);
	}

	/**
	 * Create Hash value to a given Seriennummer and format to String
	 */
	private static String createHashString(String HexSeriennummer){
		MessageDigest hasher;
		String hashString = "";

		try {
			hasher = MessageDigest.getInstance("SHA-256");
			byte[] byteHash = hasher.digest(HexSeriennummer.getBytes());

			// convert byte array to string
			for (int j = 0; j < byteHash.length; j++) {
				hashString += String.format("%02X", byteHash[j]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashString;
	}


	public static void main(String[] args) {

		System.out.println("Start EasyHash! ");
		String tempSeriennummer = "";
		String tempHash = "";
		boolean foundSeriennummer = false;

		// iterate through bytes
		for (int i = 0; i < 16; i++) {

			// iterate through Hex values (01, 02, ... FF)
			for (int counter = 1; counter < 256; counter++) {

				// Create Seriennummer with current byte position and hex value
				tempSeriennummer = createSeriennummerString(i, String.format("%02X", counter));

				// create hash
				tempHash = createHashString(tempSeriennummer);

				// check, if current hash matches the given hash
				if (SERIENNUMMER_HASH.equalsIgnoreCase(tempHash)) {
					foundSeriennummer = true;
					System.out.println("We found the matching Seriennummer!");
					System.out.println("Hash: " + SERIENNUMMER_HASH);
					System.out.println("Seriennummer: " + tempSeriennummer);
					break;
				}
			}
			// end loop
			if (foundSeriennummer) {
				break;
			}
		}
	}
}
