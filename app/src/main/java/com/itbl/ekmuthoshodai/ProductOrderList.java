package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.itbl.ekmuthoshodai.entities.ProductOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductOrderList extends Activity {

    Button btn_back;
    TextView txtPromo;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ProductOrderAdapter productOrderAdapter;
    ListView listView;
    ProductOrder productOrder;

    ArrayList<ProductOrder> productOrders=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_order);

        btn_back = findViewById(R.id.btn_back);
        txtPromo = findViewById(R.id.txtPromo);
        listView = findViewById(R.id.list_mPOrder);

        txtPromo.setTypeface(ResourcesCompat.getFont(this, R.font.amaranth));

        Display task = new Display(ProductOrderList.this);
        task.execute();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            pd = ProgressDialog.show(ProductOrderList.this, " Data Processing",
                    "Please wait a bit...");
        }

        @Override
        protected String doInBackground(Void... params){
            String result = "";

            try {
                int count = 0;
                Integer imCID;
                String imClient;

                try {
                    String response = CustomHttpClientGet.execute(" ");
                    result = response.toString();

                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());

                }
                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);

                    imCID = Integer.valueOf(JO.getString(" "));
                    imClient = JO.getString(" ");

                    ProductOrder productOrder = new ProductOrder(imCID, imClient);

                    productOrders.add(productOrder);
                    count++;
                }
            }

            catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            productOrderAdapter = new ProductOrderAdapter(ProductOrderList.this,
                    R.layout.row_product_order,productOrders);
            listView.setAdapter(productOrderAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ProductOrderList.this, ProductOrderExtra.class);

                    startActivity(intent);

                }
            });

        }
    }

    private void goToHome() {
        Intent intent = new Intent(ProductOrderList.this,Home.class);
        startActivity(intent);
    }

}
