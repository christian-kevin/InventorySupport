package com.project.kevin.inventorysupport.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.project.kevin.inventorysupport.R;

/**
 * Created by lenovo on 17/11/16.
 */

public class BranchCompanySelection extends AppCompatActivity implements View.OnClickListener {

    private RadioButton radioBtnTA1,radioBtnTA2;
    private Button btnNext;
    private int companyid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_company_selection);
        radioBtnTA1=(RadioButton)findViewById(R.id.radioButtonTA1);
        radioBtnTA2=(RadioButton)findViewById(R.id.radioButtonTA2);
        btnNext=(Button)findViewById(R.id.buttonNext);

        getSupportActionBar().setTitle("Company Selection");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radioBtnTA1.setOnClickListener(this);
        radioBtnTA1.setChecked(true);
        radioBtnTA2.setOnClickListener(this);
        btnNext.setOnClickListener(this);
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
        if(view.getId()==R.id.radioButtonTA1)
            companyid=1;
        if(view.getId()==R.id.radioButtonTA2)
            companyid=2;
        if(view.getId()==R.id.buttonNext) {
            if (getIntent().getExtras().getInt("jenisgudang") == 1) {
                Intent intent = new Intent(getApplicationContext(), SearchFormGBB.class);
                intent.putExtra("jenisgudang",getIntent().getExtras().getInt("jenisgudang"));
                intent.putExtra("company",companyid);
            }
            if (getIntent().getExtras().getInt("jenisgudang") == 2 || getIntent().getExtras().getInt("jenisgudang") == 3){
                Intent intent = new Intent(getApplicationContext(), SearchFormGBJ.class);
                intent.putExtra("jenisgudang",getIntent().getExtras().getInt("jenisgudang"));
                intent.putExtra("company",companyid);
            }
        }



    }
}
