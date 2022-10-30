package bav.astrobirthday.ui.common.opengl

import android.content.Context

data class PlanetData(
    // in degree
    val axisRotationSpeed: Float = 1f,

    // relative to parent mass center
    val orbitRadius: Float = 0f,
    // in radians
    val orbitAngle: Float = 0f,
    // in radians
    val angularVelocity: Float = 0f,

    val sphereRadius: Float = 0f,
    val ringInnerRadius: Float = 0f,
    val ringOuterRadius: Float = 0f,

    val sphereTextureResId: Int = 0,
    val ringTextureResId: Int = 0
)

fun PlanetData.toPlanetRenderData(context: Context): PlanetRenderData {
    return PlanetRenderData(
        axisRotationSpeed = axisRotationSpeed,
        orbitRadius = orbitRadius,
        orbitAngle = orbitAngle,
        angularVelocity = angularVelocity,
        sphere = Sphere(sphereRadius),
        ring = if (ringInnerRadius > 0f) Ring(ringInnerRadius, ringOuterRadius) else null,
        sphereTexture = TextureUtils.loadTexture(context, sphereTextureResId),
        ringTexture = if (ringTextureResId != 0) TextureUtils.loadTexture(
            context,
            ringTextureResId
        ) else 0
    )
}
