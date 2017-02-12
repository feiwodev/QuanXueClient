package com.zeno.quanxueclient;

import android.os.Environment;

import java.io.File;

/**
 * Created by Zeno on 2017/1/9.
 *
 * 一些全局配置
 */

public class AppConfig {

    public static final String SAVE_NOTE_FILE_PATH = Environment.getExternalStorageDirectory()
            + File.separator
            +"quan_xue"
            +File.separator
            +"note_file"
            +File.separator;
}
