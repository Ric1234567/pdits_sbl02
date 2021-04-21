package pdits_sbl02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class BaziCrypt {

	public byte[] decrypt(byte[] plainTextByte, byte[] key) {
		// result array
		byte result[] = new byte[plainTextByte.length];

		// iterate plaintext bytes to decrypt with xor (^)
		for (int i = 0; i < plainTextByte.length; i++) {
			result[i] = (byte) (plainTextByte[i] ^ key[i % key.length]);
		}

		return result;
	}

	public void crackAdvazi(String path) {
		byte[] cipherTextBytes = null;

		try {
			// read cipher file as byte array
			cipherTextBytes = Files.readAllBytes(Path.of(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] bKey = Arrays.copyOfRange(cipherTextBytes, cipherTextBytes.length - 10, cipherTextBytes.length);

		byte xor = 0;
		// find padding length
		for (int i = cipherTextBytes.length - 1; i > 0; i--) {
			if (cipherTextBytes[i] != bKey[i % bKey.length]) {
				xor = (byte) (100 - i - 1);
				break;
			}
		}

		// xor with PKCS7
		for (int i = 0; i < bKey.length; i++) {
			bKey[i] = (byte) (bKey[i] ^ xor);
		}

		// decrypt cipher text with key
		BaziCrypt decrypter = new BaziCrypt();
		byte[] result = decrypter.decrypt(cipherTextBytes, bKey);

		// print plain text
		System.out.println();
		System.out.print(new String(result).substring(0, 100 - xor));
	}
	
	public void crackBazi(String path) {
		byte[] cipherTextBytes = null;

		try {
			// read cipher file as byte array
			cipherTextBytes = Files.readAllBytes(Path.of(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] bKey = Arrays.copyOfRange(cipherTextBytes, cipherTextBytes.length - 10, cipherTextBytes.length);

		byte xor = 0;
		// find padding length
		for (int i = cipherTextBytes.length - 1; i > 0; i--) {
			if (cipherTextBytes[i] != bKey[i % bKey.length]) {
				xor = (byte) (100 - i - 1);
				break;
			}
		}

		// decrypt cipher text with key
		BaziCrypt decrypter = new BaziCrypt();
		byte[] result = decrypter.decrypt(cipherTextBytes, bKey);

		// print plain text
		System.out.println();
		System.out.print(new String(result).substring(0, 100 - xor));
	}
}
