package com.example.mvaguimaraes.quemquetolhe;

/**
 * Created by Mvaguimaraes on 3/18/17.
 */

public class Contact {

    //private variables
    int _id;
    String _fname;
    String _active;
    String _ftype;
    byte[] _img;




    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String fname,String active, String type, byte[] img){
        this._id = id;
        this._fname = fname;
        this._active = active;
        this._img = img;
        this._ftype = type;

    }

    // constructor
    public Contact(String fname,String active, String type, byte[] img){

        this._fname = fname;
        this._active = active;
        this._ftype = type;
        this._img = img;


    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting first name
    public String getFName(){
        return this._fname;
    }

    // setting first name
    public void setFName(String fname){
        this._fname = fname;
    }

    //getting active status
    public String getActive() {
        return this._active;
    }

    public void setActiveStatus(String active) {
        this._active = active;
    }

    public String getType() {
        return this._ftype;
    }

    public void setType(String type) {
        this._ftype = type;
    }

    //getting profile pic
    public byte[] getImage(){
        return this._img;
    }

    //setting profile pic

    public void setImage(byte[] b){
        this._img=b;
    }



}