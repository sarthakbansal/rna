$(document).ready(function(){
	
	var dtToday = new Date();
	var arr;
    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();

    if(month < 10)
        month = '0' + month.toString();
    if(day < 10)
        day = '0' + day.toString();

    var minDate = year + '-' + month + '-' + day;    
    $('#bookingDate').attr('min', minDate);
	
    $("#loginForm").submit(function(e){
    	
    	var flag=0;
    	var loginForm = this;
    	e.preventDefault();
    	var username, password, usertype;
    	$("input").each(function(){
    		if($(this).val() == ""){
    			flag=1;
    			$(this).parent().next().children().text("Please fill in this field !");
    			return false;
    		}
    	});
    	if(flag == 0){
    		username = $("input[type=text]").val();
    		password = $("input[type=password]").val();
    		usertype = $("input[type=radio]:checked").val();
    		
	    	$.ajax({
				url: "/RNA/checkLogin",
				type: "POST",
				data: "username="+username+"&password="+password+"&userType="+usertype,
				success: function(data){
					if(data[0].message == "success"){
						loginForm.submit();
					}
					else{
						$("#loginForm .error_message").html(data[0].message);	
					}
				}
			});
    	}
    });
    
    $("#signupForm").submit(function(e){
    	var flag=0;
    	var username, password, userType, name, attribute, mobile, email, confirmPassword, street, city, pincode, country;
    	var signupForm = this;
    	e.preventDefault();
    	$("input").each(function(){
    		if($(this).val() == ""){
    			flag=1;
    			$(this).parent().next().children().text("Please fill in this field !");
    			return false;
    		}
    	});
    	if(flag == 0){
    		userType = $("input[type=radio]:checked").val();
    		$("input").each(function(){
    			attribute = $(this).attr("name");
    			
        		if(attribute == "name"){
        			name = $(this).val();
        		}
        		else if(attribute == "mobile"){
        			mobile = $(this).val();
        		}
        		else if(attribute == "username"){
        			username = $(this).val();
        		}
        		else if(attribute == "password"){
        			password = $(this).val();
        		}
        		else if(attribute == "email"){
        			email = $(this).val();
        		}
        		else if(attribute == "confirm-password"){
        			confirmPassword = $(this).val();
        			if(confirmPassword != password){
        				$(this).parent().next().children().text("Password does not match !");
        				flag=1;
        			}        				
        		}
        		else if(attribute == "userAddress.pincode"){
        			pincode = $(this).val();
        		}
        		else if(attribute == "userAddress.street"){
        			street = $(this).val();
    			}
        		else if(attribute == "userAddress.city"){
        			city = $(this).val();
        		}
        		else if(attribute == "userAddress.country"){
        			country = $(this).val();
        		}
    		});
    		if(flag==0){
    			var address = street + " Street " + city + " " + pincode + " " + country;
		    	$.ajax({
					url: "/RNA/checkSignUp",
					type: "POST",
					data: "username="+username+"&password="+password+"&userType="+userType+"&name="+name+"&mobile="+mobile+"&email="+email+"&address="+address,
					success: function(data){
						if(data[0].status == "success")
						{
							window.open("http://www.w3schools.com");
							$("span.message").html(data[0].message);	
							setTimeout(function(){ 
								window.location.href = "/RNA/login"; 
							}, 3000);
						}
						else{
							$("input").each(function(){
								
								$("span.message").html(data[0].message);
				    			attribute = $(this).attr("name");
				    			
				    			if(attribute == "username"){
				    				$(this).parent().next().children().text(data[0].error_message1);
				    			}
				    			else if(attribute == "email"){
				    				$(this).parent().next().children().text(data[0].error_message2);
				    			}
				    			else if(attribute == "mobile"){
				    				$(this).parent().next().children().text(data[0].error_message3);
				    			}
				    		});
						}
					}
				});
    		}
    	}
    });
    
    $("#loginForm input, #signupForm input").each(function(){
		$(this).focus(function(){
			if($(this).attr("id")!="signUp"){
				$(this).parent().next().children().text("");
				$(".error_message, .message").text("");
			}
		});
	});
    
	$("#signupForm input[type='radio']").click(function(){
		
		if( $(this).val() == "company" )
		{
			$('#user-name').text("Name of company :");
			$('#user-contact').text("Phone no. :");
		}
		else
		{
			$('#user-name').text("Name :");
			$('#user-contact').text("Mobile no. :");
		}
	});
	
	$("#adminLoginForm").submit(function(e){
    	
    	var flag=0;
    	var adminLogin = this;
    	e.preventDefault();
    	var username, password;
    	$("input").each(function(){
    		if($(this).val() == ""){
    			flag=1;
    			$(this).parent().next().children().text("Please fill in this field !");
    			return false;
    		}
    	});
    	if(flag == 0){
    		username = $("input[type=text]").val();
    		password = $("input[type=password]").val();
    		
	    	$.ajax({
				url: "/RNA/admin/checkAdminLogin",
				type: "POST",
				data: "username="+username+"&password="+password,
				success: function(data){
					if(data[0].message == "success"){
						adminLogin.submit();
					}
					else{
						$("#adminLoginForm .error_message").html(data[0].message);	
					}
				}
			});
    	}
    });
	
	$("#admin li").click(function(){
		
		var tablename = "";
		$(".admin").hide();
		$(this).next().toggle();
		var id = $(this).attr("id");
		if(id != "Route"){
			
			if(id=="adminViewTransporter")
				tablename = "transporter";
			else if(id=="adminViewCompany")
				tablename = "company"; 
			
			var div=$(this).next();
			$.ajax({
				url: "/RNA/admin/adminViewData",
				type: "POST",
				data: "tablename="+tablename,
				success: function(data){
					div.html(data);
					setTimeout(function(){ 
						$(this).parent().toggle(); 
					}, 5000);
					
				}
			});
		}
	});
	$("#addRoute").click(function(e) {
		
		var flag=0;
		if($('input[type=checkbox]:checked').length == 0)
		{
			$("#truckTypeError").text("Chose atleast one truck type !");
			flag=1;
		}
		
		if($('#companyName option:selected').text() == "Chose Company")
		{
			$("#companyNameError").text("Chose a company !");
			flag=1;
		}
		
		var sourceName = $("#sourceAdd").val();
		if(sourceName == "" )
			sourceName = $("#sourceName").val();
		
		if(sourceName == null)
		{
			$("#sourceError").text("Chose a source !");
			flag=1;
		}
		
		var destinationName = $("#destinationAdd").val();
		if(destinationName == "")
			destinationName = $("#destinationName").val();
		
		if(destinationName == null)
		{
			$("#destinationError").text("Chose a destination !");
			flag=1;
		}
		
		if(sourceName == destinationName && sourceName != null){
			alert("Source and Destination can't be same !")
			flag=1;
		}
		
		if(flag == 0){
			
			 var trucktype = $("input[type=checkbox]:checked").map(
				     function () {return this.value;}).get().join(",");
			 
			 
			 var div = $(this).parent();
			 $.ajax({
					url: "/RNA/admin/addRoute",
					type: "POST",
					data: "companyID="+$('#companyName').val()+"&destinationName="+destinationName+"&trucktype="+trucktype+"&sourceName="+sourceName,
					success: function(data){
						$("span.message").text(data);
						setTimeout(function(){ 
							div.toggle(); 
							}, 5000);
						
					}
				});
		}
		e.preventDefault();

	});
	$("#admin #sourceName").change(function(){
		if($(this).val() == "other2")
		{
			$(this).parent().next().toggle();
			$(this).parent().next().next().next().toggle();
			$(this).parent().toggle();
			$(this).val("chose2");
		}
		
	});
	$("#admin #listSource").click(function(){
		$(this).parent().prev().prev().toggle();
		$(this).parent().prev().prev().prev().find("select").val("chose2");
		$(this).parent().prev().prev().prev().toggle();
		$(this).parent().toggle();
	});
	
	$("#admin #destinationName").change(function(){
		if($(this).val() == "other3")
		{
			$(this).parent().next().toggle();
			$(this).parent().next().next().next().toggle();
			$(this).parent().toggle();
			$(this).val("chose3");
		}
		
	});
	$("#admin #listDestination").click(function(){
		$(this).parent().prev().prev().toggle();
		$(this).parent().prev().prev().prev().find("select").val("chose3");
		$(this).parent().prev().prev().prev().toggle();
		$(this).parent().toggle();
	});
	
	$("#transport li").click(function(){
		var id, username;
		$(".transporter").hide();
		$(this).next().toggle();
		id = $(this).attr("id");
		
		if(id == "viewTrucks"){
			var div=$(this).next();
			username = $("#transport #userName").val();
			$.ajax({
				url: "/RNA/viewTransporterTrucks",
				type: "POST",
				data: "username="+username,
				success: function(data){
					div.html(data);
					setTimeout(function(){ 
						$(this).parent().toggle(); 
						}, 5000);
					
				}
			});
		}
	});
	
	$("#registerTruck").click(function(){
		var flag=0;
		if($("#trucktype").val() == null)
		{
			$("#truckTypeError").text("Chose a truck type !");
			flag=1;
		}
		if($("#sourceName").val() == null)
		{	
			$("#sourceError").text("Chose a source !");
			flag=1;
		}
		
		if($("#destinationName").val() == null)
		{	
			$("#destinationError").text("Chose a destination !");
			flag=1;
		}
		$("input[type=text]").each(function(){
			if($(this).val() == "")
			{
				$(this).parent().next().children().text("Please fill in this field !");
				flag=1;
			}
		});
		
		if($("#destinationName").val() == $("#sourceName").val() &&  $("#sourceName").val() != null){
			alert("Source and Destination can't be same !")
			flag=1;
		}
		if(flag == 0){
			
			 var username = $("#transport #userName").val();
			 var div = $(this).parent();
			 $.ajax({
					url: "/RNA/registerTruck",
					type: "POST",
					data: "destinationName="+$("#destinationName").val()+"&trucktype="+$("#trucktype").val()+"&panNumber="+$("#panNumber").val()+"&sourceName="+$("#sourceName").val()+"&registerationNumber="+$("#registerationNumber").val()+"&driver_name="+$("#driver-name").val()+"&driver_mobile="+$("#driver-mobile").val()+"&userName="+username,
					success: function(data){
						$("span.message").text(data);
						setTimeout(function(){ 
							div.toggle(); 
							}, 5000);
						
					}
				});
		}
	});
	$("#transport input, #transport select, #admin input, #admin select, #adminLoginForm input").each(function(){
		$(this).focus(function(){
			$("span.error, span.error_message").text("");
		});
	});
	$("#company li").click(function(){
		
		var id, companyId;
		$(".company").hide();
		$(this).next().toggle();
		id = $(this).attr("id");
		companyId = $("#companyId").val();
		
		if(id == "viewBooking"){
			var div=$(this).next();
			$.ajax({
				url: "/RNA/viewCompanyBookings",
				type: "POST",
				data: "companyId="+companyId,
				success: function(data){
					div.html(data);
					setTimeout(function(){ 
						$(this).parent().toggle(); 
						}, 5000);
					
				}
			});
		}
		if(id == "edit"){
			var div=$(this).next();
			$.ajax({
				url: "/RNA/ViewCompany",
				type: "POST",
				data: "companyId="+companyId,
				success: function(data){
					div.html(data);
					setTimeout(function(){ 
						$(this).parent().toggle(); 
						}, 5000);
					
				}
			});
		}
	});
	
	
	$(".company #sourceName").change(function(){

		var source = $(".company #sourceName").val(); 
		
		$(this).parent().parent().next().show();
		$(this).parent().parent().next().find(".sourceLocation").show();
		$(this).parent().parent().next().find(".srcaddressAdd").hide();
		$(".company #destinationName").attr("disabled",false);
		
		$.ajax({
			url: "/RNA/getLocationAddress",
			type: "POST",
			data: "companyId="+$("#companyId").val()+"&location="+$(this).val(),
			success: function(data){
				$("#sourceAddress").html(data);
				$.ajax({
					url: "/RNA/getDestinationName",
					type: "POST",
					data: "sourceName="+$("#sourceName").val()+"&companyId="+$("#companyId").val(),
					success: function(data){
						$("#destinationName").html(data);
					}
				});
			}
		});
		
		
	});
	
	$("td .srcaddAddress").click(function(){
		if($(this).text()=="Add Location")
		{
			$("#srcCity").val($(".company #sourceName").val());
			$(this).parent().parent().find(".sourceLocation").hide();
			$(this).parent().parent().find(".srcaddressAdd").show();
		}
		else
		{
			$(this).parent().parent().find(".sourceLocation").show();
			$(this).parent().parent().find(".srcaddressAdd").hide();
		}
	});
	
	$("#addsrcLocation").click(function(){
		var address = "";
		var flag=0;
		$("td.srcaddressAdd input[type=text]").each(function(){
			if($(this).val() == "")
			{
				alert("Please fill in "+$(this).attr("name")+" field !");
				flag=1;
				return false;
			}
			else
				address = address + $(this).val() + " ";
		});
		if(flag == 0){
			var elem = $(this).parent().next().children().next();
			var elem1 = $(this).parent().parent().find(".sourceLocation");
			var elem2 = $(this).parent().parent().find(".srcaddressAdd");
			address = address.substring(0, address.length);
			$.ajax({
				url: "/RNA/addLocationAddress",
				type: "POST",
				data: "address="+address+"&location="+$("#srcCity").val()+"&companyId="+$("#companyId").val(),
				success: function(data){
					elem.text(data);
					$.ajax({
						url: "/RNA/getLocationAddress",
						type: "POST",
						data: "companyId="+$("#companyId").val()+"&location="+$("#srcCity").val(),
						success: function(data){
							$("#sourceAddress").html(data);
						}
					});
					setTimeout(function(){ 
						elem1.show();
						elem2.hide();
						elem.text("");
					}, 5000);
				}
			});
		}
	});
	
	$(".company #destinationName").change(function(){
			
		var destinationName = $(".company #destinationName").val(); 
		
		$(this).parent().parent().next().show();
		$(this).parent().parent().next().find(".destinationLocation").show();
		$(this).parent().parent().next().find(".destinationaddressAdd").hide();
		
		$.ajax({
			url: "/RNA/getLocationAddress",
			type: "POST",
			data: "companyId="+$("#companyId").val()+"&location="+$(this).val(),
			success: function(data){
				$("#destinationAddress").html(data);
				$.ajax({
					url: "/RNA/getTruckType",
					type: "POST",
					data: "companyId="+$("#companyId").val()+"&destinationName="+$("#destinationName").val()+"&sourceName="+$("#sourceName").val(),
					success: function(data){
						$("#trucktype").html(data);
					}
				});
			}
		});
	});
	
	$("td .destaddAddress").click(function(){
		if($(this).text()=="Add Location")
		{
			$("#destCity").val($(".company #destinationName").val());
			$(this).parent().parent().find(".destLocation").hide();
			$(this).parent().parent().find(".destaddressAdd").show();
		}
		else
		{
			$(this).parent().parent().find(".destLocation").show();
			$(this).parent().parent().find(".destaddressAdd").hide();
		}
	});
	
	$("#addDestLocation").click(function(){
		var address = "";
		var flag=0;
		$("td.destaddressAdd input[type=text]").each(function(){
			if($(this).val() == "")
			{
				alert("Please fill in "+$(this).attr("name")+" field !");
				flag=1;
				return false;
			}
			else
				address = address + $(this).val() + " ";
		});
		if(flag == 0){
			var elem = $(this).parent().next().children().next();
			var elem1 = $(this).parent().parent().find(".destinationLocation");
			var elem2 = $(this).parent().parent().find(".destaddressAdd");
			address = address.substring(0, address.length);
			$.ajax({
				url: "/RNA/addLocationAddress",
				type: "POST",
				data: "address="+address+"&city="+$("#destCity").val()+"&companyId="+$("#companyId").val(),
				success: function(data){
					elem.text(data);
					$.ajax({
						url: "/RNA/getLocationAddress",
						type: "POST",
						data: "companyId="+$("#companyId").val()+"&location="+$("#destCity").val(),
						success: function(data){
							$("#destinationAddress").html(data);
						}
					});
					setTimeout(function(){ 
						elem1.show();
						elem2.hide();
						elem.text("");
					}, 5000);
				}
			});
		}
	});
	
	$("#company select, #company input").focus(function(){
		$(".company #sourceError, .company #destinationError, .company #srcloc, .company #destloc, .company #truckTypeError, .company #bookingDateError").text("");
	});
	
	$("#booktruck").click(function(){
		var flag=0;
		if($(".company #trucktype").val() == null)
		{
			$(".company #truckTypeError").text("Chose a truck type !");
			flag=1;
		}
		if($(".company #sourceName").val() == null)
		{	
			$(".company #sourceError").text("Chose a source !");
			flag=1;
		}
		
		if($(".company #destinationName").val() == null)
		{	
			$(".company #destinationError").text("Chose a destination !");
			flag=1;
		}

		if($(".company #destinationAddress").val() == null)
		{	
			$(".company #destloc").text("Chose a location for the above destination !");
			flag=1;
		}
		
		if($(".company #sourceAddress").val() == null)
		{	
			$(".company #srcloc").text("Chose a location for the above source !");
			flag=1;
		}
		
		if($(".company #destinationName").val() == $(".company #sourceName").val() && $(".company #sourceName").val() != null){
			alert("Source and Destination can't be same !")
			flag=1;
		}
		
		if($("#company #bookingDate").val() == "")
		{	
			$(".company #bookingDateError").text("Chose the date !");
			flag=1;
		}
		
		if(flag == 0){
			var x;
			 var div = $(this).parent();
			 $.ajax({
				url: "/RNA/bookTruck",
				type: "POST",
				data: "destinationName="+$(".company #destinationName").val()+"&trucktype="+$("#trucktype").val()+"&bookingDate="+$(".company #bookingDate").val()+"&sourceName="+$(".company #sourceName").val()+"&sourceAddress="+$(".company #sourceAddress").val()+"&destinationAddress="+$(".company #destinationAddress").val()+"&companyId="+$("#companyId").val(),
				success: function(data){
					if(data == '1'){
						x = confirm("You have already made a same type of booking earlier!, Do you want to book an another ?");
						againbook(x, div);
					}
						
					else
						$("span.message").text(data);
					setTimeout(function(){ 
						div.toggle(); 
					}, 5000);	
				}
			});
		}
	});
	
	$("span.edit").click(function(){
		var attr = $(this).attr("name");
		if(attr == "edit")
		{
			arr = new Array();
			$(".company .companyDetails").each(function(){
				$(this).attr("contenteditable","true");
				$(this).focus();
				arr.push($(this).text());
			});
			$(this).next().show();
			$(this).next().next().show();
			$(this).hide();
		}
		if(attr == "cancel")
		{
			var i=0;
			$(".company .companyDetails").each(function(){
				$(this).attr("contenteditable","false");
				$(this).text(arr[i]);
				i++;
			});
			$(this).prev().hide();
			$(this).prev().prev().show();
			$(this).hide();
		}
		if(attr == "save")
		{
			$(".company .companyDetails").each(function(){
				if($(this).text() == ""){
					$(this).next().text("Please fill in this field !");
					return false;
				}				
			});
		}
	});
	$(".company .companyDetails").focus(function(){
		$(this).next().text("");
	});
});
function againbook(x, div){
	if(x)
	{
		$.ajax({
			url: "/RNA/forceBookTruck",
			type: "POST",
			data: "destinationName="+$(".company #destinationName").val()+"&trucktype="+$("#trucktype").val()+"&bookingDate="+$(".company #bookingDate").val()+"&sourceName="+$(".company #sourceName").val()+"&srcloc="+$(".company #sourceAddress").val()+"&destloc="+$(".company #destinationAddress").val()+"&companyId="+$("#companyId").val(),
			success: function(data){
				$("span.message").text(data);
				setTimeout(function(){ 
					div.toggle(); 
				}, 5000);	
			}
		});
	}
}