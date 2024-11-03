package com.example.sensorapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private TextView locationTextView;
    private TextView addressTextView;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationTextView = findViewById(R.id.location_text);
        addressTextView = findViewById(R.id.address_text);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Button getLocationButton = findViewById(R.id.get_location_button);
        getLocationButton.setOnClickListener(v -> getLocation());

        Button getAddressButton = findViewById(R.id.button_address);
        getAddressButton.setOnClickListener(v -> getAddress());

        // Sprawdzamy uprawnienia przy starcie aplikacji
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        }
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION_PERMISSION);
    }

    private void getLocation() {
        // Sprawdzamy czy mamy uprawnienia do lokalizacji
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    locationTextView.setText(String.format("Szerokość: %.4f\nDługość: %.4f\nCzas: %s",
                            location.getLatitude(), location.getLongitude(), location.getTime()));
                } else {
                    locationTextView.setText("Nie można uzyskać lokalizacji");
                }
            }).addOnFailureListener(e -> {
                locationTextView.setText("Błąd uzyskiwania lokalizacji");
                e.printStackTrace();
            });
        } else {
            requestLocationPermission();
        }
    }

    private void getAddress() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),
                                location.getLongitude(),
                                1);
                        if (addresses != null && !addresses.isEmpty()) {
                            Address address = addresses.get(0);
                            addressTextView.setText("Adres: " + address.getAddressLine(0));
                        } else {
                            addressTextView.setText("Adres niedostępny");
                        }
                    } catch (IOException e) {
                        addressTextView.setText("Błąd geokodowania");
                        e.printStackTrace();
                    }
                } else {
                    addressTextView.setText("Nie można uzyskać lokalizacji");
                }
            }).addOnFailureListener(e -> {
                addressTextView.setText("Błąd uzyskiwania lokalizacji");
                e.printStackTrace();
            });
        } else {
            Toast.makeText(this, "Brak uprawnień do lokalizacji", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Uprawnienia do lokalizacji przyznane", Toast.LENGTH_SHORT).show();
                getLocation();
            } else {
                Toast.makeText(this, "Uprawnienia do lokalizacji odrzucone", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
