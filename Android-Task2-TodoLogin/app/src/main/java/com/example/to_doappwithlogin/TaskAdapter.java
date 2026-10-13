package com.example.to_doappwithlogin;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    public interface Listener {
        void onToggle(Task task, boolean done);
        void onDelete(Task task);
    }

    private final Listener listener;

    public TaskAdapter(Context context, List<Task> tasks, Listener listener) {
        super(context, 0, tasks);
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }
        Task t = getItem(position);

        CheckBox cb = convertView.findViewById(R.id.cbDone);
        TextView tvNotes = convertView.findViewById(R.id.tvNotes);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        cb.setOnCheckedChangeListener(null);
        cb.setText(t.title);
        cb.setChecked(t.done);

        if (t.done) cb.setPaintFlags(cb.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        else cb.setPaintFlags(cb.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);

        if (t.notes == null || t.notes.isEmpty()) {
            tvNotes.setVisibility(View.GONE);
        } else {
            tvNotes.setVisibility(View.VISIBLE);
            tvNotes.setText(t.notes);
        }

        cb.setOnCheckedChangeListener((b, checked) -> listener.onToggle(t, checked));
        btnDelete.setOnClickListener(v -> listener.onDelete(t));
        return convertView;
    }
}