package rwcjom.awit.com.rwcjo_m.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import rwcjom.awit.com.rwcjo_m.dao.Line;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table LINE.
*/
public class LineDao extends AbstractDao<Line, String> {

    public static final String TABLENAME = "LINE";

    /**
     * Properties of entity Line.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Lc = new Property(0, String.class, "lc", true, "LC");
        public final static Property Ln = new Property(1, String.class, "ln", false, "LN");
        public final static Property F_sectid = new Property(2, String.class, "f_sectid", false, "F_SECTID");
    };

    private DaoSession daoSession;


    public LineDao(DaoConfig config) {
        super(config);
    }
    
    public LineDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'LINE' (" + //
                "'LC' TEXT PRIMARY KEY NOT NULL ," + // 0: lc
                "'LN' TEXT," + // 1: ln
                "'F_SECTID' TEXT);"); // 2: f_sectid
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'LINE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Line entity) {
        stmt.clearBindings();
 
        String lc = entity.getLc();
        if (lc != null) {
            stmt.bindString(1, lc);
        }
 
        String ln = entity.getLn();
        if (ln != null) {
            stmt.bindString(2, ln);
        }
 
        String f_sectid = entity.getF_sectid();
        if (f_sectid != null) {
            stmt.bindString(3, f_sectid);
        }
    }

    @Override
    protected void attachEntity(Line entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Line readEntity(Cursor cursor, int offset) {
        Line entity = new Line( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // lc
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // ln
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // f_sectid
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Line entity, int offset) {
        entity.setLc(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setLn(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setF_sectid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(Line entity, long rowId) {
        return entity.getLc();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(Line entity) {
        if(entity != null) {
            return entity.getLc();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
