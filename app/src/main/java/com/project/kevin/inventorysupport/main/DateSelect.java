package com.project.kevin.inventorysupport.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.kevin.inventorysupport.R;
import com.project.kevin.inventorysupport.resources.MyEditDatePicker;

import java.util.Calendar;


/**
 * Created by lenovo on 05/11/16.
 */

public class DateSelect extends AppCompatActivity implements View.OnClickListener{
    private EditText datestart,dateend;
    private Button buttonNext,buttonBack;
    private MyEditDatePicker editDatePickerStart,editDatePickerEnd;
    Calendar myCalendar = Calendar.getInstance();
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_select);
        datestart = (EditText) findViewById(R.id.datestart);
        dateend = (EditText) findViewById(R.id.dateend);
        buttonNext=(Button)findViewById(R.id.buttonNext);
        buttonBack=(Button)findViewById(R.id.buttonBack);
        //datestart.setText(getIntent().getExtras().getString("customer"));

        if(getIntent().getExtras().getInt("jenisgudang")==1)
        {
            title="Gudang Bahan Baku";
        }
        else if(getIntent().getExtras().getInt("jenisgudang")==3)
        {
            title="Gudang Barang Jadi";
        } else if(getIntent().getExtras().getInt("jenisgudang")==4)
        {
            title="Outstanding Order";
        }

        datestart.setText(myCalendar.get(Calendar.YEAR) +"/"
                + (myCalendar.get(Calendar.MONTH) + 1) +"/"
                + "1");
        dateend.setText(myCalendar.get(Calendar.YEAR) +"/"
                + (myCalendar.get(Calendar.MONTH) + 1) +"/"
                + myCalendar.get(Calendar.DATE));
        editDatePickerStart = new MyEditDatePicker(this,R.id.datestart);
        editDatePickerEnd = new MyEditDatePicker(this,R.id.dateend);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        buttonNext.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

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


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonBack)
        {
            Intent intent = new Intent(getApplicationContext(), SearchFormGBJ.class);
            intent.putExtra("jenisgudang",getIntent().getExtras().getInt("jenisgudang"));
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonNext)
        {
            if(datestart.getText().toString().matches("") || dateend.getText().toString().matches(""))
                Toast.makeText(getBaseContext(),"Tanggal Belum Terisi!!!",Toast.LENGTH_SHORT).show();
            else {
                if (getIntent().getExtras().getInt("jenisgudang") == 3) {
                    Intent intent = new Intent(getApplicationContext(), ItemDetail.class);
                    intent.putExtra("norek", getIntent().getExtras().getString("norek"));
                    intent.putExtra("namabarang", getIntent().getExtras().getString("namabarang"));
                    intent.putExtra("customer", getIntent().getExtras().getString("customer"));
                    intent.putExtra("ukuran", getIntent().getExtras().getString("ukuran"));
                    intent.putExtra("jenisgudang", getIntent().getExtras().getInt("jenisgudang"));
                    intent.putExtra("datestart", datestart.getText().toString());
                    intent.putExtra("dateend", dateend.getText().toString());
                    startActivity(intent);
                }
                if (getIntent().getExtras().getInt("jenisgudang") == 1) {
                    Intent intent = new Intent(getApplicationContext(), ItemDetail.class);
                    intent.putExtra("norek", getIntent().getExtras().getString("norek"));
                    intent.putExtra("namabarang", getIntent().getExtras().getString("namabarang"));
                    intent.putExtra("ukuran", getIntent().getExtras().getString("ukuran"));
                    intent.putExtra("jenisgudang", getIntent().getExtras().getInt("jenisgudang"));
                    intent.putExtra("datestart", datestart.getText().toString());
                    intent.putExtra("dateend", dateend.getText().toString());
                    startActivity(intent);
                }
                if (getIntent().getExtras().getInt("jenisgudang") == 4) {
                    Intent intent = new Intent(getApplicationContext(), OrderDetail.class);
                    intent.putExtra("norek", getIntent().getExtras().getString("norek"));
                    intent.putExtra("namabarang", getIntent().getExtras().getString("namabarang"));
                    intent.putExtra("customer", getIntent().getExtras().getString("customer"));
                    intent.putExtra("ukuran", getIntent().getExtras().getString("ukuran"));
                    intent.putExtra("jenisgudang", getIntent().getExtras().getInt("jenisgudang"));
                    intent.putExtra("datestart", datestart.getText().toString());
                    intent.putExtra("dateend", dateend.getText().toString());
                    startActivity(intent);
                }
            }
        }
    }
}
