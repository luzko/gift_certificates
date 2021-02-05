package com.epam.esm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SQLQuery {
    //tag
    REMOVE_TAG_BY_ID("DELETE FROM Tag t WHERE t.id = ?1"),
    FIND_BY_NAME("SELECT t FROM Tag t WHERE t.name LIKE ?1"),
    FIND_ALL_TAGS("SELECT t FROM Tag t"),
    FIND_COUNT_TAGS("SELECT count(t) FROM Tag t"),
    FIND_MOST_POPULAR("SELECT tag_id, tag_name FROM tags WHERE tag_id = (" +
            "SELECT gct.tag_id_fk FROM orders o" +
            "    JOIN users u ON o.user_id_fk = u.user_id" +
            "    JOIN order_certificates oc ON o.order_id = oc.order_id_fk" +
            "    JOIN gift_certificates gc ON oc.gift_certificate_id_fk = gc.gift_certificate_id" +
            "    JOIN gift_certificate_tags gct ON gc.gift_certificate_id = gct.gift_certificate_id_fk" +
            "    WHERE o.user_id_fk = (" +
            "        SELECT o.user_id_fk FROM orders o" +
            "        GROUP BY o.user_id_fk" +
            "        ORDER BY SUM(o.cost) DESC" +
            "        LIMIT 1" +
            "    )" +
            "    GROUP BY gct.tag_id_fk" +
            "    ORDER BY COUNT(gct.tag_id_fk) DESC" +
            "    LIMIT 1" +
            ");"
    ),

    //gift certificate
    FIND_CERTIFICATE_BY_ID("SELECT gc FROM GiftCertificate gc WHERE gc.isDeleted = FALSE AND gc.id = ?1"),
    REMOVE_CERTIFICATE_BY_ID("UPDATE GiftCertificate gc SET gc.isDeleted = TRUE WHERE gc.id =?1"),

    //user
    FIND_ALL_USERS("SELECT u FROM User u"),
    FIND_COUNT_USERS("SELECT count(u) FROM User u"),

    //order
    FIND_ALL_ORDERS("SELECT o FROM Order o"),
    FIND_ORDER_BY_ID("SELECT o FROM Order o WHERE o.id = ?1"),
    REMOVE_ORDER_BY_ID("DELETE FROM Order o WHERE o.id = ?1");

    private final String value;
}
