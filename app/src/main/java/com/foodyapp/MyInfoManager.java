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
	private usersInfo selectedHousehold = null;

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

	public void createHistoryPackage(HistoryInfo user) {
		if (db != null) {
			db.addPackageToHistory(user);
		}
	}

	public void createPackage(usersInfo user, String id) {
		if (db != null) {
			db.addPackageWithId(user,id);
		}
	}

	public void deleteAllOrders(){
		if(db != null)
			db.deleteAllOrders();
	}

	public List<usersInfo> getAllHouseHolds() {
		List<usersInfo> result = new ArrayList<usersInfo>();
		if (db != null) {
			result = db.getAllHouseHolds();
		}
		return result;
	}

	public void deleteAllHouseholds(){
		if(db != null)
			db.deleteAllHouseholds();
	}

	public void removeVolunteer(Volunteers vol){
		if(db != null)
			db.removeVolunteer(vol);
	}

	public void deleteAllPackages() {
		if(db != null)
			db.deleteAllPackages();
	}

	public void deleteHousehold(usersInfo household) {
		if (db != null) {
			db.reomoveHouseHold(household);
		}
	}

	public void deletePackage(usersInfo household) {
		if (db != null) {
			db.removePackage(household);
		}
	}

	public List<usersInfo> getAllPackages() {
		List<usersInfo> result = new ArrayList<usersInfo>();
		if (db != null) {
			result = db.getAllPackages();
		}
		return result;
	}

	public List<HistoryInfo> getAllHistoryPackages() {
		List<HistoryInfo> result = new ArrayList<HistoryInfo>();
		if (db != null) {
			result = db.getAllHistoryPackages();
		}
		return result;
	}

	public List<usersInfo> getAllActivePackages() {
		List<usersInfo> result = new ArrayList<usersInfo>();
		if (db != null) {
			result = db.getAllActivePackages();
		}
		return result;
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

	public void createOrderSQL(String supplier){
		if(db != null)
			db.createOrderSQL(supplier);
	}

	public void saveInventory(HashMap<String,Integer> current, String supplier){
		if(db != null && !current.isEmpty()){
			db.saveInventory(current, supplier);
		}
	}

	public void updateInventory(HashMap<String,Integer> current, String supplier){
		if(db != null && !current.isEmpty()){
			db.updateInventory(current, supplier);
		}
	}

	public boolean isInventoryUpdated(String supplier){

		if(db != null)
			return db.isInventoryUpdated(supplier);
		return false;

	}

	public void updateProducts(Products pruduct){
		if(db != null){
			db.updateProducts(pruduct);
		}
	}

	public ArrayList<Volunteers> allVolunteers(){
		if(db != null){
			return db.allVolunteers();
		}
		return null;
	}

	public boolean isVolunteerExist(String email){
		if(db != null)
			return db.isVolunteerExist(email);
		return false;
	}

	public long newVolunteer(String email, String name, String phone) {
		if (db != null) {
			return db.newVolunteer(email, name, phone);
		}
		return -1;
	}

	public void updateHousehold(usersInfo household) {
		if (db != null && household != null) {
			db.updateHousehold(household);
		}
	}

	public void deleteAllVols(){
		if(db != null)
			db.deleteAllVols();
	}

	public int updateVolunteer(Volunteers vol) {
		if (db != null && vol != null) {
			db.updateVolunteer(vol);
		}
		return 1;
	}

	public void updatePackage(usersInfo household) {
		if (db != null && household != null) {
			db.updatePackages(household);
		}
	}

	public usersInfo getSelectedHouseHold() {
		return selectedHousehold;
	}

	public void checksInserts(){
		if(db != null)
			db.checksInserts();
	}




}
