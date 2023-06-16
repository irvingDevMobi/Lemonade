package dev.irving.lemonade

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class LemonadeUiModel(
    @StringRes val instructionResource: Int,
    @DrawableRes val imageResource: Int,
    @StringRes val imageDescriptionResource: Int
)
