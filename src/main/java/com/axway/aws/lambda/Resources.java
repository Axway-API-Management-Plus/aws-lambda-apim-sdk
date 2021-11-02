package com.axway.aws.lambda;

import org.eclipse.osgi.util.NLS;

public class Resources extends NLS {

    private static final String BUNDLE_NAME = "com.axway.aws.lambda.resources"; //$NON-NLS-1$

    public static String AWS_LAMBDA_PAGE;
    public static String AWS_LAMBDA_PAGE_DESCRIPTION;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Resources.class);
    }

    private Resources() {
        super();
    }
}