package com.clase.conversor;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private final MutableLiveData<List<CurrencyModel>> currencyMutable;
    private final MutableLiveData<CurrencyModel> resultConvertMutable;
    private final MutableLiveData<String> toastMessage;

    // Valores puestos por defecto
    private final BigDecimal DOLLAR_VALUE_DEFAULT = new BigDecimal("1.00");
    private final BigDecimal EURO_VALUE_DEFAULT = new BigDecimal("0.87");

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        currencyMutable = new MutableLiveData<>();
        resultConvertMutable = new MutableLiveData<>();
        toastMessage = new MutableLiveData<>();
        //Cargamos los valores
        initValueDefaultCurrency();
    }

    private void initValueDefaultCurrency(){
        List<CurrencyModel> init = Arrays.asList(
                new CurrencyModel("Dollar", DOLLAR_VALUE_DEFAULT, "US$"),
                new CurrencyModel("Euro", EURO_VALUE_DEFAULT, "€"));

        currencyMutable.setValue(init);
    }

    public LiveData<List<CurrencyModel>> getCurrencies(){
        return currencyMutable;
    }

    public LiveData<CurrencyModel> getResultConvert(){
        return resultConvertMutable;
    }

    public LiveData<String> getToastMessage(){
        return toastMessage;
    }

    // Nos permite cambiar la tasa de cambio de la moneda
    public void changeValueCurrency(String nameCurrency, String newValue){
        try{
            List<CurrencyModel> list = currencyMutable.getValue();

            if (list == null || newValue == null || newValue.isEmpty()) {
                toastMessage.setValue("El campo no se puede encontrar vacio!");
                Log.d("ModelView", "changeValueCurrency: El campo se encuentra vacio.");
                return;
            }

            // Buscamos la moneda en la lista y actualizamos su valor
            list.stream()
                    .filter(c -> c.getName().equalsIgnoreCase(nameCurrency))
                    .findFirst()
                    .ifPresent(c -> c.setValue(new BigDecimal(newValue.replace(",", "."))));

            currencyMutable.setValue(list);
            toastMessage.setValue("Valor de " + nameCurrency + " actualizado a " + newValue);

        }catch (Exception e){
            Log.e("ModelView", "Error: " + e.getMessage());
        }
    }

    public void convertCurrency(String nameCurrency, String amountCurrency){
        if(amountCurrency == null || amountCurrency.isEmpty()){
            toastMessage.setValue("¡Debes ingresar un monto!");
            return;
        }

        try {
            List<CurrencyModel> list = currencyMutable.getValue();
            if (list == null) return;

            BigDecimal amountToConvert = new BigDecimal(amountCurrency.replace(",", "."));

            // Obtenemos la información del Euro para usar su tasa de cambio
            CurrencyModel euroInfo = list.stream()
                    .filter(c -> c.getName().equalsIgnoreCase("Euro"))
                    .findFirst().orElse(null);

            if (euroInfo == null){
                return;
            }

            BigDecimal resultValue;
            String symbol;


                // Si la moneda es Dolar: Dividimos el monto (Euros) por la tasa del Euro.
            if (nameCurrency.equalsIgnoreCase("Dollar")) {
                resultValue = amountToConvert.divide(euroInfo.getValue(), 2, RoundingMode.HALF_UP);
                symbol = "US$";
            } else {
                // Si la moneda es Euro: Multiplicamos el monto (Dólares) por la tasa del Euro.
                resultValue = amountToConvert.multiply(euroInfo.getValue()).setScale(2, RoundingMode.HALF_UP);
                symbol = "€";
            }

            resultConvertMutable.setValue(new CurrencyModel(nameCurrency, resultValue, symbol));
            toastMessage.setValue("Conversión realizada con éxito!");

        } catch (ArithmeticException e) {
            toastMessage.setValue("Error: No se puede dividir por cero.");
            Log.e("ModelView", "Error: " + e.getMessage());
        } catch (Exception e) {
            toastMessage.setValue("Error en los datos ingresados.");
            Log.e("ModelView", "Error: " + e.getMessage());
        }
    }
}
