package com.marsanix.ledgo.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.marsanix.ledgo.R

const val GURU_NAME = "Marsani"
const val GURU_TITLE = "Android Developer"
const val GURU_EMAIL = "marsanix@gmail.com"
const val GURU_PHONE = "081277331500"
const val GURU_ABOUT = "Entusiast with Full-Stack Software Development, Cybersecurity, and Artificial Intelligence"

@Composable
fun ProfileScreen(navController: NavController) {
    Column(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()
        .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProfileScreen(
            ProfileData(
                photo =  painterResource(R.drawable.photo),
                name = GURU_NAME,
                title = GURU_TITLE,
                email = GURU_EMAIL,
                phone = GURU_PHONE,
                about = GURU_ABOUT
            )
        )

    }
}

@Composable
fun ProfileScreen(profileData: ProfileData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 60.dp, horizontal = 16.dp)

            .drawWithContent {

                drawCircle(
                    color = Color(0x4CAF50FF).copy(alpha = 0.1f),
                    radius = 500f,
                    center = Offset(size.width, 50f )
                )
                drawCircle(
                    color = Color(0xFF03A9F4).copy(alpha = 0.1f),
                    radius = 300f,
                    center = Offset(20f, 580f )
                )
                drawCircle(
                    color = Color(0xFF4CAF50).copy(alpha = 0.1f),
                    radius = 300f,
                    center = Offset(size.width - 20, size.height- 200 )
                )
                drawContent()
            }
        ,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        // Profile Picture
        Image(
            painter = profileData.photo,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User Information
        Text(
            text = profileData.name,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black

        )
        Text(
            text = profileData.title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contact Information
        Row {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Email"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = profileData.email, color = Color.Black)
        }

        Row {
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = "Phone"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = profileData.phone, color = Color.Black)
        }
        // Add similar rows for phone number, social media links, etc.

        Spacer(modifier = Modifier.height(16.dp))

        // About Section (Optional)
        if (profileData.about.isNotEmpty()) {
            Text(
                text = profileData.about,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                textAlign = TextAlign.Center

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.padding(20.dp)
                .background(Color(0xFF2AAB79), shape = RoundedCornerShape(15.dp))
        ) {
            Column(
                Modifier.padding(16.dp)
            ) {
                Text("Progress Pencapaian:", color = Color.White)
                Text("4 dari 7 Materi", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

    }
}

data class ProfileData(
    val photo: Painter,
    val name: String,
    val title: String,
    val email: String,
    val phone: String,
    val about: String = "",
)