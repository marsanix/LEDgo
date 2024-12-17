package com.marsanix.ledgo.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marsanix.ledgo.data.SharedPreferencesManager

@Composable
fun SettingAlertDialog(openDialog: MutableState<Boolean>) {
    if (openDialog.value) {

        val context = LocalContext.current
        val sharedPreferencesManager = remember {
            SharedPreferencesManager(context)
        }
        var name by remember { mutableStateOf(sharedPreferencesManager.userName) }
        LaunchedEffect(name) {
            sharedPreferencesManager.userName = name
        }

        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Ubah nama Anda!", fontWeight = FontWeight.Bold)
            },
            text = {
                Column(
                    modifier = Modifier.offset(y = 10.dp)
                ) {
                    OutlinedTextField(
                        onValueChange = {
                            name = it
                        },
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                        label = { Text("") },
                        value = name?:""
                    )
                }
            },
            confirmButton = {

                Row(
                    modifier = Modifier.padding(end = 15.dp)
                ) {
                    Button(
                        onClick = {
                            openDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = Color.Gray
                        )

                    ) {
                        Text(text = "Batal")
                    }
                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )
                    Button(
                        onClick = {

                            sharedPreferencesManager.userName = name
                            openDialog.value = false

                            Toast.makeText(context, "Perubahan nama Anda berhasil di simpan", Toast.LENGTH_SHORT).show()

                        }
                    ) {
                        Text(text = "Simpan")
                    }
                }


            }
        )
    }
}











