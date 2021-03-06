package com.cmit.clouddetection.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.cmit.clouddetection.entry.TaskResultDetail;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TASK_RESULT_DETAIL".
*/
public class TaskResultDetailDao extends AbstractDao<TaskResultDetail, Void> {

    public static final String TABLENAME = "TASK_RESULT_DETAIL";

    /**
     * Properties of entity TaskResultDetail.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, int.class, "id", false, "ID");
        public final static Property ScriptId = new Property(1, int.class, "scriptId", false, "SCRIPT_ID");
        public final static Property SerialNum = new Property(2, double.class, "serialNum", false, "SERIAL_NUM");
        public final static Property OperateNum = new Property(3, int.class, "operateNum", false, "OPERATE_NUM");
        public final static Property ParamValue = new Property(4, String.class, "paramValue", false, "PARAM_VALUE");
        public final static Property SuccessKeyword = new Property(5, String.class, "successKeyword", false, "SUCCESS_KEYWORD");
        public final static Property IsTimeStamp = new Property(6, int.class, "isTimeStamp", false, "IS_TIME_STAMP");
        public final static Property RunningResult = new Property(7, String.class, "runningResult", false, "RUNNING_RESULT");
        public final static Property SingleStepBeginTime = new Property(8, String.class, "singleStepBeginTime", false, "SINGLE_STEP_BEGIN_TIME");
        public final static Property SingleStepEndTime = new Property(9, String.class, "singleStepEndTime", false, "SINGLE_STEP_END_TIME");
        public final static Property ResultScreenShot = new Property(10, String.class, "resultScreenShot", false, "RESULT_SCREEN_SHOT");
    }


    public TaskResultDetailDao(DaoConfig config) {
        super(config);
    }
    
    public TaskResultDetailDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TASK_RESULT_DETAIL\" (" + //
                "\"ID\" INTEGER NOT NULL ," + // 0: id
                "\"SCRIPT_ID\" INTEGER NOT NULL ," + // 1: scriptId
                "\"SERIAL_NUM\" REAL NOT NULL ," + // 2: serialNum
                "\"OPERATE_NUM\" INTEGER NOT NULL ," + // 3: operateNum
                "\"PARAM_VALUE\" TEXT," + // 4: paramValue
                "\"SUCCESS_KEYWORD\" TEXT," + // 5: successKeyword
                "\"IS_TIME_STAMP\" INTEGER NOT NULL ," + // 6: isTimeStamp
                "\"RUNNING_RESULT\" TEXT," + // 7: runningResult
                "\"SINGLE_STEP_BEGIN_TIME\" TEXT," + // 8: singleStepBeginTime
                "\"SINGLE_STEP_END_TIME\" TEXT," + // 9: singleStepEndTime
                "\"RESULT_SCREEN_SHOT\" TEXT);"); // 10: resultScreenShot
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TASK_RESULT_DETAIL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TaskResultDetail entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getScriptId());
        stmt.bindDouble(3, entity.getSerialNum());
        stmt.bindLong(4, entity.getOperateNum());
 
        String paramValue = entity.getParamValue();
        if (paramValue != null) {
            stmt.bindString(5, paramValue);
        }
 
        String successKeyword = entity.getSuccessKeyword();
        if (successKeyword != null) {
            stmt.bindString(6, successKeyword);
        }
        stmt.bindLong(7, entity.getIsTimeStamp());
 
        String runningResult = entity.getRunningResult();
        if (runningResult != null) {
            stmt.bindString(8, runningResult);
        }
 
        String singleStepBeginTime = entity.getSingleStepBeginTime();
        if (singleStepBeginTime != null) {
            stmt.bindString(9, singleStepBeginTime);
        }
 
        String singleStepEndTime = entity.getSingleStepEndTime();
        if (singleStepEndTime != null) {
            stmt.bindString(10, singleStepEndTime);
        }
 
        String resultScreenShot = entity.getResultScreenShot();
        if (resultScreenShot != null) {
            stmt.bindString(11, resultScreenShot);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TaskResultDetail entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getScriptId());
        stmt.bindDouble(3, entity.getSerialNum());
        stmt.bindLong(4, entity.getOperateNum());
 
        String paramValue = entity.getParamValue();
        if (paramValue != null) {
            stmt.bindString(5, paramValue);
        }
 
        String successKeyword = entity.getSuccessKeyword();
        if (successKeyword != null) {
            stmt.bindString(6, successKeyword);
        }
        stmt.bindLong(7, entity.getIsTimeStamp());
 
        String runningResult = entity.getRunningResult();
        if (runningResult != null) {
            stmt.bindString(8, runningResult);
        }
 
        String singleStepBeginTime = entity.getSingleStepBeginTime();
        if (singleStepBeginTime != null) {
            stmt.bindString(9, singleStepBeginTime);
        }
 
        String singleStepEndTime = entity.getSingleStepEndTime();
        if (singleStepEndTime != null) {
            stmt.bindString(10, singleStepEndTime);
        }
 
        String resultScreenShot = entity.getResultScreenShot();
        if (resultScreenShot != null) {
            stmt.bindString(11, resultScreenShot);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public TaskResultDetail readEntity(Cursor cursor, int offset) {
        TaskResultDetail entity = new TaskResultDetail( //
            cursor.getInt(offset + 0), // id
            cursor.getInt(offset + 1), // scriptId
            cursor.getDouble(offset + 2), // serialNum
            cursor.getInt(offset + 3), // operateNum
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // paramValue
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // successKeyword
            cursor.getInt(offset + 6), // isTimeStamp
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // runningResult
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // singleStepBeginTime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // singleStepEndTime
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // resultScreenShot
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TaskResultDetail entity, int offset) {
        entity.setId(cursor.getInt(offset + 0));
        entity.setScriptId(cursor.getInt(offset + 1));
        entity.setSerialNum(cursor.getDouble(offset + 2));
        entity.setOperateNum(cursor.getInt(offset + 3));
        entity.setParamValue(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSuccessKeyword(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIsTimeStamp(cursor.getInt(offset + 6));
        entity.setRunningResult(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSingleStepBeginTime(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setSingleStepEndTime(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setResultScreenShot(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(TaskResultDetail entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(TaskResultDetail entity) {
        return null;
    }

    @Override
    public boolean hasKey(TaskResultDetail entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
