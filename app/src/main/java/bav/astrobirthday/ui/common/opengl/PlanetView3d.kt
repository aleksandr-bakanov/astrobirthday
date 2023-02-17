package bav.astrobirthday.ui.common.opengl

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import bav.astrobirthday.R
import bav.astrobirthday.domain.model.PlanetAndInfo

class PlanetView3d(
    activityContext: Context,
    planetAndInfo: PlanetAndInfo,
    zOrderOnTop: Boolean = true,
    isAnimated: Boolean = true
) : GLSurfaceView(activityContext) {

    private val renderer: PlanetView3dRenderer

    init {
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        holder.setFormat(PixelFormat.RGBA_8888)
        setZOrderOnTop(zOrderOnTop)

        val backgroundColor = ContextCompat.getColor(activityContext, R.color.backgroundBlack1)
        val colorArray = if (zOrderOnTop)
            floatArrayOf(0f, 0f, 0f)
        else floatArrayOf(
            backgroundColor.red.toColorFloat(),
            backgroundColor.green.toColorFloat(),
            backgroundColor.blue.toColorFloat()
        )

        renderer = PlanetView3dRenderer(activityContext, planetAndInfo, colorArray, isAnimated)

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)

        if (isAnimated.not()) {
            renderMode = RENDERMODE_WHEN_DIRTY
        }
    }

    fun setZoom(zoom: Float) {
        renderer.zoom = zoom
        requestRender()
    }

    private fun Int.toColorFloat(): Float = this / 256f

}