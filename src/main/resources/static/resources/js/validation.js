
$(document).ready(function () {
	$("#cname").blur(function () {
        var value = $(this).val();
        $.ajax({
            method: 'GET',
            url: 'checkUserName',
            dataType: 'text',
            cache: false,
            data: {
                userName: value,
            }
        }).done(function (data) {
        	console.log(data)
	        if(data == "true"){
	        	$("#error-name").text("Tên người dùng này đã tồn tại, xin thử lại tên khác");
	        }else if (value == ''){
	            $("#error-name").text("Vui lòng nhập tên người dùng");
	        }else{
	        	$("#error-name").text("");
	        }
        });
    });
	
	$("#cpassword").blur(function () {
        var value = $(this).val();
        if (value == ''){
            $("#error-password").text("Vui lòng nhập mật khẩu");
        }else if(value.length < 6){
        	$("#error-password").text("Mật khẩu phải lớn hơn 6 kí tự");
        }else{
        	$("#error-password").text("");
        }
    });
	
	$("#caddress").blur(function () {
        var value = $(this).val();
        if (value === ''){
            $("#error-address").text("Vui lòng nhập đầy đủ địa chỉ");
        }else{
        	$("#error-address").text("");
        }
    });
	
	$("#cyearOfBirth").blur(function () {
        var value = $(this).val();
        var today = new Date();
        var currentYear = today.getFullYear();
        var age = currentYear - parseInt(value);
        console.log("Age : "+age);
        if (value == ''){
            $("#error-yearOfBirth").text("Vui lòng nhập đầy đủ năm sinh");
        } else if(age < 18){
        	$("#error-yearOfBirth").text("Người đăng ký phải lớn hơn 18 tuổi");
        } else{
        	$("#error-yearOfBirth").text("");
        }
    });
	
	$("#cphone").blur(function () {
        var value = $(this).val();
        if (value == ''){
            $("#error-phone").text("Vui lòng nhập đầy đủ số điện thoại người dùng");
        } else if(value.length < 10 || value.length > 12){
        	$("#error-phone").text("Độ dài số điện thoại của bạn không hợp lệ");
        }else{
        	$("#error-phone").text("");
        }
    });
	
	$("#cemail").blur(function () {
        var value = $(this).val();
        if (value == ''){
            $("#error-email").text("Vui lòng nhập đầy đủ email người dùng");
        }else{
        	$("#error-email").html("");
        }
    });
});
