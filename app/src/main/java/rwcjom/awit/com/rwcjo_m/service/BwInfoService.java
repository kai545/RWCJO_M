package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.BwInfo;
import rwcjom.awit.com.rwcjo_m.dao.BwInfoDao;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;

/**
 * Created by Fantasy on 15/4/28.
 */
public class BwInfoService {
    private static final String TAG = BwInfoService.class.getSimpleName();
    private static BwInfoService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private BwInfoDao bwInfoDao;


    private BwInfoService() {
    }

    public static BwInfoService getInstance(Context context) {
        if (instance == null) {
            instance = new BwInfoService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.bwInfoDao = instance.mDaoSession.getBwInfoDao();
        }
        return instance;
    }

    public List<BwInfo> loadAllBwInfo(){
        return bwInfoDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<BwInfo> queryBwInfo(String where, String... params){
        return bwInfoDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param bwInfo
     * @return insert or update note id
     */
    public long saveBwInfo(BwInfo bwInfo){
        return bwInfoDao.insertOrReplace(bwInfo);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveBwInfoLists(final List<BwInfo> list){
        if(list == null || list.isEmpty()){
            return;
        }
        bwInfoDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    BwInfo bwInfo = list.get(i);
                    bwInfoDao.insertOrReplace(bwInfo);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllBwInfo(){
        bwInfoDao.deleteAll();
    }

    /**
     * delete note by id
     * @param bwid
     */
    public void deleteBwInfo(long bwid){
        bwInfoDao.deleteByKey(bwid);
        Log.i(TAG, "delete");
    }

    public void deleteBwInfo(BwInfo bwInfo){
        bwInfoDao.delete(bwInfo);
    }
}
