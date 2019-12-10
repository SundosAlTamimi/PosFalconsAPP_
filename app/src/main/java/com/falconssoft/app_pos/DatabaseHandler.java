package com.falconssoft.app_pos;

import android.app.Notification;
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
import com.falconssoft.app_pos.models.NotificationModel;
import com.falconssoft.app_pos.models.Order;
import com.falconssoft.app_pos.models.Tables;
import com.falconssoft.app_pos.models.Users;
import com.falconssoft.app_pos.models.Items;
import com.falconssoft.app_pos.models.Tables;
import com.falconssoft.app_pos.models.Users;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =5;
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
    private static final String QTY_ITEM ="QTY_ITEM";
    private static final String TAX ="TAX";


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
    private static final String ORDER_PAY = "ORDER_PAY";

    private static final String CUSTOMER_NAME1 = "CUSTOMER_NAME";
    private static final String CustomerNo1 = "CustomerNo";
    private static final String POINT_CUSTOMER1 = "POINT_CUSTOMER";
    private static final String TOTAL1 = "TOTAL";
    private static final String DATE_FOR_PAY = "DATE_FOR_PAY";
    private static final String QTY = "QTY";
    private static final String VOUCH_NO = "VOUCH_NO";

    private static final String TOTAL_BEFORE_TAX = "TOTAL_BEFORE_TAX";
    private static final String TOTAL_AFTER_TAX = "TOTAL_AFTER_TAX";
    private static final String TAX_VALUE = "TAX_VALUE";
    private static final String ITEM_NAME1 = "ITEM_NAME";
    private static final String ITEM_BARCODE1 = "ITEM_BARCODE";
    private static final String PRICE1 = "PRICE";
    // *******************************************************************************
    private static final String NOTIFICATION = "NOTIFICATION";

    private static final String DESCRIPTION2 = "DESCRIPTION";
    private static final String DATE_FOR_NOTIFICATION2 = "DATE_FOR_NOTIFICATION";
    private static final String NOTIFICATION_NAME2 = "NOTIFICATION_NAME";
    private static final String TIME_FOR_NOTIFICATION = "TIME_FOR_NOTIFICATION";
    private static final String POINT_FOR_NOTIFICATION = "POINT_FOR_NOTIFICATION";

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
                + POINT + " REAL,"
                + QTY_ITEM + " REAL,"
                + TAX + " REAL" +


                ")";

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
        String CREATE_NOTIFICATION_TABLE = "CREATE TABLE " + NOTIFICATION + "("
                + DESCRIPTION2 + " TEXT,"
                + DATE_FOR_NOTIFICATION2 + " TEXT,"
                + NOTIFICATION_NAME2 + " TEXT,"
                + POINT_FOR_NOTIFICATION + " TEXT,"
                +  TIME_FOR_NOTIFICATION+ " TEXT"

                + ")";
        db.execSQL(CREATE_NOTIFICATION_TABLE);
        // *******************************************************************************

        String CREATE_ORDER_TABLE = "CREATE TABLE " + ORDER_PAY + "("
                + CUSTOMER_NAME1 + " TEXT,"
                + CustomerNo1 + " TEXT,"
                + POINT_CUSTOMER1 + " REAL,"
                + TOTAL1 + " REAL,"
                +  DATE_FOR_PAY+ " TEXT,"
                +  QTY+ " REAL,"
                +  VOUCH_NO+ " TEXT ,"
                +  TOTAL_BEFORE_TAX+ " REAL,"
                +  TOTAL_AFTER_TAX+ " REAL,"
                +  TAX_VALUE+ " REAL ,"
                +  ITEM_NAME1+ " TEXT ,"
                +  ITEM_BARCODE1+ " TEXT ,"
                +  PRICE1+ " REAL"
                + ")";
        db.execSQL(CREATE_ORDER_TABLE);
        // *******************************************************************************

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("ALTER TABLE ITEMS_TABLE ADD TAX  REAL NOT NULL DEFAULT '0'");
        }catch (Exception e)
        {
            Log.e(TAG, e.getMessage().toString());
        }
        try{
            db.execSQL("ALTER TABLE ITEMS_TABLE ADD QTY_ITEM  REAL NOT NULL DEFAULT '0'");
        }catch (Exception e)
        {
            Log.e(TAG, e.getMessage().toString());
        }
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

