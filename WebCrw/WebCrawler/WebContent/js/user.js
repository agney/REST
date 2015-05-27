// The root URL for the RESTful services
var rootURL = "http://localhost:8080/WebCrawlerBackend/webcrawler/userService/";

window.onload = function(){  
	sessionStorage.clear();
	/*
	//alert('Inside index');
	var aKey = sessionStorage.getItem('auth-key');
	//alert(aKey);
	if(aKey!=null)
	{
		//console.log('Null Auth-Key'+aKey);
		alert('Press log-out Button to Exit From Dashboard');
		location.href = "http://localhost:8080/WebCrawler/crawler.html";
	}*/
};

$('#register').click(function() {
	location.href = "http://localhost:8080/WebCrawler/register.html";
});

$('#registerBtn').click(function() {
	if($('#username').val() == '' || $('#password').val() == '' || $('#email').val()=='')
	{
		alert('Either Username (or) Password (or) Email is Missing!!!');
		return;
	}
	register();
});

$('#login').click(function() {
	location.href = "http://localhost:8080/WebCrawler/index.html";
});

$('#loginBtn').click(function() {
	if($('#username').val() == '' || $('#password').val() == '')
	{
		alert('Either Username (or) Password is Missing!!!');
		return;
	}
	login();
});

function register() {
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL+ "doRegister",
		dataType: "json",
		data: registerToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('Registration Successfull');
			location.href = "http://localhost:8080/WebCrawler/index.html";
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('Registration error:  ' + textStatus +' '+ errorThrown);
		}
	});
}

function login() {
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL+ "doLogin",
		dataType: "text",
		data: loginToJSON(),
		success: function(data, textStatus, jqXHR){
			console.log(data + "----" + textStatus + "----" + jqXHR);
			var obj = JSON.parse(data);
			//console.log(obj.StatusCode);
			//console.log(obj.AuthKey);
			sessionStorage.setItem('auth-key',obj.AuthKey);
			sessionStorage.setItem("user", $('#username').val());
			//console.log('http://localhost:8080/WebCrawler/crawler.html');
			location.href = "http://localhost:8080/WebCrawler/crawler.html";
		},
		error: function(jqXHR, textStatus, errorThrown){
			console.log(jqXHR + "----" + textStatus + "----" + errorThrown);
			alert('Login error: ' + textStatus +' '+errorThrown);
		}
	});
}

//Helper function to serialize all the form fields into a JSON string
function registerToJSON() {
	return JSON.stringify({
		"username": $('#username').val(), 
		"password": $('#password').val(),
		"email": $('#email').val()
		});
}

//Helper function to serialize all the form fields into a JSON string
function loginToJSON() {
	return JSON.stringify({
		"username": $('#username').val(), 
		"password": $('#password').val()
		});
}
