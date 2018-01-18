package forrester.lynn.trade.report.input;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import forrester.lynn.trade.report.model.Instruction;

/**
 * Reads the Json file specified at the location
 * 
 * @author Lynn Forrester
 */
public class JsonLoader {

  private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
  
  /**
   * Reads the contents of the JSON file
   * @param fileLocation Location of the file in the classpath
   * 
   * @return the list containing all the Instructions
   */
  public List<Instruction> readJson(String fileLocation) {

    ObjectMapper objectMapper = new ObjectMapper();

    try {
      // need to tell the parser the format of the dates and how to read enums
      objectMapper.setDateFormat(dateFormat);
      objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);

      InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileLocation);

      return objectMapper.readValue(in, new TypeReference<List<Instruction>>(){});
      
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return new ArrayList<>();
  }
}
