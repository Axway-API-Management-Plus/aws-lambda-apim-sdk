package com.axway.aws.lambda;

import java.security.GeneralSecurityException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.vordel.circuit.CircuitAbortException;
import com.vordel.circuit.Message;
import com.vordel.circuit.MessageProcessor;
import com.vordel.circuit.aws.AWSFactory;
import com.vordel.config.Circuit;
import com.vordel.config.ConfigContext;
import com.vordel.el.Selector;
import com.vordel.es.Entity;
import com.vordel.es.EntityStoreException;
import com.vordel.trace.Trace;

public class AWSLambdaProcessor extends MessageProcessor {
	
	private String functionName;
	private AWSLambda awsLambda;
	
	private Selector<String> contentBody = new Selector<>("${content.body}", String.class);

	public AWSLambdaProcessor() {
	}

	@Override
	public void filterAttached(ConfigContext ctx, com.vordel.es.Entity entity) throws EntityStoreException {
		super.filterAttached(ctx, entity);
		ProfileCredentialsProvider profileCredentialsProvider;
		Selector<String> profileName = new Selector<>(entity.getStringValue("awsProfileName"), String.class);
		if(profileName == null){
			profileCredentialsProvider = new ProfileCredentialsProvider();
		}else {
			profileCredentialsProvider = new ProfileCredentialsProvider(profileName.getLiteral());
		}
		awsLambda = AWSLambdaClientBuilder.standard().withCredentials(profileCredentialsProvider).build();
		functionName = new Selector<>(entity.getStringValue("functionName"), String.class).getLiteral();

	}


	@Override
	public boolean invoke(Circuit arg0, Message msg) throws CircuitAbortException {
		
		String body = contentBody.substitute(msg);
		InvokeRequest invokeRequest = new InvokeRequest();
		invokeRequest.setPayload(body);
		invokeRequest.setFunctionName(functionName);
		try{
			InvokeResult invokeResult  = awsLambda.invoke(invokeRequest);
			String response = new String(invokeResult.getPayload().array(), "UTF-8");
			int statusCode = invokeResult.getStatusCode();
			msg.put("aws.lambda.response", response);
			msg.put("aws.lambda.http.status.code", statusCode);
			return true;
			
		}catch (Exception e) {
			Trace.error(e);
			return false;
		}

	}

}
