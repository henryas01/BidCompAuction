package com.example.bidcompauction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bidcompauction.ui.theme.BidTheme

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BidTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    SignUpScreen(
                        onSignUp = { email, pass, confirm ->
                            // TODO: handle sign up
                        },
                        onBackToLogin = {
                            // TODO: finish() or navigate back to login
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(
    onSignUp: (String, String, String) -> Unit,
    onBackToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var passVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    val passwordsMatch = password.isNotEmpty() && confirm.isNotEmpty() && password == confirm
    val canSubmit = email.isNotBlank() && password.length >= 8 && passwordsMatch

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF080808))
    ) {
        // --- AKSEN GLOW (Konsisten dengan Login) ---
        Box(
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.TopEnd)
                .offset(x = 120.dp, y = (-120).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x15FFC107), Color.Transparent)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .verticalScroll(rememberScrollState()), // Tambahkan scroll agar aman di layar kecil
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(Modifier.height(64.dp))

            // --- HEADER ---
            Column {
                Text(
                    text = "BID",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    ),
                    color = Color.White
                )
                Text(
                    text = "CompAuction",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraLight
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(32.dp))

            Text(
                text = "Create Account",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Spacer(Modifier.height(16.dp))

            // --- FORM CARD (Glassmorphism) ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0x0FFFFFFF))
                    .border(1.dp, Color(0x12FFFFFF), RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Label("Email Address")
                Spacer(Modifier.height(8.dp))
                CompactTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Enter your email",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
                )

                Spacer(Modifier.height(18.dp))

                Label("Password")
                Spacer(Modifier.height(8.dp))
                CompactTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Min. 8 characters",
                    visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        EyeToggle(visible = passVisible, onToggle = { passVisible = !passVisible })
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
                )

                Spacer(Modifier.height(18.dp))

                Label("Confirm Password")
                Spacer(Modifier.height(8.dp))
                CompactTextField(
                    value = confirm,
                    onValueChange = { confirm = it },
                    placeholder = "Repeat password",
                    visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        EyeToggle(visible = confirmVisible, onToggle = { confirmVisible = !confirmVisible })
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
                )

                // Error/Helper Text yang lebih smooth
                Spacer(Modifier.height(12.dp))
                val helperColor = if (password.isNotEmpty() && confirm.isNotEmpty() && !passwordsMatch)
                    Color(0xFFFF5252) else Color.White.copy(alpha = 0.4f)

                Text(
                    text = if (password.isNotEmpty() && confirm.isNotEmpty() && !passwordsMatch)
                        "Passwords do not match" else "Use 8 or more characters",
                    style = MaterialTheme.typography.labelSmall,
                    color = helperColor
                )
            }

            Spacer(Modifier.height(32.dp))

            // --- BUTTONS ---
            Button(
                onClick = { onSignUp(email.trim(), password, confirm) },
                enabled = canSubmit,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
            ) {
                Text(
                    "CREATE ACCOUNT",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
            }

            TextButton(
                onClick = onBackToLogin,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
            ) {
                Text(
                    "Already have an account? Login",
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(Modifier.height(40.dp))

            // --- GPU IMAGE WITH FADE ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(bottom = 20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gpu),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color(0xFF080808))
                            )
                        )
                )
            }
        }
    }
}

@Composable
private fun Label(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = Color.White.copy(alpha = 0.5f)
    )
}

@Composable
private fun EyeToggle(visible: Boolean, onToggle: () -> Unit) {
    IconButton(onClick = onToggle) {
        Icon(
            imageVector = if (visible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.3f)
        )
    }
}

@Composable
private fun CompactTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = { Text(placeholder, color = Color.White.copy(alpha = 0.2f)) },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0x1AFFFFFF),
            unfocusedContainerColor = Color(0x0AFFFFFF),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color(0xFFFFC107),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(name = "Sign Up Screen", showBackground = true, device = Devices.PIXEL_4)
@Composable
fun SignUpScreenPreview() {
    BidTheme {
        SignUpScreen(
            onSignUp = { _, _, _ -> },
            onBackToLogin = {}
        )
    }
}
