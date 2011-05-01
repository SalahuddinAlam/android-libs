package net.kazed.android.geo.input;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

public class DegreesMinutesSecondsCoordinateInput extends CoordinateInput {

   private TextView degreesInput;
   private TextView minutesInput;
   private TextView secondsInput;
   private CoordinateBoundary boundary;
   private int minutesErrorId;
   private int secondsErrorId;

   public DegreesMinutesSecondsCoordinateInput(Dialog dialog, int degreesId, int minutesId, int secondsId, CoordinateBoundary boundary,
         int minutesErrorId, int secondsErrorId) {
      degreesInput = (TextView) dialog.findViewById(degreesId);
      minutesInput = (TextView) dialog.findViewById(minutesId);
      secondsInput = (TextView) dialog.findViewById(secondsId);
      this.boundary = boundary;
      this.minutesErrorId = minutesErrorId;
      this.secondsErrorId = secondsErrorId;
   }

   public DegreesMinutesSecondsCoordinateInput(Activity activity, int degreesId, int minutesId, int secondsId, CoordinateBoundary boundary,
         int minutesErrorId, int secondsErrorId) {
      degreesInput = (TextView) activity.findViewById(degreesId);
      minutesInput = (TextView) activity.findViewById(minutesId);
      secondsInput = (TextView) activity.findViewById(secondsId);
      this.boundary = boundary;
      this.minutesErrorId = minutesErrorId;
      this.secondsErrorId = secondsErrorId;
   }

   public void resetValue() {
      degreesInput.setText("");
      minutesInput.setText("");
      secondsInput.setText("");
   }

   public void setValue(double value) {
//      if (Math.abs(value) < 0.0001) {
//         degreesInput.setText("");
//         minutesInput.setText("");
//         secondsInput.setText("");
//      } else {
         int degrees = (int) value;
         double minutesValue = 60 * (value - degrees);
         int minutes = (int) minutesValue;
         double seconds = 60 * (minutesValue - minutes);
         if (degrees < 0) {
            minutes = Math.abs(minutes);
            seconds = Math.abs(seconds);
            degreesInput.setText(Integer.toString(degrees));
         } else if (minutes < 0) {
            minutes = Math.abs(minutes);
            degreesInput.setText("-" + Integer.toString(degrees));
         } else if (seconds < 0.0) {
            seconds = Math.abs(seconds);
            degreesInput.setText("-" + Integer.toString(degrees));
         } else {
            degreesInput.setText(Integer.toString(degrees));
         }
         minutesInput.setText(Integer.toString(minutes));
         DecimalFormat format = new DecimalFormat("0.####");
         secondsInput.setText(format.format(seconds));
//      }
   }

   public double getValue() {
      double value = 0.0;
      if (degreesInput.getText().toString().startsWith("-")) {
         value = getDegrees() - (double) getMinutes()/60 - (double) getSeconds()/3600;
      } else {
         value = getDegrees() + (double) getMinutes()/60 + (double) getSeconds()/3600;
      }
      return value;
   }

   /**
    * @return True if entry field(s) are empty.
    */
   public boolean isEmpty() {
      return degreesInput.getText().length() == 0 && minutesInput.getText().length() == 0;
   }

   public boolean validate(Context context) {
      boolean valid = false;
      if (!isMinutesValid()) {
         Toast.makeText(context, minutesErrorId, 5000).show();
      } else if (!isSecondsValid()) {
            Toast.makeText(context, secondsErrorId, 5000).show();
      } else if (!boundary.isInRange(getValue())) {
         Toast.makeText(context, boundary.getBoundaryErrorId(), 5000).show();
      } else {
         valid = true;
      }
      return valid;
   }

   public boolean requestFocus() {
      return degreesInput.requestFocus();
   }

   @Override
   public boolean hasFocus() {
      return degreesInput.hasFocus() || minutesInput.hasFocus() || secondsInput.hasFocus();
   }

   private boolean isMinutesValid() {
      int minutes = getMinutes();
      return (minutes >= 0 && minutes <= 60);
   }
      
   private boolean isSecondsValid() {
      double seconds = getSeconds();
      return (seconds >= 0 && seconds <= 60);
   }
      
   private int getDegrees() {
      String degreesString = degreesInput.getText().toString();
      int degrees = 0;
      if (degreesString != null && degreesString.length() > 0) {
         degrees = Integer.parseInt(degreesString);
      }
      return degrees;
   }

   private int getMinutes() {
      String minutesString = minutesInput.getText().toString();
      int minutes = 0;
      if (minutesString != null && minutesString.length() > 0) {
         minutes = Integer.parseInt(minutesString);
      }
      return minutes;
   }

   private double getSeconds() {
      String secondsString = secondsInput.getText().toString();
      double seconds = 0.0;
      if (secondsString != null && secondsString.length() > 0) {
         seconds = Double.parseDouble(secondsString);
      }
      return seconds;
   }

}
