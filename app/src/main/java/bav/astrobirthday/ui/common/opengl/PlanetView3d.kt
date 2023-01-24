package bav.astrobirthday.ui.common.opengl

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
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

    fun setZoom(newY: Float) {
        val dy: Float = newY - previousY
        renderer.zoom = min(max(renderer.zoom + dy * TOUCH_SCALE_FACTOR, 0f), zoomThreshold)
        requestRender()
        previousY = newY
    }

    companion object {
        const val TOUCH_SCALE_FACTOR: Float = 180.0f / 320f
        const val zoomThreshold = 300f
    }

    private var previousY: Float = 0f

}