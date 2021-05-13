package com.foodyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FoodyDB";

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
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // SQL statement to create item table
            String CREATE_HOUSEHOLDS_TABLE = "create table if not exists " + TABLE_HOUSEHOLDS_NAME +" ( "
                    + HOUSEHOLDS_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + HOUSEHOLDS_COLUMN_NAME +" TEXT, "
                    + HOUSEHOLDS_COLUMN_ADDRESS + " TEXT)";
            db.execSQL(CREATE_HOUSEHOLDS_TABLE);

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
}
