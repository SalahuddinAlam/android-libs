package net.kazed.android.geo.input;

import android.content.Context;

public abstract class CoordinateInput {

   public abstract void resetValue();

   public abstract void setValue(double value);

   public abstract double getValue();

   /**
    * @return True if entry field(s) are empty.
    */
   public abstract boolean isEmpty();

   /**
    * Validate coordinate input.
    * @param context Android context.
    * @return True if input is valid.
    */
   public abstract boolean validate(Context context);
   
   /**
    * Request input focus.
    */
   public abstract boolean requestFocus();
   
   /**
    * @return True if this component has focus.
    */
   public abstract boolean hasFocus();
   
   public static class CoordinateBoundary {
      private int boundaryErrorId;
      private double minimum;
      private double maximum;
      
      public CoordinateBoundary(int boundaryErrorId, double minimum, double maximum) {
         super();
         this.boundaryErrorId = boundaryErrorId;
         this.minimum = minimum;
         this.maximum = maximum;
      }
      
      public boolean isInRange(double value) {
         return value >= minimum && value <= maximum;
      }

      public int getBoundaryErrorId() {
         return boundaryErrorId;
      }
      
   }

}
