package io.demoClient;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import io.demoClient.model.Car;
import io.demoClient.model.CarBooking;
import io.demoClient.model.CarDTO;
import io.demoClient.model.User;
import io.demoClient.model.UserDTO;
import io.demoClient.utils.Utilities;

@SpringBootApplication
@EnableEurekaClient
public class DemoClientApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(DemoClientApplication.class);

	@Value("${application.car.service.endPoint.create:'http://localhost:3002/rest/car/addNewCar'}")
	private String createNewCarEndpoint;

	@Value("${application.user.service.endPoint.create:'http://localhost:3001/rest/user/new/user'}")
	private String createNewUserEndpoint;

	@Value("${application.booking.service.endPoint.create:'http://localhost:3003/rest/booking/new/booking'}")
	private String createNewBookingEndpoint;

	@Value("${application.car.service.endPoint.getAll:'http://localhost:3002/rest/car/all'}")
	private String getAllCarInfoEndpoint;

	@Value("${application.user.service.endPoint.getAll:'http://localhost:3001/rest/user/all'}")
	private String getAllUserInfoEndpoint;

	@Value("${application.booking.service.endPoint.getAll:'http://localhost:3003/rest/booking/all'}")
	private String getAllBookingInfoEndpoint;

	@Value("${application.car.service.endPoint.reports}")
	private String carReportingEndpoint;

	@Value("${application.user.service.endPoint.reports}")
	private String UserReportingEndpoint;

	@Value("${application.booking.service.endPoint.reports}")
	private String BookingReportingEndpoint;

	public static void main(String[] args) {
		SpringApplication.run(DemoClientApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public void run(String... args) throws Exception {
		ExecutorService threadService= Executors.newFixedThreadPool(5);
		ExecutorService carBookingService=Executors.newFixedThreadPool(10);
		//while(true) {
			for(int j=0;j<5;j++) {
				CompletableFuture<User> userData = CompletableFuture.supplyAsync(this::createUserData, threadService);
				CompletableFuture<Car> carData = CompletableFuture.supplyAsync(this::createNewCarData, threadService);
				CompletableFuture.allOf(userData, carData).thenRunAsync(this::getCarReports).toCompletableFuture().join();
			}
			for (int i = 0; i < 10; i++) {
				CompletableFuture<List<User>> getAllUsersList = CompletableFuture.supplyAsync(this::getUserData);
				CompletableFuture<List<Car>> getAllCarsList = CompletableFuture.supplyAsync(this::getCarsData);
				CompletableFuture<CarBooking> carBookingsFuture = CompletableFuture.allOf(getAllUsersList, getAllCarsList).thenApplyAsync(dummy -> {
					CarBooking carBookingObj;
					List<User> users = getAllUsersList.join();
					List<Car> cars = getAllCarsList.join();
					carBookingObj = createCarBooking(users, cars);
					return carBookingObj;
				}, carBookingService);
				carBookingsFuture.thenRunAsync(this::getCarBookingReports,carBookingService).join();
			}
			threadService.shutdown();
			carBookingService.shutdown();
		//}
	}

	public User createUserData() {
		User responseObj = null;
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
		headers.add("Accept", "*/*");
		try {
			logger.info("'createUserData' Executed by Thread:" + Thread.currentThread().getName() + " and Current Time:"
					+ LocalDateTime.now());
			User requestUserObject = new User();
			requestUserObject.setUserid(Utilities.getRandomData.get());
			requestUserObject.setUsername("UserName-" + Utilities.getRandomData.get());
			HttpEntity<User> request = new HttpEntity<User>(requestUserObject, headers);
			ResponseEntity<User> response = restTemplate.exchange(createNewUserEndpoint, HttpMethod.POST, request,
					User.class);
			responseObj = response.getBody();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.warn("Exception:" + e.getMessage() + " and details:" + exceptionAsString);
			//createUserData();
		}
		return responseObj;
	}

	public List<User> getUserData() {
		List<User> listOfUsers = null;
		try {
			logger.info("'getUserData' Executed by Thread:" + Thread.currentThread().getName() + " and Current Time:"
					+ LocalDateTime.now());
			UserDTO responseUserDTO = restTemplate.getForObject(getAllUserInfoEndpoint, UserDTO.class);
			listOfUsers = responseUserDTO.getUsers();
		} catch (Exception e) {
			logger.warn("Exception:" + e.getMessage());
			//getUserData();
		}
		return listOfUsers;
	}

	public List<Car> getCarsData() {
		List<Car> listofCars = null;
		try {
			logger.info("'getCarsData' Executed by Thread:" + Thread.currentThread().getName() + " and Current Time:"
					+ LocalDateTime.now());
			CarDTO responseCarDTO = restTemplate.getForObject(getAllCarInfoEndpoint, CarDTO.class);
			listofCars = responseCarDTO.getCars();
		} catch (Exception e) {
			logger.warn("Exception:" + e.getMessage());
			//getCarsData();
		}
		return listofCars;
	}

	public Car createNewCarData() {
		Car responseObj = null;
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
		headers.add("Accept", "*/*");
		try {
			logger.info("'createNewCarData' Executed by Thread:" + Thread.currentThread().getName()
					+ " and Current Time:" + LocalDateTime.now());
			Car requestCarObject = new Car();
			Integer carPlateNumber = Utilities.getRandomData.get();
			requestCarObject.setCarPlateNumber(carPlateNumber);
			requestCarObject.setCarModelName("Car-Model-" + Utilities.getRandomData.get());
			LocalDateTime carAvailabilityStartDate = Utilities.getRandomLocalDateTime.apply(LocalDateTime.now());
			LocalDateTime carAvailabilityEndDate = carAvailabilityStartDate.plusDays(60);
			requestCarObject.setCarAvailableStartDate(carAvailabilityStartDate);
			requestCarObject.setCarAvailableEndDate(carAvailabilityEndDate);
			Double randomDoubleRentalPrice = Utilities.getRandomDoubleWithinRange.apply(10.0, 20.0);
			requestCarObject.setCarRentalPricePerHour(randomDoubleRentalPrice);
			HttpEntity<Car> request = new HttpEntity<Car>(requestCarObject, headers);
			ResponseEntity<Car> response = restTemplate.exchange(createNewCarEndpoint, HttpMethod.POST, request,
					Car.class);
			responseObj = response.getBody();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.warn("Exception:" + e.getMessage() + " and details:" + exceptionAsString);
			//createNewCarData();
		}
		return responseObj;
	}

	public CarBooking createCarBooking(List<User> userList, List<Car> carList) {
		CarBooking responseObj = null;
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
		headers.add("Accept", "*/*");
		try {
			logger.info("'createCarBooking' Executed by Thread:" + Thread.currentThread().getName()
					+ " and Current Time:" + LocalDateTime.now());
			Integer userid = userList.get(Utilities.get_RandomIndexNumber_FromList(userList)).getUserid();
			Car carObj = carList.get(Utilities.get_RandomIndexNumber_FromList(carList));
			Integer carPlateNumber = carObj.getCarPlateNumber();
			LocalDateTime requestedStartBookingDate = Utilities.getRandomLocalDateTimeShortRange
					.apply(carObj.getCarAvailableStartDate());
			LocalDateTime requestedEndBookingDate = requestedStartBookingDate.plusDays(5);
			CarBooking carBookings = new CarBooking();
			carBookings.setCarPlateNumber(carPlateNumber);
			carBookings.setUserId(userid);
			carBookings.setBookingStartDate(requestedStartBookingDate);
			carBookings.setBookingEndDate(requestedEndBookingDate);
			carBookings.setBookingTime(LocalDateTime.now());
			HttpEntity<CarBooking> request = new HttpEntity<CarBooking>(carBookings, headers);
			ResponseEntity<CarBooking> response = restTemplate.exchange(createNewBookingEndpoint, HttpMethod.POST,
					request, CarBooking.class);
			responseObj = response.getBody();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.warn("Exception:" + e.getMessage() + " and details:" + exceptionAsString);
			createCarBooking(userList, carList);
		}
		return responseObj;
	}

	public void getCarReports() {
		try {
			logger.info("'getCarReports' Executed by Thread:" + Thread.currentThread().getName() + " and Current Time:"
					+ LocalDateTime.now());
			restTemplate.getForObject(carReportingEndpoint, String.class);
		} catch (Exception e) {
			logger.warn("Exception:" + e.getMessage());
			//getCarReports();
		}
	}

	public void getUserReports() {
		try {
			logger.info("'getCarReports' Executed by Thread:" + Thread.currentThread().getName() + " and Current Time:"
					+ LocalDateTime.now());
			restTemplate.getForObject(UserReportingEndpoint, String.class);
		} catch (Exception e) {
			logger.warn("Exception:" + e.getMessage());
			//getUserReports();
		}
	}

	public void getCarBookingReports() {
		try {
			logger.info("'getCarReports' Executed by Thread:" + Thread.currentThread().getName() + " and Current Time:"
					+ LocalDateTime.now());
			restTemplate.getForObject(BookingReportingEndpoint, String.class);
		} catch (Exception e) {
			logger.warn("Exception:" + e.getMessage());
			//getCarBookingReports();
		}
	}

}
