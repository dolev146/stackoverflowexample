package com.example.stackoverflowexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

data class Celeb(
    var Company: String,
    var FirstName: String,
    var LastName: String,
    val BirthDate: Long,
    var ImgUrl: String,
    val CelebInfo: String,
    val Category: String,
    var RightVotes: Long = 0,
    var LeftVotes: Long = 0
)

val userVotesCollectionRef = Firebase.firestore.collection("userVotes")
val userCollectionRef = Firebase.firestore.collection("users")
val celebCollectionRef = Firebase.firestore.collection("celebs")
val celebListFilterNames = mutableListOf<String>()
var celebListParam = mutableListOf<Celeb>()


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()

        }
    }
}



fun retrieveUserVotes()= CoroutineScope(Dispatchers.IO).launch {
    try {
        val auth = FirebaseAuth.getInstance()
        var userEmail = auth.currentUser?.email.toString()
        userEmail = "v@v.com" // for testing purposes only it is user that already exists in the database
        userVotesCollectionRef.whereEqualTo("userEmail" , userEmail).get().addOnSuccessListener {
                documents ->
            for (document in documents) {
                val celebName = document.getString("celebFullName")
                celebListFilterNames.add(celebName.toString())
            }
        }
        println(celebListFilterNames)
    }
    catch (e: Exception) {
        withContext(Dispatchers.Main) {
            Log.d("Error", "Error: ${e.message}")
        }
    }
}




fun retrieveCelebs() = CoroutineScope(Dispatchers.IO).launch {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser?.email.toString()
    val userPref = mutableListOf<String>()
    try {
        val docRef = userCollectionRef.document(user)
        val doc = docRef.get().await()
        for (document in doc.data?.entries!!) {
            if (document.key == "userPref") {
                userPref.addAll(document.value as List<String>)
            }
        }
        println(userPref)
    } catch (e: Exception) {
        Log.e("retrieveCelebs", "Error: ${e.message}")
    }


        try {
            val querySnapshot = celebCollectionRef.get().await()
            for (document in querySnapshot.documents) {
                val celebDocument = document
                val celebCompany = celebDocument.data?.get("company").toString()
                val celebFirstName = celebDocument.data?.get("firstName").toString()
                val celebLastName = celebDocument.data?.get("lastName").toString()
                val fullName = "$celebFirstName $celebLastName"
                if(celebListFilterNames.contains(fullName)){
                    continue
                }
                val celebBirthDate = celebDocument.data?.get("birthDate").toString().toLong()
                val celebImgUrl = celebDocument.data?.get("imgUrl").toString()
                val celebInfo = celebDocument.data?.get("celebInfo").toString()
                val celebCategory = celebDocument.data?.get("category").toString()
                if (!userPref.contains(celebCategory)) {
                    continue
                }
                val celebRightVotes = celebDocument.data?.get("rightVotes").toString().toLong()
                val celebLeftVotes = celebDocument.data?.get("leftVotes").toString().toLong()
                val celebObject = Celeb(
                    Company = celebCompany,
                    FirstName = celebFirstName,
                    LastName = celebLastName,
                    BirthDate = celebBirthDate,
                    ImgUrl = celebImgUrl,
                    CelebInfo = celebInfo,
                    Category = celebCategory,
                    RightVotes = celebRightVotes,
                    LeftVotes = celebLeftVotes
                )
                celebListParam.add(celebObject)
            }
            celebListParam.shuffle()


        } catch (e: Exception) {
            Log.d("error celeb", e.toString())
        }

}