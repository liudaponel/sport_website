CREATE TABLE places(
    id serial PRIMARY KEY ,
    name text NOT NULL ,
    address text NOT NULL ,
    max_places integer NOT NULL
);
CREATE TABLE coaches(
    id serial PRIMARY KEY,
    user_id int REFERENCES users(id)
);
CREATE TABLE events(
    id serial PRIMARY KEY ,
    place int REFERENCES places(id),
    start_time timestamp NOT NULL ,
    price float DEFAULT 0.0,
    duration_hours int NOT NULL,
    duration_minutes int NOT NULL ,
    max_places int NOT NULL ,
    taken_places int DEFAULT 0,
    coach int REFERENCES coaches(id)
);
CREATE TABLE roles(
    id serial PRIMARY KEY ,
    name text NOT NULL
);
CREATE TABLE users(
    id serial PRIMARY KEY ,
    fio text NOT NULL ,
    number text NOT NULL ,
    email text NOT NULL,
    role int REFERENCES roles(id),
    password int NOT NULL
);
CREATE TABLE registrations(
    guest int REFERENCES users(id),
    event int REFERENCES events(id),
    PRIMARY KEY (guest, event)
)