package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Pytanie> pytania;
    TextView textviewpytanie;
    RadioGroup radioGroup;
    int radioButtonid[] = new int[]{
            R.id.radioButton,
            R.id.radioButton2,
            R.id.radioButton3
    };
    RadioButton radioButton_a;
    RadioButton radioButton_b;
    RadioButton radioButton_c;
    Button button;
    int aktualnepytanie = 0;
    int sumapunktow = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textviewpytanie = findViewById(R.id.textviewtrescpytania);
        radioGroup = findViewById(R.id.radiogroup);
        radioButton_a = findViewById(R.id.radioButton);
        radioButton_b = findViewById(R.id.radioButton2);
        radioButton_c = findViewById(R.id.radioButton3);
        button = findViewById(R.id.button);
        View button2 = findViewById(R.id.button2);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/sleepythree/retrofit-Jason/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Pytanie>> call = jsonPlaceHolderApi.getPytanie();

        call.enqueue(new Callback<List<Pytanie>>() {
            @Override
            public void onResponse(Call<List<Pytanie>> call, Response<List<Pytanie>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    return;
                }
                pytania = response.body();
                Toast.makeText(MainActivity.this, pytania.get(0).getTrescPytania(), Toast.LENGTH_SHORT).show();
                wyswietlPytanie(0);
            }

            @Override
            public void onFailure(Call<List<Pytanie>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (sprawdzOdp(aktualnepytanie)) {
                            sumapunktow++;
                            Toast.makeText(MainActivity.this, "dobrze", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "źle", Toast.LENGTH_SHORT).show();
                        }
                        if (aktualnepytanie < pytania.size() - 1) {
                            aktualnepytanie++;
                            wyswietlPytanie(aktualnepytanie);

                        } else {
                            //TODO: koniec testu
                            radioGroup.setVisibility(View.INVISIBLE);
                            textviewpytanie.setText("Koniec testu, punkty:"+sumapunktow);
                            button.setVisibility(View.INVISIBLE);
                            button2.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentWyslij = new Intent();
                        intentWyslij.setAction(Intent.ACTION_SEND);
                        intentWyslij.putExtra(Intent.EXTRA_TEXT, "otrzymano: "+sumapunktow);
                        intentWyslij.setType("text/plain");
                        Intent intentUdostepniona = Intent.createChooser(intentWyslij, null);
                        startActivity(intentUdostepniona);

                    }
                }
        );
    }

    private boolean sprawdzOdp(int aktualnepytanie) {
        Pytanie pytanie = pytania.get(aktualnepytanie);

        return radioGroup.getCheckedRadioButtonId() ==
                radioButtonid[pytanie.getPoprawna()];
    }

    private void wyswietlPytanie(int ktore) {
        Pytanie pytanie = pytania.get(ktore);
        textviewpytanie.setText(pytanie.getTrescPytania());
        radioButton_a.setText(pytanie.getOdpa());
        radioButton_b.setText(pytanie.getOdpb());
        radioButton_c.setText(pytanie.getOdpc());
    }
}
