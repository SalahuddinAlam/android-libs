package net.kazed.android.database;

import android.database.Cursor;

public class RecordQuery<T> {
   private final Table<T> table;
   private Cursor cursor;

   public RecordQuery(Table<T> table, Cursor cursor) {
      this.table = table;
      this.cursor = cursor;
   }

   /**
    * @return Cursor.
    */
   public Cursor getCursor() {
      return cursor;
   }

   /**
    * @return Record from first cursor position.
    */
   public T getRecord() {
      cursor.moveToFirst();
      return table.extract(cursor);
   }

   /**
    * @return Record from first cursor position.
    */
   public T getRecord(int position) {
      cursor.moveToPosition(position);
      return table.extract(cursor);
   }
}