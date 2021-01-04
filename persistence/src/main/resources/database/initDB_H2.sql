DROP TABLE IF EXISTS gift_certificate_tag;
DROP TABLE IF EXISTS gift_certificate;
DROP TABLE IF EXISTS tag;

CREATE TABLE gift_certificate
(
    gift_certificate_id   INT AUTO_INCREMENT,
    gift_certificate_name VARCHAR(200)  NOT NULL,
    description           VARCHAR(500)  NOT NULL,
    price                 DECIMAL(6, 2) NOT NULL,
    duration              TINYINT       NOT NULL,
    create_date 		  TIMESTAMP     NOT NULL,
    last_update_date      TIMESTAMP     NOT NULL,
    PRIMARY KEY (gift_certificate_id)
);

CREATE TABLE tag
(
    tag_id   INT AUTO_INCREMENT,
    tag_name VARCHAR(100) NOT NULL,
    UNIQUE      (tag_name),
    PRIMARY KEY (tag_id)
);

CREATE TABLE gift_certificate_tag
(
    gift_certificate_tag_id INT AUTO_INCREMENT,
    gift_certificate_id_fk  INT NOT NULL,
    tag_id_fk               INT NOT NULL,
    PRIMARY KEY (gift_certificate_tag_id),
    UNIQUE      (gift_certificate_id_fk, tag_id_fk),
    FOREIGN KEY (gift_certificate_id_fk) REFERENCES gift_certificate (gift_certificate_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id_fk)              REFERENCES tag (tag_id)                           ON DELETE CASCADE
);