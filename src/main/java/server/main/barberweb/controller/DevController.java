package server.main.barberweb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.main.barberweb.service.DevTools;

import java.util.UUID;

@RestController
@RequestMapping("/developer")
public class DevController {

    @Autowired
    private DevTools devTools;

    @PostMapping("/tornarAdmin")
    public ResponseEntity<String> tornarAdmin(UUID id) {
        return ResponseEntity.ok(devTools.tornarAdmin(id));
    }

    @PostMapping ("/tornarDeveloper")
    public ResponseEntity<String> tornarDeveloper(UUID id) {
        return ResponseEntity.ok(devTools.tornarDeveloper(id));
    }
}
