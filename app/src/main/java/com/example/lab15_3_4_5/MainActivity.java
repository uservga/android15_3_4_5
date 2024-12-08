package com.example.musyanovichlab15_3;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void getAll(View view) {
        String[] projection = {
                FriendsContract.Columns._ID,
                FriendsContract.Columns.NAME,
                FriendsContract.Columns.EMAIL,
                FriendsContract.Columns.PHONE
        };
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(FriendsContract.CONTENT_URI,
                projection,
                null,
                null,
                FriendsContract.Columns.NAME);
        if (cursor != null) {
            Log.d(TAG, "count: " + cursor.getCount());
            // Перебір елементів
            while (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    Log.d(TAG, cursor.getColumnName(i) + ": " + cursor.getString(i));
                }
                Log.d(TAG, "=========================");
            }
            cursor.close();
        } else {
            Log.d(TAG, "Cursor is null");
        }
    }
    public void add(View view){
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(FriendsContract.Columns.NAME, "Sam");
        values.put(FriendsContract.Columns.EMAIL, "sam@gmail.com");
        values.put(FriendsContract.Columns.PHONE, "+13676254985");
        Uri uri = contentResolver.insert(FriendsContract.CONTENT_URI, values);
        Log.d(TAG, "Friend added");
    }
    public void update(View view){
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(FriendsContract.Columns.EMAIL, "sammy@gmail.com");
        values.put(FriendsContract.Columns.PHONE, "+55555555555");
        String selection = FriendsContract.Columns.NAME + " = 'Sam'";
        int count = contentResolver.update(FriendsContract.CONTENT_URI,
                values, selection, null);
        Log.d(TAG, "Friend updated");
    }
    public void delete(View view){
        ContentResolver contentResolver = getContentResolver();
        String selection = FriendsContract.Columns.NAME + " = ?";
        String[] args = {"Sam"};
        int count = contentResolver.delete(FriendsContract.CONTENT_URI,
                selection, args);
        Log.d(TAG, "Friend deleted");
    }
}
