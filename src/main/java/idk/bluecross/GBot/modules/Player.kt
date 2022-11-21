package idk.bluecross.GBot.modules

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import idk.bluecross.GBot.util.log


class Player(private val player: AudioPlayer) : AudioLoadResultHandler {

    override fun trackLoaded(track: AudioTrack) {
        log("successfully loaded track")
        player.playTrack(track)
    }

    override fun playlistLoaded(playlist: AudioPlaylist) {
        log("playlist loaded")
    }

    override fun noMatches() {
        log("no matches")
    }

    override fun loadFailed(exception: FriendlyException) {
        log("something went wrong")
    }
}