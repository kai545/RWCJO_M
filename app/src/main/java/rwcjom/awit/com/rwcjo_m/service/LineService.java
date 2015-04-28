package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;
import rwcjom.awit.com.rwcjo_m.dao.Line;
import rwcjom.awit.com.rwcjo_m.dao.LineDao;

/**
 * Created by Fantasy on 15/4/28.
 */
public class LineService {
    private static final String TAG = LineService.class.getSimpleName();
    private static LineService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private LineDao lineDao;


    private LineService() {
    }

    public static LineService getInstance(Context context) {
        if (instance == null) {
            instance = new LineService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.lineDao = instance.mDaoSession.getLineDao();
        }
        return instance;
    }

    public List<Line> loadAllLine(){
        return lineDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<Line> queryLine(String where, String... params){
        return lineDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param line
     * @return insert or update note id
     */
    public long saveLine(Line line){
        return lineDao.insertOrReplace(line);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveLineLists(final List<Line> list){
        if(list == null || list.isEmpty()){
            return;
        }
        lineDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    Line line = list.get(i);
                    lineDao.insertOrReplace(line);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllLine(){
        lineDao.deleteAll();
    }

    /**
     * delete note by id
     * @param lc
     */
    public void deleteLine(String lc){
        lineDao.deleteByKey(lc);
        Log.i(TAG, "delete");
    }

    public void deleteLine(Line line){
        lineDao.delete(line);
    }
}
