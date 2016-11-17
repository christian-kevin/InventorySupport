package com.project.kevin.inventorysupport.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
 * Created by lenovo on 05/11/16.
 */

public class ViewListGBJ extends AppCompatActivity {
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    JSONObject jObject = new JSONObject();
    private static String url_gbj = "http://10.0.2.2:8080/gudangbarangjadi.php";
    private static String url_gbj2 = "http://10.0.2.2:8080/gudangbarangjadita2.php";

    private ArrayList<HashMap<String, String>> itemList;

    private ListView lv;
    private String strnorek,strnamabarang,strukuran,strcustomer;
    private TextView updatedate;
    String date,title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        itemList = new ArrayList<>();
        updatedate=(TextView)findViewById(R.id.updatedate);

        if(getIntent().getExtras().getInt("jenisgudang")==3)
        {
            title="Gudang Barang Jadi";
        } else if(getIntent().getExtras().getInt("jenisgudang")==4)
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

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        lv = (ListView)findViewById(R.id.list);
        lv.setClickable(true);
        new DisplayList().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext(), DateSelect.class);
                HashMap<String,String> o = itemList.get(position);
                strnorek = o.get("norek") ;
                strnamabarang = o.get("namabarang") ;
                strcustomer = o.get("customer") ;
                strukuran = o.get("ukuran") ;//As you are using Default String Adapter
                intent.putExtra("norek",strnorek);
                intent.putExtra("namabarang",strnamabarang);
                intent.putExtra("customer",strcustomer);
                intent.putExtra("ukuran",strukuran);
                intent.putExtra("jenisgudang",getIntent().getExtras().getInt("jenisgudang"));
                intent.putExtra("company",getIntent().getExtras().getInt("company"));
                startActivity(intent);
                //Toast.makeText(getBaseContext(),strnorek,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            Intent intent = new Intent(getApplicationContext(),SearchFormGBJ.class);
            intent.putExtra("jenisgudang",getIntent().getExtras().getInt("jenisgudang"));
            intent.putExtra("company",getIntent().getExtras().getInt("company"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    class DisplayList extends AsyncTask<Void,Void,Void> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewListGBJ.this);
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
            params.add(new BasicNameValuePair("namabarang",getIntent().getExtras().getString("namabarang")));
            params.add(new BasicNameValuePair("customer",getIntent().getExtras().getString("customer")));
            params.add(new BasicNameValuePair("kodematerial",getIntent().getExtras().getString("kodematerial")));
            //params.add(new BasicNameValuePair("datestart",getIntent().getExtras().getString("datestart")));
            //params.add(new BasicNameValuePair("dateend",getIntent().getExtras().getString("dateend")));
            try {
                if(getIntent().getExtras().getInt("company")==1)
                    jObject = jParser.makeHttpRequest(url_gbj, "GET", params);
                if(getIntent().getExtras().getInt("company")==2)
                    jObject = jParser.makeHttpRequest(url_gbj2, "GET", params);
                Log.d("SearchResponse", jObject.toString());
                JSONArray barang = jObject.getJSONArray("barang");
                Log.d("Array",barang.toString());
                for(int i=0;i<barang.length();i++)
                {
                    JSONObject c = barang.getJSONObject(i);
                    String norek = c.getString("norek");
                    String namabarang = c.getString("namabarang");
                    String kodematerial = c.getString("kodematerial");
                    String ukuran = c.getString("ukuran");
                    String customer = c.getString("customer");
                    String stock = ("Stock : " + c.getString("stokakhirminggujml") + " " + c.getString("uom"));
                    date=c.getString("tanggal");
                    //Log.d("Norek",norek);
                    HashMap<String, String> item = new HashMap<>();

                    // adding each child node to HashMap key => value
                    item.put("norek", norek);
                    item.put("namabarang", namabarang);
                    item.put("kodematerial", kodematerial);
                    item.put("ukuran", ukuran);
                    item.put("customer", customer);
                    item.put("stock",stock);

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
            /**
             * Updating parsed JSON data into ListView
             * */
            Log.d("Posting",itemList.toString());
            ListAdapter adapter = new SimpleAdapter(
                    ViewListGBJ.this, itemList,
                    R.layout.list_item_gbj, new String[]{"norek", "kodematerial",
                    "namabarang","ukuran","customer","stock"}, new int[]{R.id.norek,
                    R.id.kodematerial, R.id.namabarang,R.id.ukuran,R.id.customer,R.id.stock});
            updatedate.setText("Update : " + date);

            lv.setAdapter(adapter);
        }
    }

}
