package com.example.stopwatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Allow requests from frontend (e.g., http://127.0.0.1:5000)
@CrossOrigin(origins = "http://127.0.0.1:5000")  // ✅ Allow frontend access
@RestController
@RequestMapping("/api/stopwatch")
public class StopwatchController {

    @Autowired
    private StopwatchService stopwatchService;

    @PostMapping("/start")
    public ResponseEntity<Map<String, Object>> start() {
        Map<String, Object> response = new HashMap<>();

        if (stopwatchService.isRunning()) {
            response.put("message", "Stopwatch is already running.");
            response.put("status", "warning");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        stopwatchService.start();
        response.put("message", "Stopwatch started successfully.");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/stop")
    public ResponseEntity<?> stop() {
        try {
            stopwatchService.stop();
            return ResponseEntity.ok("Stopwatch stopped");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        if (!stopwatchService.isRunning() && stopwatchService.getElapsedTime() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stopwatch is already reset.");
        }
        stopwatchService.reset();
        return ResponseEntity.ok("Stopwatch reset.");
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("running", stopwatchService.isRunning());
        response.put("elapsedTimeMillis", stopwatchService.getElapsedTime());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/time")
    public String getTime() {
        long elapsedTime = stopwatchService.getElapsedTime();
        return "Elapsed time: " + elapsedTime + " ms";
    }
}
