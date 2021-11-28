package com.example.myassayment.data.remote

import com.example.myassayment.models.Doctor
import com.example.myassayment.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class FirebaseSource @Inject constructor(
    val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dataStorage: FirebaseStorage,
    private val googleSingInClient: GoogleSignInClient
) {

    // Authentication
    val user=auth.currentUser

    fun createUsre(email:String, password:String)=
        auth.createUserWithEmailAndPassword(email,password)

    fun login(email:String, password:String)=
        auth.signInWithEmailAndPassword(email,password)

    fun loginGoogle()=googleSingInClient.signInIntent

    fun firebaseAuthWithGoogle(idToken: String): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential)
    }

    fun logout()=auth.signOut()

    fun resetPassword(email: String)=auth.sendPasswordResetEmail(email)
    //FireStore

    fun getDoctors()=firestore.collection(Constants.COLLLECTION_DOCTORS)

    fun getAllDoctorsTimeSchedula(doctorId: String)=firestore.collection(Constants.TIME_SCHEDULA_COLLECTION)
        .document(doctorId).collection("date")


    //DataStorage
}