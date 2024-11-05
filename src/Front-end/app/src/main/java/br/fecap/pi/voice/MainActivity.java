package br.fecap.pi.voice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.drawable.GradientDrawable;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.fecap.pi.voice.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Intent intent = new Intent(MainActivity.this, ruth_cardoso.class); // Certifique-se de que o nome da classe corresponde à sua activity
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

    private void setRoundedButton(Button button) {
        // Criando e configurando o GradientDrawable
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(300f); // Raio dos cantos
        button.setBackground(drawable);
    }


}