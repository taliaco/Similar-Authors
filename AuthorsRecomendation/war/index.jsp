

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" language="javascript"
	src="/mainScript.js"></script>
<link rel="stylesheet" href="style.css" type="text/css" media="screen" />
<meta http-equiv="Content-Language" content="he" />
<title>Recommender Authors</title>
</head>
<body>
	<div id="mainContent">
		<h1>Recommendation Authors</h1>
		<div id="search">
			<input id="searchText" type="text" /> <br />
			<button id="searchButton">Search</button>
		</div>
		</br>
		</br>
		<div id=tableResult>
			<table style="width: 100%">
				<thead>
					<tr>
						<th id="author0" colspan="2"></th>
						<th id="author1" colspan="2"></th>
						<th id="author2" colspan="2"></th>
						<th id="author3" colspan="2"></th>
						<th id="author4" colspan="2"></th>
						<th id="author5" colspan="2"></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td id="score0" colspan="2"></td>
						<td id="score1" colspan="2"></td>
						<td id="score2" colspan="2"></td>
						<td id="score3" colspan="2"></td>
						<td id="score4" colspan="2"></td>
						<td id="score5" colspan="2"></td>
					</tr>
					<tr>
						<td id="topic00" nowrap></td>
						<td id="topic01"></td>
						<td id="topic10" nowrap></td>
						<td id="topic11"></td>
						<td id="topic20" nowrap></td>
						<td id="topic21"></td>
						<td id="topic30" nowrap></td>
						<td id="topic31"></td>
						<td id="topic40" nowrap></td>
						<td id="topic41"></td>
						<td id="topic50" nowrap></td>
						<td id="topic51"></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="result"></div>
	</div>
</body>
</html>