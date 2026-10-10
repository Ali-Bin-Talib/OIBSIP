package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Spinner spCategory, spFrom, spTo;
    EditText etValue;
    Button btnConvert;
    TextView tvResult;

    String[] categories = {"Length", "Weight", "Temperature"};

    String[] lengthUnits = {"Meter", "Kilometer", "Centimeter", "Inch", "Foot"};
    String[] weightUnits = {"Kilogram", "Gram", "Pound", "Ounce"};
    String[] tempUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    Map<String, Double> factors = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spCategory = findViewById(R.id.spCategory);
        spFrom = findViewById(R.id.spFrom);
        spTo = findViewById(R.id.spTo);
        etValue = findViewById(R.id.etValue);
        btnConvert = findViewById(R.id.btnConvert);
        tvResult = findViewById(R.id.tvResult);

        factors.put("Meter", 1.0);
        factors.put("Kilometer", 1000.0);
        factors.put("Centimeter", 0.01);
        factors.put("Inch", 0.0254);
        factors.put("Foot", 0.3048);
        factors.put("Kilogram", 1.0);
        factors.put("Gram", 0.001);
        factors.put("Pound", 0.45359237);
        factors.put("Ounce", 0.028349523);

        spCategory.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categories));

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadUnits(categories[position]);
                tvResult.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnConvert.setOnClickListener(v -> convert());
    }

    private void loadUnits(String category) {
        String[] units;
        if (category.equals("Length")) units = lengthUnits;
        else if (category.equals("Weight")) units = weightUnits;
        else units = tempUnits;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, units);
        spFrom.setAdapter(adapter);
        spTo.setAdapter(adapter);
        if (units.length > 1) spTo.setSelection(1);
    }

    private void convert() {
        String input = etValue.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double value;
        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        String category = spCategory.getSelectedItem().toString();
        String from = spFrom.getSelectedItem().toString();
        String to = spTo.getSelectedItem().toString();

        double result;
        if (category.equals("Temperature")) {
            result = convertTemperature(value, from, to);
        } else {
            double inBase = value * factors.get(from);
            result = inBase / factors.get(to);
        }

        tvResult.setText(String.format(Locale.US, "%.4f %s", result, to));
    }

    private double convertTemperature(double value, String from, String to) {
        double celsius;
        if (from.equals("Celsius")) celsius = value;
        else if (from.equals("Fahrenheit")) celsius = (value - 32) * 5 / 9;
        else celsius = value - 273.15;

        if (to.equals("Celsius")) return celsius;
        else if (to.equals("Fahrenheit")) return celsius * 9 / 5 + 32;
        else return celsius + 273.15;
    }
}