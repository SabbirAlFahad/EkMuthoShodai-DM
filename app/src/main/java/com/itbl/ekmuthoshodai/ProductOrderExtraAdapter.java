package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itbl.ekmuthoshodai.entities.ProductOrderExt;

import java.util.List;

public class ProductOrderExtraAdapter extends ArrayAdapter<ProductOrderExt> {

    private List<ProductOrderExt> list;

    private LayoutInflater inflator;

    public ProductOrderExtraAdapter(Activity context, int row_layout, List<ProductOrderExt> list) {
        super(context, R.layout.row_product_order_extra, list);
        this.list = list;
        inflator = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProductOrderExtraAdapter.POrderExtraHolder pOrderExtraHolder;

        if(convertView == null){
            convertView= inflator.inflate(R.layout.row_product_order_extra,null);
            pOrderExtraHolder = new ProductOrderExtraAdapter.POrderExtraHolder();

            pOrderExtraHolder.odrNo = convertView.findViewById(R.id.odrNo);
            pOrderExtraHolder.odrQuantity = convertView.findViewById(R.id.odrQuantity);
            pOrderExtraHolder.odrRate = convertView.findViewById(R.id.odrRate);
            pOrderExtraHolder.odrAmount = convertView.findViewById(R.id.odrAmount);
            pOrderExtraHolder.odrStatus = convertView.findViewById(R.id.odrStatus);
            pOrderExtraHolder.oderDate = convertView.findViewById(R.id.oderDate);

            convertView.setTag(pOrderExtraHolder);
            convertView.setTag(R.id.odrNo, pOrderExtraHolder.odrNo);
            convertView.setTag(R.id.odrQuantity, pOrderExtraHolder.odrQuantity);
            convertView.setTag(R.id.odrRate, pOrderExtraHolder.odrRate);
            convertView.setTag(R.id.odrAmount, pOrderExtraHolder.odrAmount);
            convertView.setTag(R.id.odrStatus, pOrderExtraHolder.odrStatus);
            convertView.setTag(R.id.oderDate, pOrderExtraHolder.oderDate);

        }
        else{
            pOrderExtraHolder = (ProductOrderExtraAdapter.POrderExtraHolder) convertView.getTag();
        }

        pOrderExtraHolder.odrNo.setText(list.get(position).getOdrNo());
        pOrderExtraHolder.odrQuantity.setText(list.get(position).getOdrQuantity());
        pOrderExtraHolder.odrRate.setText(list.get(position).getOdrRate());
        pOrderExtraHolder.odrAmount.setText(list.get(position).getOdrAmount());
        pOrderExtraHolder.odrStatus.setText(list.get(position).getOdrStatus());
        pOrderExtraHolder.oderDate.setText(list.get(position).getOderDate());

        return convertView;

    }

    static class POrderExtraHolder{

        protected static TextView odrNo;
        protected static TextView odrQuantity;
        protected static TextView odrRate;
        protected static TextView odrAmount;
        protected static TextView odrStatus;
        protected static TextView oderDate;

    }

}
