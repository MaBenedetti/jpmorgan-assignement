/**
 * 
 */
package it.java.jpmorgan.assignment.domain;

import java.time.LocalDate;

/**
 * @author mbenedetti
 *
 */
public class Instruction {

	private Entity entity;
	private OperationType operationType;
	private double agreedFx;
	private String currency;
	private LocalDate instructionDate;
	private LocalDate settlementDate;
	private long units;
	private double unitaryPrice;
	
	/**
	 * 
	 */
	public Instruction() {
		super();
	}

	/**
	 * @param entity
	 * @param operationType
	 * @param agreedFx
	 * @param currency
	 * @param instructionDate
	 * @param settlementDate
	 * @param units
	 * @param unitaryPrice
	 * @param usdAmount
	 */
	public Instruction(Entity entity, OperationType operationType, double agreedFx, String currency,
			LocalDate instructionDate, LocalDate settlementDate, long units, double unitaryPrice) {
		super();
		this.entity = entity;
		this.operationType = operationType;
		this.agreedFx = agreedFx;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.units = units;
		this.unitaryPrice = unitaryPrice;
	}

	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * @return the operationType
	 */
	public OperationType getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType the operationType to set
	 */
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return the agreedFx
	 */
	public double getAgreedFx() {
		return agreedFx;
	}

	/**
	 * @param agreedFx the agreedFx to set
	 */
	public void setAgreedFx(double agreedFx) {
		this.agreedFx = agreedFx;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the instructionDate
	 */
	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	/**
	 * @param instructionDate the instructionDate to set
	 */
	public void setInstructionDate(LocalDate instructionDate) {
		this.instructionDate = instructionDate;
	}

	/**
	 * @return the settlementDate
	 */
	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	/**
	 * @param settlementDate the settlementDate to set
	 */
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	/**
	 * @return the units
	 */
	public long getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(long units) {
		this.units = units;
	}

	/**
	 * @return the unitaryPrice
	 */
	public double getUnitaryPrice() {
		return unitaryPrice;
	}

	/**
	 * @param unitaryPrice the unitaryPrice to set
	 */
	public void setUnitaryPrice(double unitaryPrice) {
		this.unitaryPrice = unitaryPrice;
	}

	/**
	 * @return the usdAmount
	 */
	public double getUsdAmount() {
		return this.unitaryPrice * this.units * this.agreedFx;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(agreedFx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((instructionDate == null) ? 0 : instructionDate.hashCode());
		result = prime * result + ((operationType == null) ? 0 : operationType.hashCode());
		result = prime * result + ((settlementDate == null) ? 0 : settlementDate.hashCode());
		temp = Double.doubleToLongBits(unitaryPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (units ^ (units >>> 32));
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
		Instruction other = (Instruction) obj;
		if (Double.doubleToLongBits(agreedFx) != Double.doubleToLongBits(other.agreedFx))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (instructionDate == null) {
			if (other.instructionDate != null)
				return false;
		} else if (!instructionDate.equals(other.instructionDate))
			return false;
		if (operationType != other.operationType)
			return false;
		if (settlementDate == null) {
			if (other.settlementDate != null)
				return false;
		} else if (!settlementDate.equals(other.settlementDate))
			return false;
		if (Double.doubleToLongBits(unitaryPrice) != Double.doubleToLongBits(other.unitaryPrice))
			return false;
		if (units != other.units)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Instruction [entity=" + entity + ", operationType=" + operationType + ", agreedFx=" + agreedFx
				+ ", currency=" + currency + ", instructionDate=" + instructionDate + ", settlementDate="
				+ settlementDate + ", units=" + units + ", unitaryPrice=" + unitaryPrice + "]";
	}
}
