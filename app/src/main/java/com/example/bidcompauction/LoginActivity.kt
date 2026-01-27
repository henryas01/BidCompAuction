package com.example.bidcompauction


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.bidcompauction.data.viewmodel.LoginState
import com.example.bidcompauction.data.viewmodel.LoginViewModel
import com.example.bidcompauction.utils.AuthManager
import com.example.bidcompauction.utils.NavigationUtils

class LoginActivity : ComponentActivity() {

    private val authManager by lazy { AuthManager(this) }
    private val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory(authManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (authManager.isLoggedIn()) {
            NavigationUtils.navigateToDashboard(this, authManager)
            return
        }

        setContent {
            BidTheme {
                val state by viewModel.loginState.collectAsState()

                // Handle Side Effects (Navigasi & Toast)
                LaunchedEffect(state) {
                    when (state) {
                        is LoginState.Success -> {
                            NavigationUtils.navigateToDashboard(this@LoginActivity, authManager)
                        }
                        is LoginState.Error -> {
                            Toast.makeText(this@LoginActivity, (state as LoginState.Error).message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }

                LoginScreen(
                    isLoading = state is LoginState.Loading,
                    onLogin = { email, password ->
                        viewModel.login(email, password)
                    },
                    onForgotPassword = { /* Handle */ },
                    onSignUp = {
                        startActivity(Intent(this, SignUpActivity::class.java))
                    }
                )
            }
        }
    }
}
@Composable
fun LoginScreen(
    isLoading: Boolean,
    onLogin: (String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF080808)) // Base color lebih solid
    ) {
        // Efek Cahaya Latar (Glow effect) di pojok atas
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.TopEnd)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x15FFC107), Color.Transparent)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
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
                        fontWeight = FontWeight.Light // Light memberikan kesan elegan
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(40.dp))

            // --- FORM CARD (Glassmorphism ditingkatkan) ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0x0FFFFFFF)) // Lebih transparan
                    .padding(20.dp)
            ) {
                Text(
                    text = "Email Address",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.5f)
                )
                Spacer(Modifier.height(8.dp))
                RoundedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "e.g. alex@comp.com",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "Password",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.5f)
                )
                Spacer(Modifier.height(8.dp))
                RoundedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "••••••••",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.3f)
                            )
                        }
                    }
                )

                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    modifier = Modifier
                        .align(Alignment.End) // Pindah ke kanan (standar UX)
                        .clickable { onForgotPassword() }
                        .padding(top = 12.dp)
                )
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { onLogin(email.trim(), password) },
                enabled = !isLoading && email.isNotBlank() && password.isNotBlank(), // Disable saat loading
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
                } else {
                    Text("LOGIN ACCOUNT", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                }
            }

            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                onClick = onSignUp,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Don't have an account? Sign Up", color = Color.White.copy(alpha = 0.7f))
            }

            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gpu),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun RoundedTextField(
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
            unfocusedContainerColor = Color(0x0DFFFFFF),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color(0xFFFFC107),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(name = "Login Screen", showBackground = true, device = Devices.PIXEL_4)
@Composable
fun LoginScreenPreview() {
    BidTheme {
        LoginScreen(
            isLoading = false,
            onLogin = { _, _ -> },
            onForgotPassword = {},
            onSignUp = {}
        )
    }
}