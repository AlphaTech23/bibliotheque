INSERT INTO bibliothecaire (nom, mot_de_passe) VALUES
('admin', 'admin123');

INSERT INTO type_adherent (libelle, quota_exemplaire, duree_pret, duree_prolongement, duree_penalite, quota_reservation, post_prolongement)
VALUES
('Étudiant', 2, 7, 3, 5, 2, 2),
('Enseignant', 5, 14, 5, 10, 3, 3);


INSERT INTO adherent (nom, email, mot_de_passe, type_id, date_naissance)
VALUES
('Alice', 'alice@example.com', 'passalice', 1, '2010-01-15'), -- mineure
('Bob', 'bob@example.com', 'passbob', 2, '1990-06-25');       -- adulte


-- Actif : aujourd'hui est 2025-07-04 par ex
INSERT INTO abonnement (adherent_id, date_debut, date_fin)
VALUES
(1, '2025-01-01', '2025-12-31'), -- Alice (actif)
(2, '2023-01-01', '2023-12-31'); -- Bob (expiré)

INSERT INTO livre (nom, restriction) VALUES
('Livre Enfant', 10),
('Livre Adulte', 18);

INSERT INTO exemplaire (livre_id) VALUES
(1), -- exemplaire 1 → Livre Enfant
(2); -- exemplaire 2 → Livre Adulte

INSERT INTO etat_exemplaire (libelle) VALUES
('Bon état'),
('Détérioré'),
('Perdu');

INSERT INTO etat_exemplaire (libelle) VALUES
('Bon état'),
('Détérioré'),
('Perdu');

-- Exemplaire 1 en bon état
INSERT INTO statut_exemplaire (exemplaire_id, etat_id, bibliothecaire_id, date_changement)
VALUES (1, 1, 1, '2025-06-01');

-- Exemplaire 2 est détérioré
INSERT INTO statut_exemplaire (exemplaire_id, etat_id, bibliothecaire_id, date_changement)
VALUES (2, 2, 1, '2025-06-01');

INSERT INTO type_pret (libelle) VALUES
('Normal'),
('Sur place');

-- Alice emprunte exemplaire 1 (Livre Enfant)
INSERT INTO pret (adherent_id, exemplaire_id, date_pret, date_retour, type_id)
VALUES (1, 1, '2025-06-20', NULL, 1);

-- Alice réserve le même exemplaire le jour du prêt
INSERT INTO reservation (adherent_id, exemplaire_id, date_reservation, valide)
VALUES (1, 1, '2025-07-04', TRUE);

-- Bob (adulte) a une pénalité active
INSERT INTO penalite (adherent_id, date_debut, motif)
VALUES (2, '2025-07-01', 'Retard de retour');
