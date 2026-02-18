CREATE TABLE school_classes (
    id SERIAL PRIMARY KEY,
    subject_id BIGINT,
    teacher_id BIGINT,

    CONSTRAINT fk_class_subject
        FOREIGN KEY (subject_id)
        REFERENCES subjects(id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_class_teacher
        FOREIGN KEY (teacher_id)
        REFERENCES teachers(id)
        ON DELETE RESTRICT
)