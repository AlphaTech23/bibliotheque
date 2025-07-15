<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<TypeAdherent> types = (List<TypeAdherent>) request.getAttribute("types");
%>
<%@ include file="header.jsp" %>
    <title>Inscription</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>

            <div class="d-flex justify-content-center">
                <div class="card card-form col-lg-8">
                    <div class="card-header bg-white text-center">
                        <h3><i class="bi bi-person-plus me-2"></i>Création de compte</h3>
                    </div>
                    <div class="card-body">
                        <form method="post" action="${pageContext.request.contextPath}/inscription">
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label class="form-label">Nom</label>
                                    <input type="text" name="nom" class="form-control" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Email</label>
                                    <input type="email" name="email" class="form-control" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Mot de passe</label>
                                    <input type="password" name="motDePasse" class="form-control" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Date de naissance</label>
                                    <input type="date" name="dateNaissance" class="form-control" required>
                                </div>
                                <div class="col-12">
                                    <label class="form-label">Type d'adhérent</label>
                                    <select name="typeAdherent.id" class="form-select" required>
                                        <option value="">-- Choisir un type --</option>
                                        <% for (TypeAdherent t : types) { %>
                                            <option value="<%= t.getId() %>"><%= t.getLibelle() %></option>
                                        <% } %>
                                    </select>
                                </div>
                            </div>

                            <div class="mt-4">
                                <button type="submit" class="btn btn-success w-100">
                                    <i class="bi bi-person-check me-2"></i>S'inscrire
                                </button>
                            </div>
                        </form>

                        <div class="mt-3 text-center">
                            <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">
                                <i class="bi bi-box-arrow-in-right me-1"></i> Déjà inscrit ? Se connecter
                            </a>
                        </div>
                    </div>
                </div>
            </div>

    <%@ include file="footer.jsp" %>