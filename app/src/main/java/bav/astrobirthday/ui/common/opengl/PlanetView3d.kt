package bav.astrobirthday.ui.common.opengl

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import bav.astrobirthday.domain.model.PlanetAndInfo
import kotlin.math.max
import kotlin.math.min

class PlanetView3d(activityContext: Context, p: PlanetAndInfo) : GLSurfaceView(activityContext) {

    private val renderer: PlanetView3dRenderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        holder.setFormat(PixelFormat.RGBA_8888)
        setZOrderOnTop(true)

        //setBackgroundResource(R.drawable.stars2k)

        renderer = PlanetView3dRenderer(activityContext, p)

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }

    companion object {
        const val TOUCH_SCALE_FACTOR: Float = 180.0f / 320f
        const val zoomThreshold = 300f
    }

    private var previousY: Float = 0f

    override fun onTouchEvent(e: MotionEvent): Boolean {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        val y: Float = e.y

        when (e.action) {
            MotionEvent.ACTION_MOVE -> {
                val dy: Float = y - previousY
                renderer.zoom = min(max(renderer.zoom + dy * TOUCH_SCALE_FACTOR, 0f), zoomThreshold)
                requestRender()
            }
        }

        previousY = y
        return true
    }


}