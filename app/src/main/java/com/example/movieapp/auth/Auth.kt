package com.example.movieapp.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext

//Navigation
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.activity.compose.setContent
import androidx.compose.ui.text.input.PasswordVisualTransformation

// Material3 API
import androidx.compose.material3.*
import androidx.compose.runtime.*

// Composable imports
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Firebase & Firestore
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview




@OptIn(ExperimentalMaterial3Api::class)
class Auth: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            val navController= rememberNavController()
            RegisterScreen(navController = navController)
            LoginScreen(navController=navController)

        }
    }
}

@Composable
fun RegisterScreen(navController: NavHostController){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var  lastName by remember { mutableStateOf("") }

    //Firebase and Firestore declaration
    val auth= FirebaseAuth.getInstance()
    val firestore= FirebaseFirestore.getInstance()


    val context= LocalContext.current


    Column(
        modifier=Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment= Alignment.CenterHorizontally
    ){Text(
        text="Register",
        fontSize= 24.sp,
        color=MaterialTheme.colorScheme.primary
    )

        Spacer(modifier= Modifier.height(16.dp))

        TextField(
            value = firstName,
            onValueChange = {firstName=it},
            label = {Text("First Name")},
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        Spacer(modifier= Modifier.height(8.dp))

        TextField(
            value = lastName,
            onValueChange = {lastName=it},
            label = {Text("Last Name")},
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        Spacer(modifier= Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = {email=it},
            label = {Text("Email")},
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        Spacer(modifier= Modifier.height(8.dp))


        TextField(
            value = password,
            onValueChange = {password=it},
            label = {Text("Password")},
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        Spacer(modifier= Modifier.height(8.dp))


        Button(
            onClick = {
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            val user= auth.currentUser
                            val userInfo= mapOf(
                                "firstName" to firstName,
                                "lastName" to lastName,
                                "email" to email
                            )
                            firestore.collection("users").document(user!!.uid)
                                .set(userInfo)
                                .addOnSuccessListener {
                                    Toast.makeText(context,"Registration successful",Toast.LENGTH_SHORT).show()
                                    // Navigation code to login page
                                    navController.navigate("login_screen")
                                }
                                .addOnFailureListener{e->
                                    Toast.makeText(context, "Firestore Error: $e", Toast.LENGTH_SHORT).show()
                                }
                        }else{
                            Toast.makeText(context,"Authentication failed.",Toast.LENGTH_SHORT).show()

                        }

                    }
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate("login_screen")
        }){
            Text("Already have an account? Login")
        }




    }
}


@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    //Firebase and Firestore declaration
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(8.dp))


        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(8.dp))

       Button(
           onClick = {
               auth.signInWithEmailAndPassword(email,password)
                   .addOnCompleteListener{ task ->
                      if(task.isSuccessful) {
                          Toast.makeText(context, "Login successful",Toast.LENGTH_SHORT).show()

                      }else{
                          Toast.makeText(context, "Login failed:${task.exception?.message}" ,Toast.LENGTH_SHORT).show()
                      }

                       }



           },
           modifier= Modifier.fillMaxWidth()
       ){
           Text("Login")
       }
        Spacer(modifier= Modifier.height(8.dp))
        TextButton(onClick = {
            //Navigate to the Register screen
            navController.navigate("register_screen")
        }){
            Text("Don't have an account? Register")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {

    RegisterScreen(navController = rememberNavController())
}







