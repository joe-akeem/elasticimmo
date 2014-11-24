package de.joeakeem.elasticimmo.openimmo.dataimport;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import de.joeakeem.elasticimmo.model.ImportRecord;
import de.joeakeem.elasticimmo.repository.ImportRecordRepository;

@Component
public class OpenImmoImporter {
	
	private final static Logger LOG = LoggerFactory.getLogger(OpenImmoImporter.class);
	
	@SuppressWarnings("rawtypes")
	@Inject
	private StaxEventItemReader xmlItemReader;
	
	@Inject
	private ImportRecordRepository importRecordRepo;

	@Inject
	private JobLauncher jobLauncher;
	
	@Inject
	private Job job;
	
	public void run() {
		ImportRecord importRecord = new ImportRecord();
		importRecord.setStartTime(new Date());
		
		try {
			JobExecution execution = jobLauncher.run(job, new JobParameters());
			LOG.info("Exit Status : {}", execution.getStatus());
			
			importRecord.setEndTime(new Date());
			importRecordRepo.save(importRecord);
			
		} catch (JobExecutionAlreadyRunningException
				| JobRestartException
				| JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e)
		{
			LOG.error("Failed to run the import job", e);
		}
	}

	public static void main(String[] args) {
		String[] springConfig = { "/applicationContext.xml" };
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				springConfig);
		
		OpenImmoImporter openimmoImporter = context.getBean(OpenImmoImporter.class);
		openimmoImporter.run();

		LOG.info("Done.");;
	}
}