//        byte[] byteImage = {};
//        byte[] byteCatImage = {};
//        if (items.getItemPic() != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            items.getItemPic().compress(Bitmap.CompressFormat.PNG, 0, stream);
//            byteImage = stream.toByteArray();
//        }
//        if (items.getCategoryPic() != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            items.getCategoryPic().compress(Bitmap.CompressFormat.PNG, 0, stream);
//            byteCatImage = stream.toByteArray();
//        }
        values.put(CATEGORY_NAME, items.getCategoryName());
        values.put(ITEM_NAME, items.getItemName());
        values.put(ITEM_BARCODE, items.getItemBarcode());
        values.put(PRICE, items.getPrice());
        values.put(DESCRIPTION, items.getDescription());
        values.put(ITEM_PICTURE, items.getItemPic());
        values.put(CATEGORY_PICTURE, items.getCategoryPic());
        values.put(POINT,items.getPoint());
        values.put(QTY_ITEM,items.getQTY());
        values.put(TAX,items.getTax());
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

    public void AddNotification(NotificationModel notificationModel) {
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(DESCRIPTION2,notificationModel.getDescription() );
        values.put(DATE_FOR_NOTIFICATION2, notificationModel.getDate());

        values.put(NOTIFICATION_NAME2,notificationModel.getNotificationName() );
        values.put(TIME_FOR_NOTIFICATION, notificationModel.getTime());
        values.put(POINT_FOR_NOTIFICATION, notificationModel.getPoint());


        db.insert(NOTIFICATION, null, values);
        db.close();
    }


    public void AddOrdre(Order order) {
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(CUSTOMER_NAME1,order.getCustomerName() );
        values.put(CustomerNo1, order.getCustomerNo());
        values.put(POINT_CUSTOMER1,order.getNoPoint() );
        values.put(TOTAL1, order.getTotal());
        values.put(DATE_FOR_PAY, order.getDate());
        values.put(QTY, order.getQty());
        values.put(VOUCH_NO, order.getVhNo());
        values.put(TOTAL_BEFORE_TAX, order.getTotalBeforeTax());
        values.put(TOTAL_AFTER_TAX, order.getTotalAfterTax());
        values.put(TAX_VALUE, order.getTax());
        values.put(ITEM_NAME1, order.getItemName());

        values.put(ITEM_BARCODE1, order.getItemBarcode());
        values.put(PRICE1, order.getPrice());

        db.insert(ORDER_PAY, null, values);
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

        String selectQuery = "SELECT  * FROM  ITEMS";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e("select",""+selectQuery);

        if (cursor.moveToFirst()) {
            do {
                Items item = new Items();

                item.setCategoryName(cursor.getString(0));
                item.setItemName(cursor.getString(1));
                item.setItemBarcode(Integer.parseInt(cursor.getString(2)));
                item.setPrice(Double.parseDouble(cursor.getString(3)));
                item.setDescription(cursor.getString(4));
                item.setItemPic(cursor.getString(5));
                item.setCategoryPic(cursor.getString(6));
                item.setPoint(cursor.getInt(7));
                item.setQTY(cursor.getDouble(8));
                item.setTax(cursor.getDouble(9));

                items.add(item);

                // Adding transaction to list
            } while (cursor.moveToNext());
        }
        Log.e("items=",""+items.size());
        return items;
    }

    public List<Items> getAllCategory() {
        List<Items> items = new ArrayList<>();

//        String selectQuery = "SELECT DISTINCT CATEGORY_NAME FROM " + ITEMS_TABLE;
        String selectQuery = "SELECT * FROM ITEMS GROUP BY CATEGORY_NAME ";

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
                item.setItemPic(cursor.getString(5));
                item.setCategoryPic(cursor.getString(6));
                item.setPoint(cursor.getInt(7));

                items.add(item);
//                if (cursor.getBlob(5).length == 0)
//                    item.setItemPic(null);
//                else
//                    item.setItemPic(BitmapFactory.decodeByteArray(cursor.getBlob(5), 0, cursor.getBlob(5).length));
//
//
//                if (cursor.getBlob(6).length == 0)
//                    item.setCategoryPic(null);
//                else
//                    item.setCategoryPic(BitmapFactory.decodeByteArray(cursor.getBlob(6), 0, cursor.getBlob(6).length));
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

    public List<NotificationModel> getAllNotification() {
        List<NotificationModel> notificationModels = new ArrayList<NotificationModel>();

        String selectQuery = "SELECT  * FROM " + NOTIFICATION;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NotificationModel notificationModel = new NotificationModel();

                notificationModel.setDescription(cursor.getString(0));
                notificationModel.setDate(cursor.getString(1));
                notificationModel.setNotificationName(cursor.getString(2));
                notificationModel.setPoint(cursor.getString(3));
                notificationModel.setTime(cursor.getString(4));

                notificationModels.add(notificationModel);
            } while (cursor.moveToNext());
        }
        return notificationModels;
    }

    public List<Order> getAllOrder() {
        List<Order> orderArrayList = new ArrayList<Order>();

        String selectQuery = "SELECT  * FROM " + ORDER_PAY;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();

                order.setCustomerName(cursor.getString(0));
                order.setCustomerNo(cursor.getString(1));
                order.setNoPoint(cursor.getDouble(2));
                order.setTotal(cursor.getDouble(3));
                order.setDate(cursor.getString(4));
                order.setQty(cursor.getDouble(5));
                order.setVhNo(cursor.getString(6));
                order.setTotalBeforeTax(cursor.getDouble(7));
                order.setTotalAfterTax(cursor.getDouble(8));
                order.setTax(cursor.getDouble(9));
                order.setItemName(cursor.getString(10));
                order.setItemBarcode(cursor.getString(11));
                order.setPrice(cursor.getDouble(12));

                orderArrayList.add(order);
            } while (cursor.moveToNext());
        }
        return orderArrayList;
    }

    public List<Order> getOrderByDate(String Date) {
        List<Order> orderArrayList = new ArrayList<Order>();

        String selectQuery = "SELECT  * FROM " + ORDER_PAY+" where DATE_FOR_PAY = '"+Date+"'";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();

                order.setCustomerName(cursor.getString(0));
                order.setCustomerNo(cursor.getString(1));
                order.setNoPoint(cursor.getDouble(2));
                order.setTotal(cursor.getDouble(3));
                order.setDate(cursor.getString(4));
                order.setQty(cursor.getDouble(5));
                order.setVhNo(cursor.getString(6));
                order.setTotalBeforeTax(cursor.getDouble(7));
                order.setTotalAfterTax(cursor.getDouble(8));
                order.setTax(cursor.getDouble(9));
                order.setItemName(cursor.getString(10));
                order.setItemBarcode(cursor.getString(11));
                order.setPrice(cursor.getDouble(12));


                orderArrayList.add(order);
            } while (cursor.moveToNext());
        }
        return orderArrayList;
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

    public void deleteAllItems() {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + ITEMS_TABLE + ";");
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
    public void updateitem_information(String categoryName,String itemName,
                                       int itemBarcode,String description, double price,String ItemPic,double QTY,int point) {

       Items items=new Items();
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(ITEM_NAME, itemName);
        values.put(PRICE, price);

        values.put(ITEM_BARCODE, itemBarcode);

        values.put(DESCRIPTION, description);

        values.put(ITEM_PICTURE, ItemPic);

        values.put(POINT, point);
        values.put(QTY_ITEM, QTY);


        db.update(ITEMS_TABLE, values, CATEGORY_NAME + " = '" + categoryName +"'", null);
    }

}
