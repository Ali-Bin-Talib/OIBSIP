package com.example.to_doappwithlogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DBHelper db;
    int userId;
    ListView listView;
    TextView tvEmpty, tvWelcome;
    List<Task> tasks = new ArrayList<>();
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);
        if (userId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);
        db = new DBHelper(this);

        listView = findViewById(R.id.listView);
        tvEmpty = findViewById(R.id.tvEmpty);
        tvWelcome = findViewById(R.id.tvWelcome);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnLogout = findViewById(R.id.btnLogout);

        tvWelcome.setText("Hello, " + db.getUserName(userId));

        adapter = new TaskAdapter(this, tasks, new TaskAdapter.Listener() {
            @Override
            public void onToggle(Task task, boolean done) {
                db.setDone(task.id, done);
                loadTasks();
            }

            @Override
            public void onDelete(Task task) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete task?")
                        .setMessage(task.title)
                        .setPositiveButton("Delete", (d, w) -> {
                            db.deleteTask(task.id);
                            loadTasks();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> showAddDialog());

        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        loadTasks();
    }

    private void loadTasks() {
        tasks.clear();
        tasks.addAll(db.getTasks(userId));
        adapter.notifyDataSetChanged();
        tvEmpty.setVisibility(tasks.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void showAddDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(48, 24, 48, 0);

        EditText etTitle = new EditText(this);
        etTitle.setHint("Task name");
        layout.addView(etTitle);

        EditText etNotes = new EditText(this);
        etNotes.setHint("Notes (optional)");
        layout.addView(etNotes);

        new AlertDialog.Builder(this)
                .setTitle("Add Task")
                .setView(layout)
                .setPositiveButton("Add", (d, w) -> {
                    String title = etTitle.getText().toString().trim();
                    String notes = etNotes.getText().toString().trim();
                    if (title.isEmpty()) {
                        Toast.makeText(this, "Task name is required", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    db.addTask(userId, title, notes);
                    loadTasks();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}