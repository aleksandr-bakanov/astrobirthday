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
    R.drawable.tex_gas_giant_17,
    R.drawable.tex_gas_giant_18,
    R.drawable.tex_gas_giant_19,
    R.drawable.tex_gas_giant_20,
    R.drawable.tex_gas_giant_21,
    R.drawable.tex_gas_giant_22,
    R.drawable.tex_gas_giant_23,
    R.drawable.tex_gas_giant_24,
    R.drawable.tex_gas_giant_25,
    R.drawable.tex_gas_giant_26,
    R.drawable.tex_gas_giant_27,
    R.drawable.tex_gas_giant_28,
    R.drawable.tex_gas_giant_29,
    R.drawable.tex_gas_giant_30,
    R.drawable.tex_gas_giant_31,
    R.drawable.tex_gas_giant_32,
    R.drawable.tex_gas_giant_33,
    R.drawable.tex_gas_giant_34,
    R.drawable.tex_gas_giant_35,
    R.drawable.tex_gas_giant_36,
    R.drawable.tex_gas_giant_37,
    R.drawable.tex_gas_giant_38,
    R.drawable.tex_gas_giant_39,
    R.drawable.tex_gas_giant_40,
    R.drawable.tex_gas_giant_41,
    R.drawable.tex_gas_giant_42,
    R.drawable.tex_gas_giant_43,
    R.drawable.tex_gas_giant_44,
    R.drawable.tex_gas_giant_45,
    R.drawable.tex_gas_giant_46,
    R.drawable.tex_gas_giant_47,
    R.drawable.tex_gas_giant_48,
    R.drawable.tex_gas_giant_49,
    R.drawable.tex_gas_giant_50,
    R.drawable.tex_gas_giant_51,
    R.drawable.tex_gas_giant_52,
    R.drawable.tex_gas_giant_53,
    R.drawable.tex_gas_giant_54,
    R.drawable.tex_gas_giant_55,
    R.drawable.tex_gas_giant_56,
    R.drawable.tex_gas_giant_57,
    R.drawable.tex_gas_giant_58,
    R.drawable.tex_gas_giant_59,
    R.drawable.tex_gas_giant_60,
    R.drawable.tex_gas_giant_61,
    R.drawable.tex_gas_giant_62,
    R.drawable.tex_gas_giant_63,
    R.drawable.tex_gas_giant_64,
    R.drawable.tex_gas_giant_65,
    R.drawable.tex_gas_giant_66,
    R.drawable.tex_gas_giant_67,
    R.drawable.tex_gas_giant_68,
    R.drawable.tex_gas_giant_69,
    R.drawable.tex_gas_giant_70,
    R.drawable.tex_gas_giant_71,
    R.drawable.tex_gas_giant_72,
    R.drawable.tex_gas_giant_73,
    R.drawable.tex_gas_giant_74,
    R.drawable.tex_gas_giant_75,
    R.drawable.tex_gas_giant_76,
    R.drawable.tex_gas_giant_77,
    R.drawable.tex_gas_giant_78,
    R.drawable.tex_gas_giant_79,
    R.drawable.tex_gas_giant_80,
    R.drawable.tex_gas_giant_81,
    R.drawable.tex_gas_giant_82,
    R.drawable.tex_gas_giant_83,
    R.drawable.tex_gas_giant_84,
    R.drawable.tex_gas_giant_85,
    R.drawable.tex_gas_giant_86,
    R.drawable.tex_gas_giant_87,
    R.drawable.tex_gas_giant_88,
    R.drawable.tex_gas_giant_89,
    R.drawable.tex_gas_giant_90,
    R.drawable.tex_gas_giant_91,
    R.drawable.tex_gas_giant_92,
    R.drawable.tex_gas_giant_93,
    R.drawable.tex_gas_giant_94,
    R.drawable.tex_gas_giant_95,
    R.drawable.tex_gas_giant_96,
    R.drawable.tex_gas_giant_97,
    R.drawable.tex_gas_giant_98,
    R.drawable.tex_gas_giant_99,
    R.drawable.tex_gas_giant_100
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
    R.drawable.tex_ice_17,
    R.drawable.tex_ice_18,
    R.drawable.tex_ice_19,
    R.drawable.tex_ice_20,
    R.drawable.tex_ice_21,
    R.drawable.tex_ice_22,
    R.drawable.tex_ice_23,
    R.drawable.tex_ice_24,
    R.drawable.tex_ice_25,
    R.drawable.tex_ice_26,
    R.drawable.tex_ice_27,
    R.drawable.tex_ice_28,
    R.drawable.tex_ice_29,
    R.drawable.tex_ice_30,
    R.drawable.tex_ice_31,
    R.drawable.tex_ice_32,
    R.drawable.tex_ice_33,
    R.drawable.tex_ice_34,
    R.drawable.tex_ice_35,
    R.drawable.tex_ice_36,
    R.drawable.tex_ice_37,
    R.drawable.tex_ice_38,
    R.drawable.tex_ice_39,
    R.drawable.tex_ice_40,
    R.drawable.tex_ice_41,
    R.drawable.tex_ice_42,
    R.drawable.tex_ice_43,
    R.drawable.tex_ice_44,
    R.drawable.tex_ice_45,
    R.drawable.tex_ice_46,
    R.drawable.tex_ice_47,
    R.drawable.tex_ice_48,
    R.drawable.tex_ice_49,
    R.drawable.tex_ice_50,
    R.drawable.tex_ice_51,
    R.drawable.tex_ice_52,
    R.drawable.tex_ice_53,
    R.drawable.tex_ice_54,
    R.drawable.tex_ice_55,
    R.drawable.tex_ice_56,
    R.drawable.tex_ice_57,
    R.drawable.tex_ice_58,
    R.drawable.tex_ice_59,
    R.drawable.tex_ice_60,
    R.drawable.tex_ice_61,
    R.drawable.tex_ice_62,
    R.drawable.tex_ice_63,
    R.drawable.tex_ice_64,
    R.drawable.tex_ice_65,
    R.drawable.tex_ice_66,
    R.drawable.tex_ice_67,
    R.drawable.tex_ice_68,
    R.drawable.tex_ice_69,
    R.drawable.tex_ice_70,
    R.drawable.tex_ice_71,
    R.drawable.tex_ice_72,
    R.drawable.tex_ice_73,
    R.drawable.tex_ice_74,
    R.drawable.tex_ice_75,
    R.drawable.tex_ice_76,
    R.drawable.tex_ice_77,
    R.drawable.tex_ice_78,
    R.drawable.tex_ice_79,
    R.drawable.tex_ice_80,
    R.drawable.tex_ice_81,
    R.drawable.tex_ice_82,
    R.drawable.tex_ice_83,
    R.drawable.tex_ice_84,
    R.drawable.tex_ice_85,
    R.drawable.tex_ice_86,
    R.drawable.tex_ice_87,
    R.drawable.tex_ice_88,
    R.drawable.tex_ice_89,
    R.drawable.tex_ice_90,
    R.drawable.tex_ice_91,
    R.drawable.tex_ice_92,
    R.drawable.tex_ice_93,
    R.drawable.tex_ice_94,
    R.drawable.tex_ice_95,
    R.drawable.tex_ice_96,
    R.drawable.tex_ice_97,
    R.drawable.tex_ice_98,
    R.drawable.tex_ice_99,
    R.drawable.tex_ice_100
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
    R.drawable.tex_martian_17,
    R.drawable.tex_martian_18,
    R.drawable.tex_martian_19,
    R.drawable.tex_martian_20,
    R.drawable.tex_martian_21,
    R.drawable.tex_martian_22,
    R.drawable.tex_martian_23,
    R.drawable.tex_martian_24,
    R.drawable.tex_martian_25,
    R.drawable.tex_martian_26,
    R.drawable.tex_martian_27,
    R.drawable.tex_martian_28,
    R.drawable.tex_martian_29,
    R.drawable.tex_martian_30,
    R.drawable.tex_martian_31,
    R.drawable.tex_martian_32,
    R.drawable.tex_martian_33,
    R.drawable.tex_martian_34,
    R.drawable.tex_martian_35,
    R.drawable.tex_martian_36,
    R.drawable.tex_martian_37,
    R.drawable.tex_martian_38,
    R.drawable.tex_martian_39,
    R.drawable.tex_martian_40,
    R.drawable.tex_martian_41,
    R.drawable.tex_martian_42,
    R.drawable.tex_martian_43,
    R.drawable.tex_martian_44,
    R.drawable.tex_martian_45,
    R.drawable.tex_martian_46,
    R.drawable.tex_martian_47,
    R.drawable.tex_martian_48,
    R.drawable.tex_martian_49,
    R.drawable.tex_martian_50,
    R.drawable.tex_martian_51,
    R.drawable.tex_martian_52,
    R.drawable.tex_martian_53,
    R.drawable.tex_martian_54,
    R.drawable.tex_martian_55,
    R.drawable.tex_martian_56,
    R.drawable.tex_martian_57,
    R.drawable.tex_martian_58,
    R.drawable.tex_martian_59,
    R.drawable.tex_martian_60,
    R.drawable.tex_martian_61,
    R.drawable.tex_martian_62,
    R.drawable.tex_martian_63,
    R.drawable.tex_martian_64,
    R.drawable.tex_martian_65,
    R.drawable.tex_martian_66,
    R.drawable.tex_martian_67,
    R.drawable.tex_martian_68,
    R.drawable.tex_martian_69,
    R.drawable.tex_martian_70,
    R.drawable.tex_martian_71,
    R.drawable.tex_martian_72,
    R.drawable.tex_martian_73,
    R.drawable.tex_martian_74,
    R.drawable.tex_martian_75,
    R.drawable.tex_martian_76,
    R.drawable.tex_martian_77,
    R.drawable.tex_martian_78,
    R.drawable.tex_martian_79,
    R.drawable.tex_martian_80,
    R.drawable.tex_martian_81,
    R.drawable.tex_martian_82,
    R.drawable.tex_martian_83,
    R.drawable.tex_martian_84,
    R.drawable.tex_martian_85,
    R.drawable.tex_martian_86,
    R.drawable.tex_martian_87,
    R.drawable.tex_martian_88,
    R.drawable.tex_martian_89,
    R.drawable.tex_martian_90,
    R.drawable.tex_martian_91,
    R.drawable.tex_martian_92,
    R.drawable.tex_martian_93,
    R.drawable.tex_martian_94,
    R.drawable.tex_martian_95,
    R.drawable.tex_martian_96,
    R.drawable.tex_martian_97,
    R.drawable.tex_martian_98,
    R.drawable.tex_martian_99,
    R.drawable.tex_martian_100
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
    R.drawable.tex_primordial_17,
    R.drawable.tex_primordial_18,
    R.drawable.tex_primordial_19,
    R.drawable.tex_primordial_20,
    R.drawable.tex_primordial_21,
    R.drawable.tex_primordial_22,
    R.drawable.tex_primordial_23,
    R.drawable.tex_primordial_24,
    R.drawable.tex_primordial_25,
    R.drawable.tex_primordial_26,
    R.drawable.tex_primordial_27,
    R.drawable.tex_primordial_28,
    R.drawable.tex_primordial_29,
    R.drawable.tex_primordial_30,
    R.drawable.tex_primordial_31,
    R.drawable.tex_primordial_32,
    R.drawable.tex_primordial_33,
    R.drawable.tex_primordial_34,
    R.drawable.tex_primordial_35,
    R.drawable.tex_primordial_36,
    R.drawable.tex_primordial_37,
    R.drawable.tex_primordial_38,
    R.drawable.tex_primordial_39,
    R.drawable.tex_primordial_40,
    R.drawable.tex_primordial_41,
    R.drawable.tex_primordial_42,
    R.drawable.tex_primordial_43,
    R.drawable.tex_primordial_44,
    R.drawable.tex_primordial_45,
    R.drawable.tex_primordial_46,
    R.drawable.tex_primordial_47,
    R.drawable.tex_primordial_48,
    R.drawable.tex_primordial_49,
    R.drawable.tex_primordial_50,
    R.drawable.tex_primordial_51,
    R.drawable.tex_primordial_52,
    R.drawable.tex_primordial_53,
    R.drawable.tex_primordial_54,
    R.drawable.tex_primordial_55,
    R.drawable.tex_primordial_56,
    R.drawable.tex_primordial_57,
    R.drawable.tex_primordial_58,
    R.drawable.tex_primordial_59,
    R.drawable.tex_primordial_60,
    R.drawable.tex_primordial_61,
    R.drawable.tex_primordial_62,
    R.drawable.tex_primordial_63,
    R.drawable.tex_primordial_64,
    R.drawable.tex_primordial_65,
    R.drawable.tex_primordial_66,
    R.drawable.tex_primordial_67,
    R.drawable.tex_primordial_68,
    R.drawable.tex_primordial_69,
    R.drawable.tex_primordial_70,
    R.drawable.tex_primordial_71,
    R.drawable.tex_primordial_72,
    R.drawable.tex_primordial_73,
    R.drawable.tex_primordial_74,
    R.drawable.tex_primordial_75,
    R.drawable.tex_primordial_76,
    R.drawable.tex_primordial_77,
    R.drawable.tex_primordial_78,
    R.drawable.tex_primordial_79,
    R.drawable.tex_primordial_80,
    R.drawable.tex_primordial_81,
    R.drawable.tex_primordial_82,
    R.drawable.tex_primordial_83,
    R.drawable.tex_primordial_84,
    R.drawable.tex_primordial_85,
    R.drawable.tex_primordial_86,
    R.drawable.tex_primordial_87,
    R.drawable.tex_primordial_88,
    R.drawable.tex_primordial_89,
    R.drawable.tex_primordial_90,
    R.drawable.tex_primordial_91,
    R.drawable.tex_primordial_92,
    R.drawable.tex_primordial_93,
    R.drawable.tex_primordial_94,
    R.drawable.tex_primordial_95,
    R.drawable.tex_primordial_96,
    R.drawable.tex_primordial_97,
    R.drawable.tex_primordial_98,
    R.drawable.tex_primordial_99,
    R.drawable.tex_primordial_100
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
    R.drawable.tex_rock_17,
    R.drawable.tex_rock_18,
    R.drawable.tex_rock_19,
    R.drawable.tex_rock_20,
    R.drawable.tex_rock_21,
    R.drawable.tex_rock_22,
    R.drawable.tex_rock_23,
    R.drawable.tex_rock_24,
    R.drawable.tex_rock_25,
    R.drawable.tex_rock_26,
    R.drawable.tex_rock_27,
    R.drawable.tex_rock_28,
    R.drawable.tex_rock_29,
    R.drawable.tex_rock_30,
    R.drawable.tex_rock_31,
    R.drawable.tex_rock_32,
    R.drawable.tex_rock_33,
    R.drawable.tex_rock_34,
    R.drawable.tex_rock_35,
    R.drawable.tex_rock_36,
    R.drawable.tex_rock_37,
    R.drawable.tex_rock_38,
    R.drawable.tex_rock_39,
    R.drawable.tex_rock_40,
    R.drawable.tex_rock_41,
    R.drawable.tex_rock_42,
    R.drawable.tex_rock_43,
    R.drawable.tex_rock_44,
    R.drawable.tex_rock_45,
    R.drawable.tex_rock_46,
    R.drawable.tex_rock_47,
    R.drawable.tex_rock_48,
    R.drawable.tex_rock_49,
    R.drawable.tex_rock_50,
    R.drawable.tex_rock_51,
    R.drawable.tex_rock_52,
    R.drawable.tex_rock_53,
    R.drawable.tex_rock_54,
    R.drawable.tex_rock_55,
    R.drawable.tex_rock_56,
    R.drawable.tex_rock_57,
    R.drawable.tex_rock_58,
    R.drawable.tex_rock_59,
    R.drawable.tex_rock_60,
    R.drawable.tex_rock_61,
    R.drawable.tex_rock_62,
    R.drawable.tex_rock_63,
    R.drawable.tex_rock_64,
    R.drawable.tex_rock_65,
    R.drawable.tex_rock_66,
    R.drawable.tex_rock_67,
    R.drawable.tex_rock_68,
    R.drawable.tex_rock_69,
    R.drawable.tex_rock_70,
    R.drawable.tex_rock_71,
    R.drawable.tex_rock_72,
    R.drawable.tex_rock_73,
    R.drawable.tex_rock_74,
    R.drawable.tex_rock_75,
    R.drawable.tex_rock_76,
    R.drawable.tex_rock_77,
    R.drawable.tex_rock_78,
    R.drawable.tex_rock_79,
    R.drawable.tex_rock_80,
    R.drawable.tex_rock_81,
    R.drawable.tex_rock_82,
    R.drawable.tex_rock_83,
    R.drawable.tex_rock_84,
    R.drawable.tex_rock_85,
    R.drawable.tex_rock_86,
    R.drawable.tex_rock_87,
    R.drawable.tex_rock_88,
    R.drawable.tex_rock_89,
    R.drawable.tex_rock_90,
    R.drawable.tex_rock_91,
    R.drawable.tex_rock_92,
    R.drawable.tex_rock_93,
    R.drawable.tex_rock_94,
    R.drawable.tex_rock_95,
    R.drawable.tex_rock_96,
    R.drawable.tex_rock_97,
    R.drawable.tex_rock_98,
    R.drawable.tex_rock_99,
    R.drawable.tex_rock_100
)

