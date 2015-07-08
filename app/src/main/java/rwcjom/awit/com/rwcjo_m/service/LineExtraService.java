package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;
import rwcjom.awit.com.rwcjo_m.dao.LineExtra;
import rwcjom.awit.com.rwcjo_m.dao.LineExtraDao;

/**
 * Created by Fantasy on 15/7/8.
 */
public class LineExtraService {
    private static final String TAG = LineExtraService.class.getSimpleName();
    private static LineExtraService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private LineExtraDao lineExtraDao;


    private LineExtraService() {
    }

    public static LineExtraService getInstance(Context context) {
        if (instance == null) {
            instance = new LineExtraService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.lineExtraDao = instance.mDaoSession.getLineExtraDao();
        }
        return instance;
    }

    public List<LineExtra> loadAllLineExtra(){
        return lineExtraDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<LineExtra> queryLineExtra(String where, String... params){
        return lineExtraDao.queryRaw(where, params);
    }

    public LineExtra getLineExtraByLc(String lc){
        return lineExtraDao.load(lc);
    }

    /**
     * insert or update note
     * @param LineExtra
     * @return insert or update note id
     */
    public long saveLineExtra(LineExtra lineExtra){
        return lineExtraDao.insertOrReplace(lineExtra);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveLineExtraLists(final List<LineExtra> list){
        if(list == null || list.isEmpty()){
            return;
        }
        lineExtraDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    LineExtra lineExtra = list.get(i);
                    lineExtraDao.insertOrReplace(lineExtra);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllLineExtra(){
        lineExtraDao.deleteAll();
    }

    /**
     * delete note by id
     * @param lc
     */
    public void deleteLineExtra(String lc){
        lineExtraDao.deleteByKey(lc);
        Log.i(TAG, "delete");
    }

    public void deleteAll(){
        lineExtraDao.deleteAll();
    }

    public void deleteLineExtra(LineExtra lineExtra){
        lineExtraDao.delete(lineExtra);
    }
}
