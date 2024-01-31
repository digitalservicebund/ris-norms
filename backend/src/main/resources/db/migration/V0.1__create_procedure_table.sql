CREATE TABLE IF NOT EXISTS
    procedure (
                           id uuid NOT NULL PRIMARY KEY,
                           state VARCHAR(50) NOT NULL,
                            eli VARCHAR(255) NOT NULL,
                            printAnnouncementGazette VARCHAR(10) NOT NULL,
                            printAnnouncementYear VARCHAR(4) NOT NULL,
                            printAnnouncementPage VARCHAR(5) NOT NULL
    );
