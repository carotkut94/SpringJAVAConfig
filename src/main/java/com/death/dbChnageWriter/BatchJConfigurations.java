package com.death.dbChnageWriter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;

@Configuration
@EnableScheduling
@EnableBatchProcessing
public class BatchJConfigurations {

	@Bean
	public DataSourceTransactionManager transactionManager()
	{
		DataSourceTransactionManager transactionManager1 = new DataSourceTransactionManager();
		transactionManager1.setDataSource(dataSource());
		return transactionManager1;
	}
	
	
	@Bean
	public JobModel jModel()
	{
		return new JobModel();
	}
	
	@Bean
	public LastJobTime jLast()
	{
		return new LastJobTime();
	}
	
	@Bean
	public JobSchedular jobS() {
	     return new JobSchedular();
	}
	
    @Bean
    public JobRepository jRepo() throws Exception {
    	JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
    	factoryBean.setTransactionManager(transactionManager());
    	factoryBean.setDatabaseType("mysql");
    	factoryBean.setDataSource(dataSource());
    	return factoryBean.getJobRepository();
    }
    
    @Autowired			
    public JobBuilderFactory jBuilder;		
    
	@Autowired			
	public StepBuilderFactory stepBuilderFactory;		


	@Bean
	public Step step1() throws ClassNotFoundException
	{
		
		return stepBuilderFactory.get("step1")
	                .<Users, Users>chunk(2)
	                .reader(itemReader())
	                .processor(processor())
	                .writer(itemWriter())
	                .transactionAttribute(new DefaultTransactionAttribute(TransactionAttribute.PROPAGATION_SUPPORTS))
	                .build();
	}

	
	@Bean			
	public Job job() throws Exception {				
		return jBuilder.get("job").start(step1()).listener(new JobCompletetionListner()).build();
	}

	
	@Bean
	 @StepScope
	ItemProcessor<Users, Users> processor()
	{
		return new Processor();
	}

    @Bean
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jRepo());
        return jobLauncher;
    }
    
    @Bean
    public DriverManagerDataSource dataSource()
    {
    	  	DriverManagerDataSource dataSource = new DriverManagerDataSource();
    	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    	    dataSource.setUrl("jdbc:mysql://localhost:3306/users?zeroDateTimeBehavior=convertToNull");
    	    dataSource.setUsername("root");
    	    dataSource.setPassword("");
    	    return dataSource;
    }
    
    @Bean
    @StepScope
    JdbcCursorItemReader<Users> itemReader()
    {
    	System.out.println("itemReader");
    	JdbcCursorItemReader<Users> reader = new JdbcCursorItemReader<Users>();
    	reader.setDataSource(dataSource());
    	String sql = "select id, username, password , alias, updated_at, detail_id, phone, address from users, user_details Where users.id=user_details.user_id ";
    	reader.setSql(sql);
    	reader.setRowMapper(new DBRowMapper());
    	reader.setVerifyCursorPosition(false);
    	return reader;
    }
  
    
    @Bean
    @StepScope
    StaxEventItemWriter<Users> itemWriter() throws ClassNotFoundException
    {
    	StaxEventItemWriter<Users> writer = new StaxEventItemWriter<Users>();
    	System.out.println("itemWriter");
    	FileSystemResource resource = new FileSystemResource("xml/report"+new Date().getTime()+".xml");
    	writer.setResource(resource);
    	XStreamMarshaller userMarshaller = new XStreamMarshaller();
    	Map<String, Users> userMap = new HashMap<String, Users>();
    	userMarshaller.setAliases(userMap);
    	writer.setMarshaller(userMarshaller);
    	writer.setShouldDeleteIfEmpty(true);
    	return writer;
    }
}
