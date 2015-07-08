package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;
import rwcjom.awit.com.rwcjo_m.dao.PntInfo;
import rwcjom.awit.com.rwcjo_m.dao.PntInfoDao;

/**
 * Created by Fantasy on 15/4/27.
 */
public class PntInfoService {
    private static final String TAG = PntInfoService.class.getSimpleName();
    private static PntInfoService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private PntInfoDao pntInfoDao;


    private PntInfoService() {
    }

    public static PntInfoService getInstance(Context context) {
        if (instance == null) {
            instance = new PntInfoService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.pntInfoDao = instance.mDaoSession.getPntInfoDao();
        }
        return instance;
    }

    public List<PntInfo> loadAllPntInfo(){
        return pntInfoDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<PntInfo> queryPntInfo(String where, String... params){
        return pntInfoDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param pntInfo
     * @return insert or update note id
     */
    public long savePntInfo(PntInfo pntInfo){
        return pntInfoDao.insertOrReplace(pntInfo);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void savePntInfoLists(final List<PntInfo> list){
        if(list == null || list.isEmpty()){
            return;
        }
        pntInfoDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    PntInfo pntInfo = list.get(i);
                    pntInfoDao.insertOrReplace(pntInfo);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllPntInfo(){
        pntInfoDao.deleteAll();
    }

    /**
     * delete note by id
     * @param pointid
     */
    public void deletePntInfo(String pointid){
        pntInfoDao.deleteByKey(pointid);
        Log.i(TAG, "delete");
    }

    public void deleteAll(){
        pntInfoDao.deleteAll();
    }

    public void deletePntInfo(PntInfo pntInfo){
        pntInfoDao.delete(pntInfo);
    }
}
