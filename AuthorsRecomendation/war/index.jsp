

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
		<h1>Recommend on Similar Authors<br />
Based on Linked Data in Library
</h1>
		<div id="search">
			<input id="searchText" type="text" /> <br />
			<button id="searchButton">Search</button>
		</div>
		<br />
		<br />
		<div >
			<table id=tableMainAuthor>
				<thead>
					<tr>
						<th id="author0" colspan="2"></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td id="topic00" nowrap></td>
						<td id="topic01"></td>
					</tr>
				</tbody>
			</table>
			<br />
			<br />
			<table id=tableResult>
				<thead>
					<tr>
						<th id="author1" colspan="2"></th>
						<th id="author2" colspan="2"></th>
						<th id="author3" colspan="2"></th>
						<th id="author4" colspan="2"></th>
						<th id="author5" colspan="2"></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td id="score1" colspan="2"></td>
						<td id="score2" colspan="2"></td>
						<td id="score3" colspan="2"></td>
						<td id="score4" colspan="2"></td>
						<td id="score5" colspan="2"></td>
					</tr>
					<tr>
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
		<br />
		<br />
		<hr />
		<h3>Book List With Topics</h3>
		<hr />
		<div id="booksMainAuthor"></div>
		<br />
		<div id="booksResults"></div>
	</div>
</body>
</html>