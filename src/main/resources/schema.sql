--DROP TABLE msg;
--DROP TABLE headers;

CREATE TABLE IF NOT EXISTS msg (
    msg_id INTEGER NOT NULL AUTO_INCREMENT,
    message VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS headers (
    headers_id INTEGER NOT NULL,
    FOREIGN KEY (headers_id) references msg(msg_id),
    head VARCHAR(256) NOT NULL
);
