package com.example.to_doappwithlogin;

public class Task {
    public int id;
    public String title;
    public String notes;
    public boolean done;

    public Task(int id, String title, String notes, boolean done) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.done = done;
    }
}