package com.eighteen.goradar.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eighteen.goradar.model.GamesModel;

import java.util.ArrayList;

/**
 * userinfo增删改查等功能的使用
 */
public class GamesDao {
	public GamesDao() {

	}

	//根据语言查询数据
	public ArrayList<GamesModel> query() {
//		Log.i("tag","读取"+language+"语言数据");
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("H5Game.db");
		ArrayList<GamesModel> list = new ArrayList<GamesModel>();
		String sql="select * from H5game";
		Cursor cursor = db.rawQuery(sql, null);
		try {
			list = parseChatCursorToList(cursor);
			Log.e("tag","根据语言查询data"+list.size());
		} catch (Exception e) {

		}
		return list;
	}

	/*//查询全部
	public ArrayList<GamesModel> queryAll() {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("game.db");
		ArrayList<GamesModel> list = new ArrayList<GamesModel>();
		String sql="select * from H5game";
		Cursor cursor = db.rawQuery(sql, null);
		try {
			list = parseChatCursorToList(cursor);
			Log.e("tag","根据语言查询data"+list.size());
		} catch (Exception e) {

		}
		return list;
	}*/

	public ArrayList<GamesModel> parseChatCursorToList(Cursor cursor) {
		ArrayList<GamesModel> GamesModelArrayList = new ArrayList<GamesModel>();
		while (cursor.moveToNext()) {
			GamesModel GamesModel = new GamesModel();
			if (cursor != null && cursor.getCount() != 0) {
				GamesModel.setGame_name(cursor.getString(cursor.getColumnIndex("Game_name")));
				GamesModel.setUrl(cursor.getString(cursor.getColumnIndex("URl")));
				GamesModelArrayList.add(GamesModel);
			}
		}
		Log.e("tag","alldataGames"+GamesModelArrayList.toString());
		if (cursor != null) {
			cursor.close();
		}
		return GamesModelArrayList;
	}


	/** 关闭数据库 */
	public synchronized void closeDB(AssetsDatabaseManager mg) {
		// TODO Auto-generated method stub
		if (mg != null) {
			mg.closeAllDatabase();
		}
	}

}
