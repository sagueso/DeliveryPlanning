package com._1.hex.DeliveryPlanning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class DeliveryPlanningApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(DeliveryPlanningApplication.class, args);
		// Retrieve LanchApp bean from Spring context and call lanch()
		//LanchApp lanchApp = context.getBean(LanchApp.class);
		lanchApp.lanch();
		Mvp mvp = context.getBean(Mvp.class);
		try {
			mvp.launch();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
