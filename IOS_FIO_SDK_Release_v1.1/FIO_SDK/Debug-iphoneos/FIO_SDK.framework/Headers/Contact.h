//
//  Contact.h

//
//  Created by TranQuangSon on 2/20/16.
//  Copyright © Mideas. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@interface Contact : NSManagedObject

@property (nullable, nonatomic, retain) NSNumber *item_id;
@property (nullable, nonatomic, retain) NSNumber *contact_id;
@property (nullable, nonatomic, retain) NSString *name;
@property (nullable, nonatomic, retain) NSString *local_avatar_uri;
@property (nullable, nonatomic, retain) NSString *avatar_uri;
@property (nullable, nonatomic, retain) NSString *status;
@property (nullable, nonatomic, retain) NSNumber *state;
@property (nullable, nonatomic, retain) NSString *phone_number_list;
@property (nullable, nonatomic, retain) NSString *reeng_phone_number_list;
@property (nullable, nonatomic, retain) NSString *owner_phone_number;
@property (nullable, nonatomic, retain) NSNumber *contact_type;
@property (nullable, nonatomic, retain) NSNumber *is_block;
@property (nullable, nonatomic, retain) NSNumber *is_favorite;
@property (nullable, nonatomic, retain) NSNumber *modify;
@property (nullable, nonatomic, retain) NSNumber *last_change_avatar;
@property (nullable, nonatomic, retain) NSNumber *timestamp;
@property (nullable, nonatomic, retain) NSString *name_index;
@property (nullable, nonatomic, retain) NSString *language;
@property (nullable, nonatomic, retain) NSNumber *is_notify_chat;

- (void)initWithPhoneNumber:(nonnull NSString *)phoneNumber;

@end

