package com.gmail.baric0748;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

//this holds the widgets for the recycler view
public class RecyclerHolder extends RecyclerView.ViewHolder {

    public TextView cardTopic;
    public TextView noteTitle;
    public TextView noteComment;
    public WebView noteWebview;

    public RecyclerHolder(final View itemView, final RecyclerAdapter.OnItemClickListener clickListener)
    {
        super(itemView);

        cardTopic = itemView.findViewById(R.id.cardTopic);
        noteTitle = itemView.findViewById(R.id.cardTitle);
        noteComment = itemView.findViewById(R.id.cardComment);
        noteWebview = itemView.findViewById(R.id.cardWebview);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null)
                {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION)
                    {
                        clickListener.onItemClick(pos);
                    }
                }
            }
        });
    }
}

