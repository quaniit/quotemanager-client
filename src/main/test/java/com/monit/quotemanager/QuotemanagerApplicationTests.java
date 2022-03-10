package com.monit.quotemanager;

import com.monit.quotemanager.controller.QuotemanagerController;
import com.monit.quotemanager.model.QuoteImpl;
import com.monit.quotemanager.model.TradeResult;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class QuotemanagerApplicationTests {

	@Autowired
	private QuotemanagerController controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void addQuote() throws ParseException
	{
		QuoteImpl quote1 = new QuoteImpl(UUID.randomUUID(), "AAPL", 1.0, 1000, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote2 = new QuoteImpl(UUID.randomUUID(), "AAPL", 2.0, 3000, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote3 = new QuoteImpl(UUID.randomUUID(), "GOOG", 3.5, 100, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote4 = new QuoteImpl(UUID.randomUUID(), "GOOG", 3.6, 500, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote5 = new QuoteImpl(UUID.randomUUID(), "MSFT", 4, 400, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));

		assertThat(controller.findAll().isEmpty());

		controller.addQuote(quote1);
		assertThat(controller.findAll().size() == 1);
		assertThat(controller.findAll().get(0).getId().compareTo(quote1.getId()) == 0);

		controller.addQuote(quote2);
		controller.addQuote(quote3);
		controller.addQuote(quote4);
		controller.addQuote(quote5);
		assertThat(controller.findAll().size() == 5);
		assertThat(controller.findAll().get(4).getId().compareTo(quote5.getId()) == 0);
		assertThat(controller.findAll().get(4).getSymbol().equals(quote5.getSymbol()));
	}

	@Test
	public void findBySymbol() throws ParseException
	{
		QuoteImpl quote1 = new QuoteImpl(UUID.randomUUID(), "AAPL", 1.0, 1000, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote2 = new QuoteImpl(UUID.randomUUID(), "AAPL", 2.0, 3000, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote3 = new QuoteImpl(UUID.randomUUID(), "GOOG", 3.5, 100, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote4 = new QuoteImpl(UUID.randomUUID(), "GOOG", 3.6, 500, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote5 = new QuoteImpl(UUID.randomUUID(), "MSFT", 4, 400, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));

		assertThat(controller.findBySymbol("APPL").isEmpty());

		controller.addQuote(quote1);
		controller.addQuote(quote2);
		controller.addQuote(quote3);
		controller.addQuote(quote4);
		controller.addQuote(quote5);
		assertThat(controller.findBySymbol("APPL").size() == 2);
		assertThat(controller.findBySymbol("GOOG").size() == 2);
		assertThat(controller.findBySymbol("MFST").size() == 1);
		assertThat(controller.findBySymbol("BA").size() == 0);
	}

	@Test
	public void updateQuote() throws ParseException
	{
		QuoteImpl quote = new QuoteImpl(UUID.randomUUID(), "AAPL", 1.0, 1000, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));

		controller.addQuote(quote);

		quote.setPrice(1000.00);
		controller.addQuote(quote);
		assertThat(controller.findAll().size() == 1);
		assertThat(controller.findAll().get(0).getPrice() == quote.getPrice());
	}

	@Test
	public void deleteQuoteById() throws ParseException
	{
		QuoteImpl quote = new QuoteImpl(UUID.randomUUID(), "AAPL", 1.0, 1000, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		UUID id = UUID.randomUUID();

		controller.addQuote(quote);
		assertThat(controller.findAll().size() == 1);

		controller.deleteQuote(id.toString());
		assertThat(controller.findAll().size() == 1);

		controller.deleteQuote(quote.getId().toString());
		assertThat(controller.findAll().size() == 0);
	}

	@Test
	public void deleteBySymbol() throws ParseException
	{
		QuoteImpl quote1 = new QuoteImpl(UUID.randomUUID(), "AAPL", 1.0, 1000, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote2 = new QuoteImpl(UUID.randomUUID(), "AAPL", 2.0, 3000, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote3 = new QuoteImpl(UUID.randomUUID(), "GOOG", 3.5, 100, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote4 = new QuoteImpl(UUID.randomUUID(), "GOOG", 3.6, 500, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote5 = new QuoteImpl(UUID.randomUUID(), "MSFT", 4, 400, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));

		controller.addQuote(quote1);
		controller.addQuote(quote2);
		controller.addQuote(quote3);
		controller.addQuote(quote4);
		controller.addQuote(quote5);

		assertThat(controller.findAll().size() == 5);

		controller.deleteQuotesBySymbol("BA");
		assertThat(controller.findAll().size() == 5);

		controller.deleteQuotesBySymbol("GOOG");
		assertThat(controller.findAll().size() == 3);
	}

	@Test
	public void findBestQuote() throws ParseException
	{
		QuoteImpl quote1 = new QuoteImpl(UUID.randomUUID(), "AAPL", 1.0, 1000, DateUtils.parseDate("2021-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote2 = new QuoteImpl(UUID.randomUUID(), "AAPL", 2.0, 0, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote3 = new QuoteImpl(UUID.randomUUID(), "AAPL", 3.5, 100, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote4 = new QuoteImpl(UUID.randomUUID(), "AAPL", 3.6, 500, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote5 = new QuoteImpl(UUID.randomUUID(), "MSFT", 4, 400, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));

		controller.addQuote(quote1);
		controller.addQuote(quote2);
		controller.addQuote(quote3);
		controller.addQuote(quote4);
		controller.addQuote(quote5);

		assertThat(controller.findBestQuote("BA") == null);
		assertThat(controller.findBestQuote("MSFT").getId().compareTo(quote5.getId()));
		assertThat(controller.findBestQuote("AAPL").getId().compareTo(quote3.getId()));

	}

	@Test
	public void ExecuteTrade() throws ParseException
	{
		QuoteImpl quote1 = new QuoteImpl(UUID.randomUUID(), "AAPL", 1.0, 750, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));
		QuoteImpl quote2 = new QuoteImpl(UUID.randomUUID(), "AAPL", 2.0, 1000, DateUtils.parseDate("2023-01-01", Locale.US, "yyyy-MM-dd"));

		controller.addQuote(quote1);
		controller.addQuote(quote2);

		assertThat(controller.ExecuteTrade("BA", 1000).getVolumeWeightedAveragePrice() == 0);

		TradeResult result = controller.ExecuteTrade("APPL", 500);
		assertThat(result.getVolumeWeightedAveragePrice() == 1.0);
		assertThat(controller.findAll().get(0).getAvailableVolume() == 250);
		assertThat(controller.findAll().get(1).getAvailableVolume() == 1000);

		result = controller.ExecuteTrade("APPL", 500);
		assertThat(result.getVolumeWeightedAveragePrice() == 2.5);
		assertThat(controller.findAll().get(0).getAvailableVolume() == 0);
		assertThat(controller.findAll().get(1).getAvailableVolume() == 750);

		result = controller.ExecuteTrade("APPL", 500);
		assertThat(result.getVolumeWeightedAveragePrice() == 2.0);
		assertThat(controller.findAll().get(0).getAvailableVolume() == 0);
		assertThat(controller.findAll().get(1).getAvailableVolume() == 250);


	}
}
