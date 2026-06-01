package server.main.barberweb.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartUpSound {

    @EventListener(ApplicationReadyEvent.class)
    public void playSound() {

        try {

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "aplay",
                    "/home/toucan_it/Music/de-copao-na-mao-estourado"
            );

            processBuilder.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}