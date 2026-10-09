package com.example.vk_education

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class FirstActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var text by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Введите текст или номер") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(onClick = { openSecond(text) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Открыть вторую Activity")
                }

                Button(onClick = { callFriend(text) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Позвонить другу")
                }

                Button(onClick = { shareText(text) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Поделиться текстом")
                }
            }
        }
    }

    private fun openSecond(text: String) {
        if (text.isBlank()) {
            showError("Введите текст")
            return
        }
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("text", text)
        startActivity(intent)
    }

    private fun callFriend(phone: String) {
        val isValid = phone.isNotBlank() && phone.all { it.isDigit() || it == '+' }
        if (!isValid) {
            showError("Введите корректный номер телефона")
            return
        }
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(intent)
    }

    private fun shareText(text: String) {
        if (text.isBlank()) {
            showError("Введите текст")
            return
        }
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, "Поделиться через..."))
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}