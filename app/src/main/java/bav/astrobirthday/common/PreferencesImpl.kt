package bav.astrobirthday.common

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.moshi.moshi
import hu.autsoft.krate.moshi.moshiPref
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PreferencesImpl(context: Context) : Preferences, SimpleKrate(context) {

    init {
        moshi = Moshi.Builder()
            .add(LocalDateAdapter())
            .build()
    }

    override var userBirthday: LocalDate? by moshiPref(BIRTHDAY_KEY)

    override fun setBirthday(value: LocalDate?) {
        userBirthday = value
        _birthdayDate.postValue(value)
    }

    private val _birthdayDate = MutableLiveData<LocalDate?>().apply {
        value = userBirthday
    }
    override val birthdayDate: LiveData<LocalDate?> = _birthdayDate

    inner class LocalDateAdapter {
        @FromJson
        fun fromJson(value: String?) =
            value?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }

        @ToJson
        fun toJson(value: LocalDate?) =
            value?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    companion object {
        const val BIRTHDAY_KEY = "birthday_key"
    }
}