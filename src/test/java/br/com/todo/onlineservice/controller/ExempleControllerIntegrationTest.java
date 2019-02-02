package br.com.todo.onlineservice.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.todo.onlineservice.domain.RestResponsePage;
import br.com.todo.onlineservice.model.ErrorResponse;
import br.com.todo.onlineservice.model.Exemple;
import br.com.todo.onlineservice.model.FieldError;
import br.com.todo.onlineservice.repository.ExempleRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExempleControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ExempleRepository exempleRepository;

	@Before
	public void before() {
		exempleRepository.deleteAll();
	}

	@Test
	public void testCreate() {
		Exemple exemple = new Exemple("Danilo");

		ResponseEntity<Integer> response = restTemplate.postForEntity("/exemple", exemple, Integer.class);
		System.out.println(response);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
	}

	@Test
	public void testCreateWhenBadRequest() {
		ResponseEntity<ErrorResponse> response = restTemplate.postForEntity("/exemple", new Exemple(), ErrorResponse.class);

		FieldError expectedNomeField = new FieldError("name", "must not be null");

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
		assertThat(response.getBody().getMessage(), equalTo("Bad Request"));
		assertThat(response.getBody().getDetails(), equalTo("uri=/task/exemple"));
		assertThat(response.getBody().getFieldErros(), hasSize(1));
		assertThat(response.getBody().getFieldErros(), hasItems(expectedNomeField));
	}

	@Test
	public void testUpdate() {
		Exemple exemple = createCustomer();
		exemple.setName("Manoel");

		HttpEntity<Exemple> requestEntity = new HttpEntity<>(exemple);
		ResponseEntity<Void> responsePut = restTemplate.exchange("/exemple/" + exemple.getId(), HttpMethod.PUT, requestEntity, Void.class);

		assertNotNull(responsePut);
		assertNull(responsePut.getBody());
		assertThat(responsePut.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));

		ResponseEntity<Exemple> responseGet = restTemplate.getForEntity("/exemple/" + exemple.getId(), Exemple.class);

		assertNotNull(responseGet);
		assertNotNull(responseGet.getBody());
		assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
		assertEquals(exemple.getId(), responseGet.getBody().getId());
		assertEquals(exemple.getName(), responseGet.getBody().getName());
	}

	@Test
	public void testUpdateWhenBadRequest() {
		Exemple exemple = createCustomer();

		HttpEntity<Exemple> requestEntity = new HttpEntity<>(new Exemple());
		ResponseEntity<ErrorResponse> response = restTemplate.exchange("/exemple/" + exemple.getId(), HttpMethod.PUT, requestEntity, ErrorResponse.class);

		FieldError expectedNomeField = new FieldError("name", "must not be null");

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
		assertThat(response.getBody().getMessage(), equalTo("Bad Request"));
		assertThat(response.getBody().getDetails(), equalTo("uri=/task/exemple/" + exemple.getId()));
		assertThat(response.getBody().getTimestamp(), not(nullValue()));
		assertThat(response.getBody().getFieldErros(), hasSize(1));
		assertThat(response.getBody().getFieldErros(), hasItems(expectedNomeField));
	}

	@Test
	public void testUpdateWhenRecordNotFound() {
		Exemple exemple = new Exemple("Silva");

		HttpEntity<Exemple> requestEntity = new HttpEntity<>(exemple);
		ResponseEntity<Void> responsePut = restTemplate.exchange("/exemple/231231", HttpMethod.PUT, requestEntity, Void.class);

		assertNotNull(responsePut);
		assertNull(responsePut.getBody());
		assertThat(responsePut.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testGetById() {
		Exemple exemple = new Exemple("da");

		ResponseEntity<Integer> responsePost = restTemplate.postForEntity("/exemple", exemple, Integer.class);

		assertNotNull(responsePost);
		assertNotNull(responsePost.getBody());
		assertThat(responsePost.getStatusCode(), equalTo(HttpStatus.CREATED));

		ResponseEntity<Exemple> responseGet = restTemplate.getForEntity("/exemple/" + responsePost.getBody(), Exemple.class);

		assertNotNull(responseGet);
		assertNotNull(responseGet.getBody());
		assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
		assertEquals(responsePost.getBody(), responseGet.getBody().getId());
		assertEquals(exemple.getName(), responseGet.getBody().getName());
	}

	@Test
	public void testGetByIdWhenRecordNotFound() {
		ResponseEntity<Void> responseGet = restTemplate.getForEntity("/exemple/45253", Void.class);

		assertNotNull(responseGet);
		assertNull(responseGet.getBody());
		assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testSearch() {
		exempleRepository.deleteAll();

		List<Exemple> exemples = new ArrayList<>();
		exemples.add(new Exemple("Danilo"));
		exemples.add(new Exemple("Manoel"));
		exemples.add(new Exemple("Oliveira"));

		exemples.forEach((exemple) -> {
			ResponseEntity<Integer> responsePost = restTemplate.postForEntity("/exemple", exemple, Integer.class);

			assertNotNull(responsePost);
			assertNotNull(responsePost.getBody());
			assertThat(responsePost.getStatusCode(), equalTo(HttpStatus.CREATED));
		});

		Pageable pageable = PageRequest.of(0, 2);

		ParameterizedTypeReference<RestResponsePage<Exemple>> responseType = new ParameterizedTypeReference<RestResponsePage<Exemple>>() {};
		String url = String.format("/exemple?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize());

		ResponseEntity<RestResponsePage<Exemple>> responseGet = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

		assertNotNull(responseGet);
		assertNotNull(responseGet.getBody());
		assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
		assertEquals(exemples.size(), responseGet.getBody().getTotalElements());
		assertEquals(pageable.getPageSize(), responseGet.getBody().getContent().size());
	}

	@Test
	public void testSearchWhenNoResult() {
		exempleRepository.deleteAllInBatch();

		Pageable pageable = PageRequest.of(0, 2);

		ParameterizedTypeReference<RestResponsePage<Exemple>> responseType = new ParameterizedTypeReference<RestResponsePage<Exemple>>() {};
		String url = String.format("/exemple?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize());

		ResponseEntity<RestResponsePage<Exemple>> responseGet = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

		assertNotNull(responseGet);
		assertNotNull(responseGet.getBody());
		assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
		assertEquals(0, responseGet.getBody().getTotalElements());
		assertEquals(0, responseGet.getBody().getContent().size());
	}

	private Exemple createCustomer() {
		Exemple exemple = new Exemple("Danilo");

		ResponseEntity<Integer> responsePost = restTemplate.postForEntity("/exemple", exemple, Integer.class);

		assertNotNull(responsePost);
		assertNotNull(responsePost.getBody());
		assertThat(responsePost.getStatusCode(), equalTo(HttpStatus.CREATED));

		exemple.setId(responsePost.getBody());

		return exemple;
	}

}
