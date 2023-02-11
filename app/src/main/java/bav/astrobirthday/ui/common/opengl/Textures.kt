package bav.astrobirthday.ui.common.opengl

import bav.astrobirthday.R
import kotlin.random.Random

sealed class TextureType(
    val minPlanetRadius: Float,
    val maxPlanetRadius: Float,
) {
    object GasGiant : TextureType(3f, 4f)
    object Ice : TextureType(0.5f, 1.5f)
    object Martian : TextureType(0.5f, 2f)
    object Primordial : TextureType(0.5f, 2f)
    object Rock : TextureType(0.25f, 1f)
    object Terrestrial : TextureType(0.5f, 2f)
    object Volcanic : TextureType(0.25f, 1.5f)
    object Venusian : TextureType(0.5f, 2f)
    object Unknown : TextureType(0f, 0f) // == random

    fun getRandomRadius(random: Random): Float {
        return minPlanetRadius + (maxPlanetRadius - minPlanetRadius) * random.nextFloat()
    }

    companion object {
        val allTypes = listOf(
            GasGiant,
            Ice,
            Martian,
            Primordial,
            Rock,
            Terrestrial,
            Volcanic,
            Venusian
        )

        val middleSizeTypes = listOf(
            Martian,
            Primordial,
            Terrestrial,
            Volcanic,
            Venusian
        )

        val smallSizeTypes = listOf(
            Ice,
            Rock
        )
    }
}

val gasGiantTextures = listOf(
    R.drawable.tex_gas_giant_1,
    R.drawable.tex_gas_giant_2,
    R.drawable.tex_gas_giant_3,
    R.drawable.tex_gas_giant_4,
    R.drawable.tex_gas_giant_5,
    R.drawable.tex_gas_giant_6,
    R.drawable.tex_gas_giant_7,
    R.drawable.tex_gas_giant_8,
    R.drawable.tex_gas_giant_9,
    R.drawable.tex_gas_giant_10,
    R.drawable.tex_gas_giant_11,
    R.drawable.tex_gas_giant_12,
    R.drawable.tex_gas_giant_13,
    R.drawable.tex_gas_giant_14,
    R.drawable.tex_gas_giant_15,
    R.drawable.tex_gas_giant_16,
)

val iceTextures = listOf(
    R.drawable.tex_ice_1,
    R.drawable.tex_ice_2,
    R.drawable.tex_ice_3,
    R.drawable.tex_ice_4,
    R.drawable.tex_ice_5,
    R.drawable.tex_ice_6,
    R.drawable.tex_ice_7,
    R.drawable.tex_ice_8,
    R.drawable.tex_ice_9,
    R.drawable.tex_ice_10,
    R.drawable.tex_ice_11,
    R.drawable.tex_ice_12,
    R.drawable.tex_ice_13,
    R.drawable.tex_ice_14,
    R.drawable.tex_ice_15,
    R.drawable.tex_ice_16,
)

val martianTextures = listOf(
    R.drawable.tex_martian_1,
    R.drawable.tex_martian_2,
    R.drawable.tex_martian_3,
    R.drawable.tex_martian_4,
    R.drawable.tex_martian_5,
    R.drawable.tex_martian_6,
    R.drawable.tex_martian_7,
    R.drawable.tex_martian_8,
    R.drawable.tex_martian_9,
    R.drawable.tex_martian_10,
    R.drawable.tex_martian_11,
    R.drawable.tex_martian_12,
    R.drawable.tex_martian_13,
    R.drawable.tex_martian_14,
    R.drawable.tex_martian_15,
    R.drawable.tex_martian_16,
)

val primordialTextures = listOf(
    R.drawable.tex_primordial_1,
    R.drawable.tex_primordial_2,
    R.drawable.tex_primordial_3,
    R.drawable.tex_primordial_4,
    R.drawable.tex_primordial_5,
    R.drawable.tex_primordial_6,
    R.drawable.tex_primordial_7,
    R.drawable.tex_primordial_8,
    R.drawable.tex_primordial_9,
    R.drawable.tex_primordial_10,
    R.drawable.tex_primordial_11,
    R.drawable.tex_primordial_12,
    R.drawable.tex_primordial_13,
    R.drawable.tex_primordial_14,
    R.drawable.tex_primordial_15,
    R.drawable.tex_primordial_16,
)

val rockTextures = listOf(
    R.drawable.tex_rock_1,
    R.drawable.tex_rock_2,
    R.drawable.tex_rock_3,
    R.drawable.tex_rock_4,
    R.drawable.tex_rock_5,
    R.drawable.tex_rock_6,
    R.drawable.tex_rock_7,
    R.drawable.tex_rock_8,
    R.drawable.tex_rock_9,
    R.drawable.tex_rock_10,
    R.drawable.tex_rock_11,
    R.drawable.tex_rock_12,
    R.drawable.tex_rock_13,
    R.drawable.tex_rock_14,
    R.drawable.tex_rock_15,
    R.drawable.tex_rock_16,
)

