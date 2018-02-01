package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice12CameraRotateFixedView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);

    public Practice12CameraRotateFixedView(Context context) {
        super(context);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float bitMapWidth = bitmap.getWidth();
        float bitMapHeigth = bitmap.getHeight();
        float center1X = point1.x + bitMapWidth / 2;
        float center1Y = point1.y + bitMapHeigth / 2;
        float center2X = point2.x + bitMapWidth / 2;
        float center2Y = point2.y + bitMapHeigth / 2;

        Camera camera = new Camera();

        canvas.save();
        camera.save();

        //准备 canvas, 调整位置
        camera.rotateX(30); // 旋转 Camera 的三维空间
        canvas.translate(center1X, center1Y); ; // 旋转之后把投影移动回来
        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
        canvas.translate(-center1X, -center1Y); // 旋转之前把绘制内容移动到轴心（原点）
        /**Note: 目的不是把坐标系轴心移动到绘制内容的中心, 而是把绘制内容移动到坐标系轴心(其实就是移动 canvas 本身)
         * 不可以是 canvas.translate(center1X, center1Y), camera 的轴心是 View 坐标系的原点.*/

        camera.restore(); // 恢复 Camera 的状态
        //在调整之后的 canvas 上面 drawXXX

        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();


        Matrix matrix = new Matrix();

        camera.save();
        camera.rotateY(30);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-center2X, -center2Y);
        matrix.postTranslate(center2X, center2Y);

        canvas.save();
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
