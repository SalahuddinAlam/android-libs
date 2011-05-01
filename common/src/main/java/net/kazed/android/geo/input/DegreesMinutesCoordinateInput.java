package net.kazed.android.geo.input;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

public class DegreesMinutesCoordinateInput extends CoordinateInput {

   private TextView degreesInput;
   private TextView minutesInput;
   private CoordinateBoundary boundary;
   private int minutesErrorId;

   public DegreesMinutesCoordinateInput(Dialog dialog, int degreesId, int minutesId, CoordinateBoundary boundary, int minutesErrorId) {
      degreesInput = (TextView) dialog.findViewById(degreesId);
      minutesInput = (TextView) dialog.findViewById(minutesId);
      this.boundary = boundary;
      this.minutesErrorId = minutesErrorId;
   }

   public DegreesMinutesCoordinateInput(Activity activity, int degreesId, int minutesId, CoordinateBoundary boundary, int minutesErrorId) {
      degreesInput = (TextView) activity.findViewById(degreesId);
      minutesInput = (TextView) activity.findViewById(minutesId);
      this.boundary = boundary;
      this.minutesErrorId = minutesErrorId;
   }

   public void resetValue() {
      degreesInput.setText("");
      minutesInput.setText("");
   }

   public void setValue(double value) {
//      if (Math.abs(value) < 0.0001) {
//         degreesInput.setText("");
//         minutesInput.setText("");
//      } else {
         int degrees = (int) value;
         double minutes = 60 * (value - degrees);
         if (degrees < 0.0) {
            minutes = Math.abs(minutes);
            degreesInput.setText(Integer.toString(degrees));
         } else if (minutes < 0.0) {
            minutes = Math.abs(minutes);
            degreesInput.setText("-" + Integer.toString(degrees));
         } else {
            degreesInput.setText(Integer.toString(degrees));
         }
         DecimalFormat format = new DecimalFormat("0.####");
         minutesInput.setText(format.format(minutes));
//      }
   }

   public double getValue() {
      int degrees = getDegrees();
      double minutes = getMinutes();
      if (degrees == 0) {
         if (degreesInput.getText().toString().startsWith("-")) {
            minutes = -minutes;
         }
      } else if (degrees < 0) {
         minutes = -minutes;
      }
      return degrees + minutes / 60;
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
      return degreesInput.hasFocus() || minutesInput.hasFocus();
   }

   private boolean isMinutesValid() {
      double minutes = getMinutes();
      return (minutes != Double.NaN && (minutes >= -60 && minutes <= 60));
   }
      
   private int getDegrees() {
      String degreesString = degreesInput.getText().toString();
      int degrees = 0;
      if (degreesString != null && degreesString.length() > 0) {
         try {
            degrees = Integer.parseInt(degreesInput.getText().toString());
         } catch (NumberFormatException e) {
            degrees = Integer.MIN_VALUE;
         }
      }
      return degrees;
   }

   private double getMinutes() {
      String minutesString = minutesInput.getText().toString();
      double minutes = 0.0;
      if (minutesString != null && minutesString.length() > 0) {
         try {
            minutes = Double.parseDouble(minutesString.replace(',', '.'));
         } catch (NumberFormatException e) {
            minutes = Double.NaN;
         }
      }
      return minutes;
   }

}
