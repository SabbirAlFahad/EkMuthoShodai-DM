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
        super(context, R.layout.row_product_order_extra, list);
        this.list = list;
        inflator = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProductOrderHolder productOrderHolder;

        if(convertView == null){
            convertView= inflator.inflate(R.layout.row_product_order_extra,null);
            productOrderHolder = new ProductOrderHolder();

            productOrderHolder.odrName = convertView.findViewById(R.id.odrName);
            productOrderHolder.odrQuantity = convertView.findViewById(R.id.odrQuantity);
            productOrderHolder.odrAmount = convertView.findViewById(R.id.odrAmount);
            productOrderHolder.odrDate = convertView.findViewById(R.id.odrDate);
            productOrderHolder.odrStatus = convertView.findViewById(R.id.odrStatus);

            convertView.setTag(productOrderHolder);
            convertView.setTag(R.id.odrName, productOrderHolder.odrName);
            convertView.setTag(R.id.odrQuantity, productOrderHolder.odrQuantity);
            convertView.setTag(R.id.odrAmount, productOrderHolder.odrAmount);
            convertView.setTag(R.id.odrDate, productOrderHolder.odrDate);
            convertView.setTag(R.id.odrStatus, productOrderHolder.odrStatus);
        }
        else{
            productOrderHolder = (ProductOrderHolder) convertView.getTag();
        }

        productOrderHolder.odrName.setText(list.get(position).getOdrName());
        productOrderHolder.odrQuantity.setText(list.get(position).getOdrQuantity());
        productOrderHolder.odrAmount.setText(list.get(position).getOdrAmount());
        productOrderHolder.odrDate.setText(list.get(position).getOdrDate());
        productOrderHolder.odrStatus.setText(list.get(position).getOdrStatus());

        if(list.get(position).getOdrStatus().equals("1")){
            productOrderHolder.odrStatus.setText("Delivered");
        }else {
            productOrderHolder.odrStatus.setText("Pending");
        }

        return convertView;

    }

    static class ProductOrderHolder{

        protected static TextView odrName;
        protected static TextView odrQuantity;
        protected static TextView odrAmount;
        protected static TextView odrDate;
        protected static TextView odrStatus;

    }

}
