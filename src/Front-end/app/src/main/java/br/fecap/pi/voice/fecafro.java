package br.fecap.pi.voice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.fecap.pi.voice.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.bumptech.glide.Glide;

public class fecafro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fecafro);

       // Carregar imagem usando Glide
//        ImageView imageView = findViewById(R.id.image_teste);
//        String imageUrl = "https://edufecap-my.sharepoint.com/_vti_bin/afdcache.ashx/authitem/personal/santos_flavio_edu_fecap_br/SiteAssets/SitePages/AfroDay/1033391579-1.jpeg?_oat_=1730895392_0892973f810526b780290fa156ddfbe809d4540292951f5b90814ff2e27d6b9a&P1=1730862995&P2=-149452251&P3=1&P4=TTlIRY3C5C0NCy6wZ%2fjOBD6mbttxFGGPfsFbmqrEM8%2bpeuHWOGEkxPqONxuHn3xIh%2bsx6n%2b%2fbeCS4AP85PIfP6nslVbZhu57J5k1nxWeAv0gruxo%2fSFvwhaAvzxUx8LT%2b4NFH8SkiRChKlN0clqUhSVv4kFUjVAs4kn4Sckx6O6lQ0LQah%2bA9hWSGEyNc2KPXyOWt7HQwl1vxe4ofdluvWt8T%2fFaNe0lkYLSOFsBTBenQHbs412IL5qlFEAtKtpxPRx9bthjMLjDkcpRaBICMxWIuQxwFlhPJzq8enUX1A3afle6fzkgmlrtFP2tA2i0EGX0HsJSDE4un3eBiJmEng%3d%3d&width=1600&vl=0&vt=38&vw=225&vh=150&preferOptimized=true"; // URL de exemplo
//        Glide.with(this)
//                .load(imageUrl)
//                .into(imageView);

        TextView descricao = findViewById(R.id.tex_descricao);
        String text_url = "https://edufecap-my.sharepoint.com/personal/santos_flavio_edu_fecap_br/SitePages/AfroDay.aspx#:~:text=A%20reuni%C3%A3o%20deste,detalhes%20em%20breve!"; // URL de exemplo
        Glide.with(this)
                .load(text_url)
                .into(descricao);

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

        // Configurações para o Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Navegação inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(fecafro.this, MainActivity.class));
            return true;
        } else if (itemId == R.id.nav_warning) {
            startActivity(new Intent(fecafro.this, Complaint.class));
            return true;
        } else if (itemId == R.id.nav_image) {
            startActivity(new Intent(fecafro.this, Notice.class));
            return true;
        } else {
            return false;
        }
    }
}
