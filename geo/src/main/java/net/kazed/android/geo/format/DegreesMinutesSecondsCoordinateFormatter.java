package net.kazed.android.geo.format;

import java.text.DecimalFormat;

import net.kazed.android.geo.parser.CoordinateParser;

public class DegreesMinutesSecondsCoordinateFormatter extends CoordinateFormatter {

   private CoordinateParser parser = new CoordinateParser();
   private CoordinateParser.DegreesMinutesSeconds parsedValue = new CoordinateParser.DegreesMinutesSeconds();
	private DecimalFormat format = new DecimalFormat("00.0");
	

	public void format(StringBuilder builder, double coordinate) {
	   parser.parse(coordinate, parsedValue);
      if (!parsedValue.positive) {
         builder.append("-");
      }
      builder.append(parsedValue.degrees);
      builder.append('\u00B0');
      builder.append(parsedValue.minutes);
      builder.append('\'');
      builder.append(format.format(parsedValue.seconds));
      builder.append("\"");
	}
}
