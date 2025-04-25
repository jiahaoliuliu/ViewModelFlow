package com.jiahaoliuliu.viewmodelflow

import kotlin.random.Random

class RandomNumberRepository {

    fun getRandomNumber(until: Int): Int {
        return Random.nextInt(until)
    }
}