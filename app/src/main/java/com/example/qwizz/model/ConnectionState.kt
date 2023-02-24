package com.example.qwizz.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class ConnectionState: Parcelable {
    object Available : ConnectionState() {
        override fun toString(): String {
            return "Available"
        }
    }

    object Unavailable : ConnectionState() {
        override fun toString(): String {
            return "Unavailable"
        }
    }
}