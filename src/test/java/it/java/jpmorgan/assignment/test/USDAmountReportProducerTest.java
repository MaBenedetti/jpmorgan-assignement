/**
 * 
 */
package it.java.jpmorgan.assignment.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.java.jpmorgan.assignment.component.impl.USDAmountReportProducerImpl;
import it.java.jpmorgan.assignment.domain.Entity;
import it.java.jpmorgan.assignment.domain.Instruction;
import it.java.jpmorgan.assignment.domain.OperationType;
import it.java.jpmorgan.assignment.domain.USDAmountReport;
import it.java.jpmorgan.assignment.exceptions.GenericJPMorganException;

/**
 * @author mbenedetti
 *
 */
public class USDAmountReportProducerTest {

	private USDAmountReportProducerImpl reportProducer;

	@Before
	public void init(){
		
		reportProducer = new USDAmountReportProducerImpl();
		
	}

	@Test
	public void assertReportIncomingRankingOK(){

		List<Instruction> instructions = new ArrayList<>();
		
		instructions.add(new Instruction(
				new Entity("foo"), 
				OperationType.BUY, 
				0.50, 
				"SGP", 
				LocalDate.parse("01 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("01 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				200,
				100.0));

		instructions.add(new Instruction(
				new Entity("foo"), 
				OperationType.BUY, 
				0.90, 
				"SAR", 
				LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				100,
				300.0));
		
		instructions.add(new Instruction(
				new Entity("bar"), 
				OperationType.BUY, 
				0.10, 
				"EUR", 
				LocalDate.parse("19 Jan 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("21 Jan 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				370,
				50.0));
		
		try {
			USDAmountReport report = reportProducer.createReport(instructions);
			Assert.assertTrue(new Double(1.0).equals( report.getIncomingRank().get(new Entity("foo")) ) &&  
					new Double(0.05).equals( report.getIncomingRank().get(new Entity("bar")) ));
		} catch (GenericJPMorganException e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	@Test
	public void assertReportOutgoingRankingOK(){
		
		List<Instruction> instructions = new ArrayList<>();

		instructions.add(new Instruction(
				new Entity("foo"), 
				OperationType.BUY, 
				0.50, 
				"SGP", 
				LocalDate.parse("01 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("01 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				200,
				100.25));

		instructions.add(new Instruction(
				new Entity("foo"), 
				OperationType.SELL, 
				0.5, 
				"SAR", 
				LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				100,
				300.0));

		instructions.add(new Instruction(
				new Entity("bar"), 
				OperationType.SELL, 
				0.5, 
				"EUR", 
				LocalDate.parse("19 Jan 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("21 Jan 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				100,
				300.0));

		instructions.add(new Instruction(
				new Entity("bar"), 
				OperationType.SELL, 
				0.5, 
				"AED", 
				LocalDate.parse("05 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("07 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				200,
				150.0));
		
		try {
			USDAmountReport report = reportProducer.createReport(instructions);
			System.out.println(report);
			Assert.assertTrue(new Double(1.0).equals( report.getOutgoingRank().get(new Entity("bar")) ) &&  
					new Double(0.5).equals( report.getOutgoingRank().get(new Entity("foo")) ));
		} catch (GenericJPMorganException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void assertReportArabianWeekdaysOK(){
		
		List<Instruction> instructions = new ArrayList<>();

		instructions.add(new Instruction(
				new Entity("foo"), 
				OperationType.BUY, 
				0.50, 
				"AED", 
				LocalDate.parse("18 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("20 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				200,
				100.25));
		
		instructions.add(new Instruction(
				new Entity("foo"), 
				OperationType.BUY, 
				0.50, 
				"AED", 
				LocalDate.parse("16 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("18 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				200,
				100.25));

		instructions.add(new Instruction(
				new Entity("foo"), 
				OperationType.SELL, 
				0.5, 
				"SAR", 
				LocalDate.parse("18 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("21 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				100,
				300.0));

		instructions.add(new Instruction(
				new Entity("foo"), 
				OperationType.SELL, 
				0.5, 
				"SAR", 
				LocalDate.parse("16 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("18 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				100,
				300.0));
		
		try {
			USDAmountReport report = reportProducer.createReport(instructions);
			System.out.println(report);
			Assert.assertTrue(
					new Double(10025.0).equals(report.getIncomingSettlements().get(LocalDate.parse("22 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy"))))
					&&
					new Double(15000.0).equals(report.getOutgoingSettlements().get(LocalDate.parse("22 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy"))))
					);
		} catch (GenericJPMorganException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void assertReportNotArabianWeekdaysOK(){
		
		List<Instruction> instructions = new ArrayList<>();
		
		instructions.add(new Instruction(
				new Entity("foo"), 
				OperationType.BUY, 
				0.50, 
				"EUR", 
				LocalDate.parse("18 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("21 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				200,
				100.25));
		
		instructions.add(new Instruction(
				new Entity("foo"), 
				OperationType.BUY, 
				0.50, 
				"EUR", 
				LocalDate.parse("16 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("18 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				200,
				100.25));

		instructions.add(new Instruction(
				new Entity("foo"), 
				OperationType.SELL, 
				0.5, 
				"EUR", 
				LocalDate.parse("18 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("22 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				100,
				300.0));

		instructions.add(new Instruction(
				new Entity("foo"), 
				OperationType.SELL, 
				0.5, 
				"EUR", 
				LocalDate.parse("16 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				LocalDate.parse("18 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")), 
				100,
				300.0));
		
		
		try {
			USDAmountReport report = reportProducer.createReport(instructions);
			System.out.println(report);
			Assert.assertTrue(
					new Double(10025.0).equals(report.getIncomingSettlements().get(LocalDate.parse("23 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy"))))
					&&
					new Double(15000.0).equals(report.getOutgoingSettlements().get(LocalDate.parse("23 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy"))))
					);
		} catch (GenericJPMorganException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
