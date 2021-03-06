package com.example.connectivityschedule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connectivityschedule.R;
import com.example.connectivityschedule.myclasses.Alarm;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Alarm> mAlarms;
    private MyRecyclerViewActionListener myRecyclerViewActionListener;

    public RecyclerViewAdapter(ArrayList<Alarm> alarms, MyRecyclerViewActionListener listener) {
        this.mAlarms = alarms;
        this.myRecyclerViewActionListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View alarmView = inflater.inflate(R.layout.recycle_layout_item2, parent, false);
        ViewHolder viewHolder = new ViewHolder(alarmView, myRecyclerViewActionListener);

        return viewHolder;

    }   

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alarm alarm = mAlarms.get(position);

        TextView mTimeTextView = holder.TimeTv;
        mTimeTextView.setText(alarm.getTime());

        Switch mSwitch = holder.ActivateSw;
        mSwitch.setChecked(alarm.isActive());

        Spinner mSpinner = holder.ActionSpnr;
        mSpinner.setSelection(alarm.getAction());
    }

    @Override
    public int getItemCount() {
        return mAlarms.size();
    }

    @Override
    public long getItemId(int position) {
        return mAlarms.get(position).getAlarmID();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        Switch ActivateSw;
        Spinner ActionSpnr;
        TextView PrepTv;
        TextView TimeTv;


        public ViewHolder(View itemView, final MyRecyclerViewActionListener holderListener){
            super(itemView);
            ActivateSw = itemView.findViewById(R.id.alarm_switch);
            ActionSpnr = itemView.findViewById(R.id.alarm_spinner);
            PrepTv = itemView.findViewById(R.id.preposition_tv);
            TimeTv = itemView.findViewById(R.id.time_tv);

            TimeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holderListener.onTimeClickListener(getAdapterPosition());
                }
            });

            ActionSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    holderListener.onSpinnerOptionSelected(getAdapterPosition(), position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ActivateSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isPressed()) {
                        holderListener.onSwitchChanged(getAdapterPosition(), isChecked);
                    }

                }
            });


        }
    }

    public interface MyRecyclerViewActionListener{

        void onTimeClickListener(int itemPosition);
        void onSwitchChanged(int itemPosition, boolean isChecked);
        void onSpinnerOptionSelected(int itemPosition, int option);
    }

}
