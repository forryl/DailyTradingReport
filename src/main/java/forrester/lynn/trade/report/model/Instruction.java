package forrester.lynn.trade.report.model;

import java.util.Date;

import lombok.Data;

/**
 * Represents a single Instruction
 * 
 * @author Lynn Forrester
 */
@Data
public class Instruction {

  private String entityName; 
  private BuySell buySell; 
  private Double agreedFx; 
  private Currency currency; 
  private Date instructionDate;
  private Date settlementDate; 
  private Integer units; 
  private Double pricePerUnit;
  
  /*
   * Calculated field
   */
  private Double tradeAmount;
  
  @Override
  public boolean equals(Object other) {
    boolean result;
    if ((other == null) || (getClass() != other.getClass())) {
      result = false;
    } else {
      Instruction otherInstruction = (Instruction) other;
      result = entityName.equals(otherInstruction.entityName)
          && buySell.equals(otherInstruction.buySell) && agreedFx.equals(otherInstruction.agreedFx)
          && currency.equals(otherInstruction.currency)
          && instructionDate.equals(otherInstruction.instructionDate)
          && settlementDate.equals(otherInstruction.settlementDate)
          && units.equals(otherInstruction.units)
          && pricePerUnit.equals(otherInstruction.pricePerUnit);
    }
    return result;
  }

  @Override
  public int hashCode() {
    return entityName.hashCode() + buySell.hashCode() + currency.hashCode()
        + instructionDate.hashCode() + settlementDate.hashCode() + units.hashCode()
        + pricePerUnit.hashCode();
  }
}
