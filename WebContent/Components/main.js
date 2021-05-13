$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//Save
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validateCustomerForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid
	var check = ($("#hidcustomerIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "CustomerApI",
		type : check,
		data : $("#formCustomer").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onCustomerSaveComplete(response.responseText, status);
			console.log(response);
		}
	});
}); 

function onCustomerSaveComplete(response, status){
	if(status == "success")
	{
		console.log(response +  " "+status);
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
			console.log("dataaaaaa");
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	
	
	$("#hidcustomerIDSave").val("");
	$("#formCustomer")[0].reset();
}



//Validation
function validateCustomerForm() {  
	// NAME  
	if ($("#name").val().trim() == "")  {   
		return "Insert fullName.";  
		
	} 
	
	 // Email 
	if ($("#email").val().trim() == "")  {   
		return "Insert Email.";  
	} 
	
	
	// NIC  
	if ($("#nic").val().trim() == "")  {   
		return "Insert NIC."; 
		 
	}
	 
	 //set to number
	var CusMobile = $("#mobile").val().trim();  
	if (!$.isNumeric(CusMobile))  {   
		return "Insert a numerical value for Mobile Number.";  
		
	}
	 
	
	 
	 return true; 
	 
}

$(document).on("click", ".btnUpdate", function(event) 
		{     
	$("#hidcustomerIDSave").val($(this).closest("tr").find('#hidcustomerIDUpdate').val());     
	$("#name").val($(this).closest("tr").find('td:eq(0)').text());    
	$("#email").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#nic").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#mobile").val($(this).closest("tr").find('td:eq(3)').text()); 
	

});


//Remove 
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "CustomerApI",
		type : "DELETE",
		data : "customerID=" + $(this).data("customerid"),
		dataType : "text",
		complete : function(response, status)
		{
			onCustomerDeletedComplete(response.responseText, status);
		}
	});
});

function onCustomerDeletedComplete(response, status)
{
	if(status == "success")
	{
		console.log(response);
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divCustomerGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}