package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.R;

import java.util.ArrayList;

/** layout_item_mini_search.xml **/

public class MiniSearchViewHolder extends RecyclerView.ViewHolder {
    private View view;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private ConstraintLayout Constraint1;
    private MiniSearchData model;

    public MiniSearchViewHolder(View _view){
        super(_view);
        view = _view;
    }
    public void setDetails(final MiniSearchData model, String showText, String switchText) {
        this.model = model;

        final EditText text = view.findViewById(R.id.TextInput);
        text.setHint(showText);
        model.setEditable(text.getEditableText());

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() >= 0 &&
                        (model.getMaxElements() == -1L || model.getElementSet().size() < model.getMaxElements())
                ) {
                    model.getSearchCalledListener().onSearchCalled(s.toString().toLowerCase());
                }else{
                    model.SearchResults(new ArrayList<UniData>());
                }
            }
        });

        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView2 = view.findViewById(R.id.RecyclerView2);
        Constraint1 = view.findViewById(R.id.Constraint1);
        final ConstraintLayout Constraint2 = view.findViewById(R.id.Constraint2);

        recyclerView.setAdapter(model.getAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerView2.setAdapter(model.getAdapter2());
        recyclerView2.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Switch sw = view.findViewById(R.id.Swtich);

        if (model.isAllowSwitch()) {
            sw.setText(switchText);
            sw.setEnabled(true);
            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    updateChecked(isChecked);
                }
            });
            updateChecked(model.isEnabled());
            sw.setChecked(model.isEnabled());
        }else{
            sw.setVisibility(View.GONE);
        }

        if (model.getMaxElements() == model.getElementSet().size()){
            text.setVisibility(View.GONE);
        }else{
            text.setVisibility(View.VISIBLE);
        }
        model.setButtonSelectedListener(new OnButtonSelectedListener() {
            @Override
            public void onButtonSelected(boolean maxElementsLimit) {
                if (maxElementsLimit){
                    text.setVisibility(View.GONE);
                }else{
                    text.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    public void updateChecked(boolean checked){
        if (checked) {
            recyclerView.setVisibility(View.GONE);
            recyclerView2.setVisibility(View.GONE);
            Constraint1.setVisibility(View.GONE);
            model.setEnabled(false);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView2.setVisibility(View.VISIBLE);
            Constraint1.setVisibility(View.VISIBLE);

            model.setEnabled(true);
        }
    }
}
