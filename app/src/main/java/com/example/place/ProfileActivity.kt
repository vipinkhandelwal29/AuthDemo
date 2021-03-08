package com.example.place

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bean.PlaceBean
import bean.PlaceSerializedBean
import com.example.place.databinding.ActivityLoginBinding
import com.example.place.databinding.ActivityProfileBinding
import com.facebook.AccessToken
import com.facebook.Profile
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.gson.Gson


class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    private lateinit var globalPref: SharedPreferences
    private lateinit var textView: TextView
    private lateinit var sharedLogoutBtn: Button


    override fun getLayoutId() = R.layout.activity_profile

    override fun initControl() {

        setSupportActionBar(binding.iToolbar.toolbar)


        textView = findViewById<View>(R.id.tvProfileName) as TextView
        sharedLogoutBtn = findViewById<View>(R.id.btnLogout) as Button

        // Global shared preference to store login type (google or fb)
        globalPref = PreferenceManager.getDefaultSharedPreferences(this)
        globalPref.getString(KEY_LOGIN_TYPE, NONE_LOGIN)?.let {
            showLoginTypeInfo(it)


                //binding.recyclerview.adapter = adapter


            val data = readFromAsset()
            val jsonPrase = fromJson(data)
            Log.d("==>jsonPrase", "$jsonPrase")
            val gson = toGson(data)
            Log.d("==>gson", "$gson")
        }
    }

    private fun toGson(data: String): String {
        return Gson().toJson(data)
    }


    private fun fromJson(data: String): PlaceSerializedBean {
        return Gson().fromJson(data, PlaceSerializedBean::class.java)
    }

    private fun readFromAsset(): String {
        val filename = "places.json"
        val bufferReader = application.assets.open(filename).bufferedReader()
        val data = bufferReader.use { it.readText() }
        Log.d("readFromAsset: ", data)
        return data

    }

    private fun showLoginTypeInfo(loginType: String) {
        when (loginType) {
            GOOGLE_LOGIN -> {
                sharedLogoutBtn.text = resources.getString(R.string.logout_google)
                sharedLogoutBtn.tag = R.string.logout_google
                showGoogleProfile()
            }
            FB_LOGIN -> {
                sharedLogoutBtn.text = resources.getString(R.string.logout_fb)
                sharedLogoutBtn.tag = R.string.logout_fb
                showFbProfile()
            }
            NONE_LOGIN -> {
                goLoginScreen()
            }
        }
    }

    private fun showGoogleProfile() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val isLoggedIn = account != null && !account.isExpired
        if (!isLoggedIn) {
            goLoginScreen()
        } else {
            fetchGoogleProfileInfo(account)
        }
    }

    private fun fetchGoogleProfileInfo(account: GoogleSignInAccount?) {
        account?.let {
            val username = it.displayName
            val email = it.email

            supportActionBar?.title = username
            textView.append(", $username")
        }
    }

    private fun showFbProfile() {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        if (!isLoggedIn) {
            goLoginScreen();
        } else {
            val profile = Profile.getCurrentProfile()
            if (profile != null) {
                fetchFbProfileInfo(profile)
            } else {
                Profile.fetchProfileForCurrentAccessToken()
                recreate()
            }
        }
    }

    private fun fetchFbProfileInfo(profile: Profile) {
        val username = profile.name

        supportActionBar?.title = username
        textView.append(", $username")
    }

    private fun goLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun sharedLogout(view: View?) {
        view?.let {
            when (view.tag) {
                R.string.logout_fb -> {
                    LoginManager.getInstance().logOut()
                    goLoginScreen()
                }
                R.string.logout_google -> {
                    (application as MyApp).mGoogleSignInClient.signOut()
                        .addOnCompleteListener(
                            this
                        ) {
                            if (it.isSuccessful) {
                                goLoginScreen()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Can't logout from google",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                }
                else -> {
                }
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