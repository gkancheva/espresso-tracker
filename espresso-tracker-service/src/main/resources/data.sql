--delete from espresso_settings;
--delete from coffees;
--delete from users;
--delete from grinders;
--delete from coffee_machines;
--delete from bakeries;

insert into bakeries (address, name, phone_number, web_site, img_src)
    values ('ул. „Околовръстен път“ 214 (Sofia Ring Mall, top level), София 1434, България', 'Cofferro', '+359 877 550 777', 'https://coffero.com/', 'https://cdncloudcart.com/9980/files/image/fv5a0029-edit.jpg'),
           ('58, Lyuben Karavelov Str., Sofia', 'Dabov - Sofia 1', '+359 882 477 000', 'https://dabov.coffee/locations/liuben-karavelov/', 'https://dabov.coffee/wp-content/uploads/2021/10/dabov-specialty-coffee-luiben-karavelov-square.jpg'),
           ('ул. Ангел Кънчев" 8, 1000 София център, София', 'Ibeco', '+359 898 577 644', 'https://www.ibeco.bg/', 'https://shop.ibeco.bg/wp-content/uploads/2020/04/ibeco_showroom.jpg'),
           ('Preslav street, 28 Varna, Bulgaria', 'Jasmin coffee roastery', '+359897838029', 'https://jasmin.coffee/', null);

insert into coffee_machines (name) values ('Rancilio Sylvia');

insert into grinders (name) values ('Sette');

insert into users (email, password, user_role, username) values ('admin@admin.com', 'test', 'ADMIN', 'admin');

insert into coffees (name, origin, roasted_on_date, bakery_id, date_created, active)
    values ('Nicaragua', 'Nicaragua', '2022-08-08', 1, '2022-08-08', 1),
           ('Kenya', 'Kenya', '2022-08-09', 1, '2022-08-08', 1),
           ('Honduras', 'Honduras', '2022-08-10', 2, '2022-08-08', 1);

insert into espresso_settings (brewing_pressure, brewing_temperature, dose, grinding_fineness, volume, coffee_id, coffee_machine_id, grinder_id, user_id)
    values (9, 91.5, 18, '12C', 20, 1, 1, 1, 1);