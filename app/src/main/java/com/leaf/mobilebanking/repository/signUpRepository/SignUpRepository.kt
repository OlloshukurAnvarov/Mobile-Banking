package com.leaf.mobilebanking.repository.signUpRepository

import com.leaf.mobilebanking.data.model.SignUpBody
import com.leaf.mobilebanking.data.model.Verify

interface SignUpRepository {
    suspend fun signUp(signUpBody: SignUpBody): Verify
}