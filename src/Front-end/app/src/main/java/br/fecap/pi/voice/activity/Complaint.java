package br.fecap.pi.voice.activity;

import android.content.Intent;
import android.graphics.Region;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.fecap.pi.voice.R;

public class Complaint extends AppCompatActivity {

    private Spinner complaintTypeDropdown;
    private EditText reportText;
    private Button sendButton;
    private Button mainButton, complaintButton, fecafroButton;
    private boolean isFabOpen = false;
    private Spinner spinnerRegions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide(); // Remove a ActionBar
        }

        complaintTypeDropdown = findViewById(R.id.specificationSpinner);
        reportText = findViewById(R.id.reportEditText);
        sendButton = findViewById(R.id.sendButton);

        // Complaint type dropdown setup
        String[] complaintTypes = new String[]{"Racismo", "Homofobia", "Abuso Sexual", "Outros"};
        ArrayAdapter<String> complaintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, complaintTypes);
        complaintTypeDropdown.setAdapter(complaintAdapter);

        sendButton.setOnClickListener(v -> sendComplaint());

        spinnerRegions = findViewById(R.id.regionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.regions,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegions.setAdapter(adapter);

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
        mainButton.setOnClickListener(view -> startActivity(new Intent(Complaint.this, MainActivity.class)));
        complaintButton.setOnClickListener(view -> startActivity(new Intent(Complaint.this, Complaint.class)));
        fecafroButton.setOnClickListener(view -> startActivity(new Intent(Complaint.this, ruth_cardoso.class)));
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

    private String cesarCipher(String text, int OFFSET) {
        StringBuilder encryptedText = new StringBuilder();
        OFFSET = OFFSET % 26;

        // should work but if isn't work we are fucked
        if (OFFSET == 0) {
            OFFSET = 1;
        }

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                if (c >= 'A' && c <= 'Z') {
                    char newLetter = (char) ('A' + (c - 'A' + OFFSET + 26) % 26);
                    encryptedText.append(newLetter);
                } else if (c >= 'a' && c <= 'z') {
                    char newLetter = (char) ('a' + (c - 'a' + OFFSET + 26) % 26);
                    encryptedText.append(newLetter);
                } else if (c >= 'À' && c <= 'ÿ') {
                    int newCharCode = c + OFFSET;
                    if (newCharCode > 'ÿ') {
                        newCharCode = (newCharCode - 'ÿ' - 1) + 'À';
                    } else if (newCharCode < 'À') {
                        newCharCode = 'ÿ' - ('À' - newCharCode - 1);
                    }
                    encryptedText.append((char) newCharCode);
                }
            } else {
                encryptedText.append(c);
            }
        }

        return encryptedText.toString();
    }

    private void sendComplaint() {
        String type = complaintTypeDropdown.getSelectedItem().toString();
        String report = reportText.getText().toString();

        if (report.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlPostComplaint = "https://3hdf9d-3000.csb.app/complaint";

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlPostComplaint,
                response -> {
                    Toast.makeText(Complaint.this, "Complaint sent successfully", Toast.LENGTH_SHORT).show();
                    reportText.setText("");
                },
                error -> Toast.makeText(Complaint.this, "Error sending complaint", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                int currentHour = Integer.parseInt(new SimpleDateFormat("HH").format(new Date()));
                params.put("type", cesarCipher(type, currentHour));
                params.put("region", spinnerRegions.getSelectedItem().toString());
                params.put("report", cesarCipher(report, currentHour));
                return params;
            }
        };
        queue.add(postRequest);
    }
}
