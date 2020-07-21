package hackapuntos.domain;

import java.awt.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hackapuntuador {

	static HashMap<String, Integer> words;

	public static void main(String[] args) {

		words = new HashMap<String, Integer>();

		try {
			FileReader fr = new FileReader(args[0]);
			FileWriter fw = new FileWriter(System.getProperty("user.home") + "/resultPuntos.out");

			words = read(fr);
			words = (HashMap<String, Integer>) sortByValue(words);
			String result = hashMap2Text(words);   
			fw.write(result);
			fw.flush();
			fw.close();
			System.out.println(result);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}



	}

	private static String hashMap2Text(HashMap<String, Integer> words) {
		String text = "";
		LinkedList<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(words.entrySet());
		for (Map.Entry<String, Integer> entry : list) {
			System.out.println("\r\n" + entry.getKey() + ", " + entry.getValue());
			text += entry.getKey() + " ";
		}

		return text;
	}

	private static HashMap<String, Integer> read(FileReader fr) {
		HashMap<String, Integer> words = new HashMap<String, Integer>();

		int readenChar;
		try {
			readenChar = fr.read();

			String buffer = "";
			while(readenChar!=-1){
				if( ((char)readenChar == ' ') || ((char)readenChar == (char)10) || ((char)readenChar == (char)13) ) {
					if(buffer.length() == 0) {
						// Ignore empty Strings
					}else if(!words.containsKey(buffer.toLowerCase())) {
						words.put(buffer.toLowerCase(), hackapuntuation(buffer.toLowerCase()));
					}
					buffer = "";
				}else if( ((char)readenChar == ',') || ((char)readenChar == '.') || ((char)readenChar == '"') || ((char)readenChar == ':')){
					// Do nothing
				}else {
					buffer += (char)readenChar;
				}
				readenChar=fr.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return words;
	}

	private static int hackapuntuation(String buffer) {

		int hackapoints = 0;

		hackapoints += countMatches("a", buffer);                          // 1 punto por cada 'a'
		if(countMatches("a", buffer) > 0) {
			System.out.println(buffer + " recibe "
					+ countMatches("a", buffer) +" puntos por las a");
		}                        
		if(buffer.contains("u")) {                                         // 2 puntos si tiene alguna "u"
			hackapoints = hackapoints+2; 
			System.out.println(buffer + " recibe 2 puntos por la u");
		}           
		if(countMatches("e", buffer) > 2) {                                // 4 puntos si tiene más de 2 "e"s
			hackapoints = hackapoints+4;
			System.out.println(buffer + " recibe 4 puntos por las e"); 
		}  
		if(isPalyndrome(buffer)) {                                         // 6 puntos si es un palíndromo
			hackapoints = hackapoints+6; 
			System.out.println(buffer + " recibe 6 puntos por palíndromo");
		}           
		if(duplicatedChar(buffer)) {                                       // 4 puntos si tiene letras duplicadas
			hackapoints = hackapoints+4; 
			System.out.println(buffer + " recibe"
					+ " 4 puntos por caracteres duplicados");
		}         
		if(buffer.length()>=5) {                                           // 1 punto por cada caracter a partir del 5º
			hackapoints += buffer.length()-4; 
			System.out.println(buffer + " recibe "
					+ (buffer.length()-4) +" puntos por las letras a partir de la 5");}        
		if(sameVocals(buffer)) {                                           // 10 puntos si sus vocales son exactamente {"a" , "a", "o"}
			hackapoints = hackapoints+10; 
			System.out.println(buffer + " recibe 10 puntos por a a o");
		}   
		System.out.println(buffer + " tiene " + hackapoints + " puntos!");
		return hackapoints;
	}

	private static boolean sameVocals(String buffer) {
		String vocals = substractVocals(buffer);
		if(vocals.equals("aao")) {
			return true;
		}
		return false;
	}

	private static String substractVocals(String buffer) {
		String vocals = "";

		for (int i = 0; i < buffer.length(); i++) {
			char currentChar = buffer.charAt(i);
			if( (currentChar == 'a') || (currentChar == 'e') || (currentChar == 'i') || (currentChar == 'o') || (currentChar == 'u') ) {
				vocals += currentChar;
			}
		}

		return vocals;
	}

	private static boolean duplicatedChar(String buffer) {
		for(int i = 1; i<buffer.length(); i++) {
			if(buffer.charAt(i-1) == buffer.charAt(i)) {
				return true;
			}
		}
		/*Pattern pattern = Pattern.compile("/(.)\\1{1,}/");
		Matcher matcher = pattern.matcher(buffer);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		if(count < 0) {
			return true;
		}*/
		return false;
	}

	private static boolean isPalyndrome(String buffer) {
		String reverse = "";
		for (int i = buffer.length()-1; i >=0 ; i--) {
			reverse += buffer.charAt(i);
		}
		if(reverse.equals(buffer)) { return true; }

		return false;
	}

	private static int countMatches(String character, String word) {
		Pattern pattern = Pattern.compile("[^"+character+"]*"+character);
		Matcher matcher = pattern.matcher(word);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}

	public static Map<String, Integer> sortByValue(Map<String, Integer> map) {
		LinkedList<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

			public int compare(Map.Entry<String, Integer> m1, Map.Entry<String, Integer> m2) {
				return (m2.getValue()).compareTo(m1.getValue());
			}
		});

		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

}