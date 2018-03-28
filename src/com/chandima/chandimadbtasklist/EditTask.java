package com.chandima.chandimadbtasklist;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class EditTask extends Activity {

	
	private TaskDBAdapter taskDBAdapter;
	private EditText txtTitle;
	private EditText editDescription;
	private EditText editDueDate;
	private Spinner editStatus;
	private Spinner editPriority;
	private Spinner editCategory;
	private long rowId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		
		txtTitle = (EditText) findViewById(R.id.txtTitle);
		editDescription =  (EditText) findViewById(R.id.editDescription);
		editDueDate = (EditText) findViewById(R.id.editDueDate);
		editStatus = (Spinner) findViewById(R.id.editStatus);
		editPriority  = (Spinner) findViewById(R.id.editPriority);
		editCategory = (Spinner) findViewById(R.id.editCategory);
		
		//Check for nulls
		rowId = getIntent().getExtras().getLong(taskDBAdapter.COL_ID, 0L);
		Log.d(MainActivity.APP_TAG, String.format("Rowd ID : %d passed from TaskList", rowId));
		
		taskDBAdapter = new TaskDBAdapter(this);
		taskDBAdapter.open();
		populateTaskDetails();
		
	}
	
	
	private void populateTaskDetails() {
		Cursor c = taskDBAdapter.getTask(rowId);
		if (c!= null) {
			String title = c.getString(c.getColumnIndex(taskDBAdapter.COL_TITLE));
			String description = c.getString(c.getColumnIndex(taskDBAdapter.COL_DESCRIPTION));
			String dueDate = c.getString(c.getColumnIndex(taskDBAdapter.COL_DATESTRING));
			String status = c.getString(c.getColumnIndex(taskDBAdapter.COL_STATUS));
			String category = c.getString(c.getColumnIndex(taskDBAdapter.COL_CATEGORY));
			String priority = c.getString(c.getColumnIndex(taskDBAdapter.COL_PRIORITY));
			
			txtTitle.setText(title);
			editDescription.setText(description);
			editDueDate.setText(dueDate);
			
			if(status.equals("Pending")){
				editStatus.setSelection(0, true);
			} else if(status.equals("Completed")){
				editStatus.setSelection(1, true);
			}

			if(category.equals("Work")){
				editCategory.setSelection(0);
			} else if(category.equals("Home")){
				editCategory.setSelection(1);
			} else if(category.equals("School")){
				editCategory.setSelection(2);
			} else if(category.equals("Personal")){
				editCategory.setSelection(3);
			}
			

			if(priority.equals("Low")){
				editPriority.setSelection(0);
			} else if(priority.equals("Med")){
				editPriority.setSelection(1);
			} else if(priority.equals("High")){
				editPriority.setSelection(2);
			}
			
			
			c.close();
		}
		
		
	}
	
	//Event handler for the button
	public void onUpdateTask(View v){
		String title = txtTitle.getText().toString();

		String description = editDescription.getText().toString();
		String dueDate = editDueDate.getText().toString();
		String status = editStatus.getSelectedItem().toString();
		String priority = editPriority.getSelectedItem().toString();
		String category = editCategory.getSelectedItem().toString();
		
		if (rowId != 0L)
			taskDBAdapter.updateTask(rowId, title, category, description, dueDate, priority, status);
		else {
		//Create a new note instead			
			rowId = taskDBAdapter.createTask(title, category, description, dueDate, priority, status);
		}
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_task, menu);
		return true;
	}

}
