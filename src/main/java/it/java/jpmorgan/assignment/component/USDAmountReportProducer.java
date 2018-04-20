/**
 * 
 */
package it.java.jpmorgan.assignment.component;

import java.util.List;

import it.java.jpmorgan.assignment.domain.Instruction;
import it.java.jpmorgan.assignment.domain.USDAmountReport;
import it.java.jpmorgan.assignment.exceptions.GenericJPMorganException;

/**
 * @author mbenedetti
 *
 */
public interface USDAmountReportProducer {

	/**
	 * @param java.util.List of it.java.jpmorgan.assignment.domain.Instruction
	 * @return it.java.jpmorgan.assignment.domain.USDAmountReport
	 * 
	 * The method create a Report from a list of Instructions.
	 * The Report consists in 
	 *  - maps of incoming settlements and outgoing settlements, grouped by settlement date
	 *  - list of entities with inconoming and outgoing ranking
	 * @throws GenericJPMorganException 
	 */
	public USDAmountReport createReport(List<Instruction> instructions) throws GenericJPMorganException;
}
