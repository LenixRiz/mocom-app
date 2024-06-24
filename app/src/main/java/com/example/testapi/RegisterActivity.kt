package com.example.testapi

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.testapi.databinding.ActivityRegisterBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null
    private var RC_SIGN_IN = 1
    private lateinit var binding: ActivityRegisterBinding

    //clear cache
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth?.currentUser == null) {

        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        //Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleLoginBtn.setOnClickListener{
            signOutAndStartSignIn()
        }

        val signInTextView = binding.signInTv
        val spannableString = SpannableString(signInTextView.text)
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(boldSpan, 25, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // Apply bold to "Sign Up"
        signInTextView.text = spannableString

        signInTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.registerBtn.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passwordEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                // Show an alert dialog
                AlertDialog.Builder(this)
                    .setTitle("Input Required")
                    .setMessage("Please enter both email and password.")
                    .setPositiveButton("OK", null)
                    .show()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun signOutAndStartSignIn() {
        auth?.signOut()
        googleSignInClient.signOut().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

                // Start sign-in flow directly without creating a new Intent
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                    RC_SIGN_IN
                )
            } else{
                // Handle potential sign-out errors
            }
        }
    }

}