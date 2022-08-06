insert into bakeries (address, name, phone_number) values ('Ring mall', 'Cofferro', "+359 889 555 255");
insert into coffee_machines (name) values ('Rancilio Sylvia');
insert into grinders (name) values ('Sette');

insert into users (email, password, user_role, username) values ('gkancheva@live.com', 'test', 'ADMIN', 'gkancheva');

insert into coffees (name, origin, roasted_on_date, bakery_id) values ('Nicaragua', 'Nicaragua', '2022-07-13', 1);

insert into espresso_settings (brewing_pressure, brewing_temperature, dose, grinding_fineness, volume, coffee_id, coffee_machine_id, grinder_id, user_id)
    values (9, 91.5, 18, '12C', 20, 1, 1, 1, 1);