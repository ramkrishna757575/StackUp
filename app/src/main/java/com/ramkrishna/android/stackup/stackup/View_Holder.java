package com.ramkrishna.android.stackup.stackup;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ramkr on 17-Apr-16.
 *
 * The View Holder to be used in the RecyclerViewAdapter. Responsible to store refernces for the views in the RecyclerView.
 */
public class View_Holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView question_title;
    TextView tags;
    TextView display_name;
    TextView votes;
    TextView timestamp;
    ImageView profile_image;

    View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        question_title = (TextView) itemView.findViewById(R.id.question_title);
        tags = (TextView) itemView.findViewById(R.id.tags);
        display_name = (TextView) itemView.findViewById(R.id.display_name);
        votes = (TextView)itemView.findViewById(R.id.votes_text);
        timestamp = (TextView)itemView.findViewById(R.id.timestamp);
        profile_image = (ImageView)itemView.findViewById(R.id.profile_image);
    }
}