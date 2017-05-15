package com.death.dbChnageWriter;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class Processor implements ItemProcessor<Users, Users>{

	@Autowired
	public JobModel jModel;
	
	@Autowired
	public JobRepository jRepo;
	
	@Autowired
	public LastJobTime jLast;
	
	@Override
	public Users process(Users arg0) throws Exception {
		
		System.out.println("LAST TIME:"+jLast.getDateOfLast());
        java.util.Date date = arg0.getTimestamp();
        if(jLast.getDateOfLast()!=null){
        if(date.after(jLast.getDateOfLast()))
        {
        	return arg0;
        }else
        {
        	return null;
        }
        }else
        {
        	return arg0;
        }
	}
}