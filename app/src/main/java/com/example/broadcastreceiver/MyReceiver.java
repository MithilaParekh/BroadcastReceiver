package com.example.broadcastreceiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    String CHANNEL_NAME = "hi";
    String CHANNEL_ID = "hello";
     TextView textView;

    public MyReceiver(TextView textView) {
        this.textView = textView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("AIRPLANE MODE STATUS")
                    .setContentText("Airplane Mode changed")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            Intent notificationIntent = new Intent(context, MainActivity.class);
            PendingIntent c = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(c);

            NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            nm.notify(0, builder.build());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT);
                nm.createNotificationChannel(channel);
            }

       Toast.makeText(context, "Airplane Mode changed", Toast.LENGTH_SHORT).show();

        }
        int percentage=intent.getIntExtra("level",0);
        if(percentage!=0)
        {
            textView.setText(percentage+"%");
        }

    }
}
