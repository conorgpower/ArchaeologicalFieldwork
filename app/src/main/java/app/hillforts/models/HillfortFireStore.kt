package app.hillforts.models

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
import app.hillforts.models.HillfortModel
import app.hillforts.models.UnifiedStore
import org.jetbrains.anko.info

class HillfortFireStore(val context: Context) : UnifiedStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()
    val users = ArrayList<UserModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

    override fun findAllHillforts(): List<HillfortModel> {
        return hillforts
    }

    override fun findAllUsers(): List<UserModel> {
        return users
    }

    override fun findHillfortById(id:Long, user: UserModel) : HillfortModel? {
        val hillforts = findAllHillfortsForUser(user)
        val foundHillfort: HillfortModel? = hillforts.find { it.id == id }
        return foundHillfort
    }

    override fun findAllHillfortsForUser(user: UserModel): List<HillfortModel> {
        return user.hillforts
    }

    override fun createHillfort(user: UserModel, hillfort: HillfortModel) {
        val key = db.child("users").child(userId).child("hillforts").push().key
        key?.let {
            hillfort.fbId = key
            hillforts.add(hillfort)
            db.child("users").child(userId).child("hillforts").child(key).setValue(hillfort)
        }
    }

    override fun createUser(user: UserModel) {
        user.id = generateRandomId()
        users.add(user)
    }

    override fun updateHillfort(user: UserModel, hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.fbId == hillfort.fbId }
        if (foundHillfort != null) {
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.image = hillfort.image
            foundHillfort.dateVisited = hillfort.dateVisited
            foundHillfort.lat = hillfort.lat
            foundHillfort.lng = hillfort.lng
            foundHillfort.zoom = hillfort.zoom
        }
        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
    }

    override fun updateUser(user: UserModel) {
        var foundUser: UserModel? = users.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.email = user.email
            foundUser.password = user.password
        }
    }

    override fun deleteHillfort(user: UserModel, hillfort: HillfortModel) {
        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).removeValue()
        hillforts.remove(hillfort)
    }

    override fun deleteUser(user: UserModel) {
        users.remove(user)
    }

    override fun clear() {
        hillforts.clear()
    }

    fun fetchHillforts(hillfortsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(hillforts) { it.getValue<HillfortModel>(HillfortModel::class.java) }
                hillfortsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        hillforts.clear()
        db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
    }
}