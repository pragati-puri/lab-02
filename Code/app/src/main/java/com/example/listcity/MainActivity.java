package com.example.listcity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> citiesList;
    Button addCityBtn, deleteCityBtn;
    int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);
        addCityBtn = findViewById(R.id.add_city_btn);
        deleteCityBtn = findViewById(R.id.delete_city_btn);

        String []cities = {"Edmonton", "Vancouver", "Moscow"};

        citiesList = new ArrayList<>();
        citiesList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, citiesList);
        cityList.setAdapter(cityAdapter);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        cityList.setOnItemClickListener((parent, view, position, id) -> {

            if (selected == position) {
                selected = -1;
                cityList.setItemChecked(position, false);
                cityList.clearChoices();
            } else {
                selected = position;
                cityList.setItemChecked(position, true);
            }
        });

    //ADD CITY
        addCityBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add City");

            EditText input = new EditText(this);
            input.setHint("Enter city name");
            builder.setView(input);

            builder.setPositiveButton("CONFIRM", (dialog, which) -> {
                String city = input.getText().toString();
                if (!city.isEmpty()) {
                    citiesList.add(city);
                    cityAdapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());
            builder.show();
        });


    // DELETE CITY
        deleteCityBtn.setOnClickListener(v -> {
            if (selected != -1) {
                citiesList.remove(selected);
                cityAdapter.notifyDataSetChanged();
                selected = -1;
                cityList.clearChoices();
            } else {
                Toast.makeText(this, "Select a city first", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
