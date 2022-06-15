package dev.eroberts.term_tracker.ViewModel;
import android.content.Context;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.app.NotificationManager;
import android.widget.Toast;
import android.os.Build;
import android.app.NotificationChannel;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import dev.eroberts.term_tracker.R;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * The type Notification receiver.
 */
public class notification_receiver extends BroadcastReceiver {
    /**
     * The Notification id.
     */
    static int notification_id;
    /**
     * The Channel id.
     */
    String channel_id= "Test";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,intent.getStringExtra("key"),Toast.LENGTH_LONG).show();
        createNotificationChannel(context,channel_id);
        Notification n= new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_school_white_24dp)
                .setContentText(intent.getStringExtra("key"))
                .setContentTitle("Term Tracker Reminder!").build();
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notification_id++, n);
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification Channel";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
