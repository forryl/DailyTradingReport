package forrester.lynn.trade.report;

/**
 * Entry class in the Daily Report Generator
 * 
 * @author Lynn Forrester
 */
public class App 
{
    public static void main( String[] args ) {
       ReportGenerator reportGenerator = new ReportGenerator();
       reportGenerator.generateReport();
    }
}
