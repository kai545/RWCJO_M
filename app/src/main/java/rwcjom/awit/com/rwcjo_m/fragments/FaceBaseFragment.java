package rwcjom.awit.com.rwcjo_m.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fantasy on 15/4/24.
 */
public class FaceBaseFragment extends ListFragment {
    private ArrayAdapter<String> adapter;
    private List<String> data;
    private Context context;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            data.add("rose" + i);
        }
        manager =getFragmentManager();
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }

}
