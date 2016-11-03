//
//  ViewController.h
//  iMC_SDK
//
//  Created by TranQuangSon on 6/8/16.
//  Copyright © 2016 TranQuangSon. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Contact.h"

//Constant
typedef enum {
    kmcContactStateUndefine = 0,
    kmcContactStateOnline = 1,
    kmcContactStateOffline = 2,
    kmcContactStateNotSupport = 7,
    kmcContactStateNotContactExist = 8,
} kmcContactState;

//Blocks
typedef void (^normalBlock)(BOOL success,NSError *error);
typedef void (^handleBlock)(BOOL success,NSDictionary *userInfo,NSError *error);
typedef void (^contacthandleBlock)(BOOL success,NSArray *userInfo,NSError *error);

@protocol FIOManagerAppDelegate <NSObject>

@optional
//Contact
-(void) FIOManagerAppDelegate_NewContact:(Contact *)Contact;
-(void) FIOManagerAppDelegate_DeactiveContact:(Contact *)Contact;
//My user
-(void) FIOManagerAppDelegate_ConflitAccount;

@end

@interface FIO_Manager : UIResponder
//Add and remove this delegate for events supported
- (void)addDelegate:(id)delegate delegateQueue:(dispatch_queue_t)delegateQueue;
- (void)removeDelegate:(id)delegate delegateQueue:(dispatch_queue_t)delegateQueue;
- (void)removeDelegate:(id)delegate;

//Setup
+ (instancetype)sharedInstance;
- (void)setupComponents;


//Methods -  Connections
+ (BOOL)checkConnect;
+ (void)connectIfneed;
- (void)connectToServer;
- (void)disconnectToServer;

//Methods -  My user
- (void)registerUserIfNeeded:(NSString *)strUsername userCredentials:(NSString *)strToken displayName:(NSString *)strDisplayName  withHandler:(handleBlock)comppeleHandler;
- (void)deactiveAccount:(normalBlock)comppeleHandler;


//Methods - Contact
- (NSDictionary *)getCurrentUserInfo;
- (void)getMyListContact:(contacthandleBlock)comppeleHandler;
- (void)getUserInfo:(NSString *)strUsername withHandler:(contacthandleBlock)comppeleHandler;
- (void)saveContact:(Contact *)contact withHandler:(normalBlock)comppeleHandler;

//Views
- (UIViewController *)callToUser:(NSString *)userName;
- (UIViewController *)messageToUser:(NSString *)userName;
//- (UIViewController *)getLoginView;
- (UIViewController *)getMessageHistoryView;
- (UIViewController *)getCallHistoryView;
- (UIViewController *)getSettingView;
- (UIViewController *)getSocialView;

//Other (Not using)
- (void)receiveNotification:(id)notificationObject;

//Utils
+ (NSString*)getUserNameFromID:(NSString*)strID;

//Applegate Event mapping (Call it in your Applegate)
- (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions;
- (void)application:(UIApplication*)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData*)deviceToken;
- (void)application:(UIApplication*)application didFailToRegisterForRemoteNotificationsWithError:(NSError*)error;
- (void)application:(UIApplication*)application receivePushRemoteNotification:(NSDictionary*)userInfo;
- (void)application:(UIApplication*)application didReceiveRemoteNotification:(NSDictionary*)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler;
- (void)application:(UIApplication*)app didReceiveLocalNotification:(UILocalNotification*)localNotification;
- (void)applicationWillResignActive:(UIApplication *)application;
- (void)applicationDidEnterBackground:(UIApplication *)application;
- (void)applicationWillEnterForeground:(UIApplication *)application;
- (void)applicationDidBecomeActive:(UIApplication *)application;
- (void)applicationWillTerminate:(UIApplication *)application;

//Variables (Utils & Helper)
@property (nonatomic, copy) handleBlock currentHandleBlock;
//Setup for project  (Get it when register at https://mideasvn.com/developers/keys
@property (nonatomic, strong) NSString *appID;
@property (nonatomic, strong) NSString *publicKey;
@property (nonatomic, strong) NSString *googlemapKey;

//Token for current user login & cheme identify each app
@property (nonatomic, strong,readonly) NSString *iMC_Token;
@property (nonatomic, strong,readonly) NSString *iMC_Scheme;

//Setup it because SDK need process some case
@property (nonatomic, strong) UIWindow *rootWindow;
@property (nonatomic, strong) UIViewController *rootViewController;


@end
