package lider.domain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageUtil {

    public static Color[][] loadPixelsFromFile(File file) throws IOException {

        BufferedImage image = ImageIO.read(file);
        Color[][] colors = new Color[image.getWidth()][image.getHeight()];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                colors[x][y] = new Color(image.getRGB(x, y));
            }
        }
        return colors;
    }

	public static Object[] imageToArray(Color[][] pixels) {
		ArrayList arrayList = new ArrayList();
		for(int color=0; color<=2; color++) {
			for(int y=0; y<pixels[1].length; y++) {
				for(int x=0; x<pixels[0].length; x++) {
					if(color==0) {
						arrayList.add(pixels[x][y].getRed());
					}else if(color==0){
						arrayList.add(pixels[x][y].getGreen());
					}else {
						arrayList.add(pixels[x][y].getBlue());
					}
				}
			}
		}
		System.out.println(arrayList.size()/3/255/255);
		return arrayList.toArray();
	}

	public static String matrix2csv(Object[] matrix) {
		String csv = "";
		int pixelcounter = 0;
		
		for(int nx = 0; nx < ((Object[])matrix[0]).length; nx++) {
			for(int m = 0; m < matrix.length; m++) {
				csv += ((int)((Object[])matrix[m])[nx]);
				if(m==matrix.length-1) {
					csv += "\r\n";
				}else {
					csv += ";";
				}
				pixelcounter++;
				if(pixelcounter%1000 == 0) {
					System.out.println(pixelcounter);
				}
			}
		}
		
		System.out.println(pixelcounter + ", " + pixelcounter/256/256/3/10);
		return csv;
	}
}