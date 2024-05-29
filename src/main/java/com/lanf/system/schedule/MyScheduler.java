package com.lanf.system.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MyScheduler {

    @Scheduled(fixedDelay = 1 * 60 * 60 * 1000)
    public void clearExpiredData() throws Exception {
    }
}
