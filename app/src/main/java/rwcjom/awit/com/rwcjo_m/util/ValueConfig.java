package rwcjom.awit.com.rwcjo_m.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.R;

public class ValueConfig {

	public static final String SPLIT_CHAR="λ";
	public static final String NAMESPACE_STRING="http://www.xfiretest.com/";
	//cjgc2为试点环境 cjgc为测试环境
	public static final String ENDPOINT_STRING="http://61.237.239.144/cjgc/services/DownLoadData";

	public static final boolean DEBUG_MODE=true;
	public static final String TEST_ACCOUNT="liuyang2";
	public static final String TEST_PASSWORD="password";

	//左侧抽屉菜单
	public static ArrayList<Map<String, Object>> getDrawerMenuData() {
		ArrayList<Map<String, Object>> left_menu_list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("text", "首页");
		map.put("img", R.mipmap.ic_drawer_index_normal);
		left_menu_list.add(map);

		map = new HashMap<String, Object>();
		map.put("text", "水准线路");
		map.put("img", R.mipmap.ic_drawer_line_normal);
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

		return left_menu_list;
	}
}
