import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var password by remember { mutableStateOf("") }
    var editable by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(user) {
        // Fetch user information from Firestore
        user?.let {
            db.collection("users").document(it.uid).get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    firstName = document.getString("firstName") ?: ""
                    lastName = document.getString("lastName") ?: ""
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = SnackbarHostState().also { snackHostState ->
                    if (showSnackbar) {
                        LaunchedEffect(snackbarMessage) {
                            snackHostState.showSnackbar(snackbarMessage)
                            showSnackbar = false
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Profile",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            EditableTextField("First Name", firstName, editable) { firstName = it }
            Spacer(modifier = Modifier.height(8.dp))
            EditableTextField("Last Name", lastName, editable) { lastName = it }
            Spacer(modifier = Modifier.height(8.dp))
            EditableTextField("Email", email, editable) { email = it }
            Spacer(modifier = Modifier.height(8.dp))
            EditableTextField("Password", password, editable, true) { password = it }
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            // Save user information to Firestore
                            val userInfo = hashMapOf(
                                "firstName" to firstName,
                                "lastName" to lastName
                            )
                            db.collection("users").document(user!!.uid)
                                .set(userInfo)
                                .addOnSuccessListener {
                                    user.updateEmail(email).addOnCompleteListener { task ->
                                        if (task.isSuccessful && password.isNotEmpty()) {
                                            user.updatePassword(password).addOnCompleteListener { passwordTask ->
                                                if (passwordTask.isSuccessful) {
                                                    snackbarMessage = "Information updated successfully"
                                                    showSnackbar = true
                                                } else {
                                                    snackbarMessage = "Error updating password"
                                                    showSnackbar = true
                                                }
                                            }
                                        } else if (task.isSuccessful) {
                                            snackbarMessage = "Information updated successfully"
                                            showSnackbar = true
                                        } else {
                                            snackbarMessage = "Error updating email"
                                            showSnackbar = true
                                        }
                                    }
                                }
                        } catch (e: Exception) {
                            snackbarMessage = "Error updating information"
                            showSnackbar = true
                        } finally {
                            editable = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    auth.signOut()
                    navController.navigate("login_screen") {
                        popUpTo("profile_screen") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
        }
    }
}

@Composable
fun EditableTextField(label: String, value: String, editable: Boolean, isPassword: Boolean = false, onValueChange: (String) -> Unit) {
    var isEditing by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            readOnly = !isEditing,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = if (isPassword) ImeAction.Done else ImeAction.Next
            )
        )

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit $label",
            modifier = Modifier
                .clickable { isEditing = true }
                .padding(8.dp)
        )
    }
}
