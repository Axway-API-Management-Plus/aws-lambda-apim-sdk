package com.axway.aws.lambda;

import com.vordel.circuit.DefaultFilter;
import com.vordel.common.util.PropDef;
import com.vordel.config.ConfigContext;
import com.vordel.es.EntityStoreException;
import com.vordel.mime.Body;
import com.vordel.mime.HeaderSet;

public class AWSLambdaFilter extends DefaultFilter {

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

	public Class<AWSLambdaProcessor> getMessageProcessorClass() {
		return AWSLambdaProcessor.class;
	}

	public Class getConfigPanelClass() throws ClassNotFoundException {
		// Avoid any compile or runtime dependencies on SWT and other UI
		// libraries by lazily loading the class when required.
		return Class.forName("com.axway.aws.lambda.AWSLambdaFilterUI");
	}
	
	
}