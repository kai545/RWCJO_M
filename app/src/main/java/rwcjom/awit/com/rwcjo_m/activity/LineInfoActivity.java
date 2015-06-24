package rwcjom.awit.com.rwcjo_m.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.dao.BwInfo;
import rwcjom.awit.com.rwcjo_m.dao.Line;

@EActivity
public class LineInfoActivity extends ActionBarActivity {
    public final String TAG="LineInfoActivity";

    private Toolbar toolbar;
    private Map<String,Object> lineinfoMap;

    @ViewById(R.id.edit_line_code_edit)
    EditText line_lc;

    @ViewById(R.id.edit_line_benchmarkids_edit)
    EditText line_bpnt;

    @ViewById(R.id.edit_line_cedian_edit)
    EditText line_pnt;


    @AfterViews
    void initData() {
        lineinfoMap=(Map<String,Object>)getIntent().getSerializableExtra("lineinfo");
        Line line=(Line)lineinfoMap.get("line");
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_info);


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
}
