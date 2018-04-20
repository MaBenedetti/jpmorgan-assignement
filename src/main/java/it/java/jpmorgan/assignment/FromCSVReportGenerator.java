/**
 * 
 */
package it.java.jpmorgan.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.java.jpmorgan.assignment.component.impl.USDAmountReportProducerImpl;
import it.java.jpmorgan.assignment.domain.Instruction;
import it.java.jpmorgan.assignment.domain.USDAmountReport;
import it.java.jpmorgan.assignment.exceptions.GenericJPMorganException;
import it.java.jpmorgan.assignment.exceptions.ParameterNotPassedException;
import it.java.jpmorgan.assignment.exceptions.ParameterNotReadableException;
import it.java.jpmorgan.assignment.helper.CSVUtilHelper;

/**
 * @author mbenedetti
 *
 */
public class FromCSVReportGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Scanner scanner = null;

		try{
			if(args == null || args.length <= 0){
				throw new ParameterNotPassedException("Provide a valid CSV File");
			}

			//The input csv file. It will contains the records to be analyzed
			File csvInputFile = new File(args[0]);

			if(!csvInputFile.exists() || !csvInputFile.canRead())
				throw new ParameterNotReadableException("The file is not readable: ["+args[0]+"]");

			scanner = new Scanner(csvInputFile);

			List<Instruction> instructions = new ArrayList<>();

			while (scanner.hasNext()) {
				try{
					//The parsed record 
					Instruction instruction = CSVUtilHelper.parseLine(scanner);
					instructions.add(instruction);
				}catch(GenericJPMorganException e){
					System.err.println("Instruction not readable: "+e.getMessage());
				}catch(Exception e){
					System.err.println("Instruction not readable");
					e.printStackTrace();
				}
			}
			
			USDAmountReport report = new USDAmountReportProducerImpl().createReport(instructions);
			
			System.out.println(report);
		}catch(GenericJPMorganException e){
			System.err.println(e.getMessage());
			System.exit(-1);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}finally {
			if (scanner != null)
				scanner.close();
		}

		System.exit(0);

	}
}