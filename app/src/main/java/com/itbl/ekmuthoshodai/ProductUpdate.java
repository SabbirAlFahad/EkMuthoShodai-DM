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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductUpdate extends AppCompatActivity {

    TextView imNameE;
    EditText imStockE, imRateE, imQuantityE, imAmountE, imDiscountE;
    Button btnIMUpdate;

    String pstimNameE, pstimStockE, pstimRateE, pstimQuantityE, pstimAmountE, pstimDiscountE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_update);

        imNameE = findViewById(R.id.imNameE);
        imStockE = findViewById(R.id.imStockE);
        imRateE = findViewById(R.id.imRateE);
        imQuantityE = findViewById(R.id.imQuantityE);
        imAmountE = findViewById(R.id.imAmountE);
        imDiscountE = findViewById(R.id.imDiscountE);
        btnIMUpdate = findViewById(R.id.btnIMUpdate);

        Bundle bundle = getIntent().getExtras();
        String imNameEt = bundle.getString(" ","");
        imNameE.setText(imNameEt);
        String imStockEt = bundle.getString(" ","");
        imStockE.setText(imStockEt);
        String imRateEt = bundle.getString(" ","");
        imRateE.setText(imRateEt);
        String imQuantityEt = bundle.getString(" ","");
        imQuantityE.setText(imQuantityEt);
        String imAmountEt = bundle.getString(" ","");
        imAmountE.setText(imAmountEt);
        String imDiscountEt = bundle.getString(" ","");
        imDiscountE.setText(imDiscountEt);

        btnIMUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                pstimNameE =imNameE.getText().toString().trim();
                pstimStockE= imStockE.getText().toString().trim();
                pstimRateE=imRateE.getText().toString().trim();
                pstimQuantityE=imQuantityE.getText().toString().trim();
                pstimAmountE=imAmountE.getText().toString().trim();
                pstimDiscountE=imDiscountE.getText().toString().trim();

                ProUpdate task = new ProUpdate(ProductUpdate.this);
                task.execute();

                goToMyList();

            }

        });
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
            String result = "0" ;
            BufferedReader reader = null;
            StringBuilder stringBuilder ;

            try {
                URL url = new URL(" "); //JSON Link
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();

                jsonParam.put(" ", pstimNameE);
                jsonParam.put(" ", pstimStockE);
                jsonParam.put(" ", pstimRateE);
                jsonParam.put(" ", pstimQuantityE);
                jsonParam.put(" ", pstimAmountE);
                jsonParam.put(" ", pstimDiscountE);

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
