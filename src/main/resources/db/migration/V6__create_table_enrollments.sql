CREATE TABLE enrollments (
    id SERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL,
    class_id BIGINT NOT NULL,

    CONSTRAINT fk_enrollment_student
        FOREIGN KEY (student_id)
        REFERENCES students(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_enrollment_class
        FOREIGN KEY (class_Id)
        REFERENCES school_classes(id)
        ON DELETE CASCADE
)