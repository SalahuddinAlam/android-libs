package net.kazed.android.geo.format;

import android.location.Location;

public abstract class CoordinateFormatter {

	public String formatFull(Location location) {
		return formatFull(location, "  ");
	}

	public String formatFull(Location location, String separator) {
		StringBuilder builder = new StringBuilder();
		format(builder, location.getLatitude());
		builder.append(" N");
		builder.append(separator);
		format(builder, location.getLongitude());
		builder.append(" E");
		return builder.toString();
	}

	public String format(double coordinate) {
		StringBuilder builder = new StringBuilder();
		format(builder, coordinate);
		return builder.toString();
	}

	public abstract void format(StringBuilder builder, double coordinate);
}
