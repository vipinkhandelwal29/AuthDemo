package com.example.place

import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.place.databinding.ActivityLoginBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import java.util.*


const val GOOGLE_LOGIN: String = "loginType.GOOGLE_LOGIN"
const val FB_LOGIN: String = "loginType.FB_LOGIN"
const val NONE_LOGIN: String = "loginType.NONE_LOGIN"
const val KEY_LOGIN_TYPE: String = "key.KEY_LOGIN_TYPE"
// {KEY_LOGIN_TYPE: GOOGLE_LOGIN}

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val RC_SIGN_IN: Int = 20000
    private val TAG: String = LoginActivity::class.simpleName.toString()
    private var callbackManager: CallbackManager? = null
    private lateinit var fbBtn: LoginButton

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var googleBtn: SignInButton
    private lateinit var globalPrefEditor: SharedPreferences.Editor

    override fun getLayoutId() = R.layout.activity_login

    override fun initControl() {

        setSupportActionBar(binding.iToolbar.toolbar)
        title = "Sign In"
        globalPrefEditor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        setupGoogleLogin()
        setupFbLogin()
    }

    private fun setupGoogleLogin() {
        // Set the dimensions of the sign-in button.
        googleBtn = findViewById<SignInButton>(R.id.btnGoogle)
        googleBtn.setSize(SignInButton.SIZE_STANDARD)

        /*// Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);*/
        mGoogleSignInClient = (application as MyApp).mGoogleSignInClient

        googleBtn.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun setupFbLogin() {
        val EMAIL = "email"
        callbackManager = CallbackManager.Factory.create();

        fbBtn = findViewById<View>(R.id.btnFacebook) as LoginButton
        fbBtn.setReadPermissions(Arrays.asList(EMAIL))


        // If you are using in a fragment, call loginButton.setFragment(this);
        // Callback registration
        fbBtn.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                // App code
                Toast.makeText(applicationContext, "Login success", Toast.LENGTH_SHORT).show();
                goMainScreen(FB_LOGIN)
            }

            override fun onCancel() {
                // App code
                Log.i(TAG, "onCancel: ")
                Toast.makeText(applicationContext, "Login cancelled", Toast.LENGTH_SHORT).show();
            }

            override fun onError(exception: FacebookException) {
                // App code
                Log.i(TAG, "onError: ")
                Toast.makeText(applicationContext, "Login error", Toast.LENGTH_SHORT).show();
            }
        })

    }

    private fun goMainScreen(loginType: String) {
        globalPrefEditor.putString(KEY_LOGIN_TYPE, loginType)
        globalPrefEditor.apply()

        val intent = Intent(this, ProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(KEY_LOGIN_TYPE, loginType)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        when (requestCode) {
            RC_SIGN_IN -> {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                goMainScreen(GOOGLE_LOGIN)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
            /*  Toast.makeText(this, "ActionClicked", Toast.LENGTH_LONG).show()
              Log.d("btn", "menuBtnAdd  ")*/
        } else {

            return super.onOptionsItemSelected(item)
        }
    }

}