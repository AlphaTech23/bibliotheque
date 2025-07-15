<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    List<Adherent> adherents = (List<Adherent>) request.getAttribute("adherents");
%>
    <title>Réabonnement</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h2><i class="bi bi-arrow-repeat me-2"></i>Réabonnement et abonnements</h2>
            </div>

            <div class="card card-form">
                <div class="card-body">
                    <form method="post">
                        <div class="mb-3">
                            <label for="adherentId" class="form-label">Adhérent</label>
                            <select name="adherentId" class="form-select" id="adherentId">
                                <% for (Adherent a : adherents) { %>
                                    <option value="<%= a.getId() %>"><%= a.getNom() %> (<%= a.getEmail() %>)</option>
                                <% } %>
                            </select>
                        </div>

                        <div class="row g-3">
                            <div class="col-md-6">
                                <label for="dateDebut" class="form-label">Date Début</label>
                                <input type="date" name="dateDebut" class="form-control" id="dateDebut" required>
                            </div>
                            <div class="col-md-6">
                                <label for="dateFin" class="form-label">Date Fin</label>
                                <input type="date" name="dateFin" class="form-control" id="dateFin" required>
                            </div>
                        </div>

                        <div class="mt-4">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-check-circle me-2"></i>Valider le réabonnement
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <%-- Messages de feedback --%>
            <%@ include file="alerts.jsp" %>

    <%@ include file="footer.jsp" %>