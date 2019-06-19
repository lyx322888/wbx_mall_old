package com.wbx.mall.bean;

import com.wbx.mall.widget.videoplayer.IVideoInfo;

/**
 * Created by wushenghui on 2017/10/25.
 */

public class VideoInfo implements IVideoInfo {
    public String title;
    public String videoPath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @Override
    public String getVideoTitle() {
        return title;
    }

    @Override
    public String getVideoPath() {
        return videoPath;
    }
}
