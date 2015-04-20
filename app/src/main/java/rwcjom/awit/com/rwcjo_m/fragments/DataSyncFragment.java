package rwcjom.awit.com.rwcjo_m.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataSyncFragment extends Fragment {
    private String randomCode;
    private String username;

    private TextView loginedUsername;

    public DataSyncFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        EventBus.getDefault().post(new MainActivityEvent("数据同步"));
        randomCode=getArguments().getString("randomCode");
        username=getArguments().getString("username");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_data_sync, container, false);
        loginedUsername=(TextView)view.findViewById(R.id.logined_username);
        loginedUsername.setText(username);
        return view;
    }


}
