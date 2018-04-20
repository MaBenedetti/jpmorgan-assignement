/**
 * 
 */
package it.java.jpmorgan.assignment.domain;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author mbenedetti
 *
 */
public class USDAmountReport {
	
	private Map<LocalDate, Double> incomingSettlements;
	private Map<LocalDate, Double> outgoingSettlements;
	
	private Map<Entity, Double> incomingRank;
	private Map<Entity, Double> outgoingRank;

	/**
	 * 
	 */
	public USDAmountReport() {
		super();
	}

	/**
	 * @return the incomingSettlements
	 */
	public Map<LocalDate, Double> getIncomingSettlements() {
		return incomingSettlements;
	}

	/**
	 * @param incomingSettlements the incomingSettlements to set
	 */
	public void setIncomingSettlements(Map<LocalDate, Double> incomingSettlements) {
		this.incomingSettlements = incomingSettlements;
	}

	/**
	 * @return the outgoingSettlements
	 */
	public Map<LocalDate, Double> getOutgoingSettlements() {
		return outgoingSettlements;
	}
	
	/**
	 * @param outgoingSettlements the outgoingSettlements to set
	 */
	public void setOutgoingSettlements(Map<LocalDate, Double> outgoingSettlements) {
		this.outgoingSettlements = outgoingSettlements;
	}

	/**
	 * @return the incomingRank
	 */
	public Map<Entity, Double> getIncomingRank() {
		return incomingRank;
	}

	/**
	 * @param incomingRank the incomingRank to set
	 */
	public void setIncomingRank(Map<Entity, Double> incomingRank) {
		this.incomingRank = incomingRank;
	}

	/**
	 * @return the outgoingRank
	 */
	public Map<Entity, Double> getOutgoingRank() {
		return outgoingRank;
	}
	
	/**
	 * @param outgoingRank the outgoingRank to set
	 */
	public void setOutgoingRank(Map<Entity, Double> outgoingRank) {
		this.outgoingRank = outgoingRank;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((incomingRank == null) ? 0 : incomingRank.hashCode());
		result = prime * result + ((incomingSettlements == null) ? 0 : incomingSettlements.hashCode());
		result = prime * result + ((outgoingRank == null) ? 0 : outgoingRank.hashCode());
		result = prime * result + ((outgoingSettlements == null) ? 0 : outgoingSettlements.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		USDAmountReport other = (USDAmountReport) obj;
		if (incomingRank == null) {
			if (other.incomingRank != null)
				return false;
		} else if (!incomingRank.equals(other.incomingRank))
			return false;
		if (incomingSettlements == null) {
			if (other.incomingSettlements != null)
				return false;
		} else if (!incomingSettlements.equals(other.incomingSettlements))
			return false;
		if (outgoingRank == null) {
			if (other.outgoingRank != null)
				return false;
		} else if (!outgoingRank.equals(other.outgoingRank))
			return false;
		if (outgoingSettlements == null) {
			if (other.outgoingSettlements != null)
				return false;
		} else if (!outgoingSettlements.equals(other.outgoingSettlements))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "USDAmountReport [\nincomingSettlements=" + incomingSettlements + "\noutgoingSettlements="
				+ outgoingSettlements + "\nincomingRank=" + incomingRank + "\noutgoingRank=" + outgoingRank + "\n]";
	}
	
}
