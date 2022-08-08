package bav.astrobirthday.ui.common.opengl

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils


object TextureUtils {
    fun loadTexture(context: Context, resourceId: Int): Int {
        // создание объекта текстуры
        val textureIds = IntArray(1)


        //создаем пустой массив из одного элемента
        //в этот массив OpenGL ES запишет свободный номер текстуры,
        // получаем свободное имя текстуры, которое будет записано в names[0]
        GLES20.glGenTextures(1, textureIds, 0)
        if (textureIds[0] == 0) {
            return 0
        }

        //This flag is turned on by default and should be turned off if you need a non-scaled version of the bitmap.
        val options = BitmapFactory.Options()
        options.inScaled = false
        // получение Bitmap
        val bitmap = BitmapFactory.decodeResource(
            context.resources, resourceId, options
        )
        if (bitmap == null) {
            GLES20.glDeleteTextures(1, textureIds, 0)
            return 0
        }

        //glActiveTexture — select active texture unit
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        //делаем текстуру с именем textureIds[0] текущей
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0])

        //учитываем прозрачность текстуры
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
        GLES20.glEnable(GLES20.GL_BLEND)
        //включаем фильтры
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        //переписываем Bitmap в память видеокарты
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        // удаляем Bitmap из памяти, т.к. картинка уже переписана в видеопамять
        bitmap.recycle()

        // сброс приязки объекта текстуры к блоку текстуры
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        return textureIds[0]
    }
}