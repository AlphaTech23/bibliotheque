<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
    <title>Créer un adhérent</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h2><i class="bi bi-person-plus me-2"></i>Créer un adhérent</h2>
            </div>

            <div class="card card-form">
                <div class="card-header bg-white">
                    <h5 class="mb-0"><i class="bi bi-card-checklist me-2"></i>Informations adhérent</h5>
                </div>
                <div class="card-body">
                    <form method="post" action="abonnements">
                        <div class="row g-3">
                            <div class="mb-3">
                                <label class="form-label">Nom</label>
                                <input type="text" name="nom" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Email</label>
                                <input type="email" name="email" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Mot de passe</label>
                                <input type="password" name="motDePasse" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Type adhérent</label>
                                <select name="typeId" class="form-select" required>
                                    <option value="">-- Sélectionnez un type --</option>
                                    <% 
                                    List<TypeAdherent> typesAdherent = (List<TypeAdherent>) request.getAttribute("typesAdherent");
                                    for (TypeAdherent type : typesAdherent) { 
                                    %>
                                        <option value="<%= type.getId() %>"><%= type.getLibelle() %></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Date de naissance</label>
                                <input type="date" name="dateNaissance" class="form-control" required>
                            </div>
                        </div>

                        <hr class="my-4">

                        <h5 class="mb-3"><i class="bi bi-calendar-range me-2"></i>Abonnement</h5>
                        <div class="row g-3">
                            <div class="mb-3">
                                <label class="form-label">Date début</label>
                                <input type="date" name="dateDebut" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Date fin</label>
                                <input type="date" name="dateFin" class="form-control" required>
                            </div>
                        </div>

                        <div class="mt-4">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-save me-2"></i>Enregistrer
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <%-- Messages de feedback --%>
            <%@ include file="alerts.jsp" %>

    <%@ include file="footer.jsp" %>