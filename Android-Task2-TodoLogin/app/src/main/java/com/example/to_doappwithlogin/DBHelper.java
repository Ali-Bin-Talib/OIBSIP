package com.example.to_doappwithlogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "todo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, email TEXT UNIQUE, password TEXT)");
        db.execSQL("CREATE TABLE tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "user_id INTEGER, title TEXT, notes TEXT, done INTEGER DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }

    public static String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean emailExists(String email) {
        Cursor c = getReadableDatabase().rawQuery("SELECT id FROM users WHERE email=?", new String[]{email});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }

    public boolean registerUser(String name, String email, String password) {
        ContentValues v = new ContentValues();
        v.put("name", name);
        v.put("email", email);
        v.put("password", hash(password));
        return getWritableDatabase().insert("users", null, v) != -1;
    }

    public int loginUser(String email, String password) {
        Cursor c = getReadableDatabase().rawQuery("SELECT id FROM users WHERE email=? AND password=?",
                new String[]{email, hash(password)});
        int id = -1;
        if (c.moveToFirst()) id = c.getInt(0);
        c.close();
        return id;
    }

    public String getUserName(int userId) {
        Cursor c = getReadableDatabase().rawQuery("SELECT name FROM users WHERE id=?",
                new String[]{String.valueOf(userId)});
        String name = "";
        if (c.moveToFirst()) name = c.getString(0);
        c.close();
        return name;
    }

    public void addTask(int userId, String title, String notes) {
        ContentValues v = new ContentValues();
        v.put("user_id", userId);
        v.put("title", title);
        v.put("notes", notes);
        v.put("done", 0);
        getWritableDatabase().insert("tasks", null, v);
    }

    public List<Task> getTasks(int userId) {
        List<Task> list = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT id, title, notes, done FROM tasks WHERE user_id=? ORDER BY done ASC, id DESC",
                new String[]{String.valueOf(userId)});
        while (c.moveToNext()) {
            list.add(new Task(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3) == 1));
        }
        c.close();
        return list;
    }

    public void setDone(int taskId, boolean done) {
        ContentValues v = new ContentValues();
        v.put("done", done ? 1 : 0);
        getWritableDatabase().update("tasks", v, "id=?", new String[]{String.valueOf(taskId)});
    }

    public void deleteTask(int taskId) {
        getWritableDatabase().delete("tasks", "id=?", new String[]{String.valueOf(taskId)});
    }
}