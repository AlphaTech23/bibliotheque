INSERT INTO bibliothecaire (nom, mot_de_passe) VALUES
('admin', 'admin123');

INSERT INTO type_adherent (id, libelle, quota_exemplaire, duree_pret, duree_prolongement, duree_penalite, quota_reservation)
VALUES (1, 'Standard', 3, 7, 3, 5, 5);

-- Adhérents
INSERT INTO adherent (id, nom, email, mot_de_passe, type_id, date_naissance)
VALUES 
(1, 'Alice Dupont', 'alice@example.com', 'pass', 1, '2000-05-20'),
(2, 'Bob Martin', 'bob@example.com', 'pass', 1, '1995-03-15');

-- Livres
INSERT INTO livre (id, nom, restriction)
VALUES 
(1, 'Java pour les nuls', 12),
(2, 'Spring Boot avancé', 18),
(3, 'Contes pour enfants', 0);

-- Exemplaires
INSERT INTO exemplaire (id, livre_id)
VALUES 
(1, 1), (2, 1),
(3, 2), (4, 2),
(5, 3);

-- Bibliothécaire
INSERT INTO bibliothecaire (id, nom, mot_de_passe)
VALUES (1, 'Admin', 'adminpass');

-- États exemplaire
INSERT INTO etat_exemplaire (id, libelle)
VALUES (1, 'Bon état'), (2, 'Perdu'), (3, 'Détérioré');

-- Statuts (tous bons)
INSERT INTO statut_exemplaire (exemplaire_id, etat_id, bibliothecaire_id, date_changement)
VALUES 
(1, 1, 1, '2025-06-30'),
(2, 1, 1, '2025-06-30'),
(3, 1, 1, '2025-06-30'),
(4, 1, 1, '2025-06-30'),
(5, 1, 1, '2025-06-30');

-- Type de prêt
INSERT INTO type_pret (id, libelle)
VALUES (1, 'standard');

-- Prêts en cours
INSERT INTO pret (id, adherent_id, exemplaire_id, date_pret, date_retour, type_id)
VALUES 
(1, 1, 1, '2025-07-01', NULL, 1), -- Java pour les nuls - exemplaire 1
(2, 2, 3, '2025-07-02', NULL, 1); -- Spring Boot avancé - exemplaire 3

-- Prolongement pour prêt 2
INSERT INTO prolongement (id, pret_id)
VALUES (1, 2);