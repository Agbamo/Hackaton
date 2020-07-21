package lider.domain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import lider.domain.ImageUtil;

public class imagePreprocesser {
	
	public static void main (String[] args) {
		
		File folder = new File(args[0]);
		ArrayList<Object[]> vectors = new ArrayList<Object[]>();
		
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isFile() && fileEntry.getAbsolutePath().endsWith("9.jpg")) {
				try {
					Color[][] pixels = ImageUtil.loadPixelsFromFile(fileEntry);
					
					System.out.println(pixels[0][0].getRed());
					
					integrityCheck(pixels);					
					
					vectors.add(ImageUtil.imageToArray(pixels));
					
					System.out.println(pixels[0].length + ", " + pixels[1].length);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Object[] matrix = vectors.toArray();
		String file = ImageUtil.matrix2csv(matrix);
		
        try {
			FileWriter fw = new FileWriter(System.getProperty("user.home") + "/resultLider.out");
			fw.write(file);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void integrityCheck(Color[][] pixels) {
		System.out.println("Integrity check:");
		System.out.println(pixels[0][0].getRed());
		System.out.println(pixels[1][0].getRed());
		System.out.println(pixels[2][0].getRed());
		System.out.println(pixels[3][0].getRed());
		
	}
}


