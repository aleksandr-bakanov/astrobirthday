package bav.astrobirthday.ui.common.opengl

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
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

        val backgroundColor = Color.valueOf(activityContext.getColor(R.color.backgroundBlack1))
        val colorArray = if (zOrderOnTop)
            floatArrayOf(0f, 0f, 0f)
        else floatArrayOf(
            backgroundColor.red(), backgroundColor.green(), backgroundColor.blue()
        )

        renderer = PlanetView3dRenderer(activityContext, planetAndInfo, colorArray, isAnimated)

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }

    fun setZoom(zoom: Float) {
        renderer.zoom = zoom
        requestRender()
    }

}