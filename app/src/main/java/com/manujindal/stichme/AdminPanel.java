package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class AdminPanel extends AppCompatActivity {
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    public void viewFabrics(View view) {

        Intent intent = new Intent(this, FabricList.class);
        startActivity(intent);
    }

    public void viewCustomers(View view) {

        Intent intent = new Intent(this, CustomersList.class);
        intent.putExtra("source1", "customerList");
        startActivity(intent);
    }
    public void viewEmployees(View view) {
        Intent intent = new Intent(this, EmployeeList.class);
        startActivity(intent);
    }
    public void paymentStatus(View view) {

        Intent intent = new Intent(this, PaymentStatus.class);
        startActivity(intent);
    }

    public void viewDashboard(View view) {

        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
    public void goHome(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void appointments(View view) {

        Intent intent = new Intent(this, AllAppointments.class);
        startActivity(intent);
    }
    public void exportDB(View view) {

        createcsv("orders");
        createcsv("customers");
        createcsv("employees");
        createcsv("shirts");
        createcsv("suits");
        createcsv("trousers");
        createcsv("others");
        createcsv("measurements");
        createcsv("fabrics");

    }

    public void createcsv(String tablename)
    {

        try
        {
            Cursor c = database.rawQuery("select * from "+tablename, null);
            int rowcount = 0;
            int colcount = 0;
            File sdCardDir = Environment.getExternalStorageDirectory();
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour= calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);
            int sec = calendar.get(Calendar.SECOND);
            int milisec = calendar.get(Calendar.MILLISECOND);

            String filename = tablename+"Backup("+year+"-"+month+"-"+day+" "+hour+":"+minute+":"+sec+":"+milisec+").csv";
            File saveFile = new File(sdCardDir, filename);
            FileWriter fw = new FileWriter(saveFile);
            BufferedWriter bw = new BufferedWriter(fw);
            rowcount = c.getCount();
            colcount = c.getColumnCount();
            if(rowcount>0)
            {
                c.moveToFirst();
                for(int i=0; i<colcount; i++)
                {
                    if(i != colcount -1 )
                    {

                        bw.write(c.getColumnName(i) + ",");
                    }
                    else
                    {
                        bw.write(c.getColumnName(i));
                    }
                }
                bw.newLine();
                for(int i=0;i<rowcount;i++)
                {
                    c.moveToPosition(i);
                    for(int j=0; j<colcount; j++)
                    {
                        if(j != colcount -1)
                        {
                            if(c.getColumnName(j).toString().equals("Phone"))
                            {
                                bw.write(c.getString(j)+"\t,");
                            }
                            else
                            {
                                bw.write(c.getString(j)+",");
                            }
                        }
                        else
                        {
                            if(c.getColumnName(j).toString().equals("Phone"))
                            {
                                bw.write(c.getString(j)+"\t");
                            }
                            else
                            {
                                bw.write(c.getString(j));
                            }
                        }
                    }
                    bw.newLine();
                }
                bw.flush();
                c.close();
                Toast.makeText(getApplicationContext(), "Exported table "+tablename+" Successfully", Toast.LENGTH_SHORT).show();

            }
        }
        catch (Exception ex)
        {
            if(database.isOpen())
            {
                database.close();
                Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            }

        }
    }
}



               /*
               File outputFile = new File(Environment.getExternalStorageDirectory(), filename);

               //Uri uri = FileProvider.getUriForFile(AdminPanel.this, AdminPanel.this.getApplicationContext().getPackageName() + ".com.manujindal.stichme.provider", outputFile);

               Uri uri = Uri.fromFile(outputFile);

               String toNumber = "+91 9818020151"; // contains spaces.
               toNumber = toNumber.replace("+", "").replace(" ", "");

               Intent sendIntent = new Intent("android.intent.action.MAIN");
               sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
               sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
               sendIntent.setAction(Intent.ACTION_SEND);
               sendIntent.setPackage("com.whatsapp");
               sendIntent.setType("text/plain");
               startActivity(sendIntent);
               */

