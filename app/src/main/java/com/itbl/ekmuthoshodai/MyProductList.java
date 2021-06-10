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

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    MyProductAdapter myProductAdapter;
    ListView listView;
    MyProduct myProduct;
    Button btnIMSave;

    ArrayList<MyProduct> myProducts=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_product);

        listView = findViewById(R.id.list_mProduct);

        btn_back = findViewById(R.id.btn_back);
        txtPromo = findViewById(R.id.txtPromo);
        btnIMSave = findViewById(R.id.btnIMSave);
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
                String imName, imStock, imRate, imQuantity, imAmount, imDiscount;

                try {
                    String response = CustomHttpClientGet.execute(" ");

                    result = response.toString();
                    //result=result.replaceAll("[^a-zA-Z0-9]+","");

                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());

                }
                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()) {

                    JSONObject JO = jsonArray.getJSONObject(count);

                    imName = JO.getString("bookName");
                    imStock = JO.getString("bookName");
                    imRate = JO.getString("bookWriter");
                    imQuantity = JO.getString("bookCountry");
                    imAmount = JO.getString("bookLanguage");
                    imDiscount = JO.getString("status");

                    MyProduct myProduct = new MyProduct(imName, imStock, imRate, imQuantity,
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
                    intent.putExtra(" ", myProducts.get(position).getImName());
                    intent.putExtra(" ", myProducts.get(position).getImStock());
                    intent.putExtra(" ", myProducts.get(position).getImRate());
                    intent.putExtra(" ", myProducts.get(position).getImQuantity());
                    intent.putExtra(" ", myProducts.get(position).getImAmount());
                    intent.putExtra(" ", myProducts.get(position).getImDiscount());
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
