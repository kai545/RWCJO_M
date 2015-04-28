package rwcjom.awit.com.rwcjo_m.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import rwcjom.awit.com.rwcjo_m.dao.BwInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table BW_INFO.
*/
public class BwInfoDao extends AbstractDao<BwInfo, Long> {

    public static final String TABLENAME = "BW_INFO";

    /**
     * Properties of entity BwInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Bwid = new Property(0, Long.class, "bwid", true, "BWID");
        public final static Property Id = new Property(1, String.class, "id", false, "ID");
        public final static Property Od = new Property(2, String.class, "od", false, "OD");
        public final static Property Ty = new Property(3, String.class, "ty", false, "TY");
        public final static Property F_lc = new Property(4, String.class, "f_lc", false, "F_LC");
    };

    private DaoSession daoSession;

    private Query<BwInfo> line_BwInfoListQuery;

    public BwInfoDao(DaoConfig config) {
        super(config);
    }
    
    public BwInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'BW_INFO' (" + //
                "'BWID' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: bwid
                "'ID' TEXT," + // 1: id
                "'OD' TEXT," + // 2: od
                "'TY' TEXT," + // 3: ty
                "'F_LC' TEXT NOT NULL );"); // 4: f_lc
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BW_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, BwInfo entity) {
        stmt.clearBindings();
 
        Long bwid = entity.getBwid();
        if (bwid != null) {
            stmt.bindLong(1, bwid);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String od = entity.getOd();
        if (od != null) {
            stmt.bindString(3, od);
        }
 
        String ty = entity.getTy();
        if (ty != null) {
            stmt.bindString(4, ty);
        }
        stmt.bindString(5, entity.getF_lc());
    }

    @Override
    protected void attachEntity(BwInfo entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public BwInfo readEntity(Cursor cursor, int offset) {
        BwInfo entity = new BwInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // bwid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // od
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // ty
            cursor.getString(offset + 4) // f_lc
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, BwInfo entity, int offset) {
        entity.setBwid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setOd(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTy(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setF_lc(cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(BwInfo entity, long rowId) {
        entity.setBwid(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(BwInfo entity) {
        if(entity != null) {
            return entity.getBwid();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "bwInfoList" to-many relationship of Line. */
    public List<BwInfo> _queryLine_BwInfoList(String f_lc) {
        synchronized (this) {
            if (line_BwInfoListQuery == null) {
                QueryBuilder<BwInfo> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.F_lc.eq(null));
                line_BwInfoListQuery = queryBuilder.build();
            }
        }
        Query<BwInfo> query = line_BwInfoListQuery.forCurrentThread();
        query.setParameter(0, f_lc);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getLineDao().getAllColumns());
            builder.append(" FROM BW_INFO T");
            builder.append(" LEFT JOIN LINE T0 ON T.'F_LC'=T0.'LC'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected BwInfo loadCurrentDeep(Cursor cursor, boolean lock) {
        BwInfo entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Line line = loadCurrentOther(daoSession.getLineDao(), cursor, offset);
         if(line != null) {
            entity.setLine(line);
        }

        return entity;    
    }

    public BwInfo loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<BwInfo> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<BwInfo> list = new ArrayList<BwInfo>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<BwInfo> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<BwInfo> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
