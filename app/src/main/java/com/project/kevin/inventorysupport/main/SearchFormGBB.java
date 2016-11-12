package com.project.kevin.inventorysupport.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.kevin.inventorysupport.R;

/**
 * Created by lenovo on 09/11/16.
 */

public class SearchFormGBB extends AppCompatActivity implements View.OnClickListener {

    private EditText norek,namabarang,ukuran;
    private Button buttonNext,buttonBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_form_gbb);


        norek=(EditText)findViewById(R.id.norek);
        namabarang=(EditText)findViewById(R.id.namabarang);
        ukuran=(EditText)findViewById(R.id.ukuran);
        buttonNext=(Button)findViewById(R.id.buttonNext);
        buttonBack=(Button)findViewById(R.id.buttonBack);
        buttonNext.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
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
            startActivity(intent);
        }

    }
}
