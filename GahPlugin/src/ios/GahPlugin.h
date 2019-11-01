#import <Cordova/CDVPlugin.h>

/**
 * GAH cordova plugin wrapping the GAH SDK.
 */
@interface GahPlugin : CDVPlugin

/**
 * Initializes GAH SDK.
 */
- (void)initialize:(CDVInvokedUrlCommand*)command;

/**
 * Retrieves the visit id.
 */
- (void)visitId:(CDVInvokedUrlCommand*)command;

/**
 * Starts prefetch of data, caches signals. Call on applicaiton start, after GAH has been initialized.
 */
- (void)startPrefetch:(CDVInvokedUrlCommand*)command;

/**
 * Stops prefetch of data. Call before application end, or when GAH will not be further used.
 */
- (void)stopPrefetch:(CDVInvokedUrlCommand*)command;

/**
 * Clears the transaction resources. Call after each transaction (login, transfer funds etc.).
 */
- (void)clearTransaction:(CDVInvokedUrlCommand*)command;

/**
 * Checks if signal collection is completed. Needs to be called before {@code getVisiId},
 * to check if all signals are collected.
 */
- (void)isSignalCollectionCompleted:(CDVInvokedUrlCommand*)command;

/**
 * Sets the transaction as critical(high value trasfer etc.).
 */
- (void)setTransactionAsCritical:(CDVInvokedUrlCommand*)command;

@end
