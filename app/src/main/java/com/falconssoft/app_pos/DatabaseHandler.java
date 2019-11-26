package com.falconssoft.app_pos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.falconssoft.app_pos.models.CustomerInformation;
import com.falconssoft.app_pos.models.Items;
import com.falconssoft.app_pos.models.Tables;
import com.falconssoft.app_pos.models.Users;
import com.falconssoft.app_pos.models.Items;
import com.falconssoft.app_pos.models.Tables;
import com.falconssoft.app_pos.models.Users;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =2;
    private static final String DATABASE_NAME = "MenuDB";
    static SQLiteDatabase db;
    String TAG="Dbhandler";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String USERS_TABLE = "USERS";

    private static final String USER_NAME = "USER_NAME";
    private static final String PASSWORD = "PASSWORD";
// *******************************************************************************

    private static final String ITEMS_TABLE = "ITEMS";

    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final String ITEM_NAME = "ITEM_NAME";
    private static final String PRICE = "PRICE";
    private static final String ITEM_BARCODE = "ITEM_BARCODE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String ITEM_PICTURE = "ITEM_PICTURE";
    private static final String CATEGORY_PICTURE = "CATEGORY_PICTURE";
    private static final String POINT ="POINT";
    // *******************************************************************************

    private static final String TABLES_TABLE = "TABLES_TABLE";

    private static final String SECTION_NO = "SECTION_NO";
    private static final String TABEL_NO = "TABEL_NO";
    private static final String NO_OF_SEITS = "NO_OF_SEITS";
    // *******************************************************************************
    private static final String CUSTOMER_INFORMATION = "CUSTOMER_INFORMATION";

    private static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    private static final String PHONE_NO = "PHONE_NO";
    private static final String EMAIL = "EMAIL";
    private static final String POINT_CUSTOMER = "POINT_CUSTOMER";
    // *******************************************************************************

    // *******************************************************************************
    private static final String ORDER_PAY = "ORDER_PAY";

    private static final String CUSTOMER_NAME1 = "CUSTOMER_NAME";
    private static final String PHONE_NO1 = "PHONE_NO";
    private static final String POINT_CUSTOMER1 = "POINT_CUSTOMER";
    private static final String TOTAL1 = "TOTAL";
    private static final String DATE_FOR_PAY = "DATE_FOR_PAY";
    private static final String ITEM_COUNT = "ITEM_COUNT";
    private static final String VOUCH_NO = "VOUCH_NO";

    // *******************************************************************************


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_ITEMS = "CREATE TABLE " + ITEMS_TABLE + "("
                + CATEGORY_NAME + " TEXT,"
                + ITEM_NAME + " TEXT,"
                + ITEM_BARCODE + " TEXT,"
                + PRICE + " INTEGER,"
                + DESCRIPTION + " TEXT,"
                + ITEM_PICTURE + " BLOB,"
                + CATEGORY_PICTURE + " BLOB,"
                + POINT + " REAL" + ")";
        db.execSQL(CREATE_TABLE_ITEMS);
        // *******************************************************************************

        String CREATE_TABLE_USERS = "CREATE TABLE " + USERS_TABLE + "("
                + USER_NAME + " TEXT,"
                + PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_USERS);
        // *******************************************************************************
        String CREATE_TABLES_TABLE = "CREATE TABLE " + TABLES_TABLE + "("
                + SECTION_NO + " TEXT,"
                + TABEL_NO + " TEXT,"
                + NO_OF_SEITS + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLES_TABLE);
        // *******************************************************************************

        String CREATE_CUSTOMER_INFORMATION_TABLE = "CREATE TABLE " + CUSTOMER_INFORMATION + "("
                + CUSTOMER_NAME + " TEXT,"
                + PHONE_NO + " TEXT,"
                + EMAIL + " TEXT,"
                +  POINT_CUSTOMER+ " REAL"

                + ")";
        db.execSQL(CREATE_CUSTOMER_INFORMATION_TABLE);
        // *******************************************************************************


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("ALTER TABLE ITEMS_TABLE ADD POINT  REAL NOT NULL DEFAULT '0'");
        }catch (Exception e)
        {
            Log.e(TAG, e.getMessage().toString());
        }
        try{
            db.execSQL("ALTER TABLE CUSTOMER_INFORMATION ADD POINT_CUSTOMER  REAL NOT NULL DEFAULT '0'");
        }catch (Exception e)
        {
            Log.e(TAG, e.getMessage().toString());
        }




    }

    //////////////////////////////////////////////////////// ADD ///////////////////////////////////////////////

    public void addItem(Items items) {
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        byte[] byteImage = {};
        byte[] byteCatImage = {};
        if (items.getItemPic() != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            items.getItemPic().compress(Bitmap.CompressFormat.PNG, 0, stream);
            byteImage = stream.toByteArray();
        }
        if (items.getCategoryPic() != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            items.getCategoryPic().compress(Bitmap.CompressFormat.PNG, 0, stream);
            byteCatImage = stream.toByteArray();
        }
        values.put(CATEGORY_NAME, items.getCategoryName());
        values.put(ITEM_NAME, items.getItemName());
        values.put(ITEM_BARCODE, items.getItemBarcode());
        values.put(PRICE, items.getPrice());
        values.put(DESCRIPTION, items.getDescription());
        values.put(ITEM_PICTURE, byteImage);
        values.put(CATEGORY_PICTURE, byteCatImage);
        values.put(POINT,items.getPoint());

        db.insert(ITEMS_TABLE, null, values);
        db.close();
    }

    public void addUser(Users users) {
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME,users.getUsername() );
        values.put(PASSWORD, users.getPassword());

        db.insert(USERS_TABLE, null, values);
        db.close();
    }

    public void addTable(Tables tables) {
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(SECTION_NO,tables.getSectionNo() );
        values.put(TABEL_NO, tables.getTableNo());
        values.put(NO_OF_SEITS, tables.getNoOfSeits());

        db.insert(TABLES_TABLE, null, values);
        db.close();
    }


    public void addCustomer(CustomerInformation customerInformation) {
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(CUSTOMER_NAME,customerInformation.getCustomerName() );
        values.put(PHONE_NO, customerInformation.getPhoneNo());
        values.put(EMAIL, customerInformation.getEmail());
        values.put(POINT_CUSTOMER,customerInformation.getPoint());

        db.insert(CUSTOMER_INFORMATION, null, values);
        db.close();
    }

    //////////////////////////////////////////////////////// GET ///////////////////////////////////////////////

    public List<Items> getAllItems() {
        List<Items> items = new ArrayList<Items>();

        String selectQuery = "SELECT  * FROM " + ITEMS_TABLE;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Items item = new Items();

                item.setCategoryName(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setItemBarcode(Integer.parseInt(cursor.getString(2)));
                item.setPrice(Double.parseDouble(cursor.getString(3)));
                item.setDescription(cursor.getString(4));

                if (cursor.getBlob(5).length == 0)
                    item.setItemPic(null);
                else
                    item.setItemPic(BitmapFactory.decodeByteArray(cursor.getBlob(5), 0, cursor.getBlob(5).length));


                if (cursor.getBlob(6).length == 0)
                    item.setCategoryPic(null);
                else
                    item.setCategoryPic(BitmapFactory.decodeByteArray(cursor.getBlob(6), 0, cursor.getBlob(6).length));

                item.setPoint(cursor.getInt(7));

//                if (cursor.getBlob(20).length == 0)
//                    item.setPic(null);
//                else
//                    item.setPic(BitmapFactory.decodeByteArray(cursor.getBlob(20), 0, cursor.getBlob(20).length));

                // Adding transaction to list


                items.add(item);
            } while (cursor.moveToNext());
        }
        return items;
    }

    public List<Users> getAllUSER() {
        List<Users> usersList = new ArrayList<Users>();

        String selectQuery = "SELECT  * FROM " + USERS_TABLE;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Users users = new Users();

                users.setUsername(cursor.getString(0));
                users.setPassword(cursor.getString(1));

                usersList.add(users);
            } while (cursor.moveToNext());
        }
        return usersList;
    }



    public List<CustomerInformation> getAllInformation() {
        List<CustomerInformation> usersList = new ArrayList<CustomerInformation>();

        String selectQuery = "SELECT  * FROM " + CUSTOMER_INFORMATION;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CustomerInformation users = new CustomerInformation();

                users.setCustomerName(cursor.getString(0));
                users.setPhoneNo(cursor.getString(1));
                users.setEmail(cursor.getString(2));
                users.setPoint(cursor.getDouble(3));

                usersList.add(users);
            } while (cursor.moveToNext());
        }
        return usersList;
    }

    public List<Tables> getTablesInfo() {
        List<Tables> tables = new ArrayList<Tables>();

        String selectQuery = "SELECT  * FROM " + TABLES_TABLE;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Tables tables1 = new Tables();

                tables1.setSectionNo(cursor.getString(0));
                tables1.setTableNo(cursor.getInt(1));
                tables1.setNoOfSeits(cursor.getInt(2));

                tables.add(tables1);
            } while (cursor.moveToNext());
        }
        return tables;
    }

    //////////////////////////////////////////////////////// DELETE ///////////////////////////////////////////////

    public void deleteAllUsers() {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + USERS_TABLE + ";");
        db.close();
    }

    public void deleteAllInformation() {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + CUSTOMER_INFORMATION + ";");
        db.close();
    }

    public void updateCustomerPoint(String phoneNo, double points) {

        CustomerInformation customerInformation=new CustomerInformation();
        db = this.getWritableDatabase();
             ContentValues values = new ContentValues();

             values.put(POINT_CUSTOMER, points);
             db.update(CUSTOMER_INFORMATION, values, PHONE_NO + " = '" + phoneNo +"'", null);
    }

}
