CREATE TABLE study (
    study_id INTEGER PRIMARY KEY AUTOINCREMENT,
    study_matter VARCHAR(255) NOT NULL
);

CREATE TABLE topic (
    topic_id INTEGER PRIMARY KEY AUTOINCREMENT,
    topic_title VARCHAR(255) NOT NULL,
    study_id INTEGER,
    topic_parent_id INTEGER,

    CONSTRAINT fk_topic_study FOREIGN KEY (study_id) REFERENCES study(study_id),
    CONSTRAINT fk_topic_parent FOREIGN KEY (topic_parent_id) REFERENCES topic(topic_id)
);

CREATE TABLE document (
    document_id INTEGER PRIMARY KEY AUTOINCREMENT,
    document_title VARCHAR(255) NOT NULL,
    document_content TEXT,
    study_id INTEGER,
    topic_id INTEGER,

    CONSTRAINT fk_document_study FOREIGN KEY (study_id) REFERENCES study(study_id),
    CONSTRAINT fk_document_topic FOREIGN KEY (topic_id) REFERENCES topic(topic_id)
);