package com.project.kevin.inventorysupport.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.kevin.inventorysupport.R;

/**
 * Created by lenovo on 09/11/16.
 */

public class SearchFormGBB extends AppCompatActivity implements View.OnClickListener {

    private EditText norek,namabarang,ukuran;
    private Button buttonNext,buttonBack;
    String title;
    TextView ukurantext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_form_gbb);


        norek=(EditText)findViewById(R.id.norek);
        namabarang=(EditText)findViewById(R.id.namabarang);
        ukuran=(EditText)findViewById(R.id.ukuran);
        ukurantext=(TextView)findViewById(R.id.textView4);
        buttonNext=(Button)findViewById(R.id.buttonNext);
        buttonBack=(Button)findViewById(R.id.buttonBack);
        if(getIntent().getExtras().getInt("jenisgudang")==1)
        {
            title="Gudang Bahan Baku";
        }
        if(getIntent().getExtras().getInt("company")==1)
        {
            getSupportActionBar().setSubtitle("TA 1");
        }
        if(getIntent().getExtras().getInt("company")==2)
        {
            ukuran.setVisibility(View.INVISIBLE);
            ukurantext.setVisibility(View.INVISIBLE);
            getSupportActionBar().setSubtitle("TA 2");
        }


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
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonNext)
        {
            Intent intent = new Intent(getApplicationContext(), ViewListGBB.class);
            intent.putExtra("norek",norek.getText().toString());
            intent.putExtra("namabarang",namabarang.getText().toString());
            intent.putExtra("ukuran",ukuran.getText().toString());
            intent.putExtra("jenisgudang",getIntent().getExtras().getInt("jenisgudang"));
            intent.putExtra("company",getIntent().getExtras().getInt("company"));
            startActivity(intent);
        }

    }
}
