package com.xiaoli.library.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * sqllite操作基类
 *  xiaokx
 *  hioyes@qq.com
 *  2014-11-6
 */
public class DbHelper<T> {

    protected SqliteHelper sqliteHelper;
    protected SQLiteDatabase db;

    public DbHelper(Context context) {
        sqliteHelper = new SqliteHelper(context);
        db = sqliteHelper.getWritableDatabase();
    }

    /**
     * 构建db工具类
     * @param context
     * @param dbPath 数据库保存目录,必须斜杠结尾
     * @param dbName 数据库名称
     */
    public DbHelper(Context context,String dbPath,String dbName){
        sqliteHelper = new SqliteHelper(context,dbPath,dbName);
        db = sqliteHelper.getWritableDatabase();
    }

    /**
     * 插入表记录(单条)
     *
     * @param table  表名称
     * @param fields 字段键值对
     */
    public void insert(String table, Map fields) {
        String sql = "";

        try {
            table = DaoUtils.quoteCol(table);

            Object[] cols = fields.keySet().toArray();
            Object[] values = new Object[cols.length];
            for (int i = 0; i < cols.length; i++) {
                if (fields.get(cols[i]) == null) {
                    values[i] = null;
                } else {
                    values[i] = fields.get(cols[i]).toString();
                }
                cols[i] = DaoUtils.quoteCol(cols[i].toString());
            }

            sql = "INSERT INTO " + table + " ("
                    + DaoUtils.implode(", ", cols) + ") VALUES ("
                    + DaoUtils.implodeValue(", ", values) + ");";
            Log.e("sql->", sql);
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("插入表数据错误：" + sql);
        }
    }


    /**
     * 插入表记录
     *
     * @param table 表名称
     * @param vo    值对象
     */
    public void insert(String table, T vo) {
        this.insert(table, DaoUtils.voToMap(vo));
    }

    /**
     * 插入表记录(单条),并返回主键id
     *
     * @param table
     * @param fields
     * @return 主键id 失败为-1
     */
    public long insertRecord(String table, Map fields) {
        ContentValues cv = new ContentValues();
        Iterator entries = fields.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            if (entry.getValue() == null) {
                cv.putNull(entry.getKey().toString());
            } else {
                cv.put(entry.getKey().toString(), entry.getValue().toString());
            }

        }
        return db.insert(table, null, cv);
    }

    /**
     * 插入表记录(单条),并返回主键id
     *
     * @param table
     * @param vo
     * @return 主键id, 失败为-1
     */
    public long insertRecord(String table, T vo) {
        Map fields = DaoUtils.voToMap(vo);
        return this.insertRecord(table, fields);
    }

    /**
     * 表记录更新
     *
     * @param table  表名称
     * @param fields 字段键值对
     * @param where  更新条件
     */
    public void update(String table, Map fields, String where) {
        String sql = "";
        try {
            table = DaoUtils.quoteCol(table);

            // 字段值
            Object[] cols = fields.keySet().toArray();

            Object[] values = new Object[cols.length];
            for (int i = 0; i < cols.length; i++) {
                if (fields.get(cols[i]) == null) {
                    values[i] = null;
                } else {
                    values[i] = "'" + fields.get(cols[i]).toString() + "'";
                }
                cols[i] = DaoUtils.quoteCol(cols[i].toString()) + "=" + values[i];

            }
            sql = "UPDATE " + table + " SET " + DaoUtils.implode(", ", cols)
                    + " WHERE " + where;

            db.execSQL(sql);
        } catch (Exception e) {
            throw new RuntimeException("更新表记录错误：" + sql);
        }
    }

    /**
     * 表记录更新
     *
     * @param table 表名称
     * @param vo    值对象
     * @param where 更新条件
     */
    public void update(String table, T vo, String where) {
        this.update(table, DaoUtils.voToMap(vo), where);
    }

    /**
     * 删除表数据
     *
     * @param table 表名称
     * @param where 删除条件
     */
    public void delete(String table, String where) {
        db.execSQL("delete from  " + table + " where " + where);
    }

    /**
     * 执行一个sql语句
     *
     * @param sql
     */
    public void execute(String sql) {
        db.execSQL(sql);
    }

    /**
     * 查询记录总数
     *
     * @param table
     * @param where
     */
    public int getTotal(String table, String where) {
        String sql = "select count(0) as count from " + table;
        if (where != null && !"".equals(where)) {
            sql += " where " + where;
        }
        Cursor c = db.rawQuery(sql, new String[]{});
        try {
            if (c.moveToNext()) {
                return c.getInt(c.getColumnIndex("count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
        return 0;
    }

    /**
     * 查询一个一行表记录
     *
     * @param sql
     * @return
     */
    public T queryForObject(String sql, Class clazz) {
        Log.e("queryForObject->", sql);
        Cursor c = db.rawQuery(sql, new String[]{});
        Log.e("queryForObject->", c.getCount() + "");
        try {
            if (c.moveToNext()) {
                return DaoUtils.cursorToVo(c, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
        return null;
    }

    public List<T> queryForList(String sql, Class clazz) {
        List<T> list = new ArrayList<T>();
        Log.e("queryForList->", sql);
        Cursor c = db.rawQuery(sql, new String[]{});
        Log.e("queryForList->", c.getCount() + "");
        try {
            while (c.moveToNext()) {
                list.add((T) DaoUtils.cursorToVo(c, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            c.close();
        }
        return list;
    }
}
