package com.example.lenovo.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.txusballesteros.SnakeView;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;


public class Monitoring extends AppCompatActivity {
    Button pause, keep,back;
    TextView heartbeat, systolicPressure, diastolicPressure;
    EditText PhoneNumber;
    ImageView communication;
    SnakeView heartbeat_snake, systolicPressure_snake, diastolicPressure_snake;
    private int record_Heartbeat, record_systolicPressure, record_diastolicPressure;
    private int count_hignHeartbeat = 0, count_lowHeartbeat = 0, count_highPressure = 0, count_lowPressure = 0;
    User user = null;
    int i=0;
    static Context mContext;
    private Timer timer = null;
    private TimerTask timerTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        heartbeat = (TextView) findViewById(R.id.heartbeat);
        systolicPressure = (TextView) findViewById(R.id.systolicPressure);
        diastolicPressure = (TextView) findViewById(R.id.diastolicPressure);

        heartbeat_snake = (SnakeView) findViewById(R.id.heartbeat_snake);
        systolicPressure_snake = (SnakeView) findViewById(R.id.systolicPressure_snake);
        diastolicPressure_snake = (SnakeView) findViewById(R.id.diastolicPressure_snake);

        pause = (Button) findViewById(R.id.pause);
        keep = (Button) findViewById(R.id.keep);
        back = (Button) findViewById(R.id.back);

        PhoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        communication = (ImageView) findViewById(R.id.communication);

        mContext = Monitoring.this;

        heartbeat_snake.setMinValue(49);
        heartbeat_snake.setMaxValue(111);

        systolicPressure_snake.setMinValue(79);
        systolicPressure_snake.setMaxValue(146);

        diastolicPressure_snake.setMinValue(54);
        diastolicPressure_snake.setMaxValue(96);
        pause.setOnClickListener(new pauseOnClick());
        keep.setOnClickListener(new keepOnClick());
        back.setOnClickListener(new backOnClick());
        communication.setOnClickListener(new communicationOnClick());
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int n = 0;
            record_Heartbeat = Integer.parseInt(user.getHeartbeat());
            record_systolicPressure = Integer.parseInt(user.getSystolicPressure());
            record_diastolicPressure = Integer.parseInt(user.getDiastolicPressure());
            heartbeat.setText(" 心 率 : " + record_Heartbeat);
            systolicPressure.setText(" 高 压 : " + record_systolicPressure);
            diastolicPressure.setText(" 低 压 : " + record_diastolicPressure);
            heartbeat_snake.addValue(record_Heartbeat);
            systolicPressure_snake.addValue(record_systolicPressure);
            diastolicPressure_snake.addValue(record_diastolicPressure);
            if (record_Heartbeat < 60) {
                count_lowHeartbeat++;
                n = 1;
            }
            if (record_Heartbeat > 100) {
                count_hignHeartbeat++;
                n = 1;
            }
            if (record_systolicPressure < 90 || record_diastolicPressure < 60) {
                count_lowPressure++;
                n = 1;
            }

            if (record_systolicPressure > 140 || record_diastolicPressure > 90) {
                count_highPressure++;
                n = 1;
            }
            if (n == 1) {
                Toast.makeText(mContext, "当前出现\n" + count_lowHeartbeat + "次心率过低的情况,\n" + count_hignHeartbeat + "次心率过高的情况,\n" + count_lowPressure + "次血压过低的情况\n" + count_highPressure + "次血压过高的情况,\n请注意行车安全", Toast.LENGTH_SHORT).show();
            }
            if (!PhoneNumber.getText().toString().isEmpty()) {
                if (count_hignHeartbeat > 5 || count_lowHeartbeat > 5 || count_highPressure > 5 || count_lowPressure > 5) {
                    count_hignHeartbeat = 0;
                    count_lowHeartbeat = 0;
                    count_highPressure = 0;
                    count_lowPressure = 0;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + PhoneNumber.getText().toString()));
                            startActivity(intent);
                            timer.cancel();

                        }

                    }).start();
                    PhoneNumber.setText("");
                }
            }
            random_number();
        }
    };

    public void random_number() {
        timer = new Timer();
        timerTask = new TimerTask() {

            @Override
            public void run() {
                user = new User( nub(1,100),nub(36.3, 45.0), nub(40.0, 300.0), nub(50, 110), nub(80, 145), nub(55, 95), nub(2.50, 5.50));
                Message message = mHandler.obtainMessage();
                message.obj = user;
                mHandler.sendMessage(message);
            }
        };

        timer.schedule(timerTask, 2000);
    }

    public void stopTime() {
        if (timer != null) {
            i=0;
            Toast.makeText(mContext, "停止实时监测", Toast.LENGTH_SHORT).show();
            timer.cancel();
        }
    }

    public String nub(double min, double max) {//输出1位小数点随机数
        BigDecimal db = new BigDecimal(Math.random() * (max - min) + min);
        return db.setScale(1, BigDecimal.ROUND_HALF_UP).toString();
    }

    public String nub(int min, int max) {//输出整数随机数
        BigDecimal db = new BigDecimal(Math.random() * (max - min) + min);
        return db.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    }

    public String nub(float min, float max) {//输出2位小数点随机数
        BigDecimal db = new BigDecimal(Math.random() * (max - min) + min);
        return db.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    class keepOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (i==0) {
                i=1;
                random_number();
            } else {
                Toast.makeText(mContext, "请不要重复点击", Toast.LENGTH_SHORT).show();
            }

        }
    }

    class pauseOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            stopTime();
        }
    }

    private class communicationOnClick implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.Contacts.CONTENT_URI);
            Monitoring.this.startActivityForResult(intent, 1);

        }
    }
//获取联系人电话号码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor cursor = managedQuery(contactData, null, null, null,
                            null);
                    cursor.moveToFirst();
                    String num = this.getContactPhone(cursor);
                    PhoneNumber.setText(num);
                }
                break;

            default:
                break;
        }
    }

    private String getContactPhone(Cursor cursor) {
        // TODO Auto-generated method stub
        int phoneColumn = cursor
                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String result = "";
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人电话的cursor
            Cursor phone = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + contactId, null, null);
            if (phone.moveToFirst()) {
                for (; !phone.isAfterLast(); phone.moveToNext()) {
                    int index = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String phoneNumber = phone.getString(index);
                    result = phoneNumber;

                }
                if (!phone.isClosed()) {
                    phone.close();
                }
            }
        }
        return result;
    }



class backOnClick implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if(timer!=null) {
            stopTime();
        }
        Intent intent = getIntent();
        setResult(1,intent);

        final String name = intent.getStringExtra("name");
        intent =new Intent(Monitoring.this,ContentActivity.class);
        intent.putExtra("name",name);
        startActivityForResult(intent,1);
        finish();
    }
}
    @Override
    public void onBackPressed() {
        if(timer!=null) {
            stopTime();
        }
        Intent intent = getIntent();
        setResult(1,intent);

        final String name = intent.getStringExtra("name");

        intent =new Intent(Monitoring.this,ContentActivity.class);
        intent.putExtra("name",name);
        startActivityForResult(intent,1);
        finish();
    }

}

