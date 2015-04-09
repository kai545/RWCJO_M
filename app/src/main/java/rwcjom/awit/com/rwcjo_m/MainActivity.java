package rwcjom.awit.com.rwcjo_m;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.fragments.IndexFragment;
import rwcjom.awit.com.rwcjo_m.fragments.ProjectFragment;


public class MainActivity extends ActionBarActivity implements Toolbar.OnMenuItemClickListener,AdapterView.OnItemClickListener{
    private Toolbar toolbar;
    private SmoothProgressBar smthPrsbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;


    private List<Map<String, Object>> left_menu_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//注册事件总线
        setContentView(R.layout.activity_main_out);
        initToolbar();
        initDrawerMenu();
    }

    //处理事件
    public void onEventMainThread(MainActivityEvent event) {
        toolbar.setTitle(event.getTitle());
        //smthPrsbar.setVisibility(event.isProgressBarState()?View.VISIBLE:View.INVISIBLE);
        isShowProgressbar(event.isProgressBarState());
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        toolbar.setOnMenuItemClickListener(this);

        smthPrsbar=(SmoothProgressBar)findViewById(R.id.toolbar_progreebar);
    }
    private void initDrawerMenu(){
        initDrawerMenuData();
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        SimpleAdapter drawerAdapter = new SimpleAdapter(this,left_menu_list,R.layout.drawer_list_item,
                new String[]{"img","text"},
                new int[]{R.id.drawer_list_itme_img,R.id.drawer_list_itme_text});
        mDrawerList.setAdapter(drawerAdapter);
        mDrawerList.setOnItemClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		/* findView */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    private void initDrawerMenuData() {
        left_menu_list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("text", "首页");
        map.put("img", R.mipmap.ic_drawer_home_normal);
        left_menu_list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "项目查看");
        map.put("img", R.mipmap.ic_drawer_search_normal);
        left_menu_list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "通讯设置");
        map.put("img", R.mipmap.ic_drawer_explore_normal);
        left_menu_list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "参数设置");
        map.put("img", R.mipmap.ic_drawer_follow_normal);
        left_menu_list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "系统设置");
        map.put("img", R.mipmap.ic_drawer_setting_normal);
        left_menu_list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "用户手册");
        map.put("img", R.mipmap.ic_drawer_question_normal);
        left_menu_list.add(map);

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

        int img_id=Integer.parseInt(left_menu_list.get(position).get("img").toString());
        switch (img_id){
            case R.mipmap.ic_drawer_home_normal:
                fragment=new IndexFragment();
                ft.replace(R.id.drawer_fragment_layout, fragment);
                ft.commit();
                break;
            case R.mipmap.ic_drawer_search_normal:
                toolbar.setTitle("项目查看");

                fragment=new ProjectFragment();
                ft.replace(R.id.drawer_fragment_layout, fragment);
                ft.commit();
                break;
            case R.mipmap.ic_drawer_explore_normal:
                toolbar.setTitle("通讯设置");
                break;
            case R.mipmap.ic_drawer_follow_normal:
                toolbar.setTitle("参数设置");
                break;
            case R.mipmap.ic_drawer_setting_normal:
                toolbar.setTitle("系统设置");
                break;
            case R.mipmap.ic_drawer_question_normal:
                toolbar.setTitle("用户手册");
                break;
            default:break;
        }

        mDrawerLayout.closeDrawers();
    }
/*
* 右上角菜单监听
* */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_checkupdate:
                Toast.makeText(MainActivity.this, "检查更新", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_aboutus:
                Toast.makeText(MainActivity.this, "关于我们", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_exit:
                Toast.makeText(MainActivity.this, "退出", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_clouddown:
                Toast.makeText(MainActivity.this, "下载", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    private void isShowProgressbar(boolean tf){
        if (tf){
            smthPrsbar.setVisibility(View.VISIBLE);
            smthPrsbar.progressiveStart();
        }else{
            smthPrsbar.progressiveStop();
            smthPrsbar.setVisibility(View.INVISIBLE);
        }
    }

}
