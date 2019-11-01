#import "GahPlugin.h"
#import "GAHRiskEngineIntegration.h"

@interface GahPlugin()

@property(nonatomic, strong) GAHRiskEngineIntegration* integrationEngine;

@end

@implementation GahPlugin

#pragma mark override

- (void)pluginInitialize {
    self.integrationEngine = [[GAHRiskEngineIntegration alloc] init];
}

#pragma mark public

- (void)initialize:(CDVInvokedUrlCommand*)command {
    NSString* gahUrl = (NSString*)[command.arguments objectAtIndex:0];
    NSString* tmxOrgId = (NSString*)[command.arguments objectAtIndex:1];
    NSString* tmxFpServerUrl = (NSString*)[command.arguments objectAtIndex:2];
    
    if (gahUrl == nil || gahUrl.length == 0) {
        CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing mandatory parameter: GAH URL"];
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }
    
    [_integrationEngine initializeGAH:gahUrl tmxOrgId:tmxOrgId tmxFpServerUrl:tmxFpServerUrl];
    
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Initialization ok"];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)visitId:(CDVInvokedUrlCommand*)command {
    [_integrationEngine requestVisitID:self.commandDelegate callbackId:command.callbackId];
}

- (void)startPrefetch:(CDVInvokedUrlCommand*)command {
    [_integrationEngine startPrefetchingCollection];
}

- (void)stopPrefetch:(CDVInvokedUrlCommand*)command {
    [_integrationEngine stopPrefetchingCollection];
}

- (void)clearTransaction:(CDVInvokedUrlCommand*)command {
    [_integrationEngine clearResources];
}

- (void)isSignalCollectionCompleted:(CDVInvokedUrlCommand*)command {
    [_integrationEngine isSignalCollectionCompleted:self.commandDelegate callbackId:command.callbackId];
}

- (void)setTransactionAsCritical:(CDVInvokedUrlCommand*)command {
    [_integrationEngine setTransactionAsCritical];
}

@end
