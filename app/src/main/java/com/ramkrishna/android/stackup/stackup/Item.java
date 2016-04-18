package com.ramkrishna.android.stackup.stackup;

import java.util.List;

/**
 * Created by ramkr on 17-Apr-16.
 *
 * Creates individual "Item" Object that contains data retrieved and Parsed from the API.
 */
public class Item {

    /*The below variables hold the various components of each item received from the API*/
    List<String> tags;
    Owner owner;
    int view_count;
    int answer_count;
    int score;
    int last_activity_date;
    int creation_date;
    int last_edit_date;
    int question_id;
    String link;
    String title;

    Item(List<String> tags, Owner owner, int view_count, int answer_count, int score, int last_activity_date, int creation_date, int last_edit_date, int question_id, String link, String title)
    {
        this.tags = tags;
        this.owner = owner;
        this.view_count = view_count;
        this.answer_count = answer_count;
        this.score = score;
        this.last_activity_date = last_activity_date;
        this.creation_date = creation_date;
        this.last_activity_date = last_activity_date;
        this.last_edit_date = last_edit_date;
        this.question_id = question_id;
        this.link = link;
        this.title = title;
    }
}
