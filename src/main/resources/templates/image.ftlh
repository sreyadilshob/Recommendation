<!DOCTYPE>
<html>
<head>
<title> image</title>
</head>
<body>
<#list externalApi as externalApi>
	<img src="${externalApi.message!}" width="250" height="300">
</#list>
</body>
</html>