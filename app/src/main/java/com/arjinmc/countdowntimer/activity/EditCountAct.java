package com.arjinmc.countdowntimer.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.arjinmc.countdowntimer.R;
import com.arjinmc.countdowntimer.database.DataBaseHelper;
import com.arjinmc.countdowntimer.databinding.EditCountActBinding;
import com.arjinmc.countdowntimer.pojo.MussEvent;
import com.arjinmc.countdowntimer.util.Constant;
import com.arjinmc.countdowntimer.util.EventBusEv;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Yusuf on 2016/8/11.
 */
public class EditCountAct extends FragmentActivity {

    EditCountActBinding binding;
    private DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
    private MussEvent mussEvent;


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getData(EventBusEv ev) {
        if (EventBusEv.is(ev, Constant.COUNT_EDIT_DATA)) {
            mussEvent = (MussEvent) ev.getData();
            EventBus.getDefault().removeStickyEvent(ev);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        binding = DataBindingUtil.setContentView(this, R.layout.edit_count_act);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        binding.title.setText("Edit");
        binding.name.setText(mussEvent.getName());
        binding.limit.setText(String.valueOf(mussEvent.getLimit()));
    }

    @OnClick(R.id.add_event)
    public void setAddEvent(View view) {
        String name = binding.name.getText().toString();
        String limit = binding.limit.getText().toString();
        if (name.isEmpty() || limit.isEmpty()) {
            Toast.makeText(this, "please fill blanket", Toast.LENGTH_SHORT).show();
        } else {
            dataBaseHelper.updateEvents(mussEvent.getId(), name, limit);
            Toast.makeText(this, "edit data success", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }


    @Override
    public void onBackPressed() {
        addCountActBack();
        super.onBackPressed();
    }

    public void addCountActBack() {
        EventBus.getDefault().post(new EventBusEv(Constant.REFRESH_MAIN_RECYCLERVIEW, null));
        EventBus.getDefault().post(new EventBusEv(Constant.CLOSE_COUNT_DATA, null));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
