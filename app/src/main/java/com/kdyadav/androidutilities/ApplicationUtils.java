package com.kdyadav.androidutilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import static java.lang.System.out;


public class ApplicationUtils {

    public static ProgressDialog displayProgressDialog(Context context, String title, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setTitle(title);
        return progressDialog;
    }

    public static boolean isConnectingToInternet(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                try {
                    if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                        return true;
                    }
                } catch (Exception e) {
                    e.getMessage();
                    return false;
                }

            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Logger.getLogger(String.valueOf(mContext.getClass())).info("Network" +
                                    "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void myCustomToast(Context context, String toastmsg, boolean islong, boolean mood) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custome_toast, null);
        TextView toast_text = view.findViewById(R.id.toast_text);
        if (mood) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_grey_600));
        } else {
            view.setBackgroundColor(Color.RED);


        }
        toast_text.setText(toastmsg);
        int margin = context.getResources().getDimensionPixelSize(R.dimen._10sdp);
        Toast toast = new Toast(context);

        toast.setGravity(Gravity.BOTTOM, 0, margin);

        // int dur = (islong) ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT;
        toast.setDuration((islong) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();

//        Snackbar.make(view, toastmsg, dur).show();
    }

    public static void showErrorAlert(FragmentActivity context, String title, String message) {
        try {
            AlertDialog.Builder ab = new AlertDialog.Builder(context);
            ab.setMessage(message);
            ab.setNegativeButton(R.string.dismiss, (dialog, which) -> {

            });
            ab.create().show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exception", e.toString());
        }

    }

    public static void showServerAlert(FragmentActivity context, Activity activity) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null, false);
            Button dialogButtonOK = view.findViewById(R.id.dialogButtonOK);
            TextView textViewTitle = view.findViewById(R.id.textViewTitle);
            TextView textViewMsg = view.findViewById(R.id.textViewMsg);
            textViewTitle.setText(R.string.server_error_title);
            textViewMsg.setText(R.string.server_error);
            builder.setView(view);
            final AlertDialog dialog = builder.create();
            dialogButtonOK.setOnClickListener(v -> {
                dialog.dismiss();
                Intent intent = new Intent(context, activity.getClass());
                context.startActivity(intent);
                context.finish();
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exception", e.toString());
        }
    }

    public static void dismissProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public static String bitmapToBase64(Bitmap bitmap, File imageFile) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap = fixOrientation(bitmap, imageFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap fixOrientation(Bitmap mBitmap, File imageFile) {
        if (mBitmap.getWidth() > mBitmap.getHeight()) {

            int rotate = 0;
            try {
                ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
            return mBitmap;
        } else {
            return mBitmap;
        }
    }

    public static int getColor(Context context, int id) {

        try {
            final int version = Build.VERSION.SDK_INT;
            if (version >= 23) {
                return ContextCompat.getColor(context, id);
            } else {
                return context.getResources().getColor(id);
            }
        } catch (Exception e) {
            return Color.GREEN;
        }
    }

    public static void composeEmail(String[] addresses, String subject, Uri attachment, Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void dialPhoneNumber(String phoneNumber, Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static Drawable getDrawableApp(Context context, int code) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getResources().getDrawable(code, context.getTheme());
        } else {
            return context.getResources().getDrawable(code);
        }
    }

    public static int calculateInSampleSize2(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(String filePath, int maxWidth, int maxHeight) {
        Log.e("dsbff", "method init");
        Log.e("dsbff", "filepath-> " + filePath);

        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);


        Log.e("dsbff", bitmap == null ? "bitmpa decode null" : "bitmap decode not null");

        int width = options.outWidth;
        int height = options.outHeight;
        Log.e("dsbff", "width-> " + width + " height-> " + height);
        boolean landscape;
        landscape = width > height;
        int finalWidth = maxWidth;
        int finalHeight = maxHeight;

        if (landscape) {
            if (width <= maxWidth) {
                finalWidth = width;
                finalHeight = height;
            } else {
                float ratio = (float) width / (float) height;
                Log.e("ratio", ratio + "");
                finalWidth = maxWidth;
                finalHeight = (int) (maxWidth / ratio);
            }


        } else {
            if (height <= maxHeight) {
                finalWidth = width;
                finalHeight = height;
            } else {
                float ratio = (float) width / (float) height;
                Log.e("ratio", ratio + "");
                finalHeight = maxHeight;
                finalWidth = (int) (maxHeight * ratio);
            }

        }
        Log.e("dsbff", "final width-> " + finalWidth + " final height-> " + finalHeight);

        // Calculate inSampleSize
        int inSampleSize = calculateInSampleSize2(options, finalWidth, finalHeight);
        Log.e("dsbff", "insample size " + inSampleSize);
        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        Log.e("dsbff", "sampled bmp width-> " + bmp.getWidth() + " height-> " + bmp.getHeight());

        if (landscape) {
            if (bmp.getWidth() > maxWidth) {
                bmp = Bitmap.createScaledBitmap(bmp, finalWidth, finalHeight, true);
            }
        } else {
            if (bmp.getHeight() > maxHeight) {
                bmp = Bitmap.createScaledBitmap(bmp, finalWidth, finalHeight, true);
            }

        }
        Log.e("dsbff", "final bmp width-> " + bmp.getWidth() + " height-> " + bmp.getHeight());

        return ExifUtil.rotateBitmap(filePath, bmp);
    }

    public static String timeDifference(String timeInMillisec) {


        long timeInMillisecL;
        if (timeInMillisec.trim().length() > 0) {
            timeInMillisecL = Long.parseLong(timeInMillisec);
        } else {
            timeInMillisecL = System.currentTimeMillis();
        }
//        long timeInMillisecL = Long.parseLong(timeInMillisec);


//        DateFormat df = DateFormat.getTimeInstance();
//        df.setTimeZone(TimeZone.getTimeZone("gmt"));
//        String gmtTime = df.format(new Date());
//        long currentTime = Long.parseLong(gmtTime);


        Calendar aGMTCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        long currentTime = aGMTCalendar.getTimeInMillis();

//        long currentTime = System.currentTimeMillis();
        //in milliseconds
//        long diff = (timeInMillisecL*1000) - currentTime;
        long diff = currentTime - (timeInMillisecL * 1000);

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        out.print(diffDays + " days, ");
        out.print(diffHours + " hours, ");
        out.print(diffMinutes + " minutes, ");
        out.print(diffSeconds + " seconds.");


        //
        Log.v("TAG", "Days:" + diffDays);
        Log.v("TAG", "hours:" + diffHours);
        Log.v("TAG", "minutes:" + diffMinutes);
        Log.v("TAG", "seconds:" + diffSeconds);


        if (diffDays > 0) {
            if (diffDays == 1) {
                return diffDays + " day ago";
            }
            return diffDays + " days ago";
        } else if (diffHours > 0) {
            if (diffHours == 1) {
                return diffHours + " hr ago";
            }
            return diffHours + " hrs ago";
        } else if (diffMinutes > 0) {
            if (diffMinutes == 1) {
                return diffMinutes + " min ago";
            }
            return diffMinutes + " mins ago";
        } else {
            return "Just Now";
//            return diffSeconds +" seconds ago";
        }

    }

    public static String longToDateFormat(long val) {
        Date date = new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("EEE. dd MMM - HH:mm");
        df2.setTimeZone(TimeZone.getDefault());
        return df2.format(date);
    }

    public static String longToDateFormatSecond(long val) {
        //Converting sec to MilliSec
        Date date = new Date(val * 1000);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMM yyyy");
        df2.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df2.format(date);
    }

    public static String longToDateTimeFormatSecond(long val) {
        //Converting sec to MilliSec
        Date date = new Date(val * 1000);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        df2.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df2.format(date);
    }

    public static String longToDateTimeFormatSecond24(long val) {
        //Converting sec to MilliSec
        Date date = new Date(val * 1000);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        df2.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df2.format(date);
    }

    public static boolean isEmailValid(String email) {

        if (email == null || email.isEmpty()) {
            return false;
        }
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}