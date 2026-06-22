package com.shopnow.cl.shopnow.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shopnow.cl.shopnow.service.BackupService;

@Component
public class BackupScheduler {

    private final BackupService backupService;

    public BackupScheduler(BackupService backupService) {
        this.backupService = backupService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleBackup() {
        backupService.createBackup();
    }
}