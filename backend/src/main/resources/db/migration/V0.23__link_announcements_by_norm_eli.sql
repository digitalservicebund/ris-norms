UPDATE announcements SET eli = regexp_replace(eli, '/[^/]*$', '');
