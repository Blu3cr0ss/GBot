package idk.bluecross.GBot

import idk.bluecross.GBot.event.discord4j.EventHandler
import idk.bluecross.GBot.event.discord4j.PlayerEvents
import kotlin.concurrent.thread


fun main(args: Array<String>) {
    shutdownHooks()
    thread {        // JVM CANT STOP WHEN NON-DAEMON THREAD IS RUNNING
        Thread.currentThread().join()
    }
    start()      // <---------- INIT GLOBALS FILE
    CommandsManager     // AGAIN, JUST INIT THIS
    EventHandler
    player.addListener(PlayerEvents)
}

fun shutdownHooks() {
    Runtime.getRuntime().addShutdownHook(Thread { bot.logout().block() })
}