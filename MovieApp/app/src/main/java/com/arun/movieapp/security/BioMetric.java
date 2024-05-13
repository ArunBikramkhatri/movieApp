package com.arun.movieapp.security;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

public class BioMetric {

    private FragmentActivity activity ;

    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    private BiometricManager biometricManager;
    public BioMetric(FragmentActivity activity) {
        this.activity = activity;
        biometricManager = BiometricManager.from(activity);
    }

    public boolean isBiometricAvailable(){
        if(biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS){
            return true;
        }else {
            return false;
        }
    }

    public void setBioMetric(){
        if (isBiometricAvailable()){
            executor = ContextCompat.getMainExecutor(activity);
            biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(activity.getApplicationContext(),
                                    "Authentication error: " + errString, Toast.LENGTH_SHORT)
                            .show();
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }
            });
        }
    }
}
