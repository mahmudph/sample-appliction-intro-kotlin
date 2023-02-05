package id.myone.onboardinguigiant.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnboardData(
    val title: String,
    val image: Int,
    val background: Int,
): Parcelable
