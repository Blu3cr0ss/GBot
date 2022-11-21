package idk.bluecross.GBot.modules

import discord4j.core.event.domain.message.MessageCreateEvent
import idk.bluecross.GBot.commands

class Command(val req: String, val res: (MessageCreateEvent, List<String>) -> Unit) {      // command, (event)->unit
    val args = arrayListOf<String>()

    init {
        commands.add(this)
    }

    fun addArgs(args: List<String>) {
        this.args.addAll(args)
    }

    fun execute(ev: MessageCreateEvent) {
        res.invoke(ev,this.args)
        args.clear()
    }
}