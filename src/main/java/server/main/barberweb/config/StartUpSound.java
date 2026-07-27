package server.main.barberweb.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartUpSound {

    @EventListener(ApplicationReadyEvent.class)
    public void playSound() {

        try {

            String os = System.getProperty("os.name").toLowerCase();

            ProcessBuilder processBuilder;

            if (os.contains("win")) {
                processBuilder = new ProcessBuilder(
                        "powershell",
                        "-c",
                        "(New-Object Media.SoundPlayer 'C:\\Users\\SeuUsuario\\Music\\de-copao-na-mao.wav').PlaySync();"
                );
            } else {
                processBuilder = new ProcessBuilder(
                        "aplay",
                        "/home/toucan/Music/de-copao-na-mao.wav"
                );
            }

            processBuilder.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}