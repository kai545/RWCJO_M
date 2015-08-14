package rwcjom.awit.com.rwcjo_m.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.dao.BwInfo;
import rwcjom.awit.com.rwcjo_m.dao.Line;
import rwcjom.awit.com.rwcjo_m.dao.LineExtra;
import rwcjom.awit.com.rwcjo_m.dao.PersonInfo;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.service.LineExtraService;
import rwcjom.awit.com.rwcjo_m.service.PersonInfoService;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;

@EActivity
public class LineInfoActivity extends ActionBarActivity {
    public final String TAG="LineInfoActivity";

    private Toolbar toolbar;
    private Map<String,Object> lineinfoMap;
    private Line line;
    private ArrayAdapter<String> sj_adapter;
    private PersonInfoService personInfoService;
    private LineExtraService lineExtraService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharePreferenceEditor;

    private String[] ltype_array,mtype_array,weather_array;


    @ViewById(R.id.edit_line_code_edit)
    EditText line_lc;

    @ViewById(R.id.edit_line_type_edit)
    Spinner line_ltype;

    @ViewById(R.id.edit_line_mtype_edit)
    Spinner line_mtype;

    @ViewById(R.id.edit_line_benchmarkids_edit)
    EditText line_bpnt;

    @ViewById(R.id.edit_line_cedian_edit)
    EditText line_pnt;

    @ViewById(R.id.edit_line_equipbrand_edit)
    EditText line_devbrand;

    @ViewById(R.id.edit_line_instrumodel_edit)
    EditText line_devtype;

    @ViewById(R.id.edit_line_serialnum_edit)
    EditText line_devsn;

    @ViewById(R.id.edit_line_sj_edit)
    Spinner line_sj;

    @ViewById(R.id.edit_line_temperature_edit)
    EditText line_temp;

    @ViewById(R.id.edit_line_weather_edit)
    Spinner line_weather;

    @ViewById(R.id.edit_line_barometric_edit)
    EditText line_air;

    @ViewById(R.id.edit_line_mdate_edit)
    EditText line_mdate;

    @AfterViews
    void initData() {
        List<BwInfo> bws=(List<BwInfo>)lineinfoMap.get("bw");
        Log.i(TAG,"Line:"+line.getLn());
        line_lc.setText(line.getLc());
        Log.i(TAG, "line_bw_size:" + bws.size());
        for (int i = 0; i <bws.size() ; i++) {

            BwInfo bwInfo=bws.get(i);
            if (bwInfo.getTy().equals("1")){
                line_bpnt.append(bwInfo.getId()+" ");
            }else{
                line_pnt.append(bwInfo.getId()+" ");
            }
        }



        //获取司镜
        Tasks.executeInBackground(this, new BackgroundWork<List<String>>() {
            @Override
            public List<String> doInBackground() throws Exception {
                List<PersonInfo> personalInfos = personInfoService.queryPersonInfo("");//get all personals
                List<String> sj = new ArrayList<String>();
                for (int i = 0; i < personalInfos.size(); i++) {
                    sj.add(personalInfos.get(i).getUsername());
                }
                return sj;
            }
        }, new Completion<List<String>>() {
            @Override
            public void onSuccess(Context context, List<String> result) {
                sj_adapter = new ArrayAdapter<String>(LineInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, result);
                line_sj.setAdapter(sj_adapter);
            }

            @Override
            public void onError(Context context, Exception e) {
                EventBus.getDefault().post(new MainActivityEvent(false));
            }
        });

        line_mdate.setText(CommonTools.getDateWith("yyyy-MM-dd"));//观测日期
    }

    @Click(R.id.save_line_extra)
    void save(){
        saveLineExtra();
        CommonTools.showStringMsgDialog(this, "线路保存成功！","确定");
    }

    @Click(R.id.save_line_extra_and_go)
    void saveAndGo(){
        saveLineExtra();
        Intent it=new Intent(this,MeasureActivity_.class);
        startActivity(it);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lineinfoMap=(Map<String,Object>)getIntent().getSerializableExtra("lineinfo");
        line=(Line)lineinfoMap.get("line");
        Resources res =getResources();
        ltype_array=res.getStringArray(R.array.line_type);
        mtype_array=res.getStringArray(R.array.line_mtype);
        weather_array=res.getStringArray(R.array.weather);
        personInfoService=PersonInfoService.getInstance(this);
        lineExtraService=LineExtraService.getInstance(this);

        initSharePreference();
        setContentView(R.layout.activity_line_info);



        //获取线路数据
        Tasks.executeInBackground(this, new BackgroundWork<LineExtra>() {
            @Override
            public LineExtra doInBackground() throws Exception {
                LineExtra lineExtra = lineExtraService.getLineExtraByLc(line.getLc());//get all personals

                return lineExtra;
            }
        }, new Completion<LineExtra>() {
            @Override
            public void onSuccess(Context context, LineExtra result) {
                if(result!=null){
                    line_lc.setText(result.getLc());
                    line_ltype.setSelection(CommonTools.indexOfArray(ltype_array,result.getLtype()));
                    line_mtype.setSelection(CommonTools.indexOfArray(mtype_array,result.getMtype()));
                    line_devbrand.setText(result.getDevBrand());
                    line_devtype.setText(result.getDevType());
                    line_devsn.setText(result.getDevSN());
                    line_temp.setText(result.getTemp());
                    line_weather.setSelection(CommonTools.indexOfArray(weather_array,result.getWeather()));
                    line_air.setText(result.getAir());
                }

            }

            @Override
            public void onError(Context context, Exception e) {
                EventBus.getDefault().post(new MainActivityEvent(false));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_line_info, menu);
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

    private void initSharePreference(){
        sharedPreferences=getSharedPreferences(ValueConfig.SHAREPREFERENCE_XML_NAME, Activity.MODE_PRIVATE);
        sharePreferenceEditor=sharedPreferences.edit();
    }

    private void saveLineExtra(){
        LineExtra lineExtra=new LineExtra();
        lineExtra.setLc(line_lc.getText()+"");
        lineExtra.setLtype(line_ltype.getSelectedItem()+"");
        lineExtra.setMtype(line_mtype.getSelectedItem()+"");
        lineExtra.setBpntsq(line_bpnt.getText()+"");//基点
        lineExtra.setDevBrand(line_devbrand.getText()+"");
        lineExtra.setDevType(line_devtype.getText()+"");
        lineExtra.setDevSN(line_devsn.getText()+"");
        lineExtra.setStuff_name(line_sj.getSelectedItem()+"");

        lineExtra.setStuffid(sharedPreferences.getString("username",""));
        lineExtra.setStuff_pwd(sharedPreferences.getString("userpwd",""));

        lineExtra.setTemp(line_temp.getText()+"");
        lineExtra.setWeather(line_weather.getSelectedItem()+"");
        lineExtra.setAir(line_air.getText()+"");
        lineExtra.setMdate(line_mdate.getText()+"");


        lineExtraService.saveLineExtra(lineExtra);
    }

}
