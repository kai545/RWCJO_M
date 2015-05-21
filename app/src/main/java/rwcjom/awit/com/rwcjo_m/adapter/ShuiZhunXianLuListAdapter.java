package rwcjom.awit.com.rwcjo_m.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.dao.Line;

/**
 * Created by Fantasy on 15/4/9.
 */
public class ShuiZhunXianLuListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Map<String, Object>> data;

    public final class ViewHolder{
        public TextView line_name;
        public Button upload;
    }

    public ShuiZhunXianLuListAdapter(Context context,List<Map<String, Object>> data){
        this.mInflater = LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {

            holder=new ViewHolder();

            convertView = mInflater.inflate(R.layout.fragment_shuizhunxianlu_list_item, null);
            holder.line_name = (TextView)convertView.findViewById(R.id.line_name_tv);
            convertView.setTag(holder);

        }else {

            holder = (ViewHolder)convertView.getTag();
        }
        Line line=(Line)data.get(position).get("line");
        holder.line_name.setText(line.getLn());
        return convertView;
    }
}
