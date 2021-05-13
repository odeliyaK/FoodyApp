package com.foodyapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MyInfoManager {

	private static MyInfoManager instance = null;
	private Context context = null;
	private DataBase db = null;
	

	public static MyInfoManager getInstance() {
			if (instance == null) {
				instance = new MyInfoManager();
			}
			return instance;
		}

		public static void releaseInstance() {
			if (instance != null) {
				instance.clean();
				instance = null;
			}
		}
		
		private void clean() {

		}


		public Context getContext() {
			return context;
			
		}

		public void openDataBase(Context context) {
			this.context = context;
			if (context != null) {
				db = new DataBase(context);
				db.open();
			}
		}
		public void closeDataBase() {
			if(db!=null){
				db.close();
			}
		}

}
