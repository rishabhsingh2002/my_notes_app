package saver.notes.notessaver.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import saver.notes.notessaver.R
import saver.notes.notessaver.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar setup
        toolbarSetup()

        val sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE)




        binding.btnLogin.setOnClickListener {

            val email = sharedPreferences.getString("email", null)
            val password = sharedPreferences.getString("password", null)


            val etEmail = binding.etLoginEmail.text.toString()
            val etPassword = binding.etLoginPassword.text.toString()

            if (etEmail == email && etPassword == password) {
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            } else {
                showErrorSnackBar("Email or Password is wrong")
            }
        }

        binding.btnLRegister.setOnClickListener {
            Intent(this, RegisterActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }


    }

    private fun showErrorSnackBar(message: String) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                this@LoginActivity,
                R.color.red
            )
        )
        val params = snackBarView.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackBarView.layoutParams = params
        snackBar.show()
    }

    private fun toolbarSetup() {
        binding.toolbarLogin.setTitleTextColor(Color.WHITE)
        binding.toolbarLogin.title = "LogIn for Scouto"
        setSupportActionBar(binding.toolbarLogin)
    }
}