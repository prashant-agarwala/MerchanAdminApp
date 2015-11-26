package com.example.viewpagertab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.viewpagertab.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = getIntent();
        // getting attached intent data
        String product = i.getStringExtra("product");
        TextView txtProduct = (TextView) findViewById(R.id.product_label);
        // displaying selected product name
        txtProduct.setText(product);
    }
}
