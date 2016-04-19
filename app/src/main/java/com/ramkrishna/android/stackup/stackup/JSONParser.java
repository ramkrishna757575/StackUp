package com.ramkrishna.android.stackup.stackup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramkr on 17-Apr-16.
 * <p/>
 * Parses the raw JSON data fetched from the Network and creates a List containing Objects of type "Item".
 */
public class JSONParser {

    private int API_Quota;
    private JSONArray items = null;
    private String rawQuestionsList = null;
    private ArrayList<Item> itemList = new ArrayList<Item>();

    //Constuctor for JSONParser. Requires the raw JSON string as parameter.
    public JSONParser(String rawQuestionsList)
    {
        this.rawQuestionsList = rawQuestionsList;
    }

    //Parses the raw JSON string obtained from the API and creates a JSON Object
    public void parseObject()
    {
        if (rawQuestionsList != null && !rawQuestionsList.isEmpty())
        {
            try
            {
                JSONObject jsonObj = new JSONObject(rawQuestionsList);
                int quotaMax = jsonObj.getInt(Constants.TAG_QUOTA_MAX);
                int quotaRemaining = jsonObj.getInt(Constants.TAG_QUOTA_REMAINING);
                API_Quota = (quotaRemaining * 100) / quotaMax;
                items = jsonObj.getJSONArray(Constants.TAG_ITEMS);
                parseItems();
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    //Uses the JSON Object and creates individual items from it. Adds these items in a list
    private void parseItems()
    {
        for (int i = 0; i < items.length(); i++)
        {
            try
            {
                List<String> tagsList = new ArrayList<String>();
                Owner owner;
                int view_count = 0, answer_count = 0, score = 0, last_activity_date = 0, creation_date = 0, last_edit_date = 0, question_id = 0;
                String link = null, title = null;
                int reputation = 0, user_id = 0, accept_rate = 0;
                String user_type = null, profile_image = null, display_name = null, owner_link = null;

                JSONObject itemsObj = items.getJSONObject(i);
                JSONArray tags = itemsObj.getJSONArray(Constants.TAG_TAGS);
                for (int j = 0; j < tags.length(); j++)
                {
                    tagsList.add(tags.getString(j));
                }
                JSONObject ownerObj = itemsObj.getJSONObject(Constants.TAG_OWNER);
                if (ownerObj.has(Constants.TAG_OWNER_REPUTATION))
                    reputation = ownerObj.getInt(Constants.TAG_OWNER_REPUTATION);
                if (ownerObj.has(Constants.TAG_OWNER_USER_ID))
                    user_id = ownerObj.getInt(Constants.TAG_OWNER_USER_ID);
                if (ownerObj.has(Constants.TAG_OWNER_USER_TYPE))
                    user_type = ownerObj.getString(Constants.TAG_OWNER_USER_TYPE);
                if (ownerObj.has(Constants.TAG_OWNER_PROFILE_IMAGE))
                    profile_image = ownerObj.getString(Constants.TAG_OWNER_PROFILE_IMAGE);
                if (ownerObj.has(Constants.TAG_OWNER_DISPLAY_NAME))
                    display_name = ownerObj.getString(Constants.TAG_OWNER_DISPLAY_NAME);
                if (ownerObj.has(Constants.TAG_OWNER_LINK))
                    owner_link = ownerObj.getString(Constants.TAG_OWNER_LINK);
                if (ownerObj.has(Constants.TAG_OWNER_ACCEPT_RATE))
                    accept_rate = ownerObj.getInt(Constants.TAG_OWNER_ACCEPT_RATE);
                owner = new Owner(reputation, user_id, user_type, accept_rate, profile_image, display_name, owner_link);
                if (itemsObj.has(Constants.TAG_VIEW_COUNT))
                    view_count = itemsObj.getInt(Constants.TAG_VIEW_COUNT);
                if (itemsObj.has(Constants.TAG_ANSWER_COUNT))
                    answer_count = itemsObj.getInt(Constants.TAG_ANSWER_COUNT);
                if (itemsObj.has(Constants.TAG_SCORE))
                    score = itemsObj.getInt(Constants.TAG_SCORE);
                if (itemsObj.has(Constants.TAG_LAST_ACTIVITY_DATE))
                    last_activity_date = itemsObj.getInt(Constants.TAG_LAST_ACTIVITY_DATE);
                if (itemsObj.has(Constants.TAG_CREATION_DATE))
                    creation_date = itemsObj.getInt(Constants.TAG_CREATION_DATE);
                if (itemsObj.has(Constants.TAG_LAST_EDIT_DATE))
                    last_edit_date = itemsObj.getInt(Constants.TAG_LAST_EDIT_DATE);
                if (itemsObj.has(Constants.TAG_QUESTION_ID))
                    question_id = itemsObj.getInt(Constants.TAG_QUESTION_ID);
                if (itemsObj.has(Constants.TAG_LINK))
                    link = itemsObj.getString(Constants.TAG_LINK);
                if (itemsObj.has(Constants.TAG_TITLE))
                    title = itemsObj.getString(Constants.TAG_TITLE);
                Item item = new Item(tagsList, owner, view_count, answer_count, score, last_activity_date, creation_date, last_edit_date, question_id, link, title);
                itemList.add(item);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    //Returns the list of items created
    public ArrayList<Item> getItemList()
    {
        return itemList;
    }

    //Returns the API Quota as obtained from the API
    public int getAPI_Quota()
    {
        return API_Quota;
    }
}

