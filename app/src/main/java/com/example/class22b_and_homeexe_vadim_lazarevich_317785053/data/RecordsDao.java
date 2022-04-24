package com.example.class22b_and_homeexe_vadim_lazarevich_317785053.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.R;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.activities.App;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.data.Record;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.utilities.MySharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class RecordsDao {

    private final String RECORDS_KEY = "RECORD_LIST";

    public RecordsDao() {

    }


    public List<Record> readRecords(){
        List<Record> records;
        String json = MySharedPreferences.getInstance().getString(RECORDS_KEY,null);
        if (json !=null){
            records = new Gson().fromJson(json, new TypeToken<List<Record>>(){}.getType());
        }else{
            records = new ArrayList<>();
        }

        return records;
    }

    public void saveRecords(List<Record> records){
        String json = new Gson().toJson(records);
        MySharedPreferences.getInstance().putString(RECORDS_KEY,json);
    }
}
