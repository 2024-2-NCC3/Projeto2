package br.fecap.pi.voice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.fecap.pi.voice.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Notice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            startActivity(new Intent(Notice.this, MainActivity.class));
            return true;
        } else if (itemId == R.id.nav_warning) {
            startActivity(new Intent(Notice.this, Complaint.class));
            return true;
        } else if (itemId == R.id.nav_image) {
            startActivity(new Intent(Notice.this, Notice.class));
            return true;
        } else {
            return false;
        }
    }

}