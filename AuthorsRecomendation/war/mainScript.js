var AuthorsByName = [];

/**search recipes by name*/

$(document).ready( function() {
	$('#searchButton').click(function() {
		$('#booksMainAuthor').empty();
		$('#booksResults').empty();
		for (i=0; i< 6; i++){
			$('#author'+i).empty();
			$('#score'+i).empty();
			$('#topic'+i+'0').empty();
			$('#topic'+i+'1').empty();
		}
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
						$('#author'+i).append(ret.authorsInfo[i]._name);
						$('#score'+i).append(ret.authorsInfo[i]._score.toFixed(2));
						for (k=0; k< ret.authorsInfo[i]._authorTopicsVec.length; k++){
							$('#topic'+i+'0').append( (k+1) + ". " + ret.authorsInfo[i]._authorTopicsVec[k]._topicName + "</br>");
							$('#topic'+i+'1').append(ret.authorsInfo[i]._authorTopicsVec[k]._topicWeight.toFixed(2) + "</br>");
						}
					}
					$('#booksMainAuthor').append("<div>");
					$('#booksMainAuthor').append("<p>" + ret.authorsInfo[0]._name+"</p>");
					$('#booksMainAuthor').append("<p>");
					for (j=0; j< ret.authorsInfo[0]._authorBooks.length; j++){
						$('#booksMainAuthor').append("&emsp;&emsp;" + (j+1) + ". " + ret.authorsInfo[0]._authorBooks[j]._bookName+"</br>");
						for (k=0; k< ret.authorsInfo[0]._authorBooks[j]._bookTopicList.length; k++){
							$('#booksMainAuthor').append("&emsp;&emsp;&emsp;&emsp;" + (k+1) + ". " + ret.authorsInfo[0]._authorBooks[j]._bookTopicList[k]._topicName+"</br>");
						}
					}
					$('#booksMainAuthor').append("</p>");
					$('#booksMainAuthor').append("</div>");
					for (i=1; i< ret.authorsInfo.length; i++){
						$('#booksResults').append("<div>");
						$('#booksResults').append("<p>" + (i) + ". " + ret.authorsInfo[i]._name+"</p>");
						$('#booksResults').append("<p>");
						for (j=0; j< ret.authorsInfo[i]._authorBooks.length; j++){
							$('#booksResults').append("&emsp;&emsp;" + (j+1) + ". " + ret.authorsInfo[i]._authorBooks[j]._bookName+"</br>");
							for (k=0; k< ret.authorsInfo[i]._authorBooks[j]._bookTopicList.length; k++){
								$('#booksResults').append("&emsp;&emsp;&emsp;&emsp;" + (k+1) + ". " + ret.authorsInfo[i]._authorBooks[j]._bookTopicList[k]._topicName+"</br>");
							}
						}
						$('#booksResults').append("</p>");
						$('#booksResults').append("</div>");
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