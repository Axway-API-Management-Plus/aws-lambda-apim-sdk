package com.axway.aws.lambda;

import com.vordel.client.manager.Manager;
import com.vordel.client.manager.filter.DefaultGUIFilter;
import com.vordel.es.ESPK;
import com.vordel.es.Entity;
import com.vordel.es.EntityStoreException;

public class AWSLambdaFilterUI extends DefaultGUIFilter  {

	private static String TYPE = "AWSLambdaFilter";
	@Override
	public void childAdded(Entity child) {
		final String typeName = child.getType().getName();
		if (typeName.equalsIgnoreCase(TYPE)) {
			// reconfigure parent entity filter
			entityUpdated(getEntity());
		}
	}

	@Override
	public void childDeleted(ESPK parentPK, ESPK childPK) {
		Entity child = null;
		try {
			child = Manager.getInstance().getSelectedEntityStore().
					getSolutionPack().getStore().getEntity(childPK);
			final String typeName = child.getType().getName();
			if (typeName.equalsIgnoreCase(TYPE)) {
				// reconfigure parent entity filter
				entityUpdated(getEntity());
			}
		} catch (EntityStoreException e) {
			// force the update
			entityUpdated(getEntity());
		}
	}

	@Override
	public void childUpdated(Entity child) {
		final String typeName = child.getType().getName();
		if (typeName.equalsIgnoreCase(TYPE)) {
			// reconfigure parent entity filter
			entityUpdated(getEntity());
		}
	}
}