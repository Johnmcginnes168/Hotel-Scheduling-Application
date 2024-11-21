package edu.wgu.d387_sample_code.TimeZones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TimeZonesController {
    @GetMapping("/presentation")
    public ResponseEntity<String> announcePresentation(){
        String announcement = "The Online Live Presentation will begin at: " + TimeZones.getTime();
        return new ResponseEntity<String> (announcement, HttpStatus.OK);
    }
}
