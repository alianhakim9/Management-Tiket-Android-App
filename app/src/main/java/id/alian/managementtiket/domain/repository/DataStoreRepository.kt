package id.alian.managementtiket.domain.repository

interface DataStoreRepository {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun removeString(key: String)
}