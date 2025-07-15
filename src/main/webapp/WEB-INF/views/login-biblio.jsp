<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
    <title>Connexion Bibliothécaire</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>

            <div class="d-flex justify-content-center">
                <div class="card card-form col-md-6">
                    <div class="card-header bg-white text-center">
                        <h3><i class="bi bi-person-vcard me-2"></i>Connexion Bibliothécaire</h3>
                    </div>
                    <div class="card-body">
                        <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i><%= request.getAttribute("error") %>
                            </div>
                        <% } %>

                        <form action="<%= request.getContextPath() %>/bibliothecaire/login" method="post">
                            <div class="mb-3">
                                <label for="nom" class="form-label">Nom</label>
                                <input type="text" class="form-control" id="nom" name="nom" required>
                            </div>
                            <div class="mb-3">
                                <label for="motDePasse" class="form-label">Mot de passe</label>
                                <input type="password" class="form-control" id="motDePasse" name="motDePasse" required>
                            </div>
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-box-arrow-in-right me-2"></i>Se connecter
                                </button>
                            </div>
                            <div class="mt-3 text-center">
                                <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">
                                    <i class="bi bi-person me-1"></i> Espace adhérent
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

    <%@ include file="footer.jsp" %>