package com.monit.quotemanager.config;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import com.monit.quotemanager.QuotemanagerApplication;
import com.monit.quotemanager.model.QuoteImpl;
import com.monit.quotemanager.repo.QuoteRepository;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(QuotemanagerApplication.class);

    
    @Bean
  public CommandLineRunner preloadData(QuoteRepository repository) {
    return (args) -> {
	  Date expiration = DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd");
      // save a few quotes
      log.info("Preloading -->" + repository.save(new QuoteImpl(UUID.randomUUID(), "AAPL", 1.0, 750, expiration)));
      log.info("Preloading -->" + repository.save(new QuoteImpl(UUID.randomUUID(), "AAPL", 2.0, 1000, expiration)));
      log.info("Preloading -->" + repository.save(new QuoteImpl(UUID.randomUUID(), "GOOG", 3.5, 100, expiration)));
      log.info("Preloading -->" + repository.save(new QuoteImpl(UUID.randomUUID(), "GOOG", 3.6, 500, expiration)));
      log.info("Preloading -->" + repository.save(new QuoteImpl(UUID.randomUUID(), "MSFT", 4, 400, expiration)));
    };
  }
}
