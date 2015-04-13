package rwcjom.awit.com.rwcjo_m;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.ThemeSingleton;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.fragments.IndexFragment;
import rwcjom.awit.com.rwcjo_m.fragments.ProjectFragment;
import rwcjom.awit.com.rwcjo_m.fragments.ShuiZhunXianLuFragmentContainer;
import rwcjom.awit.com.rwcjo_m.implInterfaces.getPublicKeyImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.verifyAppUserImpl;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;


public class MainActivity extends ActionBarActivity implements Toolbar.OnMenuItemClickListener,AdapterView.OnItemClickListener{
    public final String TAG="MainActivity";
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

    //初始化抽屉菜单
    private void initDrawerMenu(){
        left_menu_list=ValueConfig.getDrawerMenuData();
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        SimpleAdapter drawerAdapter = new SimpleAdapter(this,left_menu_list ,R.layout.drawer_list_item,
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
                showLoginView();
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

    private EditText accountInput,passwordInput;
    private void showLoginView() {

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .title(R.string.login_dialog_title)
                .titleColor(R.color.syscolor)
                .customView(R.layout.dialog_login_view, true)
                .positiveText(R.string.login_positive_button_text)
                .negativeText(R.string.login_negative_button_text)
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        //CommonTools.showToast(MainActivity.this, "登录成功，开始同步数据");

                        Tasks.executeInBackground(MainActivity.this, new BackgroundWork<String>() {
                            @Override
                            public String doInBackground() throws Exception {
                                String result = "-200";
                                //CommonTools.showProgressDialog(MainActivity.this, "正在登录……");
                                if (passwordInput.getText().length() != 0) {
                                    getPublicKeyImpl getPubKeyImpl = new getPublicKeyImpl();
                                    String publicKey= getPubKeyImpl.getPublicKey(accountInput.getText() + "", null);
                                    if (publicKey.length() == 128) {
                                        verifyAppUserImpl verify = new verifyAppUserImpl();
                                        result = verify.getVerifyAppUser(accountInput.getText() + "", passwordInput.getText() + "", null,
                                                result);
                                        Log.i(TAG, "randomCode" + result);
                                    }
                                }
                                return result;
                            }
                        }, new Completion<String>() {
                            @Override
                            public void onSuccess(Context context, String result) {
                                if (result.length() == 13) {
                                    CommonTools.showToast(MainActivity.this, "登录成功：" + result);
                                } else {
                                    CommonTools.showToast(MainActivity.this, result + "，" + pubUtil.exception.getExceptionMsg());
                                }
                            }

                            @Override
                            public void onError(Context context, Exception e) {
                                //showError(e);
                            }
                        });


                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                }).build();
        dialog.setCanceledOnTouchOutside(false);

        accountInput = (EditText) dialog.getCustomView().findViewById(R.id.login_account);
        passwordInput = (EditText) dialog.getCustomView().findViewById(R.id.login_password);

        if (ValueConfig.DEBUG_MODE){
            accountInput.setText(ValueConfig.TEST_ACCOUNT);
            passwordInput.setText(ValueConfig.TEST_PASSWORD);
        }

        int widgetColor = ThemeSingleton.get().widgetColor;

        MDTintHelper.setEditTextTint(accountInput,
                widgetColor == 0 ? getResources().getColor(R.color.syscolor) : widgetColor);

        MDTintHelper.setEditTextTint(passwordInput,
                widgetColor == 0 ? getResources().getColor(R.color.syscolor) : widgetColor);

        dialog.show();
    }

}
