package com.example.myassayment.data.remote

import com.example.myassayment.models.Appointeiment
import com.example.myassayment.models.Client
import com.example.myassayment.models.Doctor
import com.example.myassayment.utils.Constants
import com.example.myassayment.utils.Constants.NAME_FIELD_NAMEAR
import com.example.myassayment.utils.Constants.NAME_FIELD_NAMEEN
import com.example.myassayment.utils.Constants.NAME_FIELD_SPETIALTY
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

    // ----------------------------------------Authentication-------------------------------------
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
    //-----------------------------------------------------------------------------------------


    //----------------------------------------FireStore----------------------------------------

    fun saveUser(client: Client)=firestore.collection(Constants.COLLLECTION_USERS)
        .document().set(client)

    fun getDoctors()=firestore.collection(Constants.COLLLECTION_DOCTORS)

    fun getDoctorById(doctorId:String)=
        firestore.collection(Constants.COLLLECTION_DOCTORS)
        .document(doctorId).get()

    fun getAllDoctorsTimeSchedula(doctorId: String)=
        firestore.collection(Constants.TIME_SCHEDULA_COLLECTION)
        .document(doctorId).collection("date")

    fun uploadAppointementToDoctor(appointeiment: Appointeiment,doctorId: String)=
        firestore.collection(Constants.APPOINTEMENT_COLLECTION)
            .document(doctorId).set(appointeiment)

    fun uploadMyAppointement(appointeiment: Appointeiment,doctorId: String)=
        firestore.collection(Constants.USER_APPOINTEMENT_COLLECTION)
            .document(doctorId).set(appointeiment)

    fun getSpeiality()=
        firestore.collection(Constants.COLLLECTION_SPEIALITY)

    fun getDoctorsWithSpetilst(spetilist:String)=
        firestore.collection(Constants.COLLLECTION_DOCTORS)
            .whereEqualTo(NAME_FIELD_SPETIALTY,spetilist)

    fun getDoctorSearchByName(name:String)=
        firestore.collection(Constants.COLLLECTION_DOCTORS)
            .orderBy(NAME_FIELD_NAMEEN)
            .startAt(name.trim())
            .endAt(name.trim()+"\uF8FF")

    //-------------------------------------------------------------------------------------------


    //----------------------------------------DataStorage----------------------------------------


    //-------------------------------------------------------------------------------------------
}