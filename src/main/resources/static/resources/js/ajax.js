
var book; 
function displayBook(book) {
    var s = '';
    s += '<div class="row">'
    for (var i = 0; i < book.length; i++) {
        s += '<div class="product product__style--3 col-lg-4 col-md-4 col-sm-6 col-12">'
        s += '<div class="product__thumb">'
        s += '<a class="first__img" href="single-product.html"><img alt="product image"  src="..' + book[i].image + '"/></a>'
        s += ' <div class="hot__box">'
        s += '<span class="hot-label">BEST SALLER</span>'
        s += '</div>'
        s += '</div>'
        s += '<div class="product__content content--center">'
        s += '<h4><a href="single-product.html">' + book[i].name + '</a></h4>'
        s += '<ul class="prize d-flex">'
        s += '<li>' + book[i].price + '</li>'
        s += '<li class="old_prize">$35.00</li>'
        s += '</ul>'
        s += '<div class="action">'
        s += '<div class="actions_inner">'
        s += '<ul class="add_to_links">'
        s += '<li><a class="cart" href="cart/'+book[i].id+'"><i class="bi bi-shopping-bag4"></i></a></li>'
        s += '<li><a class="wishlist" href="wishlist.html"><i class="bi bi-shopping-cart-full"></i></a></li>'
        s += '<li><a class="compare" href="#"><i class="bi bi-heart-beat"></i></a></li>'
        s += '<li><a data-toggle="modal" title="Quick View" class="quickview" id="'+book[i].id+'" href="#productmodal"><i class="bi bi-search"></i></a></li>'
        s += '</ul>'
        s += '</div>'
        s += '</div>'
        s += '<div class="product__hover--content">'
        s += '<ul class="rating d-flex">'
        s += '<li class="on"><i class="fa fa-star-o"></i></li>'
        s += '<li class="on"><i class="fa fa-star-o"></i></li>'
        s += '<li class="on"><i class="fa fa-star-o"></i></li>'
        s += '<li><i class="fa fa-star-o"></i></li>'
        s += '<li><i class="fa fa-star-o"></i></li>'
        s += '</ul>'
        s += '</div>'
        s += '</div>'
        s += '</div>'
    }
    $('#page1').html(s);
}

$(document).ready(function () {
	//pagination
    $('#page1, #page2, #page3, #page4, #page5, #page6').on('click', function () {
        var value = $(this).text();
        $.ajax({
            method: 'GET',
            url: 'pagination',
            dataType: 'json',
            cache: false,
            data: {
            	pageNumber: value,
            }
        }).done(function (data) {
            displayBook(data.content);
        });
    });
    
    //filter by price
    $('#filter').on('click', function () {
        var value = $('#amount').val().trim();
        var res = value.split("-");
        var res1 = res[0].split("$");
        var res2 = res[1].split("$");
        var price1 = res1[1].trim();
        var price2 = res2[1].trim();
        console.log("Price 1 : "+price1+" Price 2 : "+price2)
        $.ajax({
            method: 'GET',
            url: 'filterPrice',
            dataType: 'json',
            cache: false,
            data: {
                price1: price1,
                price2: price2
            }
        }).done(function (book) {
        	$( ".wn__pagination" ).hide();
            displayBook(book);
        });
    });

    //quick view
    $('.quickview').on('click', function () {
        var value = this.id;
        console.log(value);
        $.ajax({
            method: 'GET',
            url: 'quickview',
            dataType: 'json',
            cache: false,
            data: {
                id: value,
            }
        }).done(function (book) {
            console.log(book);
            var imageUrl='';
            imageUrl += '<img alt="big images" src="..'+book.image+'">'
            $('#quickBookName').text(book.name);
            $('#quickBookImage').html(imageUrl);
            $('.new-price').text(book.price);
            $('#productmodal').modal('show');
        });
    });
    
    //auto complete search
    $('#search').keyup(function() {
        var name = $('#search').val();
    		$.ajax({
                method : 'GET',
                url : 'autoComplete',
                dataType : 'json',
                cache: false,
    		    data :{ 
    		    	name : name,
    		    }
    		}).done(function (book) {
    			console.log(book)
                var s ="";
    			if(name.length == 0){
                	s='';
                }else{
                s += '<div class="alert alert-info">';
                for(var i = 0; i < book.length; i++){
                	s +='<a href="singleProduct/'+book[i].id+'" class="alert-link">'+ book[i].name+'</a></br>'
                }
                	s += '</div>';
                }
                $("#autoCompleteSearch").html(s);
           });    
    });
    
    //update cart items quantity
    $('.quantity').blur(function() {
        var newQuantity = $('.quantity').val();
        var id = this.id;
    		$.ajax({
                method : 'GET',
                url : 'updateCart',
                dataType : 'json',
                cache: false,
    		    data :{ 
                    id : id,
                    newQuantity : newQuantity ,
    		    }
    		}).done(function (book) {
    			$('.cart-total').text(book.total);
           });    
    });
    
});


