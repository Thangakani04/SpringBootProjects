INSERT INTO category ("CATEGORY_NAME") VALUES ('Fashion');
INSERT INTO category ("CATEGORY_NAME") VALUES ('Electronics');
INSERT INTO category ("CATEGORY_NAME") VALUES ('Books');
INSERT INTO category ("CATEGORY_NAME") VALUES ('Groceries');
INSERT INTO category ("CATEGORY_NAME") VALUES ('Medicines');


INSERT INTO user ("USERNAME", "PASSWORD") VALUES ('jack', 'pass_word');
INSERT INTO user ("USERNAME", "PASSWORD") VALUES ('bob', 'pass_word');
INSERT INTO user ("USERNAME", "PASSWORD") VALUES ('apple', 'pass_word');
INSERT INTO user ("USERNAME", "PASSWORD") VALUES ('glaxo', 'pass_word');

INSERT INTO cart ("TOTAL_AMOUNT", "USER_USER_ID" ) VALUES (20, 1);
INSERT INTO cart ("TOTAL_AMOUNT", "USER_USER_ID") VALUES (0, 2);

INSERT INTO USER_ROLE ("USER_ID", "ROLES") VALUES (1, 'CONSUMER');
INSERT INTO USER_ROLE ("USER_ID", "ROLES") VALUES (2, 'CONSUMER');
INSERT INTO USER_ROLE ("USER_ID", "ROLES") VALUES (3, 'SELLER');
INSERT INTO USER_ROLE ("USER_ID", "ROLES") VALUES (4, 'SELLER');

INSERT INTO PRODUCT ("PRICE", "PRODUCT_NAME", "CATEGORY_ID", "SELLER_ID") VALUES (29190, 'Apple iPad 10.2 8th Gen WiFi iOS Tablet', 2, 3);
INSERT INTO PRODUCT ("PRICE", "PRODUCT_NAME", "CATEGORY_ID", "SELLER_ID") VALUES (10, 'Crocin pain relief tablet', 5, 4);

INSERT INTO CART_PRODUCT ("CART_ID", "PRODUCT_ID", "QUANTITY") VALUES (1, 2, 2);
INSERT INTO CART_PRODUCT ("CART_ID", "PRODUCT_ID", "QUANTITY") VALUES (2, 1, 2);

COMMIT;