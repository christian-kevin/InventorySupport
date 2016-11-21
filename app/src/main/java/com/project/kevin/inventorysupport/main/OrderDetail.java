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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 09/11/16.
 */

public class OrderDetail extends AppCompatActivity {
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    JSONObject jObject = new JSONObject();
    JSONArray jArray = new JSONArray();
    private static String url = "http://www.tunasalfin.com/orderdetail.php";
    private static String url2 = "http://www.tunasalfin.com/orderdetailta2.php";

    private TextView norek,ukuran,namabarang,customer;
    private Button buttonback;
    private ArrayList<HashMap<String, String>> itemList;
    private ListView lv;
    String title;
    // private String strtanggal,strstokawalminggu,strin,strout,strstokakhirminggu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        norek=(TextView) findViewById(R.id.textnorek);
        namabarang=(TextView) findViewById(R.id.textnamabarang);
        ukuran=(TextView) findViewById(R.id.textukuran);
        customer=(TextView) findViewById(R.id.textcustomer);
            norek.setText(getIntent().getExtras().getString("norek"));
            namabarang.setText(getIntent().getExtras().getString("namabarang"));
            ukuran.setText(getIntent().getExtras().getString("ukuran"));
            customer.setText(getIntent().getExtras().getString("customer"));


            title="Outstanding Order";

        if(getIntent().getExtras().getInt("company")==1)
        {
            getSupportActionBar().setSubtitle("TA 1");
        }
        if(getIntent().getExtras().getInt("company")==2)
        {
            getSupportActionBar().setSubtitle("TA 2");
        }


        itemList = new ArrayList<>();
        new OrderList().execute();

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = (ListView)findViewById(R.id.list);



        // when i=-1, loop will display heading of each column
        // then usually data will be display from i=0 to jArray.length()


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.a
        int id = item.getItemId();

        if (id == android.R.id.home){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    class OrderList extends AsyncTask<Void,Void,Void> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OrderDetail.this);
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
                if(getIntent().getExtras().getInt("company")==1)
                    jObject = jParser.makeHttpRequest(url, "GET", params);
                if(getIntent().getExtras().getInt("company")==2)
                    jObject = jParser.makeHttpRequest(url2, "GET", params);
                Log.d("SearchResponse", jObject.toString());
                jArray = jObject.getJSONArray(getIntent().getExtras().getString("norek"));
                Log.d("Array",jArray.toString());

                for(int i=0;i<jArray.length();i++) {
                    JSONObject c = jArray.getJSONObject(i);
                    String tanggal = c.getString("tanggal");
                    String noos = c.getString("noos");
                    String tanggalos = c.getString("tanggalos");
                    String nopo = c.getString("nopo");
                    String tanggaldorev = c.getString("tanggaldorev");
                    String order = c.getString("jmlorder");
                    String pengiriman = c.getString("pengiriman");
                    String sisaorder = c.getString("sisaorder");
                    String uom = c.getString("uom");
                    //Log.d("Norek", norek);
                    HashMap<String, String> item = new HashMap<>();

                    // adding each child node to HashMap key => value
                    item.put("tanggal", tanggal);
                    item.put("noos", noos);
                    item.put("tanggalos", tanggalos);
                    item.put("nopo", nopo);
                    item.put("tanggaldorev", tanggaldorev);
                    item.put("order", order);
                    item.put("pengiriman", pengiriman);
                    item.put("sisaorder", sisaorder);
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
                    OrderDetail.this, itemList,
                    R.layout.order_list_detail, new String[]{"tanggal", "noos",
                    "tanggalos","nopo","tanggaldorev","order","pengiriman","sisaorder","uom"}, new int[]{R.id.tanggal,
                    R.id.noos, R.id.tanggalos,R.id.nopo,R.id.tanggaldorev,R.id.order,R.id.pengiriman,R.id.sisaorder,R.id.uom});

            lv.setAdapter(adapter);

        }
    }

}
