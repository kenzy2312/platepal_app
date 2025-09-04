package com.example.recipeapp.Database

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun checkUserExists(email: String): Boolean {
        return userDao.getUserByEmail(email) != null
    }
}