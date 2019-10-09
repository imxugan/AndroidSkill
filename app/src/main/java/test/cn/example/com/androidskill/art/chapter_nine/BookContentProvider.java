package test.cn.example.com.androidskill.art.chapter_nine;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import test.cn.example.com.util.LogUtil;

public class BookContentProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        LogUtil.i("onCreate");
        return false;
    }

    @Nullable
    @android.support.annotation.Nullable
    @Override
    public Cursor query(@NonNull @android.support.annotation.NonNull Uri uri, @Nullable @android.support.annotation.Nullable String[] projection, @Nullable @android.support.annotation.Nullable String selection, @Nullable @android.support.annotation.Nullable String[] selectionArgs, @Nullable @android.support.annotation.Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @android.support.annotation.Nullable
    @Override
    public String getType(@NonNull @android.support.annotation.NonNull Uri uri) {
        return null;
    }

    @Nullable
    @android.support.annotation.Nullable
    @Override
    public Uri insert(@NonNull @android.support.annotation.NonNull Uri uri, @Nullable @android.support.annotation.Nullable ContentValues values) {
        LogUtil.i(""+values);
        return null;
    }

    @Override
    public int delete(@NonNull @android.support.annotation.NonNull Uri uri, @Nullable @android.support.annotation.Nullable String selection, @Nullable @android.support.annotation.Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull @android.support.annotation.NonNull Uri uri, @Nullable @android.support.annotation.Nullable ContentValues values, @Nullable @android.support.annotation.Nullable String selection, @Nullable @android.support.annotation.Nullable String[] selectionArgs) {
        return 0;
    }
}
