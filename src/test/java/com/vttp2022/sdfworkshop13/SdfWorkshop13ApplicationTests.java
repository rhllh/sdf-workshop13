package com.vttp2022.sdfworkshop13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.vttp2022.sdfworkshop13.controller.AddressbookController;
import com.vttp2022.sdfworkshop13.model.Contact;

// ask SB to set a random port when it starts the server
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc	// provided by SB, to make it aware of controllers in project
class SdfWorkshop13ApplicationTests {

	@LocalServerPort		// assigns random port to this
	private int port;

	@Autowired
	private AddressbookController addressbookController;

	@Autowired
	private MockMvc mockMvc;		// not the actual webpage

	@Autowired
	private TestRestTemplate restTemplate;

	// can be in argument instead of hardcoded
	private static final String TEST_URL = "http://localhost:";

	@Test
	void contextLoads() {
		assertThat(addressbookController).isNotNull();
	}

	@Test
	public void testContact() throws Exception {
		Contact c = new Contact();
		c.setEmail("2@3.com");
		c.setName("two");
		c.setPhoneNumber(1234);
		assertEquals(c.getEmail(),"2@3.com");
		assertEquals(c.getName(),"two");
		assertEquals(c.getPhoneNumber(),1234);
	}

	// @Test
	// public void testAddressbookController() {
	// 	assertThat(this.restTemplate.getForObject(TEST_URL+port+File.separator+"addressbook",
	// 							String.class)).contains("Your contact information");
	// }

	@Test
	public void testSaveAddressUsingUrlEncoded() {
		try {
			ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders
									.post(TEST_URL+port+File.separator+"addressbook")
									.contentType(MediaType.APPLICATION_FORM_URLENCODED)
									.content("name=hello&email=i@am.com&phoneNumber=1237"));
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	// public void testSaveAddressUsingRestTemplate() {
	// 	final String baseUrl = TEST_URL+port+File.separator+"addressbook";
	// 	try {
	// 		URI uri = new URI(baseUrl);
	// 		Contact contact = new Contact();
	// 		contact.setName("uhh");
	// 		contact.setEmail("uh@ah.com");
	// 		contact.setPhoneNumber(2454);
	// 		HttpHeaders headers = new HttpHeaders();
	// 		HttpEntity<Contact> request = new HttpEntity<>(contact, headers);
	// 		ResponseEntity<String> result = this.restTemplate.postForEntity(TEST_URL+port+File.separator+"addressbook",
	// 						request, String.class);
	// 		assertEquals(HttpStatus.OK, result.getStatusCode());
	// 	} catch (URISyntaxException e) {
	// 		e.printStackTrace();
	// 	}
	// }

}
