package rwcjom.awit.com.rwcjo_m.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.dao.SiteNews;

/**
 * Created by Fantasy on 15/4/23.
 */
public class SiteListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<SiteNews> data;

    public final class ViewHolder{
        public TextView siteName,startEndSite;
    }

    public SiteListAdapter(Context context,List<SiteNews> data){
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

            convertView = mInflater.inflate(R.layout.fragment_site_list_item, null);
            holder.siteName = (TextView)convertView.findViewById(R.id.siteName_tv);
            holder.startEndSite = (TextView)convertView.findViewById(R.id.startSite_endSite_tv);
            convertView.setTag(holder);

        }else {

            holder = (ViewHolder)convertView.getTag();
        }

        holder.siteName.setText(data.get(position).getSitename());
        holder.startEndSite.setText(data.get(position).getStartsite()+"~"+data.get(position).getEndsite());
        return convertView;
    }
}
