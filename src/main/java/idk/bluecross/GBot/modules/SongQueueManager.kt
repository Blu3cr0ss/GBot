package idk.bluecross.GBot.modules

import com.sedmelluq.discord.lavaplayer.track.AudioReference
import discord4j.core.event.domain.message.MessageCreateEvent
import idk.bluecross.GBot.music
import idk.bluecross.GBot.player
import idk.bluecross.GBot.playerManager
import idk.bluecross.GBot.util.D4J
import idk.bluecross.GBot.util.log
import java.util.*
import kotlin.collections.ArrayDeque

object SongQueueManager {
    val songQueue = ArrayDeque<AudioReference>()
    fun addSongToQueue(song: String) {
        if (songQueue.size == 0) {
            songQueue.add(getTrackFromString(song))
            playNextSong()
        } else {
            songQueue.add(getTrackFromString(song))
        }
    }

    fun removeSongFromQueue() {
        log(songQueue)
        if (songQueue.isNotEmpty()) {
            log(songQueue.first())
            songQueue.removeFirst()
            playNextSong()
        }
    }

    fun getTrackFromString(str: String): AudioReference {
        return AudioReference(str, null)
    }

    private fun playNextSong() {
        if (songQueue.isNotEmpty()) {
            playerManager.loadItem(songQueue.first(), music)
        }
    }

    fun logQueue(ev: MessageCreateEvent) {
        if (songQueue.isNotEmpty()) {
            var str = ""
            var q = songQueue.map {
                "`" + it.identifier + "`\n"
            }.forEach {
                str += it
            }
            D4J.sendMessage(ev, "Song queue: \n" + str)
        }else{
            D4J.sendMessage(ev,"song queue is empty")
        }
    }
}