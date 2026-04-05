package com.clase.conversor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.clase.conversor.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // El binding nos permite acceder a los componentes de la vista
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        viewModel = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getApplication())
                .create(MainActivityViewModel.class);

        // Observa el resultado de la conversion de la moneda y tambien actualiza el texto de la pantalla
        viewModel.getResultConvert().observe(this, currencyResult -> {
            binding.tvResult.setText(currencyResult.getSymbol() + " " + currencyResult.getValue());
        });

        // Observa los mensajes y nos los muestra en un Toast.
        viewModel.getToastMessage().observe(this, message -> {
            if(message != null){
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        // Radio button dolar, Si se elige convertir a Dolares, habilitamos el campo de Euros
        // y bloqueamos el campo de dolares.
        binding.rbDolar.setOnClickListener(v -> {
            binding.etEuro.setEnabled(true);
            binding.etDolar.setEnabled(false);
            binding.etDolar.setText(""); // Limpieza del campo
        });

        // Radio button euro, Si se elige convertir a Euros, habilitamos el campo de Dolares
        // y bloqueamos el campo de euros.
        binding.rbEuro.setOnClickListener(v -> {
            binding.etDolar.setEnabled(true);
            binding.etEuro.setEnabled(false);
            binding.etEuro.setText(""); // Limpieza del campo
        });

        // Boton de conversion
        binding.btnConvert.setOnClickListener(v -> {
            boolean isDolar = binding.rbDolar.isChecked();

            String currencySelected = isDolar ? "Dollar" : "Euro";
            String amountInput = isDolar
                    ? binding.etEuro.getText().toString()
                    : binding.etDolar.getText().toString();

            viewModel.convertCurrency(currencySelected, amountInput);
        });

        // Boton para cambiar la tasa de cambio
        binding.btnChangeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueEuro = binding.etChangeValue.getText().toString();
                viewModel.changeValueCurrency("Euro", valueEuro);
            }
        });
    }
}