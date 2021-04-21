package pdits_sbl02;

import java.security.MessageDigest;

public class EasyHash {

	private static final String SERIENNUMMER_HASH = "B349D25C31E475799488E842797FBC36A367A0EBAE7415867DEA0796ED6F2B08";

	public static void main(String[] args) {
		String emptyHex = "00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00";
		String finalString = "";

		boolean isDone = false;
		for (int i = 0; i < 16; i++) {
			for (int counter = 1; counter < 256; counter++) {
				// string aufbau:
				finalString = "";
				String hex = String.format("%02X", counter);
				
				if(hex.equalsIgnoreCase("1A")) {
					int sdf = 0;
				}
				
				String[] newString = emptyHex.split(":");
				newString[i] = hex;

				for (int j = 0; j < newString.length; j++) {
					finalString += newString[j];
					
					//last :
					if (j != newString.length - 1) {
						finalString += ":";
					}
				}

				MessageDigest digest;
				try {
					digest = MessageDigest.getInstance("SHA-256");
					
					// Hash
					byte[] tempHash = digest.digest(finalString.getBytes());

					String finalHex = "";
					for (int j = 0; j < tempHash.length; j++) {
						finalHex += String.format("%02X", tempHash[j]);
					}

					if (SERIENNUMMER_HASH.equalsIgnoreCase(finalHex)) {
						System.out.println("stop");
						isDone = true;
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//System.out.println("" + i + ", " + counter);
			}
			if (isDone) {
				break;
			}
		}
		System.out.println("Hash: " + SERIENNUMMER_HASH);
		System.out.println("Seriennummer: " + finalString);

//		byte[] test = new byte[16];
//		test[15] = (byte) 0xFF;
//		try {
//			MessageDigest digest = MessageDigest.getInstance("SHA-256");
//
//			byte[] tempHash = digest.digest(test);
//			String tempString = new String(tempHash, StandardCharsets.UTF_8);
//			System.out.println(tempString);
//
//			for (byte b : tempHash) {
//				System.out.print(String.format("%02X", b));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private static int toUnsigned(byte b) {
		int number = b & 0xFF;
		return number;
	}
}
