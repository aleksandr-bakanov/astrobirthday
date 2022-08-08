package bav.astrobirthday.ui.common.opengl

import android.content.Context
import android.opengl.GLSurfaceView

class PlanetView3d(activityContext: Context): GLSurfaceView(activityContext) {

    private val renderer: PlanetView3dRenderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        setEGLConfigChooser(8, 8, 8, 8, 16, 0)

        renderer = PlanetView3dRenderer(activityContext)

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }

}