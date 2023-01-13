package com.ardev.idroid.ext;

import androidx.fragment.app.Fragment;
import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class  ActivityUtils {


public static void showFragment( AppCompatActivity activity, Fragment newFragment){

 activity.getSupportFragmentManager().beginTransaction()
   .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
   .add(android.R.id.content, newFragment)
   .addToBackStack(null)
   .commit();

}


}