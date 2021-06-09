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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.itbl.ekmuthoshodai.entities.ItemChild;
import com.itbl.ekmuthoshodai.entities.ItemParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NewProductEntry extends Activity {

    TextView txtPromo,
            createdByV, iTAmountV, iRateV, iAmountV, iQuantityV, cDateV, iDisV, iStockV;
    Button btnSaveEntry, btn_back;

    Spinner iNameSpin, iNameSpin2;

    private ArrayList<String> getPId = new ArrayList<String>();
    private ArrayList<String> getCId = new ArrayList<String>();
    private ArrayList<String> getPName = new ArrayList<String>();
    private ArrayList<String> getCName = new ArrayList<String>();

    //public String postiNameSpin, postiNameSpin2, postcreatedByV, postiTAmountV,
    // postiRateV, postiAmountV, postiQuantityV, postcDateV, postiDisV, postiStockV;

    //calender
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "NewProductEntry";
    String getItemId="";
    String getItemId2="";

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

        btnSaveEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToHome();

               /* postiNameSpin=iNameSpin.getSelectedItem().toString().trim();
                postiNameSpin2=iNameSpin2.getSelectedItem().toString().trim();

                postcreatedByV=createdByV.getText().toString().trim();
                postiTAmountV=iTAmountV.getText().toString().trim();
                postiRateV=iRateV.getText().toString().trim();
                postiAmountV=iAmountV.getText().toString().trim();
                postiQuantityV=iQuantityV.getText().toString().trim();
                postcDateV=cDateV.getText().toString().trim();
                postiDisV=iDisV.getText().toString().trim();
                postiStockV=iStockV.getText().toString().trim();

                Call<ResponseBody> call = NewEntryClient.getInstance()
                        .getNewEntryApi()
                        .newEntry(postiNameSpin, postcreatedByV, postiTAmountV, postiRateV,
                                postiAmountV, postiQuantityV, postcDateV, postiDisV, postiStockV);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try { String s = response.body().string();
                         Toast.makeText( NewProductEntry.this, s, Toast.LENGTH_LONG)
                                 .show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText( NewProductEntry.this, t.getMessage(), Toast.LENGTH_LONG)
                                . show();
                    }
                }); */
            }
        });

        NewProduct task = new NewProduct(NewProductEntry.this);
        task.execute();

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
            pd = ProgressDialog.show(NewProductEntry.this, "Login Processing",
                    "Please wait...");
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



        private void goToHome() {
            Intent intent = new Intent(NewProductEntry.this, Home.class);
            startActivity(intent);
        }

}