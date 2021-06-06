package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

public class MyProduct extends Activity {

    Button btn_back;
    TextView txtPromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_product);

        btn_back = findViewById(R.id.btn_back);
        txtPromo = findViewById(R.id.txtPromo);

        txtPromo.setTypeface(ResourcesCompat.getFont(this, R.font.amaranth));

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });

    }

    private void goToHome() {
        Intent intent = new Intent(MyProduct.this,Home.class);
        startActivity(intent);
    }

}
