package vitalii.promproducts;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import vitalii.promproducts.database.DatabaseHelper;
import vitalii.promproducts.database.model.Product;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private List<Product> productsList = new ArrayList<>();
    private ListView listview;
    private ArrayAdapter<Product> adapter;

    private ProgressDialog progressDialog;

    private static final String URL_ALL_GOODS = "http://tester.atbmarket.com/api2/catalogue/product-list/?type=2";
    private static final String URL_FOR_KITHEN = "http://tester.atbmarket.com/api2/catalogue/action-products/?type=2&&id=1";
    private static final String URL_CLOTHING = "http://tester.atbmarket.com/api2/catalogue/action-products/?type=2&&id=2";
    private static final String URL_CHILDRENS_GOODS = "http://tester.atbmarket.com/api2/catalogue/action-products/?type=2&&id=3";
    private static final String URL_HOUSEHOLD_GOODS = "http://tester.atbmarket.com/api2/catalogue/action-products/?type=2&&id=4";


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_аll_goods:
                new DataTask().execute(URL_ALL_GOODS);
                //reloadListView();
                return true;
            case R.id.action_for_kitchen:
                new DataTask().execute(URL_FOR_KITHEN);
                //reloadListView();
                return true;
            case R.id.action_clothing:
                new DataTask().execute(URL_CLOTHING);
                //reloadListView();
                return true;
            case R.id.action_childens_goods:
                new DataTask().execute(URL_CHILDRENS_GOODS);
                //reloadListView();
                return true;
            case R.id.action_household_goods:
                new DataTask().execute(URL_HOUSEHOLD_GOODS);
                //reloadListView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = new DatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listview);

        productsList.addAll(db.getAllProducts());

        adapter = new ProductListAdapter(this, R.layout.item_list, productsList);
        listview.setAdapter(adapter);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                Product itemPosition = (Product)parent.getItemAtPosition(position);
//
//                ImageView imageView = (ImageView) findViewById(R.id.imgHiresPreview);
//
//                Picasso.with(MainActivity.this)
//                        .load(itemPosition.getImg_hires_preview())
//                        .noFade()
//                        .resize(800, 800)
//                        .centerCrop()
//                        .into(imageView);
////                WebView myWebView = new WebView(MainActivity.this);
////                myWebView.loadUrl(itemPosition.getImg_hires_preview());
////                setContentView(myWebView);
//            }
//        });

    }

    private class DataTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            InputStream inputStream = null;
            String result = null;
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);

            try {

                HttpResponse response = client.execute(httpGet);
                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                } else
                    result = "Failed to fetch data";

                return result;

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String dataFetched) {
            parseJSONAndAddToDB(dataFetched);
            progressDialog.dismiss();
            adapter.notifyDataSetChanged();
        }


        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        private void parseJSONAndAddToDB(String data) {
            db.deleteProducts();
            try{
                JSONObject jsonObject = new JSONObject(data);

                JSONArray jsonProducts = jsonObject.getJSONArray("products");

                productsList.clear();

                int jsonArrLength = jsonProducts.length();
                for(int i=0; i < jsonArrLength; i++) {

                    JSONObject jsonProduct = jsonProducts.getJSONObject(i);

                    Product product = new Product();

                    product.setTitle(jsonProduct.getString("title").toString());
                    product.setDescription(jsonProduct.getString("description").toString());
                    product.setPrice(Float.valueOf(jsonProduct.getString("price")));
                    product.setOld_price(Float.valueOf(jsonProduct.getString("old_price")));


                    JSONObject jsonImg = jsonProduct.getJSONObject("img");
                    product.setImg_hires(jsonImg.getString("hires").toString());
                    product.setImg_hires_preview(jsonImg.getString("hires_preview").toString());

                    db.insertProduct(product);
                    productsList.add(product);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


//    public String getData(String strUrl) {
//
//        String jsonText = "";
//        InputStream iStream = null;
//        try {
//            URL url = new URL(strUrl);
//
//            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
//            httpConnection.setRequestMethod("GET");
//            httpConnection.setReadTimeout(15000);
//            httpConnection.setConnectTimeout(15000);
//            httpConnection.setDoOutput(true);
//
//
//            httpConnection.connect();
//
//            iStream = httpConnection.getInputStream();
//            try {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(iStream, "utf-8"), 8);
//                StringBuilder sb = new StringBuilder();
//                String line = null;
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line);
//                }
//                jsonText = sb.toString();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                iStream.close();
//            }
//            //leo = objectMapper.readValue(jsonText, Leo.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return jsonText;
//   }
}
