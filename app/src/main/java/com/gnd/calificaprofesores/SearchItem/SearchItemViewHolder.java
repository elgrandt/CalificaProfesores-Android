package com.gnd.calificaprofesores.SearchItem;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

/** Aqui administramos la parte gráfica de un element de busqueda **/

public class SearchItemViewHolder extends RecyclerView.ViewHolder{
    View mView;

    public SearchItemViewHolder(View _mView){
        super(_mView);
        mView = _mView;

    }

    public void setDetails(View.OnClickListener listener,String name, String details, Long score){
        TextView mAuthorText = mView.findViewById(R.id.Title);
        TextView mCommentText = mView.findViewById(R.id.Detail);

        mAuthorText.setText(name);

        mCommentText.setText(details);

        mView.findViewById(R.id.Content).setOnClickListener(listener);
    }

}
