package com.project.kevin.inventorysupport.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.project.kevin.inventorysupport.R;
import com.project.kevin.inventorysupport.resources.JSONParser;
import com.project.kevin.inventorysupport.resources.ZoomListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 06/11/16.
 */

public class ItemDetail extends AppCompatActivity {
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    JSONObject jObject = new JSONObject();
    JSONArray jArray = new JSONArray();
    private static String url_gbj = "http://www.tunasalfin.com/gudangbarangjadidetail.php";
    private static String url_gbb = "http://www.tunasalfin.com/gudangbahanbakudetail.php";
    private static String url_gbb2 = "http://www.tunasalfin.com/gudangbahanbakuta2detail.php";
    private static String url_gbj2 = "http://www.tunasalfin.com/gudangbarangjadita2detail.php";

    private TextView norek,ukuran,namabarang,customer;
    private ArrayList<HashMap<String, String>> itemList;
    private ZoomListView lv;

    String title;
   // private String strtanggal,strstokawalminggu,strin,strout,strstokakhirminggu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);
        norek=(TextView) findViewById(R.id.textnorek);
        namabarang=(TextView) findViewById(R.id.textnamabarang);
        ukuran=(TextView) findViewById(R.id.textukuran);
        customer=(TextView) findViewById(R.id.textcustomer);

        if(getIntent().getExtras().getInt("jenisgudang")==1)
        {
            title="Gudang Bahan Baku";
        }
        else if(getIntent().getExtras().getInt("jenisgudang")==2)
        {
            title="Gudang Barang Jadi";
        } else if(getIntent().getExtras().getInt("jenisgudang")==3)
        {
            title="Outstanding Order";
        }
        if(getIntent().getExtras().getInt("company")==1)
        {
            getSupportActionBar().setSubtitle("TA 1");
        }
        if(getIntent().getExtras().getInt("company")==2)
        {
            getSupportActionBar().setSubtitle("TA 2");
        }
        if(getIntent().getExtras().getInt("jenisgudang")==2) {
            norek.setText(getIntent().getExtras().getString("norek"));
            namabarang.setText(getIntent().getExtras().getString("namabarang"));
            ukuran.setText(getIntent().getExtras().getString("ukuran"));
            customer.setText(getIntent().getExtras().getString("customer"));
        }
        if(getIntent().getExtras().getInt("jenisgudang")==1) {
            norek.setText(getIntent().getExtras().getString("norek"));
            namabarang.setText(getIntent().getExtras().getString("namabarang"));
            ukuran.setText(getIntent().getExtras().getString("ukuran"));
            customer.setText(" ");
        }

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemList = new ArrayList<>();
        new StockList().execute();

        lv = (ZoomListView)findViewById(R.id.list);


        // when i=-1, loop will display heading of each column
        // then usually data will be display from i=0 to jArray.length()


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }




    class StockList extends AsyncTask<Void,Void,Void> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ItemDetail.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            params.add(new BasicNameValuePair("norek",getIntent().getExtras().getString("norek")));
            params.add(new BasicNameValuePair("datestart",getIntent().getExtras().getString("datestart")));
            params.add(new BasicNameValuePair("dateend",getIntent().getExtras().getString("dateend")));
            try {
                if(getIntent().getExtras().getInt("jenisgudang")==2 && getIntent().getExtras().getInt("company")==1) {
                    jObject = jParser.makeHttpRequest(url_gbj, "GET", params);
                }
                if(getIntent().getExtras().getInt("jenisgudang")==2 && getIntent().getExtras().getInt("company")==2) {
                    jObject = jParser.makeHttpRequest(url_gbj2, "GET", params);
                }
                if(getIntent().getExtras().getInt("jenisgudang")==1 && getIntent().getExtras().getInt("company")==1) {
                    jObject = jParser.makeHttpRequest(url_gbb, "GET", params);
                }
                if(getIntent().getExtras().getInt("jenisgudang")==1 && getIntent().getExtras().getInt("company")==2) {
                    jObject = jParser.makeHttpRequest(url_gbb2, "GET", params);
                }
                Log.d("SearchResponse", jObject.toString());
                jArray = jObject.getJSONArray(getIntent().getExtras().getString("norek"));
                Log.d("Array",jArray.toString());

                for(int i=0;i<jArray.length();i++) {
                    JSONObject c = jArray.getJSONObject(i);
                    String tanggal = c.getString("tanggal");
                    String stokawal = c.getString("stokawalminggujml");
                    String in = c.getString("injml");
                    String out = c.getString("outjml");
                    String stokakhir = c.getString("stokakhirminggujml");
                    String uom = c.getString("uom");
                    //Log.d("Norek", norek);
                    HashMap<String, String> item = new HashMap<>();

                    // adding each child node to HashMap key => value
                    item.put("tanggal", tanggal);
                    item.put("awal", stokawal);
                    item.put("in", in);
                    item.put("out", out);
                    item.put("akhir", stokakhir);
                    item.put("uom", uom);

                    // adding contact to contact list
                    itemList.add(item);


                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // dismiss the dialog once done
            if (pDialog.isShowing())
                pDialog.dismiss();

            Log.d("Posting",itemList.toString());
            ListAdapter adapter = new SimpleAdapter(
                    ItemDetail.this, itemList,
                    R.layout.item_list_detail, new String[]{"tanggal", "awal",
                    "in","out","akhir","uom"}, new int[]{R.id.tanggal,
                    R.id.awal, R.id.in,R.id.out,R.id.akhir,R.id.uom});

            lv.setAdapter(adapter);

        }
    }

}
