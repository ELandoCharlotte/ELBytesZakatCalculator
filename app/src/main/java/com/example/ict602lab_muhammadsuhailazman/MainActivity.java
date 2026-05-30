package com.example.ict602lab_muhammadsuhailazman;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etValue;
    RadioGroup rgType;
    RadioButton rbKeeping, rbWearing;
    Button btnCalculate, btnClear;
    TextView tvTotalZakatAmount, tvResultDetails, tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        ImageView toolbarLogo = findViewById(R.id.toolbarLogo);
        Glide.with(this)
                .load(R.drawable.elbytes_logo)
                .into(toolbarLogo);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_calculator), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, 0); // Removed bottom padding from root
            toolbar.setPadding(0, systemBars.top, 0, 0);
            
            // Add padding to the bottom sheet content instead
            float density = getResources().getDisplayMetrics().density;
            int padding24 = Math.round(24 * density);
            findViewById(R.id.bottomSheetContent).setPadding(
                padding24, padding24, padding24, systemBars.bottom + padding24
            );
            return insets;
        });

        tvTitle = findViewById(R.id.tvTitle);
        // setGoldColoredTitle(tvTitle, getString(R.string.app_name));
        tvTitle.setText(getString(R.string.app_name));

        etWeight = findViewById(R.id.etWeight);
        etValue = findViewById(R.id.etValue);
        rgType = findViewById(R.id.rgType);
        rbKeeping = findViewById(R.id.rbKeeping);
        rbWearing = findViewById(R.id.rbWearing);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        tvTotalZakatAmount = findViewById(R.id.tvTotalZakatAmount);
        tvResultDetails = findViewById(R.id.tvResultDetails);

        // Initialize output labels before calculation
        updateResultDisplay(0, 0, 0, 0, 0, 0, 0);

        btnCalculate.setOnClickListener(v -> calculateZakat());
        btnClear.setOnClickListener(v -> clearFields());
    }

    private void calculateZakat() {
        String weightStr = etWeight.getText().toString();
        String valueStr = etValue.getText().toString();

        if (weightStr.isEmpty() || valueStr.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            double value = Double.parseDouble(valueStr);
            double uruf = rbKeeping.isChecked() ? 85.0 : 200.0;

            // 1. Gold weight minus X (uruf)
            double weightDiff = weight - uruf;
            
            // 2. Zakat payable (Value)
            double zakatPayableValue;
            if (weightDiff > 0) {
                zakatPayableValue = weightDiff * value;
            } else {
                zakatPayableValue = 0;
            }
            
            // 3. Total zakat (2.5%)
            double totalZakat = zakatPayableValue * 0.025;

            // Total Gold Value (for info)
            double totalGoldValue = weight * value;

            updateResultDisplay(totalZakat, totalGoldValue, zakatPayableValue, weight, value, uruf, weightDiff);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateResultDisplay(double zakat, double totalVal, double payableVal, double weight, double goldPrice, double uruf, double weightDiff) {
        tvTotalZakatAmount.setText(String.format(Locale.getDefault(), "RM %.2f", zakat));
        
        String weightDiffStr;
        if (weightDiff > 0) {
            weightDiffStr = String.format(Locale.getDefault(), "(%.2f - %.0f) = %.2f", weight, uruf, weightDiff);
        } else {
            weightDiffStr = String.format(Locale.getDefault(), "(%.2f - %.0f) is less than zero", weight, uruf);
        }
        
        String payableDetails;
        if (weightDiff > 0) {
            payableDetails = String.format(Locale.getDefault(), "(%.2f * %.2f) = RM %.2f", weightDiff, goldPrice, payableVal);
        } else {
            payableDetails = "RM 0.00";
        }

        String totalZakatDetails = String.format(Locale.getDefault(), "%.2f * 0.025 = RM %.2f", payableVal, zakat);

        String details = String.format(Locale.getDefault(),
                "Gold Weight - Uruf = %s\n" +
                "Zakat payable: %s\n" +
                "Total zakat: %s\n" +
                "Total Gold Value: RM %.2f\n" +
                "Gold Price per gram: RM %.2f",
                weightDiffStr, payableDetails, totalZakatDetails, totalVal, goldPrice);

        tvResultDetails.setText(details);
    }

    private void clearFields() {
        etWeight.setText("");
        etValue.setText("");
        updateResultDisplay(0, 0, 0, 0, 0, 0, 0);
        rbKeeping.setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
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