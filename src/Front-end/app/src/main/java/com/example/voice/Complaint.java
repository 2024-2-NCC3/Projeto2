package com.example.voice;

import static androidx.fragment.app.FragmentManager.TAG;

import java.time.LocalTime;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private Spinner dropdown;
    private EditText reportText;
    private Button sendButton;


    SimpleDateFormat sdf = new SimpleDateFormat("HH");
    private final String actuallyHour = sdf.format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);


        dropdown = findViewById(R.id.specificationSpinner);
        reportText = findViewById(R.id.reportEditText);
        sendButton = findViewById(R.id.sendButton);

        System.out.println(cesarCipher("hello world", 20));

        String[] items = new String[]{"Racismo", "Homofobia", "Abuso Sexual", "Outros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComplaint();
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

    }

    private String cesarCipher(String text, int deslocamento){
        StringBuilder encryptedText = new StringBuilder();
        deslocamento = deslocamento % 26;

        for (char c: text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                char newLetter = (char) ('A' + (c - 'A' + deslocamento + 26) % 26);
                encryptedText.append(newLetter);
            }
            else if (c >= 'a' && c <= 'z') {
                    char newLetter = (char) ('a' + (c - 'a' + deslocamento + 26) % 26);
                    encryptedText.append(newLetter);
            }
            else {encryptedText.append(c);}
        }

        return encryptedText.toString();
    }




    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // this shit is very stupid but it works well

        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(Complaint.this, MainActivity.class));
            return true;
        } else if (itemId == R.id.nav_warning) {
            startActivity(new Intent(Complaint.this, Complaint.class));
            return true;
        } else if (itemId == R.id.nav_image) {
            startActivity(new Intent(Complaint.this, Notice.class));
            return true;
        } else {
            return false;
        }
    }


    private void sendComplaint() {
        String type = dropdown.getSelectedItem().toString();
        String report = reportText.getText().toString();

        if (report.isEmpty()) {
            Toast.makeText(this, "Preencha os campos vazios", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlPostComplaint = "https://3hdf9d-3000.csb.app/complaint";

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlPostComplaint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Complaint.this, "Enviado com sucesso", Toast.LENGTH_SHORT).show();
                        System.out.println("funcionou");
                        reportText.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Complaint.this, "Erro ao enviar", Toast.LENGTH_SHORT).show();
                        System.out.println(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("type", cesarCipher(type, Integer.parseInt(actuallyHour)));
                params.put("report", cesarCipher(report, Integer.parseInt(actuallyHour)));
                return params;
            }
        };

        queue.add(postRequest);
    }
}
