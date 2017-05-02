package kr.co.triggers.yolo.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;

import kr.co.triggers.yolo.inject.module.AppModule;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Photoshop {
    public static final String MIMETYPE_IMAGE = "image/jpeg";

    public static MultipartBody.Part getMultipart(String name, String url) {

        MultipartBody.Part partProfile = null;

        if (!TextUtils.isEmpty(url)) {

            File file = new File(url);
            partProfile = MultipartBody.Part.createFormData(name, file.getPath(), RequestBody.create(MediaType.parse(MIMETYPE_IMAGE), file));
        }

        return partProfile;
    }

    private static final String PROFILE_FORMAT = AppModule.URL.concat("/users/%d/profile");

    public static String getUrl(long id) {

        return String.format("%s/photos/%d", AppModule.URL, id);
    }

    public static String getPath(final Context context, final Uri uri) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            if (DocumentsContract.isDocumentUri(context, uri)) {

                if (isExternalStorageDocument(uri)) {

                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {

                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                }
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                else if (isMediaDocument(uri)) {

                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;

                    if ("image".equals(type)) {

                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    }
                    else if ("video".equals(type)) {

                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    }
                    else if ("audio".equals(type)) {

                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[] { split[1] };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
        }
        else {

            if ("content".equalsIgnoreCase(uri.getScheme())) {

                return getDataColumn(context, uri, null, null);
            }
            else if ("file".equalsIgnoreCase(uri.getScheme())) {

                return uri.getPath();
            }
        }

        return null;
    }
    public static String getDataColumn(Context context, Uri uri, String selection,  String[] selectionArgs) {

        Cursor cursor = null;

        final String column = "_data";
        final String[] projection = { column };

        try {

            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);

            if (cursor != null && cursor.moveToFirst()) {

                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
        finally {

            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) { return "com.android.externalstorage.documents".equals(uri.getAuthority()); }
    public static boolean isDownloadsDocument(Uri uri) { return "com.android.providers.downloads.documents".equals(uri.getAuthority()); }
    public static boolean isMediaDocument(Uri uri) { return "com.android.providers.media.documents".equals(uri.getAuthority()); }

    public static String getProfileById(long id) { return String.format(PROFILE_FORMAT, id); }
}
