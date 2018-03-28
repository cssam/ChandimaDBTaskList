package com.chandima.chandimadbtasklist;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class TaskListFragment extends ListFragment {
	
	private TaskDBAdapter taskDBAdapter;
	SimpleCursorAdapter cursorAdapter;
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {	
		super.onActivityCreated(savedInstanceState);
		taskDBAdapter = new TaskDBAdapter(getActivity());
		taskDBAdapter.open();
		Cursor c = taskDBAdapter.getAllTasks();
		cursorAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, c, new String[] {"title", "description", "dateString", "status", "category", "priority"},
				new int[] { android.R.id.text1, android.R.id.text2}, 0);
		setListAdapter(cursorAdapter);
	}
}
