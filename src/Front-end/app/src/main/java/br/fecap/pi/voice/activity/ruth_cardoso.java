package br.fecap.pi.voice.activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.fecap.pi.voice.R;
import br.fecap.pi.voice.adapter.Adapter;
import br.fecap.pi.voice.model.Noticia;

import java.util.ArrayList;
import java.util.List;

public class
ruth_cardoso extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Noticia> listaNoticia = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ruth_cardoso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Inicializar o RecyclerView
        recyclerView = findViewById(R.id.newsRecyclerView);

        // Criar a lista de notícias
        this.criarNoticias();

        // Configurar o Adapter com a lista de notícias
        Adapter adapter = new Adapter(this, listaNoticia);

        // Configurar o RecyclerView com um LayoutManager e o Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    // Método para criar exemplos de notícias
    private void criarNoticias() {
        Noticia noticia;

        noticia = new Noticia("https://www.save-free.com/cdn/https://scontent-sin2-1.cdninstagram.com/v/t51.29350-15/466803552_1751955152229960_1634597539392743335_n.jpg?stp=dst-jpg_e35_tt7&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xMDgweDEwODAuc2RyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-sin2-1.cdninstagram.com&_nc_cat=100&_nc_ohc=DmHum3BC2Q8Q7kNvgGYtPiY&_nc_gid=10da4dd1f0294ef780a3a0eec2bc847e&edm=ANTKIIoBAAAA&ccb=7-5&oh=00_AYBtyYf2EQXTHcnmh3y_-gUbjWMCHCNSgRlSww5tyEO8JQ&oe=673C5E13&_nc_sid=d885a2", "AFRO DAY 2024", "13/11/2024", "https://gazetadasemana.com.br/noticia/200609/afroday-2024-cultura-afro-brasileira-em-destaque-no-teatro-fecap#google_vignette");
        listaNoticia.add(noticia);

        noticia = new Noticia("https://www.save-free.com/cdn/https://scontent-fra3-1.cdninstagram.com/v/t51.29350-15/387271119_2629011913959927_1666745883246206419_n.jpg?stp=dst-jpg_e15_fr_s1080x1080_tt7&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xMDgweDEwODAuc2RyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-fra3-1.cdninstagram.com&_nc_cat=103&_nc_ohc=lqCZZKyPvNIQ7kNvgEy-6kY&_nc_gid=85e011578aee415db40faf85483110e5&edm=ANTKIIoBAAAA&ccb=7-5&oh=00_AYDwxXrH-wiZNoJCDG7_pz2du7TWlgkniajCjWbEQIo25g&oe=673C4C82&_nc_sid=d885a2", "Missão / Visão / Valores", "02/01/2024", "https://www.instagram.com/p/CyPLjCythq2/?igsh=MWg2dDFrcXhubGc5Mw==");
        listaNoticia.add(noticia);

        // Adicione mais notícias conforme necessário


        Button whats = findViewById(R.id.btn_whats);
        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chat.whatsapp.com/LLIWaCdVEbkFAacmu3pUUJ"));
                startActivity(intent);
                finish();
            }
        });

        Button insta = findViewById(R.id.btn_insta);
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/coletivo.ruthc/"));
                startActivity(intent);
                finish();
            }
        });

//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            startActivity(new Intent(ruth_cardoso.this, MainActivity.class));
            return true;
        } else if (itemId == R.id.nav_warning) {
            startActivity(new Intent(ruth_cardoso.this, Complaint.class));
            return true;
        } else {
            return false;
        }
    }
    Intent intent = new Intent(ruth_cardoso.this, fecafro.class);
}