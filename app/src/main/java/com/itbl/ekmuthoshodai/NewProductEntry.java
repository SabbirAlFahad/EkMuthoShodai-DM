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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewProductEntry extends Activity {

    TextView  txtPromo, iNameSpin;
    EditText iTAmountV, iRateV, iAmountV, iQuantityV, iDisV, iStockV;

    Button btnSaveEntry, btn_back;

    Spinner iDiscount;

    private ArrayList<String> getDisID = new ArrayList<String>();
    private ArrayList<String> getID = new ArrayList<String>();
    private ArrayList<String> getDisName = new ArrayList<String>();
    private ArrayList<String> getName = new ArrayList<String>();

    String getItemId  =" ";
    String getItemId3 =" ";

    String postiNameSpin1, postcreatedByV, postiDisV, postiStockV ,
           postiTAmountV, postiRateV, postiAmountV, postiQuantityV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_entry);

        txtPromo = findViewById(R.id.txtPromo);
        btnSaveEntry = findViewById(R.id.btnSaveEntry);
        btn_back = findViewById(R.id.btn_back);

        iNameSpin = findViewById(R.id.iNameSpin);
        iDiscount = findViewById(R.id.iDiscount);
        iTAmountV = findViewById(R.id.iTAmountV);
        iRateV = findViewById(R.id.iRateV);
        iAmountV = findViewById(R.id.iAmountV);
        iQuantityV = findViewById(R.id.iQuantityV);
        iDisV = findViewById(R.id.iDisV);
        iStockV = findViewById(R.id.iStockV);

        txtPromo.setTypeface(ResourcesCompat.getFont(this, R.font.amaranth));

        Bundle bundle = getIntent().getExtras();
        postiNameSpin1 = bundle.getString("item_ID_RT","");

        String imNameEt = bundle.getString("item_DESCR","");
        iNameSpin.setText(imNameEt);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });


        DisProduct taskDis = new DisProduct(NewProductEntry.this);
        taskDis.execute();

        btnSaveEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postcreatedByV= iDiscount.getSelectedItem().toString().trim();
                postiDisV=iDisV.getText().toString().trim();
                postiStockV=iStockV.getText().toString().trim();
                postiTAmountV=iTAmountV.getText().toString().trim();
                postiRateV=iRateV.getText().toString().trim();
                postiAmountV=iAmountV.getText().toString().trim();
                postiQuantityV=iQuantityV.getText().toString().trim();

                NewEntry task = new NewEntry(NewProductEntry.this);
                task.execute();

            }
        });
    }

    private class DisProduct extends AsyncTask<Void, Void, String> {

        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd = null;

        public DisProduct(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(NewProductEntry.this, "Data Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";

            try {
                String response = CustomHttpClientGet.execute("http://192.168.22.253:8010/Discount");
                result = response.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONArray jArray = new JSONArray(result.toString());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);

                    getDisID.add(json_data.getString("disc_ID"));
                    getDisName.add(json_data.getString("disc_DESC"));
                }

            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());

            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            ArrayAdapter<String> spinItemPAdapter = new ArrayAdapter<String>(NewProductEntry.this,
                    android.R.layout.simple_spinner_item, getDisName);

            spinItemPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            iDiscount.setAdapter(spinItemPAdapter);

            iDiscount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getItemId3 = getDisID.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }


    private class NewEntry extends AsyncTask<Void,Void,String> {

        private Activity context;
        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public NewEntry(Activity context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(NewProductEntry.this, "Data Processing",
                    "Please wait a bit for success...");
        }

        @Override
        protected String doInBackground(Void... params) {
            List<Pair<String, String>> postps= new ArrayList();

            String result = " ";

            BufferedReader reader = null;
            StringBuilder stringBuilder ;

            try {
                URL url = new URL("http://192.168.22.253:8010/insert_item");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();

                jsonParam.put("ITEM_ID", postiNameSpin1);
                jsonParam.put("DISC_ID", getItemId3);
                jsonParam.put("DISC_AMOUNT", postiDisV);
                jsonParam.put("STOCK_QTY", postiStockV);
                jsonParam.put("AMT", postiTAmountV);
                jsonParam.put("AMT_RATE", postiRateV);
                jsonParam.put("QTY", postiAmountV);
                jsonParam.put("AMT_QTY", postiQuantityV);

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
                        startActivity(new Intent(NewProductEntry.this, NewProductEntry.class));
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
            Intent intent = new Intent(NewProductEntry.this, Home.class);
            startActivity(intent);
        }

}