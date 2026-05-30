package com.example.ict602lab_muhammadsuhailazman;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class AboutActivity extends AppCompatActivity {

    TextView textAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        ImageView toolbarLogo = findViewById(R.id.toolbarLogo);
        Glide.with(this)
                .load(R.drawable.elbytes_logo)
                .into(toolbarLogo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_about), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            toolbar.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        textAbout = findViewById(R.id.textAbout);

        String aboutText =
                "Developer Details\n\n" +
                        "Name: MUHAMMAD SUHAIL AZMAN\n" +
                        "Student Number: 2024528281\n" +
                        "Programme Code: CDCS251\n" +
                        "Group: RCDCS2515B\n\n" +

                        "Application Detail\n\n" +
                        "Gold Zakat Calculator is a user-friendly mobile application that provides an easy and accurate way to calculate zakat for gold ownership, covering both gold kept for investment or savings and gold worn as personal jewelry.\n\n" +

                        "Uruf:\n" +
                        "Gold Keeping: 85 grams\n" +
                        "Gold Wearing: 200 grams\n\n" +

                        "GitHub URL:\n" +
                        "https://github.com/ELandoCharlotte/ELBytesZakatCalculator.git\n\n" +

                        "© 2026 ELBYTES. All rights reserved.\n\n";

        textAbout.setText(aboutText);
        textAbout.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home || id == R.id.action_home) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_about) {
            return true;
        } else if (id == R.id.action_share) {
            shareApp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareApp() {
        String shareBody = "Check out this Gold Zakat Calculator app by ELBYTES! Download it from GitHub: https://github.com/ELandoCharlotte/ELBytesZakatCalculator.git";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Gold Zakat Calculator");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}