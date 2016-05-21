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
    $('#txtdate').attr('min', minDate);
	
	$("input[type='radio']").click(function(){
		
		if( $(this).val() == "company" )
		{
			$('#1').text("Name of company :");
			$('#2').text("Phone no. :");
			$('#3').text("Email :");
			$('#4').html("<input type='email' name='email' required />");
		}
		else
		{
			$('#1').text("Name :");
			$('#2').text("Mobile no. :");
			$('#3').text("Email(optional) :");
			$('#4').html("<input type='email' name='email'  />");
		}
	});
	
	$("#admin li").click(function(){
		//alert( $(this).attr("id") );
		var tablename;
		$(".admin").hide();
		$(this).next().toggle();
		tablename = $(this).attr("id");
		if(tablename != "destination"){
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
	$("#addDestination").click(function(e) {
		
		var flag=0;
		if($('input[type=checkbox]:checked').length == 0)
		{
			$("#truckType").text("Chose atleast one truck type !");
			flag=1;
		}
		
		if($('#companyList option:selected').text() == "Chose Company")
		{
			$("#companyNames").text("Chose a company !");
			flag=1;
		}
		
		var source = $("#sourceAdd").val();
		if(source=="")
			source = $("#source").val();
		
		if(source == null)
		{
			$("#sourceName").text("Chose a source !");
			flag=1;
		}
		
		var destination = $("#destinationAdd").val();
		if(destination=="")
			destination = $("#dest").val();
		
		if(destination == null)
		{
			$("#destinationName").text("Chose a destination !");
			flag=1;
		}
		
		if(source == destination && source != null){
			alert("Source and Destination can't be same !")
			flag=1;
		}
		
		if(flag == 0){
			
			 var trucktype = $("input[type=checkbox]:checked").map(
				     function () {return this.value;}).get().join(",");
			 
			 //alert($('#companyList').val() +" "+ destination +" "+ trucktype +" "+ source);
			 var div = $(this).parent();
			 $.ajax({
					url: "/RNA/admin/addDestination",
					type: "POST",
					data: "companyID="+$('#companyList').val()+"&destinationName="+destination+"&trucktype="+trucktype+"&sourceName="+source,
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
	$("#admin #source").change(function(){
		if($(this).val() == "other2")
		{
			$(this).parent().next().toggle();
			$(this).parent().next().next().toggle();
			$(this).parent().toggle();
			$(this).val("chose2");
		}
		
	});
	$("#admin #listSource").click(function(){
		$(this).parent().prev().toggle();
		$(this).parent().prev().prev().find("select").val("chose2");
		$(this).parent().prev().prev().toggle();
		$(this).parent().toggle();
	});
	
	$("#admin #dest").change(function(){
		if($(this).val() == "other3")
		{
			$(this).parent().parent().next().toggle();
			$(this).parent().parent().next().next().toggle();
			$(this).parent().parent().toggle();
			$(this).val("chose3");
		}
		
	});
	$("#admin #listDestination").click(function(){
		$(this).parent().prev().toggle();
		$(this).parent().prev().prev().find("select").val("chose3");
		$(this).parent().prev().prev().toggle();
		$(this).parent().toggle();
	});
	
	$("#transport li").click(function(){
		var id, username;
		$(".transporter").hide();
		$(this).next().toggle();
		id = $(this).attr("id");
		username = $(this).attr("name");
		if(id == "viewTrucks"){
			var div=$(this).next();
			$.ajax({
				url: "/RNA/ViewData",
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
		if($("#trucktype").val() == "chose" || $("#trucktype").val() == null)
		{
			$("#truckType").text("Chose a truck type !");
			flag=1;
		}
		if($("#source").val() == "chose2" || $("#source").val() == null)
		{	
			$("#sourceName").text("Chose a source !");
			flag=1;
		}
		
		if($("#dest").val() == "chose3" || $("#dest").val() == null)
		{	
			$("#destinationName").text("Chose a destination !");
			flag=1;
		}
		$("input[type=text]").each(function(){
			if($(this).val() == "")
			{
				$(this).parent().next().children().text("Please fill in this field !");
				flag=1;
			}
		});
		
		if($("#dest").val() == $("#source").val() &&  $("#source").val() != null){
			alert("Source and Destination can't be same !")
			flag=1;
		}
		if(flag == 0){
			
			 var username = $("#userName").val();
			 var div = $(this).parent();
			 $.ajax({
					url: "/RNA/registerTruck",
					type: "POST",
					data: "destinationName="+$("#dest").val()+"&trucktype="+$("#trucktype").val()+"&pan="+$("#pan").val()+"&sourceName="+$("#source").val()+"&reg="+$("#reg-no").val()+"&driver_name="+$("#name").val()+"&driver_mobile="+$("#mobile").val()+"&userName="+username,
					success: function(data){
						$("span.message").text(data);
						setTimeout(function(){ 
							div.toggle(); 
							}, 5000);
						
					}
				});
		}
	});
	$("#transport input, #transport select, #admin input, #admin select").each(function(){
		$(this).focus(function(){
			$(this).parent().next().children().text("");
		});
	});
	$("#company li").click(function(){
		
		var id, c_id;
		$(".company").hide();
		$(this).next().toggle();
		id = $(this).attr("id");
		c_id = $("#c_id").val();
		
		if(id == "viewBooking"){
			var div=$(this).next();
			$.ajax({
				url: "/RNA/ViewBooking",
				type: "POST",
				data: "c_id="+c_id,
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
				data: "c_id="+c_id,
				success: function(data){
					div.html(data);
					setTimeout(function(){ 
						$(this).parent().toggle(); 
						}, 5000);
					
				}
			});
		}
	});
	
	
	$(".company #source").change(function(){
		
		id1="#sourceLocation";
		first = "sourceId";
		second = "destinationId";
		id2 = "#dest";
			
		var source = $(".company #source").val(); 
		
		$(this).parent().parent().next().show();
		$(this).parent().parent().next().find(".sourceLocation").show();
		$(this).parent().parent().next().find(".srcaddressAdd").hide();
		$(".company #dest").attr("disabled",false);
		$.ajax({
			url: "/RNA/bookTruckFunction1",
			type: "POST",
			data: "param1="+first+"&param2="+second+"&location="+$(this).val()+"&c_id="+$("#c_id").val(),
			success: function(data){
				$(id2).html(data);
			}
		});
		
		$.ajax({
			url: "/RNA/bookTruckFunction2",
			type: "POST",
			data: "c_id="+$("#c_id").val()+"&location="+$(this).val(),
			success: function(data){
				$("#sourceAddress").html(data);
			}
		});
		
	});
	
	$("td .srcaddAddress").click(function(){
		if($(this).text()=="Add Location")
		{
			$("#srcCity").val($(".company #source").val());
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
				url: "/RNA/bookTruckFunction3",
				type: "POST",
				data: "address="+address+"&city="+$("#srcCity").val()+"&c_id="+$("#c_id").val(),
				success: function(data){
					elem.text(data);
					$.ajax({
						url: "/RNA/bookTruckFunction2",
						type: "POST",
						data: "c_id="+$("#c_id").val()+"&location="+$("#srcCity").val(),
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
	
	$(".company #dest").change(function(){
			
		var dest = $(".company #dest").val(); 
		
		$(this).parent().parent().next().show();
		$(this).parent().parent().next().find(".destLocation").show();
		$(this).parent().parent().next().find(".destaddressAdd").hide();
		
		$.ajax({
			url: "/RNA/bookTruckFunction2",
			type: "POST",
			data: "c_id="+$("#c_id").val()+"&location="+$(this).val(),
			success: function(data){
				$("#destAddress").html(data);
			}
		});
		$.ajax({
			url: "/RNA/bookTruckFunction4",
			type: "POST",
			data: "c_id="+$("#c_id").val()+"&dest="+$(this).val()+"&source="+$("#source").val(),
			success: function(data){
				$("#trucktype").html(data);
			}
		});
		
	});
	
	$("td .destaddAddress").click(function(){
		if($(this).text()=="Add Location")
		{
			$("#destCity").val($(".company #dest").val());
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
			var elem1 = $(this).parent().parent().find(".destLocation");
			var elem2 = $(this).parent().parent().find(".destaddressAdd");
			address = address.substring(0, address.length);
			$.ajax({
				url: "/RNA/bookTruckFunction3",
				type: "POST",
				data: "address="+address+"&city="+$("#destCity").val()+"&c_id="+$("#c_id").val(),
				success: function(data){
					elem.text(data);
					$.ajax({
						url: "/RNA/bookTruckFunction2",
						type: "POST",
						data: "c_id="+$("#c_id").val()+"&location="+$("#destCity").val(),
						success: function(data){
							$("#destAddress").html(data);
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
		$(".company #sourceName, .company #destinationName, .company #srcloc, .company #destloc, .company #truckType, .company #bookingDate").text("");
	});
	
	$("#booktruck").click(function(){
		var flag=0;
		if($(".company #trucktype").val() == null)
		{
			$(".company #truckType").text("Chose a truck type !");
			flag=1;
		}
		if($(".company #source").val() == null)
		{	
			$(".company #sourceName").text("Chose a source !");
			flag=1;
		}
		
		if($(".company #dest").val() == null)
		{	
			$(".company #destinationName").text("Chose a destination !");
			flag=1;
		}

		if($(".company #destAddress").val() == null)
		{	
			$(".company #destloc").text("Chose a location for the above destination !");
			flag=1;
		}
		
		if($(".company #sourceAddress").val() == null)
		{	
			$(".company #srcloc").text("Chose a location for the above source !");
			flag=1;
		}
		
		if($(".company #dest").val() == $(".company #source").val() && $(".company #source").val() != null){
			alert("Source and Destination can't be same !")
			flag=1;
		}
		
		if($("#company #txtdate").val() == "")
		{	
			$(".company #bookingDate").text("Chose the date !");
			flag=1;
		}
		
		if(flag == 0){
			var x;
			 var div = $(this).parent();
			 $.ajax({
				url: "/RNA/bookTruck",
				type: "POST",
				data: "destination="+$(".company #dest").val()+"&trucktype="+$("#trucktype").val()+"&bookingDate="+$(".company #txtdate").val()+"&source="+$(".company #source").val()+"&srcloc="+$(".company #sourceAddress").val()+"&destloc="+$(".company #destAddress").val()+"&c_id="+$("#c_id").val(),
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
				if($(this).text() == "")
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
			data: "destination="+$(".company #dest").val()+"&trucktype="+$("#trucktype").val()+"&bookingDate="+$(".company #txtdate").val()+"&source="+$(".company #source").val()+"&srcloc="+$(".company #sourceAddress").val()+"&destloc="+$(".company #destAddress").val()+"&c_id="+$("#c_id").val(),
			success: function(data){
				$("span.message").text(data);
				setTimeout(function(){ 
					div.toggle(); 
				}, 5000);	
			}
		});
	}
}