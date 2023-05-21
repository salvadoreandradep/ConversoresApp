package com.gruposeven.conversoresapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class PostAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> postsList;

    public PostAdapter(Context context, List<String> postsList) {
        super(context, 0, postsList);
        this.context = context;
        this.postsList = postsList;
    }


    public android.view.View getView(int position, android.view.View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_post, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.postTextView = view.findViewById(R.id.postTextView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String post = postsList.get(position);
        viewHolder.postTextView.setText(post);

        return view;
    }
    private static class ViewHolder {
        TextView postTextView;
    }
}


