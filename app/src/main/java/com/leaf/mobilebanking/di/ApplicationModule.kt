package com.leaf.mobilebanking.di

import com.leaf.mobilebanking.data.api.AuthAPI
import com.leaf.mobilebanking.repository.signInRepository.SignInRepositoryImp
import com.leaf.mobilebanking.repository.signUpRepository.SignUpRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://64.227.145.43/api/")
        .build()

    @Provides
    @Singleton
    fun provideAuthAPI(retrofit: Retrofit): AuthAPI = retrofit.create(AuthAPI::class.java)

    @Provides
    @Singleton
    fun provideSignInRepo(authAPI: AuthAPI) = SignInRepositoryImp(authAPI)

    @Provides
    @Singleton
    fun provideSignUpRepo(authAPI: AuthAPI) = SignUpRepositoryImp(authAPI)

}