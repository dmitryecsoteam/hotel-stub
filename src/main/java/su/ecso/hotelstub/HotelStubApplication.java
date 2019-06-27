package su.ecso.hotelstub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import su.ecso.hotelstub.service.PriceHotelService;

import java.time.LocalDate;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class HotelStubApplication {

	@Autowired
	private PriceHotelService service;

	@Bean
	public Executor threadPoolExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(15);
		executor.setThreadNamePrefix("Hotel-stub-Thread-");
		executor.setQueueCapacity(2);
		executor.initialize();
		return executor;
	}


	public static void main(String[] args) {
		SpringApplication.run(HotelStubApplication.class, args);
	}

	@Scheduled(cron = "${cronTimer}")
	//@Scheduled(fixedRate=4500000)
	@Async("threadPoolExecutor")
	public void processTravelsWrapper() {
		service.processTravels(LocalDate.now());
	}
}