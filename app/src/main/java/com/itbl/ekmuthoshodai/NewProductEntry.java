package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView  txtPromo, txt_remember;
    EditText iTAmountV, iRateV, iAmountV, iQuantityV, iDisV, iStockV;
    CheckBox remember;
    Button btnSaveEntry, btn_back;

    Spinner  iNamePSpin,  iNameCSpin, iNameSSpin, iNameSCSpin, iDiscount;

    private ArrayList<String> getPId = new ArrayList<String>();
    private ArrayList<String> getCId = new ArrayList<String>();
    private ArrayList<String> getSId = new ArrayList<String>();
    private ArrayList<String> getSCId = new ArrayList<String>();

    private ArrayList<String> getDisID = new ArrayList<String>();

    private ArrayList<String> getPName = new ArrayList<String>();
    private ArrayList<String> getCName = new ArrayList<String>();
    private ArrayList<String> getSName = new ArrayList<String>();
    private ArrayList<String> getSCName = new ArrayList<String>();

    private ArrayList<String> getDisName = new ArrayList<String>();

    String getItemId  =" ";
    String getItemId2 =" ";
    String getItemId4 =" ";
    String getItemId5 =" ";

    String getItemId3 =" ";
    String passItemId =" ";

    String postiNameSpin1, postcreatedByV, postiDisV, postiStockV ,
           postiTAmountV, postiRateV, postiAmountV, postiQuantityV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_entry);

        txtPromo = findViewById(R.id.txtPromo);
        btnSaveEntry = findViewById(R.id.btnSaveEntry);
        btn_back = findViewById(R.id.btn_back);

        iNamePSpin = findViewById(R.id.iNamePSpin);
        iNameCSpin = findViewById(R.id.iNameCSpin);
        iNameSSpin = findViewById(R.id.iNameSSpin);
        iNameSCSpin = findViewById(R.id.iNameSCSpin);

        iDiscount = findViewById(R.id.iDiscount);
        iTAmountV = findViewById(R.id.iTAmountV);
        iRateV = findViewById(R.id.iRateV);
        iAmountV = findViewById(R.id.iAmountV);
        iQuantityV = findViewById(R.id.iQuantityV);
        iDisV = findViewById(R.id.iDisV);
        iStockV = findViewById(R.id.iStockV);
        remember=  findViewById(R.id.remember);
        txt_remember=  findViewById(R.id.txt_remember);

        txtPromo.setTypeface(ResourcesCompat.getFont(this, R.font.amaranth));

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });

        NewProduct task = new NewProduct(NewProductEntry.this);
        task.execute();

        DisProduct taskDis = new DisProduct(NewProductEntry.this);
        taskDis.execute();

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(compoundButton.isChecked()){
                    Toast.makeText(NewProductEntry.this,"Confirmed",Toast.LENGTH_SHORT).show();
                }else if(!compoundButton.isChecked()){
                    Toast.makeText(NewProductEntry.this,"Please select check mark",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnSaveEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postiNameSpin1= iNameCSpin.getSelectedItem().toString().trim();
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

    private class NewProduct extends AsyncTask<Void, Void, String> {

        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd = null;

        public NewProduct(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(NewProductEntry.this, "Data Processing",
                    "Please wait a bit...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";

            try {
                String response = CustomHttpClientGet.execute("http://192.168.22.253:8010/Item/");
                result = response.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONArray jArray = new JSONArray(result.toString());
                getPId.add("");
                getPName.add(" ---Select--- ");

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);

                    getPId.add(json_data.getString("item_ID"));
                    getPName.add(json_data.getString("item_DESCR"));

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
                    android.R.layout.simple_spinner_item, getPName);

            spinItemPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            iNamePSpin.setAdapter(spinItemPAdapter);

            iNamePSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getItemId = getPId.get(position);

                    if (!getItemId.equals("")) {
                        CProduct task = new CProduct(NewProductEntry.this);
                        task.execute();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private class CProduct extends AsyncTask<Void, Void, String> {

        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd = null;


        public CProduct(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(NewProductEntry.this, "Data Processing",
                    "Please wait a bit...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";

            try {
                String response = CustomHttpClientGet.execute("http://192.168.22.253:8010/Item_lvl2/"+getItemId);
                result = response.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                getCId.add("");
                getCName.add("  ---Select---  ");

                JSONArray jArray = new JSONArray(result.toString());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);

                    getCId.add(json_data.getString("item_ID"));
                    getCName.add(json_data.getString("item_DESCR"));

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
                    android.R.layout.simple_spinner_item, getCName);

            spinItemPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            iNameCSpin.setAdapter(spinItemPAdapter);

            iNameCSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getItemId2 = getCId.get(position);

                    if (!getItemId2.equals("")) {
                        SProduct task = new SProduct(NewProductEntry.this);
                        task.execute();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private class SProduct extends AsyncTask<Void, Void, String> {

        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd = null;


        public SProduct(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(NewProductEntry.this, "Data Processing",
                    "Please wait a bit...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";

            try {
                String response = CustomHttpClientGet.execute("http://192.168.22.253:8010/Item_lvl3/"+getItemId2);
                result = response.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                getSId.add(" ");
                getSName.add("  ---Select---  ");

                JSONArray jArray = new JSONArray(result.toString());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);

                    getSId.add(json_data.getString("item_ID"));
                    getSName.add(json_data.getString("item_DESCR"));

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
                    android.R.layout.simple_spinner_item, getSName);

            spinItemPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            iNameSSpin.setAdapter(spinItemPAdapter);

            iNameSSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getItemId4 = getSId.get(position);

                    if (!getItemId4.equals("")) {
                        SCProduct task = new SCProduct(NewProductEntry.this);
                        task.execute();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }


    private class SCProduct extends AsyncTask<Void, Void, String> {

        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd = null;

        public SCProduct(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(NewProductEntry.this, "Data Processing",
                    "Please wait a bit...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";

            try {
                String response = CustomHttpClientGet.execute("http://192.168.22.253:8010/Item_lvl4/"+getItemId4);
                result = response.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                getSCId.add("");
                getSCName.add(" ");

                JSONArray jArray = new JSONArray(result.toString());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);

                    getSCId.add(json_data.getString("item_ID"));
                    getSCName.add(json_data.getString("item_DESCR"));

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
                    android.R.layout.simple_spinner_item, getSCName);

            spinItemPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            iNameSCSpin.setAdapter(spinItemPAdapter);

            iNameSCSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getItemId5 = getSCId.get(position);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
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

                //jsonParam.put("ITEM_ID", passItemId);
                jsonParam.put("DISC_ID", getItemId3);
                jsonParam.put("DISC_AMOUNT", postiDisV);
                jsonParam.put("STOCK_QTY", postiStockV);
                jsonParam.put("AMT", postiTAmountV);
                jsonParam.put("AMT_RATE", postiRateV);
                jsonParam.put("QTY", postiAmountV);
                jsonParam.put("AMT_QTY", postiQuantityV);

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
                        startActivity(new Intent(NewProductEntry.this, Home.class));
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