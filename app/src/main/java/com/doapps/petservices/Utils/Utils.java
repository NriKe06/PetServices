package com.doapps.petservices.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.doapps.petservices.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by NriKe on 12/06/2017.
 */

public class Utils {
    public static void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static void showToastInternalServerError(Context context){
        Toast.makeText(context,context.getString(R.string.internal_server_error),Toast.LENGTH_SHORT).show();
    }

    public static void askForPermission(final Activity activity, final String[] permissions, final String message) {
        final List<String> permissionsToAsk;
        final List<String> permissionRationale = new ArrayList<>();
        permissionsToAsk = permissionsToAsk(activity, permissions, permissionRationale);
        if (permissionsToAsk.size() > 0) {
            if (permissionRationale.size() > 0) {
                showMessageOKCancel(activity, message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    ActivityCompat.requestPermissions(activity,
                                            permissionsToAsk.toArray(new String[permissionsToAsk.size()]),
                                            Constants.REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            }
                        }, null);
            } else {
                ActivityCompat.requestPermissions(activity,
                        permissionsToAsk.toArray(new String[permissionsToAsk.size()]),
                        Constants.REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }

    private static List<String> permissionsToAsk(Activity activity, String[] permissionsNeeded, List<String> permissionRationale) {
        List<String> permissionsToAsk = new ArrayList<>();
        for (String permission : permissionsNeeded) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsToAsk.add(permission);
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    permissionRationale.add(permission);
                }
            }
        }
        return permissionsToAsk;
    }

    private static void showMessageOKCancel(Activity activity, String message,
                                            DialogInterface.OnClickListener okListener,
                                            DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(activity.getString(R.string.ok_label), okListener)
                .setNegativeButton(activity.getString(R.string.cancel_label), cancelListener)
                .create()
                .show();
    }

    public static String getRealPathFromURI(ContentResolver contentResolver, Uri uri) {
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int indexColumn = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(indexColumn);
        }
        return null;
    }

    //save imagen into file
    public static File persistImage(Bitmap bitmap, Context context) {
        File filesDir = context.getFilesDir();
        final String name = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
            return imageFile;
        } catch (Exception e) {
            Log.e(context.getClass().getSimpleName(), "Error writing bitmap", e);
            return null;
        }
    }

}
