package wpollek.cs190i.cs.ucsb.edu.wpollekdrawingmultitouch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.max;
import static wpollek.cs190i.cs.ucsb.edu.wpollekdrawingmultitouch.MainActivity.seekval;

/**
 * Created by wpollek on 4/19/17.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    int smallest = 15; //smallest line size
    Map<Integer, PointF> points = new HashMap<Integer, PointF>();
    float[] px = new float[5];
    float[] py = new float[5];
    float[] lastx = new float[5];
    float[] lasty = new float[5];
    Paint paint = new Paint();
    int[] colors = this.getResources().getIntArray(R.array.colors);
    SurfaceHolder holder;
    public Bitmap painting;
    public Canvas paintingCanvas;
    int max;

    public MySurfaceView(Context context){
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }

    public MySurfaceView(Context context, AttributeSet attrs){
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
    }
    public MySurfaceView(Context context, AttributeSet attrs, int style){
        super(context, attrs, style);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();
        int index = event.getActionIndex();
         int id = event.getPointerId(index);
        PointF point = new PointF();

        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                point.set(event.getX(index),event.getY(index));
                points.put(id,point);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                points.remove(id);
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i<event.getPointerCount();i++){
                    PointF p = points.get(event.getPointerId(i));
                    float x=event.getX(i);
                    float y=event.getY(i);
                    paint.setColor(colors[event.getPointerId(i)%5]);
                    paint.setStrokeWidth(smallest + seekval);
                    Canvas canvas = new Canvas(this.painting);
                    canvas.drawLine(p.x,p.y,x,y,paint);
                    //smooth the line now
                    canvas.drawCircle(x,y,(smallest+seekval)/2,paint);
                    canvas.drawCircle(p.x,p.y,(smallest+seekval)/2,paint);

                    canvas = getHolder().lockCanvas();
                    canvas.drawColor(Color.WHITE);
                    canvas.drawBitmap(painting,0,0,paint);
                    getHolder().unlockCanvasAndPost(canvas);
                    //update map function with new location
                    p.x =x;
                    p.y =y;
                    //points.put(i,p);
                }
        }
        return true;
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder=holder;

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        max = max(width,height)*2;
        if(painting==null) {
            painting = Bitmap.createBitmap(max, max, Bitmap.Config.ARGB_8888);
        }
        else{
            painting = Bitmap.createBitmap(painting);
        }
        paintingCanvas = new Canvas(painting);
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(painting,0,0,paint);
        holder.unlockCanvasAndPost(canvas);
    }

    public void clearCanvas(){
        Canvas canvas = getHolder().lockCanvas();
        painting = Bitmap.createBitmap(max,max, Bitmap.Config.ARGB_8888);
        canvas.drawColor(Color.WHITE);
        holder.unlockCanvasAndPost(canvas);
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.holder = null;
    }
}
