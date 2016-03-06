package tangbo.com.nfc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tangbo.com.nfc.bean.Friend;
import tangbo.com.nfc.card.pboc.PbocCard;

public class DetailInformation extends AppCompatActivity {
    ListView listView;
    MyAdapter myAdapter;
    List<Friend> friends=new ArrayList<>();
    Map<String,Integer> keyWords = new HashMap<>();
    ImageView keyValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        getData();
        iniData();
        myAdapter = new MyAdapter(DetailInformation.this,friends);
        listView = (ListView)findViewById(R.id.list);
        keyValue = (ImageView)findViewById(R.id.key_word);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DetailInformation.this,PersonalInformationActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void iniData()
    {
        Friend friend1 = new Friend();
        Friend friend2 = new Friend();
        Friend friend3 = new Friend();
        Friend friend4 = new Friend();
        Friend friend5 = new Friend();
        friend1.iconUrl=getResources().getDrawable(R.drawable.p1);
        friend2.iconUrl=getResources().getDrawable(R.drawable.p2);
        friend3.iconUrl=getResources().getDrawable(R.drawable.p3);
        friend4.iconUrl=getResources().getDrawable(R.drawable.p4);
        friend5.iconUrl=getResources().getDrawable(R.drawable.p5);
        friend1.time="1.2h";
        friend2.time="0.3h";
        friend3.time="1.7h";
        friend4.time="0.6h";
        friend5.time="2.2h";
        friend1.dis="0.4 km";
        friend2.dis="0.4 km";
        friend3.dis="4.5 km";
        friend4.dis="2.2 km";
        friend5.dis="1.5 km";

        friend1.nickname="LADDER";
        friend2.nickname="^ω^樱*^_^*&";
        friend3.nickname="lvv2";
        friend4.nickname="小波萝";
        friend5.nickname="漫步人生路";

        friend1.autograph="不负如来不负卿";
        friend2.autograph="爱一个人有错吗？";
        friend3.autograph="再坚持一下，就是黎明";
        friend4.autograph="永恒~~幸福~~";
        friend5.autograph="愿青春无怨且无悔";
        friends.add(friend1);
        friends.add(friend2);
        friends.add(friend3);
        friends.add(friend4);
        friends.add(friend5);


    }
    public void getData() {
        Toast.makeText(DetailInformation.this,"ID:"+ PbocCard.serl,Toast.LENGTH_SHORT).show();
    }
}
