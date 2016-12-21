package com.vinted.data.prefs

import android.content.SharedPreferences
import lt.vilnius.tvarkau.prefs.BasePreferenceImpl
import lt.vilnius.tvarkau.prefs.IntPreference
import lt.vilnius.tvarkau.prefs.Serializer

/**
 * @author Martynas Jurkus
 *
 * @since 2014-03-24
 */
open class IntPreferenceImpl(preferences: SharedPreferences,
                             key: String,
                             default: Int) : BasePreferenceImpl<Int>(preferences, key, default, IntSerializer), IntPreference {

    private object IntSerializer : Serializer<Int> {
        override fun serialize(storage: SharedPreferences.Editor, key: String, value: Int) {
            storage.putInt(key, value)
        }

        override fun unserialize(source: SharedPreferences, key: String, default: Int): Int {
            return source.getInt(key, default)
        }
    }
}
