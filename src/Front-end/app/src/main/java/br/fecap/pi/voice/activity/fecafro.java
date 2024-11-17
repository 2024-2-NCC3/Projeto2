package br.fecap.pi.voice.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.fecap.pi.voice.R;
import br.fecap.pi.voice.adapter.Adapter;
import br.fecap.pi.voice.model.Noticia;

import java.util.ArrayList;
import java.util.List;

public class fecafro extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Noticia> listaNoticia = new ArrayList<>();
    private Button mainButton, complaintButton, fecafroButton;
    private boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecafro);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide(); // Remove a ActionBar
        }

        // Ajuste de insets para as barras do sistema
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

        // Botão para WhatsApp
        Button whats = findViewById(R.id.btn_whats);
        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chat.whatsapp.com/LLIWaCdVEbkFAacmu3pUUJ"));
                startActivity(intent);
                finish();
            }
        });

        // Botão para Instagram
        Button insta = findViewById(R.id.btn_insta);
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/Fecafro"));
                startActivity(intent);
                finish();
            }
        });

        // Configurar o FloatingActionButton e os botões adicionais
        FloatingActionButton fab = findViewById(R.id.floating_button);
        mainButton = findViewById(R.id.main_button);
        complaintButton = findViewById(R.id.denuncia_button);
        fecafroButton = findViewById(R.id.ruth_button);

        // Ocultar os botões inicialmente
        mainButton.setVisibility(View.GONE);
        complaintButton.setVisibility(View.GONE);
        fecafroButton.setVisibility(View.GONE);

        // Configurar o clique no FAB para mostrar/ocultar os botões
        fab.setOnClickListener(view -> {
            if (isFabOpen) {
                closeFabMenu();
            } else {
                openFabMenu();
            }
        });

        // Configurar os cliques nos botões para navegar para as atividades apropriadas
        mainButton.setOnClickListener(view -> {
            startActivity(new Intent(fecafro.this, MainActivity.class));
        });

        complaintButton.setOnClickListener(view -> {
            startActivity(new Intent(fecafro.this, Complaint.class));
        });

        fecafroButton.setOnClickListener(view -> {
            startActivity(new Intent(fecafro.this, ruth_cardoso.class));
        });
    }

    // Método para abrir o menu de botões com espaçamento ajustado
    private void openFabMenu() {
        mainButton.setVisibility(View.VISIBLE);
        complaintButton.setVisibility(View.VISIBLE);
        fecafroButton.setVisibility(View.VISIBLE);

        // Ajuste o espaçamento vertical dos botões
        mainButton.setTranslationY(-350f); // Distância maior
        complaintButton.setTranslationY(-250f); // Distância intermediária
        fecafroButton.setTranslationY(-150f); // Distância menor

        isFabOpen = true;
    }

    // Método para fechar o menu de botões
    private void closeFabMenu() {
        mainButton.setVisibility(View.GONE);
        complaintButton.setVisibility(View.GONE);
        fecafroButton.setVisibility(View.GONE);
        isFabOpen = false;
    }

    // Método para criar exemplos de notícias
    private void criarNoticias() {
        Noticia noticia;

        noticia = new Noticia("https://www.save-free.com/cdn/https://scontent-sin2-1.cdninstagram.com/v/t51.29350-15/466803552_1751955152229960_1634597539392743335_n.jpg?stp=dst-jpg_e35_tt7&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xMDgweDEwODAuc2RyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-sin2-1.cdninstagram.com&_nc_cat=100&_nc_ohc=DmHum3BC2Q8Q7kNvgGYtPiY&_nc_gid=10da4dd1f0294ef780a3a0eec2bc847e&edm=ANTKIIoBAAAA&ccb=7-5&oh=00_AYBtyYf2EQXTHcnmh3y_-gUbjWMCHCNSgRlSww5tyEO8JQ&oe=673C5E13&_nc_sid=d885a2", "AFRO DAY 2024", "13/11/2024", "https://gazetadasemana.com.br/noticia/200609/afroday-2024-cultura-afro-brasileira-em-destaque-no-teatro-fecap#google_vignette");
        listaNoticia.add(noticia);

        noticia = new Noticia("https://www.save-free.com/cdn/https://scontent-fra3-1.cdninstagram.com/v/t51.29350-15/387271119_2629011913959927_1666745883246206419_n.jpg?stp=dst-jpg_e15_fr_s1080x1080_tt7&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xMDgweDEwODAuc2RyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-fra3-1.cdninstagram.com&_nc_cat=103&_nc_ohc=lqCZZKyPvNIQ7kNvgEy-6kY&_nc_gid=85e011578aee415db40faf85483110e5&edm=ANTKIIoBAAAA&ccb=7-5&oh=00_AYDwxXrH-wiZNoJCDG7_pz2du7TWlgkniajCjWbEQIo25g&oe=673C4C82&_nc_sid=d885a2", "Missão / Visão / Valores", "02/01/2024", "https://www.instagram.com/p/CyPLjCythq2/?igsh=MWg2dDFrcXhubGc5Mw==");
        listaNoticia.add(noticia);

        // Adicione mais notícias conforme necessário

    }
}

