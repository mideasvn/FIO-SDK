//
//  TestSDKViewController.m
//  Skya
//
//  Created by TranQuangSon on 6/24/16.
//  Copyright © 2016 Mideas. All rights reserved.
//

#import "MainMenuViewController.h"
#import "ChatCallViewController.h"
#import "ContactViewController.h"
#import "AppDelegate.h"
#import <FIO_SDK/FIO_SDK.h>

#define APP_ID  @"2L3NRJ1BDF4O8P8FQB3Q7JSTRAHM23UJ"; //Verify your app (https://mideasvn.com/developers/keys)
#define PUBLIC_KEY @"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkVnYxkfM5nwt71q/QC5OURLNk6gs/P/WqwSiHwBSq5XhLNuYR+3ahPnUSZ9ZEiRmjBrdRgyOMtsvTMyRtSAFhTWn5v0qgJWohUzk+GnpHDBSLC8g00AFXUo0USjkkRdQhtaTnO6IFdTXyHTAyACMv41CMSgVQgIyjvc/7csU3wel5HbaVxdM6iO7HCDwvsub+q1gRVuKLRxJ9KCn6xxdfHLEfcQtijOlwL+qhrbkLuvMW5T1+shF/of/Df4KFq4qVxFHy42YjRVGf8r/difa0d3sNbuFB7h8EGcc6yiqnoEco5k5e2zVQYoCYadwHDQWQQdUp1F44mwtSCXz7Rjr0QIDAQAB" //Descrypt when send authentications to server (https://mideasvn.com/developers/keys)
#define GOOGLE_MAP_KEY_DEMO @"AIzaSyCA_ePSw7utTn28eQmM0BLnYT2PXuVMcPA"

@interface MainMenuViewController ()<UITextFieldDelegate>{
    //Views
    UITextField *tfUsername;
    UITextField *tfConnectTo;
    UIButton *btConnect;
}
@end

@implementation MainMenuViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor lightGrayColor];
    self.navigationItem.title = @"Menu";
    //Init (UI)
    [self initView];
}

