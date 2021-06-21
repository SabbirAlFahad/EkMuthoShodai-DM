package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import android.widget.AdapterView;
import android.widget.ListView;

import com.itbl.ekmuthoshodai.entities.MyProduct;

import org.json.JSONArray;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyProductList extends Activity {

    Button btn_back;
    TextView txtPromo;

    MyProductAdapter myProductAdapter;
    ListView listView;

    ArrayList<MyProduct> myProducts=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_product);

        listView = findViewById(R.id.list_mProduct);

        btn_back = findViewById(R.id.btn_back);
        txtPromo = findViewById(R.id.txtPromo);

        txtPromo.setTypeface(ResourcesCompat.getFont(this, R.font.amaranth));

        Display task = new Display(MyProductList.this);
        task.execute();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });
 }

    public class Display extends AsyncTask<Void, Void, String>{
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public Display(Activity context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            pd = ProgressDialog.show(MyProductList.this, "Data Processing",
                    "Please wait a bit. . .");
        }

        @Override
        protected String doInBackground(Void... params){
            String result = "";

            try {
                int count = 0;
                String itmId, imName, imStock, imRate, imQuantity, imAmount, imDiscount;

                try {
                    String response = CustomHttpClientGet.execute("http://192.168.22.253:8010/item_show");

                    result = response.toString();

                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());

                }

                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);

                    itmId = JO.getString("item_ID_RT");
                    imName = JO.getString("item_DESCR");
                    imStock = JO.getString("stock_QTY");
                    imRate = JO.getString("amt");
                    imQuantity = JO.getString("amt_QTY");
                    imAmount = JO.getString("amt_RATE");
                    imDiscount = JO.getString("disc_AMOUNT");

                    MyProduct myProduct = new MyProduct(itmId, imName, imStock, imRate, imQuantity,
                            imAmount, imDiscount);

                    myProducts.add(myProduct);
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
            myProductAdapter = new MyProductAdapter(MyProductList.this, R.layout.row_myproduct,myProducts);

            listView.setAdapter(myProductAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MyProductList.this, ProductUpdate.class);

                    intent.putExtra("item_ID_RT", myProducts.get(position).getItmId());
                    intent.putExtra("item_DESCR", myProducts.get(position).getImName());
                    intent.putExtra("item_DESCR", myProducts.get(position).getImName());
                    intent.putExtra("stock_QTY", myProducts.get(position).getImStock());
                    intent.putExtra("amt", myProducts.get(position).getImRate());
                    intent.putExtra("amt_QTY", myProducts.get(position).getImQuantity());
                    intent.putExtra("amt_RATE", myProducts.get(position).getImAmount());
                    intent.putExtra("disc_AMOUNT", myProducts.get(position).getImDiscount());

                    startActivity(intent);

                }
            });

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
        Intent intent = new Intent(MyProductList.this,Home.class);
        startActivity(intent);
    }

}
