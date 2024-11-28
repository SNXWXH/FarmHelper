package com.mjc.lst1995.farmhelper.feature.login

import android.app.PendingIntent
import android.content.IntentSender
import android.os.Bundle
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.mjc.lst1995.farmhelper.BuildConfig
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by viewModels()

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
                    loginViewModel.firebaseAuthWithGoogle(idToken)
                    return@registerForActivityResult
                }
            } catch (_: ApiException) {
            }
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view,savedInstanceState)
        setLoginClickListener()
        observeLoginState()
        observeIsJoined()
    }

    private fun setLoginClickListener() {
        binding.googleLoginIV.setOnClickListener {
            googleWithLogin()
        }
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState.collect { isLogin ->
                    if (isLogin) loginViewModel.userIsJoined()
                }
            }
        }
    }

    private fun observeIsJoined() {
        loginViewModel.isJoined.observe(viewLifecycleOwner) { isJoined ->
            isJoined?.let {
                if (isJoined == true) {
                    navigateTo(R.layout.fragment_home)
                } else {
                    navigateTo(R.layout.fragment_nick_name)
                }
            }
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


    private fun launchSignIn(pendingIntent: PendingIntent) {
        try {
            val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent).build()
            signInLauncher.launch(intentSenderRequest)
        } catch (e: IntentSender.SendIntentException) {
            showMessage("현재 구글로 로그인하는 기능에 문제가 발생하였습니다.")
        }
    }

    private fun navigateTo(
        @LayoutRes
        layoutId: Int,
    ) {
        when (layoutId) {
            R.layout.fragment_home ->
                findNavController().navigate(
                    R.id.action_loginFragment_to_homeFragment,
                    null,
                    navOptions()
                )

            R.layout.fragment_nick_name ->
                findNavController().navigate(R.id.action_loginFragment_to_nickNameFragment)
        }
    }

    private fun navOptions(): NavOptions {
        val navController = findNavController()
        return NavOptions
            .Builder()
            .setPopUpTo(navController.graph.startDestinationId,inclusive = true) // 시작 지점까지 모두 팝업
            .build()
    }
}
