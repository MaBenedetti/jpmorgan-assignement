/**
 * 
 */
package it.java.jpmorgan.assignment.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.java.jpmorgan.assignment.domain.Entity;
import it.java.jpmorgan.assignment.domain.Instruction;
import it.java.jpmorgan.assignment.domain.OperationType;
import it.java.jpmorgan.assignment.exceptions.CSVRecordIncompleteException;
import it.java.jpmorgan.assignment.exceptions.CSVRecordNotValidExcpetion;
import it.java.jpmorgan.assignment.exceptions.CSVRecordParsingException;
import it.java.jpmorgan.assignment.exceptions.EndOfCSVDocumentReadched;
import it.java.jpmorgan.assignment.exceptions.GenericJPMorganException;

/**
 * @author mbenedetti
 *
 */
public class CSVUtilHelper {
	
	//The character used as values's separator in CSV file
	private static final char SEPARATOR = ';';
	//The character as QUOTE in CSV file
	private static final char QUOTE = '"';
	
	/**
	 * This method create an User Object from a CSV Line
	 * */
	public static Instruction parseLine(Scanner scanner) throws GenericJPMorganException{

		//get the parsed values
		List<String> parsedLine = parseLine(scanner, null);
		
		try{
			//Creating a domain object, according to the CSV template
			Instruction user = new Instruction(new Entity(parsedLine.get(0)), 
					OperationType.fromString(parsedLine.get(1)), 
					Double.parseDouble(parsedLine.get(2)), 
					parsedLine.get(3),
					LocalDate.parse(parsedLine.get(4), DateTimeFormatter.ofPattern("dd MMM yyyy")), 
					LocalDate.parse(parsedLine.get(5), DateTimeFormatter.ofPattern("dd MMM yyyy")), 
					Long.parseLong(parsedLine.get(6)), 
					Double.parseDouble(parsedLine.get(7)));
			
			return user;
		}catch (Exception e) {
			throw new CSVRecordNotValidExcpetion("The parsed record is not valid: "+parsedLine);
		}
	}

	/**
	 * This method parse the CSV line. 
	 * If the line contains a carriage return between two QUOTES, the method read the next lines until the QUOTE is closed
	 * */
	private static List<String> parseLine(Scanner scanner, List<String> parsedLine) throws GenericJPMorganException{
		
		//Check again if the end of document has been reached
		if(!scanner.hasNext())
			throw new EndOfCSVDocumentReadched();
		
		//Get the next line
		String csvLine = scanner.nextLine();
		
		try{
			//this boolean points out if we are among two QUOTES
			boolean isInQuote = false;
			
			//The temporary variable where to insert characters. 
			String currentValue = new String();
			
			//If the list is populated means that QUOTE not been closes in previous line 
			if(parsedLine != null && parsedLine.size() > 0){
				isInQuote = true;
				currentValue = parsedLine.get(parsedLine.size()-1);
				parsedLine.remove(parsedLine.size()-1);
			}else
				parsedLine = new ArrayList<>();
			
			//iterate all characters 
			//when a SEPARATOR is found, the current value is added to the list, and the temporary variable is initialized again
			//when a QUOTE is found, the inQuote boolean change is state
			for(char c : csvLine.toCharArray()){
				if(c == SEPARATOR && !isInQuote){
					parsedLine.add(currentValue);
					currentValue = new String();
				}else if(c == QUOTE)
					isInQuote = !isInQuote;
				else
					currentValue += String.valueOf(c);
			}
			
			//the end of line is reached, we add the last word to the list
			parsedLine.add(currentValue);
			
			//if the QUOTE is not closed, we try to read the next line
			if(isInQuote){
				if(scanner.hasNext())
					return parseLine(scanner, parsedLine);
				else
					throw new CSVRecordIncompleteException("The row has a not closed QUOTE: "+csvLine);
			}
			
			return parsedLine;
		}catch(Exception e){
			throw new CSVRecordParsingException("Impossibile to parse the line: "+csvLine);
		}
	}
}
