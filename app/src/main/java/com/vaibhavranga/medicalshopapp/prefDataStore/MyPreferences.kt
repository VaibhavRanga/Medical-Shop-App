package com.vaibhavranga.medicalshopapp.prefDataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vaibhavranga.medicalshopapp.model.SavedUserDataModel
import com.vaibhavranga.medicalshopapp.model.User
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class MyPreferences(private val context: Context) {

    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_ACCOUNT_CREATION_DATE = stringPreferencesKey("user_account_creation_date")
        private val USER_ADDRESS = stringPreferencesKey("user_address")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_IS_APPROVED = intPreferencesKey("user_is_approved")
        private val USER_IS_BLOCKED = intPreferencesKey("user_is_blocked")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_PHONE_NUMBER = stringPreferencesKey("user_phone_number")
        private val USER_PINCODE = stringPreferencesKey("user_pincode")
    }

    suspend fun saveUserDetailsToPrefs(user: User) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = user.userId!!
            prefs[USER_ACCOUNT_CREATION_DATE] = user.accountCreationDate!!
            prefs[USER_ADDRESS] = user.address!!
            prefs[USER_EMAIL] = user.email!!
            prefs[USER_IS_APPROVED] = user.isApproved!!
            prefs[USER_IS_BLOCKED] = user.isBlocked!!
            prefs[USER_NAME] = user.name!!
            prefs[USER_PHONE_NUMBER] = user.phoneNumber!!
            prefs[USER_PINCODE] = user.pincode!!
        }
    }

    val getUserFromPrefs = context.dataStore.data.map { prefs ->
        SavedUserDataModel(
            accountCreationDate = prefs[USER_ACCOUNT_CREATION_DATE],
            address = prefs[USER_ADDRESS],
            email = prefs[USER_EMAIL],
            isApproved = prefs[USER_IS_APPROVED],
            isBlocked = prefs[USER_IS_BLOCKED],
            name = prefs[USER_NAME],
            phoneNumber = prefs[USER_PHONE_NUMBER],
            pincode = prefs[USER_PINCODE],
            userId = prefs[USER_ID_KEY]
        )
    }

    suspend fun clearUserPrefDetails() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}