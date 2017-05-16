package wpollek.cs190i.cs.ucsb.edu.wpollekdrawingmultitouch;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import wpollek.cs190i.cs.ucsb.edu.wpollekdrawingmultitouch.MySurfaceView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static float seekval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        final MySurfaceView sv = (MySurfaceView) findViewById(R.id.mySurfaceView);

        final SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekval=progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Button clear = (Button) findViewById(R.id.button);
        clear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //set the canvas to clear
                //reset pen size
                // call clear function (public mysurfaceview)
                sv.clearCanvas();

                return true;
            }
        });
        super.onCreate(savedInstanceState);
    }
}