- (void)initView{
    // Enter you username (ID in your system) for register to call/chat server
    tfUsername = [[UITextField alloc] initWithFrame:CGRectMake(0, 20, self.view.frame.size.width, 40)];
    tfUsername.layer.borderWidth = 1.0f;
    tfUsername.layer.borderColor = [UIColor whiteColor].CGColor;
    tfUsername.placeholder = @"Username";
    tfUsername.delegate = self;
    tfUsername.text = @"fioclient";
    
    [self.view addSubview:tfUsername];
    
    // Connect username - ID to call/chat server
    btConnect = [[UIButton alloc] initWithFrame:CGRectMake(60, 70, 200, 40)];
    [btConnect setTitle:@"Connect" forState:UIControlStateNormal];
    [btConnect addTarget:self action:@selector(connectAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btConnect];
    
    //Goto Message/Chat View (Alter connect to server)
    UIButton *btnCallChat = [UIButton buttonWithType:UIButtonTypeCustom];
    btnCallChat.frame = CGRectMake(10, 130, 145, 40);
    btnCallChat.backgroundColor = [UIColor blueColor];
    btnCallChat.layer.borderWidth = 1.0f;
    btnCallChat.layer.borderColor = [UIColor whiteColor].CGColor;
    btnCallChat.layer.cornerRadius = 1.5f;
    [btnCallChat setTitle:@"Call/Chat" forState:UIControlStateNormal];
    [btnCallChat addTarget:self action:@selector(callchatAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btnCallChat];
    
    //
    // Goto Contact View (Alter connect to server)
    UIButton *btnContact = [UIButton buttonWithType:UIButtonTypeCustom];
    btnContact.frame = CGRectMake(165, 130, 145, 40);
    //
    btnContact.backgroundColor = [UIColor blueColor];
    btnContact.layer.borderWidth = 1.0f;
    btnContact.layer.borderColor = [UIColor whiteColor].CGColor;
    btnContact.layer.cornerRadius = 1.5f;
    [btnContact setTitle:@"Contact" forState:UIControlStateNormal];
    [btnContact addTarget:self action:@selector(contactAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btnContact];
    
    //
    // Goto Setting View (Alter connect to server)
    UIButton *btnSetting = [UIButton buttonWithType:UIButtonTypeCustom];
    btnSetting.frame = CGRectMake(10, 180, 145, 40);
    btnSetting.backgroundColor = [UIColor blueColor];
    btnSetting.layer.borderWidth = 1.0f;
    btnSetting.layer.borderColor = [UIColor whiteColor].CGColor;
    btnSetting.layer.cornerRadius = 1.5f;
    [btnSetting setTitle:@"Setting" forState:UIControlStateNormal];
    [btnSetting addTarget:self action:@selector(settingAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btnSetting];
    
    //
    // Goto Setting View (Alter connect to server)
    UIButton *btnSocial = [UIButton buttonWithType:UIButtonTypeCustom];
    btnSocial.frame = CGRectMake(165, 180, 145, 40);
    btnSocial.backgroundColor = [UIColor blueColor];
    btnSocial.layer.borderWidth = 1.0f;
    btnSocial.layer.borderColor = [UIColor whiteColor].CGColor;
    btnSocial.layer.cornerRadius = 1.5f;
    [btnSocial setTitle:@"Social" forState:UIControlStateNormal];
    [btnSocial addTarget:self action:@selector(socialAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btnSocial];
}

- (void)connectAction {
    FIO_Manager *imc_Manager = [FIO_Manager sharedInstance];
    
    //Check user' token for previous login (Deactive account or Disconnect server if needed_
    if(imc_Manager.iMC_Token.length > 0){
        //Example for Deactive account (Not recommanded using it because username will delete from message/chat server)
        [imc_Manager deactiveAccount:^(BOOL success, NSError *error){
            if (success) {
                //Disconnect to Call/Chat server
                [imc_Manager disconnectToServer];
                [btConnect setTitle:@"Connect" forState:UIControlStateNormal];
            }
            
        }];
    }
    else{
        //Setup components for Call/Chat Framework (Store local message, push Notification) and Server (Connect,Stream)
        [imc_Manager setupComponents];
        imc_Manager.appID = APP_ID;
        imc_Manager.publicKey = PUBLIC_KEY;
        imc_Manager.googlemapKey = GOOGLE_MAP_KEY_DEMO;
        //Register window/root view for events (Expire token, push views)
        AppDelegate *appDelegate = [AppDelegate getInstance];
        imc_Manager.rootWindow = appDelegate.window;
        imc_Manager.rootViewController = appDelegate.navigationController;
        
        //Register Username (ID in your system)
        //If username not register with call/chat server, it auto registered)
        //Result is token (iMC_Token) and it using for next authenticate
        
        [imc_Manager registerUserIfNeeded:tfUsername.text userCredentials:@"token" displayName:@"son" withHandler:^(BOOL success,NSDictionary *userInfo, NSError *error){
            if (success) {
                //Success. Now, goto features for your user
                NSLog(@"Success.");
                [btConnect setTitle:@"Disconnect" forState:UIControlStateNormal];
            }
            else {
                //Failed. Please check error
                NSLog(@"Failed.");
                [btConnect setTitle:@"Connect" forState:UIControlStateNormal];
            }
        }];
    }
    
}

- (void)callchatAction {
    FIO_Manager *imc_Manager = [FIO_Manager sharedInstance];
    if(imc_Manager.iMC_Token.length > 0){
        //Goto call/chat View
        ChatCallViewController *chatCallViewController = [[ChatCallViewController alloc] initWithNibName:nil bundle:nil];
        [self.navigationController pushViewController:chatCallViewController animated:YES];
    }
    else{
        NSLog(@"User not registered and authenticate to call/chat server");
    }
}

- (void)contactAction {
    FIO_Manager *imc_Manager = [FIO_Manager sharedInstance];
    if(imc_Manager.iMC_Token){
        //Goto contact View
        ContactViewController *contactViewController = [[ContactViewController alloc] initWithNibName:nil bundle:nil];
        [self.navigationController pushViewController:contactViewController animated:YES];
    }
    else{
        NSLog(@"User not registered and authenticate to call/chat server");
    }
}

- (void)settingAction {
    FIO_Manager *imc_Manager = [FIO_Manager sharedInstance];
    if(imc_Manager.iMC_Token){
        //Goto Setting View
        UIViewController *viewController = [imc_Manager getSettingView];
        [self.navigationController pushViewController:viewController animated:YES];
    }
    else{
       NSLog(@"User not registered and authenticate to call/chat server");
    }
}

- (void)socialAction {
    FIO_Manager *imc_Manager = [FIO_Manager sharedInstance];
    if(imc_Manager.iMC_Token){
        //Goto social View
        UIViewController *viewController = [imc_Manager getSocialView];
        [self.navigationController pushViewController:viewController animated:YES];
    }
    else{
        NSLog(@"User not registered and authenticate to call/chat server");
    }
}



- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
