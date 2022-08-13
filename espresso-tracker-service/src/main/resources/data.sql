insert into bakeries (address, name, phone_number, web_site, img_src)
    values ('ул. „Околовръстен път“ 214 (Sofia Ring Mall, top level), София 1434, България', 'Cofferro', '+359 877 550 777', 'https://coffero.com/', 'https://cdncloudcart.com/9980/files/image/fv5a0029-edit.jpg'),
           ('58, Lyuben Karavelov Str., Sofia', 'Dabov - Sofia 1', '+359 882 477 000', 'https://dabov.coffee/locations/liuben-karavelov/', 'https://dabov.coffee/wp-content/uploads/2021/10/dabov-specialty-coffee-luiben-karavelov-square.jpg'),
           ('ул. Ангел Кънчев" 8, 1000 София център, София', 'Ibeco', '+359 898 577 644', 'https://www.ibeco.bg/', 'https://shop.ibeco.bg/wp-content/uploads/2020/04/ibeco_showroom.jpg'),
           ('Preslav street, 28 Varna, Bulgaria', 'Jasmin coffee roastery', '+359897838029', 'https://jasmin.coffee/', null);

insert into coffee_tools (name, coffee_tool_type)
    values ('Rancilio Sylvia', 'COFFEE_MACHINE'), ('Sette', 'GRINDER');

insert into users (email, password, user_role, username, coffee_machine_id, grinder_id)
    values ('admin@admin.com', '$2a$10$C33C99FgzILdrdEHsVwVhu4e8/Lh5KuzJNz0zKjBWer5MBSNzN4h6', 'ADMIN', 'admin', 1, 2);

insert into coffees (name, origin, roasted_on_date, bakery_id, date_created, active, description)
    values ('Nicaragua', 'Nicaragua', '2022-08-08', 1, '2022-08-08', 1, ' Кафе с мека текстура и характер. Наситено начало със сладки нотки на лешници, пралини и сушени плодове с послевкус на карамел.'),
           ('Kenya', 'Kenya', '2022-08-09', 1, '2022-08-08', 1, 'Кафе с нежни нотки и вкусове на малина и жълти плодове'),
           ('Honduras', 'Honduras', '2022-08-10', 2, '2022-08-08', 1, 'ОРГАНИЧНО ОТГЛЕДАНО комплексно кафе с мека киселинност и гладко, кадифено тяло.');

insert into espresso_settings (brewing_pressure, brewing_temperature, dose, grinding_fineness, volume, coffee_id, coffee_machine_id, grinder_id, user_id, extract_duration_sec)
    values (9, 91.5, 18, '12C', 20, 1, 1, 1, 1, 20);