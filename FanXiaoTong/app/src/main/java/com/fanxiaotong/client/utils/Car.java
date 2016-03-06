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
 * ���ﳵ���ݿ�������
 * 
 * @date 2013.12.27 3:18
 * 
 */
public class Car extends SQLiteOpenHelper {
	private final static int VERSION = 1;// �汾��
	private final static String DATABASE_NAME = "cart.db";
	private final static String TABLE_NAME = "table_cart";
	public final static String ID = "id";// ����ContentProviderʹ��
	public final static String FOOD_ID = "food_id";
	public final static String STORE_ID = "store_id";
	public final static String FOOD_NAME = "food_name";
	public final static String COUNT = "count";
	public final static String COST = "cost";
	public final static String NOTICE = "notice";
	public final static String USER_PHONE_NUMBER = "user_phone_number";
	public final static String FOOD_PHOTO_NAME = "food_photo_name";
	public final static String FLAVOR="flavor";//��ζ
	public final static String JARDINIERE="jardiniere";//���
	
	
	
	
	//ID,FOOD_ID,STORE_ID,FOOD_NAME,COUNT,COST
	public Car(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		/*
		 * �޸�
		 * 2014.1.10
		 */
		String strCreateSql = "CREATE TABLE " + TABLE_NAME + "(" + ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + FOOD_ID + " INTEGER,"
				+ STORE_ID + " INTEGER," + FOOD_NAME + " TEXT," + COUNT
				+ " INTEGER," + COST + " TEXT," + NOTICE + " TEXT," + USER_PHONE_NUMBER + 
				" INTEGER," + FOOD_PHOTO_NAME + " TEXT," + FLAVOR + " TEXT," + JARDINIERE + " TEXT);";
		try {
			Li.printlnLog("444444444444444444444444444444444444444444444444");
			Li.printlnLog("�������ݿ⿪ʼ����������������������");
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
	// ��ѯ�������� ;
	/*
	 * ����1������
	 * ����2���������ݰ���������Ϣ��String������ŵĶ�������
	 * ����3���൱��sql���where��sql��where��д�����ݷŵ�������ˣ����磺tage>?
	 * ����4��������ڲ���3��д��?��֪����Ϊʲôдtage>?�˰ɣ����Ǹ�������Ǵ���?��ֵ ��������new String[]{"30"}
	 * ����5�����飬�������ˣ��������ʹ�null 
	 * ����6��having���벻�����Ŀ���SQL 
	 * ����7��orderBy����
	 */
	public Cursor select() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null,null);
		return cursor;
	}
	
	public Cursor selectByfoodID(int foodId) {
		SQLiteDatabase db = this.getReadableDatabase();
		String where = FOOD_ID + "=?";
		/*
		 * �޸�
		 */
		Cursor cursor = db.query(TABLE_NAME, new String[]{ID,FOOD_ID,STORE_ID,FOOD_NAME,COUNT,COST,NOTICE,
				USER_PHONE_NUMBER,FLAVOR,JARDINIERE}, where, new String[]{String.valueOf(foodId)}, null, null, null);
		return cursor;
	}
	// �������;
	public long insert(int foodId, int storeId, String foodName, int count,
			float cost, String notice, String userPhoneNumber,String foodPhotoName,String flavor,String jardiniere) {
		// ��ȡ��дSQLiteDatabase����
		SQLiteDatabase db = this.getWritableDatabase();
		// ContentValues����map��������Ǽ�ֵ��
		ContentValues contentValues = new ContentValues();

		contentValues.put(FOOD_ID, foodId);
		contentValues.put(STORE_ID, storeId);
		contentValues.put(FOOD_NAME, foodName);
		contentValues.put(COUNT, count);
		contentValues.put(COST, String.valueOf(cost));
		contentValues.put(NOTICE, notice);
		contentValues.put(USER_PHONE_NUMBER, userPhoneNumber);
		contentValues.put(FOOD_PHOTO_NAME, foodPhotoName); 
		contentValues.put(FLAVOR, flavor);
		contentValues.put(JARDINIERE, jardiniere);
		Li.printlnLog("���ݿ�������ݿ�ʼ����������������������������");
		long num = db.insert(TABLE_NAME, null, contentValues);
		Li.printlnLog("���ݲ��������������������������������");
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
	

	public void deleteByPhoneNum(String phoneNumber)

	{

		SQLiteDatabase db = this.getWritableDatabase();

		String where = USER_PHONE_NUMBER + " = ?";

		String[] whereValue = {phoneNumber};

		db.delete(TABLE_NAME, where, whereValue);

		db.close();
	}
	// ���¼�¼;
	/*
	 * �޸�
	 */
	public void update(int id, int foodId, int storeId, String foodName,
			int count, float cost, String notice, String userPhoneNumber,String foodPhotoName,String flavor,String jardiniere) {

		SQLiteDatabase db = this.getWritableDatabase();

		String where = ID + "=?";
		String[] whereValue = { Integer.toString(id) };

		ContentValues contentValues = new ContentValues();

//		contentValues.put(ID, id);
		contentValues.put(FOOD_ID, foodId);
		contentValues.put(STORE_ID, storeId);
		contentValues.put(FOOD_NAME, foodName);
		contentValues.put(COUNT, count);
		contentValues.put(COST, String.valueOf(cost));
		contentValues.put(NOTICE, notice);
		contentValues.put(USER_PHONE_NUMBER, userPhoneNumber);
		contentValues.put(FOOD_PHOTO_NAME, foodPhotoName);
		contentValues.put(FLAVOR, flavor);
		contentValues.put(JARDINIERE, jardiniere);
		
		
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
