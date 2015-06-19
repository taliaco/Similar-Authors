var AuthorsByName = [];

/**search recipes by name*/
//$(document).ready( function() {
//$('#searchButton').click(function() {
//var author = $('#searchText').val();		
//var dataString = "name=" + author;
//$.ajax({
//url: "/makeRecommendation",
//type: "POST",
//data: dataString,
//success: function(ret){ 
//if(ret=="0" || ret==""){
//alert(author + " לא קיים במערכת");
//}
//else{
//$('#result').text("");
//AuthorsByName = ret.split('*');
//var i=0;

//for (i=1; i< AuthorsByName.length; i++)
//{
//$('#result').append("<p>" + (i) + ". " + AuthorsByName[i-1]+"</p>");
//}

//}
//},
//error: function(xhr, status, error) {
//alert("ajax failed");
//}
//});
//});
//});
$(document).ready( function() {
	$('#searchButton').click(function() {
		$('#result').empty();
		var author = $('#searchText').val();		
		var dataString = "name=" + author;
		$.ajax({
			url: "/makeRecommendation",
			type: "POST",
			data: dataString,
			dataType: "json",
			success: function(ret, textStatus, jqXHR) {
				if(ret.success)
				{
					for (i=0; i< ret.authorsInfo.length; i++){
						$('#author'+i).empty();
						$('#score'+i).empty();
						$('#topic'+i+'0').empty();
						$('#topic'+i+'1').empty();
					}
					for (i=0; i< ret.authorsInfo.length; i++){
						$('#author'+i).append(ret.authorsInfo[i]._author._name);
						$('#score'+i).append(ret.authorsInfo[i]._score);
						for (k=0; k< ret.authorsInfo[i]._authorTopicsVec.length; k++){
							$('#topic'+i+'0').append( (k+1) + ". " + ret.authorsInfo[i]._authorTopicsVec[k]._topicName + "</br>");
							$('#topic'+i+'1').append(ret.authorsInfo[i]._authorTopicsVec[k]._topicWeight.toFixed(2) + "</br>");
						}
					}
					$('#result').append("<div>");
					$('#result').append("<p>" + ret.authorsInfo[0]._author._name+"</p>");
					$('#result').append("<p>");
					for (j=1; j< ret.authorsInfo[0]._author._authorBooks.length; j++){
						$('#result').append("&emsp;&emsp;" + (j) + ". " + ret.authorsInfo[0]._author._authorBooks[j]._bookName+"</br>");
						for (k=0; k< ret.authorsInfo[0]._author._authorBooks[j]._bookTopicList.length; k++){
							$('#result').append("&emsp;&emsp;&emsp;&emsp;" + (k+1) + ". " + ret.authorsInfo[0]._author._authorBooks[j]._bookTopicList[k]._topicName+"</br>");
						}
					}
					$('#result').append("</p>");
					$('#result').append("</div>");
					for (i=1; i< ret.authorsInfo.length; i++){
						$('#result').append("<div>");
						$('#result').append("<p>" + (i) + ". " + ret.authorsInfo[i]._author._name+"</p>");
						$('#result').append("<p>");
						for (j=0; j< ret.authorsInfo[i]._author._authorBooks.length; j++){
							$('#result').append("&emsp;&emsp;" + (j+1) + ". " + ret.authorsInfo[i]._author._authorBooks[j]._bookName+"</br>");
							for (k=0; k< ret.authorsInfo[i]._author._authorBooks[j]._bookTopicList.length; k++){
								$('#result').append("&emsp;&emsp;&emsp;&emsp;" + (k+1) + ". " + ret.authorsInfo[i]._author._authorBooks[j]._bookTopicList[k]._topicName+"</br>");
							}
						}
						$('#result').append("</p>");
						$('#result').append("</div>");
					}


				}
				else{
					alert(author + " לא קיים במערכת");
				}
			},
			error: function(xhr, status, error) {
				alert("ajax failed");
			}
		});
	});
});