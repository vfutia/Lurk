package com.vfutia.lurk.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.vfutia.lurk.R
import com.vfutia.lurk.ui.theme.Typography

@Composable
fun MenuHeader(title: String) {
    Text(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxSize(),
        text = title,
        style = Typography.bodyLarge,
    )

    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = dimensionResource(id = R.dimen.divider_thickness)
    )
}