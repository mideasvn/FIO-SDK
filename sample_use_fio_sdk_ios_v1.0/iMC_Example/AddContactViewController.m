//
//  AddContactViewController.m
//  Skya
//
//  Created by TranQuangSon on 6/25/16.
//  Copyright © 2016 Mideas. All rights reserved.
//

#import "AddContactViewController.h"
#import <FIO_SDK/FIO_SDK.h>

@interface AddContactViewController ()<UITextFieldDelegate>{
    UITextField *tfUsername;
    UITextField *tfDisplayName;
    //
}
@property (nonatomic, strong) UIBarButtonItem* btnCancel;


@end

@implementation AddContactViewController
@synthesize btnCancel = _btnCancel;

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.title = @"New Contact";
    self.navigationItem.leftBarButtonItem = self.btnEditConversation;
    //
    [self initView];
    // Do any additional setup after loading the view.
}

- (UIBarButtonItem*)btnEditConversation
{
    if (_btnCancel == nil) {
        
        UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
        [button setFrame:CGRectMake(-2.0, .0, 60.0, 40.0)];
        [button addTarget:self action:@selector(cancelAction) forControlEvents:UIControlEventTouchUpInside];
        [button setContentHorizontalAlignment: UIControlContentHorizontalAlignmentLeft];
        button.titleLabel.font = [UIFont systemFontOfSize:16];
        [button setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
        [button setTitle:@"Cancel" forState:UIControlStateNormal];
        
        UIView *customView = [[UIView alloc] initWithFrame:CGRectMake(0.0, 0.0, 60.0, 40.0)];
        [customView addSubview:button];
        
        _btnCancel = [[UIBarButtonItem alloc] initWithCustomView:customView];
    }
    return _btnCancel;
}

- (void)cancelAction {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)initView{
    self.view.backgroundColor = [UIColor lightGrayColor];
    //
    int change = 1;
    //Enter other username which you want added
    tfUsername = [[UITextField alloc] initWithFrame:CGRectMake(0, 70, self.view.frame.size.width, 40)];
    tfUsername.layer.borderWidth = 1.0f;
    tfUsername.layer.borderColor = [UIColor whiteColor].CGColor;
    tfUsername.placeholder = @"Username";
    tfUsername.delegate = self;
    tfUsername.text = @"fioclient1";
    [self.view addSubview:tfUsername];
    
    //Enter your Descriptions
    tfDisplayName = [[UITextField alloc] initWithFrame:CGRectMake(0, 120, self.view.frame.size.width, 40)];
    tfDisplayName.layer.borderWidth = 1.0f;
    tfDisplayName.layer.borderColor = [UIColor whiteColor].CGColor;
    tfDisplayName.placeholder = @"Description (Name or notes)";
    tfDisplayName.delegate = self;
    if(change == 0){
        tfDisplayName.text = @"son";
    }
    [self.view addSubview:tfDisplayName];

    //Add button
    UIButton *buttonAdd = [UIButton buttonWithType:UIButtonTypeCustom];
    buttonAdd.frame = CGRectMake(60, 170, 200, 40);
    buttonAdd.backgroundColor = [UIColor blueColor];
    buttonAdd.layer.borderWidth = 1.0f;
    buttonAdd.layer.borderColor = [UIColor whiteColor].CGColor;
    buttonAdd.layer.cornerRadius = 1.5f;
    [buttonAdd setTitle:@"Add" forState:UIControlStateNormal];
    [buttonAdd addTarget:self action:@selector(addAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:buttonAdd];

}

- (void)addAction
{
    //Resign textfields
    [tfUsername resignFirstResponder];
    [tfDisplayName resignFirstResponder];
    
    FIO_Manager *imc_Manager = [FIO_Manager sharedInstance];
    NSString *strUserName = [NSString stringWithFormat:@"%@_%@",imc_Manager.iMC_Scheme,tfUsername.text];
    //Check username is register to call/chat server. If not registed, user is nil. Else data type is NSArray because method is used for functions)
    [imc_Manager getUserInfo:strUserName withHandler:^(BOOL success,NSArray *userInfo, NSError *error){
        if (success) {
            NSLog(@"successful");
            //Check exist account
            if(userInfo && userInfo.count>0){
                Contact *contact = userInfo[0];
                if(tfDisplayName.text.length>0){
                    //Change name after save
                    contact.name = tfDisplayName.text;
                }
                //Save contact (Data will stored to DB local and server
                [imc_Manager saveContact:contact withHandler:^(BOOL success, NSError *error){
                    if (success) {
                        //If save successfully, Close Add View-controller
                        [self cancelAction];
                    }
                    else{
                        //Failed. Please check error
                         NSLog(@"Failed");
                    }
                    
                }];
                
            }
            else{
                NSLog(@"user not found");
            }
        }
        else {
            //Failed. Please check error
             NSLog(@"Failed");
        }
    }];

    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
