package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;
import rwcjom.awit.com.rwcjo_m.dao.OriData;
import rwcjom.awit.com.rwcjo_m.dao.OriDataDao;

/**
 * Created by Fantasy on 15/9/9.
 */
public class OriDataService {
    private static final String TAG = OriDataService.class.getSimpleName();
    private static OriDataService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private OriDataDao oriDataDao;


    private OriDataService() {
    }

    public static OriDataService getInstance(Context context) {
        if (instance == null) {
            instance = new OriDataService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.oriDataDao = instance.mDaoSession.getOriDataDao();
        }
        return instance;
    }

    public List<OriData> loadAllOriData(){
        return oriDataDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<OriData> queryOriData(String where, String... params){
        return oriDataDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param OriData
     * @return insert or update note id
     */
    public long saveOriData(OriData oriData){
        return oriDataDao.insertOrReplace(oriData);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveOriDataLists(final List<OriData> list){
        if(list == null || list.isEmpty()){
            return;
        }
        oriDataDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    OriData oriData = list.get(i);
                    oriDataDao.insertOrReplace(oriData);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllOriData(){
        oriDataDao.deleteAll();
    }


    public void deleteAll(){
        oriDataDao.deleteAll();
    }

    public void deleteOriData(OriData oriData){
        oriDataDao.delete(oriData);
    }

}
