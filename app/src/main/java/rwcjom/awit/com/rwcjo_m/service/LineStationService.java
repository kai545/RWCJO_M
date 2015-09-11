package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;
import rwcjom.awit.com.rwcjo_m.dao.LineStation;
import rwcjom.awit.com.rwcjo_m.dao.LineStationDao;

/**
 * Created by Fantasy on 15/8/27.
 */
public class LineStationService {
    private static final String TAG = LineStationService.class.getSimpleName();
    private static LineStationService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private LineStationDao lineStationDao;


    private LineStationService() {
    }

    public static LineStationService getInstance(Context context) {
        if (instance == null) {
            instance = new LineStationService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.lineStationDao = instance.mDaoSession.getLineStationDao();
        }
        return instance;
    }

    public List<LineStation> loadAllLineStation(){
        return lineStationDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<LineStation> queryLineStation(String where, String... params){
        return lineStationDao.queryRaw(where, params);
    }

    public LineStation queryLineStationByBFCODE(String bcode,String fcode,String f_lc){
        List<LineStation> lineStationList=queryLineStation(" where f_lc=? and sb=? and sf=?", f_lc,bcode,fcode);
        if (lineStationList.size()>0){
            return lineStationList.get(0);//返回第一条结果
        }else{
            return null;
        }
    }

    /**
     * insert or update note
     * @param LineStation
     * @return insert or update note id
     */
    public long saveLineStation(LineStation lineStation){
        return lineStationDao.insertOrReplace(lineStation);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveLineStationLists(final List<LineStation> list){
        if(list == null || list.isEmpty()){
            return;
        }
        lineStationDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    LineStation lineStation = list.get(i);
                    lineStationDao.insertOrReplace(lineStation);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllLineStation(){
        lineStationDao.deleteAll();
    }



    public void deleteAll(){
        lineStationDao.deleteAll();
    }

    public void deleteLineStation(LineStation lineStation){
        lineStationDao.delete(lineStation);
    }
}