val terrestrialTextures = listOf(
    R.drawable.tex_terrestrial_1,
    R.drawable.tex_terrestrial_2,
    R.drawable.tex_terrestrial_3,
    R.drawable.tex_terrestrial_4,
    R.drawable.tex_terrestrial_5,
    R.drawable.tex_terrestrial_6,
    R.drawable.tex_terrestrial_7,
    R.drawable.tex_terrestrial_8,
    R.drawable.tex_terrestrial_9,
    R.drawable.tex_terrestrial_10,
    R.drawable.tex_terrestrial_11,
    R.drawable.tex_terrestrial_12,
    R.drawable.tex_terrestrial_13,
    R.drawable.tex_terrestrial_14,
    R.drawable.tex_terrestrial_15,
    R.drawable.tex_terrestrial_16,
)

val volcanicTextures = listOf(
    R.drawable.tex_volcanic_1,
    R.drawable.tex_volcanic_2,
    R.drawable.tex_volcanic_3,
    R.drawable.tex_volcanic_4,
    R.drawable.tex_volcanic_5,
    R.drawable.tex_volcanic_6,
    R.drawable.tex_volcanic_7,
    R.drawable.tex_volcanic_8,
    R.drawable.tex_volcanic_9,
    R.drawable.tex_volcanic_10,
    R.drawable.tex_volcanic_11,
    R.drawable.tex_volcanic_12,
    R.drawable.tex_volcanic_13,
    R.drawable.tex_volcanic_14,
    R.drawable.tex_volcanic_15,
    R.drawable.tex_volcanic_16,
)

val venusianTextures = listOf(
    R.drawable.tex_venusian_1,
    R.drawable.tex_venusian_2,
    R.drawable.tex_venusian_3,
    R.drawable.tex_venusian_4,
    R.drawable.tex_venusian_5,
    R.drawable.tex_venusian_6,
    R.drawable.tex_venusian_7,
    R.drawable.tex_venusian_8,
    R.drawable.tex_venusian_9,
    R.drawable.tex_venusian_10,
    R.drawable.tex_venusian_11,
    R.drawable.tex_venusian_12,
    R.drawable.tex_venusian_13,
    R.drawable.tex_venusian_14,
    R.drawable.tex_venusian_15,
    R.drawable.tex_venusian_16,
)

val ringTextures = listOf(
    R.drawable.tex_ring_0,
    R.drawable.tex_ring_1,
    R.drawable.tex_ring_2,
    R.drawable.tex_ring_3,
    R.drawable.tex_ring_4,
    R.drawable.tex_ring_5,
    R.drawable.tex_ring_6,
    R.drawable.tex_ring_7,
    R.drawable.tex_ring_8,
    R.drawable.tex_ring_9,
    R.drawable.tex_ring_10,
    R.drawable.tex_ring_11,
    R.drawable.tex_ring_12,
    R.drawable.tex_ring_13,
    R.drawable.tex_ring_14,
    R.drawable.tex_ring_15,
    R.drawable.tex_ring_16,
    R.drawable.tex_ring_17,
    R.drawable.tex_ring_18,
    R.drawable.tex_ring_19,
    R.drawable.tex_ring_20,
    R.drawable.tex_ring_21,
    R.drawable.tex_ring_22,
    R.drawable.tex_ring_23,
    R.drawable.tex_ring_24,
    R.drawable.tex_ring_25,
    R.drawable.tex_ring_26,
    R.drawable.tex_ring_27,
    R.drawable.tex_ring_28,
    R.drawable.tex_ring_29,
    R.drawable.tex_ring_30,
    R.drawable.tex_ring_31,
    R.drawable.tex_ring_32,
    R.drawable.tex_ring_33,
    R.drawable.tex_ring_34,
    R.drawable.tex_ring_35,
    R.drawable.tex_ring_36,
    R.drawable.tex_ring_37,
    R.drawable.tex_ring_38,
    R.drawable.tex_ring_39,
    R.drawable.tex_ring_40,
    R.drawable.tex_ring_41,
    R.drawable.tex_ring_42,
    R.drawable.tex_ring_43,
    R.drawable.tex_ring_44,
    R.drawable.tex_ring_45,
    R.drawable.tex_ring_46,
    R.drawable.tex_ring_47,
    R.drawable.tex_ring_48,
    R.drawable.tex_ring_49
)

val allPlanetTextures = listOf(
    gasGiantTextures,
    iceTextures,
    martianTextures,
    primordialTextures,
    rockTextures,
    terrestrialTextures,
    volcanicTextures,
    venusianTextures
)