package net.kazed.android.geo;

import net.kazed.android.geo.parser.CoordinateParser;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test CoordinateParser.
 */
public class CoordinateParserTest {

   /**
    * Test parse(double, CoordinateParser.DegreesMinutes)
    */
   @Test
   public void testParse() {
      CoordinateParser parser = new CoordinateParser();
      CoordinateParser.DegreesMinutes parsedValue = new CoordinateParser.DegreesMinutes();
      parser.parse(0, parsedValue);
      
      Assert.assertEquals(true, parsedValue.positive);
      Assert.assertEquals(0, parsedValue.degrees);
      Assert.assertEquals(0.0, parsedValue.minutes, 0.00001);

      parser.parse(0.5, parsedValue);
      
      Assert.assertEquals(true, parsedValue.positive);
      Assert.assertEquals(0, parsedValue.degrees);
      Assert.assertEquals(30.0, parsedValue.minutes, 0.00001);

      parser.parse(1.5, parsedValue);
      
      Assert.assertEquals(true, parsedValue.positive);
      Assert.assertEquals(1, parsedValue.degrees);
      Assert.assertEquals(30.0, parsedValue.minutes, 0.00001);

      parser.parse(-0.5, parsedValue);
      
      Assert.assertEquals(false, parsedValue.positive);
      Assert.assertEquals(0, parsedValue.degrees);
      Assert.assertEquals(30.0, parsedValue.minutes, 0.00001);

      parser.parse(-1.5, parsedValue);
      
      Assert.assertEquals(false, parsedValue.positive);
      Assert.assertEquals(1, parsedValue.degrees);
      Assert.assertEquals(30.0, parsedValue.minutes, 0.00001);
   }

   /**
    * Test parse(double, CoordinateParser.DegreesMinutes)
    */
   @Test
   public void testParseSeconds() {
      CoordinateParser parser = new CoordinateParser();
      CoordinateParser.DegreesMinutesSeconds parsedValue = new CoordinateParser.DegreesMinutesSeconds();
      parser.parse(0, parsedValue);
      
      Assert.assertEquals(true, parsedValue.positive);
      Assert.assertEquals(0, parsedValue.degrees);
      Assert.assertEquals(0.0, parsedValue.minutes, 0.00001);
      Assert.assertEquals(0.0, parsedValue.seconds, 0.00001);

      parser.parse(0.5, parsedValue);
      
      Assert.assertEquals(true, parsedValue.positive);
      Assert.assertEquals(0, parsedValue.degrees);
      Assert.assertEquals(30.0, parsedValue.minutes, 0.00001);
      Assert.assertEquals(0.0, parsedValue.seconds, 0.00001);

      parser.parse(1.5, parsedValue);
      
      Assert.assertEquals(true, parsedValue.positive);
      Assert.assertEquals(1, parsedValue.degrees);
      Assert.assertEquals(30.0, parsedValue.minutes, 0.00001);
      Assert.assertEquals(0.0, parsedValue.seconds, 0.00001);

      parser.parse(-0.5, parsedValue);
      
      Assert.assertEquals(false, parsedValue.positive);
      Assert.assertEquals(0, parsedValue.degrees);
      Assert.assertEquals(30.0, parsedValue.minutes, 0.00001);
      Assert.assertEquals(0.0, parsedValue.seconds, 0.00001);

      parser.parse(-1.5, parsedValue);
      
      Assert.assertEquals(false, parsedValue.positive);
      Assert.assertEquals(1, parsedValue.degrees);
      Assert.assertEquals(30.0, parsedValue.minutes, 0.00001);
      Assert.assertEquals(0.0, parsedValue.seconds, 0.00001);
   }

}
