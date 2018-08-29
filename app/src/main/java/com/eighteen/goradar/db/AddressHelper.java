package com.eighteen.goradar.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eighteen.goradar.model.DataModel;
import com.eighteen.goradar.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddressHelper {
	// 数据库名称
	public static final String DATABASE_NAME = "pokemon";
	private static final String TABLE_Province = "sqlitelist";
	// 数据库version
    private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase myDataBase;


	private  void initAddressDB(Context context) {
		File f = context.getFileStreamPath(DATABASE_NAME);
		if(f.exists()){
			return ;
		}
		
		// 打开静态数据库文件的输入流
		InputStream is = context.getResources().openRawResource(
				R.raw.pokemon);
		// 通过Context类来打开目标数据库文件的输出流
		FileOutputStream os = null;
		try {
			os = context.openFileOutput(DATABASE_NAME, Context.MODE_PRIVATE);
			byte[] buffer = new byte[512];
			int count = 0;
			// 将静态数据库文件拷贝到目的地
			while ((count = is.read(buffer)) > 0) {
				os.write(buffer, 0, count);
				os.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



	public synchronized void OpenDatabase(Context context){
		if (myDataBase != null && myDataBase.isOpen() ){
			return ;
		}
		initAddressDB(context);
		myDataBase = SQLiteDatabase.openDatabase(context.getFileStreamPath(DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
	}



	public synchronized void close() {
		if(myDataBase != null){
			myDataBase.close();
			myDataBase = null;
		}
	}



	

    //根据语言查询数据
	public ArrayList<DataModel> queryLanguage(String language) {

		ArrayList<DataModel> list = new ArrayList<DataModel>();
		String sql="select * from sqlitelist where language='" + language +"'";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		try {
			list = parseChatCursorToList(cursor);
//			Log.e("tag","根据语言查询data"+list.size());
		} catch (Exception e) {

		}
		return list;
	}

	//查询全部
	public ArrayList<DataModel> queryAll() {
		ArrayList<DataModel> list = new ArrayList<DataModel>();
		Cursor cursor=myDataBase.query(TABLE_Province, null, null, null, null,null,null);

		try {
			list = parseChatCursorToList(cursor);

		}catch (Exception e) {

		}
		return list;
	}

	public ArrayList<DataModel> parseChatCursorToList(Cursor cursor) {
		ArrayList<DataModel> chatListModels = new ArrayList<DataModel>();
		while (cursor.moveToNext()) {
			DataModel dataModel = new DataModel();
			if (cursor != null && cursor.getCount() != 0) {
				dataModel.setType_1(cursor.getString(cursor.getColumnIndex(ChatRoomsColumns.type_1)));
				dataModel.setType_2(cursor.getString(cursor.getColumnIndex(ChatRoomsColumns.type_2)));
				dataModel.setListid(cursor.getString(cursor.getColumnIndex(ChatRoomsColumns.listid)));
				dataModel.setName(cursor.getString(cursor.getColumnIndex(ChatRoomsColumns.name)));
				dataModel.setLanguage(cursor.getString(cursor.getColumnIndex(ChatRoomsColumns.language)));

				chatListModels.add(dataModel);
			}
		}
//		Log.e("tag","alldata"+chatListModels.size());
		if (cursor != null) {
			cursor.close();
		}
		return chatListModels;
	}


	public synchronized void update(String id,String num){
		String sql= "update " + TABLE_Province + " set name = '"+num +"' where id= " + id;
		//		Log.e("TAG", "sql:"+sql);
		myDataBase.execSQL(sql);
		//		Log.e("TAG", "sql:"+sql);
	}



}
