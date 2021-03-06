package app.hillforts.models

interface UnifiedStore {
    fun findAllHillforts(): List<HillfortModel>
    fun findAllHillfortsForUser(user: UserModel): List<HillfortModel>
    fun findHillfortById(id:Long, user: UserModel) : HillfortModel?
    fun createHillfort(user: UserModel, hillfort: HillfortModel)
    fun updateHillfort(user: UserModel, hillfort: HillfortModel)
    fun deleteHillfort(user: UserModel, hillfort: HillfortModel)

    fun clear()

    fun findAllUsers(): List<UserModel>
    fun createUser(user: UserModel)
    fun updateUser(user: UserModel)
    fun deleteUser(user: UserModel)
}