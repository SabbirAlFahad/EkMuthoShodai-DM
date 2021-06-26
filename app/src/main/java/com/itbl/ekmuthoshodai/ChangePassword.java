package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangePassword extends AppCompatActivity {

    TextView  txtPromo;
    TextInputEditText pOldPassV, pNewPassV;
    Button btnCPass, btn_back;

    String userId, userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        txtPromo = findViewById(R.id.txtPromo);
        pOldPassV = findViewById(R.id.pOldPassV);
        pNewPassV = findViewById(R.id.pNewPassV);

        btnCPass = findViewById(R.id.btnCPass);
        btn_back = findViewById(R.id.btn_back);

        txtPromo.setTypeface(ResourcesCompat.getFont(this, R.font.amaranth));

        btnCPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userPass=pNewPassV.getText().toString().trim();

                CPass task = new CPass(ChangePassword.this);
                task.execute();

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });

    }

    private class CPass extends AsyncTask<Void,Void,String> {

        private Activity context;
        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public CPass(Activity context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(ChangePassword.this, "Data Processing",
                    "Please wait a bit for success...");
        }

        @Override
        protected String doInBackground(Void... params) {
            List<Pair<String, String>> postps= new ArrayList();

            String result = "";

            BufferedReader reader = null;
            StringBuilder stringBuilder ;

            try {
                URL url = new URL("http://192.168.22.253:8010/PassUpdate");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();

                jsonParam.put("UserId", LoginActivity.newUser);
                jsonParam.put("Password", userPass);

                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());

                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line + "\n");
                }
                result=stringBuilder.toString();
                result=result.replaceAll("\n","");
                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            int r= Integer.parseInt(result);
            if(r>0){
                dialog("Message","Entered successful!!!");

            }else{

                dialog1("Failed! try again");
            }
        }
    }


    public void dialog(String message,String title){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivity(new Intent(ChangePassword.this, LoginActivity.class));
                        overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void dialog1(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void goToHome() {
        Intent intent = new Intent(ChangePassword.this,Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim);
    }

}
