package com.arjinmc.countdowntimer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arjinmc.countdowntimer.coundown_util.CountDownTimerListener;
import com.arjinmc.countdowntimer.coundown_util.CountDownTimerService;
import com.arjinmc.countdowntimer.coundown_util.CountDownTimerUtil;
import com.arjinmc.countdowntimer.R;
import com.arjinmc.countdowntimer.pojo.MussEvent;
import com.arjinmc.countdowntimer.util.CommonUtil;
import com.arjinmc.countdowntimer.util.Constant;
import com.arjinmc.countdowntimer.util.EventBusEv;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountAct extends FragmentActivity implements View.OnClickListener {


    //service countdown
    private Button btnServiceStart;
    private Button btnServiceStop;
    private TextView tvServiceTime;

    private long timer_unit = 1000;
    private long service_distination_total;

    private int timerStatus = CountDownTimerUtil.PREPARE;

    private MussEvent mussEvent;


    private CountDownTimerService countDownTimerService;
    private TextView title;

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getData(EventBusEv ev) {
        if (EventBusEv.is(ev, Constant.COUNT_DATA)) {
            mussEvent = (MussEvent) ev.getData();
            service_distination_total = timer_unit * mussEvent.getLimit();
            EventBus.getDefault().removeStickyEvent(ev);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCount(EventBusEv ev) {
        if (EventBusEv.is(ev, Constant.CLOSE_COUNT_DATA)) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_act);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initTimerStatus();

        btnServiceStart = (Button) findViewById(R.id.btn_start2);
        btnServiceStop = (Button) findViewById(R.id.btn_stop2);
        tvServiceTime = (TextView) findViewById(R.id.tv_time2);
        title = (TextView) findViewById(R.id.title);
        title.setText(mussEvent.getName());
        btnServiceStart.setOnClickListener(this);
        btnServiceStop.setOnClickListener(this);
        countDownTimerService = CountDownTimerService.getInstance(new MyCountDownLisener()
                , service_distination_total);

        initServiceCountDownTimerStatus();

    }

    @OnClick(R.id.edit)
    public void edit(View view) {

        EventBus.getDefault().postSticky(new EventBusEv(Constant.COUNT_EDIT_DATA, new MussEvent(mussEvent.getId(), mussEvent.getName(), mussEvent.getCurrentLeftTime(), mussEvent.getLimit(), mussEvent.isRunning())));
        ActivitySwitcher.editCount(this);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    tvServiceTime.setText(CommonUtil.formateTimer(countDownTimerService.getCountingTime()));
                    if (countDownTimerService.getTimerStatus() == CountDownTimerUtil.PREPARE) {
                        btnServiceStart.setText("START");
                    }
                    break;
            }
        }
    };

    private class MyCountDownLisener implements CountDownTimerListener {

        @Override
        public void onChange() {
            mHandler.sendEmptyMessage(2);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start2:
                switch (countDownTimerService.getTimerStatus()) {
                    case CountDownTimerUtil.PREPARE:
                        countDownTimerService.startCountDown();
                        btnServiceStart.setText("PAUSE");
                        break;
                    case CountDownTimerUtil.START:
                        countDownTimerService.pauseCountDown();
                        btnServiceStart.setText("RESUME");
                        break;
                    case CountDownTimerUtil.PASUSE:
                        countDownTimerService.startCountDown();
                        btnServiceStart.setText("PAUSE");
                        break;
                }
                break;
            case R.id.btn_stop2:
                btnServiceStart.setText("START");
                countDownTimerService.stopCountDown();
                break;
        }
    }

    /**
     * init timer status
     */
    private void initTimerStatus() {
        timerStatus = CountDownTimerUtil.PREPARE;
    }


    /**
     * init countdowntimer buttons status for servce
     */
    private void initServiceCountDownTimerStatus() {
        switch (countDownTimerService.getTimerStatus()) {
            case CountDownTimerUtil.PREPARE:
                btnServiceStart.setText("START");
                break;
            case CountDownTimerUtil.START:
                btnServiceStart.setText("PAUSE");
                break;
            case CountDownTimerUtil.PASUSE:
                btnServiceStart.setText("RESUME");
                break;
        }
        tvServiceTime.setText(CommonUtil.formateTimer(countDownTimerService.getCountingTime()));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

