-ignorewarnings
-dontobfuscate
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-keep class com.sun.** { *; }
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**