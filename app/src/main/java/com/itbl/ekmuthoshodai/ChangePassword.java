package com.itbl.ekmuthoshodai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.textfield.TextInputEditText;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChangePassword extends AppCompatActivity {

    CircleImageView profileImg;
    TextView pNameV, pShopNameV, txtPromo;
    TextInputEditText pOldPassV, pNewPassV;
    Button btnCPass, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        txtPromo = findViewById(R.id.txtPromo);
        pNameV = findViewById(R.id.pNameV);
        pShopNameV = findViewById(R.id.pShopNameV);
        pOldPassV = findViewById(R.id.pOldPassV);
        pNewPassV = findViewById(R.id.pNewPassV);

        profileImg = findViewById(R.id.profileImg);
        btnCPass = findViewById(R.id.btnCPass);
        btn_back = findViewById(R.id.btn_back);

        txtPromo.setTypeface(ResourcesCompat.getFont(this, R.font.amaranth));

        btnCPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });

    }

    private void goToHome() {
        Intent intent = new Intent(ChangePassword.this,Home.class);
        startActivity(intent);
    }

}
