package com.example.class22b_and_homeexe_vadim_lazarevich_317785053.logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.R;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.callbacks.CallBack_ListRecordClicked;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.data.Record;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public enum TimerStatus {
    OFF,
    RUNNING,
    PAUSE


}