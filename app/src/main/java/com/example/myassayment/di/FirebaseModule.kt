package com.example.myassayment.di

import android.content.Context
import android.provider.Settings.Global.getString
import com.example.myassayment.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth()= FirebaseAuth.getInstance()


    @Provides
    @Singleton
    fun provideFirebaseFirestore()= FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideDataStorage()= FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideGoogleSingInOption()= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("584970573241-rvo163tlttiorstne154uekq6fgo7as6.apps.googleusercontent.com")
        .requestEmail()
        .build()



    @Provides
    @Singleton
    fun provideGoogleSingInClient(@ApplicationContext context: Context, gso: GoogleSignInOptions)=
        GoogleSignIn.getClient(context,gso)


}