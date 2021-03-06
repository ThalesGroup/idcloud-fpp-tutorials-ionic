import { Component } from '@angular/core';
import { Platform } from '@ionic/angular';
import { AndroidPermissions } from '@ionic-native/android-permissions/ngx';

declare var GahPlugin: any;

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  constructor(private androidPermissions: AndroidPermissions, private platform: Platform) {
    // initialize gah plugin
    this.platform.ready().then(()=> {
      GahPlugin.initialize("https://demo-signal-collector.rnd.gemaltodigitalbankingidcloud.com/api/v1/tenants/mybank9/signals", 
      "2rj4semg", 
      "h-sdk.online-metrix.net", function(result) {
        alert("Success: " + result);
        
        // start prefetch of signals
        GahPlugin.startPrefetch(function(result) {
        console.log("Start prefetch: " + result);
    }, function(error) {
      console.log("Error: " + error);
    });
      }, function(error) {
        alert("Error: " + error);
      });
    });
  }

  getVisitId(event) {
    // check if signals are collected
    GahPlugin.isSignalCollectionCompleted(function(result) {
      // get visit id
      GahPlugin.getVisiId(function(result) {
        alert("Visit id: " + result);
        
        // perform login etc. to bank backend
        // if transaction is critical (high ammount transfer etc.) - set transaction as critical
        // GahPlugin.setTransactionAsCritical(...)
        // login(visitID)

        // after operation perform clear transaction resources
        // GahPlugin.clearTransaction(...)
      }, function(error) {
        alert("Error: " + error);
      });
    }, function(error) {
      alert("Error: " + error);
    });
  }

  requestPermissions() {
    this.androidPermissions.checkPermission(this.androidPermissions.PERMISSION.READ_PHONE_STATE).then(
      result => {
        console.log('Has permission?',result.hasPermission);
        if (!result.hasPermission) {
          this.androidPermissions.requestPermission(this.androidPermissions.PERMISSION.READ_PHONE_STATE);
        }
      },      err => this.androidPermissions.requestPermission(this.androidPermissions.PERMISSION.READ_PHONE_STATE)
    );

    this.androidPermissions.checkPermission(this.androidPermissions.PERMISSION.READ_PHONE_NUMBERS).then(
      result => {
        console.log('Has permission?',result.hasPermission);
        if (!result.hasPermission) {
          this.androidPermissions.requestPermission(this.androidPermissions.PERMISSION.READ_PHONE_NUMBERS);
        }
      },      err => this.androidPermissions.requestPermission(this.androidPermissions.PERMISSION.READ_PHONE_NUMBERS)
    );

    this.androidPermissions.checkPermission(this.androidPermissions.PERMISSION.ACCESS_FINE_LOCATION).then(
      result => {
        console.log('Has permission?',result.hasPermission);
        if (!result.hasPermission) {
          this.androidPermissions.requestPermission(this.androidPermissions.PERMISSION.ACCESS_FINE_LOCATION);
        }
      },      err => this.androidPermissions.requestPermission(this.androidPermissions.PERMISSION.ACCESS_FINE_LOCATION)
    );
  }

  exitApplication() {
    // before exit stop prefetch of signals
    GahPlugin.stopPrefetch(function(result) {
      console.log("Success: " + result);
    }, function(error) {
      console.log("Error: " + error);
    });
  }

}
