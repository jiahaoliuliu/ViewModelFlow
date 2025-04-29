package com.jiahaoliuliu.viewmodelflow.domain

import com.jiahaoliuliu.viewmodelflow.data.RandomNumberRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRandomNumberUseCase(
    private val randomNumberRepository: RandomNumberRepository = RandomNumberRepository()
) {
    fun invokeFlow(until: Int, delay: Long = 1_000): Flow<Int> {
        return flow {
            while (true) {
                delay(delay)
                emit(randomNumberRepository.getRandomNumber(until))
            }
        }
    }
}