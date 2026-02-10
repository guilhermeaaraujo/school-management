CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_student_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
)