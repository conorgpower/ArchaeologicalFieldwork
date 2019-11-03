package app.hillforts.models

import android.content.Context
import android.content.Intent
import app.hillforts.activities.HillfortListActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import app.hillforts.helpers.*
import app.hillforts.main.MainApp
import java.util.*
import kotlin.collections.ArrayList

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

    override fun findAllUsers(): List<UserModel> {
        return users
    }

    override fun findAllHillforts(): List<HillfortModel> {
        return hillforts
    }

    override fun findAllHillfortsForUser(user: UserModel): List<HillfortModel> {
        return user.hillforts
    }

    override fun createUser(user: UserModel) {
        user.id = generateRandomId()
        users.add(user)
        serialize()
    }

    override fun createHillfort(user: UserModel, hillfort: HillfortModel) {
        hillfort.id = generateRandomId()
        user.hillforts. add(hillfort)
        serialize()
    }

    override fun updateUser(user: UserModel) {
        var foundUser: UserModel? = users.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.email = user.email
            foundUser.password = user.password
        }
        serialize()
    }

    override fun updateHillfort(user: UserModel, hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = user.hillforts.find { p -> p.id == hillfort.id }
        if (foundHillfort != null) {
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.image = hillfort.image
            foundHillfort.lat = hillfort.lat
            foundHillfort.lng = hillfort.lng
            foundHillfort.zoom = hillfort.zoom
        }
        serialize()
    }

    override fun deleteUser(user: UserModel) {
        users.remove(user)
        serialize()
    }

    override fun deleteHillfort(user: UserModel, hillfort: HillfortModel) {
        user.hillforts.remove(hillfort)
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