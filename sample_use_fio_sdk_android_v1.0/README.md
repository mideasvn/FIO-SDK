# HƯỚNG DẪN TÍCH HỢP VÀ SỬ DỤNG DỊCH VỤ FIO (CALL/CHAT) TRONG ỨNG DỤNG ANDROID MOBILE
Ghi chú: Một số từ ngữ chuyên ngành sẽ không được dịch ra Tiếng Việt để giữ đúng ý nghĩa của từ.

## 1.	GIỚI THIỆU
FIO là nền tảng OTT/Message cung cấp thư viện (SDK) & API để giúp cho nhà phát triển dễ dàng tích hợp tính năng gọi điện (call) / trò chuyện (chat) vào trong các ứng dụng mobile.
FIO SDK được phát triển bởi MIDEAS Co., ltd với đội ngũ nhân viên nhiều có năm kinh nghiệm trong lĩnh vực Mobile và công nghệ.

Dưới đây là hướng dẫn chi tiết (step by step) giúp cho việc tích hợp FIO SDK vào dự án android mobile app của bạn.

Ghi chú: Để có thể chạy thử nghiệm các dự án mẫu, điều đầu tiên bạn cần phải cài đặt Android Studio (link cài đặt bên dưới): 

![](https://s32.postimg.org/xp8t7aflh/Screen_Shot_2016_07_29_at_8_47_26_AM.png)

## 2.	CÀI ĐẶT
Để cài đặt SDK lên ứng dụng của bạn, bạn cần :
- B1: Khởi tạo 1 tài khoản FIO (https://mideasvn.com/developers/signin). 
- B2: Sau khi tạo tài khoản thành công , bạn cần tạo một project . 

Khi project được tạo ra, Mideas cung cấp cho bạn :
-	AppID: ID ứng dụng
-	RSA public key & RSA private key: Một cặp khóa RSA (khóa công khai được sử dụng trên ứng dụng của bạn, chìa khóa riêng được sử dụng trên máy chủ của bạn ) .
![](https://s31.postimg.org/d3y839xkr/Untitled.png)

Các hướng dẫn sau đây sẽ giúp bạn tích hợp FIO SDK vào ứng dụng của bạn:
### 2.1	Module library:
o	Inport module library Mideas vào project
![](https://s31.postimg.org/fd2l8xiqj/Untitled.png)

o	Cài đặt trong file build.gradle:

![](https://s31.postimg.org/og30g0j9n/Screen_Shot_2016_07_26_at_2_52_01_PM.png)
### 2.2	AndroidManifest.xml
o	FIO SDK đòi hỏi một số permissions và references (gcm , google map api key ...) được cài đặt trong tập tin AndroidManifest.xml ở ứng dụng của bạn. Những điều khoản đó cho phép SDK để giám sát trạng thái mạng và nhận tin nhắn từ Google Cloud Messaging ... Dưới đây là một ví dụ với một gói com.example. Lớp Application.java của bạn phải extends FIOApplication.

![](https://s31.postimg.org/re1cvy0mj/Screen_Shot_2016_07_26_at_2_53_22_PM.png)

o	Cài đặt Google Maps API key:  FIO SDK có sử dụng Google Maps API cho tính năng chia sẽ vị trí trong chat. Cần thiết lập chứng thực từ Google Maps API tham khảo các liên kết sau đây để lấy được keys api của riêng bạn và thay thế nó trong AndroidManifest.xml: 
https://developers.google.com/maps/documentation/android-api/signup

## 3.	KẾT NỐI & TÍCH HỢP:
### 3.1	Khởi tạo dịch vụ với appId và publicKey đã đăng ký
Sử dụng appId và publicKey đã được cấp ở “Phần 2” khi đăng ký ứng dụng trên website https://mideasvn.com/developers/signin để khởi tạo dịch vụ. Ví dụ:

![](https://s31.postimg.org/3xjlrhjzv/Screen_Shot_2016_07_26_at_2_54_20_PM.png)

o	FIO SDK có một FioClient giao diện chính để tương tác với dịch vụ khác trong FIO. Bạn phải khởi tạo đối tượng FioClient (chỉ 1 lần duy nhất) trong hàm khởi tạo chính onCreate().
![](https://s31.postimg.org/51vadp2l7/Screen_Shot_2016_07_26_at_2_55_45_PM.png)

### 3.2	Listeners
o	Để có thể lắng nghe và nhận các tin nhắn cũng như cuộc gọi từ người dùng khác, bạn phải đăng ký 2 phương thức lắng nghe FioConnectListener, FioUserListener khi khởi tạo đối tượng FioClient.
![](https://s32.postimg.org/lqxa9wm9x/Screen_Shot_2016_07_26_at_2_56_26_PM.png)

### 3.3	Kết nối và chứng thực
o	Để một người dùng để chat hoặc gọi điện thoại, bạn phải xác thực lần đầu tiên sử dụng. Mideas sẽ chấp nhận một chuỗi làm ID người dùng ( UID , địa chỉ email , số điện thoại , tên người dùng , vv), mà chúng ta gọi là FioUserId.

•	Khởi đầu: Đăng ký tài khoản và nhận key chứng thực tại https://www.mideasvn.com/developers/signin
![](https://s31.postimg.org/9zt2nd9az/Screen_Shot_2016_07_26_at_2_57_22_PM.png)

•	Bước 1: Khởi tạo appID và public key (như mô tả tại mục 3.1)

•	Bước 2: Khởi động kết nối với việc sử dụng phương thức fioClient.connect(String userId, String userCredentials).

![](https://s32.postimg.org/ti77b7e7p/Screen_Shot_2016_07_26_at_2_58_13_PM.png) 

Bạn phải đăng ký lắng nghe sự kiện kết nối và chức thực FioConnectListener, các phương thức chủ yếu: connected, disconnected, connect failed...

![](https://s31.postimg.org/96rhrdy9n/Screen_Shot_2016_07_26_at_2_58_49_PM.png)

### 3.4	Lớp FioUserListener
FioUserListener phải được đăng ký ngay khi khởi tạo đối tượng FioClient. Nó sử dụng để lấy các thông tin cơ bản của người sử dụng ứng dụng, bao gồm: name, avatar... để hiển thị trên màn hình chat hoặc gọi điện.

![](https://s32.postimg.org/w4qivt9id/Screen_Shot_2016_07_26_at_2_59_35_PM.png)

Trong hàm JSONArray getListFriend() có thể return null để lấy danh sách bạn bè từ server Mideas hoặc truyền vào danh sách bạn bè của bạn như trong ví dụ.
## 4.	API HỖ TRỢ NHÀ PHÁT TRIỂN
### 4.1	Chat với 1 user
![](https://s31.postimg.org/aape8jkh7/Screen_Shot_2016_07_26_at_3_00_11_PM.png)
### 4.2	Call tới 1 user
![](https://s31.postimg.org/5km4ya8vv/Screen_Shot_2016_07_26_at_3_00_49_PM.png)
### 4.3	Tạo 1 cuộc chat nhóm
![](https://s31.postimg.org/3rntnxq0b/Screen_Shot_2016_07_26_at_3_01_30_PM.png)
### 4.4	Lịch sử đã chat
![](https://s31.postimg.org/gkbxnv1m3/Screen_Shot_2016_07_26_at_3_02_02_PM.png)
### 4.5	Lịch sử cuộc gọi
![](https://s31.postimg.org/gnryyp0bf/Screen_Shot_2016_07_26_at_3_02_53_PM.png)
### 4.6	Danh bạ người dùng
![](https://s31.postimg.org/med3f9jzf/Screen_Shot_2016_07_26_at_3_03_23_PM.png)
### 4.7	Cài đặt
![](https://s31.postimg.org/6arfo23pn/Screen_Shot_2016_07_26_at_3_03_54_PM.png)
### 4.8	Thông tin 1 user
![](https://s32.postimg.org/lb6svw885/Screen_Shot_2016_07_26_at_3_04_27_PM.png)
### 4.9	Dừng kết nối tới server
Dừng kết nối tới server FIO service và thoát khỏi hệ thống 
![](https://s32.postimg.org/670b5r0x1/Screen_Shot_2016_07_26_at_3_04_52_PM.png)



