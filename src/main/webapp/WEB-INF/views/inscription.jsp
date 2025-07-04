<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bibliotheque.models.TypeAdherent" %>
<%
    List<TypeAdherent> types = (List<TypeAdherent>) request.getAttribute("types");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-sm">
                <div class="card-header text-center bg-success text-white">
                    <h4>Créer un compte</h4>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/inscription">
                        <div class="mb-3">
                            <label class="form-label">Nom</label>
                            <input type="text" name="nom" class="form-control" required/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" required/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mot de passe</label>
                            <input type="password" name="motDePasse" class="form-control" required/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Date de naissance</label>
                            <input type="date" name="dateNaissance" class="form-control" required/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Type d’adhérent</label>
                            <select name="typeAdherent.id" class="form-select" required>
                                <option value="">-- Choisir un type --</option>
                                <%
                                    if (types != null) {
                                        for (TypeAdherent t : types) {
                                %>
                                <option value="<%= t.getId() %>"><%= t.getLibelle() %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-success w-100">S’inscrire</button>
                    </form>

                    <div class="mt-3 text-center">
                        <a href="${pageContext.request.contextPath}/login">Déjà inscrit ? Se connecter</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
