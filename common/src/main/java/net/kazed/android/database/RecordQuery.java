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
    * @return Record from current cursor position.
    */
   public T getCurrentRecord() {
      return table.extract(cursor);
   }

   /**
    * @return Record from first cursor position.
    */
   public T getRecord(int position) {
      cursor.moveToPosition(position);
      return table.extract(cursor);
   }

   public boolean hasNext() {
      return !cursor.isAfterLast();
   }

   public boolean moveToNext() {
      return cursor.moveToNext();
   }

   public boolean moveToFirst() {
      return cursor.moveToFirst();
   }

   public boolean moveToPosition(final int position) {
      return cursor.moveToPosition(position);
   }

   public int getCount() {
      return cursor.getCount();
   }
}