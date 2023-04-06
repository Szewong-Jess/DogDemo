package com.example.datedemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;

import com.example.datedemo.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Dog> DogList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //change to binding
        ActivityMainBinding binding
                = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Method to read from file
        ReadDogData();

        //DogAdapter adapter = new DogAdapter(DogList);

        DogAdapter adapter = new DogAdapter(DogList, new DogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                binding.txtViewAdoptionSumary.setText("Thank you for taking "+DogList.get(i).getDogName());
            }
        });

        binding.recyclerViewDogItems.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewDogItems.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ReadDogData() {
        DogList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getResources().openRawResource(R.raw.doginfo)));
        String csvLine;
        try {
            /*if ((CSVLine = reader.readLine()) != null) {
            no header line
            }*/
            while ((csvLine = reader.readLine()) != null){
                String[] dogFields = csvLine.split(",");
                int id = Integer.parseInt(dogFields[0]);
                String picName = dogFields[1];
                String dogBreed = dogFields[2];
                String dogName = dogFields[3];
                String dogDobStr = dogFields[4];

                //getting the drawable identifier
                int dogDrawable  = getResources()
                        .getIdentifier(picName,"drawable",getPackageName());

                //parse string dogdob
                //d - refer to one or more digits or date -e.g. 9,18,etc
                //dd -two -e.g 18,28
                //MMM - 3 letters eg JAN
                //MM -2 letter eg 02 18
                //yyyy - four digit year of era
                //yy -2 digits year of the era

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
                LocalDate dob = LocalDate.parse(dogDobStr,formatter);
                Dog eachDog = new Dog(id,dogBreed,dogName,dogDrawable,dob);
                DogList.add(eachDog);

            }
        } catch (Exception ex){
            Log.d("DATEDEMO", ex.getMessage());
        }

    }
}