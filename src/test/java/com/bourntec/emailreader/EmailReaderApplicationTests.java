package com.bourntec.emailreader;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT /*, properties = "spring.profiles.active=test"*/)
class EmailReaderApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
    private JmsTemplate jmsTemplate;

	@Rule
	EmbeddedActiveMQBroker customizedBroker = new EmbeddedActiveMQBroker();
	

	@Test
	void contextLoads() {
		ResponseEntity<String> result= this.restTemplate
				.getForEntity("http://127.0.0.1:"+port+"/process/ping", String.class);
		ResponseEntity<String> result2= this.restTemplate
				.getForEntity("http://127.0.0.1:8161/", String.class);
		Assert.assertEquals(401, result2.getStatusCodeValue());
		Assert.assertEquals(true, result2.getBody().toString().contains("Unauthorized"));
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(true, result.getBody().contains("Server is up and running."));
	}
	
	@Test
	void mysqlConnection() {
		
	}

	
	@Test
    void activeMQTest() {
        this.jmsTemplate.convertAndSend("emailreader.jmsTest", "Working Fine!");
        this.jmsTemplate.setReceiveTimeout(10000);
        assertThat(this.jmsTemplate.receiveAndConvert("emailreader.jmsTestReceiver")).isEqualTo("Working Fine!");
    }
    
}
