package com.priyesh.newsappmvvm.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecuredPreferenceDataStore @Inject constructor(@ApplicationContext val context: Context) {

    val Context.secureDataStore by preferencesDataStore(DataStoreConstants.PREF_DATA_STORE_NAME)
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    private suspend fun <T> saveData(key: String, value: T) {
        val encryptedValue = AESGCMKeyStoreHelper.encrypt(value.toString())
        context.secureDataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = encryptedValue
        }
    }

    private inline fun <reified T> getData(key: String, defaultValue: T): Flow<T> {
        return context.secureDataStore.data.map { preferences ->
            val encryptedValue = preferences[stringPreferencesKey(key)] ?: return@map defaultValue
            val decryptedValue = AESGCMKeyStoreHelper.decrypt(encryptedValue)
            convertToType(decryptedValue, defaultValue)
        }.flowOn(Dispatchers.IO)
    }

    private inline fun <reified T> convertToType(value: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> value as T
            is Int -> value.toIntOrNull() as T ?: defaultValue
            is Boolean -> value.toBoolean() as T
            is Float -> value.toFloatOrNull() as T ?: defaultValue
            is Long -> value.toLongOrNull() as T ?: defaultValue
            is Double -> value.toLongOrNull() as T ?: defaultValue
            else -> throw IllegalArgumentException("Unsupported data type")
        }
    }

    suspend fun remove(key: String) {
        context.secureDataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
        }
    }

    suspend fun removeAll() {
        context.secureDataStore.edit { it.clear() }
    }

    fun <T> saveDataInDataStore(key: String, value: T) {
        coroutineScope.launch {
            saveData(key, value)
        }
    }

    suspend fun getStringFromDataStore(key: String, defaultValue: String = ""): String {
        return getData(key, defaultValue).first()
    }

    suspend fun getBooleanFromDataStore(key: String, defaultValue: Boolean = false): Boolean {
        return getData(key, defaultValue).first()
    }

    suspend fun getIntFromDataStore(key: String, defaultValue: Int? = -1): Int {
        return getData(key, defaultValue).first() ?: -1
    }

    suspend fun getLongFromDataStore(key: String, defaultValue: Long? = -1): Long {
        return getData(key, defaultValue).first() ?: -1
    }

    suspend fun getFloatFromDataStore(key: String, defaultValue: Float? = -1f): Float {
        return getData(key, defaultValue).first() ?: -1f
    }

    suspend fun getStringFromDataStore(key: String, defaultValue: Double? = -1.0): Double {
        return getData(key, defaultValue).first() ?: -1.0
    }

    inline fun <reified T> saveObjectInDataStore(key: String, value: T) {
        coroutineScope.launch {
            val jsonData = Json.encodeToString(value)
            val encryptedData = AESGCMKeyStoreHelper.encrypt(jsonData)
            context.secureDataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = encryptedData
            }
        }
    }

    inline fun <reified T> getObjectFromDataStore(key: String, defaultValue: T): Flow<T> {
        return context.secureDataStore.data.map { preferences ->
            val encryptedData = preferences[stringPreferencesKey(key)] ?: return@map defaultValue
            try {
                val decryptedData = AESGCMKeyStoreHelper.decrypt(encryptedData)
                Json.decodeFromString<T>(decryptedData)
            } catch (e: Exception) {
                defaultValue
            }
        }
    }
}