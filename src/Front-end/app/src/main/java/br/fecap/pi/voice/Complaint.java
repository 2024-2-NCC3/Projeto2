package br.fecap.pi.voice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Complaint extends AppCompatActivity {

    private Spinner complaintTypeDropdown;
    private Spinner regionDropdown;
    private EditText reportText;
    private Button sendButton;

    SimpleDateFormat sdf = new SimpleDateFormat("HH");
    private final String actuallyHour = sdf.format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        complaintTypeDropdown = findViewById(R.id.specificationSpinner);
        regionDropdown = findViewById(R.id.regionSpinner);
        reportText = findViewById(R.id.reportEditText);
        sendButton = findViewById(R.id.sendButton);

        System.out.println(cesarCipher("hello world", 20));

        // Complaint type dropdown setup
        String[] complaintTypes = new String[]{"Racism", "Homophobia", "Sexual Abuse", "Others"};
        ArrayAdapter<String> complaintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, complaintTypes);
        complaintTypeDropdown.setAdapter(complaintAdapter);

        // Region dropdown setup
        String[] regions = new String[]{
                "Capital", "Grande São Paulo", "Litoral Norte", "Vale do Paraíba",
                "Região de Campinas", "Sorocaba", "Região de Ribeirão Preto",
                "São José do Rio Preto", "Presidente Prudente", "Terra do Sol Nascente",
                "Tóquio", "Região de Bauru", "Vale do Ribeira", "Região de Marília",
                "Baixada Santista", "Alto Tietê", "Região de Araçatuba", "Região de Franca"
        };
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, regions);
        regionDropdown.setAdapter(regionAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComplaint();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
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

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return NavBar.handleNavigation(this, item);
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
