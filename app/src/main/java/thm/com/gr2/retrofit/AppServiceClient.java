package thm.com.gr2.retrofit;

import android.content.Context;
import thm.com.gr2.util.Constants;

/**
 * Created by thm on 15/03/2018.
 */
public class AppServiceClient extends ServiceClient {
    private static MyApi mMyApiInstance;

    public static MyApi getMyApiInstance(Context context) {
        if (mMyApiInstance == null) {
            mMyApiInstance = createService(context, Constants.END_POINT_URL, MyApi.class);
        }
        return mMyApiInstance;
    }
}
