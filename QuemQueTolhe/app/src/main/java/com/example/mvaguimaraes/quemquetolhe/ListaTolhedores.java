package com.example.mvaguimaraes.quemquetolhe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.example.mvaguimaraes.quemquetolhe.MainActivity.randomNum;

/**
 * Created by Mvaguimaraes on 3/18/17.
 */

public class ListaTolhedores extends Activity {

    private EditText fname;
    private ImageView pic;
    private DatabaseHandler db;
    private String f_name;
    private String f_active;
    private String f_type;
    private ListView lv;
    private dataAdapter data;
    private Contact dataModel;
    private Bitmap bp;
    private byte[] photo;
    private Switch activeSwitch;
    private Spinner spinner;
    private static final String[]paths = {"Pessoa", "Evento", "Evento com pessoa"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_tolhedores);

        //Instantiate database handler
        db=new DatabaseHandler(this);

        lv = (ListView) findViewById(R.id.list1);
        pic= (ImageView) findViewById(R.id.pic);
        fname=(EditText) findViewById(R.id.txt1);
        activeSwitch = (Switch) findViewById(R.id.ActiveSwitch);
        spinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListaTolhedores.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        LinearLayout layout =(LinearLayout)findViewById(R.id.activity_main);

        if (randomNum == 3){
            layout.setBackgroundResource(R.drawable.bg3_light);
        } else if (randomNum == 4){
            layout.setBackgroundResource(R.drawable.bg4_light);
        } else if (randomNum == 5){
            layout.setBackgroundResource(R.drawable.bg5_light);
        } else if (randomNum == 6){
            layout.setBackgroundResource(R.drawable.bg8_light);
        } else if (randomNum == 7){
            layout.setBackgroundResource(R.drawable.bg10_light);
        }

    }

    public void buttonClicked(View v){
        int id=v.getId();

        switch(id){

            case R.id.save:

                if(fname.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"O campo nome está vazio!", Toast.LENGTH_LONG).show();
                }  else{

                    addContact();
                }

                break;

            case R.id.display:

                ShowRecords();
                break;
            case R.id.pic:
                selectImage();
                break;
        }
    }

    public void selectImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 2:
                if(resultCode == RESULT_OK){
                    Uri choosenImage = data.getData();

                    if(choosenImage !=null){

                        bp=decodeUri(choosenImage, 400);
                        pic.setImageBitmap(bp);
                    }
                }
        }
    }


    //COnvert and resize our image to 400dp for faster uploading our images to DB
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }



    // function to get values from the Edittext and image
    private void getValues(){
        f_name = fname.getText().toString();
        f_active = String.valueOf(activeSwitch.isChecked());
        photo = profileImage(bp);
        f_type = spinner.getSelectedItem().toString();
    }

    //Insert data to the database
    private void addContact(){
        getValues();

        //Toast.makeText(getApplicationContext(),"Name: " + fname + "\nActive: " + f_active + "\nType: " + f_type + "\nFoto: " + photo, Toast.LENGTH_LONG).show();

        db.addContacts(new Contact(f_name,f_active,f_type,photo));
        Toast.makeText(getApplicationContext(),"Salvo com sucesso", Toast.LENGTH_LONG).show();
    }

    //Retrieve data from the database and set to the list view
    private void ShowRecords(){
        final ArrayList<Contact> contacts = new ArrayList<>(db.getAllContacts());
        data=new dataAdapter(this, contacts);

        lv.setBackgroundColor(Color.parseColor("#ffffff"));
        lv.setAdapter(data);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dataModel = contacts.get(position);
                //System.out.println(contacts.get(position)._id + contacts.get(position)._fname + contacts.get(position)._active + contacts.get(position)._img);

                /*AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
                builder1.setMessage("Id: " + contacts.get(position)._id + "\nName: " + contacts.get(position)._fname + "\nStatus: " + contacts.get(position)._active);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();*/

                Toast.makeText(getApplicationContext(),String.valueOf(dataModel.getID()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

