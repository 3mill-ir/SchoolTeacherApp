package com.hezare.mmd;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Response;


public class App extends Application {
    static final String COOKIES_HEADER = "Set-Cookie";
    static java.net.CookieManager msCookieManager;
    private static Context c;

    public static Context getContext() {
        return c;
    }

    public static void setCookie(Response header) {
        List<String> cookiesHeader = header.headers(COOKIES_HEADER);
        Set<String> tasksSet = new HashSet<String>(cookiesHeader);
        PreferenceManager.getDefaultSharedPreferences(App.getContext())
                .edit()
                .putStringSet("Set-Cookie", tasksSet)
                .commit();


    }

    public static void getCookie(ANRequest.PostRequestBuilder header) {

        Set<String> savedcookie = PreferenceManager.getDefaultSharedPreferences(App.getContext())
                .getStringSet("Set-Cookie", new HashSet<String>());
        List<String> restorecookies = new ArrayList<String>(savedcookie);
        for (String cookie : restorecookies) {
            msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));

        }

        if (msCookieManager.getCookieStore().getCookies().size() > 0) {
            // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
            header.addHeaders("Cookie", TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
            //   Log.e("Cookie", TextUtils.join(";",  msCookieManager.getCookieStore().getCookies()));

        }
     /*   if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }}
*/
    }

    public static void getCookieFile(ANRequest.MultiPartBuilder header) {

        Set<String> savedcookie = PreferenceManager.getDefaultSharedPreferences(App.getContext())
                .getStringSet("Set-Cookie", new HashSet<String>());
        List<String> restorecookies = new ArrayList<String>(savedcookie);
        for (String cookie : restorecookies) {
            msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));

        }

        if (msCookieManager.getCookieStore().getCookies().size() > 0) {
            // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
            header.addHeaders("Cookie", TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
            //   Log.e("Cookie", TextUtils.join(";",  msCookieManager.getCookieStore().getCookies()));

        }
     /*   if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }}
*/
    }

    @Override
    public void onCreate() {
        super.onCreate();
        c = getApplicationContext();
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        AndroidNetworking.initialize(getApplicationContext());
        msCookieManager = new java.net.CookieManager();

    }
}
