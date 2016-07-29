//
//  ContactViewController.m
//  Skya
//
//  Created by TranQuangSon on 6/24/16.
//  Copyright © 2016 Mideas. All rights reserved.
//

#import "ContactViewController.h"
#import "ContactViewCell.h"
#import <FIO_SDK/FIO_SDK.h>
#import "AddContactViewController.h"

@interface ContactViewController ()<UITableViewDelegate, UITableViewDataSource>{
    UITableView *tbvContact;
    NSArray* arrayContact;
}

@property (nonatomic, strong) UIBarButtonItem* btnBack;
@property (nonatomic, strong) UIBarButtonItem* btnAddContact;
@end

@implementation ContactViewController
@synthesize btnBack = _btnBack;
@synthesize btnAddContact = _btnAddContact;
- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.navigationItem.title = @"Contacts";
    self.navigationItem.leftBarButtonItem = self.btnEditConversation;
    self.navigationItem.rightBarButtonItem = self.btnAddContact;
    //
    [self initView];
    //
    [self addNotifications];
    //
    [self loadData];
}

- (void)initView{
    //Views
    tbvContact =[[UITableView alloc] initWithFrame:CGRectMake(0,0 , self.view.frame.size.width, [[UIScreen mainScreen] bounds].size.height-44-20)];
    tbvContact.dataSource = self;
    tbvContact.delegate = self;
    [self.view addSubview:tbvContact];
}

- (void)loadData{
    //Get contacts has added. For first time, it get from server
    [[FIO_Manager sharedInstance] getMyListContact:^(BOOL success,NSArray *userInfo, NSError *error){
        //
        dispatch_async(dispatch_get_main_queue(), ^{
            arrayContact = userInfo;
            [tbvContact reloadData];
        });
        
    }];
}

#pragma mark - Navigation's actions
- (UIBarButtonItem*)btnEditConversation
{
    if (_btnBack == nil) {
        UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
        [button setFrame:CGRectMake(-2.0, .0, 40.0, 40.0)];
        [button addTarget:self action:@selector(backAction) forControlEvents:UIControlEventTouchUpInside];
        [button setContentHorizontalAlignment: UIControlContentHorizontalAlignmentLeft];
        button.titleLabel.font = [UIFont systemFontOfSize:16];
        [button setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
        [button setImage:[UIImage imageNamed:@"back_btn"] forState:UIControlStateNormal];
        UIView *customView = [[UIView alloc] initWithFrame:CGRectMake(0.0, 0.0, 40.0, 40.0)];
        [customView addSubview:button];
        
        _btnBack = [[UIBarButtonItem alloc] initWithCustomView:customView];
    }
    return _btnBack;
}

- (UIBarButtonItem*)btnAddContact
{
    if (_btnAddContact == nil) {
        UIButton* button = [UIButton buttonWithType:UIButtonTypeCustom];
        [button setFrame:CGRectMake(2.0, 2.0, 40, 40)];
        [button addTarget:self action:@selector(addNewContact) forControlEvents:UIControlEventTouchUpInside];
        UIImage* image = [UIImage imageNamed:@"contact_add"];
        [button setImage:image forState:UIControlStateNormal];
        [button setContentHorizontalAlignment: UIControlContentHorizontalAlignmentLeft];
        UIView *customView = [[UIView alloc] initWithFrame:CGRectMake(0.0, 0.0, 40.0, 40.0)];
        [customView addSubview:button];
        
        _btnAddContact = [[UIBarButtonItem alloc] initWithCustomView:customView];
    }
    
    return _btnAddContact;
}

- (void)backAction
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)addNewContact
{
    // show AddContact view (Push or present)
    AddContactViewController *addContactViewController = [[AddContactViewController alloc] initWithNibName:nil bundle:nil];
    UINavigationController *navBar = [[UINavigationController alloc] initWithRootViewController:addContactViewController];
    UIImage *imgNavigationBarBackground = nil;
    imgNavigationBarBackground = [UIImage imageNamed:@"header_bg_new"];
    [navBar.navigationBar setBackgroundImage:imgNavigationBarBackground forBarMetrics:UIBarMetricsDefault];
    [self.navigationController presentViewController:navBar animated:YES completion:nil];
}


