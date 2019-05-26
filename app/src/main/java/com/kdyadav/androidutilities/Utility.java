package com.kdyadav.androidutilities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.floor;
import static java.lang.Math.random;
import static java.lang.Math.round;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

/**
 * Created by Kapil Deo Yadav on 28/08/2017
 * Panalinks Infotech Ltd
 */
public class Utility {
    public String TAG = "Utility";
    public static final String DATE_TIME = "dd MMM yyyy HH:mm:ss z";
    private static int colorAccent = -1;
    public static String[] usernameStringArray(String userID) {
        String str[] = new String[userID.length() - 2];
        for (int i = 0; i < str.length; i++) {
            str[i] = userID.substring(i, i + 3);
        }
        return str;
    }

    public static boolean isValidPassword(String[] userID, String password) {
        for (String anUserID : userID) {
            if (password.contains(anUserID)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> usernameStringArrayList(String userID) {
        List<String> substrings = new ArrayList<>();
        for (int i = 0; i < userID.length() - 2; i++) {
            substrings.add(userID.substring(i, i + 3));
        }
        //return !substrings.Any(s -> newPassword.contains(s));
        return substrings;
    }

    public static boolean isNotNullNotEmptyNotWhiteSpace(final String string) {
        return string != null && !string.isEmpty() && !string.trim().isEmpty();
    }

    public static boolean matchString(String str1, String str2) {
        return str1.equals(str2);
    }

    public static int generateIntegerId() {
        int text = 0;
        String possible = "0123456789";

        for (int i = 0; i < possible.length(); i++)
            text += possible.charAt((int) floor(random() * possible.length()));

        return text;
    }

    public static String generateUserID() {
        StringBuilder text = new StringBuilder();
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < 11; i++)
            text.append(possible.charAt((int) floor(random() * possible.length())));

        return text.toString();
    }

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        //final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidUrl(String txtWebsite) {
        if (!txtWebsite.equals("")) {
            Pattern regex = Pattern.compile("^[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|COM|ORG|NET|MIL|EDU)$");
            Matcher matcher = regex.matcher(txtWebsite);
            return !matcher.matches();

        } else {
            return true;
        }
    }

    public static String removeExtention(String filePath) {
        // These first few lines the same as Justin's
        File f = new File(filePath);

        // if it's a directory, don't remove the extention
        if (f.isDirectory()) return filePath;

        String name = f.getName();

        // Now we know it's a file - don't need to do any special hidden
        // checking or contains() checking because of:
        final int lastPeriodPos = name.lastIndexOf('.');
        if (lastPeriodPos <= 0) {
            // No period after first character - return name as it was passed in
            return filePath;
        } else {
            // Remove the last period and everything after it
            File renamed = new File(f.getParent(), name.substring(0, lastPeriodPos));
            return renamed.getPath();
        }
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        System.out.println(phoneNumber.length());
        String regex = "^\\+?[0-9. ()-]{0,25}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) {
            System.out.println("Phone Number Valid");
            return true;
        } else {
            System.out.println("Phone Number must be in the form XXX-XXXXXXX");
            return false;
        }
    }

    public static String uniqueUUID() {
        UUID id = UUID.randomUUID();
        return String.valueOf(id);
    }

