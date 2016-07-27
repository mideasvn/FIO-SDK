//
//  AppDelegate.h
//  iMC_Example
//
//  Created by TranQuangSon on 7/26/16.
//  Copyright © 2016 mideas. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (nonatomic, strong) UINavigationController *navigationController;

+ (AppDelegate*)getInstance;

@end

