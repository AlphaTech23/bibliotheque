<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
    <title>Nouveau prêt</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h2><i class="bi bi-journal-arrow-down me-2"></i>Nouveau prêt</h2>
            </div>

            <div class="card card-form">
                <div class="card-body">
                    <%-- Messages d'erreur/succès --%>
                    <%@ include file="alerts.jsp" %>

                    <form method="post" action="<%= request.getContextPath() %>/prets/effectuer">
                        <div class="row g-3">
                            <div class="mb-3">
                                <label for="DatePret" class="form-label">Date de prêt</label>
                                <input type="date" name="datePret" id="DatePret" class="form-control" required>
                            </div><!-- Remplacer les 3 inputs par des selects -->
                            <div class="mb-3">
                                <label class="form-label">Adhérent</label>
                                <select name="adherentId" class="form-select" required>
                                    <option value="">-- Sélectionnez un adhérent --</option>
                                    <% 
                                    List<Adherent> adherents = (List<Adherent>) request.getAttribute("adherents");
                                    for (Adherent a : adherents) { 
                                    %>
                                        <option value="<%= a.getId() %>"><%= a.getNom() %></option>
                                    <% } %>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Exemplaire</label>
                                <select name="exemplaireId" class="form-select" required>
                                    <option value="">-- Sélectionnez un exemplaire --</option>
                                    <% 
                                    List<Exemplaire> exemplaires = (List<Exemplaire>) request.getAttribute("exemplaires");
                                    for (Exemplaire exemplaire : exemplaires) { 
                                    %>
                                        <option value="<%= exemplaire.getId() %>">
                                            <%= exemplaire.getLivre().getNom() %> (Ex. <%= exemplaire.getId() %>)
                                        </option>
                                    <% } %>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Type de prêt</label>
                                <select name="typePretId" class="form-select" required>
                                    <option value="">-- Sélectionnez un type --</option>
                                    <% 
                                    List<TypePret> typesPret = (List<TypePret>) request.getAttribute("typesPret");
                                    for (TypePret type : typesPret) { 
                                    %>
                                        <option value="<%= type.getId() %>"><%= type.getLibelle() %></option>
                                    <% } %>
                                </select>
                            </div>
                        </div>

                        <div class="mt-4">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-check-circle me-2"></i>Enregistrer le prêt
                            </button>
                        </div>
                    </form>
                </div>
            </div>

    <%@ include file="footer.jsp" %>