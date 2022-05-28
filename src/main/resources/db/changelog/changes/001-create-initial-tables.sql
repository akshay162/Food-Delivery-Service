create table if not exists users
(
    id serial PRIMARY KEY ,
    first_name text not null,
    last_name text,
    email text not null unique,
	created_at TIMESTAMP,
	updated_at TIMESTAMP
);

create table if not exists address (
    id serial primary key ,
    street text not null ,
    city text not null,
    state text not null,
    country text not null,
    zipcode varchar(10) not null,
    latitude float,
    longitude float,
	created_at TIMESTAMP,
	updated_at TIMESTAMP,
	CHECK (zipcode ~ '[A-Z0-9-]+')
);

create table if not exists restaurant (
    id serial PRIMARY KEY ,
    name text not null ,
    rating float(4),
    total_ratings bigint default 0,
    address_id bigint references address,
    user_id bigint references users,
	created_at TIMESTAMP,
	updated_at TIMESTAMP
);

create table if not exists menu_item (
    id serial primary key,
    item_name text not null ,
    item_description text,
    image_url text,
    item_price decimal(12, 2),
    item_discount decimal(12, 2),
    diet_type int,
    is_available boolean,
    restaurant_id bigint references restaurant on delete cascade,
	created_at TIMESTAMP,
	updated_at TIMESTAMP
);

create table if not exists user_address (
    id serial primary key ,
    user_id bigint references users on delete cascade ,
    address_id bigint references address on delete cascade,
	created_at TIMESTAMP,
	updated_at TIMESTAMP,
    unique (user_id, address_id)
);

create table if not exists orders (
    id serial PRIMARY KEY ,
    user_id bigint references users,
    address_id bigint references address,
    order_time bigint,
    restaurant_id bigint references restaurant,
    is_active boolean,
    status int,
    items_cost decimal(12, 2),
    delivery_fee decimal(12, 2),
    service_fee decimal(12, 2),
    discount_amount decimal(12, 2),
    final_order_amount decimal(12, 2),
    estimated_time_arrival int,
    actual_delivery_time int,
	created_at TIMESTAMP,
	updated_at TIMESTAMP,
    check ( items_cost >=0 and delivery_fee >=0 and service_fee >= 0 )
);

create table if not exists order_items (
    id serial primary key ,
    order_id bigint references orders on delete cascade ,
    menu_item_id bigint references menu_item,
    serving int,
    price decimal(12, 2),
	created_at TIMESTAMP,
	updated_at TIMESTAMP,
    unique (order_id, menu_item_id)
);