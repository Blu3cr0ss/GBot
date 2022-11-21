package idk.bluecross.GBot

import com.sedmelluq.discord.lavaplayer.track.AudioReference
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import discord4j.common.util.Snowflake
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.`object`.VoiceState
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.channel.VoiceChannel
import idk.bluecross.GBot.modules.Command
import idk.bluecross.GBot.modules.SongQueueManager
import idk.bluecross.GBot.util.D4J
import idk.bluecross.GBot.util.log
import java.util.concurrent.Future
import kotlin.concurrent.thread

object CommandsManager {
    lateinit var musicPlayingRn: Future<Void>

    init {
        Command("ping") { ev, args ->
            D4J.sendMessage(ev, "pong!")
        }
        Command("join") { ev, args ->
            joinVC(ev)
        }
        Command("play") { ev, args ->
            if (args.isNotEmpty()) {
                if (bot.voiceConnectionRegistry.getVoiceConnection(ev.guildId.get())
                        .block()?.isConnected?.block() != true
                )
                    joinVC(ev)
                SongQueueManager.addSongToQueue(args[0])
                D4J.sendMessage(ev, "song added to queue")
            }
        }
        Command("skip") { ev, args ->
            if (args.isNotEmpty()) {
                repeat(args[0].toIntOrNull() ?: 0) {
                    player.stopTrack()
                }
                D4J.sendMessage(ev, "Skipping song(s)...")
            } else {
                player.stopTrack()
                D4J.sendMessage(ev, "Skipping 1 song...")
            }
        }
        Command("queue") { ev, args ->
            SongQueueManager.logQueue(ev)
        }
        Command("leave") { ev, args ->
            bot.voiceConnectionRegistry.getVoiceConnection(ev.guildId.get()).block().disconnect().block()
            D4J.sendMessage(ev, "successfully disconnected from channel")
        }
        Command("help") { ev, args ->
            var res = "list of commands: \n" +
                    ">>> "
            commands.map {
                it.req
            }.forEach {
                res += it + "\n"
            }
            res += "prefix is " + prefix
            D4J.sendMessage(ev, res)
        }
    }

    fun joinVC(ev: MessageCreateEvent) {
        val member = ev.member.orElse(null)
        if (member != null) {
            val voiceState = member.voiceState.block()
            if (voiceState != null) {
                val channel = voiceState.channel.block()
                channel?.join { spec -> spec.setProvider(provider) }?.block()
                D4J.sendMessage(ev, "successfully joined voice channel")
            } else {
                D4J.sendMessage(ev, "you arent in voice channel")
            }
        }
    }
}