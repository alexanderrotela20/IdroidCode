package com.ardev.idroid.ext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import com.ardev.idroid.app.AppProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Utils {


	public static String calculateMD5(File updateFile) {
		InputStream is;
		try {
			is = new FileInputStream(updateFile);
		} catch (FileNotFoundException e) {
			Log.e("calculateMD5", "Exception while getting FileInputStream", e);
			return null;
		}

		return calculateMD5(is);
	}

	public static String calculateMD5(InputStream is) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			Log.e("calculateMD5", "Exception while getting Digest", e);
			return null;
		}

		byte[] buffer = new byte[8192];
		int read;
		try {
			while ((read = is.read(buffer)) > 0) {
				digest.update(buffer, 0, read);
			}
			byte[] md5sum = digest.digest();
			BigInteger bigInt = new BigInteger(1, md5sum);
			String output = bigInt.toString(16);
			// Fill to 32 chars
			output = String.format("%32s", output).replace(' ', '0');
			return output;
		} catch (IOException e) {
			throw new RuntimeException("Unable to process file for MD5", e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Log.e("calculateMD5", "Exception on closing MD5 input stream", e);
			}
		}
	}

	public static int dp(float px) {
    return Math.round(AppProvider.getApplicationContext().getResources().getDisplayMetrics().density * px);
    }
    
    public static int getRowCount(int itemWidth) {
		DisplayMetrics displayMetrics = AppProvider.getApplicationContext().getResources().getDisplayMetrics();

		return (displayMetrics.widthPixels / itemWidth);
	}


}