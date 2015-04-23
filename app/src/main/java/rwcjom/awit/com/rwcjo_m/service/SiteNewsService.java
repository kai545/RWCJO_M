package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;
import rwcjom.awit.com.rwcjo_m.dao.SiteNews;
import rwcjom.awit.com.rwcjo_m.dao.SiteNewsDao;

/**
 * Created by Fantasy on 15/4/23.
 */
public class SiteNewsService {
    private static final String TAG = SiteNewsService.class.getSimpleName();
    private static SiteNewsService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private SiteNewsDao siteNewsDao;


    private SiteNewsService() {
    }

    public static SiteNewsService getInstance(Context context) {
        if (instance == null) {
            instance = new SiteNewsService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.siteNewsDao = instance.mDaoSession.getSiteNewsDao();
        }
        return instance;
    }


    public SiteNews loadSiteNews(String siteid) {
        return siteNewsDao.load(siteid);
    }

    public List<SiteNews> loadAllSiteNews(){
        return siteNewsDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<SiteNews> querySiteNews(String where, String... params){
        return siteNewsDao.queryRaw(where, params);
    }

    public List<SiteNews> querySiteNewsBySection(String sectid){
        return siteNewsDao.queryBuilder()
                .where(SiteNewsDao.Properties.F_sectionid.eq(sectid))
                .orderDesc(SiteNewsDao.Properties.Siteid)
                .list();
    }


    /**
     * insert or update note
     * @param siteNews
     * @return insert or update note id
     */
    public long saveSiteNews(SiteNews siteNews){
        return siteNewsDao.insertOrReplace(siteNews);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveSiteNewsLists(final List<SiteNews> list){
        if(list == null || list.isEmpty()){
            return;
        }
        siteNewsDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    SiteNews siteNews = list.get(i);
                    siteNewsDao.insertOrReplace(siteNews);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllSiteNews(){
        siteNewsDao.deleteAll();
    }

    /**
     * delete note by id
     * @param siteid
     */
    public void deleteSiteNews(String siteid){
        siteNewsDao.deleteByKey(siteid);
        Log.i(TAG, "delete");
    }

    public void deleteSiteNews(SiteNews siteNews){
        siteNewsDao.delete(siteNews);
    }
}
