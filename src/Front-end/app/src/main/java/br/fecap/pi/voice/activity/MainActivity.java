package br.fecap.pi.voice.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import br.fecap.pi.voice.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remover a ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide(); // Remove a ActionBar
        }

        setContentView(R.layout.activity_main);

        // CardView: Nova Denúncia
        CardView cardNewReport = findViewById(R.id.card_new_report);
        cardNewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Complaint.class);
                startActivity(intent);
                finish();
            }
        });

        // CardView: S.O.S CRPIR
        CardView cardSosCrpir = findViewById(R.id.card_sos_crpir);
        cardSosCrpir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=5511974318628"));
                startActivity(intent);
            }
        });

        // CardView: Ruth Cardoso
        CardView cardRuthCardoso = findViewById(R.id.card_ruth_cardoso);
        cardRuthCardoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ruth_cardoso.class); // Certifique-se de que o nome da classe corresponde à sua activity
                startActivity(intent);
                finish();
            }
        });

        // CardView: FECAFRO
        CardView cardFecafro = findViewById(R.id.card_fecafro);
        cardFecafro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, fecafro.class); // Certifique-se de que o nome da classe corresponde à sua activity
                startActivity(intent);
                finish();
            }
        });

        // Ajuste de insets para as barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
