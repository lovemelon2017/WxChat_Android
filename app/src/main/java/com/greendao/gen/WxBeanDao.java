package com.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.winderinfo.wechat.bean.WxBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WX_BEAN".
*/
public class WxBeanDao extends AbstractDao<WxBean, Long> {

    public static final String TABLENAME = "WX_BEAN";

    /**
     * Properties of entity WxBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Type = new Property(1, int.class, "type", false, "TYPE");
        public final static Property ChatHead = new Property(2, int.class, "chatHead", false, "CHAT_HEAD");
        public final static Property ChatName = new Property(3, String.class, "chatName", false, "CHAT_NAME");
        public final static Property ChatContent = new Property(4, String.class, "chatContent", false, "CHAT_CONTENT");
        public final static Property ChatType = new Property(5, int.class, "chatType", false, "CHAT_TYPE");
        public final static Property ChatTime = new Property(6, long.class, "chatTime", false, "CHAT_TIME");
        public final static Property ChatNum = new Property(7, String.class, "chatNum", false, "CHAT_NUM");
        public final static Property IsRead = new Property(8, boolean.class, "isRead", false, "IS_READ");
        public final static Property IsLongCheck = new Property(9, boolean.class, "isLongCheck", false, "IS_LONG_CHECK");
        public final static Property IsHideTime = new Property(10, boolean.class, "isHideTime", false, "IS_HIDE_TIME");
    }


    public WxBeanDao(DaoConfig config) {
        super(config);
    }
    
    public WxBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WX_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TYPE\" INTEGER NOT NULL ," + // 1: type
                "\"CHAT_HEAD\" INTEGER NOT NULL ," + // 2: chatHead
                "\"CHAT_NAME\" TEXT," + // 3: chatName
                "\"CHAT_CONTENT\" TEXT," + // 4: chatContent
                "\"CHAT_TYPE\" INTEGER NOT NULL ," + // 5: chatType
                "\"CHAT_TIME\" INTEGER NOT NULL ," + // 6: chatTime
                "\"CHAT_NUM\" TEXT," + // 7: chatNum
                "\"IS_READ\" INTEGER NOT NULL ," + // 8: isRead
                "\"IS_LONG_CHECK\" INTEGER NOT NULL ," + // 9: isLongCheck
                "\"IS_HIDE_TIME\" INTEGER NOT NULL );"); // 10: isHideTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WX_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, WxBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getType());
        stmt.bindLong(3, entity.getChatHead());
 
        String chatName = entity.getChatName();
        if (chatName != null) {
            stmt.bindString(4, chatName);
        }
 
        String chatContent = entity.getChatContent();
        if (chatContent != null) {
            stmt.bindString(5, chatContent);
        }
        stmt.bindLong(6, entity.getChatType());
        stmt.bindLong(7, entity.getChatTime());
 
        String chatNum = entity.getChatNum();
        if (chatNum != null) {
            stmt.bindString(8, chatNum);
        }
        stmt.bindLong(9, entity.getIsRead() ? 1L: 0L);
        stmt.bindLong(10, entity.getIsLongCheck() ? 1L: 0L);
        stmt.bindLong(11, entity.getIsHideTime() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, WxBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getType());
        stmt.bindLong(3, entity.getChatHead());
 
        String chatName = entity.getChatName();
        if (chatName != null) {
            stmt.bindString(4, chatName);
        }
 
        String chatContent = entity.getChatContent();
        if (chatContent != null) {
            stmt.bindString(5, chatContent);
        }
        stmt.bindLong(6, entity.getChatType());
        stmt.bindLong(7, entity.getChatTime());
 
        String chatNum = entity.getChatNum();
        if (chatNum != null) {
            stmt.bindString(8, chatNum);
        }
        stmt.bindLong(9, entity.getIsRead() ? 1L: 0L);
        stmt.bindLong(10, entity.getIsLongCheck() ? 1L: 0L);
        stmt.bindLong(11, entity.getIsHideTime() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public WxBean readEntity(Cursor cursor, int offset) {
        WxBean entity = new WxBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // type
            cursor.getInt(offset + 2), // chatHead
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // chatName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // chatContent
            cursor.getInt(offset + 5), // chatType
            cursor.getLong(offset + 6), // chatTime
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // chatNum
            cursor.getShort(offset + 8) != 0, // isRead
            cursor.getShort(offset + 9) != 0, // isLongCheck
            cursor.getShort(offset + 10) != 0 // isHideTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, WxBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setType(cursor.getInt(offset + 1));
        entity.setChatHead(cursor.getInt(offset + 2));
        entity.setChatName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setChatContent(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setChatType(cursor.getInt(offset + 5));
        entity.setChatTime(cursor.getLong(offset + 6));
        entity.setChatNum(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIsRead(cursor.getShort(offset + 8) != 0);
        entity.setIsLongCheck(cursor.getShort(offset + 9) != 0);
        entity.setIsHideTime(cursor.getShort(offset + 10) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(WxBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(WxBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(WxBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
