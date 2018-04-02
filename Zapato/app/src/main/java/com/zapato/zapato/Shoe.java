package com.zapato.zapato;

/**
 * Created by agustincards on 3/21/18.
 */

public class Shoe {
   public String name;
   public int size;
   public final int drawableId;

   public Shoe(String n, int s, int drawId){
      name=n;
      size=s;
      drawableId=drawId;
   }
}
