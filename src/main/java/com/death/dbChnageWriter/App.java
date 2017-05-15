package com.death.dbChnageWriter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App 
{
	
    public static void main( String[] args ) throws Exception
    {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(BatchJConfigurations.class);        
    }
}
