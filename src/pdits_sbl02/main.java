package pdits_sbl02;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Arrays;

public class main {

	private static final String PATH1 = "./res/n01.txt.enc";
	private static final String PATH2 = "./res/n02.txt.enc";
	private static final String PATH3 = "./res/n03.txt.enc";
	private static final String PATH4 = "./res/n04.txt.enc";
	private static final String PATH5 = "./res/n05.txt.enc";
	private static final String PATH6 = "./res/n06.txt.enc";

	public static void main(String[] args) {
		BaziCrypt baziCrypt = new BaziCrypt();
		baziCrypt.crackBazi(PATH1);
		System.out.println();
		
		baziCrypt.crackBazi(PATH2);
		System.out.println();
		
		baziCrypt.crackBazi(PATH3);
		System.out.println();
		
		// AdvaziCrypt
		baziCrypt.crackAdvazi(PATH4);
		System.out.println();
		
		baziCrypt.crackAdvazi(PATH5);
		System.out.println();
		
		baziCrypt.crackAdvazi(PATH6);
		System.out.println();
	}

}
