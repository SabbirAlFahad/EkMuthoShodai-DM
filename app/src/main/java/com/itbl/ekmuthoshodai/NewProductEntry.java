package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.itbl.ekmuthoshodai.Interface.APIPassId;
import com.itbl.ekmuthoshodai.entities.ItemChild;
import com.itbl.ekmuthoshodai.entities.ItemParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class NewProductEntry extends Activity {

    TextView txtPromo,
            createdByV, iTAmountV, iRateV, iAmountV, iQuantityV, cDateV, iDisV, iStockV ;
    Button btnSaveEntry, btn_back;

    Spinner iNameSpin, iNameSpin2;

    private ArrayList<String> getItemName =new ArrayList<String>();
    private ArrayList<String> getItemCName =new ArrayList<String>();

    public String postiNameSpin, postiNameSpin2, postcreatedByV, postiTAmountV,
            postiRateV, postiAmountV, postiQuantityV, postcDateV, postiDisV, postiStockV;

    //calender
      private DatePickerDialog.OnDateSetListener mDateSetListener;
      private static final String TAG = "NewProductEntry";


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
                        year,month,day);
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

        getItemParent();

        btnSaveEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();

                //postiNameSpin=iNameSpin.getText().toString().trim();
                //postiNameSpin2=iNameSpin2.getText().toString().trim();

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
                                 . show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText( NewProductEntry.this, t.getMessage(), Toast.LENGTH_LONG)
                                . show();
                    }
                });
            }
        });

    }

    private void goToHome() {
        Intent intent = new Intent(NewProductEntry.this,Home.class);
        startActivity(intent);
    }

    private void getItemParent() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIPassId.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create()).build();

        APIPassId apiPassId = retrofit.create(APIPassId.class);
        Call<String> call = apiPassId.getItemParent();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.i( "Response",response.body().toString());
                if(response.isSuccessful()){

                    if(response.body()!=null){
                        Log.i( "Success",response.body().toString());

                        try {
                            String getResponse=response.body().toString();
                            List<ItemParent> getItemParentData=new ArrayList<ItemParent>();

                            JSONArray jsonArray = new JSONArray(getResponse);

                            getItemParentData.add(new ItemParent(-1,"---SELECT ITEM NAME---"));

                            for(int i=0; 1<jsonArray.length();i++) {

                                ItemParent itemParent= new ItemParent();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                itemParent.setItemId(jsonObject.getInt("itm_ID"));
                                itemParent.setItemName(jsonObject.getString("itm_Name"));
                                getItemParentData.add(itemParent);

                            }

                            for (int i=0; 1<getItemParentData.size();i++) {
                                getItemName.add(getItemParentData.get(i).getItemName().toString());
                            }

                            ArrayAdapter<String> spinItemPAdapter =
                                    new ArrayAdapter<String>(NewProductEntry.this,
                                            android.R.layout.simple_spinner_item, getItemName);

                            spinItemPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            iNameSpin.setAdapter(spinItemPAdapter);
                            iNameSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    int getItemParentID = getItemParentData.get(position).getItemId();
                                    get_itemChild(getItemParentID);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            } catch (JSONException e) {
                            e.printStackTrace();

                                 }

                             }
                    }
                }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void get_itemChild(int getItemId) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIPassId.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create()).build();

        APIPassId apiPassId = retrofit.create(APIPassId.class);
        Call<String> call = apiPassId.getItemChild(getItemId);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i( "Response",response.body().toString());
                    if(response.isSuccessful()) {

                      if (response.body() != null) {
                          Log.i("Success", response.body().toString());

                        try {
                            String getResponse= response.body().toString();
                            List<ItemChild> getItemChildData = new ArrayList<ItemChild>();
                            JSONArray jsonArray = new JSONArray(getResponse);

                            getItemChildData.add(new ItemChild( -1, -1, "---SELECT CHILD ITEM---"));

                            for(int i=0; 1<jsonArray.length();i++) {
                                ItemChild itemChild= new ItemChild();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                itemChild.setItemCId(jsonObject.getInt("itm_CID"));
                                itemChild.setItemCName(jsonObject.getString("itm_CName"));
                                getItemChildData.add(itemChild);
                            }

                            for (int i=0; 1<getItemChildData.size();i++) {
                                getItemCName.add(getItemChildData.get(i).getItemCName().toString());
                            }

                            ArrayAdapter<String> spinItemPAdapter =
                                    new ArrayAdapter<String>(NewProductEntry.this,
                                            android.R.layout.simple_spinner_item, getItemName);

                            spinItemPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            iNameSpin2.setAdapter(spinItemPAdapter);

                            iNameSpin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    int getItemChildID = getItemChildData.get(position).getItemCId();
                                    get_itemChild(getItemChildID);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }catch (JSONException e){
                            e.printStackTrace();

                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

}
