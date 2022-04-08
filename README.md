# Netlix application for customer

![firstscreen](https://user-images.githubusercontent.com/45253067/162461019-97f33943-1ff7-4a60-a38d-421b9eef3101.gif)

# Change state from admin to customer

![changestate](https://user-images.githubusercontent.com/45253067/162460628-dd76ec0a-bf5e-48a2-a3a0-775c0a169013.gif)

* API using
* Upload:

- Upload ảnh người dùng : post --> http://localhost:5000/api/uploadImageUser
- Xóa ảnh người dùng trên cloud : post --> http://localhost:5000/api/destroyImageUser
* Customer:

- Đăng ký tài khoản khách hàng: post --> http://localhost:5000/api/auth/customer/register
- Xác thực email đăng ký: get --> http://localhost:5000/api/auth/admin/verify/:userId/:uniqueString
- Đăng nhập tài khoản khách hàng : post --> http://localhost:5000/api/auth/customer/login
- Đăng xuất tài khoản: get --> http://localhost:5000/api/auth/customer/logout
- Xem profile: get --> http://localhost:5000/api/auth/customer/profile
- Chỉnh sửa profile: patch --> http://localhost:5000/api/auth/customer/profile/update
- Refresh token : get --> http://localhost:5000/api/auth/customer/refresh_token
Thay đổi mật khẩu : patch --> http://localhost:5000/api/auth/customer/changePassword
Quên mật khẩu tài khoản khách hàng: post --> http://localhost:5000/api/auth/customer/forget
Link reset mật khẩu khi quên: put --> http://localhost:5000/api/auth/customer/password/reset/:token
Đăng nhập google tài khoản khách hàng: post --> http://localhost:5000/api/auth/customer/loginGoogle
*Director:

Xem thông tin tất cả đạo diễn: get -->http://localhost:5000/api/director/all
Xem thông tin chi tiết đạo diễn: get --> http://localhost:5000/api/director/:id
*Category

Xem tất cả thể loại phim : get --> http://localhost:5000/api/category/all
*Film

Lựa chọn phim cho trẻ em hoặc người lớn : post --> http://localhost:5000/api/film/selectForAdultOrChild
Danh sách phim của người lớn : get --> http://localhost:5000/api/film/adult
Danh sách phim của trẻ em: get --> http://localhost:5000/api/film/kid
Thoát khỏi chế độ xem phim cho trẻ em: post --> http://localhost:5000/api/film/kid/exit
Xem thông tin tất cả bộ phim: get --> http://localhost:5000/api/film/all
Thông tin chi tiết của bộ phim và thông tin đánh giá của bộ phim: get --> http://localhost:5000/api/film/detail/:id
Tìm bộ phim theo thể loại: get --> http://localhost:5000/api/film/find/category/:id
Tìm bộ phim theo đạo diễn: get --> http://localhost:5000/api/film/find/director/:id
*Favourite

Xem danh sách yêu thích của người dùng: get --> http://localhost:5000/api/favourite/getList
Thêm bộ phim vào danh sách yêu thích: post --> http://localhost:5000/api/favourite/add/:idFilm
Xóa bộ phim khỏi danh sách yêu thích: delete --> http://localhost:5000/api/favourite/delete/:idFilm
*Rating

Đánh giá sao cho bộ phim: post --> http://localhost:5000/api/rating/add/:filmId
*Comment

Xem bình luận của bộ phim: get --> http://localhost:5000/api/comment/get/:filmId
Bình luận bộ phim: post --> http://localhost:5000/api/comment/add/:filmId
Chỉnh sửa bình luận: patch --> http://localhost:5000/api/comment/update/:id
Xóa mềm bình luận: patch --> http://localhost:5000/api/comment/softDelete/:id
*Payment

Xem tất cả hình thức thanh toán: get --> http://localhost:5000/api/modeOfPayment/all
Xem chi tiết hình thức thanh toán: get --> http://localhost:5000/api/modeOfPayment/:id
*Bill

Xem lịch sử hóa đơn của khách hàng: get --> http://localhost:5000/api/bill/history
Mua 1 bộ phim: post --> http://localhost:5000/api/bill/create/:filmId
Kiểm tra phim có xem được không: --> http://localhost:5000/api/bill/checkWatchFilm/:filmId
API không cần đăng nhập
* Feedback

Khách hàng gửi feedback : post --> http://localhost:5000/api/feedback/send
