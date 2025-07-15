<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
    <title>Connexion</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>

            <div class="d-flex justify-content-center">
                <div class="card card-form col-md-8 col-lg-6 p-4">
                    <div class="card-header bg-white border-0 text-center">
                        <h3><i class="bi bi-box-arrow-in-right me-2"></i>Connexion Adhérent</h3>
                    </div>
                    <div class="card-body">
                        <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger">
                                <i class="bi bi-exclamation-triangle-fill"></i> ${error}
                            </div>
                        <% } %>

                        <form method="post" action="${pageContext.request.contextPath}/login">
                            <div class="mb-3">
                                <label class="form-label">Email</label>
                                <input type="email" name="email" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Mot de passe</label>
                                <input type="password" name="motDePasse" class="form-control" required>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">
                                <i class="bi bi-box-arrow-in-right me-2"></i>Se connecter
                            </button>
                        </form>

                        <div class="mt-3 text-center">
                            <a href="${pageContext.request.contextPath}/inscription" class="text-decoration-none">
                                <i class="bi bi-person-plus me-1"></i> Créer un compte
                            </a>
                        </div>
                        <div class="mt-2 text-center">
                            <a href="${pageContext.request.contextPath}/bibliothecaire/login" class="text-decoration-none">
                                <i class="bi bi-person-vcard me-1"></i> Espace bibliothécaire
                            </a>
                        </div>
                    </div>
                </div>
            </div>

    <%@ include file="footer.jsp" %>