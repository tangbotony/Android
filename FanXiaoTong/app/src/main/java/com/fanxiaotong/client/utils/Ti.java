package com.fanxiaotong.client.utils;
import java.util.Random;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

public class Ti {
	// ≤•∑≈ƒ¨»œ¡Â…˘
	// ∑µªÿNotification id
	public static int PlaySound(final Context context) {
		NotificationManager mgr = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification nt = new Notification();
		nt.defaults = Notification.DEFAULT_SOUND;
		int soundId = new Random(System.currentTimeMillis())
				.nextInt(Integer.MAX_VALUE);
		mgr.notify(soundId, nt);
		return soundId;
	}
}