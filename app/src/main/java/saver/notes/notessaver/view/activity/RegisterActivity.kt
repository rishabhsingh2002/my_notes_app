package saver.notes.notessaver.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import saver.notes.notessaver.R
import saver.notes.notessaver.databinding.ActivityRegisterBinding
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toolbar setup
        toolbarSetup()

        binding.btnRLogin.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }


        val sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.btnRegister.setOnClickListener {

            val name = binding.etName.text.toString().trim { it <= ' ' }
            val mobile = binding.etMobile.text.toString().trim { it <= ' ' }
            val email = binding.etEmail.text.toString().trim { it <= ' ' }
            val password = binding.etPassword.text.toString().trim { it <= ' ' }

            if (validateForm(name, mobile, email, password)) {
                editor.apply {
                    putString("email", email)
                    putString("password", password)
                    putString("name", name)
                    putString("mobile", mobile)
                    apply()
                }
                Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()

                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }


        }

    }

    private fun validateForm(
        name: String,
        mobile: String,
        email: String,
        password: String
    ): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter name.")
                false
            }
            TextUtils.isEmpty(mobile) || !isValidPhoneNumber(mobile) -> {
                showErrorSnackBar("Please enter valid mobile number.")
                false
            }
            TextUtils.isEmpty(email) || !isValidEmail(email) -> {
                showErrorSnackBar("Please enter valid email.")
                false
            }
            TextUtils.isEmpty(password) || !isValidPasswordFormat(password) || password == name -> {
                showErrorSnackBar("Please enter valid password.")
                false
            }
            else -> {
                true
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPhoneNumber(target: String?): Boolean {
        return if (target == null || target.length < 10 || target.length > 10) {
            false
        } else {
            Patterns.PHONE.matcher(target).matches()
        }
    }

    private fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile(
            "^" +
                    "(?=.*?[0-9].*?[0-9])" +        //at least 2 digit
                    "(?=.*[a-z])" +
                    "[^\\W_]" +                 //at least 1 lower case letter
                    "(?=.*[A-Z])" +
                    "(?=.*[A-Z].*[A-Z])" +         //at least 2 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,15}" +               //at least 8 characters
                    "$"
        )
        return passwordREGEX.matcher(password).matches()
    }


    private fun showErrorSnackBar(message: String) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                this@RegisterActivity,
                R.color.red
            )
        )
        val params = snackBarView.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackBarView.layoutParams = params
        snackBar.show()
    }

    private fun toolbarSetup() {
        binding.toolbarRegister.setTitleTextColor(Color.WHITE)
        binding.toolbarRegister.title = "Register for Scouto"
        setSupportActionBar(binding.toolbarRegister)
    }
}