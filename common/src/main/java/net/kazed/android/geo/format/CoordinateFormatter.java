package net.kazed.android.geo.format;

import android.location.Location;

/**
 * Formats GPS coordinates.
 * @author Koert Zeilstra
 */
public abstract class CoordinateFormatter {

   /**
    * Format full location (N E).
    * @param location Location.
    * @return Formatted location.
    */
	public String formatFull(Location location) {
		return formatFull(location, "  ");
	}

   /**
    * Format full location (N E).
    * @param location Location.
    * @param separator Separator between N and E.
    * @return Formatted location.
    */
	public String formatFull(Location location, String separator) {
		StringBuilder builder = new StringBuilder();
		format(builder, location.getLatitude());
		builder.append(" N");
		builder.append(separator);
		format(builder, location.getLongitude());
		builder.append(" E");
		return builder.toString();
	}

	/**
	 * Format single coordinate element (degrees).
	 * @param coordinate Number of degrees.
	 * @return Formatted degrees.
	 */
	public String format(double coordinate) {
		StringBuilder builder = new StringBuilder();
		format(builder, coordinate);
		return builder.toString();
	}

	/**
	 * Format degrees into builder.
	 * @param builder Buffer to add formatted value to.
	 * @param coordinate Number of degrees.
	 */
	public abstract void format(StringBuilder builder, double coordinate);
}
