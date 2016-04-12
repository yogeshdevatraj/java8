package com.java8.optional;

import java.util.Optional;
public class HomeServices {
	private static final int NOW = 0;
	private static Optional<HomeServices> service;
	
	public static Optional<HomeServices> get() {
		service = Optional.of(service.orElse(new HomeServices()));
		return service;
	}
	
	public Optional<Service> getRefrigertorControl() {
		Service s = new  RefrigeratorService();
		//...
		return Optional.ofNullable(s);
	}
	
	public static void main(String[] args) {
		/* Get Home Services handle */
		Optional<HomeServices> homeServices = HomeServices.get();
		if(homeServices != null) {
			Optional<Service> refrigertorControl = homeServices.get().getRefrigertorControl();
			refrigertorControl.ifPresent(HomeServices::switchItOn);
		}
	}
	
	public static void switchItOn(Service s){
		//...
	}
}
