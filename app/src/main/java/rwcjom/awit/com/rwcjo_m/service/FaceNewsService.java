package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;
import rwcjom.awit.com.rwcjo_m.dao.FaceNews;
import rwcjom.awit.com.rwcjo_m.dao.FaceNewsDao;

/**
 * Created by Fantasy on 15/4/24.
 */
public class FaceNewsService {
    private static final String TAG = FaceNewsService.class.getSimpleName();
    private static FaceNewsService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private FaceNewsDao faceNewsDao;


    private FaceNewsService() {
    }

    public static FaceNewsService getInstance(Context context) {
        if (instance == null) {
            instance = new FaceNewsService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.faceNewsDao = instance.mDaoSession.getFaceNewsDao();
        }
        return instance;
    }

    public List<FaceNews> loadAllFaceNews(){
        return faceNewsDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<FaceNews> queryFaceNews(String where, String... params){
        return faceNewsDao.queryRaw(where, params);
    }

    public List<FaceNews> queryFaceNewsBySite(String siteid){
        return faceNewsDao.queryBuilder()
                .where(FaceNewsDao.Properties.F_siteid.eq(siteid))
                .orderDesc(FaceNewsDao.Properties.FaceId)
                .list();
    }

    /**
     * insert or update note
     * @param faceNews
     * @return insert or update note id
     */
    public long saveFaceNews(FaceNews faceNews){
        return faceNewsDao.insertOrReplace(faceNews);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveFaceNewsLists(final List<FaceNews> list){
        if(list == null || list.isEmpty()){
            return;
        }
        faceNewsDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    FaceNews faceNews = list.get(i);
                    faceNewsDao.insertOrReplace(faceNews);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllFaceNews(){
        faceNewsDao.deleteAll();
    }

    /**
     * delete note by id
     * @param faceid
     */
    public void deleteFaceNews(String faceid){
        faceNewsDao.deleteByKey(faceid);
        Log.i(TAG, "delete");
    }

    public void deleteAll(){
        faceNewsDao.deleteAll();
    }

    public void deleteFaceNews(FaceNews faceNews){
        faceNewsDao.delete(faceNews);
    }
}
