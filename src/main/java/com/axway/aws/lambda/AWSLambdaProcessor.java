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
//		Selector<String> awsRegion = new Selector<>(entity.getStringValue("region"), String.class);
//		com.vordel.es.Entity clientConfig = ctx.getEntity(entity.getReferenceValue("clientConfiguration"));
//		AWSCredentials cred = AWSFactory.getCredentials(ctx, entity);
//		ClientConfiguration clientConfiguration = createClientConfiguration(ctx, clientConfig);
//		String awsRegionStr =   awsRegion.getLiteral();
//		awsLambda = AWSLambdaClientBuilder.standard().withClientConfiguration(clientConfiguration).withRegion(Regions.fromName(awsRegionStr))
//		        .withCredentials(new AWSStaticCredentialsProvider(cred)).build();
		awsLambda = AWSLambdaClientBuilder.standard().withCredentials(profileCredentialsProvider).build();
		functionName = new Selector<>(entity.getStringValue("functionName"), String.class).getLiteral();

	}
	
//	private ClientConfiguration createClientConfiguration(ConfigContext ctx, Entity entity) throws EntityStoreException {
//		ClientConfiguration clientConfig = new ClientConfiguration();
//		if (entity == null) {
//			Trace.debug("using empty default ClientConfiguration");
//			return clientConfig;
//		}
//		if (containsKey(entity, "connectionTimeout"))
//			clientConfig.setConnectionTimeout(entity.getIntegerValue("connectionTimeout"));
//		if (containsKey(entity, "maxConnections"))
//			clientConfig.setMaxConnections(entity.getIntegerValue("maxConnections"));
//		if (containsKey(entity, "maxErrorRetry"))
//			clientConfig.setMaxErrorRetry(entity.getIntegerValue("maxErrorRetry"));
//		if (containsKey(entity, "protocol"))
//			clientConfig.setProtocol(Protocol.valueOf(entity.getStringValue("protocol")));
//		if (containsKey(entity, "proxyDomain"))
//			clientConfig.setProxyDomain(entity.getStringValue("proxyDomain"));
//		if (containsKey(entity, "proxyHost"))
//			clientConfig.setProxyHost(entity.getStringValue("proxyHost"));
//
//		if (containsKey(entity, "proxyPassword")) {
//			try {
//				byte[] password = entity.getEncryptedValue("proxyPassword");
//				String proxyPassword = new String(ctx.getCipher().decrypt(password));
//				clientConfig.setProxyPassword(proxyPassword);
//			} catch (GeneralSecurityException e) {
//				Trace.error(e);
//			}
//		}
//		if (containsKey(entity, "proxyPort"))
//			clientConfig.setProxyPort(entity.getIntegerValue("proxyPort"));
//		if (containsKey(entity, "proxyUsername"))
//			clientConfig.setProxyUsername(entity.getStringValue("proxyUsername"));
//		if (containsKey(entity, "proxyWorkstation"))
//			clientConfig.setProxyWorkstation(entity.getStringValue("proxyWorkstation"));
//		if (containsKey(entity, "socketTimeout"))
//			clientConfig.setSocketTimeout(entity.getIntegerValue("socketTimeout"));
//		if (containsKey(entity, "userAgent"))
//			clientConfig.setUserAgentPrefix(entity.getStringValue("userAgent"));
//		if (containsKey(entity, "socketSendBufferSizeHint") &&  containsKey(entity, "socketReceiveBufferSizeHint"))
//			clientConfig.setSocketBufferSizeHints(entity.getIntegerValue("socketSendBufferSizeHint"),
//					entity.getIntegerValue("socketReceiveBufferSizeHint"));
//		return clientConfig;
//	}
//
//	private boolean containsKey(Entity entity, String fieldName) {
//		if (!entity.containsKey(fieldName))
//			return false;
//		String value = entity.getStringValue(fieldName);
//		if (value == null || value.length() == 0)
//			return false;
//		return true;
//	}

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
