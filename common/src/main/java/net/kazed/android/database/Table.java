package net.kazed.android.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Table definition.
 */
public abstract class Table<T> {

    public static final String PACKAGE = Table.class.getPackage().getName();

    private String tableName;
    private String nullColumnHack;
    private Map<String, String> projectionMap = new HashMap<String, String>();
    private List<String> columnNames = new ArrayList<String>();
    private Map<String, Integer> nameToPosition;

    /**
     * Constructor.
     * @param tableName Table name.
     * @param nullColumnHack Name of a column that may contain a null value.
     */
    public Table(String tableName, String nullColumnHack) {
        super();
        this.tableName = tableName;
        this.nullColumnHack = nullColumnHack;
        nameToPosition = new HashMap<String, Integer>();
    }
    
    /**
     * @return Name of table.
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * Extract object from cursor.
     * @param cursor Query cursor.
     * @return Extracted object.
     */
    public abstract T extract(final Cursor cursor);
    
    /**
     * Insert modelObject values into collection.
     * @param modelObject Model object.
     * @return Content values.
     */
    public abstract ContentValues getValues(final T modelObject);

    /**
     * Add column
     * @param columnName Name of column.
     */
    public void addColumn(String columnName) {
        projectionMap.put(columnName, tableName + "." + columnName);
        nameToPosition.put(columnName, columnNames.size());
        columnNames.add(columnName);
    }
    
    /**
     * Add column
     * @param columnName Name of column.
     */
    public void addColumn(String tableName, String columnName) {
        projectionMap.put(columnName, tableName + "." + columnName);
        nameToPosition.put(columnName, columnNames.size());
        columnNames.add(columnName);
    }
    
    /**
     * Add column
     * @param columnName Name of column.
     */
    public void addColumn(String tableName, String columnName, String columnAlias) {
        projectionMap.put(columnAlias, tableName + "." + columnName);
        nameToPosition.put(columnAlias, columnNames.size());
        columnNames.add(columnAlias);
    }
    
    /**
     * @return List of column names.
     */
    public String[] getColumnNames() {
        String[] names = new String[columnNames.size()];
        return (String[]) columnNames.toArray(names);
    }
    
    /**
     * Get Boolean value of field.
     * @param cursor Database cursor.
     * @param fieldName Name of field.
     * @return Retrieved value, null if field value is null.
     */
    public Boolean getBoolean(Cursor cursor, String fieldName) {
    	Boolean result = null;
       
       int columnIndex = nameToPosition.get(fieldName);
       if (!cursor.isNull(columnIndex)) {
          int value = cursor.getInt(columnIndex);
          if (value == 0) {
        	  result = Boolean.FALSE;
          } else {
        	  result = Boolean.TRUE;
          }
       }
       return result;
    }
    
    /**
     * Get Integer value of field.
     * @param cursor Database cursor.
     * @param fieldName Name of field.
     * @return Retrieved value, null if field value is null.
     */
    public Integer getInteger(Cursor cursor, String fieldName) {
       Integer result = null;
       
       int columnIndex = nameToPosition.get(fieldName);
       if (!cursor.isNull(columnIndex)) {
          result = cursor.getInt(columnIndex);
       }
       return result;
    }
    
    /**
     * Get Integer value of field.
     * @param cursor Database cursor.
     * @param fieldName Name of field.
     * @param defaultValue Default value if database value is null.
     * @return Retrieved value, defaultValue if field value is null.
     */
    public Integer getInteger(Cursor cursor, String fieldName, int defaultValue) {
       Integer result = null;
       
       int columnIndex = nameToPosition.get(fieldName);
       if (cursor.isNull(columnIndex)) {
           result = defaultValue;
       } else {
           result = cursor.getInt(columnIndex);
       }
       return result;
    }
    
    /**
     * Get Long value of field.
     * @param cursor Database cursor.
     * @param fieldName Name of field.
     * @return Retrieved value, null if field value is null.
     */
    public Long getLong(Cursor cursor, String fieldName) {
        Long result = null;
       
       int columnIndex = nameToPosition.get(fieldName);
       if (!cursor.isNull(columnIndex)) {
          result = cursor.getLong(columnIndex);
       }
       return result;
    }
    
    /**
     * Get Double value of field.
     * @param cursor Database cursor.
     * @param fieldName Name of field.
     * @return Retrieved value, null if field value is null.
     */
    public Double getDouble(Cursor cursor, String fieldName) {
    	Double result = null;
       
       int columnIndex = nameToPosition.get(fieldName);
       if (!cursor.isNull(columnIndex)) {
          result = cursor.getDouble(columnIndex);
       }
       return result;
    }
    
