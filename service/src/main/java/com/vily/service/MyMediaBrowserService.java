package com.vily.service;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.service.media.MediaBrowserService;

import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-16
 *  
 **/
public class MyMediaBrowserService extends MediaBrowserService {

    @Override
    public BrowserRoot onGetRoot(  String clientPackageName, int clientUid,   Bundle rootHints) {
        return null;
    }

    @Override
    public void onLoadChildren(  String parentId,  MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {

    }
}
