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
        public TextView siteCode,siteName,startSite,endSite;
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
            holder.siteCode = (TextView)convertView.findViewById(R.id.siteCode_tv);
            holder.siteName = (TextView)convertView.findViewById(R.id.siteName_tv);
            holder.startSite = (TextView)convertView.findViewById(R.id.startSite_tv);
            holder.endSite = (TextView)convertView.findViewById(R.id.endSite_tv);
            convertView.setTag(holder);

        }else {

            holder = (ViewHolder)convertView.getTag();
        }

        holder.siteCode.setText(data.get(position).getSitecode());
        holder.siteName.setText(data.get(position).getSitename());
        holder.startSite.setText(data.get(position).getStartsite());
        holder.endSite.setText(data.get(position).getEndsite());
        return convertView;
    }
}
