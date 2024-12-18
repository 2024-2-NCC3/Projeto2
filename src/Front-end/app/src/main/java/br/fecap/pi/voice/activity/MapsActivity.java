package br.fecap.pi.voice.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import java.util.List;

import br.fecap.pi.voice.DataBaseManager;
import br.fecap.pi.voice.R;
import br.fecap.pi.voice.databinding.ActivityMapsBinding;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private DataBaseManager dataBaseManager;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dataBaseManager = new DataBaseManager(this);
        frequentedRegions();

        // Configurar o FloatingActionButton para ir para a Home
        FloatingActionButton fab = findViewById(R.id.floating_button);
        fab.setOnClickListener(view -> {
            Log.d("MapsActivity", "Navigating to Home");
            startActivity(new Intent(MapsActivity.this, MainActivity.class));
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng saoPaulo = new LatLng(-23.550520, -46.633308);
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(saoPaulo, 2));
        addHeatmap();
    }

    private void addHeatmap() {
        dataBaseManager.fetchHeatmapData(new DataBaseManager.DataCallback<List<WeightedLatLng>>() {
            @Override
            public void onSuccess(List<WeightedLatLng> heatmapData) {
                if (heatmapData != null && !heatmapData.isEmpty()) {
                    HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                            .weightedData(heatmapData)
                            .build();
                    mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
                } else {
                    Log.e("MapsActivity", "Nenhum ponto de entrada válido para o mapa de calor.");
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("MapsActivity", "Erro ao carregar dados do mapa de calor", e);
            }
        });
    }

    private void frequentedRegions() {
        dataBaseManager.fetchMostFrequentRegions(new DataBaseManager.DataCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> regions) {
                if (regions.size() >= 3) {
                    TextView mostFrequent1 = findViewById(R.id.mostFrequent1);
                    TextView mostFrequent2 = findViewById(R.id.mostFrequent2);
                    TextView mostFrequent3 = findViewById(R.id.mostFrequent3);

                    mostFrequent1.setText("Região 1: " + regions.get(0));
                    mostFrequent2.setText("Região 2: " + regions.get(1));
                    mostFrequent3.setText("Região 3: " + regions.get(2));

                    Log.d("frequentedRegions", "Regiões mais frequentes: " + regions);
                } else {
                    Log.d("frequentedRegions", "Menos de três regiões encontradas.");
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("frequentedRegions", "Erro ao buscar regiões mais frequentes", e);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Log.e("MapsActivity", "Permissão de localização não concedida.");
        }
    }
}
