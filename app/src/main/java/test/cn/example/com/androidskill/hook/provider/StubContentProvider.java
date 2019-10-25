package test.cn.example.com.androidskill.hook.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.acl.LastOwnerException;

import test.cn.example.com.util.LogUtil;

public class StubContentProvider extends ContentProvider {
    private static final String PLUGINAUTHHOST= "com.android.skill";
    @Override
    public boolean onCreate() {
        LogUtil.i("代理ContentProvider onCreate");
        return false;
    }

    @Nullable
    @android.support.annotation.Nullable
    @Override
    public Cursor query(@NonNull @android.support.annotation.NonNull Uri uri, @Nullable @android.support.annotation.Nullable String[] projection, @Nullable @android.support.annotation.Nullable String selection, @Nullable @android.support.annotation.Nullable String[] selectionArgs, @Nullable @android.support.annotation.Nullable String sortOrder) {
        String uriAuthority = uri.getAuthority();
        LogUtil.i("代理ContentProvider query      "+ uriAuthority);
        LogUtil.i("代理ContentProvider query      "+ uri.toString());
        String plugUristring = uri.toString().replaceAll(uriAuthority+"/","");
        Uri plugUri = Uri.parse(plugUristring);
        LogUtil.i("contentProvider的auth    "+plugUri.toString());
        //转发给插件的contentProvider
        getContext().getContentResolver().query(plugUri,null,null,null,null);
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
