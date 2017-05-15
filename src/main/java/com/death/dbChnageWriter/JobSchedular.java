package com.death.dbChnageWriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;

public class JobSchedular{
	  @Autowired
	  public AnnotationConfigApplicationContext annotationConfigApplicationContext;
	 
	  @Autowired
	  public Job job;
	  @Autowired
	  public JobModel jModel;
	  @Autowired
	  public JobLauncher jobLauncher;
	 
	  @Scheduled(fixedRate=10000)
	  public void printMessage() throws Exception {
		  //AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(BatchJConfigurations.class);
		  LastJobTime lJob = annotationConfigApplicationContext.getBean(LastJobTime.class);
	      if(ConnectionMaker.getLastJobExecutionDate()!=null)
	      {
	    	  lJob.setDateOfLast(ConnectionMaker.getLastJobExecutionDate());
	      }
	      else{
	          lJob.setDateOfLast(null);
	      }
		  jModel.setTime(System.currentTimeMillis());
	      jModel.setJobName("job");
		  jModel.setTime(System.currentTimeMillis());
		  JobParameters jobParameter = new JobParametersBuilder().addLong(jModel.getJobName(), jModel.getTime()).toJobParameters();
		  jobLauncher.run(job, jobParameter);
	  }
}