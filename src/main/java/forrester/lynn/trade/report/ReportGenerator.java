package forrester.lynn.trade.report;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import forrester.lynn.trade.report.consumer.InstructionConsumer;
import forrester.lynn.trade.report.input.JsonLoader;
import forrester.lynn.trade.report.model.BuySell;
import forrester.lynn.trade.report.model.Instruction;
import forrester.lynn.trade.report.util.ReportGeneratorUtils;

/**
 * The report generator class.
 * 
 * The class performs a read of the data in JSON format and then performs an adjustment on the
 * values from the json.
 * 
 * The details of the report are then outputted to the console.
 * 
 * @author Lynn Forrester
 */
public class ReportGenerator {

  private InstructionConsumer instructionConsumer = new InstructionConsumer();
  
  private JsonLoader jsonLoader = new JsonLoader();

  /**
   * Performs a read of the JSON file for input and then starts the process of generating a report
   * on the console.
   */
  public void generateReport() {
    List<Instruction> instructions = jsonLoader.readJson("jsonInstructions.json");

    Map<BuySell, Map<Date, List<Instruction>>> processedData = processData(instructions);
    generateOutgoingReport(processedData);
    System.out.println();
    generateRankingsOutgoing(processedData);
  }

  /*
   * Processes the list of Instructions
   */
  private Map<BuySell, Map<Date, List<Instruction>>> processData(List<Instruction> instructions) {

    // perform some processing on the instructions
    instructions.stream().forEach(instructionConsumer);

    // create map of BuySell Instructions and then arranged by Dates.
    return instructions.stream()
        .collect(Collectors.groupingBy(Instruction::getBuySell,
        Collectors.groupingBy(Instruction::getSettlementDate)));
  }

  /*
   * Calculates the amount of settled outgoing and incoming from the Instructions
   */
  private void generateOutgoingReport(Map<BuySell, Map<Date, List<Instruction>>> byType) {

    for (Entry<BuySell, Map<Date, List<Instruction>>> entryByType : byType.entrySet()) {
      
      if (BuySell.BUY.equals(entryByType.getKey())) {
        System.out.println("== Amount in USD settled outgoing by date ==");
      } else {
        System.out.println("== Amount in USD settled incoming by date ==");
      }
      
      for (Entry<Date, List<Instruction>> entry : entryByType.getValue().entrySet()) {
        
        List<Instruction> instructions = entry.getValue();
  
        double totalValue = instructions.stream()
            .mapToDouble(Instruction::getTradeAmount)
            .sum();
  
        String prettyPrintDate = ReportGeneratorUtils.DATE_FORMAT.format(entry.getKey());
  
        System.out.println(prettyPrintDate + " Total: " 
        		+ ReportGeneratorUtils.DECIMAL_FORMAT.format(totalValue));
      }
      System.out.println();
    }
  }

  /*
   * Generates the list of rankings for outgoing or incoming based on trade amount.
   * The highest trade amount will rank 1.
   */
  private void generateRankingsOutgoing(Map<BuySell, Map<Date, List<Instruction>>> byType) {

    for (Entry<BuySell, Map<Date, List<Instruction>>> entryByType : byType.entrySet()) {

      Map<Date, List<Instruction>> instructionsByDate = entryByType.getValue();

      if (BuySell.BUY.equals(entryByType.getKey())) {
        System.out.println("== Ranking of entities based on outgoing amount ==");
      } else {
        System.out.println("== Ranking of entities based on incoming amount ==");
      }

      // concatenate all lists together so we can find the rank
      List<Instruction> concatenated = instructionsByDate.values().stream()
          .flatMap(Collection::stream).collect(Collectors.toList());

      // sort the collection in order of trade amount
      Collections.sort(concatenated,
          (i1, i2) -> i2.getTradeAmount().compareTo(i1.getTradeAmount()));

      concatenated.stream().forEachOrdered(System.out::println);

      System.out.println();
    }
  }
}
