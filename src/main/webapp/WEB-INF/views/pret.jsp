<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Effectuer un prêt</title>
</head>
<body>
<h2>Effectuer un prêt</h2>

<c:if test="${not empty error}">
    <div style="color: red;">
        ${error}
    </div>
</c:if>

<c:if test="${not empty success}">
    <div style="color: green;">
        ${success}
    </div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/prets/effectuer">
    <label for="adherentId">Adhérent :</label><br/>
    <input type="number" name="adherentId" required/><br/><br/>

    <label for="exemplaireId">Exemplaire :</label><br/>
    <input type="number" name="exemplaireId" required/><br/><br/>

    <label for="typePretId">Type de prêt :</label><br/>
    <input type="number" name="typePretId" required/><br/><br/>

    <input type="submit" value="Effectuer le prêt"/>
</form>
</body>
</html>
