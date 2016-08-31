$(function(){
	$('#messages li').click(function(){
		$(this).fadeOut();
	});
	setTimeout(function(){
		$('#messages li.info').fadeOut();
	}, 3000);
	
	$(".nav a").on("click", function(){
		   $(".nav").find(".active").removeClass("active");
		   $(this).parent().addClass("active");
		});
});

