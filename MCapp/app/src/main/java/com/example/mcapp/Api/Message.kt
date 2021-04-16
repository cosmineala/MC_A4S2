package com.example.mcapp.Api

import java.util.*

data class Message (
        val id: UUID,
        val sender: String,
        val content: String,
        val seed: String = ""
        )
