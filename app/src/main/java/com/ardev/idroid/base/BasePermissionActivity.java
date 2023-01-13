package com.ardev.idroid.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public abstract class BasePermissionActivity extends BaseActivity {

	private ActivityResultLauncher<String[]> mPermissionLauncher;
	private final ActivityResultContracts.RequestMultiplePermissions mPermissionsContract = new ActivityResultContracts.RequestMultiplePermissions();
     



		@Override
		protected void onCreate(Bundle bundle) {
	super.onCreate(bundle);
	mPermissionLauncher = registerForActivityResult(mPermissionsContract, isGranted -> {
		if (isGranted.containsValue(false)) {
			
			new MaterialAlertDialogBuilder(this)
			.setTitle("Permiso denegado")
			.setMessage("Es necesario que aceptes los permisos para que la app funcione correctamente")
			.setPositiveButton("Conceder", (d, which) -> {
				
				requestPermissions();
			})
			.setNegativeButton( "Cancelar", (d, which) -> {
				
				
			})
			.show();
			
			
			} else{
			checkPermissions();
		}
	});
	
	
	
	
	}
	@Override
		protected void onPostCreate(Bundle bundle) {
	super.onPostCreate(bundle);
	checkPermissions();
	
	}
	

	@Override
	public void onResume() {
		super.onResume();
			if (!permissionsGranted()) requestPermissions();
            
		
		}
		
		
		
		private void requestPermissions() {
        mPermissionLauncher.launch(
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE});
    }
   
     private boolean permissionsGranted() {
        return ContextCompat.checkSelfPermission(BasePermissionActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(BasePermissionActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    
		
private void checkPermissions() {

	if (permissionsGranted()) {
     onPermissionsGranted();
     } else {
                requestPermissions();
            }
}


public void cambiarActivity(Context c, Class<?> mClass) {
        Intent i = new Intent();
        i.setClass(c, mClass);
        startActivity(i);  
    }

public abstract void onPermissionsGranted();

}