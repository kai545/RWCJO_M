/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rwcjom.awit.com.rwcjo_m.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.adapter.ShuiZhunXianLuListAdapter;
import rwcjom.awit.com.rwcjo_m.dao.BwInfo;
import rwcjom.awit.com.rwcjo_m.dao.Line;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.service.BwInfoService;
import rwcjom.awit.com.rwcjo_m.service.LineService;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;

public class ShuiZhunXianLuFragment extends ListFragment {
	private static final String TAG = "ShuiZhunXianLuFragment";
	private static final String ARG_POSITION = "position";
	private int position;
	private String sectid;
	private List<Map<String,Object>> lineData=new ArrayList<Map<String,Object>>();
	private Context context;
	private FragmentManager manager;
	private FragmentTransaction transaction;
	private ShuiZhunXianLuListAdapter shuiZhunXianLuListAdapter;
	private LineService lineService;
	private BwInfoService bwInfoService;

	public static ShuiZhunXianLuFragment newInstance(int position,String sectid) {
		ShuiZhunXianLuFragment f = new ShuiZhunXianLuFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		b.putString("sectid", sectid);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context=activity;
		lineService=LineService.getInstance(context);
		bwInfoService=BwInfoService.getInstance(context);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "shuizhunfragment create!");
		position = getArguments().getInt(ARG_POSITION);
		sectid = getArguments().getString("sectid");
		manager = getFragmentManager();
		Tasks.executeInBackground(context, new BackgroundWork<List<Map<String,Object>>>() {
			@Override
			public List<Map<String,Object>> doInBackground() throws Exception {
				List<Line> lines = lineService.queryLine(" where f_sectid=?",sectid);
				for (int i = 0; i <lines.size() ; i++) {
					Map<String,Object> lineMap=new HashMap<String, Object>();
					Line line=lines.get(i);
					List<BwInfo> bwInfos =bwInfoService.queryBwInfo(" where f_lc=? order by od", line.getLc());
					lineMap.put("line",line);
					lineMap.put("bw",bwInfos);
					lineData.add(lineMap);
				}


				return lineData;
			}
		}, new Completion<List<Map<String,Object>>>() {
			@Override
			public void onSuccess(Context context, List<Map<String,Object>> result) {
				shuiZhunXianLuListAdapter = new ShuiZhunXianLuListAdapter(context, result);
				setListAdapter(shuiZhunXianLuListAdapter);
			}

			@Override
			public void onError(Context context, Exception e) {
				EventBus.getDefault().post(new MainActivityEvent(false));
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_shuizhunxianlu,null);
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		CommonTools.showToast(context,"click:"+position);
	}

}