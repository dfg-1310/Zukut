package com.android.zukut.util;

import java.io.File;

/**
 * Created by tsingh on 20/1/15.
 */
public class AppFile {

    public AppFile(File file, String mimeType) {
        this.file = file;
        this.mimeType = mimeType;
    }

    File file;
    String mimeType;

    public File getFile() {
        return file;
    }

    public String getMimeType() {
        return mimeType;
    }

}
