package com.foodyapp;

import android.content.Context;

import com.foodyapp.model.Products;
import com.foodyapp.model.Volunteers;
import com.foodyapp.model.usersInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyInfoManager {

	private static MyInfoManager instance = null;
	private Context context = null;
	private DataBase db = null;
	int newId=0;
	int updateId=0;

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

	public void createHouseHold(usersInfo user) {
		if (db != null) {
			db.addHouseHold(user);
		}
	}

	public List<usersInfo> getAllHouseHolds() {
		List<usersInfo> result = new ArrayList<usersInfo>();
		if (db != null) {
			result = db.getAllHouseHolds();
		}
		return result;
	}


	public void deleteHousehold(usersInfo household) {
		if (db != null) {
			db.reomoveHouseHold(household);
		}
	}


		public void products(){
			if(db != null){
				db.Products();
			}
		}

	public ArrayList<Products> allProducts(){
		if(db != null)
			return db.allProducts();
		return null;
	}

	public boolean checkIfOrderHappen(String supplier){
		if(db != null){
			return db.checkIfOrderHappen(supplier);
		}
		return false;
	}


	public void makeOrder(HashMap<String,Integer> products, HashMap<String,Integer> current, String supplier){
			if(db != null && !products.isEmpty() && !current.isEmpty()){
				db.makeOrder(products, current, supplier);
		}
	}

	public void saveInventory(HashMap<String,Integer> current, String supplier){
		if(db != null && !current.isEmpty()){
			db.saveInventory(current, supplier);
		}
	}

	public boolean isInventoryUpdated(String supplier){

		if(db != null)
			return db.isInventoryUpdated(supplier);
		return false;

	}

	public long newVolunteer(String name, String phone) {
		if (db != null) {
			return db.newVolunteer(name, phone);
		}
		return -1;
	}

}
