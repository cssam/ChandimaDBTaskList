package com.chandima.chandimadbtasklist;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static final String APP_TAG = "ChandimaDBTaskList";
	
	private TaskDBAdapter taskDBAdapter;
	private EditText txtTitle;
	private EditText editDescription;
	private EditText editDueDate;
	private Spinner editStatus;
	private Spinner editPriority;
	private Spinner editCategory;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		taskDBAdapter = new TaskDBAdapter(this);
		taskDBAdapter.open();
		
		setContentView(R.layout.activity_main);
		txtTitle = (EditText) findViewById(R.id.txtTitle);
		editDescription =  (EditText) findViewById(R.id.editDescription);
		editDueDate = (EditText) findViewById(R.id.editDueDate);
		editStatus = (Spinner) findViewById(R.id.editStatus);
		editPriority  = (Spinner) findViewById(R.id.editPriority);
		editCategory = (Spinner) findViewById(R.id.editCategory);
		
	}
	
	public void onClick(View v) {
		if (v.getId() == R.id.btnAdd) {
			//Add a note
			String title = txtTitle.getText().toString();
			String description = editDescription.getText().toString();
			String dueDate = editDueDate.getText().toString();
			String status = editStatus.getSelectedItem().toString();
			String priority = editPriority.getSelectedItem().toString();
			String category = editCategory.getSelectedItem().toString();
			
			
			try
			{
				taskDBAdapter.createTask(title, category, description, dueDate, priority, status);
				Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
				txtTitle.setText("");
				editDescription.setText("");
				editDueDate.setText("");
				//editStatus.setSelected(false);
			}
			catch (Exception exp)
			{
				Log.e(APP_TAG, "Failed to insert a note", exp);			
			}
		}
		else if (v.getId() == R.id.btnList) {
			//Display the list
			Intent showTasks = new Intent(this, TaskList.class);
			startActivity(showTasks);
		}
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