    /**
     * Get Double value of field.
     * @param cursor Database cursor.
     * @param fieldName Name of field.
     * @param defaultValue Default value if database value is null.
     * @return Retrieved value, null if field value is null.
     */
    public Double getDouble(Cursor cursor, String fieldName, Double defaultValue) {
       Double result = null;
       
       int columnIndex = nameToPosition.get(fieldName);
       if (cursor.isNull(columnIndex)) {
          result = defaultValue;
       } else {
          result = cursor.getDouble(columnIndex);
       }
       return result;
    }
    
    /**
     * Get String value of field.
     * @param cursor Database cursor.
     * @param fieldName Name of field.
     * @param defaultValue Default value.
     * @return Retrieved value, defaultValue if field value is null.
     */
    public String getString(Cursor cursor, String fieldName, String defaultValue) {
       String result = null;
       
       int columnIndex = nameToPosition.get(fieldName);
       if (!cursor.isNull(columnIndex)) {
          result = cursor.getString(columnIndex);
       } else {
          result = defaultValue;
       }
       return result;
    }

    /**
     * Get String value of field.
     * @param cursor Database cursor.
     * @param fieldName Name of field.
     * @return Retrieved value, null if field value is null.
     */
    public String getString(Cursor cursor, String fieldName) {
       String result = null;
       
       if (!nameToPosition.containsKey(fieldName)) {
          throw new IllegalArgumentException("Column not found: " + fieldName);
       }
       int columnIndex = nameToPosition.get(fieldName);
       if (!cursor.isNull(columnIndex)) {
          result = cursor.getString(columnIndex);
       }
       return result;
    }

