var exec = require('cordova/exec');

var PLUGIN_NAME = 'GahPlugin';

function GahPlugin() {
}

/**
 * Initializes GAH SDK.
 *
 * @param gahUrl GAH signal collector backend URL.
 * @param tmxOrgId Thread matrix organization id.
 * @param tmxFpServerUrl Thread matrix server URL.
 * @param callback Success callback.
 * @param errorCallback Error callback.
 */
GahPlugin.prototype.initialize = function (gahUrl, tmxOrgId, tmxFpServerUrl, callback, errorCallback) {
    exec(callback, errorCallback, PLUGIN_NAME, 'initialize', [gahUrl, tmxOrgId, tmxFpServerUrl]);
};

/**
 * Retrieves the visit id.
 *
 * @param callback Success callback.
 * @param errorCallback Error callback.
 */
GahPlugin.prototype.getVisiId = function (callback, errorCallback) {
    exec(callback, errorCallback, PLUGIN_NAME, 'visitId', []);
};

/**
 * Starts prefetch of data, caches signals. Call on applicaiton start, after GAH has been initialized
 */
GahPlugin.prototype.startPrefetch = function (callback, errorCallback) {
    exec(callback, errorCallback, PLUGIN_NAME, 'startPrefetch', []);
};

/**
 * Stops prefetch of data. Call before application end, or when GAH will not be further used.
 */
GahPlugin.prototype.stopPrefetch = function (callback, errorCallback) {
    exec(callback, errorCallback, PLUGIN_NAME, 'stopPrefetch', []);
};

/**
 * Clears the transaction resources. Call after each transaction (login, transfer funds etc.).
 */
GahPlugin.prototype.clearTransaction = function (callback, errorCallback) {
    exec(callback, errorCallback, PLUGIN_NAME, 'clearTransaction', []);
};

/**
 * Checks if signal collection is completed. Needs to be called before {@code getVisiId}, 
 * to check if all signals are collected
 */
GahPlugin.prototype.isSignalCollectionCompleted = function (callback, errorCallback) {
    exec(callback, errorCallback, PLUGIN_NAME, 'isSignalCollectionCompleted', []);
};

/**
 * Sets the transaction as critical(high value trasfer etc.).
 */
GahPlugin.prototype.setTransactionAsCritical = function (callback, errorCallback) {
    exec(callback, errorCallback, PLUGIN_NAME, 'setTransactionAsCritical', []);
};

var gahPlugin = new GahPlugin();
module.exports = gahPlugin;
