package com.fanxiaotong.client.utils;

import com.fanxiaotong.client.config.ConfigurationFiles;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;

public class Sho extends Thread {

	private AudioRecord audioRecord;
	private int bufferSize = 100;

	/**
	 * 采样率：音频的采样频率，每秒钟能够采样的次数，采样率越高，音质越高。 给出的实例是44100、22050、11025但不限于这几个参数。
	 * 例如要采集低质量的音频就可以使用4000、8000等低采样率。
	 */
	private static int SAMPLE_RATE_IN_HZ = 22050;
	private Handler handler;
	private int what;

	private boolean stop = false;
	private long lastTimeMillis = 0;
	private boolean canGetNewTime = true;

	// 到达该值之后 触发事件
	// private static int BLOW_BOUNDARY = 40;

	public Sho(Handler handler, int what) {
		super();
		bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
				AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
				SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_MONO,
				AudioFormat.ENCODING_PCM_16BIT, bufferSize);
		this.handler = handler;
		this.what = what;
	}

	public void stopShout() {
		canGetNewTime = true;
		stop = true;
	}

	public boolean isStop() {
		return stop;
	}

	@Override
	public void run() {

		stop = false;
		try {

			audioRecord.startRecording();

			// 用于读取的 buffer
			byte[] buffer = new byte[bufferSize];
			while (!stop) {

				int readSize = audioRecord.read(buffer, 0, bufferSize) + 1;
				int value = 0;
				for (int i = 0; i < buffer.length; i++) {
					value += Math.abs(buffer[i]);
					// value += buffer[i] * buffer[i];
				}
				int tmp = value / readSize;
				// size = Math.abs(tmp - 30) * 10;
				int size = (tmp * tmp) / 100;
				size = (size * size) / 8;
				// size = (int) ((Math.abs(value /readSize) / 100) >> 1);

				long costTime = 0;
				if (size > 32 && canGetNewTime) {
					lastTimeMillis = System.currentTimeMillis();
					canGetNewTime = false;
				} else if (size > 32) {
					costTime = System.currentTimeMillis() - lastTimeMillis;
				} else {
					canGetNewTime = true;
					lastTimeMillis = 0;
					costTime = 0;
				}


				Message msg = Message.obtain();

				Li.printlnLog("TimeMillis1---" + lastTimeMillis);
				Li.printlnLog("TimeMillis2---" + costTime);
				if (costTime > 300) {
					handler.sendEmptyMessage(ConfigurationFiles.MESSAGE_I_WANT_A_DISH);
					canGetNewTime = true;
				} else {
					msg.what = what;
					msg.arg2 = size;
					handler.sendMessage(msg);
				}
			}
			audioRecord.stop();
			audioRecord.release();
			bufferSize = 100;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}