package com.cmit.clouddetection.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.cmit.clouddetection.entry.TaskResult;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TASK_RESULT".
*/
public class TaskResultDao extends AbstractDao<TaskResult, Long> {

    public static final String TABLENAME = "TASK_RESULT";

    /**
     * Properties of entity TaskResult.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property TaskSerial = new Property(1, String.class, "taskSerial", false, "TASK_SERIAL");
        public final static Property InstanceName = new Property(2, String.class, "instanceName", false, "INSTANCE_NAME");
        public final static Property ScriptId = new Property(3, String.class, "scriptId", false, "SCRIPT_ID");
        public final static Property Operator = new Property(4, String.class, "operator", false, "OPERATOR");
        public final static Property Province = new Property(5, String.class, "province", false, "PROVINCE");
        public final static Property Business = new Property(6, String.class, "business", false, "BUSINESS");
        public final static Property Channel = new Property(7, String.class, "channel", false, "CHANNEL");
        public final static Property BeginTime = new Property(8, String.class, "beginTime", false, "BEGIN_TIME");
        public final static Property EndTime = new Property(9, String.class, "endTime", false, "END_TIME");
        public final static Property TaskResult = new Property(10, String.class, "taskResult", false, "TASK_RESULT");
        public final static Property PhoneNum = new Property(11, String.class, "phoneNum", false, "PHONE_NUM");
    }


    public TaskResultDao(DaoConfig config) {
        super(config);
    }
    
    public TaskResultDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TASK_RESULT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TASK_SERIAL\" TEXT," + // 1: taskSerial
                "\"INSTANCE_NAME\" TEXT," + // 2: instanceName
                "\"SCRIPT_ID\" TEXT," + // 3: scriptId
                "\"OPERATOR\" TEXT," + // 4: operator
                "\"PROVINCE\" TEXT," + // 5: province
                "\"BUSINESS\" TEXT," + // 6: business
                "\"CHANNEL\" TEXT," + // 7: channel
                "\"BEGIN_TIME\" TEXT," + // 8: beginTime
                "\"END_TIME\" TEXT," + // 9: endTime
                "\"TASK_RESULT\" TEXT," + // 10: taskResult
                "\"PHONE_NUM\" TEXT);"); // 11: phoneNum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TASK_RESULT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TaskResult entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String taskSerial = entity.getTaskSerial();
        if (taskSerial != null) {
            stmt.bindString(2, taskSerial);
        }
 
        String instanceName = entity.getInstanceName();
        if (instanceName != null) {
            stmt.bindString(3, instanceName);
        }
 
        String scriptId = entity.getScriptId();
        if (scriptId != null) {
            stmt.bindString(4, scriptId);
        }
 
        String operator = entity.getOperator();
        if (operator != null) {
            stmt.bindString(5, operator);
        }
 
        String province = entity.getProvince();
        if (province != null) {
            stmt.bindString(6, province);
        }
 
        String business = entity.getBusiness();
        if (business != null) {
            stmt.bindString(7, business);
        }
 
        String channel = entity.getChannel();
        if (channel != null) {
            stmt.bindString(8, channel);
        }
 
        String beginTime = entity.getBeginTime();
        if (beginTime != null) {
            stmt.bindString(9, beginTime);
        }
 
        String endTime = entity.getEndTime();
        if (endTime != null) {
            stmt.bindString(10, endTime);
        }
 
        String taskResult = entity.getTaskResult();
        if (taskResult != null) {
            stmt.bindString(11, taskResult);
        }
 
        String phoneNum = entity.getPhoneNum();
        if (phoneNum != null) {
            stmt.bindString(12, phoneNum);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TaskResult entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String taskSerial = entity.getTaskSerial();
        if (taskSerial != null) {
            stmt.bindString(2, taskSerial);
        }
 
        String instanceName = entity.getInstanceName();
        if (instanceName != null) {
            stmt.bindString(3, instanceName);
        }
 
        String scriptId = entity.getScriptId();
        if (scriptId != null) {
            stmt.bindString(4, scriptId);
        }
 
        String operator = entity.getOperator();
        if (operator != null) {
            stmt.bindString(5, operator);
        }
 
        String province = entity.getProvince();
        if (province != null) {
            stmt.bindString(6, province);
        }
 
        String business = entity.getBusiness();
        if (business != null) {
            stmt.bindString(7, business);
        }
 
        String channel = entity.getChannel();
        if (channel != null) {
            stmt.bindString(8, channel);
        }
 
        String beginTime = entity.getBeginTime();
        if (beginTime != null) {
            stmt.bindString(9, beginTime);
        }
 
        String endTime = entity.getEndTime();
        if (endTime != null) {
            stmt.bindString(10, endTime);
        }
 
        String taskResult = entity.getTaskResult();
        if (taskResult != null) {
            stmt.bindString(11, taskResult);
        }
 
        String phoneNum = entity.getPhoneNum();
        if (phoneNum != null) {
            stmt.bindString(12, phoneNum);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TaskResult readEntity(Cursor cursor, int offset) {
        TaskResult entity = new TaskResult( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // taskSerial
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // instanceName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // scriptId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // operator
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // province
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // business
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // channel
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // beginTime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // endTime
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // taskResult
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11) // phoneNum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TaskResult entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTaskSerial(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setInstanceName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setScriptId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setOperator(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setProvince(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBusiness(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setChannel(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setBeginTime(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setEndTime(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setTaskResult(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setPhoneNum(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TaskResult entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TaskResult entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TaskResult entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
