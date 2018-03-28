package com.chandima.chandimadbtasklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TaskDBAdapter {


	private static final String DB_NAME = "my_task.db";
	private static final String DB_TABLE = "tasks";
	
	 public static final String COL_TITLE = "title";
     public static String COL_ID = "_id";
     public static String COL_CATEGORY = "category";
     public static String COL_DESCRIPTION = "description";
     public static String COL_DATESTRING = "dateString";
     public static String COL_PRIORITY = "priority";
     public static String COL_STATUS = "status";
	
	private static final int DB_VERSION = 1;
	
	
	//It is important to have the _id column as the ListView automatically provides 
	//that value back on its event handler method
	 private static final String DB_TABLE_CREATE = 
	            "CREATE TABLE tasks (_id integer primary key autoincrement, "
	            + "title text not null,"
	            + "description text not null,"
	            + "status text not null,"
	            + "dateString text not null,"
	            + "category text not null,"
	            + "priority text not null);";

	 private SQLiteDatabase db;
	 private DBHelper dbHelper;
	 private Context context;
	 

	 public TaskDBAdapter(Context ctx) {
		 context = ctx;
	 }
	 
	 public TaskDBAdapter open() throws SQLException {
		 dbHelper = new DBHelper(context);
         db = dbHelper.getWritableDatabase();
		 return this;
	 }
	 
	 
	 public long createTask(String title, String category, String description, String dateString, String priority, String status) {
		 ContentValues cv = new ContentValues();
		 cv.put(COL_TITLE, title);
		 cv.put(COL_CATEGORY, category);
		 cv.put(COL_DESCRIPTION, description);
		 cv.put(COL_DATESTRING, dateString);
		 cv.put(COL_PRIORITY, priority);
		 cv.put(COL_STATUS, status);
		 
		 return db.insert(DB_TABLE, null, cv);
	 }
	 
	 public Cursor getAllTasks()
     {
         Cursor c = db.query(DB_TABLE, new String[] { COL_ID, COL_TITLE, COL_CATEGORY, COL_DESCRIPTION, COL_DATESTRING, COL_PRIORITY, COL_STATUS }, null, null, null, null, null);
         if (c != null)
        	 c.moveToFirst();
         return c;
     }
	 
	 public Cursor getCompletedTasks()
     {
         Cursor c = db.query(DB_TABLE, new String[] { COL_ID, COL_TITLE, COL_CATEGORY, COL_DESCRIPTION, COL_DATESTRING, COL_PRIORITY, COL_STATUS }, "status=completed", null, null, null, null);
         if (c != null)
        	 c.moveToFirst();
         return c;
     }
	 
	 public boolean updateTask(long rowId, String title, String category, String description, String dateString, String priority, String status)
     {
		 ContentValues cv = new ContentValues();
		 cv.put(COL_TITLE, title);
		 cv.put(COL_CATEGORY, category);
		 cv.put(COL_DESCRIPTION, description);
		 cv.put(COL_DATESTRING, dateString);
		 cv.put(COL_PRIORITY, priority);
		 cv.put(COL_STATUS, status);

         return db.update(DB_TABLE, cv, COL_ID + "=" + rowId, null) > 0;
     }
	 
	 
	 public Cursor getTask(long rowId) {
		 Cursor c = db.query(DB_TABLE, new String[] { COL_ID, COL_TITLE, COL_CATEGORY, COL_DESCRIPTION, COL_DATESTRING, COL_PRIORITY, COL_STATUS  }, COL_ID + "=" + rowId, null, null, null, null);
         if (c != null)
        	 c.moveToFirst();
         return c;
	 }
	 
	 public boolean deleteTask(long rowId) {
		 return db.delete(DB_TABLE, COL_ID + "=" + rowId, null) > 0;
	 }
	 
	 
	 
	 public void close() {
		 if (dbHelper != null)
			 dbHelper.close();
	 }
	 	 
	 
	 
	 //Database Helper class
	 private static class DBHelper extends SQLiteOpenHelper {
		 
		 DBHelper(Context ctx) {
			 super(ctx, DB_NAME, null, DB_VERSION);
		 }
		 
		 @Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_TABLE_CREATE);			
		}
		 
		 @Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS notes");
			onCreate(db);			
		}
	 }

}
