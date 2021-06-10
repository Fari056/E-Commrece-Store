package com.Arid2760.fsshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.Arid2760.fsshop.gertterSetter.GetUserData;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String database_name = "FSElectDatabase";
    private static final String table_name = "Users";
    private static final String Item_table = "Products";
    private static final int db_version = 1;
    private ByteArrayOutputStream BOS;
    private byte[] imageInByte;

    public DatabaseHelper(Context context) {
        super(context, database_name, null, db_version);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_name + "( userId INTEGER PRIMARY KEY AUTOINCREMENT,userName VARCHAR,userEmail VARCHAR,userPassword VARCHAR,userPhone VARCHAR,userDOB VARCHAR,userGender VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Item_table + "( prodId INTEGER PRIMARY KEY AUTOINCREMENT,prodName VARCHAR,prodPrice INTEGER,prodDescription VARCHAR,prodImage BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }

    public boolean signUp(GetUserData data) {

        boolean result = true;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("userName", data.getUserName());
            contentValues.put("userEmail", data.getUserEmail());
            contentValues.put("userPassword", data.getUserPassword());
            contentValues.put("userPhone", data.getUserPhone());
            contentValues.put("userDOB", data.getUserDOB());
            contentValues.put("userGender", data.getUserGender());
            result = sqLiteDatabase.insert(table_name, null, contentValues) > 0;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public GetUserData login(String email, String password) {
        GetUserData data = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from " + table_name + " where userEmail =? and userPassword =?", new String[]{email, password});
            if (cursor.moveToFirst()) {
                data = new GetUserData();
                data.setId(cursor.getInt(0));
                data.setUserName(cursor.getString(1));
                data.setUserEmail(cursor.getString(2));
                data.setUserPassword(cursor.getString(3));
                data.setUserPhone(cursor.getString(4));
                data.setUserDOB(cursor.getString(5));
                data.setUserGender(cursor.getString(6));
            }
        } catch (Exception e) {
            data = null;
        }
        return data;
    }
//
//    public GetUserData find(int id) {
//        GetUserData data = null;
//        try {
//            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from " + table_name + " WHERE userId =?", new String[]{String.valueOf(id)});
//            if (cursor.moveToFirst()) {
//                data.setId(cursor.getInt(0));
//                data.setUserName(cursor.getString(1));
//                data.setUserEmail(cursor.getString(2));
//                data.setUserPassword(cursor.getString(3));
//                data.setUserPhone(cursor.getString(4));
//                data.setUserDOB(cursor.getString(5));
//                data.setUserGender(cursor.getString(6));
//            }
//        } catch (Exception e) {
//            data = null;
//        }
//        return data;
//    }

    public GetUserData checkUserName(String email) {
        GetUserData data = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from " + table_name + " WHERE userEmail =? ", new String[]{email});
            if (cursor.moveToFirst()) {
                data.setId(cursor.getInt(0));
                data.setUserName(cursor.getString(1));
                data.setUserEmail(cursor.getString(2));
                data.setUserPassword(cursor.getString(3));
                data.setUserPhone(cursor.getString(4));
                data.setUserDOB(cursor.getString(5));
                data.setUserGender(cursor.getString(6));
            }
        } catch (Exception e) {
            data = null;
        }
        return data;
    }

    public boolean updateUser(GetUserData data) {
        boolean result = true;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("userName", data.getUserName());
            contentValues.put("userEmail", data.getUserEmail());
            contentValues.put("userPassword", data.getUserPassword());
            contentValues.put("userPhone", data.getUserPhone());
            contentValues.put("userDOB", data.getUserDOB());
            contentValues.put("userGender", data.getUserGender());
            // updating row
            result = db.update(table_name, contentValues, "userId =?", new String[]{String.valueOf(data.getId())}) > 0;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public GetUserData getUserRow(String id) {
        GetUserData data = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from " + table_name + " where userId =?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                data = new GetUserData();
                data.setId(cursor.getInt(0));
                data.setUserName(cursor.getString(1));
                data.setUserEmail(cursor.getString(2));
                data.setUserPassword(cursor.getString(3));
                data.setUserPhone(cursor.getString(4));
                data.setUserDOB(cursor.getString(5));
                data.setUserGender(cursor.getString(6));
            }
        } catch (Exception e) {
            data = null;
        }
        return data;
    }

    public ArrayList<HashMap<String, String>> getAllUsers() {

        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT  * FROM " + table_name;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("userId", cursor.getString(0));
                data.put("userName", cursor.getString(1));
                data.put("userEmail", cursor.getString(2));
                data.put("userPassword", cursor.getString(3));
                data.put("userPhone", cursor.getString(4));
                data.put("userDOB", cursor.getString(5));

                wordList.add(data);
            } while (cursor.moveToNext());
        }
        return wordList;
    }

    public void deleteUser(String Id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(" DELETE FROM " + table_name + " Where userId ='" + Id + "'");
    }

    //User data Ends


    //Product Data Start

    public boolean insertProduct(GetProductData data) {

        boolean result = true;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Bitmap bitmap = data.getImageBitmap();
            BOS = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, BOS);
            imageInByte = BOS.toByteArray();

            ContentValues contentValues = new ContentValues();
            contentValues.put("prodName", data.getName());
            contentValues.put("prodPrice", data.getPrice());
            contentValues.put("prodDescription", data.getDescription());
            contentValues.put("prodImage", imageInByte);
            result = sqLiteDatabase.insert(Item_table, null, contentValues) > 0;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public ArrayList<GetProductData> getAllProduct() {

        ArrayList<GetProductData> wordList;
        wordList = new ArrayList<GetProductData>();

        String selectQuery = "SELECT  * FROM " + Item_table + "";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                GetProductData data = new GetProductData();
                data.setId(cursor.getInt(0));
                data.setName(cursor.getString(1));
                data.setPrice(cursor.getString(2));
                data.setDescription(cursor.getString(3));
                byte[] imageByte = cursor.getBlob(4);
                Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                data.setImageBitmap(image);
                wordList.add(data);
            } while (cursor.moveToNext());
        }
        return wordList;
    }

    public GetProductData getProductRow(String Id) {
        GetProductData data = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from " + Item_table + " where prodId =?", new String[]{Id});
            if (cursor.moveToFirst()) {
                data = new GetProductData();
                data.setId(cursor.getInt(0));
                data.setName(cursor.getString(1));
                data.setPrice(cursor.getString(2));
                data.setDescription(cursor.getString(3));
            }
        } catch (Exception e) {
            data = null;
        }
        return data;
    }

    public GetProductData checkProductName(String pName) {
        GetProductData pData = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from " + Item_table + " WHERE prodName =?", new String[]{pName});
            if (cursor.moveToFirst()) {
                pData.setId(cursor.getInt(0));
                pData.setPrice(cursor.getString(1));
                pData.setDescription(cursor.getString(2));

            }
        } catch (Exception e) {
            pData = null;
        }
        return pData;
    }

    public boolean updateProduct(GetProductData pData) {
        boolean result = true;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("prodName", pData.getName());
            contentValues.put("prodPrice", pData.getPrice());
            contentValues.put("prodDescription", pData.getDescription());
            result = sqLiteDatabase.update(Item_table, contentValues, "prodId =?", new String[]{String.valueOf(pData.getId())}) > 0;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public void deleteProduct(String Id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(" DELETE FROM " + Item_table + " Where prodId ='" + Id + "'");
    }


    public ArrayList<GetProductData> getSearchedProduct(String Name) {

        ArrayList<GetProductData> wordList;
        wordList = new ArrayList<GetProductData>();

        String selectQuery = "SELECT  * FROM " + Item_table + " where prodName like'" + "%" + Name + "%" + "'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                GetProductData data = new GetProductData();
                data.setId(cursor.getInt(0));
                data.setName(cursor.getString(1));
                data.setPrice(cursor.getString(2));
                data.setDescription(cursor.getString(3));
                byte[] imageByte = cursor.getBlob(4);
                Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                data.setImageBitmap(image);
                wordList.add(data);
            } while (cursor.moveToNext());
        }
        return wordList;
    }
    //Product Data End
}
