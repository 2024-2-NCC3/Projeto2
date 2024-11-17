package br.fecap.pi.voice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;




public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        SplashScreen.installSplashScreen(this);



        setContentView(R.layout.activity_main);


        //Botões adicionados
        Button denuncia = findViewById(R.id.button_warning);
        denuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Complaint.class);
                startActivity(intent);
                finish();
            }
        });

        Button fecafro = findViewById(R.id.btn_fecafro);
        fecafro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, fecafro.class);
                startActivity(intent);
                finish();
            }
        });

        Button ruthCardoso = findViewById(R.id.btn_ruth);
        ruthCardoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ruth_cardoso.class);
                startActivity(intent);
                finish();
            }
        });

        Button crpir = findViewById(R.id.btn_crpir);
        crpir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=5511974318628"));
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

}