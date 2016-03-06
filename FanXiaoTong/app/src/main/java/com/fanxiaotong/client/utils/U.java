package com.fanxiaotong.client.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import com.fanxiaotong.client.fragment.Ri;
import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

public class U extends Thread {
    private String downloadUrl;
    private File saveFile;
    private String fileName;
    private Context context;
    private NotificationManager notificationManager;
    private Notification notification;// 
    private RemoteViews notificationViews;// 
    private Timer timer;// 定时器，用于更新下载进度
    private TimerTask task;// 定时器执行的任务

    private final int notificationID = 1;// 通知的id
    private final int updateProgress = 1;//
    private final int downloadSuccess = 2;// 下载成功
    private final int downloadError = 3;// 下载失败
    private Dow downLoadUtil;

    public U(String downloadUrl, String fileLocation, String fileName, Context context) {
        this.downloadUrl = downloadUrl;
        this.saveFile = new File(fileLocation);
        this.context = context;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        super.run();
        try {
            initNofication();
            handlerTask();
            downLoadUtil = new Dow();
            int downSize = downLoadUtil.downloadUpdateFile(downloadUrl, saveFile, fileName, callback,context);
            if (downSize == downLoadUtil.getRealSize() && downSize != 0) {
                handler.sendEmptyMessage(downloadSuccess);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initNofication() {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = R.drawable.ic_launcher;// 
        notification.tickerText = "正在下载，请稍后！";// 
        Intent intent = new Intent();
        intent.setClass(context, Ri.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
        notification.contentIntent = contentIntent;
        notificationViews = new RemoteViews(context.getPackageName(), R.layout.down_notification);
        notificationViews.setImageViewResource(R.id.download_icon, R.drawable.ic_launcher);
     
    }

    private void handlerTask() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(updateProgress);
            }
        };
        timer.schedule(task, 500, 500);
    }

    @SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == updateProgress) {// 更新下载进度
                int fileSize = downLoadUtil.getRealSize();
                int totalReadSize = downLoadUtil.getTotalSize();
                if (totalReadSize > 0) {
                    float size = (float) totalReadSize * 100 / (float) fileSize;
                    DecimalFormat format = new DecimalFormat("0.00");
                    String progress = format.format(size);
                    notificationViews.setTextViewText(R.id.progressTv, "已下了" + progress + "%");
                    notificationViews.setProgressBar(R.id.progressBar, 100, (int) size, false);
                    notification.contentView = notificationViews;
                    notificationManager.notify(notificationID, notification);
                }
            } else if (msg.what == downloadSuccess) {// 下载完成
                notificationViews.setTextViewText(R.id.progressTv, "下载完成");
                notificationViews.setProgressBar(R.id.progressBar, 100, 100, false);
                notification.contentView = notificationViews;
                notification.tickerText = "下载完成";
                notificationManager.notify(notificationID, notification);
                if (timer != null && task != null) {
                    timer.cancel();
                    task.cancel();
                    timer = null;
                    task = null;
                }
                // 安装apk
                Uri uri = Uri.fromFile(new File(saveFile + "/"+fileName));
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                // PendingIntent 通知栏跳转
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, installIntent, 0);
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                notification.contentIntent = pendingIntent;
                notification.contentView.setTextViewText(R.id.progressTv, "下载完成，点击安装！");
                notificationManager.notify(notificationID, notification);
           

            } else if (msg.what == downloadError) {// 下载失败
                if (timer != null && task != null) {
                    timer.cancel();
                    task.cancel();
                    timer = null;
                    task = null;
                }
                notificationManager.cancel(notificationID);

            }
        }

    };
    /**
     * 下载回调
     */
    Do callback = new Do() {

        @Override
        public void downloadError(String msg) {
            handler.sendEmptyMessage(downloadError);
        }
    };

}
