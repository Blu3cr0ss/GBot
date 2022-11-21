package idk.bluecross.GBot.event.discord4j

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import idk.bluecross.GBot.modules.SongQueueManager
import idk.bluecross.GBot.util.log

object PlayerEvents : AudioEventAdapter() {
    override fun onTrackEnd(player: AudioPlayer?, track: AudioTrack?, endReason: AudioTrackEndReason?) {
        log("song ended")
        SongQueueManager.removeSongFromQueue()
    }
}