package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditRecord extends AppCompatActivity {
    Intent intent;
    SQLiteDatabase database;
    int id;
    String columnName;
    EditText display;
    int position;
    String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        intent = getIntent();
        id = intent.getIntExtra("id1", -1);
        position = intent.getIntExtra("position1", -1);
        source = intent.getStringExtra("source1");
        columnName = intent.getStringExtra("columnName1");
        display = (EditText) findViewById(R.id.display);
        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);
        Cursor c = database.rawQuery("SELECT "+columnName+" FROM abcd11 WHERE id="+id, null);
        c.moveToFirst();
        display.setText(c.getString(c.getColumnIndex(columnName)));
    }
    public void saveChanges(View view) {
        Intent intent = new Intent(this, DetailedRecord.class);
        String display1 = display.getText().toString();
        try {
            String sql = "UPDATE abcd11 SET "+columnName+" = '"+display1+"' WHERE id = "+id;
            database.execSQL(sql);
            if(source.equals("finalorder"))
            {
                intent.putExtra("id1", id);
                intent.putExtra("source1", "finalorder");
            }
            if(source.equals("allRecords"))
            {
                intent.putExtra("position1", position);
                intent.putExtra("source1", "allRecords");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        startActivity(intent);
    }
}

