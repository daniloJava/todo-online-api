package br.com.todo.onlineservice.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {

	@Valid
	private final Document document = new Document();

	@Valid
	private final Route route = new Route();

	@Getter
	@Setter
	public static class Document {

		@Valid
		private final Contact contact = new Contact();

		@NotEmpty
		private String title;

		@NotEmpty
		private String description;

		@NotEmpty
		private String version;

		@Getter
		@Setter
		public static class Contact {

			@NotEmpty
			private String name;

			@NotEmpty
			private String email;

			@NotEmpty
			private String url;
		}
	}

	@Getter
	@Setter
	public static class Route {

		private Double distanceTimeUnit;

		private Double distanceUnit;

		private Double lateDeliveryMax;

		private Integer vehicleCapacity;

	}

}
