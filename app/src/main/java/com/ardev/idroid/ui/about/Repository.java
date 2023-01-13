package com.ardev.idroid.ui.about;

import androidx.core.content.res.ResourcesCompat;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense;
import android.net.Uri;
import com.ardev.idroid.ext.ActivityUtils;
import com.ardev.idroid.ui.about.LicenseFragment;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.ardev.idroid.R;
import androidx.appcompat.app.AppCompatActivity;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import android.content.Context;

	public  class Repository {
	 private static Context mContext;
	
	public static MaterialAboutList createAboutList( final Context context, AppCompatActivity activity) {
							mContext = context;
	MaterialAboutCard appCard = new MaterialAboutCard.Builder()
                 
               
             .addItem(new MaterialAboutTitleItem.Builder()
                .text("Idroid Code")
                .desc("© 2022 Alexander Rotela")
                .icon(R.drawable.logo)
                .build())
                
				
              
                .addItem(ConvenienceBuilder.createVersionActionItem(context,
                getDrawable(R.drawable.ic_info)
                         
                        ,
                        "Versión",
                        true))
                        
                        .addItem(new MaterialAboutActionItem.Builder()
                .text("Registro de cambios")
                .icon(R.drawable.ic_changelog)
                .setOnClickAction(ConvenienceBuilder.createWebViewDialogOnClickAction(context, "Registro de cambios", "file:///android_asset/changelogs.html", true, false))
                .build())
                
                
                .addItem(new MaterialAboutActionItem.Builder()
                .text("Licencias")
             	.icon(R.drawable.ic_license)
                 .setOnClickAction( () -> {
                 LicenseFragment newFragment = new LicenseFragment();
                 ActivityUtils.showFragment(activity, newFragment);
                 
                 })
                .build())    
                 .outline(false)             
                       .build();
                
                 MaterialAboutCard authorCard = new MaterialAboutCard.Builder()
                 .title("Autor")
                   .outline(false)
.addItem(new MaterialAboutActionItem.Builder()
                .text("Alexander Rotela")
                .subText("Paraguay")
                 .icon(R.drawable.ic_dev)
                
                .build()).build();
                
                
                     
                        
                        
                        
                        MaterialAboutCard communityCard = new MaterialAboutCard.Builder()
                .title("Redes sociales")
                  .outline(false)
                .addItem(ConvenienceBuilder.createWebsiteActionItem(context,
                        getDrawable(R.drawable.ic_facebook),
                        "Facebook",
                        false,
                        Uri.parse("https://facebook.com/alexander.rotela.18")))
                         .addItem(ConvenienceBuilder.createWebsiteActionItem(context,
                        getDrawable(R.drawable.ic_instagram),
                        "Instagram",
                        false,
                        Uri.parse("https://discord.gg/pffnyE6prs"))) 
                        .addItem(ConvenienceBuilder.createWebsiteActionItem(context,
                        getDrawable(R.drawable.ic_twitter),
                        "Twitter",
                        false,
                        Uri.parse("https://discord.gg/pffnyE6prs")))
                .addItem(ConvenienceBuilder.createWebsiteActionItem(context,
                        getDrawable(R.drawable.ic_telegram),
                        "Telegram",
                        false,
                        Uri.parse("https://t.me/alexanderrotela")))
                .build();
                
                
                
                
                
                 
                
                
                
                
                MaterialAboutCard otherCard = new MaterialAboutCard.Builder()
         .title("Otros")
		   .outline(false)
        
        
       // .cardColor(Color.parseColor("#c0cfff"))
 .addItem(new MaterialAboutActionItem.Builder()
 .icon(getDrawable(R.drawable.ic_apoyar))
 .text("Apoyar").build())
 .addItem(new MaterialAboutActionItem.Builder()
 .icon(getDrawable(R.drawable.ic_share))
 .text("Compartir").build())
         .addItem(ConvenienceBuilder.createEmailItem(context,
                        getDrawable(R.drawable.ic_coment),
                        "Enviar comentarios",
                        false,
                        "alexanderrotela2000@gmail.com",
                        "")).build();
                        
                      MaterialAboutCard moreCard = new MaterialAboutCard.Builder()
                        .outline(false)
                      .addItem(new MaterialAboutActionItem.Builder()
                .icon(getDrawable(R.drawable.ic_apps))
                .text("Más aplicaciones").build())
                .build();
                        
       
                
                
                return new MaterialAboutList.Builder()
                .addCard(appCard)
                 .addCard(authorCard)
                .addCard(communityCard)
                 .addCard(otherCard)
                 .addCard(moreCard)
                .build();
}


	public static MaterialAboutList createLicenseList(final Context c) {
								mContext = c;

        MaterialAboutCard materialAboutLibraryLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                getDrawable(R.drawable.ic_license),
                "material-about-library", "2016", "Daniel Stone",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard androidIconicsLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                getDrawable(R.drawable.ic_license),
                "Android Iconics", "2016", "Mike Penz",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard leakCanaryLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                getDrawable(R.drawable.ic_license),
                "LeakCanary", "2015", "Square, Inc",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard mitLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                getDrawable(R.drawable.ic_license),
                "MIT Example", "2017", "Matthew Ian Thomson",
                OpenSourceLicense.MIT);

        MaterialAboutCard gplLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                getDrawable(R.drawable.ic_license),
                "GPL Example", "2017", "George Perry Lindsay",
                OpenSourceLicense.GNU_GPL_3);

        return new MaterialAboutList(materialAboutLibraryLicenseCard,
                androidIconicsLicenseCard,
                leakCanaryLicenseCard,
                mitLicenseCard,
                gplLicenseCard);
    }
    
    
    private static Drawable getDrawable(@DrawableRes int drawable) {
        return ResourcesCompat.getDrawable(requireContext().getResources(),
                drawable,
                requireContext().getTheme());
    }

	private static Context requireContext() {
	return mContext;
	
	}

}