DROP TABLE IF EXISTS notifications;

CREATE TABLE notifications (
                               id UUID PRIMARY KEY,
                               created_at TIMESTAMP NOT NULL,
                               user_id UUID NOT NULL,
                               message VARCHAR(255) NOT NULL,
                               important BOOLEAN NOT NULL
);
