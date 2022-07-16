package com.vttp2022.sdfworkshop13;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.vttp2022.sdfworkshop13.util.IOUtil.*;		// import all static methods

@SpringBootApplication
public class SdfWorkshop13Application {
	private static final Logger logger = LoggerFactory.getLogger(SdfWorkshop13Application.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SdfWorkshop13Application.class);
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);

		List<String> optVals = appArgs.getOptionValues("dataDir");
		if (optVals != null) {
			logger.info("optVals is not null: " + optVals.get(0));
			createDirectory((String) optVals.get(0));
		} else {
			logger.warn("No dataDir specified");
			System.exit(1);
		}

		app.run(args);
	}

}
