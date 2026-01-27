package com.example.bidcompauction

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bidcompauction.data.model.SignupRequest
import com.example.bidcompauction.data.viewmodel.SignupUiState
import com.example.bidcompauction.data.viewmodel.SignupViewModel
import com.example.bidcompauction.ui.theme.BidTheme

// 1. Data Class State Form
data class SignUpFormState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

class SignUpActivity : ComponentActivity() {
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BidTheme {
                val uiState by viewModel.uiState.collectAsState()

                // Handle Side Effects (Navigasi setelah sukses)
                LaunchedEffect(uiState) {
                    when (uiState) {
                        is SignupUiState.Success -> {
                            Toast.makeText(this@SignUpActivity, "Account Created!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                            finish()
                        }
                        is SignupUiState.Error -> {
                            Toast.makeText(this@SignUpActivity, (uiState as SignupUiState.Error).message, Toast.LENGTH_LONG).show()
                        }
                        else -> {}
                    }
                }

                Surface(color = MaterialTheme.colorScheme.background) {
                    SignUpScreen(
                        isLoading = uiState is SignupUiState.Loading,
                        onSignUp = { form ->
                            // Integrasi ke ViewModel
                            val request = SignupRequest(
                                name = form.name,
                                email = form.email,
                                phoneNumber = form.phone,
                                password = form.password
                            )
                            viewModel.signup(request)
                        },
                        onBackToLogin = {
                            startActivity(Intent(this, LoginActivity::class.java))
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
    isLoading: Boolean,
    onSignUp: (SignUpFormState) -> Unit,
    onBackToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var formState by remember { mutableStateOf(SignUpFormState()) }
    var passVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    val passwordsMatch = formState.password.isNotEmpty() &&
            formState.password == formState.confirmPassword

    val canSubmit = formState.name.isNotBlank() &&
            formState.email.isNotBlank() &&
            formState.phone.isNotBlank() &&
            formState.password.length >= 8 &&
            passwordsMatch

    Box(modifier = modifier.fillMaxSize().background(Color(0xFF080808))) {
        // Efek Glow Background
        Box(
            modifier = Modifier.size(400.dp).align(Alignment.TopEnd).offset(x = 120.dp, y = (-120).dp)
                .background(Brush.radialGradient(colors = listOf(Color(0x15FFC107), Color.Transparent)))
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 28.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(Modifier.height(64.dp))

            // --- HEADER ---
            Column {
                Text(text = "BID", style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Black, letterSpacing = 2.sp), color = Color.White)
                Text(text = "CompAuction", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraLight), color = MaterialTheme.colorScheme.primary)
            }

            Spacer(Modifier.height(32.dp))
            Text(text = "Create Account", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = Color.White)
            Spacer(Modifier.height(16.dp))

            // --- FORM CARD ---
            Column(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).background(Color(0x0FFFFFFF))
                    .border(1.dp, Color(0x12FFFFFF), RoundedCornerShape(24.dp)).padding(20.dp)
            ) {
                Label("Full Name")
                CompactTextField(
                    value = formState.name,
                    onValueChange = { formState = formState.copy(name = it) },
                    placeholder = "Enter your full name",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                Spacer(Modifier.height(18.dp))

                Label("Email Address")
                CompactTextField(
                    value = formState.email,
                    onValueChange = { formState = formState.copy(email = it) },
                    placeholder = "alex@example.com",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
                )

                Spacer(Modifier.height(18.dp))

                Label("Phone Number")
                CompactTextField(
                    value = formState.phone,
                    onValueChange = { formState = formState.copy(phone = it) },
                    placeholder = "08123456789",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next)
                )

                Spacer(Modifier.height(18.dp))

                Label("Password")
                CompactTextField(
                    value = formState.password,
                    onValueChange = { formState = formState.copy(password = it) },
                    placeholder = "Min. 8 characters",
                    visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = { EyeToggle(visible = passVisible, onToggle = { passVisible = !passVisible }) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
                )

                Spacer(Modifier.height(18.dp))

                Label("Confirm Password")
                CompactTextField(
                    value = formState.confirmPassword,
                    onValueChange = { formState = formState.copy(confirmPassword = it) },
                    placeholder = "Repeat password",
                    visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = { EyeToggle(visible = confirmVisible, onToggle = { confirmVisible = !confirmVisible }) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
                )

                // Error Message
                if (formState.password.isNotEmpty() && formState.confirmPassword.isNotEmpty() && !passwordsMatch) {
                    Text(text = "Passwords do not match", color = Color(0xFFFF5252), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 8.dp))
                }
            }

            Spacer(Modifier.height(32.dp))

            // --- ACTION BUTTONS ---
            Button(
                onClick = { onSignUp(formState) },
                enabled = canSubmit && !isLoading,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.Black),
                modifier = Modifier.fillMaxWidth().height(58.dp)
            ) {
                if (isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.Black)
                else Text("CREATE ACCOUNT", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
            }

            TextButton(onClick = onBackToLogin, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Already have an account? Login", color = Color.White.copy(alpha = 0.6f))
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

// --- REUSABLE COMPONENTS ---
@Composable
private fun Label(text: String) {
    Text(text = text, style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.5f), modifier = Modifier.padding(bottom = 8.dp))
}

@Composable
private fun EyeToggle(visible: Boolean, onToggle: () -> Unit) {
    IconButton(onClick = onToggle) {
        Icon(imageVector = if (visible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility, contentDescription = null, tint = Color.White.copy(alpha = 0.3f))
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
        value = value, onValueChange = onValueChange, singleLine = true,
        placeholder = { Text(placeholder, color = Color.White.copy(alpha = 0.2f)) },
        keyboardOptions = keyboardOptions, visualTransformation = visualTransformation,
        trailingIcon = trailingIcon, shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0x1AFFFFFF), unfocusedContainerColor = Color(0x0AFFFFFF),
            focusedTextColor = Color.White, unfocusedTextColor = Color.White,
            cursorColor = Color(0xFFFFC107), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

//@Preview(name = "Sign Up Screen", showBackground = true, device = Devices.PIXEL_4)
//@Composable
//fun SignUpScreenPreview() {
//    BidTheme {
//        SignUpScreen(
//            onSignUp = { _, _, _ -> },
//            onBackToLogin = {}
//        )
//    }
//}
