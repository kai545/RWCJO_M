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

/**
 * Created by Fantasy on 15/4/9.
 */
public class ShuiZhunXianLuListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Map<String, Object>> data;

    public final class ViewHolder{
        public TextView lc,ln;
        public Button upload;
    }

    public ShuiZhunXianLuListAdapter(Context context,List<Map<String, Object>> data){
        this.mInflater = LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public int getCount() {
        return 0;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
