package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

public class MiniSearchListItemViewHolder extends RecyclerView.ViewHolder {
    View view;
    public MiniSearchListItemViewHolder(View _view){
        super(_view);
        view = _view;
    }
    public void setDetails(String title, String details,View.OnClickListener listener){
        TextView titleView = view.findViewById(R.id.Title);
        TextView detailView = view.findViewById(R.id.Detail);

        titleView.setText(title);
        detailView.setText(details);

        ConstraintLayout content = view.findViewById(R.id.Content);
        content.setOnClickListener(listener);

    }
}
