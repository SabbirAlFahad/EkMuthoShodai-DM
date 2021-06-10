package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itbl.ekmuthoshodai.entities.ProductOrder;

import java.util.List;

public class ProductOrderAdapter extends ArrayAdapter<ProductOrder> {
    private List<ProductOrder> list;

    private LayoutInflater inflator;

    public ProductOrderAdapter(Activity context, int row_layout, List<ProductOrder> list) {
        super(context, R.layout.row_product_order, list);
        this.list = list;
        inflator = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProductOrderAdapter.ProductOrderHolder productOrderHolder;

        if(convertView == null){
            convertView= inflator.inflate(R.layout.row_product_order,null);
            productOrderHolder = new ProductOrderAdapter.ProductOrderHolder();

            productOrderHolder.imCID = convertView.findViewById(R.id.imCID);
            productOrderHolder.imClient = convertView.findViewById(R.id.imClient);

            convertView.setTag(productOrderHolder);
            convertView.setTag(R.id.imCID, productOrderHolder.imCID);
            convertView.setTag(R.id.imClient, productOrderHolder.imClient);
        }
        else{
            productOrderHolder = (ProductOrderAdapter.ProductOrderHolder) convertView.getTag();
        }

        productOrderHolder.imCID.setText(String.valueOf(list.get(position).getImCID()));
        productOrderHolder.imClient.setText(list.get(position).getImClient());

        return convertView;

    }

    static class ProductOrderHolder{
        protected static TextView imCID;
        protected static TextView imClient;

    }
}
