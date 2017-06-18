package com.thoughworks.githubapiintegration.view.commitInfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thoughworks.githubapiintegration.R;
import com.thoughworks.githubapiintegration.model.Person;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    private ArrayList<Person> persons;

    public CustomAdapter(Context context, ArrayList<Person> persons) {
        super(context, R.layout.commit_item_row, R.id.person_image, persons);
        this.persons = persons;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.commit_item_row, parent, false);

        TextView personName = (TextView) row.findViewById(R.id.person_name);
        TextView commitMessage = (TextView) row.findViewById(R.id.commit_message);
        TextView commitSha = (TextView) row.findViewById(R.id.commit_sha);
        ImageView imageView = (ImageView) row.findViewById(R.id.person_image);

        personName.setText(persons.get(position).getName());
        commitMessage.setText(persons.get(position).getCommitMessage());
        commitSha.setText(persons.get(position).getCommitSha());

        Picasso.with(getContext())
                .load(persons.get(position).getImageUrl())
                .placeholder(R.drawable.loading)
                .transform(new CircleTransformation())
                .into(imageView);

        return row;
    }
}
