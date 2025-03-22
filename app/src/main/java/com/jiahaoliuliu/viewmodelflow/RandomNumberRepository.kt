package com.jiahaoliuliu.viewmodelflow

import kotlinx.coroutines.delay
import kotlin.random.Random

class RandomNumberRepository {

    fun getRandomNumber(): Int {
        return Random.nextInt()
    }
}