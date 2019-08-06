package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class TestImpl {

	private static Double lowestValue = 1.00;
	private static Double highestValue = 20.00;

	public void testImplementation(String l) throws IOException {
		List<String> numList = readSourceFile();
		for (String s : numList) {
			writeToTheFile(convertToWord(s));
		}
	}

	private List<String> readSourceFile() throws IOException {
		BufferedReader inputFileText = new BufferedReader(new FileReader("/Users/vishnu/Downloads/demo/testFile.txt"));
		List<String> numberList = new ArrayList<String>();
		try {

			String line = inputFileText.readLine();
			while (line != null) {

				if (Double.valueOf(line) >= lowestValue && Double.valueOf(line) <= highestValue) {
					numberList.add(line);
				}
				line = inputFileText.readLine();
			}

		} finally {
			inputFileText.close();
		}

		return numberList;
	}

	private void writeToTheFile(String everything) {
		try (PrintWriter output = new PrintWriter(new FileWriter("/Users/vishnu/Downloads/demo/Result.txt", true))) {
			output.printf("%s\r\n", everything);
		} catch (Exception e) {
		}

	}

	private static final String INVALID_INPUT = "Unknow number pattern informed";

	private static final String[] singleDigit = { "", 
			"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve",
			"thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };

	private static final String[] doubleDigit = { "", 
			"ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };

	public static String convertToWord(String value) throws IllegalArgumentException {
		return transaleToWord(value);
	}

	private enum DataType {
		INTEGER, FLOATING_POINT
	}

	private static String getHundredsTensOnes(int value) {
		StringBuilder returnValue = new StringBuilder();
		if (value % 100 < 20) {
			returnValue.append(singleDigit[value % 100]);
			value /= 100;
		} else {
			returnValue.append(singleDigit[value % 10]);
			value /= 10;
			returnValue.insert(0, doubleDigit[value % 10] + " ");
			value /= 10;
		}
		if (value > 0) {
			returnValue.insert(0, singleDigit[value] + " hundred ");
		}
		return returnValue.toString();
	}

	private static String convertToDollars(String dollars) throws IllegalArgumentException, ParseException {
		StringBuilder dollarsStrBuilder = new StringBuilder(256);
		String[] values = dollars.split(",");
		int valuesIndex = 0;
		if (getIntValue(dollars) == 0) {
			return "";
		}
		switch (values.length) {
		case 1:
			dollarsStrBuilder.append(getHundredsTensOnes(getIntValue(values[valuesIndex])));
			break;
		}
		return dollarsStrBuilder.append(" dollar(s)").toString();
	}

	private static String translateCents(String cents) throws ParseException {
		if (cents.equals("00")) {
			return "";
		}
		return getHundredsTensOnes(getIntValue(cents)) + " cent(s)";
	}

	private static String transaleToWord(String number) throws IllegalArgumentException {
		StringBuilder numberInWords = new StringBuilder(256);
		try {
			switch (dataType(number)) {
			case INTEGER:
				numberInWords.append(convertToDollars(number));
				break;
			case FLOATING_POINT:
				int cents_position = number.length() - 2;
				boolean isMoreThanOneDollar = true;
				String cents = "";

				numberInWords.append(convertToDollars(number.substring(0, cents_position - 1)));
				if (numberInWords.toString().equals("")) {
					isMoreThanOneDollar = false;
				}
				cents = translateCents(number.substring(cents_position));
				if (isMoreThanOneDollar && !cents.equals("")) {
					numberInWords.append(" and ");
				}
				numberInWords.append(cents);
				break;
			default:
				throw new IllegalArgumentException(INVALID_INPUT);
			}
		} catch (IllegalArgumentException | ParseException ex) {
			System.err.println(ex);
			throw new IllegalArgumentException(ex);
		}
		return numberInWords.toString();
	}

	private static DataType dataType(String number) {
		if (Pattern.matches(".*(\\.\\d\\d)$", number)) {
			return DataType.FLOATING_POINT;
		}
		return DataType.INTEGER;
	}

	private static int getIntValue(String s) throws ParseException {
		return Math.abs(Integer.valueOf(NumberFormat.getNumberInstance(Locale.US).parse(s).toString()));
	}

}
