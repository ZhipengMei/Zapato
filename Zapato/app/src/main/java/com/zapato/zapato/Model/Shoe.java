package com.zapato.zapato.Model;

import com.zapato.zapato.Network.FirebaseManager;

import java.net.URL;

/**
 * Created by agustincards on 3/21/18.
 */

public class Shoe {

    public String brand;
    public String name;
    public String sellerID;  // sellerID is used for fetching seller's info
    public double size;
    public double price;
    public String gender;    //male, female, unisex
    public String shoeType;  //running, basketball, soccer
    public String shoeColor; //red, black
    public String shoeImageUrl; //red, black


    public Shoe(String shoeName, String sellerID, double shoeSize, double shoePrice){
        this.name = shoeName;
        this.sellerID = sellerID;
        this.size = shoeSize;
        this.price = shoePrice;

        //TODO temporary default attribute,
        this.brand = "Nike";
        this.shoeType = "Running";
        this.gender = "Unisex";
        this.shoeColor = "Red";

   }

//    public Shoe(String shoeName, String sellerID, int shoeSize, int shoePrice, String shoeImage){
//        this.name = shoeName;
//        this.sellerID = sellerID;
//        this.size = shoeSize;
//        this.price = shoePrice;
//        this.shoeImageUrl = shoeImage;
//
//        //TODO temporary default attribute,
//        this.gender = "male";
//        this.shoeType = "Running";
//        this.gender = "Unisex";
//        this.shoeColor = "Red";
//
//    }

}
