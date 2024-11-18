package br.fecap.pi.voice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.fecap.pi.voice.R;

public class Complaint extends AppCompatActivity {

    private Spinner complaintTypeDropdown;
    private Spinner regionDropdown;
    private EditText reportText;
    private Button sendButton;
    private Button mainButton, complaintButton, fecafroButton;
    private boolean isFabOpen = false;

    SimpleDateFormat sdf = new SimpleDateFormat("HH");
    private final String actuallyHour = sdf.format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide(); // Remove a ActionBar

            dropdown = findViewById(R.id.specificationSpinner);

        complaintTypeDropdown = findViewById(R.id.specificationSpinner);
        regionDropdown = findViewById(R.id.regionSpinner);
        reportText = findViewById(R.id.reportEditText);
        sendButton = findViewById(R.id.sendButton);
        // Complaint type dropdown setup

        String[] complaintTypes = new String[]{"Racismo", "Homofobia", "Abuso Sexual", "Outros"};
        ArrayAdapter<String> complaintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, complaintTypes);
        complaintTypeDropdown.setAdapter(complaintAdapter);

        // Region dropdown setup
        Spinner spinnerRegions = findViewById(R.id.regionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.regions,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegions.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComplaint();
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
            startActivity(new Intent(Complaint.this, MainActivity.class));
        });

        complaintButton.setOnClickListener(view -> {
            startActivity(new Intent(Complaint.this, Complaint.class));
        });

        fecafroButton.setOnClickListener(view -> {
            startActivity(new Intent(Complaint.this, ruth_cardoso.class));
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


    private String cesarCipher(String text, int OFFSET){
        StringBuilder encryptedText = new StringBuilder();
        OFFSET = OFFSET % 26;

        //Should work but if isn't work we´re fucked
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
                        newCharCode = 'À' + (newCharCode - 'ÿ' - 1);
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
        String region = regionDropdown.getSelectedItem().toString();
        String report = reportText.getText().toString();

        if (report.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlPostComplaint = "https://3hdf9d-3000.csb.app/complaint";

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlPostComplaint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Complaint.this, "Complaint sent successfully", Toast.LENGTH_SHORT).show();
                        reportText.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Complaint.this, "Error sending complaint", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("type", cesarCipher(type, Integer.parseInt(actuallyHour)));
                params.put("region", region);
                params.put("report", cesarCipher(report, Integer.parseInt(actuallyHour)));
                return params;
            }
        };
        queue.add(postRequest);
    }
}
