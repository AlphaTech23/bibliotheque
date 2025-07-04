-- Création de la base
DROP DATABASE IF EXISTS bibliotheque;
CREATE DATABASE IF NOT EXISTS bibliotheque;
USE bibliotheque;

-- Table bibliothecaire
CREATE TABLE bibliothecaire (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

-- Table type_adherent
CREATE TABLE type_adherent (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL,
    quota_exemplaire INT NOT NULL,
    duree_pret INT NOT NULL,
    duree_prolongement INT NOT NULL,
    duree_penalite INT NOT NULL,
    quota_reservation INT NOT NULL,
) ENGINE=InnoDB;

-- Table adherent
CREATE TABLE adherent (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    type_id INT NOT NULL,
    date_naissance DATE NOT NULL,
    FOREIGN KEY (type_id) REFERENCES type_adherent(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

-- Table livre
CREATE TABLE livre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(200) NOT NULL,
    restriction INT NOT NULL
) ENGINE=InnoDB;

-- Table exemplaire
CREATE TABLE exemplaire (
    id INT AUTO_INCREMENT PRIMARY KEY,
    livre_id INT NOT NULL,
    FOREIGN KEY (livre_id) REFERENCES livre(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Table etat_exemplaire
CREATE TABLE etat_exemplaire (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- Table statut_exemplaire
CREATE TABLE statut_exemplaire (
    id INT AUTO_INCREMENT PRIMARY KEY,
    exemplaire_id INT NOT NULL,
    etat_id INT NOT NULL,
    bibliothecaire_id INT NOT NULL,
    date_changement DATE NOT NULL,
    FOREIGN KEY (exemplaire_id) REFERENCES exemplaire(id) ON DELETE CASCADE,
    FOREIGN KEY (etat_id) REFERENCES etat_exemplaire(id) ON DELETE RESTRICT,
    FOREIGN KEY (bibliothecaire_id) REFERENCES bibliothecaire(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

-- Table abonnement
CREATE TABLE abonnement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    adherent_id INT NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    FOREIGN KEY (adherent_id) REFERENCES adherent(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Table type_pret
CREATE TABLE type_pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- Table pret
CREATE TABLE pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    adherent_id INT NOT NULL,
    exemplaire_id INT NOT NULL,
    date_pret DATE NOT NULL,
    date_retour DATE,
    type_id INT NOT NULL,
    FOREIGN KEY (adherent_id) REFERENCES adherent(id) ON DELETE RESTRICT,
    FOREIGN KEY (exemplaire_id) REFERENCES exemplaire(id) ON DELETE RESTRICT,
    FOREIGN KEY (type_id) REFERENCES type_pret(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

-- Table prolongement
CREATE TABLE prolongement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pret_id INT NOT NULL,
    FOREIGN KEY (pret_id) REFERENCES pret(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Table reservation
CREATE TABLE reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    adherent_id INT NOT NULL,
    exemplaire_id INT NOT NULL,
    date_reservation DATE NOT NULL,
    valide DATE,
    FOREIGN KEY (adherent_id) REFERENCES adherent(id) ON DELETE RESTRICT,
    FOREIGN KEY (exemplaire_id) REFERENCES exemplaire(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

-- Table penalite
CREATE TABLE penalite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    adherent_id INT NOT NULL,
    date_debut DATE,
    motif VARCHAR(100),
    FOREIGN KEY (adherent_id) REFERENCES adherent(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Table jour_ferier
CREATE TABLE jour_ferier (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_ferier DATE NOT NULL,
    evenement VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- Table jour_ouvrable
CREATE TABLE jour_ouvrable (
    id INT AUTO_INCREMENT PRIMARY KEY,
    jour_semaine VARCHAR(10) NOT NULL
) ENGINE=InnoDB;