#pragma mark - UITableView's datasources and delegates

-(NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return arrayContact.count;
}

- (CGFloat)tableView:(UITableView*)tableView heightForRowAtIndexPath:(NSIndexPath*)indexPath
{
    return 100;
}


-(UITableViewCell*) tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellID = @"ContactViewCellID";
    ContactViewCell *cell = (ContactViewCell*)[tableView dequeueReusableCellWithIdentifier:cellID];
    if(cell == nil)
    {
        cell = [[ContactViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellID];
        
    }
    cell.selectionStyle = UITableViewCellSelectionStyleGray;
    
    Contact *contact = [arrayContact objectAtIndex:indexPath.row];
    [cell setValueForCell:contact];
    //
    [cell.btnCall addTarget:self action:@selector(callAction:) forControlEvents:UIControlEventTouchUpInside];
    cell.btnCall.tag = indexPath.row;
    //
    [cell.btnMessage addTarget:self action:@selector(messageAction:) forControlEvents:UIControlEventTouchUpInside];
    cell.btnMessage.tag = indexPath.row;
    
    return cell;
}

#pragma mark - contacting 's actions

- (void)messageAction:(id)sender{
    UIButton *button = sender;
    int index = button.tag;
    //Get selected contact
    Contact *contact = [arrayContact objectAtIndex:index];
    //Request data and continue with action
    FIO_Manager *imc_Manager = [FIO_Manager sharedInstance];
    //Check username is register to call/chat server. If not registed, user is nil. Else data type is NSArray because method is used for functions)
    [imc_Manager getUserInfo:contact.phone_number_list withHandler:^(BOOL success,NSArray *userInfo, NSError *error){
        if (success) {
            NSLog(@"successful");
            //Check exist account
            if(userInfo && userInfo.count>0){
                //Get ViewController (Push or present or add to window)
                UIViewController *callView = [imc_Manager messageToUser:contact.phone_number_list];
                UINavigationController* rootNav = self.navigationController;
                rootNav.navigationBarHidden = YES;
                [rootNav pushViewController:callView animated:NO];
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

- (void)callAction:(id)sender{
    UIButton *button = sender;
    int index = button.tag;
    //Get selected contact
    Contact *contact = [arrayContact objectAtIndex:index];
    //
    FIO_Manager *imc_Manager = [FIO_Manager sharedInstance];
    //Check username is register to call/chat server. If not registed, user is nil. Else data type is NSArray because method is used for functions)
    [imc_Manager getUserInfo:contact.phone_number_list withHandler:^(BOOL success,NSArray *userInfo, NSError *error){
        if (success) {
            NSLog(@"successful");
            //Check exist account
            if(userInfo && userInfo.count>0){
                //Get ViewController (Push or present or add to window)
                UIViewController *callView = [imc_Manager callToUser:contact.phone_number_list];
                UINavigationController* rootNav = self.navigationController;
                rootNav.navigationBarHidden = YES;
                [rootNav pushViewController:callView animated:NO];
            }
            else{
                NSLog(@"user not found");
            }
        }
        else {
            // hien thi thong bao
            NSLog(@"that bai");
            
            
        }
    }];
    
}


- (void)addNotifications{
    //Setup iMC_Manager delegate for events (You can add this delegate for view-controllers need invoke supported delegate method)
    [[FIO_Manager sharedInstance] addDelegate:self delegateQueue:dispatch_get_main_queue()];
}

- (void)removeNotifications{
    //Remove helpers (Important!)
    [[FIO_Manager sharedInstance] removeDelegate:self delegateQueue:dispatch_get_main_queue()];
}

#pragma mark - FIOManagerAppDelegate

-(void) FIOManagerAppDelegate_NewContact:(Contact *)Contact{
    [self loadData];
}
- (void)dealloc{
    [self removeNotifications];
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
