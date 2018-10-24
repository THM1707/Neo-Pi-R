package thm.com.gr2.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import thm.com.gr2.util.Constants;

/**
 * Created by thm on 15/03/2018.
 */
public class AppServiceClient extends ServiceClient {
    private static MyApi mMyApiInstance;
//    private InternetConnectionListener mInternetConnectionListener;
//
//    public void setInternetConnectionListener(
//            InternetConnectionListener internetConnectionListener) {
//        mInternetConnectionListener = internetConnectionListener;
//    }
//
//    public void removeInternetConnectionListener() {
//        mInternetConnectionListener = null;
//    }
//
//    private boolean isInternetAvailable() {
//        ConnectivityManager connectivityManager =
//                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//
//    }

    public static MyApi getMyApiInstance(Context context) {
        if (mMyApiInstance == null) {
            mMyApiInstance = createService(context, Constants.END_POINT_URL, MyApi.class);
        }
        return mMyApiInstance;
    }
}
