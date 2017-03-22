package edu.csulb.android.canvasapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Mak on 3/12/2017.
 */

public class UnlockReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            Log.e("TICK", intent.getAction());
//            Toast.makeText()
        Intent notificationIntent = new Intent(context,MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context, 0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle("Canvas Application")
                    .setContentText("Tap To Open Application")
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);

        }

    }
}
