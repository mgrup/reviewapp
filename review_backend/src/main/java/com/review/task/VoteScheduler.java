package com.review.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class VoteScheduler {

    private static final Logger log = LoggerFactory.getLogger(VoteScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void voteFirstTask() throws IOException {
         System.out.println("voteFirstTask in task "+dateFormat.format(new Date()));
         log.info("The time is now {}", dateFormat.format(new Date()));

         ProcessBuilder builder = new ProcessBuilder("phantomjs", "E:\\__projects\\test\\phantomFile.js");
         Process process = builder.start();
         System.out.println(process.isAlive());
    }
}
