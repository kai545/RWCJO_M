package rwcjom.awit.com.rwcjo_m.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import rwcjom.awit.com.rwcjo_m.dao.FaceInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table FACE_INFO.
*/
public class FaceInfoDao extends AbstractDao<FaceInfo, String> {

    public static final String TABLENAME = "FACE_INFO";

    /**
     * Properties of entity FaceInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Faceid = new Property(0, String.class, "faceid", true, "FACEID");
        public final static Property Jointflag = new Property(1, String.class, "jointflag", false, "JOINTFLAG");
        public final static Property Structtype = new Property(2, String.class, "structtype", false, "STRUCTTYPE");
        public final static Property Structname = new Property(3, String.class, "structname", false, "STRUCTNAME");
        public final static Property Structbase = new Property(4, String.class, "structbase", false, "STRUCTBASE");
        public final static Property Designatt = new Property(5, String.class, "designatt", false, "DESIGNATT");
        public final static Property Piernum = new Property(6, String.class, "piernum", false, "PIERNUM");
        public final static Property Dkname = new Property(7, String.class, "dkname", false, "DKNAME");
        public final static Property Dkilo = new Property(8, String.class, "dkilo", false, "DKILO");
        public final static Property Dchain = new Property(9, String.class, "dchain", false, "DCHAIN");
        public final static Property Rkname = new Property(10, String.class, "rkname", false, "RKNAME");
        public final static Property Rkilo = new Property(11, String.class, "rkilo", false, "RKILO");
        public final static Property Rchain = new Property(12, String.class, "rchain", false, "RCHAIN");
        public final static Property Remark = new Property(13, String.class, "remark", false, "REMARK");
        public final static Property F_siteid = new Property(14, String.class, "f_siteid", false, "F_SITEID");
    };


    public FaceInfoDao(DaoConfig config) {
        super(config);
    }
    
    public FaceInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'FACE_INFO' (" + //
                "'FACEID' TEXT PRIMARY KEY NOT NULL ," + // 0: faceid
                "'JOINTFLAG' TEXT," + // 1: jointflag
                "'STRUCTTYPE' TEXT," + // 2: structtype
                "'STRUCTNAME' TEXT," + // 3: structname
                "'STRUCTBASE' TEXT," + // 4: structbase
                "'DESIGNATT' TEXT," + // 5: designatt
                "'PIERNUM' TEXT," + // 6: piernum
                "'DKNAME' TEXT," + // 7: dkname
                "'DKILO' TEXT," + // 8: dkilo
                "'DCHAIN' TEXT," + // 9: dchain
                "'RKNAME' TEXT," + // 10: rkname
                "'RKILO' TEXT," + // 11: rkilo
                "'RCHAIN' TEXT," + // 12: rchain
                "'REMARK' TEXT," + // 13: remark
                "'F_SITEID' TEXT);"); // 14: f_siteid
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'FACE_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, FaceInfo entity) {
        stmt.clearBindings();
 
        String faceid = entity.getFaceid();
        if (faceid != null) {
            stmt.bindString(1, faceid);
        }
 
        String jointflag = entity.getJointflag();
        if (jointflag != null) {
            stmt.bindString(2, jointflag);
        }
 
        String structtype = entity.getStructtype();
        if (structtype != null) {
            stmt.bindString(3, structtype);
        }
 
        String structname = entity.getStructname();
        if (structname != null) {
            stmt.bindString(4, structname);
        }
 
        String structbase = entity.getStructbase();
        if (structbase != null) {
            stmt.bindString(5, structbase);
        }
 
        String designatt = entity.getDesignatt();
        if (designatt != null) {
            stmt.bindString(6, designatt);
        }
 
        String piernum = entity.getPiernum();
        if (piernum != null) {
            stmt.bindString(7, piernum);
        }
 
        String dkname = entity.getDkname();
        if (dkname != null) {
            stmt.bindString(8, dkname);
        }
 
        String dkilo = entity.getDkilo();
        if (dkilo != null) {
            stmt.bindString(9, dkilo);
        }
 
        String dchain = entity.getDchain();
        if (dchain != null) {
            stmt.bindString(10, dchain);
        }
 
        String rkname = entity.getRkname();
        if (rkname != null) {
            stmt.bindString(11, rkname);
        }
 
        String rkilo = entity.getRkilo();
        if (rkilo != null) {
            stmt.bindString(12, rkilo);
        }
 
        String rchain = entity.getRchain();
        if (rchain != null) {
            stmt.bindString(13, rchain);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(14, remark);
        }
 
        String f_siteid = entity.getF_siteid();
        if (f_siteid != null) {
            stmt.bindString(15, f_siteid);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public FaceInfo readEntity(Cursor cursor, int offset) {
        FaceInfo entity = new FaceInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // faceid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // jointflag
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // structtype
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // structname
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // structbase
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // designatt
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // piernum
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // dkname
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // dkilo
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // dchain
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // rkname
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // rkilo
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // rchain
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // remark
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14) // f_siteid
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, FaceInfo entity, int offset) {
        entity.setFaceid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setJointflag(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setStructtype(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setStructname(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStructbase(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDesignatt(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPiernum(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDkname(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDkilo(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setDchain(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setRkname(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setRkilo(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setRchain(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setRemark(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setF_siteid(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(FaceInfo entity, long rowId) {
        return entity.getFaceid();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(FaceInfo entity) {
        if(entity != null) {
            return entity.getFaceid();
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
