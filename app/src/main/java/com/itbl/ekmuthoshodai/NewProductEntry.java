package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.itbl.ekmuthoshodai.entities.NewProductData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewProductEntry extends Activity {

    TextView txtPromo;
    EditText createdByV, iTAmountV, cDateV, iRateV, iAmountV, iQuantityV, iDisV, iStockV;

    Button btnSaveEntry, btn_back;

    Spinner iNameSpin, iNameSpin2;

    private ArrayList<String> getPId = new ArrayList<String>();
    private ArrayList<String> getCId = new ArrayList<String>();
    private ArrayList<String> getPName = new ArrayList<String>();
    private ArrayList<String> getCName = new ArrayList<String>();

    //calender
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "NewProductEntry";

    String getItemId =" ";
    String getItemId2 =" ";

    String postiNameSpin2, postcreatedByV, postcDateV, postiDisV, postiStockV ,
            postiTAmountV, postiRateV, postiAmountV, postiQuantityV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_entry);

        txtPromo = findViewById(R.id.txtPromo);
        btnSaveEntry = findViewById(R.id.btnSaveEntry);
        btn_back = findViewById(R.id.btn_back);
        cDateV = findViewById(R.id.cDateV);
        iNameSpin = findViewById(R.id.iNameSpin);
        iNameSpin2 = findViewById(R.id.iNameSpin2);
        createdByV = findViewById(R.id.createdByV);
        iTAmountV = findViewById(R.id.iTAmountV);
        iRateV = findViewById(R.id.iRateV);
        iAmountV = findViewById(R.id.iAmountV);
        iQuantityV = findViewById(R.id.iQuantityV);
        iDisV = findViewById(R.id.iDisV);
        iStockV = findViewById(R.id.iStockV);

        txtPromo.setTypeface(ResourcesCompat.getFont(this, R.font.amaranth));

        //calender
        cDateV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewProductEntry.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                cDateV.setText(date);
            }
        };

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });

        NewProduct task = new NewProduct(NewProductEntry.this);
        task.execute();

        btnSaveEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToHome();

                postiNameSpin2=iNameSpin2.getSelectedItem().toString().trim();
                postcDateV =cDateV.getText().toString().trim();
                postcreatedByV= createdByV.getText().toString().trim();
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
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";

            try {
                String response = CustomHttpClientGet.execute("http://192.168.22.253:8010/Parent_Item");
                result = response.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONArray jArray = new JSONArray(result.toString());
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

            iNameSpin.setAdapter(spinItemPAdapter);

            iNameSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    getItemId = getPId.get(position);
                    CProduct task = new CProduct(NewProductEntry.this);
                    task.execute();
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
                String response = CustomHttpClientGet.execute("http://192.168.22.253:8010/Child_Item/"+getItemId);
                result = response.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                getCId.clear();
                getCName.clear();
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

            iNameSpin2.setAdapter(spinItemPAdapter);
            iNameSpin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getItemId2 = getCId.get(position);

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
            String result = "0" ;
            BufferedReader reader = null;
            StringBuilder stringBuilder ;

            try {
                URL url = new URL(" ");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();

                jsonParam.put("email", postiNameSpin2 );
                jsonParam.put("password", postcDateV);
                jsonParam.put("fname", postcreatedByV);
                jsonParam.put("lname", postiDisV);
                jsonParam.put("phone", postiStockV);
                jsonParam.put("tdx", postiTAmountV);
                jsonParam.put("image", postiRateV);
                jsonParam.put("type", postiAmountV);
                jsonParam.put("type", postiQuantityV);

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