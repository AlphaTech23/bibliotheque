<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.bibliotheque.models.Adherent" %>
<% 
    Adherent adherent = (Adherent)request.getSession().getAttribute("adherent"); 
    boolean isLoggedIn = adherent != null;
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Réservation d'exemplaire</title>
    <!-- Bootstrap CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <h2 class="mb-4 text-center">Réserver un exemplaire</h2>

            <!-- Messages d'alerte -->
            <% if(request.getAttribute("error") != null) { %>
                <div class="alert alert-danger">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <% if(request.getAttribute("success") != null) { %>
                <div class="alert alert-success">
                    <%= request.getAttribute("success") %>
                </div>
            <% } %>

            <form method="post" action="<%= request.getContextPath() %>/reservation/effectuer" 
                  class="border p-4 rounded bg-light shadow-sm">
                  
                <!-- Champ Adhérent -->
                <div class="mb-3">
                    <% if(isLoggedIn) { %>
                        <input type="hidden" name="adherentId" value="<%= adherent.getId() %>">
                    <% } else { %>
                        <label for="adherentId" class="form-label">Numéro d'adhérent:</label>
                        <input type="number" name="adherentId" id="adherentId" 
                               class="form-control" required>
                    <% } %>
                </div>

                <!-- Champ Date de réservation -->
                <div class="mb-3">
                    <label for="dateReservation" class="form-label">Date de réservation:</label>
                    <input type="date" name="dateReservation" id="dateReservation" 
                           class="form-control" required>
                </div>

                <!-- Champ Exemplaire -->
                <div class="mb-3">
                    <label for="exemplaireId" class="form-label">Numéro d'exemplaire:</label>
                    <input type="number" name="exemplaireId" id="exemplaireId" 
                           class="form-control" required>
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary">Effectuer la réservation</button>
                </div>

                <div class="mb-3">
                    <% if(isLoggedIn) { %>
                        <input type="hidden" name="valide" value="<%= 0 %>">
                    <% } else { %>
                        <input type="hidden" name="valide" value="<%= 1 %>">
                    <% } %>
                </div>
            </form>
        </div>
    </div>

    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>