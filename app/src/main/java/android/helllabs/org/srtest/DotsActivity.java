package android.helllabs.org.srtest;

import android.app.Activity;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;


public class DotsActivity extends Activity {

    private GLSurfaceView surface;
    private boolean rendererSet;
    private Handler handler;


    private class RendererWrapper implements GLSurfaceView.Renderer {

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, final int width, final int height) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    setAspectRatio(width, height);
                }
            });
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            glClear(GL_COLOR_BUFFER_BIT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl);

        handler = new Handler();

        surface = (GLSurfaceView)findViewById(R.id.surface);
        surface.setEGLContextClientVersion(2);
        surface.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        surface.setRenderer(new RendererWrapper());
        rendererSet = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (rendererSet) {
            surface.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (rendererSet) {
            surface.onResume();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dots, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAspectRatio(int screenWidth, int screenHeight) {

        android.view.ViewGroup.LayoutParams videoParams = surface.getLayoutParams();

        if (screenWidth < screenHeight) {
            videoParams.width = screenWidth;
            videoParams.height = screenWidth * 3 / 4;
        } else {
            videoParams.width = screenHeight * 4 / 3;
            videoParams.height = screenHeight;
        }

        surface.setLayoutParams(videoParams);
    }
}

