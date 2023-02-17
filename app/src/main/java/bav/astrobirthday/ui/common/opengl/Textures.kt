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
    R.drawable.tex_ring_50,
    R.drawable.tex_ring_51,
    R.drawable.tex_ring_52,
    R.drawable.tex_ring_53,
    R.drawable.tex_ring_54,
    R.drawable.tex_ring_55,
    R.drawable.tex_ring_56,
    R.drawable.tex_ring_57,
    R.drawable.tex_ring_58,
    R.drawable.tex_ring_59,
    R.drawable.tex_ring_60,
    R.drawable.tex_ring_61,
    R.drawable.tex_ring_62,
    R.drawable.tex_ring_63,
    R.drawable.tex_ring_64,
    R.drawable.tex_ring_65,
    R.drawable.tex_ring_66,
    R.drawable.tex_ring_67,
    R.drawable.tex_ring_68,
    R.drawable.tex_ring_69,
    R.drawable.tex_ring_70,
    R.drawable.tex_ring_71,
    R.drawable.tex_ring_72,
    R.drawable.tex_ring_73,
    R.drawable.tex_ring_74,
    R.drawable.tex_ring_75,
    R.drawable.tex_ring_76,
    R.drawable.tex_ring_77,
    R.drawable.tex_ring_78,
    R.drawable.tex_ring_79,
    R.drawable.tex_ring_80,
    R.drawable.tex_ring_81,
    R.drawable.tex_ring_82,
    R.drawable.tex_ring_83,
    R.drawable.tex_ring_84,
    R.drawable.tex_ring_85,
    R.drawable.tex_ring_86,
    R.drawable.tex_ring_87,
    R.drawable.tex_ring_88,
    R.drawable.tex_ring_89,
    R.drawable.tex_ring_90,
    R.drawable.tex_ring_91,
    R.drawable.tex_ring_92,
    R.drawable.tex_ring_93,
    R.drawable.tex_ring_94,
    R.drawable.tex_ring_95,
    R.drawable.tex_ring_96,
    R.drawable.tex_ring_97,
    R.drawable.tex_ring_98,
    R.drawable.tex_ring_99
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