
package com.kdyadav.androidutilities;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;


public class CustomTextUtil {

    public static String getColoredSpannedString(String text, String color) {
        if (TextUtils.isEmpty(color))
            color = "#000000";
        return "<font color=" + color + ">" + text + "</font>";
    }

    public static Spanned getSpannedText(String text1, String text2) {
        return Html.fromHtml(text1 + text2);
    }


    public static void setTextBulletColor(ImageView view, int color) {
        if (view == null) return;
        GradientDrawable bgShape = (GradientDrawable) view.getDrawable();
        bgShape.setColor(ContextCompat.getColor(view.getContext(), R.color.colorAccent));
    }

    public static Spanned getStringFromHtml(String str) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(str);
        }
    }

    public static void setFontForWebview(WebView web, float fontSize) {
        final WebSettings webSettings = web.getSettings();
        webSettings.setDefaultFontSize((int) fontSize);
    }

    /**
     * Methord to add addtional size for web view
     **/
    public static void setFontForWebview(WebView web, float fontSize, float addtionalSize) {
        setFontForWebview(web, fontSize + addtionalSize);
    }

    public static String setDifferentFontSizeForText(String text, float propotion, int start, int end) {
        SpannableString commentsSpan = new SpannableString(text);
        commentsSpan.setSpan(new RelativeSizeSpan(propotion), start, end, 0);
        return commentsSpan.toString();
    }

    public static Spannable getSpannedText(String slugColor, String slug, String heading) {
        if (TextUtils.isEmpty(slug)) {
            return new SpannableString(heading);
        }
        slug = slug + " / ";
        String text = slug + heading;
        Spannable spanText = new SpannableString(text);
        spanText.setSpan(new ForegroundColorSpan(Color.parseColor(slugColor)), 0, slug.length(), 0);
        return spanText;
    }

    public static Spannable getText(int slugColor, String slug, String heading) {
        if (TextUtils.isEmpty(slug)) {
            return new SpannableString(heading);
        }
        slug = slug + " / ";
        String text = slug + heading;
        Spannable spanText = new SpannableString(text);
        spanText.setSpan(new BackgroundColorSpan(slugColor), 0, text.length(), 0);
        return spanText;
    }

    public static Spannable getDiffColorText(int slugColor, String slug, String textWithChangeColor) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString str2= new SpannableString(textWithChangeColor);
        str2.setSpan(new ForegroundColorSpan(slugColor), 0, str2.length(), 0);
        builder.append(str2);

        SpannableString str1= new SpannableString(slug);
        //str1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str1.length(), 0);
        builder.append(str1);


        return builder;
    }

}
