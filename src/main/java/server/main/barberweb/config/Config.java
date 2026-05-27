package server.main.barberweb.config;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class Config {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    //Bootstrap info
    public static String DEV_PASSWORD = dotenv.get("DEV_PASSWORD");
    public static String DEV_EMAIL = dotenv.get("DEV_EMAIL");

    //JWT
    public static final String JWT_SECRET = dotenv.get("JWT_SECRET");
}
