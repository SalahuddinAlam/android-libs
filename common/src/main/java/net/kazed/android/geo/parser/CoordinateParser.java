package net.kazed.android.geo.parser;

public class CoordinateParser {

   public void parse(double value, DegreesMinutes parsedValue) {
      if (value < 0) {
         parsedValue.degrees = Math.abs((int) value);
         parsedValue.positive = false;
      } else {
         parsedValue.degrees = (int) value;
         parsedValue.positive = true;
      }
      if (parsedValue.positive) {
         parsedValue.minutes = (value - parsedValue.degrees) * 60;
      } else {
         parsedValue.minutes = Math.abs((value + parsedValue.degrees) * 60);
      }
   }
   
   public void parse(double value, DegreesMinutesSeconds parsedValue) {
      if (value < 0) {
         parsedValue.degrees = Math.abs((int) value);
         parsedValue.positive = false;
      } else {
         parsedValue.degrees = (int) value;
         parsedValue.positive = true;
      }
      double minutesValue = 0.0;
      if (parsedValue.positive) {
         minutesValue = (value - parsedValue.degrees) * 60;
      } else {
         minutesValue = Math.abs((value + parsedValue.degrees) * 60);
      }
      parsedValue.minutes = (int) minutesValue;
      parsedValue.seconds = (minutesValue - parsedValue.minutes) * 60;
   }
   
   public static class DegreesMinutes {
      public boolean positive;
      public int degrees;
      public double minutes;
   }
   
   public static class DegreesMinutesSeconds {
      public boolean positive;
      public int degrees;
      public int minutes;
      public double seconds;
   }
}