    private static void setFontTextView(ViewGroup group, Typeface font) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(font);
            } else if (v instanceof ViewGroup)
                setFontTextView((ViewGroup) v, font);
        }
    }

    public static String replaceSpace(String string) {

        return string.replaceAll("", "*");
    }

    public static String replaceSlash(String string) {

        return string.replaceAll("/", "*");

    }

    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    final boolean delete = file.delete();
                }
            }
        }
        return (path.delete());
    }

    public static Age FindAge(Calendar birthDay) {
        int years = 0;
        int months = 0;
        int days = 0;
        int hour = 0;
        int min = 0;
        int sec = 0;

        //create calendar object for current day
        Calendar currentDay = Calendar.getInstance();
        //Get difference between years
        years = currentDay.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = currentDay.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        //Get difference between months
        months = currMonth - birthMonth;

        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;

            if (currentDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;

        } else if (months == 0 && currentDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }


        //Calculate the days
        if (currentDay.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = currentDay.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (currentDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = currentDay.get(Calendar.DAY_OF_MONTH);
            currentDay.add(Calendar.MONTH, -1);
            days = currentDay.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        hour = currentDay.get(Calendar.HOUR_OF_DAY) - birthDay.get(Calendar.HOUR_OF_DAY);
        if (hour < 0) {
            days--;
            hour += 24;
        }
        min = currentDay.get(Calendar.MINUTE) - birthDay.get(Calendar.MINUTE);
        if (min < 0) {
            hour--;
            if (hour < 0) {
                days--;
                hour += 24;
            }
            min += 60;
        }
        sec = currentDay.get(Calendar.SECOND) - birthDay.get(Calendar.SECOND);
        if (sec < 0) {
            min--;
            if (min < 0) {
                hour--;
                min += 60;
                if (hour < 0) {
                    days--;
                    hour += 24;
                }
            }
            sec += 60;
        }

        System.out.println("The age is : " + years + " years, " + months + " months,  " + days + " days, " + hour + " hours, " + min + " min and " + sec + " seconds.");
        return new Age(days, months, years);
    }

    public static void rotateProgressImage(ImageView image) {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(ObjectAnimator.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(1800);
        image.startAnimation(rotate);
    }

    /**
     * Show Soft Keyboard with new Thread
     *
     * @param activity
     */
    public static void hideSoftInput(final Activity activity) {
        if (activity.getCurrentFocus() != null) {
            new Runnable() {
                public void run() {
                    InputMethodManager imm =
                            (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                    }
                }
            }.run();
        }
    }

    /**
     * Hide Soft Keyboard from Dialogs with new Thread
     *
     * @param context
     * @param view
     */
    public static void hideSoftInputFrom(final Context context, final View view) {
        new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }.run();
    }

    /**
     * Show Soft Keyboard with new Thread
     *
     * @param context
     * @param view
     */
    public static void showSoftInput(final Context context, final View view) {
        new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        }.run();
    }

    public static void hideKeyboard(View view, final Activity activity) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyboard(innerView, activity);
            }
        }
    }

    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static boolean isTimeBetweenTwoTime(String argStartTime, String argEndTime, String argCurrentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        //
        if (argStartTime.matches(reg) && argEndTime.matches(reg)
                && argCurrentTime.matches(reg)) {
            boolean valid = false;
            // Start Time
            Date startTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                    .parse(argStartTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);

            // Current Time
            Date currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                    .parse(argCurrentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);

            // End Time
            Date endTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                    .parse(argEndTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);

            //
            if (currentTime.compareTo(endTime) < 0) {

                currentCalendar.add(Calendar.DATE, 1);
                currentTime = currentCalendar.getTime();

            }

            if (startTime.compareTo(endTime) < 0) {

                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.getTime();

            }
            //
            if (currentTime.before(startTime)) {

                System.out.println(" Time is Lesser ");

                valid = false;
            } else {

                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.getTime();

                }

                System.out.println("Comparing , Start Time /n " + startTime);
                System.out.println("Comparing , End Time /n " + endTime);
                System.out
                        .println("Comparing , Current Time /n " + currentTime);

                if (currentTime.before(endTime)) {
                    System.out.println("RESULT, Time lies b/w");
                    valid = true;
                } else {
                    valid = false;
                    System.out.println("RESULT, Time does not lies b/w");
                }

            }
            return valid;

        } else {
            throw new IllegalArgumentException(
                    "Not a valid time, expecting HH:MM:SS format");
        }

    }

    private static boolean isBetweenValidTime(Date startTime, Date endTime, Date validateTime) {
        boolean validTimeFlag = false;
        if (endTime.compareTo(startTime) <= 0) {
            if (validateTime.compareTo(endTime) < 0 || validateTime.compareTo(startTime) >= 0) {
                validTimeFlag = true;
            }
        } else if (validateTime.compareTo(endTime) < 0 && validateTime.compareTo(startTime) >= 0) {
            validTimeFlag = true;
        }
        return validTimeFlag;
    }

    private static String returnCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                // Current day is Sunday
                return "Sunday";
            case Calendar.MONDAY:
                // Current day is Monday
                return "Monday";
            case Calendar.TUESDAY:
                // Current day is Tuesday
                return "Tuesday";
            case Calendar.WEDNESDAY:
                // Current day is Wedesday
                return "Wednesday";
            case Calendar.THURSDAY:
                // Current day is Thursday
                return "Thursday";
            case Calendar.FRIDAY:
                // Current day is Friday
                return "Friday";
            case Calendar.SATURDAY:
                // Current day is Saturday
                return "Saturday";
        }
        return "";
    }

    public static boolean doNotNotifyWithinHour(long doNotNotifyWithinHourCurrentTime) {
        boolean withinHour = false;
        try {
            long currentTimeString = System.currentTimeMillis();
            try {
                return doNotNotifyWithinHourCurrentTime > currentTimeString;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {

        }
        return false;
    }

    public static boolean doNotifyOn(String[] days) {
        //Logic for Do Notify on Day
        String currentDay = returnCurrentDay();
        for (String day : days) {
            if (currentDay.equals(day)) {
                return true;
            }
        }
        return false;
    }

    public static boolean notifyBetween(String startTimeIn24HourFormat, String endTimeIn24HourFormat) {
        //Logic for notifyBetween
        SimpleDateFormat msimpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        boolean isTimebwTwoTime = false;
        Date currentDate = new Date();
        String currentTimeString = msimpleDateFormat.format(currentDate.getTime());
        try {
            Date cDate = msimpleDateFormat.parse(currentTimeString);
            Date sDate = msimpleDateFormat.parse(startTimeIn24HourFormat);
            Date eDate = msimpleDateFormat.parse(endTimeIn24HourFormat);
            isTimebwTwoTime = isBetweenValidTime(sDate, eDate, cDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTimebwTwoTime;
    }

    public static String formattedTimeStamp(String timstamp, SimpleDateFormat simpleDateFormat) {
        try {
            SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date sDate = oldDateFormat.parse(timstamp);
            timstamp = simpleDateFormat.format(sDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return timstamp;
    }

    public static String formattedTime(String timstamp, SimpleDateFormat timeFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Date sDate = simpleDateFormat.parse(timstamp);
            timstamp = timeFormat.format(sDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return timstamp;
    }

    public int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static class Age {
        private int days;
        private int months;
        private int years;

        @SuppressWarnings("unused")
        private Age() {
            //Prevent default constructor
        }

        public Age(int days, int months, int years) {
            this.days = days;
            this.months = months;
            this.years = years;
        }

        public int getDays() {
            return this.days;
        }

        public int getMonths() {
            return this.months;
        }

        public int getYears() {
            return this.years;
        }

        @Override
        public String toString() {
            return years + " Years, " + months + " Months, " + days + " Days";
        }
    }

    public static void centerToolbarTitle(final Toolbar toolbar) {
        final CharSequence title = toolbar.getTitle();
        final ArrayList<View> outViews = new ArrayList<>(1);
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT);
        if (!outViews.isEmpty()) {
            final TextView titleView = (TextView) outViews.get(0);
            titleView.setGravity(Gravity.CENTER_HORIZONTAL);
            final Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.setMargins(0, 0, 60, 0);
            toolbar.requestLayout();
        }
    }

    @SuppressLint("DefaultLocale")
    public static String dateDiff(String time1, String time2) {
        String dateStart = "20/04/2018 09:29:58";
        String dateStop = "20/04/2018 05:31:48";

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String difference = null;
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

            difference = String.format("%d.%d", String.valueOf(diffHours), String.valueOf(diffMinutes));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return difference;
    }

    /**
     * Converting dp to pixel
     */
    public static int dpToPx(Activity activity, int dp) {
        Resources r = activity.getResources();
        return round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private Bitmap getBitmapFromUri(Activity activity, Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                activity.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = Objects.requireNonNull(parcelFileDescriptor).getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public static Date formattedDate(DateFormat dateFormatter, String dateString) {
        try {
            return dateFormatter.parse(dateString);
        } catch (ParseException e) {
            e.getMessage();
        }
        return null;
    }

    public static String formattedDateString(DateFormat dateFormatter, Date date) {
        try {
            return dateFormatter.format(date);
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public static String formattedDateString(DateFormat responseDateFormatter, DateFormat displayDateFormatter, String dateString) {
        try {
            return displayDateFormatter.format(responseDateFormatter.parse(dateString));
        } catch (ParseException e) {
            e.getMessage();
        }
        return "";
    }

    public static String getLastnCharacters(String inputString, int subStringLength) {
        int length = inputString.length();
        if (length <= subStringLength) {
            return inputString;
        }
        int startIndex = length - subStringLength;
        return inputString.substring(startIndex);
    }

    public static int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static String uniqueImageName() {
        String imageName = uniqueUUID() + ".jpg";
        Log.d("IMAGE_NAME", "IMAGE_NAME:" + imageName);
        return imageName;
    }

    private void setPic(String currentPhotoPath, ImageView imageView) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    public Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        // Read EXIF Data
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = Objects.requireNonNull(exif).getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        // Return result
        return Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
    }

    public File getPhotoFileUri(Activity activity, String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(Objects.requireNonNull(activity).getExternalFilesDir(Environment.DIRECTORY_PICTURES), "");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
        }

        // Return the file target for the photo based on filename

        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    public static String imageFileName(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }

    public static boolean removeDirectory(File directory) {

        // System.out.println("removeDirectory " + directory);

        if (directory == null)
            return false;
        if (!directory.exists())
            return true;
        if (!directory.isDirectory())
            return false;

        String[] list = directory.list();

        // Some JVMs return null for File.list() when the
        // directory is empty.
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                File entry = new File(directory, list[i]);

                //        System.out.println("\tremoving entry " + entry);

                if (entry.isDirectory())
                {
                    if (!removeDirectory(entry))
                        return false;
                }
                else
                {
                    if (!entry.delete())
                        return false;
                }
            }
        }

        return directory.delete();
    }

    public static Point getScreenDimensions(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);

        Point point = new Point();
        point.set(dm.widthPixels, dm.heightPixels);
        return point;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

    /**
     * dd MMM yyyy HH:mm:ss z
     *
     * @param date
     * @return The date formatted.
     */
    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME, Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * API 11
     *
     * @see Build.VERSION_CODES#HONEYCOMB
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * API 14
     *
     * @see Build.VERSION_CODES#ICE_CREAM_SANDWICH
     */
    public static boolean hasIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * API 16
     *
     * @see Build.VERSION_CODES#JELLY_BEAN
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * API 19
     *
     * @see Build.VERSION_CODES#KITKAT
     */
    public static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * API 21
     *
     * @see Build.VERSION_CODES#LOLLIPOP
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * API 23
     *
     * @see Build.VERSION_CODES#M
     */
    public static boolean hasMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return "v" + pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return context.getString(android.R.string.unknownName);
        }
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getColorAccent(Context context) {
        if (colorAccent < 0) {
            int accentAttr = hasLollipop() ? android.R.attr.colorAccent : R.attr.colorAccent;
            TypedArray androidAttr = context.getTheme().obtainStyledAttributes(new int[]{accentAttr});
            colorAccent = androidAttr.getColor(0, 0xFF009688); //Default: material_deep_teal_500
        }
        return colorAccent;
    }

    /**
     * Create the reveal effect animation
     *
     * @param view the View to reveal
     * @param cx   coordinate X
     * @param cy   coordinate Y
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void reveal(final View view, int cx, int cy) {
        if (!hasLollipop()) {
            view.setVisibility(View.VISIBLE);
            return;
        }

        //Get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        //Create the animator for this view (the start radius is zero)
        Animator animator =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

        //Make the view visible and start the animation
        view.setVisibility(View.VISIBLE);
        animator.start();
    }

    /**
     * Create the un-reveal effect animation
     *
     * @param view the View to hide
     * @param cx   coordinate X
     * @param cy   coordinate Y
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void unReveal(final View view, int cx, int cy) {
        if (!hasLollipop()) {
            view.setVisibility(View.GONE);
            return;
        }

        //Get the initial radius for the clipping circle
        int initialRadius = view.getWidth();

        //Create the animation (the final radius is zero)
        Animator animator =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

        //Make the view invisible when the animation is done
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });

        //Start the animation
        animator.start();
    }

    public static void hideKeyboardOnClickOtherView(final Activity context, final View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftInputFrom(context, view);
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                final View innerView = ((ViewGroup) view).getChildAt(i);

                hideKeyboardOnClickOtherView(context, innerView);
            }
        }
    }


}
