package com.hezare.mmd;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hezare.mmd.Adapters.ClassStudentListAdapter;
import com.hezare.mmd.Chat.ChatActivity;
import com.hezare.mmd.Models.ClassStudentListModel;
import com.hezare.mmd.WebSide.Parser;
import com.hezare.mmd.WebSide.SendRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Student extends AppCompatActivity {


    String USerID;
    int ratenum = 5;
    //  String [] a={"عارف نقشینی","دانا ایران پناه","امیر حدودی آذر","رضا محمدی","محمد علیزاده","علی رضایی","آرین حاجی زاده","محمد جواد نژاد","صدرا مختاری","مبین حسینی","سینا نعمتی","مهدی بابایی","صادق رضالو","امیر سنایی","رضا خلیلی"};
    String filepath = null;
    Button sendall;
    private List<ClassStudentListModel> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView studentheader;
    private ClassStudentListAdapter mAdapter;
    private EditText search;
    private int TabType = 2;
    private String ClassID;
    private String BarnameHaftegiId;
    private ProgressDialog loading;

    @Override
    protected void onDestroy() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("Day");
        editor.remove("Month");
        editor.apply();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentlist);
        Utli.StrictMode();
        Utli.changeFont(getWindow().getDecorView());
        studentheader = (TextView) findViewById(R.id.studentheader);
        search = (EditText) findViewById(R.id.studentsearchbox);
        studentheader.setText(getIntent().getStringExtra("name"));
        ClassID = getIntent().getStringExtra("classid");
        BarnameHaftegiId = getIntent().getStringExtra("barnameid");
        final LinearLayout nmlay = (LinearLayout) findViewById(R.id.nmlay);


        BottomNavigationView bottombar = (BottomNavigationView) findViewById(R.id.navigation);
        final BottomNavigationView bottombarnomre = (BottomNavigationView) findViewById(R.id.navigationnomre);
        disableShiftMode(bottombar);
        bottombar.setSelectedItemId(R.id.absent);


        sendall = (Button) findViewById(R.id.sendall);
        sendall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dl = new Dialog(Student.this);
                dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dl.setContentView(R.layout.dialog_homework);
                Utli.changeFont(dl.getWindow().getDecorView());

                dl.findViewById(R.id.studentname).setVisibility(View.GONE);
                final AppCompatSpinner haftedarsihomework = (AppCompatSpinner) dl.findViewById(R.id.haftedarsi);
                final EditText mothhomework = (EditText) dl.findViewById(R.id.month);
                final EditText dayhomework = (EditText) dl.findViewById(R.id.day);
                final EditText onvanhomework = (EditText) dl.findViewById(R.id.onvan);
                final EditText detailshomework = (EditText) dl.findViewById(R.id.details);
                final Button choosefile = (Button) dl.findViewById(R.id.choosefile);


                choosefile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");      //all files
                        intent.addCategory(Intent.CATEGORY_OPENABLE);

                        try {
                            startActivityForResult(Intent.createChooser(intent, "انتخاب فایل تکلیف..."), 110);
                        } catch (android.content.ActivityNotFoundException ex) {
                            // Potentially direct the user to the Market with a Dialog
                            Toast.makeText(Student.this, "فایل منیجری نصب نیست!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                SharedPreferences preferenceshomework = PreferenceManager.getDefaultSharedPreferences(Student.this);
                String daystrhomework = preferenceshomework.getString("Day", "");
                String monthstrhomework = preferenceshomework.getString("Month", "");
                dayhomework.setText(daystrhomework);
                mothhomework.setText(monthstrhomework);
                dl.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dl.dismiss();
                        filepath = null;
                    }
                });
                dl.findViewById(R.id.saveandexit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mothhomework.getText().toString().matches("")) {
                            Toast.makeText(Student.this, "ماه نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (dayhomework.getText().toString().matches("")) {
                            Toast.makeText(Student.this, "روز نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (onvanhomework.getText().toString().matches("")) {
                            Toast.makeText(Student.this, "عنوان تکلیف نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (detailshomework.getText().toString().matches("")) {
                            Toast.makeText(Student.this, "توضیح تکلیف نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (filepath == null) {
                            Toast.makeText(Student.this, "فایلی برای تکلیف انتخاب نشده!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int dayint = Integer.parseInt(dayhomework.getText().toString());
                        if (dayint < 10) {
                            dayhomework.setText("");
                            dayhomework.setText("0" + dayint);
                        }
                        int monthint = Integer.parseInt(mothhomework.getText().toString());
                        if (monthint < 10) {
                            mothhomework.setText("");
                            mothhomework.setText("0" + monthint);
                        }

                        MakeWorkDialog(DilogWorksType.LOADING, "درحال ثبت تکلیف جمعی...");
                        int week = haftedarsihomework.getSelectedItemPosition() + 1;
                        SendRequest.SendPostHomeWork(USerID, BarnameHaftegiId, mothhomework.getText().toString() + "/" + dayhomework.getText().toString(), week, onvanhomework.getText().toString(), detailshomework.getText().toString(), filepath, "Jami");
                        new SendRequest().setOnHomeWorkCompleteListner(new SendRequest.OnHomeWorkCompleteListner() {
                            @Override
                            public void OnHomeWorkCompleteed(String response) {
                                try {
                                    JSONObject oneObject = Parser.Parse(response).getJSONObject(0);
                                    String Text = oneObject.getString("Text");
                                    String Key = oneObject.getString("Key");
                                    if (Key.matches("OK")) {
                                        if (loading.isShowing()) {
                                            loading.dismiss();
                                            dl.dismiss();
                                            Toast.makeText(Student.this, Text, Toast.LENGTH_SHORT).show();
                                            filepath = null;
                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Student.this);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("Day", dayhomework.getText().toString());
                                            editor.putString("Month", mothhomework.getText().toString());
                                            editor.apply();
                                        }

                                    } else {
                                        MakeWorkDialog(DilogWorksType.ERROR, Text);

                                    }


                                } catch (JSONException e) {
                                    MakeWorkDialog(DilogWorksType.ERROR, "خطایی در ثبت پیش آمده!");


                                }
                            }


                        });
                        new SendRequest().setOnHomeWorkErrorListner(new SendRequest.OnHomeWorkErrorListner() {
                            @Override
                            public void OnHomeWorkErrored(String response) {
                                if (response.trim().contains("connectionError")) {
                                    MakeWorkDialog(DilogWorksType.ERROR, "خطا در اتصال به سرور!");

                                } else {
                                    MakeWorkDialog(DilogWorksType.ERROR, "خطایی پیش آمده!");

                                }

                            }
                        });

                    }
                });

                dl.show();
            }
        });


        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homework:
                        TabType = 0;
                        if (movieList.size() > 0) {
                            sendall.setVisibility(View.VISIBLE);
                        }
                        nmlay.setVisibility(View.GONE);
                        break;
                 /*   case R.id.classgrade:
                        TabType=1;
                        sendall.setVisibility(View.GONE);
                        nmlay.setVisibility(View.GONE);
                        break;*/
                    case R.id.absent:
                        TabType = 2;
                        sendall.setVisibility(View.GONE);
                        nmlay.setVisibility(View.GONE);
                        break;

                 /*   case R.id.examgrade:
                        TabType=3;
                        sendall.setVisibility(View.GONE);
                        nmlay.setVisibility(View.GONE);
                        break;*/
                    case R.id.chat:
                        TabType = 4;
                        sendall.setVisibility(View.GONE);
                        nmlay.setVisibility(View.GONE);
                        break;
                    case R.id.rate:
                        TabType = 5;
                        sendall.setVisibility(View.GONE);
                        nmlay.setVisibility(View.GONE);
                        break;
                    case R.id.nomreh:
                        TabType = 1;
                        bottombarnomre.setSelectedItemId(R.id.classgrade);
                        sendall.setVisibility(View.GONE);
                        nmlay.setVisibility(View.VISIBLE);
                        break;
                }

                return true;

            }
        });


        bottombarnomre.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.classgrade:
                        TabType = 1;
                        sendall.setVisibility(View.GONE);
                        break;

                    case R.id.examgrade:
                        TabType = 3;
                        sendall.setVisibility(View.GONE);
                        break;

                }


                return true;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ClassStudentListAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        mAdapter.setOnClickListner(new ClassStudentListAdapter.OnClickListner() {
            @Override
            public void OnClicked(View view, final int position, final List<ClassStudentListModel> moviesList) {
                final Dialog dl = new Dialog(Student.this);
                dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Utli.changeFont(dl.getWindow().getDecorView());

                switch (TabType) {
                    case 4:
                        Intent chat = new Intent(Student.this, ChatActivity.class);
                        chat.putExtra("name", moviesList.get(position).getTitle());
                        chat.putExtra("F_OvliaID", moviesList.get(position).getF_OvliaID());
                        startActivity(chat);
                        break;


                    case 5:
                        dl.setContentView(R.layout.dialog_rate);
                        final AppCompatSpinner haftedarsi1 = (AppCompatSpinner) dl.findViewById(R.id.haftedarsi);
                        final EditText moth1 = (EditText) dl.findViewById(R.id.month);
                        final EditText day1 = (EditText) dl.findViewById(R.id.day);
                        final EditText detailst1 = (EditText) dl.findViewById(R.id.details);
                        final CheckBox rate = (CheckBox) dl.findViewById(R.id.starch);
                        final CheckBox faild = (CheckBox) dl.findViewById(R.id.faildch);
                        final SeekBar RateSeek = (SeekBar) dl.findViewById(R.id.seekBar);
                        ((TextView) dl.findViewById(R.id.studentname)).setText(moviesList.get(position).getTitle());
                        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(Student.this);
                        String daystr1 = preferences1.getString("Day", "");
                        String monthstr1 = preferences1.getString("Month", "");
                        day1.setText(daystr1);
                        moth1.setText(monthstr1);
                        dl.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dl.dismiss();
                            }
                        });

                        rate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    //takhirlay.setVisibility(View.VISIBLE);
                                    faild.setChecked(false);
                                } else {
                                    //  takhirlay.setVisibility(View.GONE);
                                    faild.setChecked(true);

                                }
                            }
                        });
                        faild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    rate.setChecked(false);
                                    //takhirlay.setVisibility(View.GONE);
                                } else {
                                    rate.setChecked(true);

                                }
                            }
                        });

                        dl.findViewById(R.id.saveandexit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (moth1.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "ماه نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (day1.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "روز نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                int dayint = Integer.parseInt(day1.getText().toString());
                                if (dayint < 10) {
                                    day1.setText("");
                                    day1.setText("0" + dayint);
                                }
                                int monthint = Integer.parseInt(moth1.getText().toString());
                                if (monthint < 10) {
                                    moth1.setText("");
                                    moth1.setText("0" + monthint);
                                }

                                if (rate.isChecked()) {
                                    ratenum = RateSeek.getProgress() + 1;
                                } else {
                                    ratenum = (RateSeek.getProgress() + 1) * -1;

                                }


                                MakeDialog(DilogType.LOADING, null);
                                int week = haftedarsi1.getSelectedItemPosition() + 1;
                                SendRequest.SendPostTashvighoTanbiheKelasi(moviesList.get(position).getStudentID(), BarnameHaftegiId, ratenum + "", detailst1.getText().toString(), week + "", moth1.getText().toString() + "/" + day1.getText().toString());
                                new SendRequest().setOnTashvighoTanbiheKelasiCompleteListner(new SendRequest.OnTashvighoTanbiheKelasiCompleteListner() {
                                    @Override
                                    public void OnTashvighoTanbiheKelasiCompleteed(String response) {

                                        try {
                                            JSONObject oneObject = Parser.Parse(response).getJSONObject(0);
                                            String Text = oneObject.getString("Text");
                                            String Key = oneObject.getString("Key");
                                            if (Key.matches("OK")) {
                                                if (loading.isShowing()) {
                                                    loading.dismiss();
                                                    dl.dismiss();
                                                    Toast.makeText(Student.this, Text, Toast.LENGTH_SHORT).show();
                                                    SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(Student.this);
                                                    SharedPreferences.Editor editor1 = preferences1.edit();
                                                    editor1.putString("Day", day1.getText().toString());
                                                    editor1.putString("Month", moth1.getText().toString());
                                                    editor1.apply();
                                                    dl.dismiss();
                                                }

                                            } else {
                                                MakeDialog(DilogType.ERROR, Text);

                                            }


                                        } catch (JSONException e) {
                                            MakeDialog(DilogType.ERROR, "خطایی در ثبت پیش آمده!");


                                        }


                                    }
                                });
                                new SendRequest().setOnTashvighoTanbiheKelasiErrorListner(new SendRequest.OnTashvighoTanbiheKelasiErrorListner() {
                                    @Override
                                    public void OnTashvighoTanbiheKelasiErrored(String response) {
                                        if (response.trim().contains("connectionError")) {
                                            MakeDialog(DilogType.ERROR, "خطا در اتصال به سرور!");

                                        } else {
                                            MakeDialog(DilogType.ERROR, "خطایی پیش آمده!");

                                        }
                                    }
                                });


                            }
                        });


                        dl.show();


                        break;


                    case 2:
                        dl.setContentView(R.layout.dialog_absent);
                        final CheckBox absent = (CheckBox) dl.findViewById(R.id.absentch);
                        final CheckBox takhirch = (CheckBox) dl.findViewById(R.id.takhirch);
                        final LinearLayout takhirlay = (LinearLayout) dl.findViewById(R.id.takjirlay);

                        final AppCompatSpinner haftedarsiabsent = (AppCompatSpinner) dl.findViewById(R.id.haftedarsi);
                        final EditText mothabsent = (EditText) dl.findViewById(R.id.month);
                        final EditText dayabsent = (EditText) dl.findViewById(R.id.day);
                        final EditText takhiredit = (EditText) dl.findViewById(R.id.takhiredit);
                        final EditText detailsabsent = (EditText) dl.findViewById(R.id.details);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Student.this);
                        String daystr = preferences.getString("Day", "");
                        String monthstr = preferences.getString("Month", "");
                        dayabsent.setText(daystr);
                        mothabsent.setText(monthstr);

                        ((TextView) dl.findViewById(R.id.studentname)).setText(moviesList.get(position).getTitle());
                        takhirch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    takhirlay.setVisibility(View.VISIBLE);
                                    absent.setChecked(false);
                                } else {
                                    takhirlay.setVisibility(View.GONE);
                                    absent.setChecked(true);


                                }
                            }
                        });
                        absent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    takhirch.setChecked(false);
                                    takhirlay.setVisibility(View.GONE);
                                } else {
                                    takhirch.setChecked(true);
                                    takhirlay.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                        dl.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dl.dismiss();
                            }
                        });
                        dl.findViewById(R.id.saveandexit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mothabsent.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "ماه نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (dayabsent.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "روز نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (takhirch.isChecked()) {
                                    if (takhiredit.getText().toString().matches("")) {
                                        Toast.makeText(Student.this, "مدت تاخیر نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                int dayint = Integer.parseInt(dayabsent.getText().toString());
                                if (dayint < 10) {
                                    dayabsent.setText("");
                                    dayabsent.setText("0" + dayint);
                                }
                                int monthint = Integer.parseInt(mothabsent.getText().toString());
                                if (monthint < 10) {
                                    mothabsent.setText("");
                                    mothabsent.setText("0" + monthint);
                                }
                                String takhir;

                                if (takhirch.isChecked()) {
                                    takhir = takhiredit.getText().toString();
                                } else {
                                    takhir = "-1";
                                }

                                MakeWorkDialog(DilogWorksType.LOADING, "درحال ثبت...");
                                int week = haftedarsiabsent.getSelectedItemPosition() + 1;
                                SendRequest.SendPostAbsent(moviesList.get(position).getStudentID(), BarnameHaftegiId, takhir, detailsabsent.getText().toString(), week, mothabsent.getText().toString() + "/" + dayabsent.getText().toString());
                                new SendRequest().setOnAbsentCompleteListner(new SendRequest.OnAbsentCompleteListner() {
                                    @Override
                                    public void OnAbsentCompleteed(String response) {
                                        try {
                                            JSONObject oneObject = Parser.Parse(response).getJSONObject(0);
                                            String Text = oneObject.getString("Text");
                                            String Key = oneObject.getString("Key");
                                            if (Key.matches("OK")) {
                                                if (loading.isShowing()) {
                                                    loading.dismiss();
                                                    dl.dismiss();
                                                    Toast.makeText(Student.this, Text, Toast.LENGTH_SHORT).show();
                                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Student.this);
                                                    SharedPreferences.Editor editor = preferences.edit();
                                                    editor.putString("Day", dayabsent.getText().toString());
                                                    editor.putString("Month", mothabsent.getText().toString());
                                                    editor.apply();
                                                }

                                            } else {
                                                MakeWorkDialog(DilogWorksType.ERROR, Text);

                                            }


                                        } catch (JSONException e) {
                                            MakeWorkDialog(DilogWorksType.ERROR, "خطایی در ثبت پیش آمده!");


                                        }
                                    }


                                });
                                new SendRequest().setOnAbsentErrorListner(new SendRequest.OnAbsentErrorListner() {
                                    @Override
                                    public void OnAbsentErrored(String response) {
                                        if (response.trim().contains("connectionError")) {
                                            MakeWorkDialog(DilogWorksType.ERROR, "خطا در اتصال به سرور!");

                                        } else {
                                            MakeWorkDialog(DilogWorksType.ERROR, "خطایی پیش آمده!");

                                        }

                                    }
                                });


                            }
                        });
                        dl.show();
                        break;
                    case 3:
                        dl.setContentView(R.layout.dialog_examgrade);
                        ((TextView) dl.findViewById(R.id.studentname)).setText(moviesList.get(position).getTitle());
                        final EditText number1 = (EditText) dl.findViewById(R.id.classnumber);
                        final EditText details1 = (EditText) dl.findViewById(R.id.details);
                        dl.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dl.dismiss();
                            }
                        });
                        dl.findViewById(R.id.saveandexit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               /* if (haftedarsi.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "هفته درسی نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                }else*/
                                if (number1.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "نمره امتحان نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                } /* else if (details1.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "توضیحات نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                }*/

                                MakeWorkDialog(DilogWorksType.LOADING, "درحال ثبت...");
                                SendRequest.SendPostExamNumber(moviesList.get(position).getStudentID(), BarnameHaftegiId, number1.getText().toString(), details1.getText().toString());
                                new SendRequest().setOnExamGradeCompleteListner(new SendRequest.OnExamGradeCompleteListner() {
                                    @Override
                                    public void OnExamGradeCompleteed(String response) {
                                        try {
                                            JSONObject oneObject = Parser.Parse(response).getJSONObject(0);
                                            String Text = oneObject.getString("Text");
                                            String Key = oneObject.getString("Key");
                                            if (Key.matches("OK")) {
                                                if (loading.isShowing()) {
                                                    loading.dismiss();
                                                    dl.dismiss();
                                                    Toast.makeText(Student.this, Text, Toast.LENGTH_SHORT).show();

                                                }

                                            } else {
                                                MakeWorkDialog(DilogWorksType.ERROR, Text);

                                            }


                                        } catch (JSONException e) {
                                            MakeWorkDialog(DilogWorksType.ERROR, "خطایی در ثبت پیش آمده!");


                                        }
                                    }


                                });
                                new SendRequest().setOnExamGradeErrorListner(new SendRequest.OnExamGradeErrorListner() {
                                    @Override
                                    public void OnExamGradeErrored(String response) {
                                        if (response.trim().contains("connectionError")) {
                                            MakeWorkDialog(DilogWorksType.ERROR, "خطا در اتصال به سرور!");

                                        } else {
                                            MakeWorkDialog(DilogWorksType.ERROR, "خطایی پیش آمده!");

                                        }

                                    }
                                });
                            }
                        });
                        dl.show();
                        break;
                    case 1:
                        dl.setContentView(R.layout.dialog_classgrade);
                        ((TextView) dl.findViewById(R.id.studentname)).setText(moviesList.get(position).getTitle());
                        final AppCompatSpinner haftedarsi = (AppCompatSpinner) dl.findViewById(R.id.haftedarsi);
                        final EditText moth = (EditText) dl.findViewById(R.id.month);
                        final EditText day = (EditText) dl.findViewById(R.id.day);
                        final EditText number = (EditText) dl.findViewById(R.id.classnumber);
                        final EditText details = (EditText) dl.findViewById(R.id.details);


                        SharedPreferences preferencesabsent = PreferenceManager.getDefaultSharedPreferences(Student.this);
                        String daystrabsent = preferencesabsent.getString("Day", "");
                        String monthstrabsent = preferencesabsent.getString("Month", "");
                        day.setText(daystrabsent);
                        moth.setText(monthstrabsent);
                        dl.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dl.dismiss();
                            }
                        });
                        dl.findViewById(R.id.saveandexit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               /* if (haftedarsi.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "هفته درسی نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                }else*/
                                if (moth.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "ماه نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (day.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "روز نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (number.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "نمره درس نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                int dayint = Integer.parseInt(day.getText().toString());
                                if (dayint < 10) {
                                    day.setText("");
                                    day.setText("0" + dayint);
                                }
                                int monthint = Integer.parseInt(moth.getText().toString());
                                if (monthint < 10) {
                                    moth.setText("");
                                    moth.setText("0" + monthint);
                                }

                                MakeWorkDialog(DilogWorksType.LOADING, "درحال ثبت...");
                                int week = haftedarsi.getSelectedItemPosition() + 1;
                                SendRequest.SendPostClassNumber(moviesList.get(position).getStudentID(), BarnameHaftegiId, moth.getText().toString() + "/" + day.getText().toString(), week, number.getText().toString(), details.getText().toString());
                                new SendRequest().setOnListClassNumberCompleteListner(new SendRequest.OnListClassNumberCompleteListner() {
                                    @Override
                                    public void OnListClassNumberCompleteed(String response) {
                                        try {
                                            JSONObject oneObject = Parser.Parse(response).getJSONObject(0);
                                            String Text = oneObject.getString("Text");
                                            String Key = oneObject.getString("Key");
                                            if (Key.matches("OK")) {
                                                if (loading.isShowing()) {
                                                    loading.dismiss();
                                                    dl.dismiss();
                                                    Toast.makeText(Student.this, Text, Toast.LENGTH_SHORT).show();
                                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Student.this);
                                                    SharedPreferences.Editor editor = preferences.edit();
                                                    editor.putString("Day", day.getText().toString());
                                                    editor.putString("Month", moth.getText().toString());
                                                    editor.apply();

                                                }

                                            } else {
                                                MakeWorkDialog(DilogWorksType.ERROR, Text);

                                            }


                                        } catch (JSONException e) {
                                            MakeWorkDialog(DilogWorksType.ERROR, "خطایی در ثبت پیش آمده!");


                                        }
                                    }


                                });
                                new SendRequest().setOnListClassNumberErrorListner(new SendRequest.OnListClassNumberErrorListner() {
                                    @Override
                                    public void OnListClassNumberErrored(String response) {
                                        if (response.trim().contains("connectionError")) {
                                            MakeWorkDialog(DilogWorksType.ERROR, "خطا در اتصال به سرور!");

                                        } else {
                                            MakeWorkDialog(DilogWorksType.ERROR, "خطایی پیش آمده!");

                                        }

                                    }
                                });
                            }
                        });
                        dl.show();
                        break;
                    case 0:
                        dl.setContentView(R.layout.dialog_homework);
                        ((TextView) dl.findViewById(R.id.studentname)).setText(moviesList.get(position).getTitle());
                        final AppCompatSpinner haftedarsihomework = (AppCompatSpinner) dl.findViewById(R.id.haftedarsi);
                        final EditText mothhomework = (EditText) dl.findViewById(R.id.month);
                        final EditText dayhomework = (EditText) dl.findViewById(R.id.day);
                        final EditText onvanhomework = (EditText) dl.findViewById(R.id.onvan);
                        final EditText detailshomework = (EditText) dl.findViewById(R.id.details);
                        final Button choosefile = (Button) dl.findViewById(R.id.choosefile);


                        choosefile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("*/*");      //all files
                                intent.addCategory(Intent.CATEGORY_OPENABLE);

                                try {
                                    startActivityForResult(Intent.createChooser(intent, "انتخاب فایل تکلیف..."), 110);
                                } catch (android.content.ActivityNotFoundException ex) {
                                    // Potentially direct the user to the Market with a Dialog
                                    Toast.makeText(Student.this, "فایل منیجری نصب نیست!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                        SharedPreferences preferenceshomework = PreferenceManager.getDefaultSharedPreferences(Student.this);
                        String daystrhomework = preferenceshomework.getString("Day", "");
                        String monthstrhomework = preferenceshomework.getString("Month", "");
                        dayhomework.setText(daystrhomework);
                        mothhomework.setText(monthstrhomework);
                        dl.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dl.dismiss();
                                filepath = null;
                            }
                        });
                        dl.findViewById(R.id.saveandexit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mothhomework.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "ماه نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (dayhomework.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "روز نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (onvanhomework.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "عنوان تکلیف نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (detailshomework.getText().toString().matches("")) {
                                    Toast.makeText(Student.this, "توضیح تکلیف نباید خالی باشد!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (filepath == null) {
                                    Toast.makeText(Student.this, "فایلی برای تکلیف انتخاب نشده!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                int dayint = Integer.parseInt(dayhomework.getText().toString());
                                if (dayint < 10) {
                                    dayhomework.setText("");
                                    dayhomework.setText("0" + dayint);
                                }
                                int monthint = Integer.parseInt(mothhomework.getText().toString());
                                if (monthint < 10) {
                                    mothhomework.setText("");
                                    mothhomework.setText("0" + monthint);
                                }

                                MakeWorkDialog(DilogWorksType.LOADING, "درحال ثبت...");
                                int week = haftedarsihomework.getSelectedItemPosition() + 1;
                                SendRequest.SendPostHomeWork(moviesList.get(position).getStudentID(), BarnameHaftegiId, mothhomework.getText().toString() + "/" + dayhomework.getText().toString(), week, onvanhomework.getText().toString(), detailshomework.getText().toString(), filepath, "fardi");
                                new SendRequest().setOnHomeWorkCompleteListner(new SendRequest.OnHomeWorkCompleteListner() {
                                    @Override
                                    public void OnHomeWorkCompleteed(String response) {
                                        try {
                                            JSONObject oneObject = Parser.Parse(response).getJSONObject(0);
                                            String Text = oneObject.getString("Text");
                                            String Key = oneObject.getString("Key");
                                            if (Key.matches("OK")) {
                                                if (loading.isShowing()) {
                                                    loading.dismiss();
                                                    dl.dismiss();
                                                    Toast.makeText(Student.this, Text, Toast.LENGTH_SHORT).show();
                                                    filepath = null;
                                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Student.this);
                                                    SharedPreferences.Editor editor = preferences.edit();
                                                    editor.putString("Day", dayhomework.getText().toString());
                                                    editor.putString("Month", mothhomework.getText().toString());
                                                    editor.apply();
                                                }

                                            } else {
                                                MakeWorkDialog(DilogWorksType.ERROR, Text);

                                            }


                                        } catch (JSONException e) {
                                            MakeWorkDialog(DilogWorksType.ERROR, "خطایی در ثبت پیش آمده!");


                                        }
                                    }


                                });
                                new SendRequest().setOnHomeWorkErrorListner(new SendRequest.OnHomeWorkErrorListner() {
                                    @Override
                                    public void OnHomeWorkErrored(String response) {
                                        if (response.trim().contains("connectionError")) {
                                            MakeWorkDialog(DilogWorksType.ERROR, "خطا در اتصال به سرور!");

                                        } else {
                                            MakeWorkDialog(DilogWorksType.ERROR, "خطایی پیش آمده!");

                                        }

                                    }
                                });

                            }
                        });

                        dl.show();
                }
            }
        });


        MakeDialog(DilogType.LOADING, null);
        SendRequest.SendPostListStudents(ClassID);
        new SendRequest().setOnListStudentsCompleteListner(new SendRequest.OnListStudentsCompleteListner() {
            @Override
            public void OnListStudentsCompleteed(String response) {
                JSONArray j = Parser.Parse(response);
                List<String> contacts = new ArrayList<>();


                for (int i1 = 0; i1 < j.length(); i1++) {
                    try {
                        JSONObject oneObject = j.getJSONObject(i1);
                        String FirstName = oneObject.getString("FirstName");
                        String LastName = oneObject.getString("LastName");
                        String ID = oneObject.getString("ID");
                        String F_OvliaID = oneObject.getString("OvliaChatId");
                        if (loading.isShowing()) {
                            loading.dismiss();
                        }
                        USerID = ID;
                        String Name = FirstName + ":" + LastName + "*" + ID + "#" + F_OvliaID;
                        contacts.add(Name);
                        Log.e("Students : ", FirstName + "/" + LastName + "#" + ID);

                    } catch (JSONException e) {
                        MakeDialog(DilogType.ERROR, "تاکنون دانش آموزی برای این کلاس ثبت نشده!");
                    }
                }

                ParseNames(contacts);

                if (j.length() < 1) {
                    MakeDialog(DilogType.ERROR, "تاکنون دانش آموزی برای این کلاس ثبت نشده!");

                }
            }
        });
        new SendRequest().setOnListStudentsErrorListner(new SendRequest.OnListStudentsErrorListner() {
            @Override
            public void OnListStudentsErrored(String response) {
                if (response.trim().contains("connectionError")) {
                    MakeDialog(DilogType.ERROR, "خطا در اتصال به سرور!");

                } else {
                    MakeDialog(DilogType.ERROR, "خطایی پیش آمده!");

                }
            }
        });


      /*  for (String name : a){
            Log.e("NAME",name);
            ClassStudentListModel movie = new ClassStudentListModel(name);
            movieList.add(movie);
            mAdapter.notifyDataSetChanged();
        }
*/


       /* ClassStudentListModel movie = new ClassStudentListModel("رضا محمدی");
        movieList.add(movie);

        movie = new ClassStudentListModel("محمد علیزاده");
        movieList.add(movie);
        movie = new ClassStudentListModel("علی رضایی");
        movieList.add(movie);
        movie = new ClassStudentListModel("آرین حاجی زاده");
        movieList.add(movie);
        movie = new ClassStudentListModel("محمد جواد نژاد");
        movieList.add(movie);
        movie = new ClassStudentListModel("صدرا مختاری");
        movieList.add(movie);
        movie = new ClassStudentListModel("امیر حدودی آذر");
        movieList.add(movie);
        movie = new ClassStudentListModel("مبین حسینی");
        movieList.add(movie);
        movie = new ClassStudentListModel("سینا نعمتی");
        movieList.add(movie);
        movie = new ClassStudentListModel("مهدی بابایی");
        movieList.add(movie);
        movie = new ClassStudentListModel("صادق رضالو");
        movieList.add(movie);
        movie = new ClassStudentListModel("آرین دوستی");
        movieList.add(movie);
        movie = new ClassStudentListModel("پوریا شمس");
        movieList.add(movie);



        mAdapter.notifyDataSetChanged();*/


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text


            }
        });


    }

    private void ParseNames(List<String> names) {

        for (char alphabet = 'آ'; alphabet <= 'ی'; alphabet++) {
            List<String> contacts = getContactsWithLetter(alphabet, names);

            if (contacts.size() > 0) {
                //   ContactsSection contactsSection = new ContactsSection(String.valueOf(alphabet), contacts);
                //   sectionAdapter.addSection(contactsSection);
                Log.e("NAME", "Header");
                ClassStudentListModel movie = new ClassStudentListModel(alphabet + "", true, null, null, null);
                movieList.add(movie);
                for (String name : contacts) {
                    Log.e("aaaa", name);
                    String stname = name.substring(0, name.lastIndexOf("*"));
                    String stidold = name.substring(name.lastIndexOf('*') + 1);
                    String F_OvliaID = name.substring(name.lastIndexOf('#') + 1);
                    String stid = stidold.replace("#" + F_OvliaID, "");
                    movie = new ClassStudentListModel(stname, false, stid, BarnameHaftegiId, F_OvliaID);
                    movieList.add(movie);
                    mAdapter.notifyDataSetChanged();
                }

            }
        }
    }

    private List<String> getContactsWithLetter(char letter, List<String> names) {
        List<String> contacts = new ArrayList<>();

        for (String contact : names) {
            String lastName = contact.substring(contact.lastIndexOf(":") + 1);
            if (lastName.charAt(0) == letter) {
                contacts.add(contact.replaceAll(":", " "));
            }
        }

        return contacts;
    }

    void filter(String text) {
        List<ClassStudentListModel> temp = new ArrayList<>();
        for (ClassStudentListModel d : movieList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getTitle().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        mAdapter.updateList(temp);

    }

    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    private void MakeDialog(DilogType type, String Text) {
        if (type == DilogType.LOADING) {
            loading = new ProgressDialog(Student.this);
            loading.setMessage("درحال دریافت لیست دانش آموزان.......");
            loading.setCancelable(false);
            loading.show();
        } else if (type == DilogType.ERROR) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            final AlertDialog alt = new AlertDialog.Builder(Student.this).create();
            alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خطا!</p>"));
            alt.setMessage(Text);
            alt.setButton(Dialog.BUTTON_POSITIVE, "تمام", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alt.dismiss();
                    finish();

                }
            });
            alt.show();
        }
    }

    private void MakeWorkDialog(DilogWorksType type, String Text) {
        if (type == DilogWorksType.LOADING) {
            loading = new ProgressDialog(Student.this);
            loading.setMessage(Text);
            loading.setCancelable(false);
            loading.show();
        } else if (type == DilogWorksType.ERROR) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            final AlertDialog alt = new AlertDialog.Builder(Student.this).create();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 110:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    filepath = new Utli().getPath(uri);
                    Toast.makeText(this, "فایل مورد نظر انتخاب شد!", Toast.LENGTH_SHORT).show();
                    // Get the path
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public enum DilogType {
        LOADING,
        ERROR
    }

    public enum DilogWorksType {
        LOADING,
        ERROR
    }
}

