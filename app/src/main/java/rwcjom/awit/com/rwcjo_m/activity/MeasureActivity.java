package rwcjom.awit.com.rwcjo_m.activity;

import android.content.Context;
import android.os.Bundle;
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

import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import io.palaima.smoothbluetooth.SmoothBluetooth;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.dao.BwInfo;
import rwcjom.awit.com.rwcjo_m.dao.Line;
import rwcjom.awit.com.rwcjo_m.dao.LineExtra;
import rwcjom.awit.com.rwcjo_m.dao.LineStation;
import rwcjom.awit.com.rwcjo_m.event.BluetoothDataEvent;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.listener.MeasureBluetoothListener;
import rwcjom.awit.com.rwcjo_m.service.LineStationService;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;

@EActivity
public class MeasureActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    public final String TAG = "MeasureActivity";
    private Toolbar toolbar;
    private Map<String, Object> lineinfoMap;
    private Line line;//线路基础信息
    private List<BwInfo> bwInfo_list;//线路测点信息
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private SimpleAdapter drawerAdapter;//测站适配器
    private ActionBarDrawerToggle mDrawerToggle;
    private List<Map<String, Object>> left_measure_line_data=new ArrayList<Map<String, Object>>();//左侧测站列表数据
    private List<LineStation> lineStationList;//数据库中的测站

    private SmoothBluetooth mSmoothBluetooth;

    private LineExtra lineExtra;

    private int measureCounterForStation = -1;

    private double data_houju1,data_houju2,data_qianju1,data_qianju2,data_houchi1,data_houchi2,data_qianchi1,data_qianchi2;

    private LineStationService lineStationService;

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
    TextView measure_textview_station_houchi2;//后尺2

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

    @AfterViews
    void initBluetooth() {//初始化蓝牙
        mSmoothBluetooth = new SmoothBluetooth(this);
        mSmoothBluetooth.setListener(new MeasureBluetoothListener(this, mSmoothBluetooth));
        mSmoothBluetooth.tryConnection();
    }

    @Click(R.id.measure_station_btn_remeasure)
    void reMeasure(){
        if(measure_textview_station_code.getText().length()!=0){
            resetAllData();
            refreshAllTextView();
        }else{
            CommonTools.showToast(this,"未选择测站");
        }

    }

    @Click(R.id.measure_station_btn_add_zpnt)
    void addZPnt(){//在当前测站后增加转点
        int position=0;



        if (measureCounterForStation >= 0 && lineStationList.size()!=0){//
            String ZPnt="Z00001";
            LineStation lineStation_new=new LineStation();
            LineStation lineStation_now=lineStationList.get(position);
            lineStation_new.setSb(ZPnt);
            lineStation_new.setSf(lineStation_now.getSf());
            lineStation_new.setF_lc(line.getLc());

            lineStation_now.setSf(ZPnt);
            lineStationService.saveLineStation(lineStation_now);

            lineStationService.saveLineStation(lineStation_new);

            lineStationList.add(lineStation_new);

            lineStation2ListData(lineStationList);


            drawerAdapter.notifyDataSetChanged();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        EventBus.getDefault().register(this);//注册事件总线
        lineExtra = (LineExtra) getIntent().getSerializableExtra("line_extra");
        lineinfoMap = (Map<String, Object>) getIntent().getSerializableExtra("lineinfo");
        line = (Line) lineinfoMap.get("line");
        bwInfo_list = (List<BwInfo>) lineinfoMap.get("bw");
        lineStationService=LineStationService.getInstance(this);
        initDrawerMenu();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSmoothBluetooth.stop();
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


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    //初始化抽屉菜单中的测站列表，如果数据库没有记录则先初始化数据
    private void initDrawerMenu() {

        //从数据读取测站信息
        Tasks.executeInBackground(this, new BackgroundWork<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> doInBackground() throws Exception {
                lineStationList = lineStationService.queryLineStation(" where f_lc=? order by abs(sno)", line.getLc());
                if (lineStationList.size() == 0) {
                    lineStationList=new ArrayList<LineStation>();
                    for (int i = 0; i < bwInfo_list.size() - 1; i++) {
                        BwInfo back_pnt = bwInfo_list.get(i);
                        BwInfo front_pnt = bwInfo_list.get(i + 1);
                        LineStation lineStation=new LineStation();
                        lineStation.setSb((back_pnt.getTy().equalsIgnoreCase("1") ? "B" : "P") + back_pnt.getId());
                        lineStation.setSf((front_pnt.getTy().equalsIgnoreCase("1") ? "B" : "P") + front_pnt.getId());
                        lineStation.setF_lc(line.getLc());
                        lineStationService.saveLineStation(lineStation);
                        lineStationList.add(lineStation);
                    }
                }

                lineStation2ListData(lineStationList);

                return left_measure_line_data;
            }
        }, new Completion<List<Map<String, Object>>>() {
            @Override
            public void onSuccess(Context context, List<Map<String, Object>> result) {
                mDrawerList = (ListView) findViewById(R.id.measure_line_drawer_list);
                drawerAdapter = new SimpleAdapter(MeasureActivity.this, result, R.layout.measure_line_left_list_item,
                        new String[]{"station_code", "b_pnt_name", "f_pnt_name"},
                        new int[]{R.id.measure_list_itme_station_code, R.id.measure_list_itme_b_pnt, R.id.measure_list_itme_f_pnt});
                mDrawerList.setAdapter(drawerAdapter);
                mDrawerList.setOnItemClickListener(MeasureActivity.this);

		/* findView */
                mDrawerLayout = (DrawerLayout) findViewById(R.id.measure_line_drawer);
                mDrawerToggle = new ActionBarDrawerToggle(MeasureActivity.this, mDrawerLayout, toolbar, R.string.drawer_open,
                        R.string.drawer_close);
                mDrawerToggle.syncState();
                mDrawerLayout.setDrawerListener(mDrawerToggle);
            }

            @Override
            public void onError(Context context, Exception e) {
                EventBus.getDefault().post(new MainActivityEvent(false));
            }
        });

    }

    /*
    * 抽屉菜单监听
    * */
    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        // TODO Auto-generated method stub
        //此处应从数据读取

        Map<String, Object> measure_station = left_measure_line_data.get(position);
        measure_textview_station_code.setText(position + 1 + "");
        //充值测量计数
        measureCounterForStation = 0;//开始奇数测量次数
        measure_textview_station_b.setText(measure_station.get("b_pnt_name") + "");
        measure_textview_station_f.setText(measure_station.get("f_pnt_name") + "");
        mDrawerLayout.closeDrawers();
    }

    //重置测量数据
    private void resetAllData(){
        data_houju1=0;
        data_houju2=0;
        data_qianju1=0;
        data_qianju2=0;
        data_houchi1=0;
        data_houchi2=0;
        data_qianchi1=0;
        data_qianchi2=0;
    }

    //刷新Tv显示
    private void refreshAllTextView(){
        measure_textview_station_houju1.setText(data_houju1==0?"":data_houju1+"");//后距1

        measure_textview_station_houju2.setText(data_houju2==0?"":data_houju2+"");//后距2

        measure_textview_station_houchi1.setText(data_houchi1==0?"":data_houchi1+"");//后尺1

        measure_textview_station_houchi2.setText(data_houchi2==0?"":data_houchi2+"");//后尺2

        measure_textview_station_qianju1.setText(data_qianju1==0?"":data_qianju1+"");//前距1

        measure_textview_station_qianju2.setText(data_qianju2==0?"":data_qianju2+"");//前距2

        measure_textview_station_qianchi1.setText(data_qianchi1==0?"":data_qianchi1+"");//前尺1

        measure_textview_station_qianchi2.setText(data_qianchi2==0?"":data_qianchi2+"");//前尺2

        DecimalFormat dformat = new DecimalFormat("0.00000");
        double data_shijucha1=CommonTools.sub(data_houju1,data_qianju1);
        measure_textview_station_shijucha1.setText(dformat.format(data_shijucha1));//视距差1

        double data_shijucha2=CommonTools.sub(data_houju2,data_qianju2);
        measure_textview_station_shijucha2.setText(dformat.format(data_shijucha2));//视距差2

        double data_gaocha1=CommonTools.sub(data_houchi1,data_qianchi1);
        measure_textview_station_gaocha1.setText(dformat.format(data_gaocha1));//高差1

        double data_gaocha2=CommonTools.sub(data_houchi2,data_qianchi2);
        measure_textview_station_gaocha2.setText(dformat.format(data_gaocha2));//高差2

        measure_textview_station_shijucha.setText(CommonTools.div(CommonTools.add(data_shijucha1,data_shijucha2),2)+"");//视距差


        measure_textview_station_all_shijucha.setText("0");//累计视距差

        measure_textview_station_gaocha.setText(CommonTools.div(CommonTools.add(data_gaocha1,data_gaocha2),2)+"");//高差

        measure_textview_station_gaocha_cha.setText(dformat.format(CommonTools.sub(data_gaocha1,data_gaocha2)*1000));//高差之差

        double data_breadcha=CommonTools.sub(data_houchi1,data_houchi2)*1000;
        measure_textview_station_breadcha.setText(dformat.format(data_breadcha));//后尺读数差

        double data_freadcha=CommonTools.sub(data_qianchi1,data_qianchi2)*1000;
        measure_textview_station_freadcha.setText(dformat.format(data_freadcha));//前尺读数差
    }

    //处理蓝牙数据
    public void onEventMainThread(BluetoothDataEvent event) {
        //CommonTools.showToast(this, event.getData());//收到水准仪数据
        if (measureCounterForStation >= 0 && measureCounterForStation < 4) {
            measureCounterForStation++;
            //数据示例：R2.16841 HD1.750
            String data = event.getData();
            double r=Double.parseDouble(data.substring(data.indexOf("R") + 1, data.indexOf("HD") - 1));
            double hd = Double.parseDouble(data.substring(data.indexOf("D") + 1, data.length() - 1));
            CommonTools.showToast(this, r+ "," +hd);//收到水准仪数据

            measureDataFlow(hd, r);//根据流程分配数据
        } else {
            CommonTools.showToast(this, "无效数据（无效测站或超过测量次数）");//收到水准仪数据
        }


    }

    //数据处理
    private void measureDataFlow(double hd, double r) {
        if (lineExtra.getMtype().equalsIgnoreCase("aBFFB")) {
            if (Integer.parseInt(measure_textview_station_code.getText() + "") % 2 != 0) {//奇测站
                measureDataWrite("BFFB",measureCounterForStation,hd,r);
            } else {//偶测站
                measureDataWrite("FBBF",measureCounterForStation,hd,r);
            }
        }else if (lineExtra.getMtype().equalsIgnoreCase("BFFB")) {
            measureDataWrite("BFFB",measureCounterForStation,hd,r);
        }

        refreshAllTextView();//数据就位后，刷新显示
    }

    //数据填充
    private void measureDataWrite(String mtype, int order, double hd, double r) {//bffb bf
        if (mtype.equalsIgnoreCase("BFFB")) {
            switch (order) {
                case 1:
                    data_houju1=hd;
                    data_houchi1=r;
                    break;
                case 2:
                    data_qianju1=hd;
                    data_qianchi1=r;
                    break;
                case 3:
                    data_qianju2=hd;
                    data_qianchi2=r;
                    break;
                case 4:
                    data_houju2=hd;
                    data_houchi2=r;
                    break;
            }
        } else if (mtype.equalsIgnoreCase("FBBF")) {
            switch (order) {

                case 1:
                    data_qianju1=hd;
                    data_qianchi1=r;
                    break;
                case 2:
                    data_houju1=hd;
                    data_houchi1=r;
                    break;
                case 3:
                    data_houju2=hd;
                    data_houchi2=r;
                    break;
                case 4:
                    data_qianju2=hd;
                    data_qianchi2=r;
                    break;
            }

        } else if (mtype.equalsIgnoreCase("BF")) {
            switch (order) {
                case 1:
                    data_houju1=hd;
                    data_houchi1=r;
                    break;
                case 2:
                    data_qianju1=hd;
                    data_qianchi1=r;
                    break;
            }
        } else if (mtype.equalsIgnoreCase("FB")) {
            switch (order) {
                case 1:
                    data_qianju1=hd;
                    data_qianchi1=r;
                    break;
                case 2:
                    data_houju1=hd;
                    data_houchi1=r;
                    break;
            }
        }

    }

    //测站数据转换成list数据
    private void  lineStation2ListData(List<LineStation> lineStations){
        //List<Map<String, Object>> lineStationListData = new ArrayList<Map<String, Object>>();
        left_measure_line_data.clear();

        List<LineStation> lineStations_ori=new ArrayList<LineStation>();
        lineStations_ori.addAll(lineStations);

        List<LineStation> lineStations_dst=new ArrayList<LineStation>();



        for (int i = 0; i < lineStations.size(); i++) {

            LineStation lineStation=lineStations.get(i);
            if (lineStation.getSb().contains("B")){//找到第一个测站
                lineStations_dst.add(lineStation);
                searchByBpnt(lineStation,
                        lineStations_ori,
                        lineStations_dst);
            }
        }

        for (int j = 0; j < lineStations_dst.size(); j++) {
            Map<String, Object> measure_station = new HashMap<String, Object>();
            measure_station.put("station_code", j + 1);
            measure_station.put("b_pnt_name", lineStations_dst.get(j).getSb());
            measure_station.put("f_pnt_name", lineStations_dst.get(j).getSf());
            left_measure_line_data.add(measure_station);
        }

    }

    //测站智能排序
    private LineStation searchByBpnt(LineStation lineStation,
                                     List<LineStation> lineStations_ori,
                                     List<LineStation> lineStations_dst){

        LineStation lineStation_result=null;
        if (lineStation!=null && lineStations_ori.size()!=0){
            for (int i = 0; i <lineStations_ori.size() ; i++) {
                if (lineStations_ori.get(i).getSb().equalsIgnoreCase(lineStation.getSf())){//找到测站
                    lineStations_dst.add(lineStations_ori.get(i));
                    lineStation_result=lineStations_ori.get(i);
                    lineStations_ori.remove(i);
                    return searchByBpnt(lineStation_result, lineStations_ori, lineStations_dst);
                }
            }
        }
        return lineStation_result;
    }

}
