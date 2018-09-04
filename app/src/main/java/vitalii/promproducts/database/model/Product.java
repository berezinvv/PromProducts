package vitalii.promproducts.database.model;

public class Product {

    public static final String TABLE_NAME = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_OLD_PRICE = "old_price";
    public static final String COLUMN_IMG_HIRES = "img_hires";
    public static final String COLUMN_IMG_HIRES_PREVIEW = "img_hires_preview";


    private int id;
    private String title;
    private String description;
    private float price;
    private float old_price;
    private String img_hires;
    private String img_hires_preview;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_PRICE + " FLOAT,"
                    + COLUMN_OLD_PRICE + " FLOAT,"
                    + COLUMN_IMG_HIRES + " TEXT,"
                    + COLUMN_IMG_HIRES_PREVIEW + " TEXT "
                    + ")";

    public Product() {
    }

    public Product(int id, String title, String description, float price, float old_price, String img_hires, String img_hires_preview) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.old_price = old_price;
        this.img_hires = img_hires;
        this.img_hires_preview = img_hires_preview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getOld_price() {
        return old_price;
    }

    public void setOld_price(float old_price) {
        this.old_price = old_price;
    }

    public String getImg_hires() {
        return img_hires;
    }

    public void setImg_hires(String img_hires) {
        this.img_hires = img_hires;
    }

    public String getImg_hires_preview() {
        return img_hires_preview;
    }

    public void setImg_hires_preview(String img_hires_preview) {
        this.img_hires_preview = img_hires_preview;
    }
}
