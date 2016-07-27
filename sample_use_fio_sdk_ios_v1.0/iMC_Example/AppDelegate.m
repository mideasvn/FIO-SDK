//
//  AppDelegate.m
//  iMC_Example
//
//  Created by TranQuangSon on 7/26/16.
//  Copyright © 2016 mideas. All rights reserved.
//

#import "AppDelegate.h"
#import <FIO_SDK/FIO_SDK.h>
#import "MainMenuViewController.h"

@interface AppDelegate ()

@end

@implementation AppDelegate
@synthesize navigationController;

+ (AppDelegate *) getInstance {
    return (AppDelegate *) [[UIApplication sharedApplication] delegate];
}


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    
    MainMenuViewController* viewController = [[MainMenuViewController alloc] initWithNibName:nil bundle:nil];
    navigationController =[[UINavigationController alloc] initWithRootViewController:viewController];
    navigationController.navigationBar.translucent=NO;
    
    self.window.rootViewController = navigationController;
    [self.window makeKeyAndVisible];
    //
    [[FIO_Manager sharedInstance] application:application didFinishLaunchingWithOptions:launchOptions];
    
    return YES;
}

- (void)application:(UIApplication*)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData*)deviceToken
{
    //Send to server
    [[FIO_Manager sharedInstance] application:application didRegisterForRemoteNotificationsWithDeviceToken:deviceToken];
    
}

- (void)application:(UIApplication*)application didFailToRegisterForRemoteNotificationsWithError:(NSError*)error
{
    [[FIO_Manager sharedInstance] application:application didFailToRegisterForRemoteNotificationsWithError:error];
}



- (void)application:(UIApplication*)application didReceiveRemoteNotification:(NSDictionary*)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler
{
    [[FIO_Manager sharedInstance] application:application didReceiveRemoteNotification:userInfo fetchCompletionHandler:completionHandler];
}


- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
    [[FIO_Manager sharedInstance] applicationWillResignActive:application];
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    [[FIO_Manager sharedInstance] applicationDidEnterBackground:application];
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    [[FIO_Manager sharedInstance] applicationWillEnterForeground:application];
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    [[FIO_Manager sharedInstance] applicationDidBecomeActive:application];
    
    
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    [[FIO_Manager sharedInstance] applicationWillTerminate:application];
}


#pragma notification

- (void)application:(UIApplication*)application didReceiveLocalNotification:(UILocalNotification*)localNotification
{
    [[FIO_Manager sharedInstance] application:application didReceiveLocalNotification:localNotification];
}


@end
