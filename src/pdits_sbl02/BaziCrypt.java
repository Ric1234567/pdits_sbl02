package pdits_sbl02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Aufgabe 1 "BaziCrypt-Verschlüsselung" der "Praxis der IT-Sicherheit"-SBL02.
 * Enthält auch Teile der Aufgabe 2/3 "Denksport - AdvaziCrypt-Verschlüsselung"
 */
public class BaziCrypt {

	// paths to the encrypted message-files
	private static final String CIPHER_MESSAGE_PATH_1 = "./res/n01.txt.enc";
	private static final String CIPHER_MESSAGE_PATH_2 = "./res/n02.txt.enc";
	private static final String CIPHER_MESSAGE_PATH_3 = "./res/n03.txt.enc";

	public static void main(String[] args) {
		// BaziCrypt
		System.out.println("Started to crack BaziCrypt!");
		System.out.println();

		BaziCrypt baziCrypt = new BaziCrypt();

		System.out.println("File: \"" + CIPHER_MESSAGE_PATH_1 + "\":");
		baziCrypt.crackBaziCrypt(CIPHER_MESSAGE_PATH_1);
		System.out.println();
		System.out.println();

		System.out.println("File: \"" + CIPHER_MESSAGE_PATH_2 + "\":");
		baziCrypt.crackBaziCrypt(CIPHER_MESSAGE_PATH_2);
		System.out.println();
		System.out.println();

		System.out.println("File: \"" + CIPHER_MESSAGE_PATH_3 + "\":");
		baziCrypt.crackBaziCrypt(CIPHER_MESSAGE_PATH_3);
		System.out.println();
		System.out.println();
	}

	/**
	 * Reads a given file from a path as an byte array.
	 */
	protected byte[] readCipherMessageFromFile(String cipherFilePath) {
		byte[] cipherTextBytes = null;

		try {
			// read cipher file as byte array
			cipherTextBytes = Files.readAllBytes(Path.of(cipherFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cipherTextBytes;
	}

	/**
	 * Decrypts a given cipher text with a given key.
	 */
	protected byte[] decrypt(byte[] cipherText, byte[] key) {
		// result array
		byte[] plainText = new byte[cipherText.length];

		// iterate cipher text bytes to decrypt with xor (^)
		for (int i = 0; i < cipherText.length; i++) {
			plainText[i] = (byte) (cipherText[i] ^ key[i % key.length]);
		}

		return plainText;
	}

	/**
	 * Finds the length of the padding.
	 */
	protected int findPaddingLength(byte[] bCipherText, byte[] bKey) {
		for (int i = bCipherText.length - 1; i > 0; i--) {

			// if the cipherText and the key bytes are different the padding is over and the
			// plaintext starts (in reverse)
			if (bCipherText[i] != bKey[i % bKey.length]) {
				return (byte) (bCipherText.length - i - 1);
			}
		}

		// no padding found
		return 0;
	}

	/**
	 * Cracks the BaziCrypt encryption.
	 */
	private void crackBaziCrypt(String cipherFilePath) {
		byte[] bCipherText = this.readCipherMessageFromFile(cipherFilePath);

		// the last 10 bytes of the ciphertext are the key
		byte[] bKey = Arrays.copyOfRange(bCipherText, bCipherText.length - 10, bCipherText.length);
		System.out.println("Key: " + new String(bKey));
		
		// find padding length
		int paddingLength = findPaddingLength(bCipherText, bKey);

		// decrypt ciphertext with key
		byte[] bPlainText = this.decrypt(bCipherText, bKey);

		// print plain text with removed padding
		System.out.print(new String(bPlainText).substring(0, bCipherText.length - paddingLength));
	}
}
