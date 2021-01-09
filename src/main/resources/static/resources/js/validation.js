var statusName, statusPassword, statusAddress;

$(document).ready(function () {
	$("#cname").blur(function () {
        var value = $(this).val();
        if (value == ''){
            $("#error-name").html("Vui lòng nhập tên người dùng");
        }
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
	        	$("#error-name").html("Tên người dùng này đã tồn tại, xin thử lại tên khác");
	        }else{
	        	$("#error-name").html("");
	        }
        });
    });
	
	$("#cpassword").blur(function () {
        var value = $(this).val();
        if (value == ''){
            $("#error-password").html("Vui lòng nhập mật khẩu");
        }else if(value.length < 6){
        	$("#error-password").html("Mật khẩu phải lớn hơn 6 kí tự");
        }else{
        	$("#error-password").html("");
        }
    });
	
	$("#caddress").blur(function () {
        var value = $(this).val();
        if (value === ''){
            $("#error-address").html("Vui lòng nhập đầy đủ địa chỉ");
        }else{
        	$("#error-address").html("");
        }
    });
	
	$("#cyearOfBirth").blur(function () {
        var value = $(this).val();
        var today = new Date();
        var currentYear = today.getFullYear();
        var age = currentYear - parseInt(value);
        console.log("Age : "+age);
        if (value == ''){
            $("#error-yearOfBirth").html("Vui lòng nhập đầy đủ năm sinh");
        } else if(age < 18){
        	$("#error-yearOfBirth").html("Người đăng ký phải lớn hơn 18 tuổi");
        } else{
        	$("#error-yearOfBirth").html("")
        }
    });
	
	$("#cphone").blur(function () {
        var value = $(this).val();
        if (value == ''){
            $("#error-phone").html("Vui lòng nhập đầy đủ số điện thoại người dùng");
        } else if(value.length < 10 || value.length > 12){
        	$("#error-phone").html("Độ dài số điện thoại của bạn không hợp lệ");
        }else{
        	$("#error-phone").html("");
        }
    });
	
	$("#cemail").blur(function () {
        var value = $(this).val();
        if (value == ''){
            $("#error-email").html("Vui lòng nhập đầy đủ email người dùng");
        }else{
        	$("#error-email").html("");
        }
    });
});