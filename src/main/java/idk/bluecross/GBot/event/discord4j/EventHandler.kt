package idk.bluecross.GBot.event.discord4j

import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import idk.bluecross.GBot.bot
import idk.bluecross.GBot.commands
import idk.bluecross.GBot.me
import idk.bluecross.GBot.prefix
import idk.bluecross.GBot.util.log


object EventHandler {
    init {
        bot.on(ReadyEvent::class.java)
            .subscribe { event: ReadyEvent ->
                val self = event.self
                me = self
                log(String.format("Logged in as %s#%s", self.username, self.discriminator))
            }

        bot
            .on(MessageCreateEvent::class.java)
            .subscribe { event ->
                val text = event.message.content.split(" ")
                val content: String = text[0]
                val args = ArrayList(text)
                args.removeAt(0)
                for (msg in commands) {
                    if (content == "$prefix${msg.req}") {
                        msg.addArgs(args)
                        msg.execute(event)
                        break
                    }
                }
            }
    }
}
