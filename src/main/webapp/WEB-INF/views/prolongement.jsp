<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Prolonger un prêt</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-5">

    <h2 class="mb-4">Prolonger un prêt</h2>

    
    <!-- Message d'erreur -->
    <% if(request.getAttribute("error") != null) { %>
        <div class="alert alert-danger">
            <%= request.getAttribute("error") %>
        </div>
    <% } %>

    <!-- Message de succès -->
    <% if(request.getAttribute("success") != null) { %>
        <div class="alert alert-success">
            <%= request.getAttribute("success") %>
        </div>
    <% } %>

    <form method="post" action="<%= request.getContextPath() %>/prolongement/effectuer" class="border p-4 rounded shadow">
        <div class="mb-3">
            <label for="pretId" class="form-label">ID du prêt :</label>
            <input type="number" name="pretId" class="form-control" required/>
        </div>

        <div class="mb-3">
            <label for="dateProlongement" class="form-label">Date du prolongement :</label>
            <input type="date" name="dateProlongement" class="form-control" required/>
        </div>

        <button type="submit" class="btn btn-primary">Prolonger le prêt</button>
    </form>

</body>
</html>
