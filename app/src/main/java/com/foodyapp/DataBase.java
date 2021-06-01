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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.foodyapp.model.Volunteers;
import com.foodyapp.model.usersInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private static final String PACKAGES_COLUMN_HOUSEHOLD_NAME = "householdName";
    private static final String PACKAGES_COLUMN_HOUSEHOLD_ADDRESS = "householdAddress";
    private static final String PACKAGES_COLUMN_STATUS = "status";
    private static final String[] TABLE_PACKAGES_COLUMNS = {PACKAGES_COLUMN_ID, PACKAGES_COLUMN_HOUSEHOLD_ID,PACKAGES_COLUMN_HOUSEHOLD_NAME, PACKAGES_COLUMN_HOUSEHOLD_ADDRESS, PACKAGES_COLUMN_STATUS};

    //delivery table
    private static final String TABLE_DELIVERY_NAME = "deliveries";
    private static final String DELIVERY_COLUMN_ID = "ID";
    private static final String DELIVERY_COLUMN_PACKAGE = "packageID";
    private static final String DELIVERY_COLUMN_DATE = "date";
    private static final String[] TABLE_DELIVERY_COLUMNS = {DELIVERY_COLUMN_ID, DELIVERY_COLUMN_PACKAGE, DELIVERY_COLUMN_DATE};

    //history table
    private static final String TABLE_HISTORY_NAME = "packagesHistory";
    private static final String HISTORY_COLUMN_PACKAGE_ID = "packageID";
    private static final String HISTORY_COLUMN_PACKAGE_HOUSEHOLD_NAME = "householdName";
    private static final String HISTORY_COLUMN_PACKAGE_HOUSEHOLD_ADDRESS = "householdAddress";
    private static final String HISTORY_COLUMN_DATE = "date";
    private static final String[] TABLE_HISTORY_COLUMNS = { HISTORY_COLUMN_PACKAGE_ID,HISTORY_COLUMN_PACKAGE_HOUSEHOLD_NAME,HISTORY_COLUMN_PACKAGE_HOUSEHOLD_ADDRESS, HISTORY_COLUMN_DATE};
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
    private static final String VOLUNTEER_COLUMN_EMAIL = "email";
    private static final String VOLUNTEERS_COLUMN_NAME = "name";
    private static final String VOLUNTEERS_COLUMN_PHONE = "phone";
    private static final String[] TABLE_VOLUNTEER_COLUMNS = {VOLUNTEER_COLUMN_EMAIL, VOLUNTEERS_COLUMN_NAME, VOLUNTEERS_COLUMN_PHONE};

    private SQLiteDatabase db = null;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

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

            // SQL statement to create volunteer table
            String CREATE_VOLUNTEER_TABLE = "create table if not exists " + TABLE_VOLUNTEER_NAME +" ( "
                    + VOLUNTEER_COLUMN_EMAIL +" TEXT PRIMARY KEY, "
                    + VOLUNTEERS_COLUMN_NAME +" TEXT , "
                    + VOLUNTEERS_COLUMN_PHONE +" INTEGER)";
            db.execSQL(CREATE_VOLUNTEER_TABLE);

            // SQL statement to create order_products table
            String CREATE_ORDER_PRODUCTS_TABLE = "create table if not exists " + TABLE_ORDER_PRODUCT_NAME +" ( "
                    + ORDER_PRODUCT_COLUMN_ORDER +" INTEGER, "
                    + ORDER_PRODUCT_COLUMN_PRODUCT +" TEXT , "
                    + ORDER_PRODUCT_COLUMN_QUANTITY +" INTEGER, PRIMARY KEY ( " + ORDER_PRODUCT_COLUMN_ORDER + "," + ORDER_PRODUCT_COLUMN_PRODUCT+ "))";
            db.execSQL(CREATE_ORDER_PRODUCTS_TABLE);

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
                    + PACKAGES_COLUMN_HOUSEHOLD_NAME +" TEXT, "
                    + PACKAGES_COLUMN_HOUSEHOLD_ADDRESS +" TEXT, "
                    + PACKAGES_COLUMN_STATUS + " TEXT)";
            db.execSQL(CREATE_PACKAGES_TABLE);



            // SQL statement to create packages history table
            String CREATE_HISTORY_TABLE = "create table if not exists " + TABLE_HISTORY_NAME +" ( "
                    + HISTORY_COLUMN_PACKAGE_ID +" INTEGER, "
                    + HISTORY_COLUMN_PACKAGE_HOUSEHOLD_NAME +" TEXT, "
                    + PACKAGES_COLUMN_HOUSEHOLD_ADDRESS +" TEXT, "
                    + HISTORY_COLUMN_DATE + " TEXT)";
            db.execSQL(CREATE_HISTORY_TABLE);


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



    public long newVolunteer(String email, String name, String phone){

        try{
            ContentValues values = new ContentValues();
            values.put(VOLUNTEER_COLUMN_EMAIL, email);
            values.put(VOLUNTEERS_COLUMN_NAME, name);
            values.put(VOLUNTEERS_COLUMN_PHONE, phone);

            return db.insert(TABLE_VOLUNTEER_NAME,null, values);

        } catch (Throwable t) {
            t.printStackTrace();
        }
        return -1;
    }
    //make an order from Tenuva/Meshek/Osem/Butcher.
    public void makeOrder(HashMap<String,Integer> products, HashMap<String,Integer> current, String supplier){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        Cursor cursor = null;
        long orderID;
        if(products != null)
        {
            orderID = createOrderSQL(supplier);
            //it means we succeeded to create an order.
            if(orderID >= 0){
                for(String pro : products.keySet()){
                    try{
                        ContentValues values = new ContentValues();
                        values.put(PRODUCTS_COLUMN_QUANTITY, current.get(pro));
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

    //creating an order and return it's ID.
    public long createOrderSQL(String supplier){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        try{
            ContentValues values = new ContentValues();
            values.put(ORDERS_COLUMN_SUPPLIER,supplier);
            values.put(ORDERS_COLUMN_DATE,currentDate);

            return db.insert(TABLE_ORDERS_NAME, null, values);
        }
        catch (Throwable t){
            t.printStackTrace();

        }
        return -1;
    }

    //create a new volunteer
    public Volunteers cursorToVol(Cursor cursor) {
        Volunteers result = new Volunteers();
        try {
            result.setEmail(cursor.getString(0));
            result.setName(cursor.getString(1));
            result.setPhone(cursor.getString(2));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    //create a new product by cursor (by getting a product from DB)
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

    //check volunteer by email
    public Boolean isVolunteerExist(String email){
        ArrayList<Volunteers> volunteers = new ArrayList<>();
        Cursor cursor = null;
        boolean flag = false;
        try {
            cursor = db.query(TABLE_VOLUNTEER_NAME, TABLE_VOLUNTEER_COLUMNS, VOLUNTEER_COLUMN_EMAIL + " = ?", new String[] {email},
                    null, null, null);
            if(cursor.getCount()>0)
                flag = true;
            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                Volunteers vol = cursorToProduct(cursor);
//                volunteers.add(vol);
//                cursor.moveToNext();
//            }
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

    //get all volunteers from DB.
    public ArrayList<Volunteers> allVolunteers(){
        ArrayList<Volunteers> volunteers = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_VOLUNTEER_NAME, TABLE_VOLUNTEER_COLUMNS, null, null,
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Volunteers vol = cursorToVol(cursor);
                volunteers.add(vol);
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
        return volunteers;
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

    //saving the updates of the inventory.
    public void saveInventory(HashMap<String,Integer> current, String supplier){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        for(String pro : current.keySet()) {
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_QUANTITY, current.get(pro));
                values.put(PRODUCTS_COLUMN_UPDATES, currentDate);
                db.update(TABLE_PRODUCTS_NAME, values, PRODUCTS_COLUMN_NAME + " = ? and " + PRODUCTS_COLUMN_SUPPLIER + " = ?",
                        new String[]{pro, supplier});
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

    }

    //saving the updates of the inventory after package was sent.
    public void updateInventory(HashMap<String,Integer> current, String supplier){

        for(String pro : current.keySet()) {
            try {
                ContentValues values = new ContentValues();
                values.put(PRODUCTS_COLUMN_QUANTITY, current.get(pro));
                //values.put(PRODUCTS_COLUMN_UPDATES, );
                db.update(TABLE_PRODUCTS_NAME, values, PRODUCTS_COLUMN_NAME + " = ? and " + PRODUCTS_COLUMN_SUPPLIER + " = ?",
                        new String[]{pro, supplier});
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

    }

    //check if someone updated the inventory today (by product kind - dairy/grocery..)
    public boolean isInventoryUpdated(String supplier){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        boolean flag = false;
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_PRODUCTS_NAME, TABLE_PRODUCTS_COLUMNS, PRODUCTS_COLUMN_SUPPLIER + " = ? and " + PRODUCTS_COLUMN_UPDATES + " = ?",
                    new String[]{supplier,currentDate}, null, null, PRODUCTS_COLUMN_NAME + " DESC", "1");
            if(cursor.getCount()>0)
                flag = true;
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

    //insert all products to DB for the first time we run this app.
    public void Products(){
        ArrayList<Products> currentProducts = allProducts();

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

        try {
            ContentValues cv=new ContentValues();
            cv.put(HOUSEHOLDS_COLUMN_NAME, user.getName());
            cv.put(HOUSEHOLDS_COLUMN_ADDRESS, user.getAddress());
            db.insert(TABLE_HOUSEHOLDS_NAME, null, cv);
            //after adding new household, creating new package

            addPackage(user);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    //adds packages to the db
    void addPackage(usersInfo user){
        String id= MyInfoManager.getInstance().getAllHouseHolds().get(MyInfoManager.getInstance().getAllHouseHolds().size()-1).getId();
        try {
            ContentValues cv=new ContentValues();
            cv.put(PACKAGES_COLUMN_HOUSEHOLD_ID, id);
            cv.put(PACKAGES_COLUMN_HOUSEHOLD_NAME, user.getName());
            cv.put(PACKAGES_COLUMN_HOUSEHOLD_ADDRESS, user.getAddress());
            db.insert(TABLE_PACKAGES_NAME, null, cv);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    void addPackageWithId(usersInfo user, String id){
        // String id= MyInfoManager.getInstance().getAllHouseHolds().get(MyInfoManager.getInstance().getAllHouseHolds().size()-1).getId();
        try {
            ContentValues cv=new ContentValues();
            cv.put(PACKAGES_COLUMN_HOUSEHOLD_ID, id);
            cv.put(PACKAGES_COLUMN_HOUSEHOLD_NAME, user.getName());
            cv.put(PACKAGES_COLUMN_HOUSEHOLD_ADDRESS, user.getAddress());
            db.insert(TABLE_PACKAGES_NAME, null, cv);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    //adds packages to history table in the db
    void addPackageToHistory(HistoryInfo user){

        try {
            ContentValues cv=new ContentValues();
            cv.put(HISTORY_COLUMN_PACKAGE_ID, user.getNum());
            cv.put(HISTORY_COLUMN_PACKAGE_HOUSEHOLD_NAME, user.getName());
            cv.put(HISTORY_COLUMN_PACKAGE_HOUSEHOLD_ADDRESS, user.getAddress());
            cv.put(HISTORY_COLUMN_DATE,user.getDate());
            db.insert(TABLE_HISTORY_NAME, null, cv);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    //updates households
    public int updateVolunteer(Volunteers vol) {
        int i = 0;
        try {

            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(VOLUNTEERS_COLUMN_NAME, vol.getName());
            values.put(VOLUNTEERS_COLUMN_PHONE, vol.getPhone());
            // update
            i = db.update(TABLE_VOLUNTEER_NAME, values, VOLUNTEER_COLUMN_EMAIL + " = ?",
                    new String[] { String.valueOf(vol.getEmail()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return i;
    }
    //updates households
    public int updateHousehold(usersInfo houseHold) {
        int i = 0;
        try {

            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(HOUSEHOLDS_COLUMN_NAME, houseHold.getName());
            values.put(HOUSEHOLDS_COLUMN_ADDRESS, houseHold.getAddress());
            // update
            i = db.update(TABLE_HOUSEHOLDS_NAME, values, HOUSEHOLDS_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(houseHold.getId()) });
                updatePackages(houseHold);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return i;
    }

    //updates households
    public int updatePackages(usersInfo houseHold) {
        int i = 0;
        try {

            // make values to be inserted
            ContentValues values = new ContentValues();

            values.put(PACKAGES_COLUMN_HOUSEHOLD_NAME,houseHold.getName());
            values.put(PACKAGES_COLUMN_HOUSEHOLD_ADDRESS,houseHold.getAddress());

            // update
            i = db.update(TABLE_PACKAGES_NAME, values, PACKAGES_COLUMN_HOUSEHOLD_ID + " = ?",
                    new String[] { String.valueOf(houseHold.getId()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return i;
    }

    //removes households- if household was removed, the package also removes.
    void reomoveHouseHold(usersInfo household){
        boolean succeded = false;
        try {

            // delete folder
            int rowAffected = db.delete(TABLE_HOUSEHOLDS_NAME, HOUSEHOLDS_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(household.getId()) });
            if(rowAffected>0) {
                succeded = true;
            }

        } catch (Throwable t) {
            succeded = false;
            t.printStackTrace();
        } finally {
            if(succeded){
                reomovePackage(household);
            }
        }


    }

    void removeVolunteer(Volunteers vol){

        try {

            // delete items
            db.delete(TABLE_VOLUNTEER_NAME, VOLUNTEER_COLUMN_EMAIL + " = ?",
                    new String[] { String.valueOf(vol.getEmail()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }
    //removes package
    void reomovePackage(usersInfo household){

        try {

            // delete items
            db.delete(TABLE_PACKAGES_NAME, PACKAGES_COLUMN_HOUSEHOLD_ID + " = ?",
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

    private usersInfo cursorToHouseHoldPackage(Cursor cursor) {
        usersInfo result = new usersInfo();
        try {
            //result.setId(Integer.parseInt(cursor.getString(0)));
            result.setNum(cursor.getInt(0));
            result.setId(cursor.getString(1));
            result.setName(cursor.getString(2));
            result.setAddress(cursor.getString(3));

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    private HistoryInfo cursorToHistorydPackage(Cursor cursor) {
        HistoryInfo result = new HistoryInfo();
        try {
            //result.setId(Integer.parseInt(cursor.getString(0)));
            result.setNum(cursor.getInt(0));
            result.setName(cursor.getString(1));
            result.setAddress(cursor.getString(2));
            result.setDate(cursor.getString(3));




        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    public List<usersInfo>getAllPackages(){
        List<usersInfo> result = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_PACKAGES_NAME,TABLE_PACKAGES_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                usersInfo item = cursorToHouseHoldPackage(cursor);
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

    public List<HistoryInfo>getAllHistoryPackages(){
        List<HistoryInfo> result = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_HISTORY_NAME,TABLE_HISTORY_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                HistoryInfo item = cursorToHistorydPackage(cursor);
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

    public List<usersInfo>getAllActivePackages(){
        List<usersInfo> result = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_PACKAGES_NAME,TABLE_PACKAGES_COLUMNS, PACKAGES_COLUMN_STATUS+ " = ?", new String[] {"ACTIVE"},null,
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

    //inserts for checks:
    public void checksInserts(){

        ArrayList<Products> currentProducts = allProducts();

        if(currentProducts.isEmpty()) {
            //inserting volunteer
            try {
                ContentValues values = new ContentValues();
                values.put(VOLUNTEERS_COLUMN_NAME, "Moshe Sason");
                values.put(VOLUNTEERS_COLUMN_PHONE, "1234567");
                values.put(VOLUNTEER_COLUMN_EMAIL, "moshe@gmail.com");
                db.insert(TABLE_VOLUNTEER_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                ContentValues values = new ContentValues();
                values.put(VOLUNTEERS_COLUMN_NAME, "Eden Levi");
                values.put(VOLUNTEERS_COLUMN_PHONE, "987654");
                values.put(VOLUNTEER_COLUMN_EMAIL, "eden@gmail.com");
                db.insert(TABLE_VOLUNTEER_NAME, null, values);
            } catch (Throwable t) {
                t.printStackTrace();
            }

            //inserting households
            try {
                long id;
                ContentValues values = new ContentValues();
                values.put(HOUSEHOLDS_COLUMN_NAME, "Liav Dagan");
                values.put(HOUSEHOLDS_COLUMN_ADDRESS, "Haifa, Hanesharim 4");
                id = db.insert(TABLE_HOUSEHOLDS_NAME, null, values);
                usersInfo household = new usersInfo("Liav Dagan", "Haifa, Hanesharim 4");
                addPackage(household);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                long id;
                ContentValues values = new ContentValues();
                values.put(HOUSEHOLDS_COLUMN_NAME, "Shiran Markovitz");
                values.put(HOUSEHOLDS_COLUMN_ADDRESS, "Haifa, Haagana 21");
                id = db.insert(TABLE_HOUSEHOLDS_NAME, null, values);
                usersInfo household = new usersInfo("Shiran Markovitz", "Haifa, Haagana 21");
                addPackage(household);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

    }


}
