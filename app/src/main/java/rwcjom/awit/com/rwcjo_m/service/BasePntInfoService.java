package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.BasePntInfo;
import rwcjom.awit.com.rwcjo_m.dao.BasePntInfoDao;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;

/**
 * Created by Fantasy on 15/4/28.
 */
public class BasePntInfoService {
    private static final String TAG = BasePntInfoService.class.getSimpleName();
    private static BasePntInfoService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private BasePntInfoDao basePntInfoDao;


    private BasePntInfoService() {
    }

    public static BasePntInfoService getInstance(Context context) {
        if (instance == null) {
            instance = new BasePntInfoService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.basePntInfoDao = instance.mDaoSession.getBasePntInfoDao();
        }
        return instance;
    }

    public List<BasePntInfo> loadAllBasePntInfo(){
        return basePntInfoDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<BasePntInfo> queryBasePntInfo(String where, String... params){
        return basePntInfoDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param basePntInfo
     * @return insert or update note id
     */
    public long saveBasePntInfo(BasePntInfo basePntInfo){
        return basePntInfoDao.insertOrReplace(basePntInfo);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveBasePntInfoLists(final List<BasePntInfo> list){
        if(list == null || list.isEmpty()){
            return;
        }
        basePntInfoDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    BasePntInfo basePntInfo = list.get(i);
                    basePntInfoDao.insertOrReplace(basePntInfo);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllBasePntInfo(){
        basePntInfoDao.deleteAll();
    }

    /**
     * delete note by id
     * @param basepntid
     */
    public void deleteBasePntInfo(String basepntid){
        basePntInfoDao.deleteByKey(basepntid);
        Log.i(TAG, "delete");
    }

    public void deleteBasePntInfo(BasePntInfo basePntInfo){
        basePntInfoDao.delete(basePntInfo);
    }
}
