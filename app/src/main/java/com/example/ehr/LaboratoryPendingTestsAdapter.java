package com.example.ehr;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class LaboratoryPendingTestsAdapter extends RecyclerView.Adapter<LaboratoryPendingTestsViewHolder> {


    List<LaboratoryPendingTestsModel> testList;
    LaboratoryPendingTestsFragment testFragment;
    TextChangeCallback callback;

    public LaboratoryPendingTestsAdapter(List<LaboratoryPendingTestsModel> testList, LaboratoryPendingTestsFragment testFragment, TextChangeCallback callback) {
        this.testList = testList;
        this.testFragment = testFragment;
        this.callback = callback;
    }


    @Override
    public int getItemViewType(final int position) {
        return R.layout.laboratory_pending_test_view;
    }


    @NonNull
    @Override
    public LaboratoryPendingTestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new LaboratoryPendingTestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaboratoryPendingTestsViewHolder holder, int position) {
        LaboratoryPendingTestsModel testModel = testList.get(position);
        String testname = testModel.getTestname();
        String firstname = testModel.getFirstname().concat(" ").concat(testModel.getLastname());
        String testreport = testModel.getTestreport();
        String testid = testModel.getTestid();
        String visitid = testModel.getVisitid();
        String laboratoryid = testModel.getLaboratoryid();
        String tdate = testModel.getTdate();

        holder.testname.setText(testname);
        holder.firstname.setText(firstname);
        holder.testid.setText(testid);
        holder.visitid.setText(visitid);
        holder.laboratoryid.setText(laboratoryid);
        holder.tdate.setText(tdate);
        holder.testreport.setText(testreport);
        holder.testreport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.equals(" "))
            {
                return;
                //Toast.makeText(testFragment.getActivity(), "Please update test report status",Toast.LENGTH_SHORT).show();
            }
            testModel.set(holder.getAdapterPosition(), String.valueOf(charSequence));
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        holder.Addtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View editLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.laboratory_pending_test_view,null);
                EditText Testreport = editLayout.findViewById(R.id.laboratory_test_report);
                Testreport.setText(testModel.getTestreport());

                String testreport = Testreport.getText().toString();

                testModel.setTestreport(testreport);
                testFragment.onAddtestClicked(testModel);

            }
        });

    }
    @Override
    public int getItemCount()
    {
        return testList.size();
    }

    public interface TextChangeCallback {
        void textChangedAt(int position, LaboratoryPendingTestsModel text);
    }
}
