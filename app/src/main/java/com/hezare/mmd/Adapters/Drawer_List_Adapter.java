package com.hezare.mmd.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hezare.mmd.App;
import com.hezare.mmd.MainActivity;
import com.hezare.mmd.R;


public class Drawer_List_Adapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    String[] result;
    Context context;
    int[] imageId;

    public Drawer_List_Adapter(MainActivity mainActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result = prgmNameList;
        context = mainActivity;
        imageId = prgmImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.drawer_list_item, null);
        holder.tv = (TextView) rowView.findViewById(R.id.textView1);
        holder.tv.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        return rowView;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }

}