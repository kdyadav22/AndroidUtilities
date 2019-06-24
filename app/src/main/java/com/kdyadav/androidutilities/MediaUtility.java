package com.kdyadav.androidutilities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by Kapil Deo Yadav on 1/21/2016
 */
public class MediaUtility {

    private MediaRecorder myRecorder;
    private String outputFile = null;
    CountDownTimer cdt;
    int i = 0;

    Bitmap bitmap;
    InputStream in;
    String filename = "";
    Uri fileUri;
    Activity c;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int REQUEST_AUDIO_EXTERNAL_STORAGE = 2;
    private static String[] PERMISSIONS_STORAGE_AUDIO = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAPTURE_AUDIO_OUTPUT
    };

    private static final int REQUEST_VIDEO_EXTERNAL_STORAGE = 3;
    private static String[] PERMISSIONS_STORAGE_VIDEO = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAPTURE_VIDEO_OUTPUT
    };


    public static void saveOrieantedImage(Bitmap bitmap, File file) {

        OutputStream os;
        try {
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static void verifyAudioStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE_AUDIO,
                    REQUEST_AUDIO_EXTERNAL_STORAGE
            );
        }
    }

    public static void verifyVideoStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE_VIDEO,
                    REQUEST_VIDEO_EXTERNAL_STORAGE
            );
        }
    }

    public MediaUtility(Activity act) {
        c = act;
    }

    public static void saveOrientedImage(Bitmap bitmap, File file) {

        OutputStream os;
        try {
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public Uri getMediaFileURI(String dirName, String folderName, String mediaName) {
        File mediaFile = null;
        try {
            File mediaStorageDir = new File(String.format("%s/%s/%s", Environment.getExternalStorageDirectory(), dirName, folderName));
            // Create a media file name
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + mediaName);
            if (!mediaFile.exists()) {
                mediaFile.getParentFile().mkdirs();
                mediaFile.createNewFile();
            }
        } catch (Exception e) {
            Toast.makeText(c, "URI Error=" + e,
                    Toast.LENGTH_LONG).show();
            e.getMessage();
        }
        return Uri.fromFile(mediaFile);
    }

    public Uri getMediaFileURI(String dirName, String folderName, String mediaName, Activity activity) {
        File mediaFile = null;
        try {
            File mediaStorageDir = new File(String.format("%s/%s/%s", Environment.getExternalStorageDirectory(), dirName, folderName));
            // Create a media file name
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + mediaName);
            if (!mediaFile.exists()) {
                mediaFile.getParentFile().mkdirs();
                mediaFile.createNewFile();
            }
        } catch (Exception e) {
            Toast.makeText(c, "URI Error=" + e,
                    Toast.LENGTH_LONG).show();
            e.getMessage();
        }


        Uri photoURI = FileProvider.getUriForFile(activity,
                BuildConfig.APPLICATION_ID + ".provider",
                mediaFile);


        return photoURI;
    }

    public File getOutputMediaFile(String dirName, String folderName, String mediaName) {
        File mediaFile = null;
        try {
            File mediaStorageDir = new File(String.format("%s/%s/%s", Environment.getExternalStorageDirectory(), dirName, folderName));
            // Create a media file name
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + mediaName);
            if (!mediaFile.exists()) {
                mediaFile.getParentFile().mkdirs();
                mediaFile.createNewFile();
            }
        } catch (Exception e) {
            Toast.makeText(c, "URI Error=" + e,
                    Toast.LENGTH_LONG).show();
        }
        return mediaFile;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public Bitmap getRotatedBitmap(String path, Bitmap bitmap) {
        Bitmap rotatedBitmap = null;
        Matrix m = new Matrix();
        ExifInterface exif = null;
        int orientation = 1;

        try {
            if (path != null) {
                // Getting Exif information of the file
                exif = new ExifInterface(path);
            }
            if (exif != null) {
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        m.preRotate(270);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        m.preRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        m.preRotate(180);
                        break;
                    default:

                }
                // Rotates the image according to the orientation
                rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            }
        } catch (IOException e) {
            e.getMessage();
        }

        return rotatedBitmap;
    }

    public static void playAudio(Activity activity, String filePath) {
        try {
            Intent viewMediaIntent = new Intent();
            viewMediaIntent.setAction(Intent.ACTION_VIEW);
            Uri audio = Uri.parse(filePath);
            viewMediaIntent.setDataAndType(audio, "audio/*");
            viewMediaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Intent ii = Intent.createChooser(viewMediaIntent, "Play Music");
            activity.startActivity(ii);
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void playVideo(Activity activity, String filePath) {
        try {
            Intent viewMediaIntent = new Intent();
            viewMediaIntent.setAction(Intent.ACTION_VIEW);
            Uri audio = Uri.parse(filePath);
            viewMediaIntent.setDataAndType(audio, "video/*");
            viewMediaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Intent ii = Intent.createChooser(viewMediaIntent, "Play Video");
            activity.startActivity(ii);
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static boolean removeDirectory(File directory) {

        // System.out.println("removeDirectory " + directory);

        if (directory == null)
            return false;
        if (!directory.exists())
            return true;
        if (!directory.isDirectory())
            return false;


        final File to = new File(directory.getAbsolutePath() + System.currentTimeMillis());
        directory.renameTo(to);
        to.delete();

        /*String[] list = directory.list();

        // Some JVMs return null for File.list() when the
        // directory is empty.
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                File entry = new File(directory, list[i]);

                //        System.out.println("\tremoving entry " + entry);

                if (entry.isDirectory()) {
                    if (!removeDirectory(entry))
                        return false;
                } else {
                    if (!entry.delete())
                        return false;
                }
            }
        }*/

        return to.delete();
    }
}