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

// Allow requests from frontend (updated to include both localhost and 127.0.0.1)
@CrossOrigin(origins = {"http://localhost:5000", "http://127.0.0.1:5000"})
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
    public ResponseEntity<Map<String, Object>> stop() {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!stopwatchService.isRunning()) {
                response.put("message", "Stopwatch is not running.");
                response.put("status", "warning");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            stopwatchService.stop();
            response.put("message", "Stopwatch stopped successfully.");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            response.put("message", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @PostMapping("/reset")
    public ResponseEntity<Map<String, Object>> reset() {
        Map<String, Object> response = new HashMap<>();
        
        if (!stopwatchService.isRunning() && stopwatchService.getElapsedTime() == 0) {
            response.put("message", "Stopwatch is already reset.");
            response.put("status", "warning");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        stopwatchService.reset();
        response.put("message", "Stopwatch reset successfully.");
        response.put("status", "success");
        return ResponseEntity.ok(response);
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