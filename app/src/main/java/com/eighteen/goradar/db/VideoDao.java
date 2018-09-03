package com.eighteen.goradar.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eighteen.goradar.model.VideoModel;

import java.util.ArrayList;

/**
 * userinfo增删改查等功能的使用
 */
public class VideoDao {
	private static final String TAG = VideoDao.class.getSimpleName();

	public VideoDao() {

	}

	/**
	 * 查询预览数据
	 * @return
	 */
	public ArrayList<VideoModel> queryAll() {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("videolist.db");
		ArrayList<VideoModel> list = new ArrayList<VideoModel>();
		String sql="select * from videos";
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//根据语言查询景点数据
	public ArrayList<VideoModel> queryLanguage(String language) {
		Log.e("tag",language);

		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("videolist.db");
		ArrayList<VideoModel> list = new ArrayList<VideoModel>();
		String sql="select * from videos where Language='" + language +"'";
		Log.i(TAG, "queryLanguage: "+sql);
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	//根据语言查询景点数据
	public void insert(VideoModel model) {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("videolist.db");
		//插入数据SQL语句
		String sql = "insert into videolist(id,url)values(?,?)";
		Object[] args = {
				model.getId(),model.getUrl()
		};
		db.execSQL(sql, args);
		db.close();


	}



	//根据id语言查询数据
	public ArrayList<VideoModel> querybyIDLanguage(String language, String attractionid) {
		Log.e("tag",language);
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("videolist.db");
		ArrayList<VideoModel> list = new ArrayList<VideoModel>();
		String sql = "select * from videos" + " where ID='"+attractionid+"' and Language='" + language + "'";
		Log.i("tag", "getData: "+sql);
		try {
			Cursor cursor = db.rawQuery(sql, null);//防止没有这个表，导致崩溃
			list = parseCursorToList(cursor);
		} catch (Exception e) {
			Log.i(TAG, "queryAll: "+e);
		}
		return list;
	}

	public ArrayList<VideoModel> parseCursorToList(Cursor cursor) {
		ArrayList<VideoModel> list = new ArrayList<VideoModel>();
		while (cursor.moveToNext()) {
			VideoModel model = new VideoModel();
			if (cursor != null && cursor.getCount() != 0) {
				model.setLanguage(cursor.getString(cursor.getColumnIndex("Language")));
				model.setId(cursor.getString(cursor.getColumnIndex("ID")));
				model.setUrl(cursor.getString(cursor.getColumnIndex("URL")));
				model.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
				model.setLogo_URL(cursor.getString(cursor.getColumnIndex("Logo_URL")));
//				model.setImageurl(cursor.getString(cursor.getColumnIndex("imgeurl")));
				list.add(model);
			}
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}



	/** 关闭数据库 */
	public synchronized void closeDB(AssetsDatabaseManager mg) {
		// TODO Auto-generated method stub
		if (mg != null) {
			mg.closeAllDatabase();
		}
	}

}
