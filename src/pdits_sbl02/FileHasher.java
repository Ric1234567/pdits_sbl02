package pdits_sbl02;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Scanner;

/**
 * Aufgabe 4 "FileHasher" der "Praxis der IT-Sicherheit"-SBL02.
 */

public class FileHasher {

	// path of file to hash
	private static final String FILE_PATH = "./res/n01.txt.enc";

	/**
	 * Get the algorithm the user wants to use (SHA-256, SHA-512, MD5)
	 */
	private String readInAlgorithm(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Algorithm (SHA-256, SHA-512, MD5): ");
		String algorithm = scanner.nextLine();
		scanner.close();
		return algorithm;
	}

	/**
	 * convert byte array hash to hexadecimal string
	 */
	private String toHexString(byte[] hash){
		String hashHexString = "";

		for (int i = 0; i < hash.length; i++) {
			hashHexString += String.format("%02X", hash[i]);
		}
		return hashHexString;
	}

	/**
	 * write hash value to file
	 */
	private void writeHashToFile(String hash, String algorithm){
		String fileName = algorithm + "-Hash.log";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(hash);
			bw.close();
			// print filename
			System.out.println(algorithm + "-Hash value written in file \"./" + fileName + "\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FileHasher fileHasher = new FileHasher();
		try {
			String hashHexString = "";
			String algorithm = fileHasher.readInAlgorithm();

			// create instance of hasher of the algorithm
			MessageDigest hasher = MessageDigest.getInstance(algorithm);

			// read file as byte array and generate the hash value of the file
			byte[] hash = hasher.digest(Files.readAllBytes(Path.of(FILE_PATH)));
			hashHexString = fileHasher.toHexString(hash);

			System.out.println("Hash of file \"" + FILE_PATH + "\" with " + algorithm + ": ");
			System.out.println(hashHexString);

			// write hash value to file
			fileHasher.writeHashToFile(hashHexString, algorithm);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
