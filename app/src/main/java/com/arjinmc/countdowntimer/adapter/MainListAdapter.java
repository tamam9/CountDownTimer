package com.arjinmc.countdowntimer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arjinmc.countdowntimer.R;
import com.arjinmc.countdowntimer.activity.ActivitySwitcher;
import com.arjinmc.countdowntimer.activity.MainActivity;
import com.arjinmc.countdowntimer.database.DataBaseHelper;
import com.arjinmc.countdowntimer.pojo.MussEvent;
import com.arjinmc.countdowntimer.util.CommonUtil;
import com.arjinmc.countdowntimer.util.Constant;
import com.arjinmc.countdowntimer.util.EventBusEv;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Yusuf on 2016/8/10.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {


    private final ArrayList<MussEvent> list;
    private final MainActivity activity;
    private long timer_unit = 1000;

    DataBaseHelper databaseHelper;

    public MainListAdapter(ArrayList<MussEvent> list, MainActivity mainActivity) {
        this.list = list;
        activity = mainActivity;
        databaseHelper = new DataBaseHelper(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.main_list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int id = list.get(position).getId();
        int currentLeftTime = list.get(position).getCurrentLeftTime();
        int limit = list.get(position).getLimit();
        int running = list.get(position).isRunning();
        holder.id.setText(String.valueOf(id));
        holder.name.setText(list.get(position).getName());
        holder.currentLeftTime.setText(String.valueOf(currentLeftTime));
        holder.limit.setText(CommonUtil.formateTimer(limit*timer_unit));
        holder.running.setText(String.valueOf(running));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                databaseHelper.deleteEvent(list.get(position));
                Toast.makeText(activity, "delete Success", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new EventBusEv(Constant.REFRESH_MAIN_RECYCLERVIEW, null));

                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new EventBusEv(Constant.COUNT_DATA, list.get(position)));
                ActivitySwitcher.countAct(activity);

            }
        });
    }




    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        TextView currentLeftTime;
        TextView limit;
        TextView running;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            name = (TextView) itemView.findViewById(R.id.name);
            currentLeftTime = (TextView) itemView.findViewById(R.id.current_left_time);
            limit = (TextView) itemView.findViewById(R.id.limit);
            running = (TextView) itemView.findViewById(R.id.running);

        }
    }
}
