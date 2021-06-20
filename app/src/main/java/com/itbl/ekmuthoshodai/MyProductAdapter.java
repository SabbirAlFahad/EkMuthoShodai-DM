package com.itbl.ekmuthoshodai;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import com.itbl.ekmuthoshodai.entities.MyProduct;

import java.util.List;

public class MyProductAdapter extends ArrayAdapter<MyProduct> {

    private List<MyProduct> list;

    private LayoutInflater inflater;

    public MyProductAdapter(Activity context, int row_layout, List<MyProduct> list) {
        super(context, R.layout.row_myproduct, list);
        this.list = list;
        inflater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        MyProductHolder myProductHolder;

        if(convertView == null){
            convertView= inflater.inflate(R.layout.row_myproduct,null);
            myProductHolder = new MyProductHolder();

            myProductHolder.imName = convertView.findViewById(R.id.imName);
            myProductHolder.imStock = convertView.findViewById(R.id.imStock);
            myProductHolder.imRate = convertView.findViewById(R.id.imRate);
            myProductHolder.imQuantity = convertView.findViewById(R.id.imQuantity);
            myProductHolder.imAmount = convertView.findViewById(R.id.imAmount);
            myProductHolder.imDiscount = convertView.findViewById(R.id.imDiscount);

            convertView.setTag(myProductHolder);
            convertView.setTag(R.id.imName, myProductHolder.imName);
            convertView.setTag(R.id.imStock, myProductHolder.imStock);
            convertView.setTag(R.id.imRate, myProductHolder.imRate);
            convertView.setTag(R.id.imQuantity, myProductHolder.imQuantity);
            convertView.setTag(R.id.imAmount, myProductHolder.imAmount);
            convertView.setTag(R.id.imDiscount, myProductHolder.imDiscount);

        }
        else{
            myProductHolder = (MyProductHolder) convertView.getTag();
        }

        myProductHolder.imName.setText(list.get(position).getImName());
        myProductHolder.imStock.setText(list.get(position).getImStock());
        myProductHolder.imRate.setText(list.get(position).getImRate());
        myProductHolder.imQuantity.setText(list.get(position).getImQuantity());
        myProductHolder.imAmount.setText(list.get(position).getImAmount());
        myProductHolder.imDiscount.setText(list.get(position).getImDiscount());

        return convertView;

    }

    static class MyProductHolder{

        protected static TextView imName;
        protected static TextView imStock;
        protected static TextView imRate;
        protected static TextView imQuantity;
        protected static TextView imAmount;
        protected static TextView imDiscount;

    }

}

