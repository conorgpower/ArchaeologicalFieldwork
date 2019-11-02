package app.hillforts.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import app.hillforts.helpers.*
import java.util.*

val JSON_FILE = "unified.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class UnifiedJSONStore : UnifiedStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()
    var hillforts = mutableListOf<HillfortModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAllUsers(): MutableList<UserModel> {
        return users
    }

//    override fun findAllHillforts(userId): MutableList<HillfortModel> {
//        return hillforts
//    }

    override fun createUser(user: UserModel) {
        user.id = generateRandomId()
        users.add(user)
        serialize()
    }

//    override fun createHillfort(hillfort: HillfortModel) {
//        hillfort.id = generateRandomId()
//        hillforts.add(hillfort)
//        serialize()
//    }


    override fun updateUser(user: UserModel) {
        var foundUser: UserModel? = users.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.email = user.email
            foundUser.password = user.password
        }
        serialize()
    }

    override fun deleteUser(user: UserModel) {
        users.remove(user)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(users, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        users = Gson().fromJson(jsonString, listType)
    }
}