package com.thermostate.eroski.domain

interface Parser {
    fun parse(file: String): List<String>
}