package com.gy.xmllayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class MoonView extends View {

    private float bitmapX;

    private float bitmapY;

    public MoonView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.moon);
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    public float getBitmapX() {
        return bitmapX;
    }

    public void setBitmapX(float bitmapX) {
        this.bitmapX = bitmapX;
    }

    public float getBitmapY() {
        return bitmapY;
    }

    public void setBitmapY(float bitmapY) {
        this.bitmapY = bitmapY;
    }
}
