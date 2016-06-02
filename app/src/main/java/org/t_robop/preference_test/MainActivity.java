package org.t_robop.preference_test;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    int rema;

    //表示指定
    TextView tV;
    TextView TV;
    Button plus;
    Button write;
    Button read;
    Button dia;

    //表示変数
    int num;

    Calendar calendar;

    Calendar nowCale;
    Calendar birthCale;

    //preference
    SharedPreferences prefer;

    SharedPreferences.Editor editor;

    int y;
    int m;
    int d;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tV=(TextView)findViewById(R.id.textView);
        plus=(Button)findViewById(R.id.plus);
        write=(Button)findViewById(R.id.write);
        read=(Button)findViewById(R.id.read);
        dia=(Button)findViewById(R.id.button);
        TV = (TextView) findViewById(R.id.textV);

        calendar = Calendar.getInstance();

        nowCale=Calendar.getInstance();
        birthCale=Calendar.getInstance();

        //preference使います
        prefer = getSharedPreferences("test", MODE_PRIVATE);

    }

    public void write(View v)
    {
        //書き込み準備
        SharedPreferences.Editor editor = prefer.edit();
        editor.putInt("test", num);
        //書き込み実行
        editor.apply();
    }

    public void read(View v)
    {
        //読み込み準備
        //読み込み（10はデフォ値）
        num = prefer.getInt("test", 10);
        //表示
        tV.setText(String.valueOf(num));
    }

    public void plus(View v)
    {
        //加算して表示
        num=num+1;
        tV.setText(String.valueOf(num));
    }

    public void datedia(View v)
    {

        ////////// 日付情報の初期設定 //////////
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR); // 年
        int monthOfYear = calendar.get(Calendar.MONTH); // 月
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // 日

        nowCale.set(0,monthOfYear,dayOfMonth,0,0);

        Log.d("aaa",String.valueOf(d));

        //読み込み（,デフォ値）
        y = prefer.getInt("year", year);
        m = prefer.getInt("month", monthOfYear);
        d = prefer.getInt("day", dayOfMonth);

        ////////////////////////////////////////


        // 日付設定時のリスナ作成
        //ok押した時の処理
        DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener() {
            //okボタンを押したときの年月日を取得できる
            public void onDateSet(android.widget.DatePicker datePicker, int year,
                                  int monthOfYear, int dayOfMonth) {


                //押されてる日時を変数へ
                y=year;
                m=monthOfYear;
                d=dayOfMonth;

                birthCale.set(0,m,d,0,0);

                        //書き込み準備
                        editor = prefer.edit();
                        editor.putInt("year", y);
                        editor.putInt("month", m);
                        editor.putInt("day", d);
                        //書き込み実行
                        editor.commit();

                rema=getDiffDays(birthCale,nowCale);

                Log.d("bbbb",String.valueOf(rema));


                if (rema==0) {
                    TV.setText("今日です");
                }
                else
                {
                    TV.setText("あと"+rema+"日です");
                }



            }
        };

        // 日付設定ダイアログの作成・リスナの登録
        datePickerDialog = new DatePickerDialog(this, DateSetListener, y, m, d);

        // 日付設定ダイアログの表示
        datePickerDialog.show();

    }


    int getDiffDays(Calendar calendar1, Calendar calendar2) {
        //==== ミリ秒単位での差分算出 ====//
        long diffTime = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();

        //==== 日単位に変換 ====//
        int MILLIS_OF_DAY = 1000 * 60 * 60 * 24;
        int diffDays = (int)(diffTime / MILLIS_OF_DAY);

        return diffDays;
    }




}
