package forrester.lynn.trade.report.model;

/**
 * 
 * 
 */
public enum BuySell {

  BUY("b"),
  SELL("s");
  
  private String value;
  
  BuySell(final String value){
    this.value = value;
  }
  
  @Override
  public String toString() {
      return value;
  }
  
}
