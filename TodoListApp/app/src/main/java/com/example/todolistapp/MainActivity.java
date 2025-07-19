package com.example.todolistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.Listener {

    private final List<Task> taskList = new ArrayList<>();
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(taskList, this);
        rv.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabAddTask);
        fab.setOnClickListener(v -> showTaskDialog(null, -1));
    }

    private void showTaskDialog(final Task task, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_task, null);
        final EditText et = view.findViewById(R.id.editTextTaskName);
        if (task != null) {
            et.setText(task.getName());
            builder.setTitle(R.string.edit_task);
        } else {
            builder.setTitle(R.string.add_task);
        }
        builder.setView(view)
               .setPositiveButton(android.R.string.ok, (d, w) -> {
                   String name = et.getText().toString().trim();
                   if (name.isEmpty()) return;
                   if (task != null && pos >= 0) {
                       task.setName(name);
                       adapter.notifyItemChanged(pos);
                   } else {
                       taskList.add(new Task(name));
                       adapter.notifyItemInserted(taskList.size() - 1);
                   }
               })
               .setNegativeButton(android.R.string.cancel, null)
               .show();
    }

    @Override
    public void onEdit(int position) {
        showTaskDialog(taskList.get(position), position);
    }

    @Override
    public void onDelete(int position) {
        taskList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onTaskCompletionChanged(int position, boolean isCompleted) {
        // Task completion status is already updated in the adapter
        // This method can be used for additional logic like saving to database,
        // logging, analytics, etc.
        
        // For now, we'll just ensure the adapter is notified of the change
        // (though this is already handled in the adapter)
        adapter.notifyItemChanged(position);
    }
}