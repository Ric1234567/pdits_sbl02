package pdits_sbl02;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.System.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Scanner;
import java.util.logging.FileHandler;

public class FileHasher {
	private static final String PATH1 = "./res/n01.txt.enc";

	private static final String PATH2 = "C:\\Users\\hhage\\Documents\\Uni\\Master\\SS2021\\Praxis der IT-Sec\\SBLs\\SBL2\\Übung 02 - Kryptographie 1.pdf";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); // Create a Scanner object
		System.out.println("Enter Algorithm: ");
		String algorithm = scanner.nextLine();

		try {
			// read cipher file as byte array
			byte[] file = Files.readAllBytes(Path.of(PATH2));

			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] hash = digest.digest(file);

			String hashString = "";
			System.out.print(algorithm + ": ");
			for (int i = 0; i < hash.length; i++) {
				System.out.print(String.format("%02X", hash[i]));
				hashString += String.format("%02X", hash[i]);
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(algorithm + "-Hash.log"));//pfad????????????????????????????????????
			bw.write(hashString);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		scanner.close();
	}

}
