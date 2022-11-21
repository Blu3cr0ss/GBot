package idk.bluecross.GBot.util

import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import reactor.core.publisher.Mono

object D4J {
    fun sendMessage(ev: MessageCreateEvent, message: Any) {
        Mono.just(ev).flatMap { e ->
            e.message.channel.flatMap { ch ->
                ch.createMessage(message.toString())
            }
        }.subscribe()
    }
}