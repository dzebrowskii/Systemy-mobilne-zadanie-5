package com.example.sensorapp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SensorActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private List<Sensor> sensorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        RecyclerView recyclerView = findViewById(R.id.sensor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SensorAdapter(sensorList, this::onSensorClick));
    }

    private void onSensorClick(Sensor sensor) {
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            startActivity(new Intent(this, AccelerometerActivity.class));
        } else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            startActivity(new Intent(this, LocationActivity.class));
        } else {
            Toast.makeText(this, "Sensor nieobsługiwany w tej aplikacji", Toast.LENGTH_SHORT).show();
        }
    }
}
