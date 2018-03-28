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

 class MarketListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Market> marketList;

    public MarketListAdapter(Context context, int layout, ArrayList<Market> marketList) {
        this.context = context;
        this.layout = layout;
        this.marketList = marketList;
    }

    @Override
    public int getCount() {
        return marketList.size();
    }

    @Override
    public Object getItem(int position) {
        return marketList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName, txtDescription, txtOfficeLocation;
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
            holder.txtOfficeLocation = (TextView) row.findViewById(R.id.txtOfficeLocation);

            holder.imageView = (ImageView) row.findViewById(R.id.imageView);
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



        Market market = marketList.get(position);



        holder.txtName.setText(market.getName());
        holder.txtDescription.setText(market.getDescription());
        holder.txtOfficeLocation.setText(Double.toString(market.getOfficeLocation()));

        byte[] marketImage = market.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(marketImage, 0, marketImage.length);
        holder.imageView.setImageBitmap(bitmap);



        return row;
    }
}
