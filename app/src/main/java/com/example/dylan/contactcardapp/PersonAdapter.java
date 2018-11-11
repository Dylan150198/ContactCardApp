package com.example.dylan.contactcardapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";
    private List<Person> people;
    private Context context;

    public PersonAdapter(List<Person> people, Context context) {
        this.people = people;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // This method will be called whenever the ViewHolder is created
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // This method will bind the data to the ViewHolder from where it'll be shown to other Views
        final Person person = people.get(position);
        holder.name.setText(person.toString());

        Picasso.with(context)
                .load(person.getProfileThumbNail())
                .into(holder.avatar);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person clickedPerson = people.get(position);
                Intent skipIntent = new Intent(v.getContext(), ProfileActivity.class);
                skipIntent.putExtra(KEY_NAME, clickedPerson.toString());
                skipIntent.putExtra(KEY_IMAGE, clickedPerson.getProfileMedium());
                v.getContext().startActivity(skipIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return people.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {
        // Define the View objects
        public TextView name;
        public ImageView avatar;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initialize the View objects
            name = (TextView) itemView.findViewById(R.id.name);
            avatar = (ImageView) itemView.findViewById(R.id.imageView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
