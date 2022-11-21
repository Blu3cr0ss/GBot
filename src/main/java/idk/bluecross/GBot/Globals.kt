package idk.bluecross.GBot

import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrameBufferFactory
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer
import discord4j.core.DiscordClient
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.User
import discord4j.voice.AudioProvider
import idk.bluecross.GBot.modules.Command
import idk.bluecross.GBot.modules.LavaPlayerAudioProvider
import idk.bluecross.GBot.modules.Player
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

var token = System.getenv("GBot_token")     //MTA0MzIwMzUxNDY5Mzg2MTQ0MQ.GyN8Mb.BHIje0D9kNfCccTR6ELIIWEma_9tK0Y56rgUas

lateinit var playerManager:DefaultAudioPlayerManager
lateinit var player:AudioPlayer
lateinit var provider: AudioProvider
lateinit var music:Player
private lateinit var client: DiscordClient
lateinit var bot: GatewayDiscordClient

lateinit var me:User

val commands = arrayListOf<Command>()
val prefix = ">"



fun start() {
    val client = DiscordClientBuilder.create(token).build()
    val bot = client.login().block()!!
    val playerManager = DefaultAudioPlayerManager()
    playerManager.configuration.frameBufferFactory =
        AudioFrameBufferFactory { bufferDuration: Int, format: AudioDataFormat?, stopping: AtomicBoolean? ->
            NonAllocatingAudioFrameBuffer(
                bufferDuration,
                format,
                stopping
            )
        }
    AudioSourceManagers.registerRemoteSources(playerManager)

    val player = playerManager.createPlayer()
    val provider = LavaPlayerAudioProvider(player)
    val trackScheduler = Player(player)

    ::playerManager.set(playerManager)
    ::client.set(client)
    ::bot.set(bot)
    ::player.set(player)
    ::provider.set(provider)
    ::music.set(trackScheduler)
}
