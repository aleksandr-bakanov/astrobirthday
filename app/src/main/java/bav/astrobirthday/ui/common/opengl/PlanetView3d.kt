package bav.astrobirthday.ui.common.opengl

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import bav.astrobirthday.R
import bav.astrobirthday.domain.model.PlanetAndInfo

class PlanetView3d(activityContext: Context, p: PlanetAndInfo) : GLSurfaceView(activityContext) {

    private val renderer: PlanetView3dRenderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        holder.setFormat(PixelFormat.RGBA_8888)
        setZOrderOnTop(true)

        setBackgroundResource(R.drawable.stars2k)

        renderer = PlanetView3dRenderer(activityContext, p)

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }

}