package com.gemalto.plugin.gah;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.gemalto.riskengine.GAHBSecConfig;
import com.gemalto.riskengine.GAHCore;
import com.gemalto.riskengine.GAHCoreConfig;
import com.gemalto.riskengine.GAHGemaltoSignalConfig;
import com.gemalto.riskengine.GAHPrefetchStatusCallback;
import com.gemalto.riskengine.GAHResponseCallback;
import com.gemalto.riskengine.GAHSignalGroupConstants;
import com.gemalto.riskengine.GAHTMXConfig;

import org.apache.cordova.CallbackContext;


/**
 * This class is holding business logic to communicate with GAHRiskEngine
 * It initiates the App backend server call post getting response from GAHRiskEngine
 */
public class GAHRiskEngineIntegration {

    private static final String TAG = GAHRiskEngineIntegration.class.getSimpleName();

    private final Context mContext;

    public GAHRiskEngineIntegration(@NonNull final Context context) {
        mContext = context;
    }

    /**
     * Initializes the GAH SDK.
     *
     * @param url
     *         GAH back end url.
     * @param tmxOrgId
     *         Thread Matrix SDK organization id.
     * @param tmxFpServerUrl
     *         Thread Matrix SDK server url.
     */
    public void initializeGAH(@NonNull final String url,
                              @Nullable final String tmxOrgId,
                              @Nullable final String tmxFpServerUrl) {
        final GAHCoreConfig coreConfig = new GAHCoreConfig.Builder(mContext, url).build();
        final GAHGemaltoSignalConfig signalConfig = new GAHGemaltoSignalConfig.Builder().build();
        final GAHBSecConfig gahbSecConfig = new GAHBSecConfig.Builder().build();

        if ((tmxOrgId != null && !tmxOrgId.isEmpty()) && (tmxFpServerUrl != null && !tmxFpServerUrl.isEmpty())) {
            final GAHTMXConfig threatMetrixConfig = new GAHTMXConfig.Builder(mContext, tmxOrgId, tmxFpServerUrl)
                    .build();
            GAHCore.initialize(coreConfig, signalConfig, threatMetrixConfig);
        } else {
            GAHCore.initialize(coreConfig, signalConfig);
        }
    }

    /**
     * Initiates pre-fetching of signals from all providers before the actual signal collection
     * This enhances the signal collection time during the actual login
     */
    public void startPrefetchCollection() {
        GAHCore.startPrefetchSignals();
    }

    /**
     * Requests the Visit ID value from GAHRiskEngine.
     *
     * @param callbackContext
     *         Callback back to javascript.
     */
    public void requestVisitId(@NonNull final CallbackContext callbackContext) {
        GAHCore.requestVisitID(new GAHResponseCallback() {
            @Override
            public void success(final String visitId) {
                Log.d("******1", "Success Callback from RESDK after getting visitID, visit id:" + visitId);
                callbackContext.success(visitId);
            }

            @Override
            public void error(final int errorCode, final String errorMessage) {
                clearTransactionData();
                callbackContext.error("Error message: " + errorMessage + " error code: " + errorCode);
            }
        });
    }

    /**
     * Checks if signal collection is completed.
     *
     * @param callbackContext
     *         Callback back to javascript.
     */
    public void isSignalCollectionCompleted(@NonNull final CallbackContext callbackContext) {
        GAHCore.requestPrefetchStatus(new GAHPrefetchStatusCallback() {
            @Override
            public void onPrefetchCompleted(final int statusCode, final String message) {
                if (statusCode == 2100) {
                    callbackContext.success(message);
                } else {
                    callbackContext.error(message);
                }
            }
        });
    }

    /**
     * Clears transaction data after every transaction
     */
    public void clearTransactionData() {
        GAHCore.clearTransactionResources();
    }

    /**
     * Stops pre-fetching of signals
     */
    public void stopPrefetchCollection() {
        GAHCore.stopPrefetchSignals();
    }

    /**
     * Sets the transaction as critical.
     */
    public void setTransactionAsCritical() {
        GAHCore.setTransactionAsCritical();
    }
}
