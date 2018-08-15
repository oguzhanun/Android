package moguzhanun.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class DailySelfieAlarm extends BroadcastReceiver{

    private static final int MY_NOTIFICATION_ID = 1;
    private final CharSequence tickerText = "It's time to take a selfie!";
    private final CharSequence contentTitle = "Selfie Time";
    private final CharSequence contentText = "Take a better selfie please!!!";
    private Intent mNotificationIntent;
    private PendingIntent mContentIntent;
    //private final long[] mVibratePattern = { 0, 200, 200, 300 };

    @Override
    public void onReceive(Context context, Intent intent) {

        mNotificationIntent = new Intent(context, MainActivity.class);

        mContentIntent = PendingIntent.getActivity(context, 0,
                mNotificationIntent, FLAG_ACTIVITY_NEW_TASK);

        Notification.Builder notificationBuilder = new Notification.Builder(
                context).setTicker(tickerText)
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .setAutoCancel(true).setContentTitle(contentTitle)
                .setContentText(contentText).setContentIntent(mContentIntent);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(MY_NOTIFICATION_ID,
                notificationBuilder.build());
    }
}
