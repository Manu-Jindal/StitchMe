package com.manujindal.stichme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

/**
 * Created by manu on 10/7/17.
 */



public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        long OrderId = intent.getLongExtra("OrderId1", -1);
        String source = intent.getStringExtra("source1");
        String type = intent.getStringExtra("type1");
        String CustomerName = intent.getStringExtra("CustomerName1");
        String OrderNumber = intent.getStringExtra("OrderNumber1");
        String day = intent.getStringExtra("day");
        Log.i("OrderNumber1", OrderNumber);

        Intent notificationIntent = new Intent(context, DetailedRecord.class);
        notificationIntent.putExtra("OrderId1", OrderId);
        notificationIntent.putExtra("source1", "notification");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(DetailedRecord.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);


        Notification notification;
        if(type.equals("trial"))
        {
            notification = builder.setContentTitle("Trial Alert")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Trial for Order#"+OrderNumber+" ("+CustomerName+") is scheduled for "+day+". Click for order info."))
                    .setContentText("Trial for Order#"+OrderNumber+" ("+CustomerName+") is scheduled for "+day+". Click for order info.")
                    .setTicker("New Trial Alert!")
                    .setSmallIcon(R.drawable.element)
                    .setContentIntent(pendingIntent).build();
            if(day.equals("today"))
            {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(OrderId+100000000 , notification);
            }
            else
            {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(OrderId+200000000 , notification);
            }
        }
        else
        {
            notification = builder.setContentTitle("Delivery Alert")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Delivery for Order#"+OrderNumber+" ("+CustomerName+") is scheduled for "+day+". Click for order info."))
                    .setContentText("Delivery for Order#"+OrderNumber+" ("+CustomerName+") is scheduled for "+day+". Click for order info.")
                    .setTicker("New Trial Alert!")
                    .setSmallIcon(R.drawable.element)
                    .setContentIntent(pendingIntent).build();
            if(day.equals("today"))
            {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(OrderId+1000000000, notification);
            }
            else
            {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(OrderId+2000000000, notification);
            }

        }

    }
}