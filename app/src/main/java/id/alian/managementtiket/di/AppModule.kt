package id.alian.managementtiket.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.alian.managementtiket.commons.Constants.BASE_URL
import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.repository.*
import id.alian.managementtiket.domain.repository.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesTicketApi(): TicketApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TicketApi::class.java)
    }

    @Provides
    @Singleton
    fun providesUserRepository(api: TicketApi): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesTicketRepository(api: TicketApi): TicketRepository {
        return TicketRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesOrderRepository(api: TicketApi): OrderRepository {
        return OrderRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesPaymentRepository(api: TicketApi): PaymentRepository {
        return PaymentRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(api: TicketApi): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepositoryImpl(context)
    }
}