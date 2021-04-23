package pdits_sbl02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Aufgabe 2 "Denksport - AdvaziCrypt-Verschlüsselung" der "Praxis der
 * IT-Sicherheit"-SBL02.
 */
public class AdvaziCrypt {

	private static final String CIPHER_MESSAGE_PATH_4 = "./res/n04.txt.enc";
	private static final String CIPHER_MESSAGE_PATH_5 = "./res/n05.txt.enc";
	private static final String CIPHER_MESSAGE_PATH_6 = "./res/n06.txt.enc";

	public static void main(String[] args) {
		// AdvaziCrypt
		System.out.println("Started to crack AdvaziCrypt!");
		System.out.println();

		AdvaziCrypt advaziCrypt = new AdvaziCrypt();

		System.out.println("Crack file at \"" + CIPHER_MESSAGE_PATH_4 + "\":");
		advaziCrypt.crackAdvazi(CIPHER_MESSAGE_PATH_4);
		System.out.println();
		System.out.println();

		System.out.println("Crack file at \"" + CIPHER_MESSAGE_PATH_5 + "\":");
		advaziCrypt.crackAdvazi(CIPHER_MESSAGE_PATH_5);
		System.out.println();
		System.out.println();

		System.out.println("Crack file at \"" + CIPHER_MESSAGE_PATH_6 + "\":");
		advaziCrypt.crackAdvazi(CIPHER_MESSAGE_PATH_6);
		System.out.println();
		System.out.println();
	}

	/**
	 * This method xors the key with the paddingLength. This is the style of PKCS7
	 * padding-method.
	 */
	private void xorKeyPKCS7(byte[] bKey, int paddingLength) {
		for (int i = 0; i < bKey.length; i++) {
			bKey[i] = (byte) (bKey[i] ^ paddingLength);
		}
	}

	/**
	 * Cracks the AdvaziCrypt encryption.
	 */
	private void crackAdvazi(String cipherMessageFilePath) {
		// use same methods like BaziCrypt
		BaziCrypt baziCryptDecrypter = new BaziCrypt();

		byte[] bCipherText = baziCryptDecrypter.readCipherMessageFromFile(cipherMessageFilePath);

		// the last 10 bytes of the ciphertext are the key
		byte[] bKey = Arrays.copyOfRange(bCipherText, bCipherText.length - 10, bCipherText.length);

		// find padding length
		int paddingLength = baziCryptDecrypter.findPaddingLength(bCipherText, bKey);

		// xor the key in the style of PKCS7
		this.xorKeyPKCS7(bKey, paddingLength);

		// decrypt cipher text with key
		byte[] bPlainText = baziCryptDecrypter.decrypt(bCipherText, bKey);

		// print plain text
		System.out.print(new String(bPlainText).substring(0, bCipherText.length - paddingLength));
	}
}
