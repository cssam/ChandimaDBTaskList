package com.chandima.chandimadbtasklist;


import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;

import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class TaskList extends ListActivity {

	private TaskDBAdapter taskDBAdapter;
	private SimpleCursorAdapter cursorAdapter;
	
	public static final int NOTES_ACTIVITY_REQUEST_CODE = 100; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);
		// Show the Up button in the action bar.

	}

	private void bindListWithNotes() {
		Cursor cursor = taskDBAdapter.getAllTasks();
		cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, 
				new String[] {"title", "description", "dateString", "status", "category", "priority"},
				new int[] { android.R.id.text1, android.R.id.text2}, 0);
		setListAdapter(cursorAdapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//id represents the _id value of the underlying row
		Intent editNote = new Intent(this, EditTask.class);
		editNote.putExtra(taskDBAdapter.COL_ID, id);
		startActivityForResult(editNote, NOTES_ACTIVITY_REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {	
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == NOTES_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
			//rebind the data from the cursor
			bindListWithNotes();	
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_list, menu);
		return true;
	}

}
