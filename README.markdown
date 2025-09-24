# Tài liệu Mô tả & Hướng dẫn Sử dụng  
**Chương trình: HỆ THỐNG QUẢN LÝ SẢN PHẨM**

## 1. Giới thiệu Tổng quan  
Hệ Thống Quản Lý Sản Phẩm là một công cụ nội bộ được xây dựng để giúp quý khách dễ dàng quản lý thông tin về người dùng (nhân viên bán hàng), các danh mục sản phẩm và các sản phẩm cụ thể.

Chương trình được xây dựng trên nền tảng công nghệ hiện đại, đảm bảo hệ thống hoạt động nhanh, mượt mà và dữ liệu được cập nhật ngay lập tức mà không cần phải tải lại trang.

### Các chức năng chính:  
- **Quản lý Người dùng**: Dễ dàng thêm mới, sửa hoặc xóa thông tin người bán.  
- **Quản lý Danh mục**: Tạo ra các danh mục (ví dụ: "Điện thoại", "Laptop", "Thời trang") để phân loại sản phẩm.  
- **Quản lý Sản phẩm**: Thêm mới các sản phẩm, liên kết chúng với một người bán và một danh mục cụ thể.  
- **Tìm kiếm và Lọc thông minh**: Nhanh chóng xem tất cả sản phẩm hoặc lọc danh sách sản phẩm theo một danh mục mong muốn.

## 2. Tổng quan về Công nghệ Sử dụng  

### 2.1. Phía Máy chủ (Backend - "Bộ não" của hệ thống):  
- **Nền tảng Spring Boot (Java)**  
- **Công nghệ API GraphQL**: Thay vì sử dụng các phương thức cũ, tôi đã áp dụng GraphQL - một công nghệ giao tiếp dữ liệu tiên tiến.  
- **Cơ sở dữ liệu SQL Server**

### 2.2. Phía Giao diện Người dùng (Frontend - Những gì bạn thấy và tương tác):  
- **HTML và CSS**  
- **JavaScript và AJAX**: Đây là "phép màu" giúp trang web trở nên sống động và thông minh.  

#### Nó hoạt động như thế nào?  
Khi bạn thêm một sản phẩm mới, thay vì phải tải lại toàn bộ trang web, JavaScript sẽ âm thầm gửi yêu cầu lên máy chủ thông qua AJAX. Sau khi nhận được phản hồi, nó chỉ cập nhật lại đúng khu vực cần thiết (ví dụ: bảng danh sách sản phẩm). Điều này tạo ra một trải nghiệm liền mạch, mượt mà và tức thì.

## 3. Hướng dẫn Truy cập và Sử dụng  

### Bước 1: Truy cập vào chương trình  
- Mở trình duyệt web của bạn (Chrome, Firefox, Cốc Cốc, v.v.).  
- Truy cập vào đường dẫn sau: [http://localhost:8080](http://localhost:8080)  
- Bạn sẽ thấy giao diện chính của chương trình.  


### Bước 2: Các thao tác cơ bản (Thêm dữ liệu ban đầu)  
Trước khi có thể thêm sản phẩm, hệ thống cần có thông tin về Người bán (User) và Danh mục (Category).

#### Để thêm Người dùng mới:  
- Nhìn vào ô **"Quản lý User"**.  
- Điền thông tin **Họ tên**, **Email**, và **Mật khẩu** vào các ô tương ứng.  
- Nhấn nút **"Thêm User"**.  
- Một thông báo thành công sẽ hiện ra cùng với **ID của người dùng** đó. **Hãy ghi nhớ ID này**.

#### Để thêm Danh mục mới:  
- Nhìn vào ô **"Quản lý Category"**.  
- Điền **Tên Category** (ví dụ: "Laptop Gaming").  
- Nhấn nút **"Thêm Category"**.  
- Hệ thống cũng sẽ báo thành công và trả về **ID của danh mục**. **Hãy ghi nhớ ID này**.

### Bước 3: Thêm và xem sản phẩm  
Giờ bạn đã có thể thêm sản phẩm vào hệ thống.

#### Để thêm một sản phẩm mới:  
- Nhìn vào ô **"Thêm sản phẩm mới"** ở góc dưới bên phải.  
- Điền các thông tin của sản phẩm như **Tên**, **Giá**, **Số lượng**.  
- Tại ô **ID của User**, nhập ID của người bán mà bạn đã tạo ở Bước 2.  
- Tại ô **ID của Category**, nhập ID của danh mục mà bạn đã tạo ở Bước 2.  
- Nhấn nút **"Thêm sản phẩm"**.

#### Để xem danh sách sản phẩm:  
- Tất cả sản phẩm sẽ được tự động hiển thị trong bảng **"Danh sách sản phẩm"**.  
- Mỗi khi bạn thêm một sản phẩm mới, bảng này sẽ tự động cập nhật.

### Bước 4: Lọc sản phẩm theo danh mục  
Khi có nhiều sản phẩm, bạn có thể muốn xem các sản phẩm thuộc một danh mục cụ thể.

- Nhìn vào ô **"Nhập ID Category để lọc"** ở phía trên bảng sản phẩm.  
- Nhập **ID của danh mục** bạn muốn xem (ví dụ: ID của "Laptop Gaming").  
- Nhấn nút **"Lọc sản phẩm"**.  
- Bảng sẽ ngay lập tức chỉ hiển thị các sản phẩm thuộc danh mục đó.  
- Để xem lại tất cả sản phẩm, chỉ cần nhấn nút **"Xem tất cả"**.

## 4. Ảnh Minh họa  

### Truy cập localhost:8080/graphql:  
![GraphQL Interface](https://github.com/user-attachments/assets/a50d47f1-236c-47f6-931a-8c1f0af4c1be)

### Thêm, sửa, xóa:  
![CRUD Operations](https://github.com/user-attachments/assets/ec46aaf2-b3d6-4409-87f6-19c4a8aea0dd)

### Lọc sản phẩm:  
![Filter Products](https://github.com/user-attachments/assets/f647088f-6d4c-4329-a4d0-63867e72bb58)
