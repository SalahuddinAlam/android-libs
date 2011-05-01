package net.kazed.android.geo.input;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class DegreesCoordinateInput extends CoordinateInput {

   private EditText degreesInput;
   private CoordinateBoundary boundary;

   @Deprecated
   public DegreesCoordinateInput(Dialog dialog, int degreesId, int minutesId, CoordinateBoundary boundary) {
      degreesInput = (EditText) dialog.findViewById(degreesId);
      this.boundary = boundary;
   }

   public DegreesCoordinateInput(Dialog dialog, int degreesId, CoordinateBoundary boundary) {
      degreesInput = (EditText) dialog.findViewById(degreesId);
      this.boundary = boundary;
   }

   @Deprecated
   public DegreesCoordinateInput(Activity activity, int degreesId, int minutesId, CoordinateBoundary boundary) {
      degreesInput = (EditText) activity.findViewById(degreesId);
      this.boundary = boundary;
   }

   public DegreesCoordinateInput(Activity activity, int degreesId, CoordinateBoundary boundary) {
      degreesInput = (EditText) activity.findViewById(degreesId);
      this.boundary = boundary;
   }

   public void resetValue() {
      degreesInput.setText("");
   }

   public void setValue(double value) {
//      if (Math.abs(value) < 0.0001) {
//         degreesInput.setText("");
//      } else {
         DecimalFormat format = new DecimalFormat("0.######");
         degreesInput.setText(format.format(value));
//      }
   }

   public double getValue() {
      return getDegrees();
   }

   /**
    * @return True if entry field(s) are empty.
    */
   public boolean isEmpty() {
      return degreesInput.getText().length() == 0;
   }

   public boolean validate(Context context) {
      boolean valid = false;
      if (!boundary.isInRange(getValue())) {
         Toast.makeText(context, boundary.getBoundaryErrorId(), 5000).show();
      } else {
         valid = true;
      }
      return valid;
   }

   @Override
   public boolean requestFocus() {
      return degreesInput.requestFocus();
   }

   @Override
   public boolean hasFocus() {
      return degreesInput.hasFocus();
   }

   public boolean isDegreesValid(int minimum, int maximum) {
      double degrees = getDegrees();
      return (degrees >= minimum && degrees <= maximum);
   }

   private double getDegrees() {
      String degreesString = degreesInput.getText().toString();
      double degrees = 0.0;
      if (degreesString != null && degreesString.length() > 0) {
         degrees = Double.parseDouble(degreesString);
      }
      return degrees;
   }

}
