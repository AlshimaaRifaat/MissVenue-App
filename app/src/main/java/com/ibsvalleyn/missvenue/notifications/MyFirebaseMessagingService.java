package com.ibsvalleyn.missvenue.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.MainActivity;

import java.util.Map;

/**
 * Created by MAGIC on 5/27/2018.
 */


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public MyFirebaseMessagingService() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {

            Map<String, String> data = remoteMessage.getData();

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            long notificationId = System.currentTimeMillis();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            int userId = Integer.parseInt(data.get("user_id"));
            intent.putExtra("user_id", userId);

            Log.e("PlayerDetails","data.get(user_id) "+userId);
            PendingIntent contentIntent = PendingIntent.getActivity(this, (int) (Math.random() * 100), intent, 0);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setTicker(data.get("title"))
                    .setSmallIcon(R.drawable.home)
                    .setContentTitle(data.get("title"))
                    .setContentText(data.get("body"))
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                    .setContentIntent(contentIntent);
            notificationManager.notify((int) notificationId, notificationBuilder.build());
        }
    }

}
