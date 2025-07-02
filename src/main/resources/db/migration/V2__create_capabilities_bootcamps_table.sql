CREATE TABLE IF NOT EXISTS capabilities_bootcamps (
    id BIGSERIAL PRIMARY KEY,
    id_capability BIGINT NOT NULL,
    id_bootcamp BIGINT NOT NULL
);
