package com.example.place

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MyApp : Application() {
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        super.onCreate()
    }
}