package com.github.monsterhxw.external;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Xuewei Huang
 * @since 2022-09-26
 */
public class ThreadPriorityPractice {
    
    static class Job implements Runnable {
        
        private int priority;
        
        private long jobCount;
        
        Job(int priority) {
            this.priority = priority;
            jobCount = 0;
        }
        
        @Override
        public void run() {
            while (notStart) {
                Thread.yield();
            }
            while (notEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }
    
    private static volatile boolean notStart = true;
    
    private static volatile boolean notEnd = true;
    
    public static void main(String[] args) throws InterruptedException {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread: " + i);
            thread.setPriority(priority);
            thread.start();
        }
        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;
        
        jobs.forEach(job -> System.out.println("Job priority : " + job.priority + ", job count : " + job.jobCount));
    }
}
