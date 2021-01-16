package com.webShop.webShop.config;

import com.webShop.webShop.services.schedulerServices.TokenSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class ScheduledTaskConfig {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTaskConfig.class);

    @Autowired
    private TokenSchedulerService tokenSchedulerService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void deleteRegisterToken() {
        log.info("Deleting registration tokens...");
        tokenSchedulerService.deleteRegistrationTokens();
    }

    //every day at noon 12pm
    @Scheduled(cron = "0 0 12 * * ?")
    public void deleteChangePasswordToken() {
        log.info("Deleting change password tokens...");
        tokenSchedulerService.deleteChangePasswordTokens();
    }
}
