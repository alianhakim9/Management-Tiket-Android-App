package id.alian.managementtiket.domain.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import id.alian.managementtiket.commons.dataStore
import id.alian.managementtiket.data.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import java.lang.Exception
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val context: Context
) : DataStoreRepository {
    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun removeString(key: String) {
        context.dataStore.edit {
            val preferencesKey = stringPreferencesKey(key)
            it.remove(preferencesKey)
        }
    }
}