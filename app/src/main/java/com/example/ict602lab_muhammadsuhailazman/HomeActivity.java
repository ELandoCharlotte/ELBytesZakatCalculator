package com.example.ict602lab_muhammadsuhailazman;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            toolbar.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        setGoldColoredTitle(tvWelcome, getString(R.string.welcome_msg));

        ImageView ivLogo = findViewById(R.id.ivLogo);
        Glide.with(this)
                .load(R.drawable.elbytes_logo)
                .into(ivLogo);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void setGoldColoredTitle(TextView textView, String fullText) {
        SpannableString spannableString = new SpannableString(fullText);
        int goldStart = fullText.indexOf("Gold");
        if (goldStart != -1) {
            int goldEnd = goldStart + 4;
            int goldColor = ContextCompat.getColor(this, R.color.gold);
            spannableString.setSpan(new ForegroundColorSpan(goldColor), goldStart, goldEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(spannableString);
    }
}