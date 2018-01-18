package forrester.lynn.trade.report;

import org.junit.Before;
import org.junit.Test;

import forrester.lynn.trade.report.ReportGenerator;

/**
 * Test Class for the Report Generator
 */
public class ReportGeneratorTest {

  private ReportGenerator generator;
  
  @Before
  public void setUp(){
    generator = new ReportGenerator();
  }
  
  @Test
  public void testLoadingFile(){
    generator.generateReport();
  }
}
