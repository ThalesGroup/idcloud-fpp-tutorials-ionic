#import <Foundation/Foundation.h>
#import <GAHRiskEngine/GAHRiskEngine.h>
#import <GAHRiskEngine/GAHGemaltoSignalConfig.h>
#import <GAHRiskEngine/BehavioSecIOSSDK.h>
#import <Cordova/CDVPlugin.h>

/**
 * This class is holding business logic to communicate with GAHRiskEngine
 * It initiates the App backend server call post getting response from GAHRiskEngine
 */
@interface GAHRiskEngineIntegration : NSObject

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
- (void) initializeGAH: (NSString*) url tmxOrgId:(NSString*) tmxOrgId tmxFpServerUrl:(NSString*) tmxFpServerUrl;

/**
 * Clears transaction data after every transaction
 */
-(void)clearResources;

/*!
 * startPrefetchingCollections - Starts all provider signal collection prefetch
 */
- (void)startPrefetchingCollection;

/**
 * Stops pre-fetching of signals
 */
- (void)stopPrefetchingCollection;

/**
 * Requests the Visit ID value from GAHRiskEngine.
 *
 * @param delegate
 *         Callback back to javascript.
 * @param callbackId Callback id.
 */
-(void)requestVisitID:(id <CDVCommandDelegate>) delegate callbackId:(NSString*) callbackId;


/**
 * Sets the transaction as critical.
 */
- (void)setTransactionAsCritical;

/**
 * Checks if signal collection is completed.
 *
 * @param delegate
 *         Callback back to javascript.
 * @param callbackId Callback id.
 */
- (void)isSignalCollectionCompleted:(id <CDVCommandDelegate>) delegate callbackId:(NSString*) callbackId;

@end

