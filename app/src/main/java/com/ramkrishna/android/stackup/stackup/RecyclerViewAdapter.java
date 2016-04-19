package com.ramkrishna.android.stackup.stackup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

/**
 * Created by ramkr on 17-Apr-16.
 *
 * Adapter for the recycler view. Manages the Updation of UI as per data changes.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    List<Item> list = Collections.emptyList();
    Context context;

    //Adapter Constructor
    public RecyclerViewAdapter(List<Item> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.question_title.setText(list.get(position).title);
        String tags = "";
        for (int i = 0; i < list.get(position).tags.size(); i++)
        {
            tags += list.get(position).tags.get(i) + " ";
        }
        holder.tags.setText(tags);
        holder.display_name.setText("By - " + list.get(position).owner.display_name);
        holder.votes.setText(Integer.toString(list.get(position).score));
        Timestamp lastActiveDate = new Timestamp(list.get(position).last_activity_date);
        holder.timestamp.setText(lastActiveDate.toString());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(list.get(position).link));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        if(list.get(position).owner.getDisplay_image()== null)
        {
            GetProfilePic gpc = new GetProfilePic(holder,position);
            gpc.execute();
        }else{
            holder.profile_image.setImageBitmap(list.get(position).owner.getDisplay_image());
        }
    }

    @Override
    public int getItemCount()
    {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        notifyDataSetChanged();
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Item data)
    {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Item data)
    {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

    //Inner class to handle Image download for each Owner
    private class GetProfilePic extends AsyncTask {
        NetworkDataManager ndm = new NetworkDataManager(context);
        String url;
        int position;
        ViewHolder holder;
        Bitmap displayImage;
        GetProfilePic(ViewHolder holder,int position)
        {
            this.position = position;
            this.url = list.get(position).owner.profile_image;
            this.holder = holder;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            ndm.setUrl(url);
            ndm.initNetwork();
        }

        @Override
        protected Object doInBackground(Object[] params)
        {
            ndm.fetchBitmapData();
            return null;
        }

        @Override
        protected void onPostExecute(Object o)
        {
            super.onPostExecute(o);
            displayImage = ndm.getBitmap();
            if(displayImage != null)
            {
                list.get(position).owner.setDisplay_image(displayImage);
                holder.profile_image.setImageBitmap(displayImage);
            }
        }
    }
}
