package com.grw.truco
import java.io.Serializable
class Match(
    val playerOneName: String, val playerOneScore: Int,
    val playerTwoName: String, val playerTwoScore: Int,): Serializable {


    override fun toString(): String {
        if(playerOneScore > playerTwoScore){
            return "$playerOneName $playerOneScore x $playerTwoScore $playerTwoName";
        }

        return "$playerTwoName $playerTwoScore x $playerOneScore $playerOneName"
    }
}