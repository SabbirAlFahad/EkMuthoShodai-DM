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
    ProductOrderAdapter productOrderAdapter;
    ListView listView;

    ArrayList<ProductOrder> productOrders=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_order);

        btn_back = findViewById(R.id.btn_back);
        listView = findViewById(R.id.list_mPOrder);


        Display task = new Display(ProductOrderList.this);
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
            pd = ProgressDialog.show(ProductOrderList.this, "Data Processing",
                    "Please wait a bit. . .");
        }

        @Override
        protected String doInBackground(Void... params){
            String result = "";

            try {
                int count = 0;
                String odrId, odrName, odrQuantity, odrAmount, odrDate, dlvryId, odrStatus;

                try {
                    String response = CustomHttpClientGet.execute("http://192.168.22.253:8010/Order_show");

                    result = response.toString();

                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }

                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);

                    odrId = JO.getString("order_DTL_ID");
                    odrName = JO.getString("item_DESCR");
                    odrQuantity = JO.getString("qnty");
                    odrAmount = JO.getString("rate");
                    odrDate = JO.getString("date");
                    dlvryId = JO.getString("dbid");
                    odrStatus = JO.getString("status");

                    ProductOrder productOrder = new ProductOrder(odrId, odrName, odrQuantity, odrAmount, odrDate, dlvryId, odrStatus);

                    productOrders.add(productOrder);
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
            productOrderAdapter = new ProductOrderAdapter(ProductOrderList.this, R.layout.row_product_order_extra,productOrders);

            listView.setAdapter(productOrderAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ProductOrderList.this, OrderStatusUpdate.class);

                    intent.putExtra("order_DTL_ID", productOrders.get(position).getOdrId());
                    intent.putExtra("item_DESCR", productOrders.get(position).getOdrName());
                    intent.putExtra("qnty", productOrders.get(position).getOdrQuantity());
                    intent.putExtra("rate", productOrders.get(position).getOdrAmount());
                    intent.putExtra("date", productOrders.get(position).getOdrDate());
                    intent.putExtra("dbid", productOrders.get(position).getDlvryId());
                    intent.putExtra("status", productOrders.get(position).getOdrStatus());

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
