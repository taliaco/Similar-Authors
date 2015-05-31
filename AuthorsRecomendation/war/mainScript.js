var AuthorsByName = [];

/**search recipes by name*/
$(document).ready( function() {
	$('#searchButton').click(function() {
		var author = $('#searchText').val();		
		var dataString = "name=" + author;
		$.ajax({
			url: "/makeRecommendation",
			type: "POST",
			data: dataString,
			success: function(ret){ 
				if(ret=="0" || ret==""){
					alert(author + " לא קיים במערכת");
				}
				else{
					$('#result').text("");
					AuthorsByName = ret.split('*');
					var i=0;
					
					for (i=1; i< AuthorsByName.length; i++)
					{
						$('#result').append("<p>" + (i) + ". " + AuthorsByName[i-1]+"</p>");
					}
					
				}
			},
			error: function(xhr, status, error) {
				alert("ajax failed");
			}
		});
	});
});