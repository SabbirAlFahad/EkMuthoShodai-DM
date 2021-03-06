package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.itbl.ekmuthoshodai.entities.MyProduct;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductUpdate extends AppCompatActivity {

    TextView imNameE;
    EditText imStockE, imRateE, imQuantityE, imAmountE, imDiscountE;
    Button btnIMUpdate, btn_back;

    String pstitmId ,pstimStockE, pstimRateE, pstimQuantityE, pstimAmountE, pstimDiscountE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_update);

        imNameE = findViewById(R.id.imNameE);
        imStockE = findViewById(R.id.imStockE);
        imRateE = findViewById(R.id.imRateE);
        imQuantityE = findViewById(R.id.imQuantityU);
        imAmountE = findViewById(R.id.imAmountE);
        imDiscountE = findViewById(R.id.imDiscountE);
        btnIMUpdate = findViewById(R.id.btnIMUpdate);
        btn_back = findViewById(R.id.btn_back);

        Bundle bundle = getIntent().getExtras();
        pstitmId = bundle.getString("item_ID_RT","");

        String imNameEt = bundle.getString("item_DESCR","");
        imNameE.setText(imNameEt);
        String imStockEt = bundle.getString("stock_QTY","");
        imStockE.setText(imStockEt);
        String imRateEt = bundle.getString("amt","");
        imRateE.setText(imRateEt);
        String imQuantityEt = bundle.getString("amt_QTY","");
        imQuantityE.setText(imQuantityEt);
        String imAmountEt = bundle.getString("amt_RATE","");
        imAmountE.setText(imAmountEt);
        String imDiscountEt = bundle.getString("disc_AMOUNT","");
        imDiscountE.setText(imDiscountEt);

        btnIMUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                pstimStockE= imStockE.getText().toString().trim();
                pstimRateE=imRateE.getText().toString().trim();
                pstimQuantityE=imQuantityE.getText().toString().trim();
                pstimAmountE=imAmountE.getText().toString().trim();
                pstimDiscountE=imDiscountE.getText().toString().trim();

                ProUpdate task = new ProUpdate(ProductUpdate.this);
                task.execute();

            }

        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack();
            }
        });
    }

    private void goToBack() {
        Intent intent = new Intent(ProductUpdate.this, MyProductList.class);
        startActivity(intent);
        overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim);
    }

    private class ProUpdate extends AsyncTask<Void,Void,String> {

        private Activity context;
        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public ProUpdate(Activity context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(ProductUpdate.this, "Data Processing",
                    "Please wait a bit for success...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "" ;
            BufferedReader reader = null;
            StringBuilder stringBuilder ;

            try {
                URL url = new URL("http://192.168.22.253:8010/update_item");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();

                jsonParam.put("item_ID_RT", pstitmId);
                jsonParam.put("stock_QTY", pstimStockE);
                jsonParam.put("amt", pstimRateE);
                jsonParam.put("amt_QTY", pstimQuantityE);
                jsonParam.put("amt_RATE", pstimAmountE);
                jsonParam.put("disc_AMOUNT", pstimDiscountE);

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

                dialog1("Failed!! Please try again");
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
                        startActivity(new Intent(ProductUpdate.this, Home.class));
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

    private void goToMyList() {
        Intent intent = new Intent(ProductUpdate.this, MyProductList.class);
        startActivity(intent);
    }

}
