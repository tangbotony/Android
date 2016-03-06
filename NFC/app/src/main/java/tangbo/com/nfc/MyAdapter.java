package tangbo.com.nfc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tangbo.com.nfc.bean.Friend;
/**
 * Created by tangbo on 16/2/2.
 */
public class MyAdapter extends BaseAdapter{
    private Context context;                        //运行上下文
    private List<Friend> listItems;    //商品信息集合
    private LayoutInflater listContainer;           //视图容器

    public MyAdapter(Context context, List<Friend> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文
        this.listItems = listItems;
    }


    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = listContainer.inflate(R.layout.item_friend,null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            holder.autograph = (TextView) convertView.findViewById(R.id.autograph);
            holder.dis = (TextView) convertView.findViewById(R.id.dis);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);//绑定ViewHolder对象
        }
        else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        holder.icon.setImageDrawable(listItems.get(position).iconUrl);
        holder.nickname.setText(listItems.get(position).nickname);
        holder.autograph.setText(listItems.get(position).autograph);
        holder.dis.setText(listItems.get(position).dis);
        holder.time.setText(listItems.get(position).time);
        return convertView;
    }
    class ViewHolder{
        public ImageView icon;
        public TextView nickname;
        public TextView autograph;
        public TextView dis;
        public TextView time;
    }
}








