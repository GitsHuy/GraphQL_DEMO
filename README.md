📖 Tài liệu Mô tả & Hướng dẫn Sử dụng
Chương trình: HỆ THỐNG QUẢN LÝ SẢN PHẨM

🌟 1. Giới thiệu Tổng quan
Hệ Thống Quản Lý Sản Phẩm là một công cụ nội bộ được thiết kế để hỗ trợ quản lý thông tin về người dùng (nhân viên bán hàng), danh mục sản phẩm, và sản phẩm cụ thể một cách hiệu quả.  

Nền tảng hiện đại: Chương trình sử dụng công nghệ tiên tiến, đảm bảo hoạt động nhanh, mượt mà, và cập nhật dữ liệu tức thời mà không cần tải lại trang.  
Các chức năng chính:
Quản lý Người dùng: Thêm, sửa, xóa thông tin người bán.  
Quản lý Danh mục: Tạo và quản lý các danh mục (ví dụ: "Điện thoại", "Laptop", "Thời trang").  
Quản lý Sản phẩm: Thêm sản phẩm, liên kết với người bán và danh mục.  
Tìm kiếm và Lọc thông minh: Xem tất cả sản phẩm hoặc lọc theo danh mục mong muốn.




🛠️ 2. Tổng quan về Công nghệ Sử dụng
2.1. Phía Máy chủ (Backend - "Bộ não" của hệ thống)

Spring Boot (Java): Nền tảng mạnh mẽ để xử lý logic backend.  
GraphQL API: Công nghệ giao tiếp dữ liệu tiên tiến, thay thế các phương thức REST truyền thống.  
SQL Server: Cơ sở dữ liệu để lưu trữ thông tin người dùng, danh mục, và sản phẩm.

2.2. Phía Giao diện Người dùng (Frontend - "Giao diện bạn thấy")

HTML và CSS: Xây dựng giao diện trực quan, thân thiện.  
JavaScript và AJAX: Tạo trải nghiệm động, mượt mà.  
Cách hoạt động: Khi thêm sản phẩm mới, JavaScript gửi yêu cầu qua AJAX đến máy chủ, sau đó chỉ cập nhật khu vực cần thiết (ví dụ: bảng danh sách sản phẩm), mang lại trải nghiệm liền mạch.




🚀 3. Hướng dẫn Truy cập và Sử dụng
📌 Bước 1: Truy cập vào chương trình

Mở trình duyệt web (Chrome, Firefox, Cốc Cốc, v.v.).  
Truy cập đường dẫn: http://localhost:8080.  
Giao diện chính của chương trình sẽ hiện ra.

📌 Bước 2: Các thao tác cơ bản (Thêm dữ liệu ban đầu)
Thêm Người dùng mới:

Vào mục Quản lý User.  
Điền thông tin: Họ tên, Email, và Mật khẩu.  
Nhấn nút Thêm User.  
Hệ thống sẽ hiển thị thông báo thành công cùng ID người dùng. Ghi nhớ ID này.

Thêm Danh mục mới:

Vào mục Quản lý Category.  
Điền Tên Category (ví dụ: "Laptop Gaming").  
Nhấn nút Thêm Category.  
Hệ thống sẽ trả về ID danh mục. Ghi nhớ ID này.

📌 Bước 3: Thêm và xem sản phẩm
Thêm một sản phẩm mới:

Vào mục Thêm sản phẩm mới (góc dưới bên phải).  
Điền thông tin: Tên, Giá, Số lượng.  
Nhập ID của User và ID của Category đã tạo ở Bước 2.  
Nhấn nút Thêm sản phẩm.

Xem danh sách sản phẩm:

Tất cả sản phẩm được hiển thị trong bảng Danh sách sản phẩm.  
Bảng tự động cập nhật mỗi khi thêm sản phẩm mới.

📌 Bước 4: Lọc sản phẩm theo danh mục

Vào mục Nhập ID Category để lọc (phía trên bảng sản phẩm).  
Nhập ID danh mục muốn xem (ví dụ: ID của "Laptop Gaming").  
Nhấn nút Lọc sản phẩm.  
Bảng sẽ chỉ hiển thị các sản phẩm thuộc danh mục đó.  
Để xem lại tất cả sản phẩm, nhấn nút Xem tất cả.


🖼️ 4. Ảnh Minh họa
4.1. Truy cập GraphQL:
  <img width="1143" height="560" alt="image" src="https://github.com/user-attachments/assets/a50d47f1-236c-47f6-931a-8c1f0af4c1be" />
4.2. Thêm, sửa, xóa dữ liệu:
  <img width="1138" height="614" alt="image" src="https://github.com/user-attachments/assets/ec46aaf2-b3d6-4409-87f6-19c4a8aea0dd" />
4.3. Lọc sản phẩm theo danh mục:
  <img width="1160" height="605" alt="image" src="https://github.com/user-attachments/assets/f647088f-6d4c-4329-a4d0-63867e72bb58" />





