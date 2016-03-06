package tangbo.com.nfc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ScanActivity extends AppCompatActivity {
    private TextView textTitle;
    private ImageView scanLine;
    private ImageView scanRay;
    private LinearLayout switchAera;
    private Button switchButton;
    private NfcAdapter nfcAdapter;
    private boolean flag;
    private int recoder=0;
    int[] location = new int[2];
    int[] location1 = new int[2];

    Timer timer;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };
    // handler类接收数据
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                starAnnimation();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        flag = false;
        textTitle = (TextView) findViewById(R.id.title);
        scanLine = (ImageView) findViewById(R.id.scan_line);
        scanRay = (ImageView) findViewById(R.id.scan_ray);
        switchAera = (LinearLayout) findViewById(R.id.switch_aera);
        switchButton = (Button) findViewById(R.id.switch_button);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        switchAera.setVisibility(View.INVISIBLE);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(
                        android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);
            }
        });
        if (nfcAdapter == null) {
            textTitle.setText("设备不支持NFC！");
            flag = true;
        }
        if(nfcAdapter != null&&!nfcAdapter.isEnabled()) {
            switchAera.setVisibility(View.VISIBLE);
        }else if(nfcAdapter != null&&nfcAdapter.isEnabled())
        {
            if(timer==null)
            {
                timer = new Timer();
            }
            timer.schedule(task, 500, 5000); // 1s后执行task,经过1s再次执行
        }
    }
    public void starAnnimation()
    {
        scanRay.getLocationOnScreen(location);
        scanLine.getLocationOnScreen(location1);
        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,Util.dip2px(ScanActivity.this,200-3));
        translateAnimation.setDuration(4000);
        scanLine.startAnimation(translateAnimation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(nfcAdapter != null&&!nfcAdapter.isEnabled()) {
            switchAera.setVisibility(View.VISIBLE);
        }else if(nfcAdapter != null && nfcAdapter.isEnabled())
        {
            switchAera.setVisibility(View.INVISIBLE);
            if(timer==null)
            {
                timer = new Timer();
                timer.schedule(task, 500, 5000); // 1s后执行task,经过1s再次执行
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null){
            timer.cancel();  //将原任务从队列中移除
        }
    }
}
