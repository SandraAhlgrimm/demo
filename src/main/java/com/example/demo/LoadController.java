package com.example.demo;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

 

@RestController
public class LoadController {


    private final Logger log = LoggerFactory.getLogger(LoadController.class);


    /**
     * Simulate requests to use a lot of CPU.
     *
     * @param iterations the number of iterations to run (times 20,000).
     * @return the result
     */
    @GetMapping("/run/{iterations}")
    String run(@PathVariable Long iterations) {
        Long givenIterations = iterations;
        if (iterations == null) {
            iterations = 20000L;
        } else {
            iterations *= 20000;
        }
        while (iterations > 0) {
            if (iterations % 20000 == 0) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ie) {}
            }
            iterations--;
        }
        log.debug("iterations: " + givenIterations * 20000);
        return "We are done with the CPU load " + givenIterations * 20000 + " iterations.";
    }

 

    /**
     * Simulate requests that use a lot of memory.
     *
     * @param bites the number of megabytes to eat
     * @return the result.
     */
    @GetMapping("/eat/{bites}")
    String eat(@PathVariable Integer bites) {
        if (bites == null) {
            bites = 1;
        }
        Long givenBites = bites.longValue();
        HashMap hunger = new HashMap<>();
        for (int i = 0; i < bites * 1024 * 1024; i += 8192) {
            byte[] bytes = new byte[8192];
            hunger.put(i, bytes);
            for (int j = 0; j < 8192; j++) {
                bytes[j] = '0';
            }
        }
        log.debug("iterations: " + givenBites * 1024 * 1024);
        return "We are done with the memory load: " + givenBites * 1024 * 1024 + " bytes.";
    }
}