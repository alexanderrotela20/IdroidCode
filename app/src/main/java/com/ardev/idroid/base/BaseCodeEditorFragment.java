package com.ardev.idroid.base;

import android.graphics.Typeface;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import androidx.annotation.MenuRes;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.appcompat.view.menu.MenuBuilder;
import io.github.rosemoe.sora.lang.Language;
import androidx.core.content.res.ResourcesCompat;
import com.google.android.material.elevation.SurfaceColors;

import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.widget.CodeEditor;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import io.github.rosemoe.sora.widget.ArSymbolInputView;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import org.eclipse.tm4e.core.internal.theme.reader.ThemeReader;
import org.eclipse.tm4e.core.theme.IRawTheme;
import com.ardev.idroid.app.IdroidApplication;
import com.ardev.idroid.R;


public abstract class BaseCodeEditorFragment extends BaseFragment {

public BaseCodeEditorFragment(String path) {
		super(path);
		}

	ArSymbolInputView symbol;
	protected CodeEditor editor;
	MaterialToolbar toolbar;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_code_editor, container, false);

		editor = v.findViewById(R.id.editor);
		symbol = v.findViewById(R.id.symbol);

		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle bundle) {
		super.onViewCreated(view, bundle);
		readText();
			
			symbol.bindEditor(editor);

			symbol.addSymbols(getSymbolsName(), getSymbols());
		
		//symbol.setBackgroundColor(SurfaceColors.SURFACE_2.getColor(requireActivity()));
			  symbol.setTypefaceText(ResourcesCompat.getFont(requireContext(), R.font.jetbrains_mono_regular));
  editor.setTypefaceText(ResourcesCompat.getFont(requireContext(), R.font.jetbrains_mono_regular));
			editor.setLigatureEnabled(true);
			editor.setNonPrintablePaintingFlags(CodeEditor.FLAG_DRAW_WHITESPACE_LEADING);

			editor.setInputType(EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS | EditorInfo.TYPE_CLASS_TEXT
					| EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

			editor.getProps().useICULibToSelectWords = true;
			editor.getProps().symbolPairAutoCompletion = true;
			editor.getProps().deleteMultiSpaces = -1;
			editor.getProps().deleteEmptyLineFast = false;
	
		//UtilKt.addSystemWindowInsetToMargin(symbol, false, false, false, true);
		initCodeView();
		              
    mainViewModel.isIndexing()
                .observe(getViewLifecycleOwner(), indexing -> {
                   editor.setEditable(!indexing);
                });
                
                
                
	}

  @Override
    public void onPause() {
        super.onPause();
	saveText();

}


	 @Override
	public void onResume() {
		super.onResume();
	readText();
}
	
	
   @Override 
   protected void setMenu(Menu menu) {
   
   menu.add("Guardar").setOnMenuItemClickListener(item -> {
    saveText();
	return true;
    });
   }
	
	
	
	

	private void initCodeView() {
		try {

			EditorColorScheme editorColorScheme = editor.getColorScheme();
			editorColorScheme = TextMateColorScheme.create(getIRawTheme());
			editor.setColorScheme(editorColorScheme);
			editor.setEditorLanguage(getLanguage(editorColorScheme));

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	

	protected IRawTheme getIRawTheme() {
	try {
	if(IdroidApplication.isDarkMode(requireContext())){
	
		IRawTheme iRawTheme =  ThemeReader.readThemeSync("darcula.json",
requireContext().getAssets().open("textmate/darcula.json"));
	return  iRawTheme;
	
	} else {
	IRawTheme iRawTheme =  ThemeReader.readThemeSync("QuietLight.tmTheme",
requireContext().getAssets().open("textmate/QuietLight.tmTheme"));
	return  iRawTheme;
	
	}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	    
	
	return  null;
	}
	
	
	
	protected void readText() {
	try {
			editor.setText(FileUtils.readFileToString(new File(getPath()), StandardCharsets.UTF_8));
	} catch (Exception e) {
		}
	}
	
	protected void saveText(){
				try {
                    FileUtils.writeStringToFile(new File(getPath()), editor.getText().toString(),
                       StandardCharsets.UTF_8);
                       
              	  } catch (IOException e) {
                    // ignored
                }
	}
		protected void saveText(String text){
				try {
                 FileUtils.writeStringToFile(new File(getPath()), text,
                       StandardCharsets.UTF_8);
                       
              	  } catch (IOException e) {
                    // ignored
                }
	}

	
	
	protected abstract String[] getSymbolsName();

	protected abstract String[] getSymbols();

	//protected abstract IRawTheme getIRawTheme();

	protected abstract Language getLanguage(EditorColorScheme scheme);

}