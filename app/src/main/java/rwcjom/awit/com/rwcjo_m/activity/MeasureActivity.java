package rwcjom.awit.com.rwcjo_m.activity;

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
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.dao.BwInfo;
import rwcjom.awit.com.rwcjo_m.dao.Line;

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
    private List<Map<String, Object>> left_measure_line_data=new ArrayList<Map<String, Object>>();

    @ViewById(R.id.measure_textview_station_code)
    TextView measure_textview_station_code;//测站编号

    @ViewById(R.id.measure_textview_station_b)
    TextView measure_textview_station_b;//后视点

    @ViewById(R.id.measure_textview_station_f)
    TextView measure_textview_station_f;//前视点

    @ViewById(R.id.measure_textview_station_houju1)
    TextView measure_textview_station_houju1;//后距1

    @ViewById(R.id.measure_textview_station_houju2)
    TextView measure_textview_station_houju2;//后距2

    @ViewById(R.id.measure_textview_station_houchi1)
    TextView measure_textview_station_houchi1;//后尺1

    @ViewById(R.id.measure_textview_station_houchi2)
    TextView measure_textview_station_houchi2;//后尺1

    @ViewById(R.id.measure_textview_station_qianju1)
    TextView measure_textview_station_qianju1;//前距1

    @ViewById(R.id.measure_textview_station_qianju2)
    TextView measure_textview_station_qianju2;//前距2

    @ViewById(R.id.measure_textview_station_qianchi1)
    TextView measure_textview_station_qianchi1;//前尺1

    @ViewById(R.id.measure_textview_station_qianchi2)
    TextView measure_textview_station_qianchi2;//前尺2

    @ViewById(R.id.measure_textview_station_shijucha1)
    TextView measure_textview_station_shijucha1;//视距差1

    @ViewById(R.id.measure_textview_station_shijucha2)
    TextView measure_textview_station_shijucha2;//视距差2

    @ViewById(R.id.measure_textview_station_gaocha1)
    TextView measure_textview_station_gaocha1;//高差1

    @ViewById(R.id.measure_textview_station_gaocha2)
    TextView measure_textview_station_gaocha2;//高差2

    @ViewById(R.id.measure_textview_station_shijucha)
    TextView measure_textview_station_shijucha;//视距差

    @ViewById(R.id.measure_textview_station_all_shijucha)
    TextView measure_textview_station_all_shijucha;//累计视距差

    @ViewById(R.id.measure_textview_station_gaocha)
    TextView measure_textview_station_gaocha;//高差

    @ViewById(R.id.measure_textview_station_gaocha_cha)
    TextView measure_textview_station_gaocha_cha;//高差之差

    @ViewById(R.id.measure_textview_station_breadcha)
    TextView measure_textview_station_breadcha;//后尺读数差

    @ViewById(R.id.measure_textview_station_freadcha)
    TextView measure_textview_station_freadcha;//前尺读数差



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

        Map<String, Object> measure_station=left_measure_line_data.get(position);
        measure_textview_station_code.setText(position+1+"");
        measure_textview_station_b.setText(measure_station.get("b_pnt_name")+"");
        measure_textview_station_f.setText(measure_station.get("f_pnt_name")+"");
        mDrawerLayout.closeDrawers();
    }
}
