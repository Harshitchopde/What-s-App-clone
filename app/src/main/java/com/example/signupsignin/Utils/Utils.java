package com.example.signupsignin.Utils;

import android.content.Context;
import android.os.Vibrator;

import com.example.signupsignin.Model.Users;

import java.util.ArrayList;

public class Utils {
//    public static  ArrayList<Users> arrayList;
//    public static  void getArrayList(){
//        if(arrayList==null){
//            arrayList = new ArrayList<>();
//        }
//
//    }
  public static void vibrate(Context context){
      ((Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE))
              .vibrate(100);
  }
}
