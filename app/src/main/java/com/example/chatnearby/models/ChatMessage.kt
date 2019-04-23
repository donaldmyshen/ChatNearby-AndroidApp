package com.example.chatnearby.models


class ChatMessage(
        val id: String,
        val text: String,
        val fromId: String,
        val toId: String,
        val timestamp: Long // message time
) {
    constructor() : this("", "", "", "", -1)
}