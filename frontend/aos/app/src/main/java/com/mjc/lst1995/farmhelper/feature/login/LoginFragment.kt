package com.mjc.lst1995.farmhelper.feature.login

import android.app.PendingIntent
import android.content.IntentSender
import android.os.Bundle
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
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
                if (idToken != null) {
                    firebaseAuthWithGoogle(idToken)
                    return@registerForActivityResult
                }
            } catch (_: ApiException) {
            }
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setLoginClickListener()
    }

    private fun setLoginClickListener() {
        binding.googleLoginIV.setOnClickListener {
            googleWithLogin()
        }
    }

    private fun googleWithLogin() {
        signInClient = Identity.getSignInClient(requireContext())
        signInClient
            .getSignInIntent(signInRequest.build())
            .addOnSuccessListener { pendingIntent ->
                launchSignIn(pendingIntent)
            }.addOnFailureListener {
                showMessage("현재 구글로 로그인하는 기능에 문제가 발생하였습니다.")
            }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth
                .signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 성공하였을 때 서버에 닉네임이 있는지 확인하여 동작 설정
                        findNavController().navigate(R.id.action_loginFragment_to_nickNameFragment)
                    } else {
                        showMessage("로그인에 실패하였습니다. 다시 시도해 주세요.")
                    }
                }
        } catch (e: Exception) {
            showMessage("로그인에 실패하였습니다. 다시 시도해 주세요.")
        }
    }

    private fun launchSignIn(pendingIntent: PendingIntent) {
        try {
            val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent).build()
            signInLauncher.launch(intentSenderRequest)
        } catch (e: IntentSender.SendIntentException) {
            showMessage("현재 구글로 로그인하는 기능에 문제가 발생하였습니다.")
        }
    }
}
