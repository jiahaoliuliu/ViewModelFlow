package com.jiahaoliuliu.viewmodelflow.data

import kotlin.random.Random

class RandomNumberRepository {

    fun getRandomNumber(until: Int): Int {
        return Random.Default.nextInt(until)
    }
}