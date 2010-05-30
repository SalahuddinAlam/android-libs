package net.kazed.android.geo.format;

import java.text.DecimalFormat;

import net.kazed.android.geo.parser.CoordinateParser;

public class DegreesMinutesCoordinateFormatter extends CoordinateFormatter {

   private CoordinateParser parser = new CoordinateParser();
   private CoordinateParser.DegreesMinutes parsedValue = new CoordinateParser.DegreesMinutes();
	private DecimalFormat format = new DecimalFormat("00.000");

	public void format(StringBuilder builder, double coordinate) {
      parser.parse(coordinate, parsedValue);
		if (!parsedValue.positive) {
			builder.append("-");
		}
		builder.append(parsedValue.degrees);
		builder.append('\u00B0');
		builder.append(format.format(parsedValue.minutes));
		builder.append("\'");
	}
}
