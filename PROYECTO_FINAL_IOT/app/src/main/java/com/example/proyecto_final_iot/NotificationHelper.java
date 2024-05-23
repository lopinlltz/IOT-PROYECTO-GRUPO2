package com.example.proyecto_final_iot;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import me.leolin.shortcutbadger.ShortcutBadger;

public class NotificationHelper {
    private static final String CHANNEL_ID = "my_channel_id";
    private static final String CHANNEL_NAME = "My Channel";
    private static NotificationManager notificationManager;

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            getNotificationManager(context).createNotificationChannel(channel);
        }
    }

    private static NotificationManager getNotificationManager(Context context) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public static void sendNotification(Context context, String title, String message) {
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notifpic)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();
        getNotificationManager(context).notify((int) System.currentTimeMillis(), notification);

        NotificacionManager.agregarNotificacion(new Notificacion(title, message));

        actualizarBadge(context, NotificacionManager.obtenerNotificaciones().size());
    }

    private static void actualizarBadge(Context context, int numeroNotificaciones) {
        ShortcutBadger.applyCount(context, numeroNotificaciones);
    }
}
