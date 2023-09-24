package edu.cscc;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Scanner;

// TODO - student name, date, purpose of program
//Calvin Gates, 9/15/2022, practice using string functions to take raw values from text file then manipulate them to create a webpage
public class Lab02 {

	private static String pre = "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n<title>Natural Arches"+
			"</title>\n<meta charset=\"utf-8\">\n</head>\n<body>\n"+
			"<h1>Ohio Natural Arches</h1>\n<ul>\n";
	private static String post = "</ul>\n</body>\n</html>\n";
	private static String linkURL = "";
	private static final String LI_START = "<li>";
	private static final String LINK_END = "</a>";
	private static final String LI_END = "</li>\n";


	public static void main(String[] args) {
		BufferedWriter bw = null;
		File file = new File("Lab2Data.txt");
		try ( Scanner input = new Scanner(file); ) {
			bw = new BufferedWriter(new FileWriter(new File("arches.html")));
			bw.write(pre);
			input.nextLine();
			while(input.hasNext()) {
				String str = input.nextLine();
				
				String[] array = str.split(",");
				String name = array[0];
				String park = array[2];
				String county = array[1];
				String[] coordinates = array[3].split(" ");
				
				//After coordinate portion of string has been sliced up this block of code
				//will remove the degree symbol, turn the string into a double, then assign it to a variable
				Integer length = 0;
				length = coordinates[0].toString().length();
				Double number1 = Double.parseDouble(coordinates[0].toString().substring(0, length - 1));
				length = coordinates[1].toString().length();
				Double number2 = Double.parseDouble(coordinates[1].toString().substring(0, length - 1));
				length = coordinates[2].toString().length();
				Double number3 = Double.parseDouble(coordinates[2].toString().substring(0, length - 1));
				length = coordinates[4].toString().length();
				Double number4 = Double.parseDouble(coordinates[4].toString().substring(0, length - 1));
				length = coordinates[5].toString().length();
				Double number5 = Double.parseDouble(coordinates[5].toString().substring(0, length - 1));
				length = coordinates[6].toString().length();
				Double number6 = Double.parseDouble(coordinates[6].toString().substring(0, length - 1));
				
				//Converts coordinates into coordinates into decimal format, rounds to 4 digits, makes negative if necessary, then reassigns the variable
				double latitude;
				double longitude;
				latitude = number1 + (number2 / 60) + (number3 / 3600);
				longitude = number4 + (number5 / 60) + (number6 / 3600);
				latitude = round(latitude, 4);
				longitude = round(longitude, 4);
				if (coordinates[3].equals("S")) {
					latitude = latitude * -1;
				}
				if (coordinates[7].equals("W")) {
					longitude = longitude * -1;
				}
				
				//Creates URL and HTML element to link name to google maps for specific coordinates
				linkURL = "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;
				String LINK_START = "";
				LINK_START = "<a href=" + linkURL + ">";
				
				StringBuilder output1 = new StringBuilder();
				output1.append(LI_START);
				output1.append(LINK_START);
				output1.append(name);
				output1.append(LINK_END);
				output1.append(" at coordinates (");
				output1.append(latitude);
				output1.append(",");
				output1.append(longitude);
				output1.append(") is located at ");
				output1.append(park);
				output1.append(" in ");
				output1.append(county);
				output1.append(" County");
				output1.append(LI_END);

				bw.write(output1.toString());
			}
			bw.write(post);
		} catch(FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex2) {
			System.out.println(ex2.getMessage());
		}
		if (bw != null) {
			try { bw.close(); } catch (IOException e) { /* do nothing */ }
		}
		System.out.println("HTML file generated!");
	}
	

	//A rounding method to round the raw decimal format to 4 places, don't fully understand it but I found it online
	//noticed it removes the fourth digit if it's a zero :/
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}

//Alternative method of isolating the coordinate numbers
//String test = array[3].toString();
//System.out.println(array[3].indexOf('°'));
//System.out.println(array[3].indexOf('°', 3));
//System.out.println(array[3].indexOf('\''));
//System.out.println(array[3].indexOf('\'', 10));
//System.out.println(array[3].indexOf('"'));
//System.out.println(array[3].indexOf('"', 15));
//test.substring(array[3].indexOf('°'), array[3].indexOf('°', 3));
//System.out.println(test.substring(0, array[3].indexOf('°')));

//Experimenting with trying to assign coordinate variables using a for loop
//String output = LI_START + "Raw input data: " + str.replaceAll(""+DEGREES, "&deg;") + LI_END;
//System.out.println(array[3]);
//for (int i = 0; i < 7; i++) {
//	if (i != 3) {
//		Integer length = i;
//		length1 = coordinates[i].toString().length();
//		System.out.println(coordinates[i].toString().substring(0, length1 - 1));
//		Integer number = Integer.parseInt(coordinates[i].toString().substring(0, length1 - 1));
//	}
//}

//Old output method using concatenation instead of stringbuilder
//String output = LI_START + LINK_START + name + LINK_END + " at coordinates (" + latitude + "," + longitude + ") is located at " + park + " in " + county + " County" + LI_END;