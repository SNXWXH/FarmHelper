package com.mjc.lst1995.farmhelper.login

import android.app.PendingIntent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mjc.lst1995.farmhelper.BuildConfig
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var signInClient: SignInClient

    private val signInRequest =
        GetSignInIntentRequest
            .builder()
            .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            try {
                val idToken = signInClient.getSignInCredentialFromIntent(result.data).googleIdToken
                Log.d("LoginFragment", idToken.toString())
                if (idToken != null) {
                    firebaseAuthWithGoogle(idToken)
                } else {
                    Log.d("LoginFragment", "No ID token!")
                }
            } catch (e: ApiException) {
                Log.w("LoginFragment", "Google sign in failed / signInLauncher", e)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.googleLoginIV.setOnClickListener {
            googleWithLogin()
        }
    }

    private fun googleWithLogin() {
        signInClient = Identity.getSignInClient(requireContext())
        signInClient.getSignInIntent(signInRequest.build())
            .addOnSuccessListener { pendingIntent ->
                Log.d("LoginFragment", "Received signInIntent from Google")
                launchSignIn(pendingIntent)
            }
            .addOnFailureListener { e ->
                Log.e("LoginFragment", "Google Sign-In intent failed", e)
            }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("LoginFragment", "signInWithCredential:success")
                    } else {
                        Log.w("LoginFragment", "signInWithCredential:failure", task.exception)
                    }
                }
        } catch (e: Exception) {
            Log.e("LoginFragment", "Firebase auth with Google failed", e)
        }
    }

    private fun launchSignIn(pendingIntent: PendingIntent) {
        try {
            val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent).build()
            signInLauncher.launch(intentSenderRequest)
        } catch (e: IntentSender.SendIntentException) {
            Log.e("LoginFragment", "Failed to launch sign-in", e)
        }
    }

}
