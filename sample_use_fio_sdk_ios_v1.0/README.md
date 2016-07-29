# HƯỚNG DẪN TÍCH HỢP VÀ SỬ DỤNG DỊCH VỤ FIO (CALL/CHAT/SOCIAL NETWORK) TRONG ỨNG DỤNG IOS MOBILE
Ghi chú: Một số từ ngữ chuyên ngành sẽ không được dịch ra Tiếng Việt để giữ đúng ý nghĩa của từ.

## 1.	GIỚI THIỆU
FIO là nền tảng OTT/Message cung cấp thư viện (SDK) & API để giúp cho nhà phát triển dễ dàng tích hợp tính năng gọi điện (call) / trò chuyện (chat) vào trong các ứng dụng mobile.
FIO SDK được phát triển bởi MIDEAS Co., ltd với đội ngũ nhân viên nhiều có năm kinh nghiệm trong lĩnh vực Mobile và công nghệ.

Dưới đây là hướng dẫn chi tiết (step by step) giúp cho việc tích hợp FIO SDK vào dự án android mobile app của bạn.

Ghi chú: Để có thể chạy thử nghiệm các dự án mẫu, điều đầu tiên bạn cần phải cài đặt Xcode (link cài đặt bên dưới): 

![](https://s32.postimg.org/fiun82h9x/Screen_Shot_2016_07_29_at_9_25_50_AM.png)

## 2.	CÀI ĐẶT
Để cài đặt SDK lên ứng dụng của bạn, bạn cần :

B1: Khởi tạo 1 tài khoản FIO (https://www.mideasvn.com/developers). 

B2: Sau khi tạo tài khoản thành công , bạn cần tạo một project . Khi project được tạo ra, Mideas cung cấp cho bạn:
-	AppID: ID ứng dụng
-	RSA public key & RSA private key: Một cặp khóa RSA (khóa công khai được sử dụng trên ứng dụng của bạn, chìa khóa riêng được sử dụng trên máy chủ của bạn).

B3: Tạo 1 key sử dụng Google Map cho ứng dụng iOS trên trang chủ Google Developer Console > Google Map

![](https://s31.postimg.org/d3y839xkr/Untitled.png)

Các hướng dẫn sau đây sẽ giúp bạn tích hợp FIO SDK vào ứng dụng của bạn:

o	Import FIO_SDK.framework (Lưu ý: Do kiến trúc khi biên dịch của Simulator và device là khác nhau nên chúng tôi phân thành 2 thư mục để bạn có thể dễ dàng phát triển 
-	Debug-iphoneos khi build ứng dụng lên device.
-	Debug-iphonesimulator khi build ứng dụng lên simulator.

Project > Targers > General tab > Linked frameworks and Library > Add other > Trỏ tới thư mục chứa FIO_SDK.framework và chọn.

![](https://s31.postimg.org/cm6dqlzzf/Untitled.png)

o	Thêm FIO_SDK.framework vào mục Enbedded Binaries:
![](https://s31.postimg.org/uketj2d17/Untitled.png)

o	Import FIO_SDK.bundle chứa những file reources bao gồm
-	Xib file (Giao diện các màn hình liên quan)
-	.png file (Bạn có thể chỉnh sửa icon giao diện,sticker bằng cách nạp chồng nhưng phải đúng tên file)
-	Các file json chứa cấu hình hoặc danh sách các Sticker, có thể Thêm, xoá, sửa các item nhưng cần đúng format item

## 3.	KẾT NỐI & TÍCH HỢP:
### 3.1	Cấu hình các tham số được yêu cầu trong bước cài đặt bao gồm
-	appId và publicKey đã được cấp ở “Phần 2” khi đăng ký ứng dụng trên website www.mideasvn.com để khởi tạo dịch vụ
-	GoogleMap key cho việc load bản đồ.
Ví dụ:

![](https://s32.postimg.org/4ntydrxtx/Screen_Shot_2016_07_29_at_9_04_03_AM.png)

![](https://s32.postimg.org/vinldvved/Screen_Shot_2016_07_29_at_9_04_18_AM.png)

![](https://s32.postimg.org/xs1f2dm9h/Screen_Shot_2016_07_29_at_9_04_27_AM.png)

o	FIO_SDK có một lớp đối tượng FIO_Manager để tương tác với dịch vụ và xử lý các kết nối. Bạn cần phải khởi tạo đối tượng FIO_Manager (nó sẽ được khởi tạo 1 lần duy nhất và sử dụng trong toàn bộ quá trình hoạt động của app)
![](https://s31.postimg.org/ktlsxui2j/Screen_Shot_2016_07_29_at_9_06_26_AM.png)

### 3.2	Delegate
o	FIO_SDK chứa các hàm delegate dùng để thông báo sự kiện cho các lớp màn hình mong muốn nhận biết sự thay đổi như:
+ Thêm một contact mới thành công.
ấdsdf ![](https://s32.postimg.org/63brvhf4l/Screen_Shot_2016_07_29_at_9_07_42_AM.png) dsfas df

+ Một contact trong danh bạ đã deactive account (Xoá tài khoản)
![](https://s32.postimg.org/b5gj8rs51/Screen_Shot_2016_07_29_at_9_08_39_AM.png)
+ Tài khoản bị đăng nhập trên một thiết bị khác
![](https://s31.postimg.org/69i1p4ptn/Screen_Shot_2016_07_29_at_9_09_25_AM.png)

o	Để có thể nhận thông báo này thì cần đăng ký sự kiện lắng nghe và thêm các phương thức mong muốn lắng nghe từ các lớp cụ thể.

![](https://s31.postimg.org/mhy9ynoe3/Screen_Shot_2016_07_29_at_9_10_00_AM.png)

o	Xoá đăng ký nhận thông báo khi không còn nhu cầu sử dụng
![](https://s32.postimg.org/3z4lby2ol/Screen_Shot_2016_07_29_at_9_10_21_AM.png)

### 3.3	Kết nối và chứng thực
o	Để sử dụng tính năng Call or Chat, bạn phải chứng thực bảo mật rằng đây thực sự là người dùng của bạn bằng các thông tin được xem là như định danh User ID (như UIDs, email addresses, phone numbers, usernames, etc), cái mà chúng tôi gọi là FioUserId. Tài khoản trên hệ thống của bạn sẽ được ghi nhận nếu đã tồn tại hoặc được đăng ký mới khi chưa có trên hệ thống của chúng tôi. (Dưới đây là các bước cơ bản để chứng thực giữa cách tạọ kết nối chứng thực giữa server Mideas và server của bạn. Xem thêm tài liệu server đính kèm.)

Đăng ký tài khoản và nhận key chứng thực tại https://www.mideasvn.com/developers/signin
![](https://s31.postimg.org/9zt2nd9az/Screen_Shot_2016_07_26_at_2_57_22_PM.png)

B1: Đăng ký tài khoản với định danh user(username,phone) và tham số chứng thực(token,password mã hoá) để server mIdeas dùng để chứng thực với server của bạn,đảm bảo rằng tài khoản là hợp lệ
![](https://s31.postimg.org/hrhp26f6z/Screen_Shot_2016_07_29_at_9_15_48_AM.png)

B2: Xoá tài khoản đăng ký

![](https://s31.postimg.org/gh2f70knf/Screen_Shot_2016_07_29_at_9_16_32_AM.png)

## 4.	API HỖ TRỢ NHÀ PHÁT TRIỂN
### 4.1	Kiểm tra tài khoản user có tồn tại để thực hiện Call/Chat
![](https://s31.postimg.org/7wcb7ml3f/Screen_Shot_2016_07_29_at_9_17_40_AM.png)
### 4.2	Chat với 1 user
![](https://s32.postimg.org/cmun467vp/Screen_Shot_2016_07_29_at_9_18_30_AM.png)
### 4.3	Call tới 1 user
![](https://s31.postimg.org/c8xj3xjej/Screen_Shot_2016_07_29_at_9_19_17_AM.png)
### 4.4	Lịch sử đã chat
![](https://s32.postimg.org/cor4sljv9/Screen_Shot_2016_07_29_at_9_19_57_AM.png)
### 4.5	Lịch sử cuộc gọi
![](https://s32.postimg.org/vwitezmo5/Screen_Shot_2016_07_29_at_9_20_25_AM.png)
### 4.6	Danh bạ người dùng
![](https://s31.postimg.org/hlz8kiazv/Screen_Shot_2016_07_29_at_9_21_03_AM.png)
### 4.7	Thêm mới danh bạ
![](https://s31.postimg.org/gdlgt7iuz/Screen_Shot_2016_07_29_at_9_21_30_AM.png)
### 4.8	Cài đặt
![](https://s31.postimg.org/s0l74wdqj/Screen_Shot_2016_07_29_at_9_22_10_AM.png)
### 4.9	Mạng xã hội
![](https://s32.postimg.org/66rx58hlh/Screen_Shot_2016_07_29_at_9_22_44_AM.png)
### 4.10 Dừng kết nối tới server
Dừng kết nối tới server FIO service và thoát khỏi hệ thống
![](https://s32.postimg.org/b11oc8yqd/Screen_Shot_2016_07_29_at_9_24_00_AM.png)



