package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.BrgInfo;
import rwcjom.awit.com.rwcjo_m.dao.BrgInfoDao;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;

/**
 * Created by Fantasy on 15/4/27.
 */
public class BrgInfoService {
    private static final String TAG = BrgInfoService.class.getSimpleName();
    private static BrgInfoService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private BrgInfoDao brgInfoDao;


    private BrgInfoService() {
    }

    public static BrgInfoService getInstance(Context context) {
        if (instance == null) {
            instance = new BrgInfoService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.brgInfoDao = instance.mDaoSession.getBrgInfoDao();
        }
        return instance;
    }

    public List<BrgInfo> loadAllBrgInfo(){
        return brgInfoDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<BrgInfo> queryBrgInfo(String where, String... params){
        return brgInfoDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param brgInfo
     * @return insert or update note id
     */
    public long saveBrgInfo(BrgInfo brgInfo){
        return brgInfoDao.insertOrReplace(brgInfo);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveBrgInfoLists(final List<BrgInfo> list){
        if(list == null || list.isEmpty()){
            return;
        }
        brgInfoDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    BrgInfo brgInfo = list.get(i);
                    brgInfoDao.insertOrReplace(brgInfo);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllBrgInfo(){
        brgInfoDao.deleteAll();
    }

    /**
     * delete note by id
     * @param faceid
     */
    public void deleteBrgInfo(String faceid){
        brgInfoDao.deleteByKey(faceid);
        Log.i(TAG, "delete");
    }

    public void deleteAll(){
        brgInfoDao.deleteAll();
    }

    public void deleteBrgInfo(BrgInfo brgInfo){
        brgInfoDao.delete(brgInfo);
    }
}
