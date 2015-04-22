package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;
import rwcjom.awit.com.rwcjo_m.dao.SecNews;
import rwcjom.awit.com.rwcjo_m.dao.SecNewsDao;

/**
 * Created by Fantasy on 15/4/22.
 */
public class SecNewsService {
    private static final String TAG = SecNewsService.class.getSimpleName();
    private static SecNewsService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private SecNewsDao secNewsDao;


    private SecNewsService() {
    }

    public static SecNewsService getInstance(Context context) {
        if (instance == null) {
            instance = new SecNewsService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.secNewsDao = instance.mDaoSession.getSecNewsDao();
        }
        return instance;
    }


    public SecNews loadSecNews(String sectid) {
        return secNewsDao.load(sectid);
    }

    public List<SecNews> loadAllSecNews(){
        return secNewsDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<SecNews> querySecNews(String where, String... params){
        return secNewsDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param secNews
     * @return insert or update note id
     */
    public long saveSecNews(SecNews secNews){
        return secNewsDao.insertOrReplace(secNews);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveSecNewsLists(final List<SecNews> list){
        if(list == null || list.isEmpty()){
            return;
        }
        secNewsDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    SecNews secNews = list.get(i);
                    secNewsDao.insertOrReplace(secNews);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllSecNews(){
        secNewsDao.deleteAll();
    }

    /**
     * delete note by id
     * @param sectid
     */
    public void deleteSecNews(String sectid){
        secNewsDao.deleteByKey(sectid);
        Log.i(TAG, "delete");
    }

    public void deleteSecNews(SecNews secNews){
        secNewsDao.delete(secNews);
    }

}
