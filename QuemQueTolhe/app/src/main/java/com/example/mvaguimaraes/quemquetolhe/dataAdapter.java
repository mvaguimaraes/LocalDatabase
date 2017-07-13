package com.example.mvaguimaraes.quemquetolhe;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.app.AlertDialog;

import java.util.ArrayList;

/**
 * Created by Mvaguimaraes on 3/18/17.
 */

public class dataAdapter extends ArrayAdapter<Contact> {

    Context context;
    ArrayList<Contact> mcontact;


    public dataAdapter(Context context, ArrayList<Contact> contact){
        super(context, R.layout.listcontacts, contact);
        this.context=context;
        this.mcontact=contact;
    }

    public  class  Holder{
        TextView nameFV,availability;
        ImageView pic,delete;
        Switch ActiveSwitch;
        private Contact dataModel;
        private DatabaseHandler db;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        Contact data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        final Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {


            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listcontacts, parent, false);

            viewHolder.nameFV = (TextView) convertView.findViewById(R.id.txtViewer);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.imgView);
            viewHolder.ActiveSwitch = (Switch) convertView.findViewById(R.id.ActiveSwitch);
            viewHolder.availability = (TextView) convertView.findViewById(R.id.txtViewer2);
            viewHolder.delete = (ImageView) convertView.findViewById(R.id.Delete);

            viewHolder.db = new DatabaseHandler(getContext());


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }

        if(data.getActive().equals("true")){

            viewHolder.nameFV.setText("Nome: "+data.getFName() + "\nDisponível: Sim" + "\nTipo de usuário: " + data.getType());

        } else if(data.getActive().equals("false")){

            viewHolder.nameFV.setText("Nome: "+data.getFName() + "\nDisponível: Não" + "\nTipo de usuário: " + data.getType());

        }
        //viewHolder.availability.setText("Available: " + data.getActive());
        viewHolder.pic.setImageBitmap(convertToBitmap(data.getImage()));

        final ArrayList<Contact> contacts = new ArrayList<>(viewHolder.db.getAllContacts());
        viewHolder.dataModel = contacts.get(position);

        if (viewHolder.dataModel.getActive().equals("true")) {
            viewHolder.ActiveSwitch.setChecked(true);
        } else if (viewHolder.dataModel.getActive().equals("false")){
            viewHolder.ActiveSwitch.setChecked(false);
        }

        viewHolder.ActiveSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    viewHolder.dataModel.setActiveStatus("true");
                    viewHolder.db.updateContact(viewHolder.dataModel,viewHolder.dataModel.getID());
                    viewHolder.ActiveSwitch.setChecked(true);
                    viewHolder.nameFV.setText("Nome: "+ viewHolder.dataModel.getFName() + "\nDisponível: Sim" + "\nTipo de usuário: " + viewHolder.dataModel.getType());
                    //viewHolder.availability.setText("Available: " + viewHolder.dataModel.getActive());

                } else {

                    viewHolder.dataModel.setActiveStatus("false");
                    viewHolder.db.updateContact(viewHolder.dataModel,viewHolder.dataModel.getID());
                    viewHolder.ActiveSwitch.setChecked(false);
                    viewHolder.nameFV.setText("Name: "+ viewHolder.dataModel.getFName() + "\nDisponível: Não" + "\nTipo de usuário: " + viewHolder.dataModel.getType());
                    //viewHolder.availability.setText("Available: " + viewHolder.dataModel.getActive());

                }
            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Você realmente deseja deletar " + viewHolder.dataModel.getFName() + "?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                viewHolder.db.deleteContact(viewHolder.dataModel.getID());
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Não",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        //viewHolder.db.updateContact(contacts,position);


        // Return the completed view to render on screen
        return convertView;
    }
    //get bitmap image from byte array

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

}