    /**
     * Query database.
     * @param db Database.
     * @param uri Item URI.
     * @param projection Selected columns.
     * @param selection Where clause.
     * @param selectionArgs Where clause.
     * @param orderByClause
     * @return
     */
    public Cursor query(SQLiteDatabase db, String[] projection, String selection,
                    String[] selectionArgs, String orderByClause) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tableName);
        qb.setProjectionMap(projectionMap);
        return qb.query(db, projection, selection, selectionArgs, null, null, orderByClause);
    }
    
    /**
     * Query database.
     * @param db Database.
     * @param uri Item URI.
     * @param projection Selected columns.
     * @param selection Where clause.
     * @param selectionArgs Where clause.
     * @param orderByClause
     * @return
     */
    public Cursor queryWithJoin(SQLiteDatabase db, String joinTables, String[] projection, String selection,
                    String[] selectionArgs, String orderByClause) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tableName + ", " + joinTables);
        qb.setProjectionMap(projectionMap);
        return qb.query(db, projection, selection, selectionArgs, null, null, orderByClause);
    }
    
    /**
     * Query database.
     * @param db Database.
     * @param uri Item URI.
     * @param projection Selected columns.
     * @param selection Where clause.
     * @param selectionArgs Where clause.
     * @param orderByClause
     * @return
     */
    public Cursor queryItem(SQLiteDatabase db, Uri uri, String[] projection, String selection,
                    String[] selectionArgs, String orderByClause) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tableName);
        qb.setProjectionMap(projectionMap);
        String where = null;
        String[] whereArgs = null;
        String idSegment = uri.getLastPathSegment();
        if (selection != null && selection.length() > 0) {
            where = selection + " and ";
        }
        if (selectionArgs == null) {
            whereArgs = new String[1];
        } else {
            whereArgs = new String[selectionArgs.length + 1];
            for (int i = 0; i < selectionArgs.length; i++) {
                whereArgs[i] = selectionArgs[i];
            }
        }
        where = BaseColumns._ID + " = ?";
        whereArgs[whereArgs.length-1] = idSegment;
        return qb.query(db, projection, where, whereArgs, null, null, orderByClause);
    }
    
    /**
     * Query database.
     * @param db Database.
     * @param id Item ID.
     * @param projection Selected columns.
     * @param selection Where clause.
     * @param selectionArgs Where clause.
     * @param orderByClause
     * @return
     */
    public T queryItemById(SQLiteDatabase db, long id, String[] projection) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tableName);
        qb.setProjectionMap(projectionMap);
        String where = BaseColumns._ID + " = ?";
        String[] whereArgs = {Long.toString(id)};
        Cursor cursor = qb.query(db, projection, where, whereArgs, null, null, null);
        T object = null;
        try {
           if (cursor.moveToFirst()) {
              object = extract(cursor);
           }
        } finally {
           if (cursor != null) {
              cursor.close();
           }
        }
        return object;
    }
    
    /**
     * Query database.
     * @param db Database.
     * @param projection Selected columns.
     * @param where Where clause.
     * @param whereArgs Where clause.
     * @param orderBy Order by clause.
     * @return
     */
    public T queryItem(final SQLiteDatabase db, final String[] projection, final String where, final String[] whereArgs, final String orderBy) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tableName);
        qb.setProjectionMap(projectionMap);
        Cursor cursor = qb.query(db, projection, where, whereArgs, null, null, orderBy);
        T object = null;
        try {
           if (cursor.moveToFirst()) {
              object = extract(cursor);
           }
        } finally {
           if (cursor != null) {
              cursor.close();
           }
        }
        return object;
    }
    
    /**
     * Query database.
     * @param db Database.
     * @param projection Selected columns.
     * @param where Where clause.
     * @param whereArgs Where clause.
     * @return
     */
    public T queryItem(final SQLiteDatabase db, final String[] projection, final String where, final String[] whereArgs) {
        return queryItem(db, projection, where, whereArgs, null);
    }
    
    /**
     * Query database.
     * @param db Database.
     * @param uri Item Uri.
     * @param projection Selected columns.
     * @param selection Where clause.
     * @param selectionArgs Where clause.
     * @param orderByClause
     * @return
     */
    public T queryItemByUri(SQLiteDatabase db, Uri uri, String[] projection) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tableName);
        qb.setProjectionMap(projectionMap);
        String where = BaseColumns._ID + " = ?";
        String idSegment = uri.getLastPathSegment();
        String[] whereArgs = {idSegment};
        Cursor cursor = qb.query(db, projection, where, whereArgs, null, null, null);
        T object = null;
        try {
           if (cursor.moveToFirst()) {
              object = extract(cursor);
           }
        } finally {
           if (cursor != null) {
              cursor.close();
           }
        }
        return object;
    }
    
    /**
     * Query database.
     * @param db Database.
     * @param projection Selected columns.
     * @param where Where clause.
     * @param whereArgs Where clause.
     * @param orderBy Order by clause.
     * @return List of retrieved items.
     */
    public List<T> queryItems(final SQLiteDatabase db, final String[] projection, final String where, final String[] whereArgs, final String orderBy) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tableName);
        qb.setProjectionMap(projectionMap);
        Cursor cursor = qb.query(db, projection, where, whereArgs, null, null, orderBy);
        List<T> list = new ArrayList<T>();
        try {
           boolean reading = cursor.moveToFirst();
           while(reading) {
              list.add(extract(cursor));
              reading = cursor.moveToNext();
           }
        } finally {
           if (cursor != null) {
              cursor.close();
           }
        }
        return list;
    }
    
    /**
     * Insert values into db.
     * @param db Database.
     * @param values Values of new record.
     * @return ID of created record.
     */
    public long insert(SQLiteDatabase db, ContentValues values) {
        return db.insert(tableName, nullColumnHack, values);
    }

    /**
     * Update values in db.
     * @param db Database.
     * @param uri Database item URI.
     * @param values Updated values.
     * @return Number of rows updated.
     */
    public int update(SQLiteDatabase db, Uri uri, ContentValues values) {
      String whereClause = BaseColumns._ID + " = ?";
      String idSegment = uri.getLastPathSegment();
      int count = db.update(tableName, values, whereClause, new String[] { idSegment });
      return count;
    }
    
    /**
     * Update values in db.
     * @param db Database.
     * @param uri Database item URI.
     * @param values Updated values.
     * @return Number of rows updated.
     */
    public int update(final SQLiteDatabase db, final long id, final ContentValues values) {
      String whereClause = BaseColumns._ID + " = ?";
      int count = db.update(tableName, values, whereClause, new String[] {Long.toString(id)});
      return count;
    }
    
    /**
     * Delete record from db.
     * @param db Database.
     * @param uri Database item URI.
     */
    public int delete(final SQLiteDatabase db, final Uri uri) {
    	String whereClause = BaseColumns._ID + " = ?";
        String idSegment = uri.getLastPathSegment();
        return delete(db, whereClause, new String[] {idSegment});
    }

    /**
     * Delete record from db.
     * @param db Database.
     * @param id Database item ID.
     */
    public int delete(final SQLiteDatabase db, final long id) {
      String whereClause = BaseColumns._ID + " = ?";
        return delete(db, whereClause, new String[] {Long.toString(id)});
    }

    /**
     * Delete record from db.
     * @param db Database.
     * @param whereClause Where clause.
     * @param whereArg Arguments for where clause.
     * @return Number of deleted records.
     */
    public int delete(final SQLiteDatabase db, final String whereClause, final String... whereArg) {
       return db.delete(tableName, whereClause, whereArg);
    }

}
