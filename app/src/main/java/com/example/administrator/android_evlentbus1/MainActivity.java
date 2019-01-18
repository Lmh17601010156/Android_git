package com.example.administrator.android_evlentbus1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_click1;
    private Button btn_click2;
    private Button btn_click3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_click1 = (Button) findViewById(R.id.btn_click1);
        btn_click2 = (Button) findViewById(R.id.btn_click2);
        btn_click3 = (Button) findViewById(R.id.btn_click3);

        btn_click1.setOnClickListener(this);
        btn_click2.setOnClickListener(this);
        btn_click3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_click1:
                clickElvent();
                break;
            case R.id.btn_click2:
                click2Elvent();
                break;
            case R.id.btn_click3:
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().isRegistered(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void clickElvent() {
        // 获取当前线程的姓名
        String threadName = Thread.currentThread().getName();
        UserBean user = new UserBean();
        user.threadName = threadName;
        user.data = "abc";
        EventBus.getDefault().post(user);
    }

    private void click2Elvent() {
        // 子线程发送事件
        new Thread(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                UserBean user = new UserBean();
                user.threadName = threadName;
                user.data = "abc";
                EventBus.getDefault().postSticky(user);
            }
        }).start();
    }

    public void onMessageEvent(UserBean user){
        String threadName = Thread.currentThread().getName();
        Log.i("userBean",user.toString() + "thread:" + threadName);
    }
}
