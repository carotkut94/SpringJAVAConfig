package com.death.dbChnageWriter;

import java.util.Date;
import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class JobCompletetionListner implements JobExecutionListener{

	@Autowired
	public JobRepository jRepo;
	
	private Date startTime;
	private static  Date stopTime;
	private static Date completeTime;
	@Override
	public void beforeJob(JobExecution arg0) {
		// TODO Auto-generated method stub
		startTime = new Date();
		System.out.println("Start Time: "+startTime);
	}

	@Override
    public void afterJob(JobExecution jobExecution) {
        stopTime = new Date();
        System.out.println("Job stops at :"+stopTime);
 
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            System.out.println("job completed successfully");
            //Here you can perform some other business logic like cleanup
        }else if(jobExecution.getStatus() == BatchStatus.FAILED){
            System.out.println("job failed with following exceptions ");
            List<Throwable> exceptionList = jobExecution.getAllFailureExceptions();
            for(Throwable th : exceptionList){
                System.err.println("exception :" +th.getLocalizedMessage());
            }
        }
    }
	
	static Date getLastRunDate()
	{
		return completeTime;
	}
}
