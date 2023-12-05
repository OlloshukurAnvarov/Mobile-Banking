package com.leaf.mobilebanking.domain

import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.repository.cardsRepository.CardRepository
import java.io.IOException
import javax.inject.Inject

class DeleteCardUseCase @Inject constructor(private val repository: CardRepository) {
    suspend operator fun invoke(bearerToken: String?, id: Int): State {
        if (bearerToken == null) return State.Error(-1)

        try {
            val token = "Bearer $bearerToken"
            repository.delete(token, id.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            State.Error(-1)
        }
        return State.Success<Unit>()

    }
}