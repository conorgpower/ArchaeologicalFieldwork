package app.hillforts.models

interface UnifiedStore {
//    fun findAllHillforts(): List<HillfortModel>
//    fun createHillforts(hillfort: HillfortModel)
//    fun updateHillforts(hillfort: HillfortModel)
//    fun deleteHillforts(hillfort: HillfortModel)

    fun findAllUsers(): List<UserModel>
    fun createUser(user: UserModel)
    fun updateUser(user: UserModel)
    fun deleteUser(user: UserModel)
}