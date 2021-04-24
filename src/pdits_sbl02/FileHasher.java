package pdits_sbl02;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.System.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Scanner;
import java.util.logging.FileHandler;

/**
 * Aufgabe 4 "FileHasher" der "Praxis der IT-Sicherheit"-SBL02.
 */
public class FileHasher {
	// path of file to hash
	private static final String FILE_PATH = "./res/n01.txt.enc";

	public static void main(String[] args) {
		// read in the algorithm the user wants to use
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Algorithm (SHA-256, SHA-512, MD5): ");
		String algorithm = scanner.nextLine();
		scanner.close();

		try {
			// read cipher file as byte array
			byte[] file = Files.readAllBytes(Path.of(FILE_PATH));

			// create instance of hasher of the algorithm
			MessageDigest hasher = MessageDigest.getInstance(algorithm);

			// create the hash value of the file
			byte[] hash = hasher.digest(file);

			String hashHexString = "";
			System.out.print("Hash of file \"" + FILE_PATH + "\" with " + algorithm + ": ");
			System.out.println();

			// convert hash to hex string
			for (int i = 0; i < hash.length; i++) {
				hashHexString += String.format("%02X", hash[i]);
			}

			// print the hash
			System.out.print(hashHexString);
			System.out.println();

			String fileName = algorithm + "-Hash.log";

			// write hash to file
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(hashHexString);
			bw.close();

			// print filename
			System.out.println(algorithm + "-Hash value written in file \"" + fileName + "\"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
