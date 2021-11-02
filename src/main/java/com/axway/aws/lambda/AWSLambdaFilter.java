package com.axway.aws.lambda;

import com.vordel.circuit.DefaultFilter;
import com.vordel.common.util.PropDef;
import com.vordel.config.ConfigContext;
import com.vordel.es.EntityStoreException;
import com.vordel.mime.Body;
import com.vordel.mime.HeaderSet;

public class AWSLambdaFilter extends DefaultFilter {

	@Override
	protected final void setDefaultPropertyDefs() {
		this.reqProps.add(new PropDef("content.body", Body.class));
		this.reqProps.add(new PropDef("http.headers", HeaderSet.class));
		genProps.add(new PropDef("aws.lambda.response",String.class));
		genProps.add(new PropDef("aws.lambda.http.status.code",Integer.class));

	}

	@Override
	public void configure(ConfigContext ctx, com.vordel.es.Entity entity) throws EntityStoreException {
		super.configure(ctx, entity);
	}

	@Override
	public Class<AWSLambdaProcessor> getMessageProcessorClass() {
		return AWSLambdaProcessor.class;
	}

}