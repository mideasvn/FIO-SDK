//
//  ChatCallViewController.m
//  hana
//
//  Created by TranQuangSon on 6/24/16.
//  Copyright © 2016 Mideas. All rights reserved.
//

#import "ChatCallViewController.h"
#import <FIO_SDK/FIO_SDK.h>

@interface ChatCallViewController ()<UITextFieldDelegate>{
    UITextField *tfUsername;
    //
}
@property (nonatomic, strong) UIBarButtonItem* btnback;

@end

@implementation ChatCallViewController
@synthesize btnback = _btnback;

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.view.backgroundColor = [UIColor lightGrayColor];
    //Action
    self.navigationItem.leftBarButtonItem = self.btnEditConversation;
    
    //Views (UI)
    [self initView];
}

// Back
- (UIBarButtonItem*)btnEditConversation
{
    if (_btnback == nil) {
        UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
        [button setFrame:CGRectMake(-2.0, .0, 40.0, 40.0)];
        [button addTarget:self action:@selector(backAction) forControlEvents:UIControlEventTouchUpInside];
        [button setContentHorizontalAlignment: UIControlContentHorizontalAlignmentLeft];
        button.titleLabel.font = [UIFont systemFontOfSize:16];
        [button setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
        [button setImage:[UIImage imageNamed:@"back_btn"] forState:UIControlStateNormal];
        //
        UIView *customView = [[UIView alloc] initWithFrame:CGRectMake(0.0, 0.0, 40.0, 40.0)];
        [customView addSubview:button];
        //
        _btnback = [[UIBarButtonItem alloc] initWithCustomView:customView];
    }
    return _btnback;
}

- (void)backAction
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)initView{
    //
    //Enter other username which you want contacting
    tfUsername = [[UITextField alloc] initWithFrame:CGRectMake(0, 20, self.view.frame.size.width, 40)];
    tfUsername.layer.borderWidth = 1.0f;
    tfUsername.layer.borderColor = [UIColor whiteColor].CGColor;
    tfUsername.placeholder = @"Connect to";
    tfUsername.delegate = self;
    tfUsername.text = @"fioclient1";
       
    [self.view addSubview:tfUsername];
    
    //Goto Message View (Chat with other username)
    UIButton *btnChat = [UIButton buttonWithType:UIButtonTypeCustom];
    btnChat.frame = CGRectMake(10, 80, 145, 40);
    btnChat.backgroundColor = [UIColor blueColor];
    btnChat.layer.borderWidth = 1.0f;
    btnChat.layer.borderColor = [UIColor whiteColor].CGColor;
    btnChat.layer.cornerRadius = 1.5f;
    [btnChat setTitle:@"Chat" forState:UIControlStateNormal];
    [btnChat addTarget:self action:@selector(chatAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btnChat];
    
    //Goto Call View (Call with other username)
    UIButton *btnCall = [UIButton buttonWithType:UIButtonTypeCustom];
    btnCall.frame = CGRectMake(165, 80, 145, 40);
    btnCall.backgroundColor = [UIColor blueColor];
    btnCall.layer.borderWidth = 1.0f;
    btnCall.layer.borderColor = [UIColor whiteColor].CGColor;
    btnCall.layer.cornerRadius = 1.5f;
    [btnCall setTitle:@"Call" forState:UIControlStateNormal];
    [btnCall addTarget:self action:@selector(callAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btnCall];
    
    //Goto HistoryChat View (Call with other username)
    UIButton *btnChatHistory = [UIButton buttonWithType:UIButtonTypeCustom];
    btnChatHistory.frame = CGRectMake(10, 130, 145, 40);
    btnChatHistory.backgroundColor = [UIColor blueColor];
    btnChatHistory.layer.borderWidth = 1.0f;
    btnChatHistory.layer.borderColor = [UIColor whiteColor].CGColor;
    btnChatHistory.layer.cornerRadius = 1.5f;
    [btnChatHistory setTitle:@"History (Message)" forState:UIControlStateNormal];
    [btnChatHistory addTarget:self action:@selector(chatHistoryAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btnChatHistory];
    
    //Goto HistoryCall View (Call with other username)
    UIButton *btnCallHistory = [UIButton buttonWithType:UIButtonTypeCustom];
    btnCallHistory.frame = CGRectMake(165, 130, 145, 40);
    btnCallHistory.backgroundColor = [UIColor blueColor];
    btnCallHistory.layer.borderWidth = 1.0f;
    btnCallHistory.layer.borderColor = [UIColor whiteColor].CGColor;
    btnCallHistory.layer.cornerRadius = 1.5f;
    [btnCallHistory setTitle:@"History (Call)" forState:UIControlStateNormal];
    [btnCallHistory addTarget:self action:@selector(callHistoryAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btnCallHistory];
}

#pragma mark - Actions
- (void)chatAction {
    FIO_Manager *imc_Manager = [FIO_Manager sharedInstance];
    //When user search a contact, please enter add scheme to username for check.
    //When goto a user detail, scheme have added to username
    NSString *strUserName = [NSString stringWithFormat:@"%@_%@",imc_Manager.iMC_Scheme,tfUsername.text];
    //Check username is register to call/chat server. If not registed, user is nil. Else data type is NSArray because method is used for functions)
    [imc_Manager getUserInfo:strUserName withHandler:^(BOOL success,NSArray *userInfo, NSError *error){
        if (success) {
            NSLog(@"Success");
            //Check exist account
            if(userInfo && userInfo.count>0){
                //Get ViewController (Push or present or add to window)
                UIViewController *callView = [imc_Manager messageToUser:strUserName];
                UINavigationController* rootNav = self.navigationController;
                rootNav.navigationBarHidden = YES;
                [rootNav pushViewController:callView animated:NO];
            }
        }
        else {
            //Failed. Please check error
            NSLog(@"Failed");
        }
    }];
    
}

- (void)callAction {
    FIO_Manager *imc_Manager = [FIO_Manager sharedInstance];
    //When user search a contact, please enter add scheme to username for check.
    //When goto a user detail, scheme have added to username
    NSString *strUserName = [NSString stringWithFormat:@"%@_%@",imc_Manager.iMC_Scheme,tfUsername.text];
    //Check username is register to call/chat server. If not registed, user is nil (Else data type is NSArray because method is used for functions)
    [imc_Manager getUserInfo:strUserName withHandler:^(BOOL success,NSArray *userInfo, NSError *error){
        if (success) {
            NSLog(@"Success");
            //Check exist account
            if(userInfo && userInfo.count>0){
                //Get ViewController (Push or present or add to window)
                UIViewController *callView = [imc_Manager callToUser:strUserName];
                UINavigationController* rootNav = self.navigationController;
                rootNav.navigationBarHidden = YES;
                [rootNav pushViewController:callView animated:NO];
            }
        }
        else {
            //Failed. Please check error
            NSLog(@"Failed");
        }
    }];
    
}

- (void)chatHistoryAction {
    FIO_Manager *imc_Manager = [FIO_Manager sharedInstance];
    //Get ViewController (Push or present or add to window)
    UIViewController *viewController = [imc_Manager getMessageHistoryView];
    [self.navigationController pushViewController:viewController animated:YES];
}

- (void)callHistoryAction {
    FIO_Manager *imc_Manager = [FIO_Manager sharedInstance];
    //Get ViewController (Push or present or add to window)
    UIViewController *viewController = [imc_Manager getCallHistoryView];
    [self.navigationController pushViewController:viewController animated:YES];
}

#pragma mark - UITextField
- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
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
