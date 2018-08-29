package com.eighteen.goradar.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eighteen.goradar.model.SearchModel;
import com.eighteen.goradar.service.Pokemon;

import java.util.ArrayList;

/**
 * userinfo增删改查等功能的使用
 */
public class UserDao {
	public UserDao() {

	}


	//根据语言查询数据
	public ArrayList<SearchModel> queryLanguage(String language) {
//		Log.i("tag","读取"+language+"语言数据");
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("pokemonlist.db");
		ArrayList<SearchModel> list = new ArrayList<SearchModel>();
		String sql="select * from list where language='" + language +"'";
		Cursor cursor = db.rawQuery(sql, null);
		try {
			list = parseChatCursorToList(cursor);
//			Log.e("tag","根据语言查询data"+list.size());
		} catch (Exception e) {

		}
		return list;
	}

	//根据list_id查询name
	public ArrayList<SearchModel> query_list_name(String list_id, String language) {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("pokemonlist.db");
		ArrayList<SearchModel> list = new ArrayList<SearchModel>();
		String sql = "select * from list" + " where list_id='"+list_id+"' and language='" + language + "'";
		Cursor cursor = db.rawQuery(sql, null);
		try {
			list = parseChatCursorToList(cursor);
		}catch (Exception e) {

		}
		return list;
	}


    //根据语言查询英文的name
	public ArrayList<SearchModel> queryPokemon_name(String list_id) {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("pokemonlist.db");
		ArrayList<SearchModel> list = new ArrayList<SearchModel>();
//		String sql="select * from list where list_id='" + list_id +"'";
		String sql = "select * from list" + " where list_id='"+list_id+"' and language='" + "en" + "'";

		Cursor cursor = db.rawQuery(sql, null);
		try {
			list = parseChatCursorToList(cursor);
		}catch (Exception e) {

		}
		return list;
	}

	//根据语言查询英文的name
	public ArrayList<Pokemon> query_name_lan(String list_id) {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("pokemonlist.db");
		ArrayList<Pokemon> list = new ArrayList<Pokemon>();
//		String sql="select * from list where pokemon_name='" + pokemon_name +"'";
//		String sql = "select * from list" + " where pokemon_name='"+pokemon_name+"' and language='" + "en" + "'";
		String sql = "select * from list" + " where list_id='"+list_id+"' and language='" + "en" + "'";
		Cursor cursor = db.rawQuery(sql, null);
		try {
			list = parseChat(cursor);
		}catch (Exception e) {

		}
		return list;
	}


	//根据语言查询英文的name
	public ArrayList<Pokemon> query_name(String pokemon_name) {
		AssetsDatabaseManager assetsDatabaseManager = AssetsDatabaseManager.getManager();
		// 通过管理对象获取数据库
		SQLiteDatabase db = assetsDatabaseManager.getDatabase("pokemonlist.db");
		ArrayList<Pokemon> list = new ArrayList<Pokemon>();
		String sql="select * from list where pokemon_name='" + pokemon_name +"'";
		Cursor cursor = db.rawQuery(sql, null);
		try {
			list = parseChat(cursor);
		}catch (Exception e) {

		}
		return list;
	}

	public ArrayList<Pokemon> parseChat(Cursor cursor) {
		ArrayList<Pokemon> chatListModels = new ArrayList<Pokemon>();
		while (cursor.moveToNext()) {
			Pokemon dataModel = new Pokemon();
			if (cursor != null && cursor.getCount() != 0) {
				dataModel.name=cursor.getString(cursor.getColumnIndex("pokemon_name"));
				dataModel.list_id=cursor.getString(cursor.getColumnIndex("list_id"));
				/*dataModel.setName(cursor.getString(cursor.getColumnIndex("pokemon_name")));
				dataModel.setList_id(cursor.getString(cursor.getColumnIndex("list_id")));
				dataModel.setPokemon_name(cursor.getString(cursor.getColumnIndex("pokemon_name")));
				dataModel.setType1(cursor.getString(cursor.getColumnIndex("type1")));
				dataModel.setType2(cursor.getString(cursor.getColumnIndex("type2")));
				dataModel.setTotal(cursor.getString(cursor.getColumnIndex("total")));
				dataModel.setHp(cursor.getString(cursor.getColumnIndex("hp")));
				dataModel.setAttack(cursor.getString(cursor.getColumnIndex("attack")));
				dataModel.setDefense(cursor.getString(cursor.getColumnIndex("defense")));
				dataModel.setSp_attack(cursor.getString(cursor.getColumnIndex("sp_attack")));
				dataModel.setSp_defense(cursor.getString(cursor.getColumnIndex("sp_defense")));
				dataModel.setSpeed(cursor.getString(cursor.getColumnIndex("speed")));
				dataModel.setLanguage(cursor.getString(cursor.getColumnIndex("language")));*/

				chatListModels.add(dataModel);
			}
		}
		Log.e("tag","alldata"+chatListModels.size());
		if (cursor != null) {
			cursor.close();
		}
		return chatListModels;
	}




	public ArrayList<SearchModel> parseChatCursorToList(Cursor cursor) {
		ArrayList<SearchModel> chatListModels = new ArrayList<SearchModel>();
		while (cursor.moveToNext()) {
			SearchModel dataModel = new SearchModel();
			if (cursor != null && cursor.getCount() != 0) {
				dataModel.setName(cursor.getString(cursor.getColumnIndex("pokemon_name")));
				dataModel.setList_id(cursor.getString(cursor.getColumnIndex("list_id")));
				dataModel.setPokemon_name(cursor.getString(cursor.getColumnIndex("pokemon_name")));
				dataModel.setType1(cursor.getString(cursor.getColumnIndex("type1")));
				dataModel.setType2(cursor.getString(cursor.getColumnIndex("type2")));
				dataModel.setTotal(cursor.getString(cursor.getColumnIndex("total")));
				dataModel.setHp(cursor.getString(cursor.getColumnIndex("hp")));
				dataModel.setAttack(cursor.getString(cursor.getColumnIndex("attack")));
				dataModel.setDefense(cursor.getString(cursor.getColumnIndex("defense")));
				dataModel.setSp_attack(cursor.getString(cursor.getColumnIndex("sp_attack")));
				dataModel.setSp_defense(cursor.getString(cursor.getColumnIndex("sp_defense")));
				dataModel.setSpeed(cursor.getString(cursor.getColumnIndex("speed")));
				dataModel.setLanguage(cursor.getString(cursor.getColumnIndex("language")));

				chatListModels.add(dataModel);
			}
		}
		Log.e("tag","alldata"+chatListModels.size());
		if (cursor != null) {
			cursor.close();
		}
		return chatListModels;
	}


	/** 关闭数据库 */
	public synchronized void closeDB(AssetsDatabaseManager mg) {
		// TODO Auto-generated method stub
		if (mg != null) {
			mg.closeAllDatabase();
		}
	}

}
