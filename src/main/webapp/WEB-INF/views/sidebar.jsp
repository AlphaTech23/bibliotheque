<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% 
    String currentPath = request.getServletPath();
    boolean isBibliothecaire = session.getAttribute("bibliothecaire") != null;
    boolean isAdherent = session.getAttribute("adherent") != null;
%>

<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-lg-2 col-md-3 sidebar">
            <div class="position-sticky pt-3">
                <% if (isBibliothecaire || isAdherent) { %>
                    <ul class="nav flex-column">
                        <!-- Menu commun -->
                        <li class="nav-item">
                            <a class="nav-link <%= currentPath.equals("/livres") ? "active" : "" %>" 
                               href="${pageContext.request.contextPath}/livres">
                                <i class="bi bi-search me-2"></i>Recherche
                            </a>
                        </li>

                        <!-- Menu bibliothécaire -->
                        <% if (isBibliothecaire) { %>
                            <li class="nav-item">
                                <a class="nav-link <%= currentPath.equals("/reservation") ? "active" : "" %>" 
                                   href="${pageContext.request.contextPath}/reservation/historique">
                                    <i class="bi bi-calendar-check me-2"></i>Réservations
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <%= currentPath.equals("/abonnements") ? "active" : "" %>" 
                                   href="${pageContext.request.contextPath}/abonnements">
                                    <i class="bi bi-people me-2"></i>Adherents
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <%= currentPath.equals("/abonnements/renew") ? "active" : "" %>" 
                                   href="${pageContext.request.contextPath}/abonnements/renew">
                                    <i class="bi bi-arrow-repeat me-2"></i>Réabonnements
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <%= currentPath.equals("/prets/historique") ? "active" : "" %>" 
                                   href="${pageContext.request.contextPath}/prets/historique">
                                    <i class="bi bi-clock-history me-2"></i>Prêts
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <%= currentPath.equals("/penalites") ? "active" : "" %>" 
                                   href="${pageContext.request.contextPath}/penalites">
                                    <i class="bi bi-exclamation-triangle me-2"></i>Penalités
                                </a>
                            </li>
                        <% } %>

                        <!-- Menu adhérent -->
                        <% if (isAdherent) { %>
                            <li class="nav-item">
                                <a class="nav-link <%= currentPath.equals("/prets/historique") ? "active" : "" %>" 
                                   href="${pageContext.request.contextPath}/prets/historique">
                                    <i class="bi bi-clock-history me-2"></i>Mes prêts
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <%= currentPath.equals("/reservation/historique") ? "active" : "" %>" 
                                   href="${pageContext.request.contextPath}/reservation/historique">
                                    <i class="bi bi-bookmark me-2"></i>Mes réservations
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <%= currentPath.equals("/penalites") ? "active" : "" %>" 
                                   href="${pageContext.request.contextPath}/penalites">
                                    <i class="bi bi-exclamation-triangle me-2"></i>Mes penalités
                                </a>
                            </li>
                        <% } %>
                    </ul>

                    <div class="nav-divider"></div>

                    <!-- Actions rapides -->
                    <ul class="nav flex-column">
                        <% if (isBibliothecaire) { %>
                            <li class="nav-item">
                                <a class="nav-link <%= currentPath.equals("/prets") ? "active" : "" %>" 
                                   href="${pageContext.request.contextPath}/prets">
                                    <i class="bi bi-plus-circle me-2"></i>Nouveau prêt
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <%= currentPath.equals("/pret/retour") ? "active" : "" %>" 
                                   href="${pageContext.request.contextPath}/prets/retour">
                                    <i class="bi bi-arrow-return-left me-2"></i>Retour prêt
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <%= currentPath.equals("/prolongement") ? "active" : "" %>" 
                                   href="${pageContext.request.contextPath}/prolongement">
                                    <i class="bi bi-calendar2-plus me-2"></i>Prolongement
                                </a>
                            </li>
                        <% } else { %>
                            <li class="nav-item">
                                <a class="nav-link <%= currentPath.equals("/reservation") ? "active" : "" %>" 
                                   href="${pageContext.request.contextPath}/reservation">
                                    <i class="bi bi-plus-circle me-2"></i>Nouvelle réservation
                                </a>
                            </li>
                        <% } %>
                    </ul>

                    <div class="nav-divider"></div>

                    
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                                <i class="bi bi-box-arrow-right me-1"></i>Déconnexion
                            </a>
                        </li>
                    </ul>
                <% } %>
            </div>
        </div>

        <!-- Main Content -->
        <main class="col-lg-10 col-md-9 ms-sm-auto px-md-4 main-content">