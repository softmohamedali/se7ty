package com.example.myassayment.data.remote

import com.example.myassayment.models.*
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
import javax.inject.Singleton

@Singleton
class FirebaseSource @Inject constructor(
    val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dataStorage: FirebaseStorage,
    private val googleSingInClient: GoogleSignInClient
) {

    // ----------------------------------------Authentication-------------------------------------
    fun user()=auth.currentUser

    fun createUsre(email:String, password:String)=
        auth.createUserWithEmailAndPassword(email,password)

    fun login(email:String, password:String)=
        auth.signInWithEmailAndPassword(email,password)

    fun loginGoogle()=googleSingInClient.signInIntent

    fun firebaseSinginWithGoogle(idToken: String): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential)
    }

    fun logout()=auth.signOut()

    fun resetPassword(email: String)=auth.sendPasswordResetEmail(email)
    //-----------------------------------------------------------------------------------------


    //----------------------------------------FireStore----------------------------------------

    fun saveUser(client: Client):Task<Void>{
        val ref=firestore.collection(Constants.COLLLECTION_USERS)
            .document(auth.currentUser!!.uid)
        var mclient=client
        mclient.clientId=ref.id
        return ref.set(mclient)
    }


    fun getDoctors()=firestore.collection(Constants.COLLLECTION_DOCTORS)

    fun getDoctorById(doctorId:String)=
        firestore.collection(Constants.COLLLECTION_DOCTORS)
        .document(doctorId).get()

    fun getAllDoctorsTimeSchedula(doctorId: String)=
        firestore.collection(Constants.TIME_SCHEDULA_COLLECTION)
        .document(doctorId).collection("date")


    fun uploadMyAppointement(appointeiment: Appointeiment):Task<Void>{
        val ref=firestore.collection(Constants.USER_APPOINTEMENT_COLLECTION).document()
        val appo=appointeiment
        appo.appointeimentId=ref.id
            return ref.set(appo)
    }


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

    fun upLoadMassage()=firestore.collection("massages")
        .document()

    fun getUserInfo()=firestore.collection(Constants.COLLLECTION_USERS)
        .document(auth.currentUser!!.uid).get()

    fun getUserApponitement()=firestore.collection(Constants.USER_APPOINTEMENT_COLLECTION)
        .whereEqualTo("clientId",auth.currentUser?.uid)

    fun delteAppointement(appoId:String)=firestore.collection(Constants.USER_APPOINTEMENT_COLLECTION)
        .document(appoId).delete()

    fun addTimeSchedula(time: TimeSchedule,doctorId: String):Task<Void>{
        val ref=firestore.collection(Constants.TIME_SCHEDULA_COLLECTION)
            .document(doctorId).collection("date").document()
        val mytime=time
        mytime.id=ref.id
        return ref.set(mytime)
    }

    fun deleteTimeSchedula(timeSchId:String,doctorId: String)=firestore.collection(Constants.TIME_SCHEDULA_COLLECTION)
        .document(doctorId).collection("date").document(timeSchId).delete()

    fun getLapTests()=firestore.collection(Constants.COLLLECTION_LAPTESTS)

    fun getSearchLapTests(name:String)=firestore.collection(Constants.COLLLECTION_LAPTESTS)
        .orderBy("name")
        .startAt(name.trim())
        .endAt(name.trim()+"\uf8ff")

    fun bookLapTests(lapTestsBook:BookTest)=firestore.collection(Constants.COLLECTION_BOOKINGlAPTESTS)

    fun getAllBranches(area:String,city:String)=firestore.collection(Constants.COLLLECTION_BRANCHES)
    //-------------------------------------------------------------------------------------------


    //----------------------------------------DataStorage----------------------------------------


    //-------------------------------------------------------------------------------------------
}