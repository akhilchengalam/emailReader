package com.bourntec.emailreader.flows;

import org.springframework.integration.dsl.IntegrationFlow;

/**
 * @author Gopal
 *
 */
public interface ImapIntegrationFlow {

	public IntegrationFlow integrationFlow(String email, String password);

}