package com.example.sensorapp;

import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorHolder> {

    private final List<Sensor> sensorList;
    private final SensorClickListener sensorClickListener;

    public SensorAdapter(List<Sensor> sensorList, SensorClickListener sensorClickListener) {
        this.sensorList = sensorList;
        this.sensorClickListener = sensorClickListener;
    }

    @NonNull
    @Override
    public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_list_item, parent, false);
        return new SensorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorHolder holder, int position) {
        Sensor sensor = sensorList.get(position);
        holder.bind(sensor);
        holder.itemView.setOnClickListener(v -> sensorClickListener.onSensorClick(sensor));

        // Wyróżnienie tylko dla akcelerometru i lokalizacji (symulowanej przez TYPE_MAGNETIC_FIELD)
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER || sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            holder.itemView.setBackgroundColor(0xFFFFE082); // Żółte wyróżnienie
        } else {
            holder.itemView.setBackgroundColor(0xFFFFFFFF); // Domyślny biały
        }
    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }

    static class SensorHolder extends RecyclerView.ViewHolder {
        private final TextView sensorNameTextView;

        public SensorHolder(@NonNull View itemView) {
            super(itemView);
            sensorNameTextView = itemView.findViewById(R.id.sensor_name);
        }

        public void bind(Sensor sensor) {
            sensorNameTextView.setText(sensor.getName());
        }
    }

    public interface SensorClickListener {
        void onSensorClick(Sensor sensor);
    }
}
