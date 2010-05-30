package net.kazed.android.geo.format;

import java.text.DecimalFormat;

public class DegreesCoordinateFormatter extends CoordinateFormatter {

	private DecimalFormat format = new DecimalFormat("00.000000");

	public void format(StringBuilder builder, double coordinate) {
      builder.append(format.format(coordinate));
		builder.append('\u00B0');
	}
}
