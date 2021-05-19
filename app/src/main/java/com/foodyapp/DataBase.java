package com.foodyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {
    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FoodyDB";

    long newId=0;
    long updateId=0;
    //household table
    private static final String TABLE_HOUSEHOLDS_NAME = "households";
    private static final String HOUSEHOLDS_COLUMN_ID = "ID";
    private static final String HOUSEHOLDS_COLUMN_NAME = "name";
    private static final String HOUSEHOLDS_COLUMN_ADDRESS = "address";
//    private static final String HOUSEHOLDS_COLUMN_PACKAGE = "package number";
//    private static final String HOUSEHOLDS_COLUMN_ORGANIZATION = "organizationID";
    private static final String[] TABLE_HOUSEHOLD_COLUMNS = {HOUSEHOLDS_COLUMN_ID, HOUSEHOLDS_COLUMN_NAME, HOUSEHOLDS_COLUMN_ADDRESS};

    //packages table
    private static final String TABLE_PACKAGES_NAME = "packages";
    private static final String PACKAGES_COLUMN_ID = "ID";
    private static final String PACKAGES_COLUMN_HOUSEHOLD_ID = "householdID";
    private static final String PACKAGES_COLUMN_STATUS = "status";
    private static final String[] TABLE_PACKAGES_COLUMNS = {PACKAGES_COLUMN_ID, PACKAGES_COLUMN_HOUSEHOLD_ID, PACKAGES_COLUMN_STATUS};

    //delivery table
    private static final String TABLE_DELIVERY_NAME = "deliveries";
    private static final String DELIVERY_COLUMN_ID = "ID";
    private static final String DELIVERY_COLUMN_PACKAGE = "packageID";
    private static final String DELIVERY_COLUMN_DATE = "date";
    private static final String[] TABLE_DELIVERY_COLUMNS = {DELIVERY_COLUMN_ID, DELIVERY_COLUMN_PACKAGE, DELIVERY_COLUMN_DATE};

    //products table
    private static final String TABLE_PRODUCTS_NAME = "products";
    private static final String PRODUCTS_COLUMN_NAME = "name";
    private static final String PRODUCTS_COLUMN_QUANTITY = "quantity";
    private static final String PRODUCTS_COLUMN_UPDATES = "updateDate";
    private static final String PRODUCTS_COLUMN_SUPPLIER = "supplier";
    private static final String[] TABLE_INVENTORY_COLUMNS = {PRODUCTS_COLUMN_NAME, PRODUCTS_COLUMN_QUANTITY, PRODUCTS_COLUMN_UPDATES, PRODUCTS_COLUMN_SUPPLIER};

    //suppliers table
    private static final String TABLE_SUPPLIERS_NAME = "suppliers";
    private static final String SUPPLIERS_COLUMN_NAME = "name";
    private static final String[] TABLE_SUPPLIERS_COLUMNS = {SUPPLIERS_COLUMN_NAME};

    //order table
    private static final String TABLE_ORDERS_NAME = "orders";
    private static final String ORDERS_COLUMN_ID = "ID";
    private static final String ORDERS_COLUMN_VOLUNTEER = "volunteerID";
    private static final String ORDERS_COLUMN_DATE = "date";
    private static final String[] TABLE_ORDERS_COLUMNS = {ORDERS_COLUMN_ID, ORDERS_COLUMN_VOLUNTEER, ORDERS_COLUMN_DATE};

    //order product table
    private static final String TABLE_ORDER_PRODUCT_NAME = "order_product";
    private static final String ORDER_PRODUCT_COLUMN_ORDER = "orderID";
    private static final String ORDER_PRODUCT_COLUMN_PRODUCT = "productName";
    private static final String ORDERS_COLUMN_QUANTITY = "quantity";
    private static final String[] TABLE_ORDER_PRODUCT_COLUMNS = {ORDER_PRODUCT_COLUMN_ORDER, ORDER_PRODUCT_COLUMN_PRODUCT, ORDERS_COLUMN_QUANTITY};

    //volunteer table
    static final String TABLE_VOLUNTEER_NAME = "volunteers";
    private static final String VOLUNTEER_COLUMN_ID = "id";
    private static final String VOLUNTEERS_COLUMN_NAME = "name";
    private static final String VOLUNTEERS_COLUMN_PHONE = "phone";
    private static final String[] TABLE_VOLUNTEER_COLUMNS = {VOLUNTEER_COLUMN_ID, VOLUNTEERS_COLUMN_NAME, VOLUNTEERS_COLUMN_PHONE};

    private SQLiteDatabase db = null;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            // SQL statement to create houseHold table
            String CREATE_HOUSEHOLDS_TABLE = "create table if not exists " + TABLE_HOUSEHOLDS_NAME +" ( "
                    + HOUSEHOLDS_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + HOUSEHOLDS_COLUMN_NAME +" TEXT, "
                    + HOUSEHOLDS_COLUMN_ADDRESS + " TEXT)";
            db.execSQL(CREATE_HOUSEHOLDS_TABLE);

            // SQL statement to create packages table
            String CREATE_PACKAGES_TABLE = "create table if not exists " + TABLE_PACKAGES_NAME +" ( "
                    + PACKAGES_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + PACKAGES_COLUMN_HOUSEHOLD_ID +" INTEGER, "
                    + PACKAGES_COLUMN_STATUS + " TEXT)";
            db.execSQL(CREATE_PACKAGES_TABLE);


        } catch (Throwable t) {
            t.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSEHOLDS_NAME);
        onCreate(db);
        try {

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void close() {
        try {
            db.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    //adds house holds to the db
    void addHouseHold( String name, String address){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(HOUSEHOLDS_COLUMN_NAME, name);
        cv.put(HOUSEHOLDS_COLUMN_ADDRESS, address);
        long result= db.insert(TABLE_HOUSEHOLDS_NAME, null, cv);
        if (result ==-1){
            Toast.makeText(context, "failed adding household to db", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "success adding household to db", Toast.LENGTH_SHORT).show();
            newId=result;
        }
    }

    //updates households
    void updateHouseHold(String id, String newName, String newAddress){
        SQLiteDatabase db=this.getWritableDatabase();
       ContentValues cv=new ContentValues();
       cv.put(HOUSEHOLDS_COLUMN_NAME, newName);
        cv.put(HOUSEHOLDS_COLUMN_ADDRESS, newAddress);

        long result=db.update(TABLE_HOUSEHOLDS_NAME, cv, HOUSEHOLDS_COLUMN_ID + " =?", new String[] { String.valueOf(id) });
        if (result==-1){
            Toast.makeText(context, "failed update db", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "success update  db", Toast.LENGTH_SHORT).show();
            updateId=result;
        }
    }

    //removes households
    void reomoveHouseHold(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        try {

            // delete items
            long result=  db.delete(TABLE_HOUSEHOLDS_NAME, HOUSEHOLDS_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(id) });
            if (result==-1){
                Toast.makeText(context, "failed delete from db", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "success delete from  db", Toast.LENGTH_SHORT).show();


            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }


    Cursor readAllHouseHolds(){
        String query=" SELECT * FROM " + TABLE_HOUSEHOLDS_NAME;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=null;
        if (db!=null){
            cursor=db.rawQuery(query, null);
        }
        return cursor;
    }



}
