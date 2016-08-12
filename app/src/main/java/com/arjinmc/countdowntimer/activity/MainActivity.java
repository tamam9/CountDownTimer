package com.arjinmc.countdowntimer.activity;

import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arjinmc.countdowntimer.R;
import com.arjinmc.countdowntimer.adapter.MainListAdapter;
import com.arjinmc.countdowntimer.database.DataBaseHelper;
import com.arjinmc.countdowntimer.databinding.MainActBinding;
import com.arjinmc.countdowntimer.pojo.MussEvent;
import com.arjinmc.countdowntimer.util.Constant;
import com.arjinmc.countdowntimer.util.EventBusEv;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Yusuf on 2016/8/9.
 */
public class MainActivity extends FragmentActivity {

    MainActBinding mainActBinding;
    private DataBaseHelper dbHelper;
    private SQLiteDatabase readableDatabase;
    private DataBaseHelper dataBaseHelper = new DataBaseHelper(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mainActBinding = DataBindingUtil.setContentView(this, R.layout.main_act);
        ButterKnife.bind(this);
        mainActBinding.executePendingBindings();
        init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLoginInfo(EventBusEv ev) {
        if (EventBusEv.is(ev, Constant.REFRESH_MAIN_RECYCLERVIEW)) {
            init();

        }
    }

    private ArrayList<MussEvent> loadData() {
        return (ArrayList<MussEvent>) dataBaseHelper.getAllEvents();
    }

    private void init() {

        MainListAdapter mainListAdapter = new MainListAdapter(loadData(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mainActBinding.mainRecyclerview.setLayoutManager(linearLayoutManager);

        mainActBinding.mainRecyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);

            }
        });
        mainActBinding.mainRecyclerview.setAdapter(mainListAdapter);

    }


    @OnClick(R.id.deleteAll)
    public void setDelete(View view) {
        dataBaseHelper.deleteAllEvents();
        init();
    }


    @OnClick(R.id.add_event)
    public void setAddEvent(View view) {
        ActivitySwitcher.addCount(this);
    }


    private void likeActionBar() {
        //        mainToolbar.setNavigationIcon(R.mipmap.ic_launcher);
//        mainToolbar.setTitle("title");
//        mainToolbar.setSubtitle("subTitle");
//        View.OnCreateContextMenuListener onCreateContextMenuListener = new View.OnCreateContextMenuListener() {
//            @Override
//            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                getMenuInflater().inflate(R.menu.menu_main, menu);
//
//            }
//        };
//        mainToolbar.setOnCreateContextMenuListener(onCreateContextMenuListener);
//        mainToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_LONG).show();
//                return true;
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
