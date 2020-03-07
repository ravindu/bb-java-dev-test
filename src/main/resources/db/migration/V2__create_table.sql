CREATE TABLE IF NOT EXISTS event_tbl (
  event_id varchar(255) NOT NULL,
  time_stamp timestamp NOT NULL,
  created_date timestamp DEFAULT NULL,
  updated_date timestamp DEFAULT NULL,
  PRIMARY KEY (event_id)
);

CREATE TABLE IF NOT EXISTS product_tbl (
  product_id bigint(11),
  product_name varchar(255) NOT NULL,
  quantity integer(10) NOT NULL,
  sale_amount double NOT NULL,
  created_date timestamp DEFAULT NULL,
  updated_date timestamp DEFAULT NULL,
  fk_event_id varchar(255) NOT NULL,
  PRIMARY KEY (product_id)
);

ALTER TABLE
    product_tbl
ADD
    CONSTRAINT fk_ref_event_id FOREIGN KEY (fk_event_id) REFERENCES event_tbl (event_id);




