package com.example.song.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void notify1(View view) {
        NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, ResultActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("My Notification")
                .setContentText("Hello Notification")
                .setContentIntent(contentIntent)
                .setTicker("NOtification")
                .build();

        notifyMgr.notify(1, notification);
    }

    public void notify2(View view) {
        NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, ResultActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                // 下面三个属性是必须的，不然会报错
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("My Notification")
                .setContentText("Hello Notification" + System.currentTimeMillis())
                //设置action
                .setContentIntent(contentIntent)
                // 设置是否自动清除
                .setAutoCancel(true)
                .build();

        notifyMgr.notify(2, notification);
    }

    public void notify3(View view) {
        NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifyMgr.cancel(2);
    }
    public void notify4(View view) {
        NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifyMgr.cancelAll();
    }

    // 大图通知
    public void notify5(View view) {
        NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent dismissIntent = new Intent(this, ResultActivity.class);
        dismissIntent.setAction("取消");
        PendingIntent piDismiss = PendingIntent.getActivity(
                this, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeIntent = new Intent(this, ResultActivity.class);
        snoozeIntent.setAction("确定");
        PendingIntent piSnooze = PendingIntent.getActivity(
                this, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this)
                // 下面三个属性是必须的，不然会报错
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("My Notification")
                .setContentText("Hello Notification" + System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                // 4.1之前被忽略
                .setStyle(new NotificationCompat.BigTextStyle().bigText("aaaaaaaaaaaaaaaaaaaa" +
                        "ggggggggggggggggggggggg" +
                        "ggggggggggggggggggggggg" +
                        "gggggggggggggggggggggg" +
                        "ggggggggggggggggggggggggg" +
                        "ggggggggggggggggggggggggg" +
                        "hhhhhhhhhhhhhhhhhhhhhhh" +
                        "hhhhhhhhhhhhhhhhhhhh" +
                        "h" +
                        "a"))
                // 添加 action BUtton
                .addAction(R.drawable.icon, "Dismiss", piDismiss)
                .addAction(R.drawable.second, "snooze", piSnooze)
                .build();
        notifyMgr.notify(3, notification);
    }

    public void notify6(View view) {
        final NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, ResultActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder builder= new NotificationCompat.Builder(this);

        // 下面三个属性是必须的，不然会报错
        builder.setSmallIcon(R.drawable.icon)
            .setContentTitle("My Notification");
        new Thread(new Runnable() {
            @Override
            public void run() {
                int incr;
                for(incr = 0; incr <= 100; incr += 5) {
                    // false表示不适用模糊匹配
                    builder.setProgress(100, incr, false);
                    notifyMgr.notify(6, builder.build());
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {

                    }
                }
                builder.setContentText("Download complete")
                        .setProgress(0, 0, false);
                notifyMgr.notify(6, builder.build());
            }
        }).start();

    }

    // 浮动通知
    public void notify7(View view) {
        NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, ResultActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                // 下面三个属性是必须的，不然会报错
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("My Notification")
                .setContentText("Hello Notification" + System.currentTimeMillis())
                // 设置是否自动清除
                .setAutoCancel(true)
                // 浮动通知——两种方法
//                .setFullScreenIntent(contentIntent, false)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        notifyMgr.notify(7, notification);
    }
    // 自定义通知
    public void notify8(View view) {
        NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, ResultActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification_view);
        Notification notification = new NotificationCompat.Builder(this)
                // 下面三个属性是必须的，不然会报错
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("2222")
                .setContentText("Notification")
                .setContent(remoteView)
                .setAutoCancel(true)
                // 浮动通知——两种方法
//                .setFullScreenIntent(contentIntent, false)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        notifyMgr.notify(7, notification);
    }

}
