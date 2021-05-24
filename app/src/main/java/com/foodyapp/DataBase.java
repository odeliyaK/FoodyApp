package com.foodyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.foodyapp.model.Products;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import com.foodyapp.model.usersInfo;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private Context context;
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
    private static final String[] TABLE_PRODUCTS_COLUMNS = {PRODUCTS_COLUMN_NAME,
            PRODUCTS_COLUMN_QUANTITY, PRODUCTS_COLUMN_UPDATES, PRODUCTS_COLUMN_SUPPLIER};
    //suppliers table
    private static final String TABLE_SUPPLIERS_NAME = "suppliers";
    private static final String SUPPLIERS_COLUMN_NAME = "name";
    private static final String[] TABLE_SUPPLIERS_COLUMNS = {SUPPLIERS_COLUMN_NAME};

    //order table
    private static final String TABLE_ORDERS_NAME = "orders";
    private static final String ORDERS_COLUMN_ID = "ID";
    private static final String ORDERS_COLUMN_SUPPLIER = "supplier";
    private static final String ORDERS_COLUMN_DATE = "date";
    private static final String[] TABLE_ORDERS_COLUMNS = {ORDERS_COLUMN_ID,
            ORDERS_COLUMN_SUPPLIER, ORDERS_COLUMN_DATE};
    //order product table
    private static final String TABLE_ORDER_PRODUCT_NAME = "order_product";
    private static final String ORDER_PRODUCT_COLUMN_ORDER = "orderID";
    private static final String ORDER_PRODUCT_COLUMN_PRODUCT = "productName";
    private static final String ORDER_PRODUCT_COLUMN_QUANTITY = "quantity";
    private static final String[] TABLE_ORDER_PRODUCT_COLUMNS = {ORDER_PRODUCT_COLUMN_ORDER,
            ORDER_PRODUCT_COLUMN_PRODUCT, ORDER_PRODUCT_COLUMN_QUANTITY};
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


            // SQL statement to create order table
            String CREATE_ORDERS_TABLE = "create table if not exists " + TABLE_ORDERS_NAME +" ( "
                    + PACKAGES_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ORDERS_COLUMN_SUPPLIER +" TEXT, "
                    + ORDERS_COLUMN_DATE + " TEXT)";
            db.execSQL(CREATE_ORDERS_TABLE);

            // SQL statement to create suppliers table
            String CREATE_SUPPLIERS_TABLE = "create table if not exists " + TABLE_SUPPLIERS_NAME +" ( "
                    + SUPPLIERS_COLUMN_NAME +" TEXT PRIMARY KEY)";
            db.execSQL(CREATE_SUPPLIERS_TABLE);

            // SQL statement to create products table
            String CREATE_PRODUCTS_TABLE = "create table if not exists " + TABLE_PRODUCTS_NAME +" ( "
                    + PRODUCTS_COLUMN_NAME +" TEXT PRIMARY KEY, "
                    + PRODUCTS_COLUMN_SUPPLIER +" TEXT, "
                    + PRODUCTS_COLUMN_UPDATES +" TEXT, "
                    + PRODUCTS_COLUMN_QUANTITY + " INTEGER)";
            db.execSQL(CREATE_PRODUCTS_TABLE);

            // SQL statement to create order_products table
            String CREATE_ORDER_PRODUCTS_TABLE = "create table if not exists " + TABLE_ORDER_PRODUCT_NAME +" ( "
                    + ORDER_PRODUCT_COLUMN_ORDER +" INTEGER , "
                    + ORDER_PRODUCT_COLUMN_PRODUCT +" TEXT , "
                    + ORDER_PRODUCT_COLUMN_QUANTITY +" INTEGER, PRIMARY KEY ( " + ORDER_PRODUCT_COLUMN_ORDER + "," + ORDER_PRODUCT_COLUMN_PRODUCT+ "))";
            db.execSQL(CREATE_ORDER_PRODUCTS_TABLE);


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

    public boolean checkIfOrderHappen(String supplier){
        boolean flag = false;
        Cursor cursor = null;
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        try {
            cursor = db.query(TABLE_ORDERS_NAME, TABLE_ORDERS_COLUMNS, ORDERS_COLUMN_SUPPLIER + " = ? and " + ORDERS_COLUMN_DATE + " = ?", new String[] {supplier, currentDate},
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                flag = true;
                cursor.moveToNext();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return flag;
    }
    //check if an order happened today from Tenuva.
    public boolean checkOrderTenuva(){
        boolean flag = false;
        Cursor cursor = null;
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        try {
            cursor = db.query(TABLE_ORDERS_NAME, TABLE_ORDERS_COLUMNS, ORDERS_COLUMN_SUPPLIER + " = ? and " + ORDERS_COLUMN_DATE + " = ?", new String[] {"Tenuva", currentDate},
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                flag = true;
                cursor.moveToNext();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return flag;
    }

    //check if an order happened today from Butcher.
    public boolean checkOrderButcher(){
        boolean flag = false;
        Cursor cursor = null;
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        try {
            cursor = db.query(TABLE_ORDERS_NAME, TABLE_ORDERS_COLUMNS, ORDERS_COLUMN_SUPPLIER + " = ? and " + ORDERS_COLUMN_DATE + " = ?", new String[] {"Butcher", currentDate},
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                flag = true;
                cursor.moveToNext();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return flag;
    }


    //check if an order happened today from Butcher.
    public boolean checkOrderOsem(){
        boolean flag = false;
        Cursor cursor = null;
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        try {
            cursor = db.query(TABLE_ORDERS_NAME, TABLE_ORDERS_COLUMNS, ORDERS_COLUMN_SUPPLIER + " = ? and " + ORDERS_COLUMN_DATE + " = ?", new String[] {"Osem", currentDate},
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                flag = true;
                cursor.moveToNext();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return flag;
    }

    //check if an order happened today from Butcher.
    public boolean checkOrderMeshek(){
        boolean flag = false;
        Cursor cursor = null;
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        try {
            cursor = db.query(TABLE_ORDERS_NAME, TABLE_ORDERS_COLUMNS, ORDERS_COLUMN_SUPPLIER + " = ? and " + ORDERS_COLUMN_DATE + " = ?", new String[] {"Meshek", currentDate},
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                flag = true;
                cursor.moveToNext();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return flag;
    }

    //make an order from Tenuva/Meshek/Osem/Butcher.
    public void makeOrder(HashMap<String,Integer> products){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        Cursor cursor = null;
        long orderID;
        if(products != null)
        {
            orderID = createOrderSQL("Tenuva");
            //it means we succeeded create an order.
            if(orderID >= 0){
                for(String pro : products.keySet()){
                    try{
                        ContentValues values = new ContentValues();
                        values.put(PRODUCTS_COLUMN_QUANTITY, products.get(pro));
                        values.put(PRODUCTS_COLUMN_UPDATES, currentDate);
                        db.update(TABLE_PRODUCTS_NAME, values, PRODUCTS_COLUMN_NAME + " = ?",
                                new String[] { pro });
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                    try{
                        ContentValues values = new ContentValues();
                        values.put(ORDER_PRODUCT_COLUMN_ORDER, orderID);
                        values.put(ORDER_PRODUCT_COLUMN_PRODUCT, pro);
                        values.put(ORDER_PRODUCT_COLUMN_QUANTITY, products.get(pro));
                        db.insert(TABLE_ORDER_PRODUCT_NAME,null, values);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }

                }
            }
        }
    }

    public long createOrderSQL(String supplier){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        try{
            ContentValues values = new ContentValues();
            values.put(ORDERS_COLUMN_SUPPLIER,"Tenuva");
            values.put(ORDERS_COLUMN_DATE,currentDate);

            return db.insert(TABLE_ORDERS_NAME, null, values);
        }
        catch (Throwable t){
            t.printStackTrace();

        }
        return -1;
    }

    public Products cursorToProduct(Cursor cursor) {
        Products result = new Products();
        try {
            result.setName(cursor.getString(0));
            result.setSupplier(cursor.getString(3));
            result.setUpdate(cursor.getString(2));
            result.setQuantity(cursor.getInt(1));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    //get all products from DB.
    public ArrayList<Products> allProducts(){
        ArrayList<Products> products = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_PRODUCTS_NAME, TABLE_PRODUCTS_COLUMNS, null, null,
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Products product = cursorToProduct(cursor);
                products.add(product);
                cursor.moveToNext();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return products;
    }
    public void Products(){
        ArrayList<Products> currentProducts = allProducts();
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
//        try {
//            ContentValues values = new ContentValues();
//            values.put(ORDERS_COLUMN_ID, 1);
//            values.put(ORDERS_COLUMN_SUPPLIER, "Tenuva");
//            values.put(ORDERS_COLUMN_DATE, currentDate);
//
//            db.insert(TABLE_ORDERS_NAME, null, values);
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
        //check if there are already products in DB. If not - it means DB is empty and those insert will happen.
        if(currentProducts.isEmpty()){
            //dairy
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Milk");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Tenuva");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Cheese");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Tenuva");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Eggs");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Tenuva");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Butter");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Tenuva");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Cottage");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Tenuva");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            //meat & poultry
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Meat");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Butcher");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Chicken");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Butcher");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Fish");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Butcher");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            //fruits & vegetables
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Cucumber");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Meshek");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Tomato");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Meshek");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Potato");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Meshek");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Apple");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Meshek");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Banana");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Meshek");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Onion");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Meshek");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Carrot");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Meshek");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            //grocery
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Bread");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Osem");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Pasta");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Osem");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Oil");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Osem");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Rice");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Osem");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_NAME, "Coffee");
                values.put(PRODUCTS_COLUMN_QUANTITY, 20);
                values.put(PRODUCTS_COLUMN_SUPPLIER, "Osem");
                values.putNull(PRODUCTS_COLUMN_UPDATES);
                db.insert(TABLE_PRODUCTS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            //insert suppliers:
            try {
                ContentValues values = new ContentValues();
                values.put(SUPPLIERS_COLUMN_NAME, "Tenuva");
                db.insert(TABLE_SUPPLIERS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(SUPPLIERS_COLUMN_NAME, "Meshek");
                db.insert(TABLE_SUPPLIERS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(SUPPLIERS_COLUMN_NAME, "Butcher");
                db.insert(TABLE_SUPPLIERS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(SUPPLIERS_COLUMN_NAME, "Osem");
                db.insert(TABLE_SUPPLIERS_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }



    //adds house holds to the db
    void addHouseHold(usersInfo user){
//        SQLiteDatabase db=this.getWritableDatabase();
        try {
            ContentValues cv=new ContentValues();
            cv.put(HOUSEHOLDS_COLUMN_NAME, user.getName());
            cv.put(HOUSEHOLDS_COLUMN_ADDRESS, user.getAddress());
            db.insert(TABLE_HOUSEHOLDS_NAME, null, cv);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    //updates households
    void updateHouseHold(String id, String newName, String newAddress){
//        SQLiteDatabase db=this.getWritableDatabase();
       ContentValues cv=new ContentValues();
       cv.put(HOUSEHOLDS_COLUMN_NAME, newName);
        cv.put(HOUSEHOLDS_COLUMN_ADDRESS, newAddress);

        long result=db.update(TABLE_HOUSEHOLDS_NAME, cv, HOUSEHOLDS_COLUMN_ID + " =?", new String[] { String.valueOf(id) });
        if (result==-1){
            Toast.makeText(context, "failed update db", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "success update  db", Toast.LENGTH_SHORT).show();
      //      updateId=result;
        }
    }

    //removes households
    void reomoveHouseHold(usersInfo household){
//        SQLiteDatabase db=this.getWritableDatabase();
        try {

            // delete items
             db.delete(TABLE_HOUSEHOLDS_NAME, HOUSEHOLDS_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(household.getId()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }


    Cursor readAllHouseHolds(){
        String query=" SELECT * FROM " + TABLE_HOUSEHOLDS_NAME;
       // SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=null;
        cursor=db.query(TABLE_HOUSEHOLDS_NAME,null,null,null,null,null,null);
        if (db!=null){
            cursor=db.rawQuery(query, null);
        }
        return cursor;
    }

    public List<usersInfo>getAllHouseHolds(){
        List<usersInfo> result = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_HOUSEHOLDS_NAME,TABLE_HOUSEHOLD_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                usersInfo item = cursorToHouseHold(cursor);
                result.add(item);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;

    }

    private usersInfo cursorToHouseHold(Cursor cursor) {
        usersInfo result = new usersInfo();
        try {
            //result.setId(Integer.parseInt(cursor.getString(0)));
            result.setId(cursor.getString(0));
            result.setName(cursor.getString(1));
            result.setAddress(cursor.getString(2));

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

}
