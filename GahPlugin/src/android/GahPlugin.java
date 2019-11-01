package com.gemalto.plugin.gah;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * GAH plugin wrapping the GAH SDK.
 */
public class GahPlugin extends CordovaPlugin {
    private GAHRiskEngineIntegration mEngineIntegration;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();

        mEngineIntegration = new GAHRiskEngineIntegration(cordova.getActivity().getApplicationContext());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext)
            throws JSONException {
        if (action.equals("initialize")) {
            final String gahUrl = data.getString(0);
            final String tmxOrgId = data.getString(1);
            final String tmxFpServerUrl = data.getString(2);

            if (gahUrl == null || gahUrl.isEmpty()) {
                callbackContext.error("Missing mandatory parameter: GAH URL");
                return false;
            }

            mEngineIntegration.initializeGAH(gahUrl, tmxOrgId, tmxFpServerUrl);
            callbackContext.success("initialization ok");
        } else if (action.equals("visitId")) {
            mEngineIntegration.requestVisitId(callbackContext);
        } else if (action.equals("startPrefetch")) {
            mEngineIntegration.startPrefetchCollection();
        } else if (action.equals("stopPrefetch")) {
            mEngineIntegration.stopPrefetchCollection();
            return true;
        } else if (action.equals("clearTransaction")) {
            mEngineIntegration.clearTransactionData();
            return true;
        } else if (action.equals("isSignalCollectionCompleted")) {
            mEngineIntegration.isSignalCollectionCompleted(callbackContext);
            return true;
        } else if (action.equals("setTransactionAsCritical")) {
            mEngineIntegration.setTransactionAsCritical();
        } else {
            callbackContext.error("undefined action");
            return false;
        }

        return true;
    }
}
