package com.cmit.clouddetection.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.cmit.clouddetection.activity.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;


/**
 * Created by zhou on 2017/11/10.
 */

public class ShotScreenUtils {
    private MediaProjectionManager mMediaProjectionManager;
    private int mScreenDensity;
    private ImageReader mImageReader;
    private Intent mResultData;
    private int mResultCode;
    private MediaProjection mMediaProjection = null;
    private DisplayMetrics metrics;
    private WindowManager mWindowManager;
    private VirtualDisplay mVirtualDisplay;
    private int windowWidth;
    private int windowHeight;
    private Context context;
    private MyApplication mInstan;


    public ShotScreenUtils(Context context) {
        this.context = context;
        mInstan = MyApplication.getInstan();
        init();
    }

    public void init() {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mMediaProjectionManager = (MediaProjectionManager) context.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        windowWidth = mWindowManager.getDefaultDisplay().getWidth();
        windowHeight = mWindowManager.getDefaultDisplay().getHeight();
        metrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;
        mImageReader = ImageReader.newInstance(windowWidth, windowHeight, 0x1, 2); //ImageFormat.RGB_565
        startVirtual();
    }


    public void startVirtual() {
        if (mMediaProjection != null) {
            virtualDisplay();
        } else {
            setUpMediaProjection();
        }
    }

    public void setUpMediaProjection() {
        mResultData = mInstan.getIntent();
        mResultCode = mInstan.getResult();
        if (mResultCode == 0 || mResultData == null) {
//            Toast.makeText(context, "没有获取到权限", Toast.LENGTH_SHORT).show();
        } else {
            mMediaProjectionManager = (MediaProjectionManager) context.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            if (mMediaProjection == null) {
                mMediaProjection = mMediaProjectionManager.getMediaProjection(mResultCode, mResultData);
            }
            virtualDisplay();
        }
    }


    private void virtualDisplay() {
        mVirtualDisplay = mMediaProjection.createVirtualDisplay("screen-mirror",
                windowWidth, windowHeight, mScreenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mImageReader.getSurface(), null, null);
    }


    public Bitmap startCapture(int sleep) throws InterruptedException {
        Thread.sleep(sleep);
        Image image = mImageReader.acquireLatestImage();
        int width = image.getWidth();
        int height = image.getHeight();
        final Image.Plane[] planes = image.getPlanes();
        final ByteBuffer buffer = planes[0].getBuffer();
        int pixelStride = planes[0].getPixelStride();
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * width;
        Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        image.close();
        if (bitmap != null) {
            tearDownMediaProjection();
            stopVirtual();
            return bitmap;
        }
        startCapture(sleep);
        return null;
    }

    private void tearDownMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
    }

    public int createPNG(Bitmap bitmap, String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        } else {
            file.mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    private void stopVirtual() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        mVirtualDisplay = null;
    }
}
