package com.example.sensorapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SensorDetailsActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private TextView temperatureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_details);

        temperatureTextView = findViewById(R.id.sensor_value);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        int sensorType = getIntent().getIntExtra("sensorType", -1);

        if (sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            if (temperatureSensor != null) {
                sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                temperatureTextView.setText("Sensor temperatury niedostępny");
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float temperature = event.values[0];
            temperatureTextView.setText(String.format("Temperatura: %.1f °C", temperature));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nie wymaga implementacji
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
