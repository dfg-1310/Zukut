package com.android.zukut.httpClient;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.zukut.util.PreferenceKeeper;


public class AppRestClient {

    private final Context context;
    private final Handler h;
    private static ImageLoader imageLoader;
    private RequestQueue queue;
    private static AppRestClient client = null;

    private AppRestClient(RequestQueue queue, Context context) {
        this.context = context;
        this.h = new Handler();
        this.queue = queue;
        imageLoader = new ImageLoader(queue, new LruBitmapCache(context));
    }

    public static ImageLoader getImageLoader(){
        return imageLoader;
    }

    public static synchronized AppRestClient getClient() {
        return client;
    }

    public void sendRequest(AppHttpRequest request, String tag) {
        // if user is not logged in and login required for request
//        if (request.isLoginRequired()
//                && !PreferenceKeeper.getInstance().isProfileActivate()) {
//            request.getListener()._onError(
//                    AppUtil.getUserNotLoggedInError(context));
//            return;
//        }
        request.setTag(tag);
        queue.add(request);
    }

    /**
     * Note : This cancels any pending request with the given tag
     * 
     * @param request
     *            - Request to send
     * @param tag
     *            - Tag that will be associated with this request
     * @param delay
     *            - Delay after which this request shall be sent
     */
    public synchronized void sendRequest(final AppHttpRequest request,
            final String tag, int delay) {
        // if user is not logged in and login required for request
//        if (request.isLoginRequired()
//                && !PreferenceKeeper.getInstance().isProfileActivate()) {
//            request.getListener()._onError(
//                    AppUtil.getUserNotLoggedInError(context));
//            return;
//        }
        removeDelayedCalls(tag);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                request.setTag(tag);
                queue.add(request);
            }
        };
        h.postAtTime(r, tag, SystemClock.uptimeMillis() + delay);
    }

    private void removeDelayedCalls(String tag) {
        h.removeCallbacksAndMessages(tag);
    }

    /**
     * cancels all the request that came from this tag. Activity shall call
     * cancelAll on its onStop. Note: This also cancels any request that is send
     * with delay
     * 
     * @param tag
     */
    public void cancelAll(String tag) {
        removeDelayedCalls(tag);
        queue.cancelAll(tag);
    }

    public static void init(Context context) {
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB
                                                                              // cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new AppNetwork(context));
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        client = new AppRestClient(queue, context);
    }
}
