package com.leaf.mobilebanking.domain

import com.google.gson.GsonBuilder
import com.leaf.mobilebanking.data.constants.ErrorCodes
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.data.model.ErrorBody
import com.leaf.mobilebanking.data.model.Verify
import com.leaf.mobilebanking.data.model.VerifyErrorBody
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.repository.verifyRepository.VerifyRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class VerifyUseCache @Inject constructor(private val repository: VerifyRepository) {

    @Inject
    lateinit var settings: Settings
    suspend operator fun invoke(token: String?, code: String): State {
        if (token.isNullOrEmpty() || code.isEmpty()) return State.Error(ErrorCodes.CODE_ERROR)

        try {
            val entity = Verify(token, code)
            val response = repository.signUpVerify(entity)

            settings.accessToken = response.token

        }catch (e: Exception){
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()
                if (errorBody != null) {
                    try {
                        val error = GsonBuilder().create()
                            .fromJson(errorBody.charStream(), VerifyErrorBody::class.java)
                        return State.ErrorIO(error.message)
                    } catch (ee: Exception) {
                        if (ee is IOException) return State.NoNetwork
                        return State.Error(-1)
                    }
                }
            }
            return State.Error(-1)
        }

        return State.Success<Unit>()

    }
}