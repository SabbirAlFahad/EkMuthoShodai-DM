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
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import org.json.JSONArray;
import org.json.JSONObject;
import com.itbl.ekmuthoshodai.entities.ProductOrderExt;

import java.util.ArrayList;

public class ProductOrderExtra extends Activity {

    Button btn_back;
    TextView txtPromo;

    ProductOrderExtraAdapter productOrderExtraAdapter;
    ListView listView;

    ArrayList<ProductOrderExt> productOrderExts=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_order_list_ext);

        listView = findViewById(R.id.list_mProduct_E);

        btn_back = findViewById(R.id.btn_back);
        txtPromo = findViewById(R.id.txtPromo);

        txtPromo.setTypeface(ResourcesCompat.getFont(this, R.font.amaranth));

       Display task = new Display(ProductOrderExtra.this);
       task.execute();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                goToHome();
            }
        });

    }

    public class Display extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public Display(Activity context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            pd = ProgressDialog.show(ProductOrderExtra.this, "Data Processing",
                    "Please wait a bit. . .");
        }

        @Override
        protected String doInBackground(Void... params){
            String result = "";

            try {
                int count = 0;
                String odrNo, odrQuantity, odrRate, odrAmount, odrStatus, oderDate;

                try {
                    String response = CustomHttpClientGet.execute(" ");

                    result = response.toString();

                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());

                }
                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()) {

                    JSONObject JO = jsonArray.getJSONObject(count);

                    odrNo = JO.getString(" ");
                    odrQuantity = JO.getString(" ");
                    odrRate = JO.getString(" ");
                    odrAmount = JO.getString(" ");
                    odrStatus = JO.getString(" ");
                    oderDate = JO.getString(" ");

                    ProductOrderExt productOrderExt = new ProductOrderExt(odrNo ,odrQuantity, odrRate, odrAmount,
                            odrStatus, oderDate);

                    productOrderExts.add(productOrderExt);
                    count++;
                }
            }

            catch (Exception e) {
                Log.e("log_tag", "Error in http connection !" + e.toString());
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            productOrderExtraAdapter = new ProductOrderExtraAdapter(ProductOrderExtra.this, R.layout.row_product_order_extra,productOrderExts);
            listView.setAdapter(productOrderExtraAdapter);

        }
    }

    public void dialog(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void goToHome() {
        Intent intent = new Intent(ProductOrderExtra.this, ProductOrderList.class);
        startActivity(intent);
    }

}
