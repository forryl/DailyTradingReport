package forrester.lynn.trade.report.model;

/**
 * Represents a Currency in ISO4217 format
 * 
 * Note: this is not a complete list of all available currencies, just a shortened list.
 * 
 * @author Lynn Forrester
 */
public enum Currency {
  EUR,
  GBP,
  AUS,
  SAR,
  AED,
  CAD,
  INR,
  SGD,
  ARS;

  @Override
  public String toString() {
    return name();
  }
}
