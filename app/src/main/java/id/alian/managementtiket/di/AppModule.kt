package id.alian.managementtiket.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.alian.managementtiket.commons.Constants.BASE_URL
import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.repository.OrderRepositoryImpl
import id.alian.managementtiket.data.repository.PaymentRepositoryImpl
import id.alian.managementtiket.data.repository.TicketRepositoryImpl
import id.alian.managementtiket.data.repository.UserRepositoryImpl
import id.alian.managementtiket.domain.repository.OrderRepository
import id.alian.managementtiket.domain.repository.PaymentRepository
import id.alian.managementtiket.domain.repository.TicketRepository
import id.alian.managementtiket.domain.repository.UserRepository
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

}