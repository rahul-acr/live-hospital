package org.social.it.batch;

import org.social.it.batch.runner.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "org.social.it.**.repository")
@SpringBootApplication
@ComponentScan(value = "org.social.it")
public class BatchApplication {

    private static final Logger LOG = LoggerFactory.getLogger(BatchApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(BatchApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        ApplicationContext applicationContext = application.run(args);

        applicationContext.getBean(args[0], Runner.class).run(args);
//        MasterDataProcessor masterDataProcessor = applicationContext.getBean(MasterDataProcessor.class);

//        masterDataProcessor.readData(new File("/home/rahul/LiveHospital/Hospitals_Master_Data.csv"));
    }

}
