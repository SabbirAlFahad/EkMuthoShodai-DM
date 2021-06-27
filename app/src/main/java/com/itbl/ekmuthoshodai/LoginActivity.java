package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends Activity {

    TextView txt_remember, txt_login, itbl;
    EditText LogUser;
    TextInputEditText LogPassword;
    Button btnLogin;
    CheckBox remember;
    Animation btnAnim;
    public static String newUser, newPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txt_login =  findViewById(R.id.txt_login);
        itbl =  findViewById(R.id.itbl);
        LogUser= findViewById(R.id.logId);
        LogPassword=  findViewById(R.id.logpass);
        remember=  findViewById(R.id.remember);
        txt_remember=  findViewById(R.id.txt_remember);
        btnLogin=  findViewById(R.id.btnlogin);

        txt_login.setTypeface(ResourcesCompat.getFont(this, R.font.bree_serif));
        itbl.setTypeface(ResourcesCompat.getFont(this, R.font.amaranth_bold));
        btnLogin.setTypeface(ResourcesCompat.getFont(this, R.font.bree_serif));

        btnLogin.setEnabled(false);

        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_bounce);

        SharedPreferences loginPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = loginPreferences.getString("remember","");

        if (checkbox.equals("true")){
            Intent intent = new Intent(LoginActivity.this,Home.class);
            startActivity(intent);

        }else if (checkbox.equals("false")){
            Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_LONG).show();
        }

        txt_remember.setTypeface(ResourcesCompat.getFont(this, R.font.amaranth_bold));

        LogUser.addTextChangedListener(loginTextWatcher);
        LogPassword.addTextChangedListener(loginTextWatcher);

        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (LogUser.getText().toString().isEmpty() || LogPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill The Empty Field", Toast.LENGTH_LONG).show();

                } else {
                 Login task = new Login(LoginActivity.this);
                    task.execute();
                }

            }

        });

        //Remember me
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(compoundButton.isChecked()){
                    //SharedPreferences loginPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    //SharedPreferences.Editor preferencesEditor = loginPreferences.edit();
                    //preferencesEditor.putString("remember", "true");
                    //preferencesEditor.apply();
                    Toast.makeText(LoginActivity.this,"Checked",Toast.LENGTH_SHORT).show();

                }else if(!compoundButton.isChecked()){
                    //SharedPreferences loginPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    //SharedPreferences.Editor preferencesEditor = loginPreferences.edit();
                    //preferencesEditor.putString("remember", "false");
                    //preferencesEditor.apply();
                    Toast.makeText(LoginActivity.this,"Unchecked",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void gotoHome() {
            Intent intent = new Intent(LoginActivity.this, Home.class);
            startActivity(intent);
        overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim);
    }

    private class Login extends AsyncTask<Void, Void, String> {

        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;


        public Login(Activity context) {
            this.context = context;

        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(LoginActivity.this, "Login Processing",
                    "Please wait...");

        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            BufferedReader reader = null;
            StringBuilder stringBuilder;

            try {
                URL url = new URL("http://192.168.22.241:8010/Userlogin");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("UserId", LogUser.getText().toString().trim());
                jsonParam.put("Password", LogPassword.getText().toString().trim());

                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
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
                result="["+result+"]";
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {


                JSONArray jArray = new JSONArray(result.toString());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(0);

                    newUser = json_data.getString("userId");
                    newPass = json_data.getString("password");

                }
            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());

            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            if(LogUser.getText().toString().trim().equals(newUser)){
                if(LogPassword.getText().toString().trim().equals(newPass)){
                    gotoHome();
                }else {
                    dialog("Wrong Password");
                }
            }else {
                dialog("Wrong User Id");
            }
        }

    }

    public void dialog(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

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

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = LogUser.getText().toString().trim();
            String passwordInput = LogPassword.getText().toString().trim();

            btnLogin.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

}
