package com.ardev.component.dialog;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.ViewGroup;
import androidx.core.graphics.ColorKt;
 
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
 
import com.ardev.idroid.ext.ColorUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.app.Dialog;
import  com.ardev.idroid.R;
import android.widget.ImageView;
import com.google.android.material.card.MaterialCardView;
import com.ardev.component.view.colorpicker.ColorPicker;
import com.ardev.component.view.colorpicker.listeners.SimpleColorSelectionListener;


public class ColorPickerDialog extends DialogFragment {

    private AlertDialog dialog;
    private String mColor;
    private DialogListener mListener; 
    public void setOnColorSelected( DialogListener mListener) { this.mListener = mListener; }
	private MaterialCardView preview;
	private ColorPicker colorPicker;
	private int colorSelected;
	public void setCurrentColor(String color) {
	mColor = ColorUtils.getColor(color);
	
	}

public ColorPickerDialog() {}

 @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
final View view = LayoutInflater.from(getActivity()).inflate(R.layout.colorpicker_dialog, (ViewGroup) getView(), false);
initView(view);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Seleccionar color");
        builder.setView(view);
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton(
                "Elegir", (dlg, i) -> {
					if(mListener != null) {
						
						mListener.onColorSelected(colorSelected);
						dismiss();
						
					}
					 
					});
                    
      dialog = builder.create();
       dialog.setCanceledOnTouchOutside(false);
      view.post(() -> {                       
   dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
});

              
              
        return dialog;
    }
    
    private void initView(View view) {

preview = view.findViewById(R.id.preview);
colorPicker = view.findViewById(R.id.colorPicker);
colorPicker.setColor(Color.parseColor(mColor));
preview.setCardBackgroundColor(Color.parseColor(mColor));
        colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelected(int color) {
           preview.setCardBackgroundColor(color);
            
            
           colorSelected = color;
          dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
           
            }
        });
		colorSelected = Color.parseColor(mColor);
        
      }
      
      
       public interface DialogListener {
        void onColorSelected(int color);
    }

        
        
 }