package forrester.lynn.trade.report.consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import forrester.lynn.trade.report.model.BuySell;
import forrester.lynn.trade.report.model.Currency;
import forrester.lynn.trade.report.model.Instruction;

/**
 * Tests the InstructionConsumer class.
 * 
 * @author Lynn Forrester
 */
public class InstructionConsumerTest {

  private InstructionConsumer consumer;
  
  @Before
  public void setUp(){
    consumer = new InstructionConsumer();
  }
  
  /**
   * Tests the consumer does not alter the settlement date as the date is 
   * not at the weekend
   */
  @Test
  public void testConsumerNoWeekends() {

    Calendar settlement = Calendar.getInstance();
    settlement.set(2018, Calendar.JANUARY, 5); // FRIDAY

    Calendar instructed = Calendar.getInstance();
    instructed.set(2018, Calendar.JANUARY, 2); 

    Instruction instruction = new Instruction();
    instruction.setAgreedFx(1.0);
    instruction.setBuySell(BuySell.BUY);
    instruction.setCurrency(Currency.GBP);
    instruction.setEntityName("foo");
    instruction.setInstructionDate(instructed.getTime());
    instruction.setPricePerUnit(1.0);
    instruction.setSettlementDate(settlement.getTime());
    instruction.setUnits(1);

    assertNull(instruction.getTradeAmount());

    consumer.accept(instruction);

    assertNotNull(instruction.getTradeAmount());

    // 1 * 1 * 1
    assertEquals(new Double(1), instruction.getTradeAmount());

    assertEquals(settlement.getTime(), instruction.getSettlementDate());
  }
  
  /**
   * Tests the consumer does alter the settlement date as the date is 
   * at the weekend
   */
  @Test
  public void testConsumerWeekend() {

    Calendar settlement = Calendar.getInstance();
    settlement.set(2018, Calendar.JANUARY, 7); // SUNDAY 7 JAN

    Calendar instructed = Calendar.getInstance();
    instructed.set(2018, Calendar.JANUARY, 2); 

    Instruction instruction = new Instruction();
    instruction.setAgreedFx(1.0);
    instruction.setBuySell(BuySell.BUY);
    instruction.setCurrency(Currency.GBP);
    instruction.setEntityName("foo");
    instruction.setInstructionDate(instructed.getTime());
    instruction.setPricePerUnit(1.0);
    instruction.setSettlementDate(settlement.getTime());
    instruction.setUnits(1);

    assertNull(instruction.getTradeAmount());

    consumer.accept(instruction);

    assertNotNull(instruction.getTradeAmount());

    // 1 * 1 * 1
    assertEquals(new Double(1), instruction.getTradeAmount());

    settlement.add(Calendar.DATE, 1); // Moves to MON 8
    
    assertEquals(settlement.getTime(), instruction.getSettlementDate());
  }
  
  /**
   * Tests the consumer does alter the settlement date as the currency 
   * means it's the weekend
   */
  @Test
  public void testConsumerWeekendsAEDCurrency() {

    Calendar settlement = Calendar.getInstance();
    settlement.set(2018, Calendar.JANUARY, 5); // FRIDAY 5 JAN

    Calendar instructed = Calendar.getInstance();
    instructed.set(2018, Calendar.JANUARY, 2);

    Instruction instruction = new Instruction();
    instruction.setAgreedFx(1.0);
    instruction.setBuySell(BuySell.BUY);
    instruction.setCurrency(Currency.AED);
    instruction.setEntityName("foo");
    instruction.setInstructionDate(instructed.getTime());
    instruction.setPricePerUnit(1.0);
    instruction.setSettlementDate(settlement.getTime());
    instruction.setUnits(1);

    assertNull(instruction.getTradeAmount());

    consumer.accept(instruction);

    assertNotNull(instruction.getTradeAmount());

    // 1 * 1 * 1
    assertEquals(new Double(1), instruction.getTradeAmount());
    
    settlement.add(Calendar.DATE, 2); // Moves to SUN 7

    assertEquals(settlement.getTime(), instruction.getSettlementDate());
  }
  
  /**
   * Tests the consumer does alter the settlement date as the currency 
   * means it's the weekend
   */
  @Test
  public void testConsumerWeekendsSARCurrency() {

    Calendar settlement = Calendar.getInstance();
    settlement.set(2018, Calendar.JANUARY, 6); // SAT 5 JAN

    Calendar instructed = Calendar.getInstance();
    instructed.set(2018, Calendar.JANUARY, 2);

    Instruction instruction = new Instruction();
    instruction.setAgreedFx(1.0);
    instruction.setBuySell(BuySell.BUY);
    instruction.setCurrency(Currency.SAR);
    instruction.setEntityName("foo");
    instruction.setInstructionDate(instructed.getTime());
    instruction.setPricePerUnit(1.0);
    instruction.setSettlementDate(settlement.getTime());
    instruction.setUnits(1);

    assertNull(instruction.getTradeAmount());
    
    consumer.accept(instruction);

    assertNotNull(instruction.getTradeAmount());

    // 1 * 1 * 1
    assertEquals(new Double(1), instruction.getTradeAmount());
    
    settlement.add(Calendar.DATE, 1); // Moves to SUN 7

    assertEquals(settlement.getTime(), instruction.getSettlementDate());
  }
  
  /**
   * Tests the consumer calculates the trade amount
   */
  @Test
  public void testTradeAmount() {

    Calendar settlement = Calendar.getInstance();
    settlement.set(2018, Calendar.JANUARY, 5); // FRIDAY

    Calendar instructed = Calendar.getInstance();
    instructed.set(2018, Calendar.JANUARY, 2); 

    Instruction instruction = new Instruction();
    instruction.setAgreedFx(2.0);
    instruction.setBuySell(BuySell.BUY);
    instruction.setCurrency(Currency.GBP);
    instruction.setEntityName("foo");
    instruction.setInstructionDate(instructed.getTime());
    instruction.setPricePerUnit(2.0);
    instruction.setSettlementDate(settlement.getTime());
    instruction.setUnits(2);

    assertNull(instruction.getTradeAmount());

    consumer.accept(instruction);

    assertNotNull(instruction.getTradeAmount());

    // 2 * 2 * 2
    assertEquals(new Double(8), instruction.getTradeAmount());

    assertEquals(settlement.getTime(), instruction.getSettlementDate());
  }
}
