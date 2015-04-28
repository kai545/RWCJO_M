package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;
import rwcjom.awit.com.rwcjo_m.dao.FaceInfo;
import rwcjom.awit.com.rwcjo_m.dao.FaceInfoDao;

/**
 * Created by Fantasy on 15/4/24.
 */
public class FaceInfoService {

    private static final String TAG = FaceInfoService.class.getSimpleName();
    private static FaceInfoService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private FaceInfoDao faceInfoDao;


    private FaceInfoService() {
    }

    public static FaceInfoService getInstance(Context context) {
        if (instance == null) {
            instance = new FaceInfoService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.faceInfoDao = instance.mDaoSession.getFaceInfoDao();
        }
        return instance;
    }

    public List<FaceInfo> loadAllFaceInfo(){
        return faceInfoDao.loadAll();
    }

    public FaceInfo loadByPK(String faceid){
        return faceInfoDao.load(faceid);
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<FaceInfo> queryFaceInfo(String where, String... params){
        return faceInfoDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param faceInfo
     * @return insert or update note id
     */
    public long saveFaceInfo(FaceInfo faceInfo){
        return faceInfoDao.insertOrReplace(faceInfo);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveFaceInfoLists(final List<FaceInfo> list){
        if(list == null || list.isEmpty()){
            return;
        }
        faceInfoDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    FaceInfo faceInfo = list.get(i);
                    faceInfoDao.insertOrReplace(faceInfo);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllFaceInfo(){
        faceInfoDao.deleteAll();
    }

    /**
     * delete note by id
     * @param faceid
     */
    public void deleteFaceInfo(String faceid){
        faceInfoDao.deleteByKey(faceid);
        Log.i(TAG, "delete");
    }

    public void deleteFaceInfo(FaceInfo faceInfo){
        faceInfoDao.delete(faceInfo);
    }
}
