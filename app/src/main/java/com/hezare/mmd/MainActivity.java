package com.hezare.mmd;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hezare.mmd.Adapters.ClassCheckBoxAdapter;
import com.hezare.mmd.Adapters.Drawer_List_Adapter;
import com.hezare.mmd.Models.ClassCheckBoxModel;
import com.hezare.mmd.Utils.AppUpdate;
import com.hezare.mmd.Utils.Utils;
import com.hezare.mmd.WebSide.Parser;
import com.hezare.mmd.WebSide.SendRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static int[] icons = {R.drawable.ic_edit_black_24dp, R.drawable.ic_info_black_24dp, R.drawable.ic_exit_to_app_black_24dp};
    public static String[] items = {"ویرایش رمز عبور", "درباره ما", "خروج از حساب کاربری"};
    ProgressDialog loading;
    String[] colorsid = {
            "#607D8B",
            "#F44336",
            "#9C27B0",
            "#673AB7",
            "#2196F3",
            "#009688",
            "#4CAF50",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#F57C00",
            "#795548",
            "#9E9E9E",

    };
    DrawerLayout drawer;
    ListView ListDrawer;
    private List<ClassCheckBoxModel> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ClassCheckBoxAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Utli.StrictMode();
        Utli.changeFont(getWindow().getDecorView());
        ((TextView) findViewById(R.id.teachername)).setText(PreferenceManager.getDefaultSharedPreferences(this).getString("Name", ""));

       /* final RelativeLayout threezang=(RelativeLayout)findViewById(R.id.threezang);
        final RelativeLayout fourzang=(RelativeLayout)findViewById(R.id.fourzang);

        findViewById(R.id.sss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fourzang.setVisibility(View.GONE);
                threezang.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.cccc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fourzang.setVisibility(View.VISIBLE);
                threezang.setVisibility(View.GONE);
            }
        });
*/


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ClassCheckBoxAdapter(movieList);
        //  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


           /* ClassCheckBoxModel movie = new ClassCheckBoxModel("کلاس اول 1","#F44336",getWindow().getDecorView(),1,2);
            movieList.add(movie);

            movie = new ClassCheckBoxModel("کلاس اول 2","#673AB7",getWindow().getDecorView(),2,3);
            movieList.add(movie);

             movie = new ClassCheckBoxModel("کلاس اول 3","#4CAF50",getWindow().getDecorView(),3,1);
            movieList.add(movie);

             movie = new ClassCheckBoxModel("کلاس اول 4","#FFC107",getWindow().getDecorView(),5,2);
             movieList.add(movie);



        movie = new ClassCheckBoxModel("کلاس سوم 1","#795548",getWindow().getDecorView(),4,2);
        movieList.add(movie);



        movie = new ClassCheckBoxModel("کلاس سوم 3","#607D8B",getWindow().getDecorView(),4,3);
        movieList.add(movie);



        movie = new ClassCheckBoxModel("کلاس سوم 4","#009688",getWindow().getDecorView(),2,1);
        movieList.add(movie);

        movie = new ClassCheckBoxModel("کلاس سوم 5","#536DFE",getWindow().getDecorView(),3,2);
        movieList.add(movie);


        mAdapter.notifyDataSetChanged();*/


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        findViewById(R.id.opendrawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ListDrawer = (ListView) navigationView.findViewById(R.id.drawer_slidermenu);
        ListDrawer.setAdapter(new Drawer_List_Adapter(this, items, icons));
        ListDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ShowChange();
                        break;


                    case 1:
                        startActivity(new Intent(MainActivity.this, About.class));
                        break;

                    case 2:
                        final AlertDialog alt = new AlertDialog.Builder(MainActivity.this).create();
                        alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خروج</p>"));
                        alt.setMessage("تمایل دارید از حساب کاربری خود خارج شوید؟");
                        alt.setButton(Dialog.BUTTON_POSITIVE, "آره", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MakeDialog(DilogType.LOADING, null);
                                SendRequest.SendPostLogOut();
                                new SendRequest().setOnLogOutCompleteListner(new SendRequest.OnLogOutCompleteListner() {
                                    @Override
                                    public void OnLogOutCompleteed(String response) {
                                        try {
                                            JSONObject oneObject = Parser.Parse(response).getJSONObject(0);
                                            String Text = oneObject.getString("Text");
                                            String Key = oneObject.getString("Key");
                                            String Name = oneObject.getString("Option");
                                            if (Key.trim().matches("OK")) {
                                                if (loading.isShowing()) {
                                                    loading.dismiss();
                                                }
                                                Toast.makeText(MainActivity.this, Text, Toast.LENGTH_SHORT).show();
                                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.clear();
                                                editor.commit();
                                                finish();

                                            } else {
                                                MakeDialog(DilogType.ERROR, Text);

                                            }

                                        } catch (JSONException e) {

                                        }
                                    }
                                });
                                new SendRequest().setOnLogOutErrorListner(new SendRequest.OnLogOutErrorListner() {
                                    @Override
                                    public void OnLogOutErrored(String response) {
                                        if (response.trim().contains("connectionError")) {
                                            MakeDialog(DilogType.ERROR, "خطا در اتصال به سرور!");

                                        } else {
                                            MakeDialog(DilogType.ERROR, "خطایی پیش آمده!");

                                        }
                                    }
                                });


                            }
                        });

                        alt.setButton(Dialog.BUTTON_NEGATIVE, "نه", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alt.dismiss();

                            }
                        });
                        alt.show();
                }
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String id = preferences.getString("ID", "1");
        SendRequest.SendPostBarnameHaftegi(id);
        MakeDialog(DilogType.LOADING, null);
        new SendRequest().setOnBarnameHaftegiCompleteListner(new SendRequest.OnBarnameHaftegiCompleteListner() {
            @Override
            public void OnBarnameHaftegiCompleteed(String response) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                String result = response.replaceAll("\\\\", "").replaceAll("^\"|\"$", "");
                Log.e("BarnameHaftegi", result);

                try {
                    JSONObject jObject = new JSONObject(result);
                    JSONObject jObject2 = new JSONObject(jObject.get("Root").toString());

                    JSONArray jArray = jObject2.getJSONArray("KelasHa");
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        String ClassId = oneObject.getString("ClassId");
                        String ClassName = oneObject.getString("ClassName");
                        JSONArray sub = oneObject.getJSONArray("Barname");
                        String RuzeHafte = null;
                        String Zang = null;
                        String BarnameHaftegiId = null;
                        String NameDars = null;
                        for (int k = 0; k < sub.length(); k++) {
                            JSONObject subObject = sub.getJSONObject(k);
                            RuzeHafte = subObject.getString("RuzeHafte");
                            Zang = subObject.getString("Zang");
                            BarnameHaftegiId = subObject.getString("BarnameHaftegiId");
                            NameDars = subObject.getString("NameDars");

                            Log.e("Classa : ", RuzeHafte + "/" + Zang);
                            /*String color=null;
                            try {
                                color= colorsid[i]  ;
                            }catch (Exception e){
                                color= "#F44336"  ;

                            }*/

                            if (Zang.equals("4")) {
                                //Log.e("Zang","5zange");
                                findViewById(R.id.column_5zange).setVisibility(View.VISIBLE);
                            }

                            Random r = new Random();
                            int c = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));
                            String color = "#" + Integer.toHexString(c).toUpperCase().substring(2);
                            ClassCheckBoxModel movie = new ClassCheckBoxModel(ClassName + " (" + NameDars + ")", color, getWindow().getDecorView(), Integer.parseInt(RuzeHafte), Integer.parseInt(Zang), NameDars, ClassId, BarnameHaftegiId);
                            movieList.add(movie);
                            mAdapter.notifyDataSetChanged();
                        }
                        //    Log.e("Classa : ", ClassId + "/" + ClassName + "/" + RuzeHafte + "/" + Zang + "/" + BarnameHaftegiId + "/" + NameDars);


                    }
                } catch (JSONException e) {

                    Log.e("ex", e.getMessage());
                }
            }
        });
        new SendRequest().setOnBarnameHaftegiErrorListner(new SendRequest.OnBarnameHaftegiErrorListner() {
            @Override
            public void OnBarnameHaftegiErrored(String response) {
                if (response.trim().contains("connectionError")) {
                    MakeDialog(DilogType.ERROR, "خطا در اتصال به سرور!");

                } else {
                    MakeDialog(DilogType.ERROR, "خطایی پیش آمده!");

                }

            }
        });

        final SharedPreferences pref = getSharedPreferences("ShowDialog", 0);
        Log.i("showDialog", String.valueOf(pref.getBoolean("ShowDialog", false)));
        if (pref.getBoolean("ShowDialog", false)) {
            try {
                new AppUpdate(this).check_Version();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    private void ShowChange() {
        final Dialog dl = new Dialog(MainActivity.this);
        dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dl.setContentView(R.layout.changepass);
        final TextInputLayout oldWrapper = (TextInputLayout) dl.findViewById(R.id.oldWrapper);
        final TextInputLayout newWrapper = (TextInputLayout) dl.findViewById(R.id.newWrapper);
        final Button change = (Button) dl.findViewById(R.id.change);
        final Button cancel = (Button) dl.findViewById(R.id.cancel);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old = oldWrapper.getEditText().getText().toString().trim();
                final String newe = newWrapper.getEditText().getText().toString().trim();
                if (old.matches("")) {
                    oldWrapper.setError("رمز عبور فعلی نباید خالی باشد");
                    return;
                } else {
                    oldWrapper.setErrorEnabled(false);

                }
                if (newe.matches("")) {
                    newWrapper.setError("رمز عبور جدید نباید خالی باشد");
                    return;
                } else {
                    newWrapper.setErrorEnabled(false);
                }


                MakeDialog(DilogType.LOADING, null);
                SendRequest.SendPostChangePass(old, newe);
                new SendRequest().setOnChangePassCompleteListner(new SendRequest.OnChangePassCompleteListner() {
                    @Override
                    public void OnChangePassCompleteed(String response) {
                        try {
                            JSONObject oneObject = Parser.Parse(response).getJSONObject(0);
                            String Text = oneObject.getString("Text");
                            String Key = oneObject.getString("Key");
                            if (Key.trim().matches("Success")) {
                                if (loading.isShowing()) {
                                    loading.dismiss();
                                }
                                dl.dismiss();
                                Toast.makeText(MainActivity.this, Text, Toast.LENGTH_SHORT).show();
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.commit();
                                startActivity(new Intent(MainActivity.this, Login.class));
                                finish();

                            } else {
                                MakeDialog(DilogType.ERROR, Text);

                            }

                        } catch (JSONException e) {

                        }
                    }
                });
                new SendRequest().setOnChangePassErrorListner(new SendRequest.OnChangePassErrorListner() {
                    @Override
                    public void OnChangePassErrored(String response) {
                        if (response.trim().contains("connectionError")) {
                            MakeDialog(DilogType.ERROR, "خطا در اتصال به سرور!");

                        } else {
                            MakeDialog(DilogType.ERROR, "خطایی پیش آمده!");

                        }
                    }
                });


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.dismiss();
            }
        });

        dl.show();

    }

    public void ChangeTabel(final int roz, final int zang, final String color, final View view, final String title, String darsname, final String classid, final String barnameid) {

        final TextView tabel = (TextView) view.findViewById(Utils.WichTabel3(roz, zang));
        tabel.setSelected(true);
        tabel.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tabel.setSingleLine(true);
        tabel.setText(darsname);
        if (color.matches("#000000")) {
            tabel.setBackgroundColor(Color.parseColor(color));
            tabel.setEnabled(false);
            tabel.setTextColor(Color.BLACK);

        } else {
            tabel.setBackgroundColor(Color.parseColor(color));
            tabel.setEnabled(true);
            tabel.setTextColor(Color.WHITE);
        }

        tabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent student = new Intent(view.getContext(), Student.class);
                student.putExtra("name", tabel.getText().toString());
                student.putExtra("classid", classid);
                student.putExtra("barnameid", barnameid);
                view.getContext().startActivity(student);
            }
        });


    }

    private void MakeDialog(DilogType type, String Text) {
        if (type == DilogType.LOADING) {
            loading = new ProgressDialog(MainActivity.this);
            loading.setMessage("درحال دریافت....");
            loading.setCancelable(false);
            loading.show();
        } else if (type == DilogType.ERROR) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            final AlertDialog alt = new AlertDialog.Builder(MainActivity.this).create();
            alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خطا!</p>"));
            alt.setMessage(Text);
            alt.setButton(Dialog.BUTTON_POSITIVE, "تمام", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alt.dismiss();

                }
            });
            alt.show();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);

        } else {
            finish();

        }
    }

    public enum DilogType {
        LOADING,
        ERROR
    }
}
