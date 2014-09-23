delete from  PRODUCT_FEATURES;
delete from  SENTENCE_SENTIMENT;
delete from  REVIEW_SENTENCES;
delete from PRODUCT_REVIEWS;
delete from PRODUCT_INFO;

drop table PRODUCT_FEATURES;
drop table SENTENCE_SENTIMENT;
drop table REVIEW_SENTENCES;
drop table PRODUCT_REVIEWS;
drop table PRODUCT_INFO;


create table PRODUCT_INFO (product_id BIGINT NOT NULL AUTO_INCREMENT,product_name VARCHAR(200) NOT NULL,rating INT,category VARCHAR(200),
PRIMARY KEY (product_id ));

create table PRODUCT_REVIEWS (review_id BIGINT NOT NULL AUTO_INCREMENT,product_id BIGINT NOT NULL,commented_user VARCHAR(100),commented_date_time VARCHAR(50),comment_text LONGTEXT NOT NULL,rating INT,
 PRIMARY KEY ( review_id, product_id ),
 FOREIGN KEY ( product_id ) references PRODUCT_INFO(product_id)
);

create table REVIEW_SENTENCES (
	product_id BIGINT NOT NULL,
        review_id BIGINT NOT NULL,
        sentence_id BIGINT NOT NULL,
        sentence_text LONGTEXT NOT NULL,
        PRIMARY KEY ( product_id, review_id,  sentence_id),
	FOREIGN KEY ( product_id ) references PRODUCT_INFO(product_id),
        FOREIGN KEY ( review_id ) references PRODUCT_REVIEWS(review_id)
);

create table SENTENCE_SENTIMENT ( 
       	product_id BIGINT NOT NULL,
        review_id BIGINT NOT NULL,
        sentence_id BIGINT NOT NULL,
        feature VARCHAR(50) NOT NULL,
        opinion VARCHAR(50) NOT NULL,
        sentiment BIGINT NOT NULL
);

create table PRODUCT_FEATURES (
        product_id BIGINT NOT NULL,
        feature VARCHAR(50) NOT NULL,
        is_frequent_feature VARCHAR(3) NOT NULL,
		FOREIGN KEY (product_id) references PRODUCT_INFO(product_id)
);
