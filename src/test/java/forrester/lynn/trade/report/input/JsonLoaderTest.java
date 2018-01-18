package forrester.lynn.trade.report.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import forrester.lynn.trade.report.model.Instruction;

/**
 * Tests the JsonLoader class
 * 
 * @author Lynn Forrester
 */
public class JsonLoaderTest {

  private JsonLoader loader;
  
  @Before
  public void setUp(){
    loader = new JsonLoader();
  }
  
  /**
   * Tests the file loads correctly
   */
  @Test
  public void testLoadingFile(){
    List<Instruction> instructions = loader.readJson("jsonInstructions.json");
    
    assertFalse(instructions.isEmpty());
    assertEquals(2, instructions.size());
  }
}
