package com.ramkrishna.android.stackup.stackup;

import android.graphics.Bitmap;

/**
 * Created by ramkr on 17-Apr-16.
 *
 * Owner Object created for each Item received from the API.
 * Contains information like Profile Image, Display Name,etc.
 */
public class Owner {
    int reputation;
    int user_id;
    String user_type;
    int accept_rate;
    String profile_image;
    String display_name;
    String link;
    Bitmap display_image;

    Owner(int reputation, int user_id, String user_type, int accept_rate, String profile_image, String display_name, String link)
    {
        this.reputation = reputation;
        this.user_id = user_id;
        this.user_type = user_type;
        this.accept_rate = accept_rate;
        this.profile_image = profile_image;
        this.display_name = display_name;
        this.link = link;
    }


    public Bitmap getDisplay_image()
    {
        return display_image;
    }

    public void setDisplay_image(Bitmap display_image)
    {
        this.display_image = display_image;
    }
}
