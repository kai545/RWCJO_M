package rwcjom.awit.com.rwcjo_m.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.dao.BwInfo;
import rwcjom.awit.com.rwcjo_m.dao.Line;
import rwcjom.awit.com.rwcjo_m.fragments.IndexFragment;
import rwcjom.awit.com.rwcjo_m.fragments.ProjectFragment;
import rwcjom.awit.com.rwcjo_m.fragments.ShuiZhunXianLuFragmentContainer;

@EActivity
public class MeasureActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    public final String TAG="MeasureActivity";
    private Toolbar toolbar;
    private Map<String,Object> lineinfoMap;
    private Line line;
    private List<BwInfo> bwInfo_list;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<Map<String, Object>> left_measure_line_data=new ArrayList<Map<String, Object>>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        lineinfoMap=(Map<String,Object>)getIntent().getSerializableExtra("lineinfo");
        line=(Line)lineinfoMap.get("line");
        bwInfo_list=(List<BwInfo>)lineinfoMap.get("bw");
        initDrawerMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_measure, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    //初始化抽屉菜单中的测站列表
    private void initDrawerMenu(){

        for (int i = 0; i <bwInfo_list.size()-1 ; i++) {
            Map<String, Object> measure_station=new HashMap<String, Object>();
            BwInfo back_pnt=bwInfo_list.get(i);
            BwInfo front_pnt=bwInfo_list.get(i + 1);
            measure_station.put("b_pnt_name", (back_pnt.getTy().equalsIgnoreCase("1") ? "B" : "P" )+ back_pnt.getId());
            measure_station.put("f_pnt_name", (front_pnt.getTy().equalsIgnoreCase("1") ? "B" : "P" )+ front_pnt.getId());
            left_measure_line_data.add(measure_station);
        }


        mDrawerList = (ListView) findViewById(R.id.measure_line_drawer_list);
        SimpleAdapter drawerAdapter = new SimpleAdapter(this,left_measure_line_data ,R.layout.measure_line_left_list_item,
                new String[]{"b_pnt_name","f_pnt_name"},
                new int[]{R.id.measure_list_itme_b_pnt,R.id.measure_list_itme_f_pnt});
        mDrawerList.setAdapter(drawerAdapter);
        mDrawerList.setOnItemClickListener(this);

		/* findView */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.measure_line_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /*
    * 抽屉菜单监听
    * */
    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        // TODO Auto-generated method stub
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;

        int img_id=Integer.parseInt(left_measure_line_data.get(position).get("b_pnt_name").toString());
        switch (img_id){
            case R.mipmap.ic_drawer_index_normal:
                fragment=new IndexFragment();
                ft.replace(R.id.drawer_fragment_layout, fragment);
                ft.commit();
                break;
            case R.mipmap.ic_drawer_line_normal:
                fragment=new ShuiZhunXianLuFragmentContainer();
                ft.replace(R.id.drawer_fragment_layout, fragment);
                ft.commit();
                break;
            case R.mipmap.ic_drawer_search_normal:
                fragment=new ProjectFragment();
                ft.replace(R.id.drawer_fragment_layout, fragment);
                ft.commit();
                break;
            case R.mipmap.ic_drawer_explore_normal:
                toolbar.setTitle("通讯设置");
                //fragment=new BluetoothFragment();
                //ft.replace(R.id.drawer_fragment_layout, fragment);
                //ft.commit();
                startActivity(new Intent(this,BluetoothActivity.class));
                break;
            case R.mipmap.ic_drawer_follow_normal:
                toolbar.setTitle("参数设置");
                break;
            case R.mipmap.ic_drawer_setting_normal:
                toolbar.setTitle("系统设置");
                break;
            case R.mipmap.ic_drawer_question_normal:
                toolbar.setTitle("用户手册");
                startActivity(new Intent(this,PhoneGapActivity.class));
                break;
            default:break;
        }

        mDrawerLayout.closeDrawers();
    }
}
