CREATE TABLE IF NOT EXISTS
    migration_log
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    size INT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
