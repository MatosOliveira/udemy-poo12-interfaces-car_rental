/**
 * 
 */
package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

/**
 * @author Matos
 *
 */
public class RentalService {

	private Double pricePerHour;
	private Double pricePerDay;
	
	private BrazilTaxService taxService;

	public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		
		double minutes = Duration.between(carRental.getStartDate(), carRental.getFinishtDate()).toMinutes();
		double hours = minutes / 60.00;
		double basicPayment;
		
		if(hours <= 12.0) {
			basicPayment = pricePerHour * Math.ceil(hours);
		} else {
			basicPayment = pricePerDay * Math.ceil(hours / 24.0);
		}
		
		double tax = taxService.taxService(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
}