package com.project.kevin.inventorysupport.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.project.kevin.inventorysupport.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
   // private RadioButton radioBtnGBB,radioBtnWIP,radioBtnGBJ,radioBtnOrder;
    private ImageButton nextButton,btnGBJ,btnGBB,btnOrder;
    //private int state=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Typeface typeface = Typeface.createFromAsset(getAssets(),"Georgia.ttf"); // create a typeface from the raw ttf
        //radioBtnGBB=(RadioButton)findViewById(R.id.radioButtonGBB);
        //radioBtnWIP=(RadioButton)findViewById(R.id.radioButtonWIP);
        //radioBtnGBJ=(RadioButton)findViewById(R.id.radioButtonGBJ);
        //radioBtnOrder=(RadioButton)findViewById(R.id.radioButtonOrder);
        //radioBtnGBB.setTypeface(typeface);
        //radioBtnWIP.setTypeface(typeface);
        //radioBtnGBJ.setTypeface(typeface);

        //nextButton=(ImageButton)findViewById(R.id.nextButton);

        btnGBJ=(ImageButton)findViewById(R.id.btnGBJ);
        btnGBB=(ImageButton)findViewById(R.id.btnGBB);
        btnOrder=(ImageButton)findViewById(R.id.btnOrder);



        //radioBtnGBB.setChecked(true);
        btnGBB.setOnClickListener(this);
        //radioBtnWIP.setOnClickListener(this);
        btnGBJ.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
        //nextButton.setOnClickListener(this);

    }

    public void onClick(View v) {


        if(v.getId()==R.id.btnGBB)
        {
            Intent intent = new Intent(getApplicationContext(), SearchFormGBB.class);
            intent.putExtra("jenisgudang",1);
            startActivity(intent);
        }
        if(v.getId()==R.id.btnGBJ)
        {
            Intent intent = new Intent(getApplicationContext(), SearchFormGBJ.class);
            intent.putExtra("jenisgudang",3);
            startActivity(intent);
        }
        if(v.getId()==R.id.btnOrder)
        {
                Intent intent = new Intent(getApplicationContext(), SearchFormGBJ.class);
        intent.putExtra("jenisgudang",4);
        startActivity(intent);}

    }
}
