//
//  ContactViewCell.m
//  hana
//
//  Created by TranQuangSon on 6/24/16.
//  Copyright © 2016 Mideas. All rights reserved.
//

#import "ContactViewCell.h"


@interface ContactViewCell(){
    UIImageView *imvAvatar;
    UILabel *lbDescription;
    UILabel *lbPhoneNumber;
}
@end

@implementation ContactViewCell
@synthesize btnMessage;
@synthesize btnCall;


- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
        [self initComponent];
    }
    return self;
}


-(void) initComponent
{
    //Avatar
    imvAvatar = [[UIImageView alloc] initWithFrame:CGRectMake(10.0f, 10.0f, 80, 80)];
    [self.contentView addSubview:imvAvatar];
    
    //Description (Name or Notes)
    lbDescription = [[UILabel alloc] initWithFrame:CGRectMake(100.0f, 20.0f, self.frame.size.width-100-10-80-10, 20)];
    lbDescription.font = [UIFont boldSystemFontOfSize:15];
    lbDescription.textAlignment=NSTextAlignmentLeft;
    lbDescription.textColor = [UIColor blackColor];
    lbDescription.backgroundColor = [UIColor clearColor];
    [self.contentView addSubview:lbDescription];
    
    //Phone-number (Value is Phone-Number Or user-name)
    lbPhoneNumber = [[UILabel alloc] initWithFrame:CGRectMake(100.0f, 40.0f, self.frame.size.width-100-10-80-10, 20)];
    lbPhoneNumber.font = [UIFont systemFontOfSize:15];
    lbPhoneNumber.numberOfLines = 1;
    lbPhoneNumber.textAlignment=NSTextAlignmentLeft;
    lbPhoneNumber.textColor = [UIColor grayColor];
    lbPhoneNumber.backgroundColor = [UIColor clearColor];
    [self.contentView addSubview:lbPhoneNumber];
    
    //Message button
    btnMessage = [UIButton buttonWithType:UIButtonTypeCustom];
    btnMessage.frame = CGRectMake(self.frame.size.width-80-10, 10.0f, 80.0f, 35.0f);
    btnMessage.backgroundColor = [UIColor colorWithRed:122.0/255 green:190.0/255 blue:19.0/255 alpha:1.0];
    btnMessage.layer.masksToBounds = YES;
    btnMessage.layer.cornerRadius = 5;
    [btnMessage setTitle:@"Message" forState:UIControlStateNormal];
    btnMessage.titleLabel.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:btnMessage];
    
    //Call button
    btnCall = [UIButton buttonWithType:UIButtonTypeCustom];
    btnCall.frame = CGRectMake(self.frame.size.width-80-10, 55.0f, 80.0f, 35.0f);
    btnCall.backgroundColor = [UIColor colorWithRed:122.0/255 green:190.0/255 blue:19.0/255 alpha:1.0];
    btnCall.layer.masksToBounds = YES;
    btnCall.layer.cornerRadius = 5;
    [btnCall setTitle:@"Call now" forState:UIControlStateNormal];
    btnCall.titleLabel.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:btnCall];
    
    
}

//Set changed value for re-used cell
-(void) setValueForCell:(Contact*)contact{
    //Set avatar
    imvAvatar.image = [UIImage imageNamed:@"wshc_ava.png"];
    //Set Info
    lbDescription.text = contact.name;
    lbPhoneNumber.text = [FIO_Manager getUserNameFromID:contact.phone_number_list];
    
    //Check user 's state (Online or Offline)
    if([contact.state intValue] == kmcContactStateOnline){
        btnMessage.hidden = NO;
        btnCall.hidden = NO;
    }
    else{
        //User has deactive account
        btnMessage.hidden = YES;
        btnCall.hidden = YES;
        
    }
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
