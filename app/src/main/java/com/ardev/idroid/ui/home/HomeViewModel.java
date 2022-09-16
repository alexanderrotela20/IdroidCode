package com.ardev.idroid.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;



public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> currentPath = new MutableLiveData<String>();
	
    
	SavedStateHandle ssh;
public  HomeViewModel(SavedStateHandle ssh) {
    
	 this.ssh = ssh;
	} 
    
       public LiveData<String> getFatherPath() {
         return  ssh.getLiveData("father");
    }

    public void setFatherPath(String father) {
		ssh.set("father", father);
       }
         
}
