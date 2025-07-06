<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.example.bibliotheque.models.*" %>

<html>
<head>
    <title>Réabonnement</title>
</head>
<body>
    <h2>Réabonnement Manuel</h2>

    <form method="post">
        <label for="adherentId">Adhérent :</label>
        <select name="adherentId">
            <%
                List<Adherent> adherents = (List<Adherent>) request.getAttribute("adherents");
                if (adherents != null) {
                    for (Adherent a : adherents) {
            %>
                <option value="<%= a.getId() %>"><%= a.getNom() %> (<%= a.getEmail() %>)</option>
            <%
                    }
                }
            %>
        </select><br><br>

        <label for="dateDebut">Date Début :</label>
        <input type="date" name="dateDebut" required /><br><br>

        <label for="dateFin">Date Fin :</label>
        <input type="date" name="dateFin" required /><br><br>

        <button type="submit">Réabonner</button>
    </form>

    <%
        String message = (String) request.getAttribute("message");
        String error = (String) request.getAttribute("error");

        if (message != null) {
    %>
        <div style="color:green"><%= message %></div>
    <%
        }

        if (error != null) {
    %>
        <div style="color:red"><%= error %></div>
    <%
        }
    %>
</body>
</html>
