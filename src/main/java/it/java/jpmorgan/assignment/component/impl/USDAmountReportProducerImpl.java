/**
 * 
 */
package it.java.jpmorgan.assignment.component.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import it.java.jpmorgan.assignment.component.USDAmountReportProducer;
import it.java.jpmorgan.assignment.domain.Entity;
import it.java.jpmorgan.assignment.domain.Instruction;
import it.java.jpmorgan.assignment.domain.USDAmountReport;
import it.java.jpmorgan.assignment.exceptions.GenericJPMorganException;
import it.java.jpmorgan.assignment.exceptions.NoInstructionToAnalyzeException;

/**
 * @author mbenedetti
 *
 */
public class USDAmountReportProducerImpl implements USDAmountReportProducer {

	/**
	 * 
	 */
	public USDAmountReportProducerImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see it.java.jpmorgan.assignment.component.USDAmountReportProducer#createReport(java.util.List)
	 */
	@Override
	public USDAmountReport createReport(List<Instruction> instructions) throws GenericJPMorganException {

		if(instructions == null || instructions.size() == 0)
			throw new NoInstructionToAnalyzeException();
		
		//Create the empty report
		USDAmountReport report = new USDAmountReport();

		//Create the map where put incoming settlments grouped by date
		Map<LocalDate, Double> incomingSettlements = new HashMap<>();
		//Create the map where put outgoing settlments grouped by date
		Map<LocalDate, Double> outgoingSettlements = new HashMap<>();

		//Create the map where put incoming ranking for each entity		
		Map<Entity, Double> incomingAmounts = new HashMap<>();
		//Create the map where put outgoing ranking for each entity
		Map<Entity, Double> outgoingAmounts = new HashMap<>();

		//Variables where store the highest amount, used to calculate rank
		AtomicReference<Double> incomingMaxAmount = new AtomicReference<Double>(Double.valueOf(0.0));
		AtomicReference<Double> outgoingMaxAmount = new AtomicReference<Double>(Double.valueOf(0.0));
		
		//Iterate instructions
		instructions.forEach((instruction) -> {

			//Storing settlment date and date of week
			LocalDate settlementDate = instruction.getSettlementDate();
			DayOfWeek settlementDayOfWeek = settlementDate.getDayOfWeek();
			
			//Temporary variables that will point at incoming or outgoing maps
			Map<LocalDate, Double> settlements;
			Map<Entity, Double> amounts;

			//if is the operation is a purchase, we'll use the incoming maps, if a sale the outgoing maps, otherwise an exception is raised 
			switch (instruction.getOperationType()) {
			case BUY:
				settlements = incomingSettlements;
				amounts = incomingAmounts;
				incomingMaxAmount.set(Math.max(incomingMaxAmount.get(), instruction.getUsdAmount())); 
				break;
			case SELL:
				settlements = outgoingSettlements;
				amounts = outgoingAmounts;
				outgoingMaxAmount.set(Math.max(outgoingMaxAmount.get(), instruction.getUsdAmount()));
				break;
			default:
				throw new UnsupportedOperationException("The operation "+instruction.getOperationType().getNameOperation()+" is not supported");
			}

			//if the settlement date falls on a weekend, we'll jump to the first working date
			//for currencies AED e SAR the weekend falls on friday and saturday, otherwise the dates are saturday and sunday
			if(instruction.getCurrency().equals("AED") || instruction.getCurrency().equals("SAR")){
				if(settlementDayOfWeek.equals(DayOfWeek.FRIDAY) || settlementDayOfWeek.equals(DayOfWeek.SATURDAY))
					settlementDate = settlementDate.with(DayOfWeek.SUNDAY);
			}else if(settlementDayOfWeek.equals(DayOfWeek.SUNDAY) || settlementDayOfWeek.equals(DayOfWeek.SATURDAY))
				settlementDate = settlementDate.plusWeeks(1).with(DayOfWeek.MONDAY);

			//We'll add the USD Amount to the map, for the settlement date
			if(settlements.containsKey(settlementDate))
				settlements.computeIfPresent(settlementDate, (k, v) -> v + instruction.getUsdAmount());
			else
				settlements.put(settlementDate, instruction.getUsdAmount());
			
			//We'll add the USD Amount to the map, for the entity
			if(amounts.containsKey(instruction.getEntity()))
				amounts.computeIfPresent(instruction.getEntity(), (k, v) -> v + instruction.getUsdAmount());
			else
				amounts.put(instruction.getEntity(), instruction.getUsdAmount());
			
			//now the max amount can be updated with the new total amount for the current entity
			switch (instruction.getOperationType()) {
			case BUY:
				incomingMaxAmount.set(Math.max(incomingMaxAmount.get(), amounts.get(instruction.getEntity()))); 
				break;
			case SELL:
				outgoingMaxAmount.set(Math.max(outgoingMaxAmount.get(), amounts.get(instruction.getEntity())));
				break;
			default:
				throw new UnsupportedOperationException("The operation "+instruction.getOperationType().getNameOperation()+" is not supported");
			}
		});
		
		//When all entities has their total amounts updated, the ranking maps can be created using the max amounts as reference.
		Map<Entity, Double> incomingRanking = new LinkedHashMap<>(incomingAmounts.size());
		incomingAmounts.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.forEachOrdered( record -> incomingRanking.put(record.getKey(), record.getValue() / incomingMaxAmount.get()) );

		Map<Entity, Double> outgoingRanking = new LinkedHashMap<>(outgoingAmounts.size());
		outgoingAmounts.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		.forEachOrdered( record -> {
			System.out.println(record);
			outgoingRanking.put(record.getKey(), record.getValue() / outgoingMaxAmount.get());
		});

		//Populating the report
		report.setIncomingSettlements(incomingSettlements);
		report.setOutgoingSettlements(outgoingSettlements);
		
		report.setIncomingRank(incomingRanking);
		report.setOutgoingRank(outgoingRanking);

		return report;
	}

}
