#import "GAHRiskEngineIntegration.h"
#import <GAHRiskEngine/GAHRiskEngine.h>
#import <GAHRiskEngine/GAHCoreConfig.h>
#import <GAHRiskEngine/GAHGemaltoSignalConfig.h>
#import <GAHRiskEngine/GAHBSecConfig.h>
#import <GAHRiskEngine/GAHTMXConfig.h>
#import <GAHRiskEngine/GAHSignalGroupConstants.h>


@implementation GAHRiskEngineIntegration

- (void) initializeGAH: (NSString*) url tmxOrgId:(NSString*) tmxOrgId tmxFpServerUrl:(NSString*) tmxFpServerUrl {
    GAHCoreConfig * reConfig = [GAHCoreConfig sharedConfigurationWithUrl:url];
    
    GAHGemaltoSignalConfig *signalConfig = [GAHGemaltoSignalConfig sharedConfiguration];
    [signalConfig setLocFetchTime:50];
    [signalConfig setLocBackgrounCollectionTime:30000];
    [signalConfig setLocAllowedFreshTime:30000];
    
    GAHBSecConfig * bsecConfig = [GAHBSecConfig sharedConfiguration];
    
    NSSet* configObejcts;
    if ((tmxOrgId != nil && tmxOrgId.length > 0) && ((tmxFpServerUrl != nil && tmxFpServerUrl.length > 0))) {
        GAHTMXConfig * tmxConfig = [GAHTMXConfig sharedConfigurationWithOrgID:tmxOrgId andFingerprintServer:tmxFpServerUrl];
        [tmxConfig setSessionTimeOut:300];
        [tmxConfig setProfileRequestTimeout:5];
        
        configObejcts = [NSSet setWithObjects:reConfig,signalConfig,bsecConfig,tmxConfig, nil];
    } else {
        configObejcts = [NSSet setWithObjects:reConfig,signalConfig,bsecConfig, nil];
    }
    
    [GAHCore initialize:configObejcts];
}

-(void)requestVisitID:(id <CDVCommandDelegate>) delegate callbackId:(NSString*) callbackId {
    [GAHCore requestVisitID:^(NSString *visitID) {
        CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:visitID];
        [delegate sendPluginResult:result callbackId:callbackId];
    } failure:^(NSInteger errorCode, NSString *errorMessage) {
        NSString* error = [NSString stringWithFormat:@"Error message: %@, error code: %lu", errorMessage, (long) errorCode];
        CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error];
        [delegate sendPluginResult:result callbackId:callbackId];
    }];
}

- (void)startPrefetchingCollection {
    [GAHCore startPrefetchSignals];
}

- (void)stopPrefetchingCollection {
    [GAHCore stopPrefetchSignals];
}

- (void)setTransactionAsCritical {
    
    [GAHCore setTransactionAsCritical];
}

- (void)isSignalCollectionCompleted:(id <CDVCommandDelegate>) delegate callbackId:(NSString*) callbackId {
    [GAHCore requestPrefetchStatus:^(NSInteger statusCode, NSString *statusMessage) {
        if (statusCode == 2100) {
            CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:statusMessage];
            [delegate sendPluginResult:result callbackId:callbackId];
        } else {
            CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:statusMessage];
            [delegate sendPluginResult:result callbackId:callbackId];
        }
    }];
}

-( void)clearResources {
    [GAHCore clearTransactionResources];
}

@end

