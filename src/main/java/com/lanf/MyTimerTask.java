package com.lanf;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

    @Override
    public void run() {
        // 定时任务的具体操作
        System.out.println("定时任务执行了！");
    }

    public static void main(String[] args) {
        System.out.println("定时任务执行了！");
        // 创建一个定时器
        Timer timer = new Timer();

        // 创建一个定时任务
        MyTimerTask task = new MyTimerTask();

        // 3秒后执行定时任务，然后每隔3秒执行一次
        timer.schedule(task, 3000, 3000);
    }

    public void getTwitterMonitor() {
        // 定时任务的具体操作
        System.out.println("定时任务执行了！");
    }

}
