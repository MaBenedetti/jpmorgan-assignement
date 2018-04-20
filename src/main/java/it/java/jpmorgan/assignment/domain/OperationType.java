/**
 * 
 */
package it.java.jpmorgan.assignment.domain;

/**
 * @author mbenedetti
 *
 */
public enum OperationType {

	BUY("B"),
	SELL("S");
	
	private String nameOperation;
	
	OperationType(String nameOperation) {
		this.nameOperation = nameOperation;
	}
	
	public String getNameOperation() {
		return nameOperation;
	}
	
	public static OperationType fromString(final String nameOperation){
		
		switch (nameOperation) {
		case "B":
			return BUY;
		case "S":
			return SELL;
		default:
			return null;
		}
		
	}
}
