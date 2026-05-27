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
                    "paplay",
                    "/home/toucan/Music/de-copao-na-mao.wav"
            );

            processBuilder.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}