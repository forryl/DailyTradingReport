package forrester.lynn.trade.report.consumer;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

import forrester.lynn.trade.report.model.Currency;
import forrester.lynn.trade.report.model.Instruction;

/**
 * This class represents the Instruction Consumer.
 * 
 * @author Lynn Forrester
 */
public class InstructionConsumer implements Consumer<Instruction>{

  /**
   * Performs calculations on the Instruction to work out the tradeAmount
   * and also checks for the settlement date.
   * 
   * @param t The Instruction to perform the adjustments on
   */
  @Override
  public void accept(Instruction instruction) {
    
    // calculate the trade amount
    Double tradeAmount =
        instruction.getPricePerUnit() 
        * instruction.getUnits() 
        * instruction.getAgreedFx();
    
    instruction.setTradeAmount(tradeAmount);
    
    // check the settlement dates and adjust to the next working day if required.
    while (isWeekend(instruction.getSettlementDate(), instruction.getCurrency())) {
      instruction.setSettlementDate(addDay(instruction.getSettlementDate()));
    }
  }
  
  /*
   * Performs a check against the currency to see if the date falls at the weekend based on the
   * currency type.
   */
  private boolean isWeekend(Date d, Currency currency) {
    Calendar c = Calendar.getInstance();
    c.setTime(d);

    boolean alternateCurrency = alternativeWeekendCurrency(currency);
    
    boolean friSatWeekend = Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK) 
        || Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK);
    
    boolean satSunWeekend = Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK) 
        || Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK);
    
    return (alternateCurrency && friSatWeekend) || (!alternateCurrency && satSunWeekend);
  }

  /*
   * Adds the specified number of days the date
   */
  private Date addDay(Date d) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(d);
    calendar.add(Calendar.DATE, 1);
    return calendar.getTime();
  }

  /*
   * Looks up to see if the currency value means the weekend falls on different dates
   */
  private boolean alternativeWeekendCurrency(Currency currency) {
    return Currency.AED.equals(currency) || Currency.SAR.equals(currency);
  }

}
