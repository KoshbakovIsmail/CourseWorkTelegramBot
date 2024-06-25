-- liquibase formatted sql
 -- changeset koshbakov:1
CREATE TABLE public.notification_task
(
id bigint NOT NULL,
chat_id bigint NOT NULL,
message text NOT NULL,
notification_date_time timestamp without time zone NOT NULL,
CONSTRAINT pk_notification_task PRIMARY KEY (id)

);

