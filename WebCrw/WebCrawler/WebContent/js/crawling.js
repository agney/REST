// The root URL for the RESTful services
var rootURL = "http://localhost:8080/WebCrawlerBackend/webcrawler/crawlingService/";
var validatorServiceURL = "http://localhost:8080/WebCrawlerBackend/webcrawler/urlService/";

//Global counter for call back
var count = 0;

//URL and status arrays
var urlArray = new Array();
var statusArray = new Array();
var authKey;

window.setInterval(function(){
		getAllStatus();
}, 120000);  //for Health check after every 2 minutes.

$(document).ready(function() {
	/*authKey = sessionStorage.getItem('auth-key');
	alert('Hiiiiiiii'+authKey+'XXX');
	*/
	if(sessionStorage.getItem('auth-key')==null)
	{
		//alert('Inside Exit '+authKey+' XXX')
		//authKey = sessionStorage.getItem('auth-key');
		//console.log('Null Auth-Key'+authKey);
		location.href = "http://localhost:8080/WebCrawler/index.html";
	}
	else
	{
		$('#userheader').text('Welcome '+sessionStorage.getItem("user"));
		//console.log('Auth-Key'+authKey);
		getAllURL();
	}	
});

$('#addBtn').click(function() {
	if(urlArray.length > 9) {
		alert("You can not crawl for more than 10 URLs !!!");
		return;
	}
	isValidURL();
});

$('#logoutBtn').click(function() {
	if(sessionStorage.getItem('auth-key')==null)
	{
		alert('Please Login!!!');
		return;
	}
	clearSession();
});

//This function will be called after every 2 minutes.
function getAllStatus() {
	if(sessionStorage.getItem('auth-key')==null)
	{
		//console.log('inside get All Status');
		alert('Please Login!!!');
		return;
	}
	var length = urlArray.length;
	count = 0;
	for (var i = 0; i < length; i++) {
		getURLStatus(i, urlArray[i]);
	}
}


function isValidURL() {
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: validatorServiceURL+ "isValidURL",
		dataType: "text",
		data: urlToJSON(),
		success: function(data, textStatus, jqXHR)
		{
			console.log('Inside Success of isValidURL');
			if(urlArray.indexOf($('#url').val()) == -1)
			{
				addURL();
			}
			else
			{
				alert('URL Already Exist in the table, Try different one !!!');
			}
			console.log(textStatus + "-->" +data);			
		},
		error: function(jqXHR, textStatus, errorThrown){
			isValid = 0;
			console.log('Inside Failure of isValidURL');
			alert('inValid URL, please add Proper URL : ' + textStatus +' '+ errorThrown);
		}
	});
}

//This function will be called when user click on Add Button
function addURL() {
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL+ "addUrl",
		dataType: "text",
		data: urlToJSON(),
		success: function(data, textStatus, jqXHR){
			console.log($('#url').val() + "-->" +data);
			var length = urlArray.length;
			urlArray[length] = $('#url').val();
			statusArray[length] = data;
			//alert('URL Added successfully');
			printURLTable();
			//getAllURL();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('URL Addition Error: ' + textStatus +' '+ errorThrown);
		}
	});
}

//This func will be called only once when user land into crawler.html page.
//to fill the url and status arrays.
function getAllURL() {
	$.ajax({
		type: 'POST',
		contentType: 'text/plain',
		url: rootURL+ "getUrls",
		dataType: "text",
		data: sessionStorage.getItem('auth-key'),
		success: function(data, textStatus, jqXHR){
			//console.log("Plain ->" + data);
			var response = JSON.parse(data);
			//console.log("JSON ->" + response);
			//console.log("JSON Value ->" + response[0].url + "--" + response[0].status);
			//console.log("Data size" + response.length)
			var length =  response.length;
			for (var i = 0; i < length; i++) {
				urlArray[i] = response[i].url;
				statusArray[i] = response[i].status;
			}
			printURLTable();
			//alert('URL Fetched successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('URLs are Loading... ');
		}
	});
}

//This function is called to get status of single URL
function getURLStatus(index, url) {
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL+ "getUrlStatus",
		dataType: "text",
		data: url,
		success: function(data, textStatus, jqXHR){
			statusArray[index] = data;
			count++;
			if(count == urlArray.length)
				printURLTable();
			//alert('URL Status successfully fetched');
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('URL Status Fetched Error: ' + textStatus + ' '+errorThrown);
		}
	});
}

//This function is called to get status of single URL
function clearSession() {
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL+ "clearSession",
		dataType: "text",
		data: sessionStorage.getItem('auth-key'),
		success: function(data, textStatus, jqXHR){
			sessionStorage.clear();
			location.href = "http://localhost:8080/WebCrawler/index.html";
			//alert('Session cleared successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('Session clear Error: ' + textStatus +' '+errorThrown);
		}
	});
}

//This function will fill table URLs and status.
function printURLTable() {
	var tableContent = '';
	for (var i = 0; i < urlArray.length; i++) {
		//tableContent += '<tr><td class="text-center">'+ (i+1) +'</td><td class="text-center">' + urlArray[i] + '</td><td class="text-center">' +statusArray[i]+ '</td><td class="text-center">' + (statusArray[i] == 200?'Active':'InActive')+ '</td></tr>';
		tableContent += '<tr><td class="text-center">'+ (i+1) +'</td><td class="text-center"><a href="' + urlArray[i] + '" target="_blank">'+urlArray[i]+'</a></td><td class="text-center">' +statusArray[i]+ '</td><td class="text-center">' + ((statusArray[i] == 200 || statusArray[i] == 301)?'Active':'InActive')+ '</td></tr>';
	}
	var table = document.getElementById('urlTable');
	table.innerHTML = tableContent;
	console.log(table);
}

//Helper function to serialize all the form fields into a JSON string
function urlToJSON() {
	return JSON.stringify({
		"authKey": sessionStorage.getItem('auth-key'), 
		"url": $('#url').val()
		});
}