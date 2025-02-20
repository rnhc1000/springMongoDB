package br.dev.ferreiras.mongodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@SpringBootApplication
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class MongoDbApplication implements CommandLineRunner {
public static final Logger logger = LoggerFactory.getLogger(MongoDbApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MongoDbApplication.class, args);
	}

	/**
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	@Override
	public void run(String... args) throws Exception {

		final ZonedDateTime zonedDateTime = ZonedDateTime.now(ZonedDateTime.now().getZone());
		final String javaVersion = System.getProperty("java.version");
		final int  numOfCores = Runtime.getRuntime().availableProcessors();
		final long totalMemory = Runtime.getRuntime().totalMemory()/1000_000L;

		if (MongoDbApplication.logger.isInfoEnabled()) {
			MongoDbApplication.logger.info("MongoDB Restful API started running at {},running java version {}, " +
																		 "on top of {} cores and {}MB of RAM",
					zonedDateTime,
					javaVersion,
					numOfCores,
					totalMemory
			);
		}
	}
}
