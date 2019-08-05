package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;

import com.gnd.calificaprofesores.R;

public class StarsViewHolder extends RecyclerView.ViewHolder  {
    View mView;
    public StarsViewHolder(View _mView){
        super(_mView);
        mView = _mView;
    }
    public void SetDetails(float Rating){
        RatingBar ratingBar = (RatingBar)mView.findViewById(R.id.Rating);
        ratingBar.setRating(Rating);

    }
}
