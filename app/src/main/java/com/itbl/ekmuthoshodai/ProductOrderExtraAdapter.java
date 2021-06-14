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
        super(context, R.layout.row_product_extra, list);
        this.list = list;
        inflator = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProductOrderExtraAdapter.POrderExtraHolder pOrderExtraHolder;

        if(convertView == null){
            convertView= inflator.inflate(R.layout.row_product_extra,null);
            pOrderExtraHolder = new ProductOrderExtraAdapter.POrderExtraHolder();

            pOrderExtraHolder.imName = convertView.findViewById(R.id.imName);
            pOrderExtraHolder.imStatus = convertView.findViewById(R.id.imStatus);
            pOrderExtraHolder.imQuantity = convertView.findViewById(R.id.imQuantity);
            pOrderExtraHolder.imAmount = convertView.findViewById(R.id.imAmount);
            pOrderExtraHolder.imDiscount = convertView.findViewById(R.id.imDiscount);

            convertView.setTag(pOrderExtraHolder);
            convertView.setTag(R.id.imName, pOrderExtraHolder.imName);
            convertView.setTag(R.id.imStatus, pOrderExtraHolder.imStatus);
            convertView.setTag(R.id.imQuantity, pOrderExtraHolder.imQuantity);
            convertView.setTag(R.id.imAmount, pOrderExtraHolder.imAmount);
            convertView.setTag(R.id.imDiscount, pOrderExtraHolder.imDiscount);

        }
        else{
            pOrderExtraHolder = (ProductOrderExtraAdapter.POrderExtraHolder) convertView.getTag();
        }

        pOrderExtraHolder.imName.setText((list.get(position).getImName()));
        pOrderExtraHolder.imStatus.setText(list.get(position).getImStatus());
        pOrderExtraHolder.imQuantity.setText(list.get(position).getImQuantity());
        pOrderExtraHolder.imAmount.setText(list.get(position).getImRAmount());
        pOrderExtraHolder.imDiscount.setText(list.get(position).getImDiscount());

        return convertView;

    }

    static class POrderExtraHolder{

        protected static TextView imName;
        protected static TextView imStatus;
        protected static TextView imQuantity;
        protected static TextView imAmount;
        protected static TextView imDiscount;

    }

}
