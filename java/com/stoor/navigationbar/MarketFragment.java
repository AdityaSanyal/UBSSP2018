package com.stoor.navigationbar;


import android.Manifest;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class MarketFragment extends Fragment {

    RelativeLayout establishMarketLayout;
    RelativeLayout marketListLayout;

    GridView gridView;
    ArrayList<Market> list;
    MarketListAdapter adapter = null;

    FloatingActionButton createNewClub;
    EditText edtName, edtDescription, edtOfficeLocation;
    Button btnChoose, btnAdd;
    ImageView imageView;

    public static SQLiteHelper sqLiteHelper;

    final int REQUEST_CODE_GALLERY = 999;

    boolean alternate = true;

    View view;

    public MarketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_market,container,false);

        init();

        list = new ArrayList<Market>();
        adapter = new MarketListAdapter(getContext(), R.layout.market_items, list);
        gridView.setAdapter(adapter);

        /*
        ClubDB is database name initialized below
         */

        sqLiteHelper = new SQLiteHelper(getActivity());
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS MARKET (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR, " +
                "description VARCHAR, " +
                "price DOUBLE, " +
                "image BLOB)");

        setdatabase();

        createNewClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (alternate == true) {

                    marketListLayout.setVisibility(View.INVISIBLE);
                    establishMarketLayout.setVisibility(View.VISIBLE);
                    createNewClub.setImageResource(R.drawable.ic_check_circle_black_24dp);

                    alternate = false;

                } else {

                    establishMarketLayout.setVisibility(View.INVISIBLE);
                    marketListLayout.setVisibility(View.VISIBLE);
                    createNewClub.setImageResource(R.drawable.ic_edit_black_24dp);

                    alternate = true;
                    getFragmentManager().beginTransaction().detach(MarketFragment.this).attach(MarketFragment.this).commit();
                }

            }
        });

        // get all data from sqlite
        Log.i("Database Name :  ", sqLiteHelper.getDatabaseName());

        Cursor cursor = sqLiteHelper.getData("SELECT * FROM MARKET");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            double officeLocation = cursor.getDouble(3);
            byte[] image = cursor.getBlob(4);

            list.add(new Market(name, description,officeLocation, image, id));
        }
        adapter.notifyDataSetChanged();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = sqLiteHelper.getData("SELECT id FROM MARKET");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(getActivity(), arrID.get(position));

                        } else {
                            // delete
                            Cursor c = sqLiteHelper.getData("SELECT id FROM MARKET");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

        //Log.i("Activity",getActivity().toString());

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY
            );
                Log.i("info", "clicked2");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    sqLiteHelper.insertDataMarket(
                            edtName.getText().toString().trim(),
                            edtDescription.getText().toString().trim(),
                            Double.parseDouble(edtOfficeLocation.getText().toString().trim()),
                            imageViewToByte(imageView)
                    );
                    Toast.makeText(getContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    public void init() {

        marketListLayout = (RelativeLayout) view.findViewById(R.id.marketListLayout);
        establishMarketLayout = (RelativeLayout) view.findViewById(R.id.establishMarketLayout);

        gridView = (GridView) view.findViewById(R.id.gridView);

        createNewClub = (FloatingActionButton) view.findViewById(R.id.createNewMarket);

        edtName = (EditText) view.findViewById(R.id.edtName);
        edtDescription = (EditText) view.findViewById(R.id.edtDescription);
        edtOfficeLocation = (EditText) view.findViewById(R.id.edtOfficeLocation);
        btnChoose = (Button) view.findViewById(R.id.btnChoose);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        imageView = (ImageView) view.findViewById(R.id.imageView);

    }

    ImageView imageViewMarket;
    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_club_activity);
        dialog.setTitle("Update");

        imageViewMarket = (ImageView) dialog.findViewById(R.id.imageViewFood);

        //Log.i("view", dialog.toString());
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        final EditText edtEstablishedYear = (EditText) dialog.findViewById(R.id.edtEstablishedYear);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewMarket.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }

        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sqLiteHelper.updateDataMarket(edtName.getText().toString().trim(),
                            Double.parseDouble(edtOfficeLocation.getText().toString().trim()),
                            imageViewToByte(imageViewMarket),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateMarketList();
            }
        });
    }

    private void showDialogDelete(final int idClub){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getContext());

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    sqLiteHelper.deleteDataMarket(idClub);
                    Toast.makeText(getContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateMarketList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateMarketList(){
        // get all data from sqlite
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM MARKET");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            Double officeLocation = cursor.getDouble(3);
            byte[] image = cursor.getBlob(4);

            list.add(new Market(name, description, officeLocation, image, id));
        }
        adapter.notifyDataSetChanged();
    }

    public void setdatabase() {
//        sqLiteHelper = new SQLiteHelper(getActivity(), "MarketDB", null, 1);
//
//        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS MARKET(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB)");
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
