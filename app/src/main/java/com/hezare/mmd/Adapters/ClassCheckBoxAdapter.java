package com.hezare.mmd.Adapters;

/**
 * Created by amirhododi on 8/2/2017.
 */

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hezare.mmd.App;
import com.hezare.mmd.MainActivity;
import com.hezare.mmd.Models.ClassCheckBoxModel;
import com.hezare.mmd.R;

import java.util.List;

public class ClassCheckBoxAdapter extends RecyclerView.Adapter<ClassCheckBoxAdapter.MyViewHolder> {

    private List<ClassCheckBoxModel> moviesList;

    public ClassCheckBoxAdapter(List<ClassCheckBoxModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.checkbox_list_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ClassCheckBoxModel checkboxmodel = moviesList.get(position);
        holder.title.setText(checkboxmodel.getTitle());
        holder.title.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        setCheckBoxColor(holder.chk, checkboxmodel.getColor(), checkboxmodel.getColor());
        holder.chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    new MainActivity().ChangeTabel(checkboxmodel.getRoz(), checkboxmodel.getZang(), checkboxmodel.getColor(), checkboxmodel.getView(), checkboxmodel.getTitle(), checkboxmodel.getDarsname(), checkboxmodel.getClassid(), checkboxmodel.getBarnameHaftegiId());

                } else {
                    new MainActivity().ChangeTabel(checkboxmodel.getRoz(), checkboxmodel.getZang(), "#000000", checkboxmodel.getView(), checkboxmodel.getTitle(), checkboxmodel.getDarsname(), checkboxmodel.getClassid(), checkboxmodel.getBarnameHaftegiId());

                }
            }
        });
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.chk.isChecked()) {
                    holder.chk.setChecked(false);
                } else {
                    holder.chk.setChecked(true);

                }
            }
        });

        new MainActivity().ChangeTabel(checkboxmodel.getRoz(), checkboxmodel.getZang(), checkboxmodel.getColor(), checkboxmodel.getView(), checkboxmodel.getTitle(), checkboxmodel.getDarsname(), checkboxmodel.getClassid(), checkboxmodel.getBarnameHaftegiId());


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setCheckBoxColor(CheckBox checkBox, String checkedColor, String uncheckedColor) {
        int states[][] = {{android.R.attr.state_checked}, {}};
        int colors[] = {Color.parseColor(checkedColor), Color.parseColor(uncheckedColor)};
        CompoundButtonCompat.setButtonTintList(checkBox, new
                ColorStateList(states, colors));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public AppCompatCheckBox chk;
        public LinearLayout click;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            chk = (AppCompatCheckBox) view.findViewById(R.id.checkBox);
            click = (LinearLayout) view.findViewById(R.id.liclick);


        }
    }

}
