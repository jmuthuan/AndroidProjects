package com.jmuthuan.treely.ui.persons

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jmuthuan.treely.R
import com.jmuthuan.treely.ui.theme.TreelyTheme
import com.jmuthuan.treely.ui.theme.primaryContainerDarkMediumContrast
import com.jmuthuan.treely.ui.theme.secondaryContainerDarkMediumContrast
import com.jmuthuan.treely.ui.theme.tertiaryContainerDarkMediumContrast
import com.jmuthuan.treely.utils.Gender

@Composable
fun PersonCardTree(
    name: String,
    birthday: String,
    picture: Painter? = null, //TODO check if pass Painter or id to fetch data
    gender: Gender = Gender.OTHER,
    modifier: Modifier = Modifier
) {
    val backgorundCardColor = when(gender) {
        Gender.MALE -> primaryContainerDarkMediumContrast
        Gender.FEMALE -> tertiaryContainerDarkMediumContrast
        Gender.OTHER -> secondaryContainerDarkMediumContrast

    }

    Card(
        modifier = Modifier
            .size(width = 240.dp, height = 136.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgorundCardColor
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 16.dp
        )

    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.person_add),
                    contentDescription = stringResource(R.string.add_person_icon_button),
                    modifier = Modifier.clickable {
                        //TODO add relative person (show animation with options)
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = birthday,
                    fontSize = 16.sp,
//TODO replace fontSize with: style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 4.dp)
//TODO replace fontSize with: style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar_profile), //TODO
                    contentDescription = stringResource(id = R.string.profile_picture),
                    modifier = Modifier
                        .shadow(
                            elevation = 24.dp,
                            shape = RoundedCornerShape(16.dp),
                            clip = true,
                            spotColor = Color.Black
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .size(72.dp)
                )

                Spacer(modifier = Modifier.height(24.dp) )

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.more_horiz),
                    contentDescription = stringResource(id = R.string.more_horizontal_icon),
                    modifier = Modifier.clickable {
                        //TODO show options: show details / delete
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonCardTreePreview() {
    TreelyTheme {
        PersonCardTree("", "")
    }
}