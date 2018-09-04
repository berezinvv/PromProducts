package vitalii.promproducts.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vitalii.promproducts.database.model.Product;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "products_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Product.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Product.TABLE_NAME);
        onCreate(db);
    }

    public long insertProduct(Product product) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Product.COLUMN_TITLE, product.getTitle());
        values.put(Product.COLUMN_DESCRIPTION, product.getDescription());
        values.put(Product.COLUMN_PRICE, product.getPrice());
        values.put(Product.COLUMN_OLD_PRICE, product.getOld_price());
        values.put(Product.COLUMN_IMG_HIRES, product.getImg_hires());
        values.put(Product.COLUMN_IMG_HIRES_PREVIEW, product.getImg_hires_preview());

        long id = db.insert(Product.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Product.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex(Product.COLUMN_ID)));
                product.setTitle(cursor.getString(cursor.getColumnIndex(Product.COLUMN_TITLE)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(Product.COLUMN_DESCRIPTION)));
                product.setPrice(cursor.getFloat(cursor.getColumnIndex(Product.COLUMN_PRICE)));
                product.setOld_price(cursor.getFloat(cursor.getColumnIndex(Product.COLUMN_OLD_PRICE)));
                product.setImg_hires(cursor.getString(cursor.getColumnIndex(Product.COLUMN_IMG_HIRES)));
                product.setImg_hires_preview(cursor.getString(cursor.getColumnIndex(Product.COLUMN_IMG_HIRES_PREVIEW)));


                products.add(product);
            } while (cursor.moveToNext());
        }

        db.close();

        return products;
    }

//    public int updateProduct(Product product) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Product.COLUMN_TITLE, product.getTitle());
//        values.put(Product.COLUMN_DESCRIPTION, product.getDescription());
//        values.put(Product.COLUMN_PRICE, product.getPrice());
//        values.put(Product.COLUMN_OLD_PRICE, product.getOld_price());
//        values.put(Product.COLUMN_IMG_HIRES, product.getImg_hires());
//        values.put(Product.COLUMN_IMG_HIRES_PREVIEW, product.getImg_hires_preview());
//
//        // updating row
//        return db.update(Product.TABLE_NAME, values, Product.COLUMN_ID + " = ?",
//                new String[]{String.valueOf(product.getId())});
//    }

    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Product.TABLE_NAME, Product.COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        db.close();
    }
    public void deleteProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Product.TABLE_NAME, null, null);
        db.close();
    }
}
