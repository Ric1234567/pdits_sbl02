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
		System.out.println("Start to decrypt File: \"" + cipherFilePath + "\":");

		byte[] bCipherText = this.readCipherMessageFromFile(cipherFilePath);

		// the last 10 bytes of the ciphertext are the key
		byte[] bKey = Arrays.copyOfRange(bCipherText, bCipherText.length - 10, bCipherText.length);

		// find padding length
		int paddingLength = findPaddingLength(bCipherText, bKey);

		// decrypt ciphertext with key
		byte[] bPlainText = this.decrypt(bCipherText, bKey);

		// print results
		System.out.println("Key (char): " + new String(bKey));
		System.out.println("Padding length: " + paddingLength);
		System.out.println("Decrypted message: \n" + new String(bPlainText).substring(0, bCipherText.length - paddingLength));
		System.out.println();
		System.out.println();
	}

	public static void main(String[] args) {
		// BaziCrypt
		System.out.println("BaziCrypt started!");


		BaziCrypt baziCrypt = new BaziCrypt();

		// decrypt file n01.txt.enc
		baziCrypt.crackBaziCrypt(CIPHER_MESSAGE_PATH_1);

		// decrypt file n02.txt.enc
		baziCrypt.crackBaziCrypt(CIPHER_MESSAGE_PATH_2);

		// decrypt file n03.txt.enc
		baziCrypt.crackBaziCrypt(CIPHER_MESSAGE_PATH_3);
	}
}
