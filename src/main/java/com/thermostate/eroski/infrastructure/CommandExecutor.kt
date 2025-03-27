package com.thermostate.eroski.infrastructure

import java.io.BufferedReader
import java.io.InputStreamReader

internal object CommandExecutor {
    fun execute(commando: String?) {
        try {
            val proceso = Runtime.getRuntime().exec(commando)
            val lector = BufferedReader(InputStreamReader(proceso.inputStream))
            val errorLector = BufferedReader(InputStreamReader(proceso.errorStream))
            var linea: String?
            while ((lector.readLine().also { linea = it }) != null) {
                println(linea)
            }
            var error: String?
            while ((errorLector.readLine().also { error = it }) != null) {
                throw RuntimeException("Error executing command: $commando because: $error")
            }

            val outputCode = proceso.waitFor()
            println("Exit code: $outputCode")
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}