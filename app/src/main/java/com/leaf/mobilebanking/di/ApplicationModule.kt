package com.leaf.mobilebanking.di

import android.content.Context
import androidx.room.Room
import com.leaf.mobilebanking.data.AppDataBase
import com.leaf.mobilebanking.data.api.AuthAPI
import com.leaf.mobilebanking.data.api.CardAPI
import com.leaf.mobilebanking.data.dao.BankingDao
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.data.preferences.SettingsImp
import com.leaf.mobilebanking.repository.cardsRepository.CardRepository
import com.leaf.mobilebanking.repository.cardsRepository.CardRepositoryImp
import com.leaf.mobilebanking.repository.securityRepository.SecurityRepositoryImp
import com.leaf.mobilebanking.repository.securityRepository.SecurityRepository
import com.leaf.mobilebanking.repository.signInRepository.SignInRepository
import com.leaf.mobilebanking.repository.signInRepository.SignInRepositoryImp
import com.leaf.mobilebanking.repository.signUpRepository.SignUpRepository
import com.leaf.mobilebanking.repository.signUpRepository.SignUpRepositoryImp
import com.leaf.mobilebanking.repository.verifyRepository.VerifyRepository
import com.leaf.mobilebanking.repository.verifyRepository.VerifyRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://64.227.145.43/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @Singleton
    fun provideAuthAPI(retrofit: Retrofit): AuthAPI = retrofit.create()

    @Provides
    @Singleton
    fun provideCardAPI(retrofit: Retrofit): CardAPI = retrofit.create()

    @Provides
    @Singleton
    fun provideSignInRepo(authAPI: AuthAPI): SignInRepository = SignInRepositoryImp(authAPI)

    @Provides
    @Singleton
    fun provideSignUpRepo(authAPI: AuthAPI): SignUpRepository = SignUpRepositoryImp(authAPI)

    @Provides
    @Singleton
    fun provideSignUpVerifyRepo(authAPI: AuthAPI): VerifyRepository = VerifyRepositoryImp(authAPI)

    @Provides
    @Singleton
    fun provideCardRepo(cardAPI: CardAPI) : CardRepository = CardRepositoryImp(cardAPI)

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context): Settings = SettingsImp(context)

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) : AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java, "Banking"
    ).build()

    @Provides
    @Singleton
    fun provideDao(appDataBase: AppDataBase): BankingDao = appDataBase.bankingDao()

    @Provides
    @Singleton
    fun provideSecurityRepo(bankingDao: BankingDao) : SecurityRepository = SecurityRepositoryImp(bankingDao)

}