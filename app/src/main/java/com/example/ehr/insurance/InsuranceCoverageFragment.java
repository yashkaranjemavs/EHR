package com.example.ehr.insurance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ehr.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsuranceCoverageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsuranceCoverageFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_insurance_coverage, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.insurance_menu);
//        if(item!=null)
//            item.setVisible(false);
    }
}