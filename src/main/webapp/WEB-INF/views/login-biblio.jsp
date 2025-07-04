<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Connexion Bibliothécaire</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body class="bg-light">
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header bg-primary text-white">
                            <h4 class="card-title text-center">Connexion Bibliothécaire</h4>
                        </div>
                        <div class="card-body">
                            <form action="<%= request.getContextPath() %>/bibliothecaire/login" method="post">
                                <% if (request.getAttribute("error") !=null) { %>
                                    <div class="alert alert-danger">
                                        <%= request.getAttribute("error") %>
                                    </div>
                                    <% } %>
                                        <div class="mb-3">
                                            <label for="nom" class="form-label">Nom</label>
                                            <input type="text" class="form-control" id="nom" name="nom" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="motDePasse" class="form-label">Mot de passe</label>
                                            <input type="password" class="form-control" id="motDePasse"
                                                name="motDePasse" required>
                                        </div>
                                        <div class="d-grid">
                                            <button type="submit" class="btn btn-primary">Se connecter</button>
                                        </div>
                                        <div class="mt-3 text-right">
                                            <a href="${pageContext.request.contextPath}/login">Se
                                                connecter en tant qu'adherent</a>
                                        </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>