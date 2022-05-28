create table if not exists user_wallet (
    id serial PRIMARY KEY,
    user_id bigint references users unique,
    balance decimal(12,2) default 0,
    blocked_amount decimal(12,2) default 0,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    check(balance >= 0 and blocked_amount >= 0)
);

create table if not exists restaurant_wallet (
    id serial PRIMARY KEY,
    restaurant_id bigint references restaurant unique,
    balance decimal(12,2) default 0,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    check(balance >= 0)
);

create table if not exists user_wallet_transactions (
    id serial PRIMARY KEY,
    user_id bigint references users,
    order_id bigint references orders,
    amount decimal(12,2) default 0,
    transaction_type text, -- C - Credit, D - Debit
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

create table if not exists restaurant_wallet_transactions (
    id serial PRIMARY KEY,
    restaurant_id bigint references restaurant,
    order_id bigint references orders,
    amount decimal(12,2) default 0,
    transaction_type text, -- C - Credit, D - Debit
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);