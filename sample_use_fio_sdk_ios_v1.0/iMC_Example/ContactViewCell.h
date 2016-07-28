//
//  ContactViewCell.h
//  Skya
//
//  Created by TranQuangSon on 6/24/16.
//  Copyright © 2016 Mideas. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <FIO_SDK/FIO_SDK.h>

@interface ContactViewCell:UITableViewCell

@property (nonatomic,strong) UIButton *btnMessage;
@property (nonatomic,strong) UIButton *btnCall;
//
-(void) setValueForCell:(Contact*)contact;

@end
