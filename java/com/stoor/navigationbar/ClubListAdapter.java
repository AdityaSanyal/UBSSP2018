package com.stoor.navigationbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ClubListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Club> clubsList;

    public ClubListAdapter(Context context, int layout, ArrayList<Club> clubsList) {
        this.context = context;
        this.layout = layout;
        this.clubsList = clubsList;
    }

    @Override
    public int getCount() {
        return clubsList.size();
    }

    @Override
    public Object getItem(int position) {
        return clubsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName, txtDescription, txtEstablishedYear, txtOfficeHours, txtOfficeLocation;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.txtDescription = (TextView) row.findViewById(R.id.txtDescription);
            holder.txtEstablishedYear = (TextView) row.findViewById(R.id.txtEstablishedYear);
            holder.txtOfficeHours = (TextView) row.findViewById(R.id.txtOfficeHours);
            holder.txtOfficeLocation = (TextView) row.findViewById(R.id.txtOfficeLocation);

            holder.imageView = (ImageView) row.findViewById(R.id.imgClub);
            row.setTag(holder);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                Intent intent = new Intent(context,Selected_Club.class);


                //intent.putExtra("Title",mData.get(position).getTitle());
                //intent.putExtra("Description",mData.get(position).getDescription());
                //intent.putExtra("Thumbnail",mData.get(position).getThumbnail());

                context.startActivity(intent);

                    Log.i("info","clicked");

                }
            });
        }
        else {
            holder = (ViewHolder) row.getTag();
        }



        Club club = clubsList.get(position);



        holder.txtName.setText(club.getName());
        holder.txtDescription.setText(club.getDescription());
        holder.txtEstablishedYear.setText(club.getEstablishedYear());
        holder.txtOfficeHours.setText(club.getOfficeHours());
        holder.txtOfficeLocation.setText(club.getOfficeLocation());

        byte[] clubImage = club.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(clubImage, 0, clubImage.length);
        holder.imageView.setImageBitmap(bitmap);



        return row;
    }
}
