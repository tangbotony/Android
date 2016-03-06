package com.fanxiaotong.client.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 用户数据库助手类
 * @date 2013.12.27 3:18
 */
public class Us extends SQLiteOpenHelper {
	private final static int VERSION = 1;// 版本号
	private final static String DATABASE_NAME = "user.db";
	private final static String TABLE_NAME = "table_user";
	public final static String ID = "id";// 后面ContentProvider使用
	public final static String USER_ID = "user_id";
	public final static String USER_PHONE_NUMBER = "phone_number";
	public final static String USER_PASSWORD = "user_password";
	public final static String USER_ADDRESS = "address";
	public final static String USER_lEVEL = "user_level";
	//ID,FOOD_ID,STORE_ID,FOOD_NAME,COUNT,COST
	public Us(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		String strCreateSql = "CREATE TABLE " + TABLE_NAME + "(" + ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_ID + " INTEGER,"
				+ USER_PHONE_NUMBER + " TEXT," + USER_PASSWORD + " TEXT," + USER_ADDRESS
				+ " TEXT," + USER_lEVEL + " INTEGER);";
		try {
			db.execSQL(strCreateSql);
		} catch (SQLException ex) {

			Log.d(TABLE_NAME, "create table failure");
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		try {
			db.execSQL(sql);
			onCreate(db);
			Log.v(DATABASE_NAME, "onUpgrade");
		} catch (SQLException ex) {
			Log.d(DATABASE_NAME, "update data failure");
		}
	}
	// 查询所有数据 ;
	/*
	 * 参数1：表名
	 * 参数2：返回数据包含的列信息，String数组里放的都是列名
	 * 参数3：相当于sql里的where，sql里where后写的内容放到这就行了，例如：tage>?
	 * 参数4：如果你在参数3里写了?（知道我为什么写tage>?了吧），那个这里就是代替?的值 接上例：new String[]{"30"}
	 * 参数5：分组，不解释了，不想分组就传null 
	 * 参数6：having，想不起来的看看SQL 
	 * 参数7：orderBy排序
	 */
	public Cursor select() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}
	public Cursor selectByPhoneNumber(String phoneNumber) {
		SQLiteDatabase db = this.getReadableDatabase();
		String where = USER_PHONE_NUMBER + "=?";
		/*
		 String strCreateSql = "CREATE TABLE " + TABLE_NAME + "(" + ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_ID + " INTEGER,"
				+ USER_PHONE_NUMBER + " TEXT," + USER_REGISTER_DATE + " DATE," + USER_ADDRESS
				+ " TEXT," + USER_lEVEL + " INTEGER);";
		*/
		Cursor cursor = db.query(TABLE_NAME, new String[]{ID, USER_ID, USER_PHONE_NUMBER, USER_PASSWORD, USER_ADDRESS, USER_lEVEL}, where, new String[]{phoneNumber}, null, null, null);
		return cursor;
	}
	// 添加数据;
	public long insert(int userId, String phoneNumber, String userPassword, String address, int level) {
		// 获取可写SQLiteDatabase对象
		SQLiteDatabase db = this.getWritableDatabase();
		// ContentValues类似map，存入的是键值对
		ContentValues contentValues = new ContentValues();
		contentValues.put(USER_ID, userId);
		contentValues.put(USER_PHONE_NUMBER, phoneNumber);
		contentValues.put(USER_PASSWORD, userPassword);
		contentValues.put(USER_ADDRESS, address);
		contentValues.put(USER_lEVEL, String.valueOf(level));
		Li.printlnLog("插入数据库开始。。。。。。。。");
		long num = db.insert(TABLE_NAME, null, contentValues);
		Li.printlnLog("num:"+num);
		Li.printlnLog("插入数据库结束。。。。。。。。");
		db.close();
		return num;
	}
	public void delete(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String where = ID + " = ?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(TABLE_NAME, where, whereValue);
		db.close();
	}
	// 更新记录;
	public void update(int id, int userId, String phoneNumber, String userPassword, String address, int level) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = ID + "=?";
		String[] whereValue = { Integer.toString(id) };
		ContentValues contentValues = new ContentValues();
//		contentValues.put(ID, id);
		contentValues.put(USER_ID, userId);
		contentValues.put(USER_PHONE_NUMBER, phoneNumber);
		contentValues.put(USER_PASSWORD, userPassword);
		contentValues.put(USER_ADDRESS, address);
		contentValues.put(USER_lEVEL, String.valueOf(level));
		db.update(TABLE_NAME, contentValues, where, whereValue);
		db.close();
	}
	public boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = this.getReadableDatabase().getPath();
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY
							| SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}
}