val terrestrialCloudsTextures = listOf(
    R.drawable.tex_terrestrial_clouds_1,
    R.drawable.tex_terrestrial_clouds_2,
    R.drawable.tex_terrestrial_clouds_3,
    R.drawable.tex_terrestrial_clouds_4,
    R.drawable.tex_terrestrial_clouds_5,
    R.drawable.tex_terrestrial_clouds_6,
    R.drawable.tex_terrestrial_clouds_7,
    R.drawable.tex_terrestrial_clouds_8,
    R.drawable.tex_terrestrial_clouds_9,
    R.drawable.tex_terrestrial_clouds_10,
    R.drawable.tex_terrestrial_clouds_11,
    R.drawable.tex_terrestrial_clouds_12,
    R.drawable.tex_terrestrial_clouds_13,
    R.drawable.tex_terrestrial_clouds_14,
    R.drawable.tex_terrestrial_clouds_15,
    R.drawable.tex_terrestrial_clouds_16,
    R.drawable.tex_terrestrial_clouds_17,
    R.drawable.tex_terrestrial_clouds_18,
    R.drawable.tex_terrestrial_clouds_19,
    R.drawable.tex_terrestrial_clouds_20,
    R.drawable.tex_terrestrial_clouds_21,
    R.drawable.tex_terrestrial_clouds_22,
    R.drawable.tex_terrestrial_clouds_23,
    R.drawable.tex_terrestrial_clouds_24,
    R.drawable.tex_terrestrial_clouds_25,
    R.drawable.tex_terrestrial_clouds_26,
    R.drawable.tex_terrestrial_clouds_27,
    R.drawable.tex_terrestrial_clouds_28,
    R.drawable.tex_terrestrial_clouds_29,
    R.drawable.tex_terrestrial_clouds_30,
    R.drawable.tex_terrestrial_clouds_31,
    R.drawable.tex_terrestrial_clouds_32,
    R.drawable.tex_terrestrial_clouds_33,
    R.drawable.tex_terrestrial_clouds_34,
    R.drawable.tex_terrestrial_clouds_35,
    R.drawable.tex_terrestrial_clouds_36,
    R.drawable.tex_terrestrial_clouds_37,
    R.drawable.tex_terrestrial_clouds_38,
    R.drawable.tex_terrestrial_clouds_39,
    R.drawable.tex_terrestrial_clouds_40,
    R.drawable.tex_terrestrial_clouds_41,
    R.drawable.tex_terrestrial_clouds_42,
    R.drawable.tex_terrestrial_clouds_43,
    R.drawable.tex_terrestrial_clouds_44,
    R.drawable.tex_terrestrial_clouds_45,
    R.drawable.tex_terrestrial_clouds_46,
    R.drawable.tex_terrestrial_clouds_47,
    R.drawable.tex_terrestrial_clouds_48,
    R.drawable.tex_terrestrial_clouds_49,
    R.drawable.tex_terrestrial_clouds_50,
    R.drawable.tex_terrestrial_clouds_51,
    R.drawable.tex_terrestrial_clouds_52,
    R.drawable.tex_terrestrial_clouds_53,
    R.drawable.tex_terrestrial_clouds_54,
    R.drawable.tex_terrestrial_clouds_55,
    R.drawable.tex_terrestrial_clouds_56,
    R.drawable.tex_terrestrial_clouds_57,
    R.drawable.tex_terrestrial_clouds_58,
    R.drawable.tex_terrestrial_clouds_59,
    R.drawable.tex_terrestrial_clouds_60,
    R.drawable.tex_terrestrial_clouds_61,
    R.drawable.tex_terrestrial_clouds_62,
    R.drawable.tex_terrestrial_clouds_63,
    R.drawable.tex_terrestrial_clouds_64,
    R.drawable.tex_terrestrial_clouds_65,
    R.drawable.tex_terrestrial_clouds_66,
    R.drawable.tex_terrestrial_clouds_67,
    R.drawable.tex_terrestrial_clouds_68,
    R.drawable.tex_terrestrial_clouds_69,
    R.drawable.tex_terrestrial_clouds_70,
    R.drawable.tex_terrestrial_clouds_71,
    R.drawable.tex_terrestrial_clouds_72,
    R.drawable.tex_terrestrial_clouds_73,
    R.drawable.tex_terrestrial_clouds_74,
    R.drawable.tex_terrestrial_clouds_75,
    R.drawable.tex_terrestrial_clouds_76,
    R.drawable.tex_terrestrial_clouds_77,
    R.drawable.tex_terrestrial_clouds_78,
    R.drawable.tex_terrestrial_clouds_79,
    R.drawable.tex_terrestrial_clouds_80,
    R.drawable.tex_terrestrial_clouds_81,
    R.drawable.tex_terrestrial_clouds_82,
    R.drawable.tex_terrestrial_clouds_83,
    R.drawable.tex_terrestrial_clouds_84,
    R.drawable.tex_terrestrial_clouds_85,
    R.drawable.tex_terrestrial_clouds_86,
    R.drawable.tex_terrestrial_clouds_87,
    R.drawable.tex_terrestrial_clouds_88,
    R.drawable.tex_terrestrial_clouds_89,
    R.drawable.tex_terrestrial_clouds_90,
    R.drawable.tex_terrestrial_clouds_91,
    R.drawable.tex_terrestrial_clouds_92,
    R.drawable.tex_terrestrial_clouds_93,
    R.drawable.tex_terrestrial_clouds_94,
    R.drawable.tex_terrestrial_clouds_95,
    R.drawable.tex_terrestrial_clouds_96,
    R.drawable.tex_terrestrial_clouds_97,
    R.drawable.tex_terrestrial_clouds_98,
    R.drawable.tex_terrestrial_clouds_99,
    R.drawable.tex_terrestrial_clouds_100
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
    R.drawable.tex_volcanic_17,
    R.drawable.tex_volcanic_18,
    R.drawable.tex_volcanic_19,
    R.drawable.tex_volcanic_20,
    R.drawable.tex_volcanic_21,
    R.drawable.tex_volcanic_22,
    R.drawable.tex_volcanic_23,
    R.drawable.tex_volcanic_24,
    R.drawable.tex_volcanic_25,
    R.drawable.tex_volcanic_26,
    R.drawable.tex_volcanic_27,
    R.drawable.tex_volcanic_28,
    R.drawable.tex_volcanic_29,
    R.drawable.tex_volcanic_30,
    R.drawable.tex_volcanic_31,
    R.drawable.tex_volcanic_32,
    R.drawable.tex_volcanic_33,
    R.drawable.tex_volcanic_34,
    R.drawable.tex_volcanic_35,
    R.drawable.tex_volcanic_36,
    R.drawable.tex_volcanic_37,
    R.drawable.tex_volcanic_38,
    R.drawable.tex_volcanic_39,
    R.drawable.tex_volcanic_40,
    R.drawable.tex_volcanic_41,
    R.drawable.tex_volcanic_42,
    R.drawable.tex_volcanic_43,
    R.drawable.tex_volcanic_44,
    R.drawable.tex_volcanic_45,
    R.drawable.tex_volcanic_46,
    R.drawable.tex_volcanic_47,
    R.drawable.tex_volcanic_48,
    R.drawable.tex_volcanic_49,
    R.drawable.tex_volcanic_50,
    R.drawable.tex_volcanic_51,
    R.drawable.tex_volcanic_52,
    R.drawable.tex_volcanic_53,
    R.drawable.tex_volcanic_54,
    R.drawable.tex_volcanic_55,
    R.drawable.tex_volcanic_56,
    R.drawable.tex_volcanic_57,
    R.drawable.tex_volcanic_58,
    R.drawable.tex_volcanic_59,
    R.drawable.tex_volcanic_60,
    R.drawable.tex_volcanic_61,
    R.drawable.tex_volcanic_62,
    R.drawable.tex_volcanic_63,
    R.drawable.tex_volcanic_64,
    R.drawable.tex_volcanic_65,
    R.drawable.tex_volcanic_66,
    R.drawable.tex_volcanic_67,
    R.drawable.tex_volcanic_68,
    R.drawable.tex_volcanic_69,
    R.drawable.tex_volcanic_70,
    R.drawable.tex_volcanic_71,
    R.drawable.tex_volcanic_72,
    R.drawable.tex_volcanic_73,
    R.drawable.tex_volcanic_74,
    R.drawable.tex_volcanic_75,
    R.drawable.tex_volcanic_76,
    R.drawable.tex_volcanic_77,
    R.drawable.tex_volcanic_78,
    R.drawable.tex_volcanic_79,
    R.drawable.tex_volcanic_80,
    R.drawable.tex_volcanic_81,
    R.drawable.tex_volcanic_82,
    R.drawable.tex_volcanic_83,
    R.drawable.tex_volcanic_84,
    R.drawable.tex_volcanic_85,
    R.drawable.tex_volcanic_86,
    R.drawable.tex_volcanic_87,
    R.drawable.tex_volcanic_88,
    R.drawable.tex_volcanic_89,
    R.drawable.tex_volcanic_90,
    R.drawable.tex_volcanic_91,
    R.drawable.tex_volcanic_92,
    R.drawable.tex_volcanic_93,
    R.drawable.tex_volcanic_94,
    R.drawable.tex_volcanic_95,
    R.drawable.tex_volcanic_96,
    R.drawable.tex_volcanic_97,
    R.drawable.tex_volcanic_98,
    R.drawable.tex_volcanic_99,
    R.drawable.tex_volcanic_100
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
    R.drawable.tex_venusian_17,
    R.drawable.tex_venusian_18,
    R.drawable.tex_venusian_19,
    R.drawable.tex_venusian_20,
    R.drawable.tex_venusian_21,
    R.drawable.tex_venusian_22,
    R.drawable.tex_venusian_23,
    R.drawable.tex_venusian_24,
    R.drawable.tex_venusian_25,
    R.drawable.tex_venusian_26,
    R.drawable.tex_venusian_27,
    R.drawable.tex_venusian_28,
    R.drawable.tex_venusian_29,
    R.drawable.tex_venusian_30,
    R.drawable.tex_venusian_31,
    R.drawable.tex_venusian_32,
    R.drawable.tex_venusian_33,
    R.drawable.tex_venusian_34,
    R.drawable.tex_venusian_35,
    R.drawable.tex_venusian_36,
    R.drawable.tex_venusian_37,
    R.drawable.tex_venusian_38,
    R.drawable.tex_venusian_39,
    R.drawable.tex_venusian_40,
    R.drawable.tex_venusian_41,
    R.drawable.tex_venusian_42,
    R.drawable.tex_venusian_43,
    R.drawable.tex_venusian_44,
    R.drawable.tex_venusian_45,
    R.drawable.tex_venusian_46,
    R.drawable.tex_venusian_47,
    R.drawable.tex_venusian_48,
    R.drawable.tex_venusian_49,
    R.drawable.tex_venusian_50
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
    terrestrialCloudsTextures,
    volcanicTextures,
    venusianTextures
)