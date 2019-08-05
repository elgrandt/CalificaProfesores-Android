package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

/** layout_item_small_message_and_button.xml **/

public class ClickableViewHolder extends RecyclerView.ViewHolder {
    View view;
    public ClickableViewHolder(View _view){
        super(_view);
        view = _view;
    }
    public void setDetails(String text, View.OnClickListener onClickListener){
        TextView textView = view.findViewById(R.id.Text);
        textView.setText(text);

        ConstraintLayout content = view.findViewById(R.id.Content);

        content.setOnClickListener(onClickListener);
    }
}
