package com.manujindal.stichme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }
    public void datewise(View view)
    {
        Intent intent = new Intent(this, DashboardChooseDate.class);
        startActivity(intent);
    }
    public void statuswise(View view)
    {
        Intent intent = new Intent(this, DashboardOrderStatus.class);
        startActivity(intent);
    }
}
