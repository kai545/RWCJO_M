package rwcjom.awit.com.rwcjo_m.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.activity.BaseApplication;
import rwcjom.awit.com.rwcjo_m.dao.DaoSession;
import rwcjom.awit.com.rwcjo_m.dao.PersonInfo;
import rwcjom.awit.com.rwcjo_m.dao.PersonInfoDao;

/**
 * Created by Fantasy on 15/4/28.
 */
public class PersonInfoService {
    private static final String TAG = PersonInfoService.class.getSimpleName();
    private static PersonInfoService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private PersonInfoDao personInfoDao;


    private PersonInfoService() {
    }

    public static PersonInfoService getInstance(Context context) {
        if (instance == null) {
            instance = new PersonInfoService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.personInfoDao = instance.mDaoSession.getPersonInfoDao();
        }
        return instance;
    }

    public List<PersonInfo> loadAllPersonInfo(){
        return personInfoDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<PersonInfo> queryPersonInfo(String where, String... params){
        return personInfoDao.queryRaw(where, params);
    }

    public List<PersonInfo> queryPersonBySite(String sectid){
        return personInfoDao.queryBuilder()
                .where(PersonInfoDao.Properties.F_sectid.eq(sectid))
                .orderDesc(PersonInfoDao.Properties.Userid)
                .list();
    }
    /**
     * insert or update note
     * @param personInfo
     * @return insert or update note id
     */
    public long savePersonInfo(PersonInfo personInfo){
        return personInfoDao.insertOrReplace(personInfo);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void savePersonInfoLists(final List<PersonInfo> list){
        if(list == null || list.isEmpty()){
            return;
        }
        personInfoDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    PersonInfo personInfo = list.get(i);
                    personInfoDao.insertOrReplace(personInfo);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllPersonInfo(){
        personInfoDao.deleteAll();
    }

    /**
     * delete note by id
     * @param userid
     */
    public void deletePersonInfo(String userid){
        personInfoDao.deleteByKey(userid);
        Log.i(TAG, "delete");
    }

    public void deleteAll(){
        personInfoDao.deleteAll();
    }

    public void deletePersonInfo(PersonInfo personInfo){
        personInfoDao.delete(personInfo);
    }
}
