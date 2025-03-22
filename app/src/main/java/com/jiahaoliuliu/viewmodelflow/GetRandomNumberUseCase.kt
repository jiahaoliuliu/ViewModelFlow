package com.jiahaoliuliu.viewmodelflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRandomNumberUseCase(
    private val randomNumberRepository: RandomNumberRepository = RandomNumberRepository()
) {
    fun invokeFlow(): Flow<Int> {
        return flow {
            while(true) {
                delay(1_000)
                emit(randomNumberRepository.getRandomNumber())
            }
        }
    }

    fun invoke(): Int {
        return randomNumberRepository.getRandomNumber()
    }
}