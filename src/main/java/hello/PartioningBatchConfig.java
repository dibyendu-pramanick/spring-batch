package hello;

/*import java.io.IOException;
import java.net.MalformedURLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
public class PartioningBatchConfig {
	
	@Autowired
    ResourcePatternResolver resoursePatternResolver;
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Bean
	public ResourcelessTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}

	@Bean
	public MapJobRepositoryFactoryBean mapJobRepositoryFactory(ResourcelessTransactionManager txManager)
			throws Exception {

		MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean(txManager);

		factory.afterPropertiesSet();

		return factory;
	}

	@Bean
	public JobRepository jobRepository(MapJobRepositoryFactoryBean factory) throws Exception {
		return factory.getObject();
	}
	
	@Bean
	public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		return launcher;
	}

    // tag::readerwriterprocessor[]
//    @Bean
//    @StepScope
//    public FlatFileItemReader<Person> reader(@Value("#{stepExecutionContext[fileName]}") String filename) {
//        return new FlatFileItemReaderBuilder<Person>()
//            .name("personItemReader")
//            .resource(new ClassPathResource(filename))
//            .delimited()
//            .names(new String[]{"firstName", "lastName"})
//            .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
//                setTargetType(Person.class);
//            }})
//            .build();
//    }
	
	@Bean
    @StepScope
    public FlatFileItemReader<Person> reader(@Value("#{stepExecutionContext[fileName]}") String filename, 
    		@Value("#{stepExecutionContext[skipCount]}") int skipCount,
    		@Value("#{stepExecutionContext[totalCount]}") int totalCount) {
        return new FlatFileItemReaderBuilder<Person>()
            .name("personItemReader")
            .resource(new ClassPathResource(filename))
            .delimited()
            .names(new String[]{"firstName", "lastName"})
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }}).linesToSkip(skipCount).maxItemCount(totalCount)
            .build();
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }
    

    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
            .dataSource(dataSource)
            .build();
    }
    
   
    
    @Bean
    public TaskExecutor taskExecutor(){
    	 ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
         taskExecutor.setMaxPoolSize(5);
         taskExecutor.setCorePoolSize(5);
         taskExecutor.setQueueCapacity(5);
         taskExecutor.afterPropertiesSet();
         return taskExecutor;
    }
    
    @Bean
    public SkipListner applicationSkipListener() {
    	return new SkipListner();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Person> writer, FlatFileItemReader<Person> reader) {
        return stepBuilderFactory.get("step1")
            .<Person, Person> chunk(10)
            .reader(reader)
            .processor(processor())
            .faultTolerant()
            .skipLimit(1)
            .skip(NullPointerException.class)
            .listener(applicationSkipListener())
            .writer(writer)
            //.taskExecutor(taskExecutor())
            .build();
    }
    
    @Bean
    public CustomMultiResourcePartitioner partitioner() {
        CustomMultiResourcePartitioner partitioner = new CustomMultiResourcePartitioner();
        Resource[] resources;
        try {
            resources = resoursePatternResolver.getResources("file:C:/Users/c_dprama/Projects/gs-batch-processing/src/main/resources/*.csv");
        } catch (IOException e) {
            throw new RuntimeException("I/O problems when resolving the input file pattern.", e);
        }
        partitioner.setResources(resources);
        return partitioner;
    }
    
    @Bean
    public Step partitionStep(Step step1) throws UnexpectedInputException, MalformedURLException, ParseException {
        return stepBuilderFactory.get("partitionStep")
          .partitioner("slaveStep", partitioner())
          .step(step1)
          .taskExecutor(taskExecutor())
          .gridSize(10)
          .build();
    }
    
    @Bean
    public Job partitionerJob(Step partitionStep, JobCompletionNotificationListener listener) throws UnexpectedInputException, MalformedURLException, ParseException {
        return jobBuilderFactory.get("partitionerJob")
		  .listener(listener)
          .start(partitionStep)
          .build();
    }
    
    

}*/
