delete from dict_access;
delete from dict_catalog;
delete from dict_classify;
delete from dict_info;

insert into dict_classify (sn, code, name, des) values(1, 'classify1', '分类1', '');
insert into dict_classify (sn, code, name, des) values(2, 'classify2', '分类2', '');
insert into dict_catalog (sn, code, name, classify, op_mode, edit_flag, des) values(1, 'catalog1', '目录1', 'classify1', 1, 1, '');
insert into dict_catalog (sn, code, name, classify, op_mode, edit_flag, des) values(2, 'catalog2', '目录2', 'classify1', 2, 1, '');
insert into dict_catalog (sn, code, name, classify, op_mode, edit_flag, des) values(1, 'catalog3', '目录3', 'classify2', 3, 0, '');
insert into dict_info (catalog_code, sn, dict_id, dict_code, dict_value, system_flag, show_flag, enable_flag) values('catalog1', 1, 10, null, 'ID值1', 1, 1, 1);
insert into dict_info (catalog_code, sn, dict_id, dict_code, dict_value, system_flag, show_flag, enable_flag) values('catalog1', 2, 20, null, 'ID值2', 1, 1, 1);
insert into dict_info (catalog_code, sn, dict_id, dict_code, dict_value, system_flag, show_flag, enable_flag) values('catalog2', 1, 0, 'code1', 'CODE值1', 0, 1, 1);
insert into dict_info (catalog_code, sn, dict_id, dict_code, dict_value, system_flag, show_flag, enable_flag) values('catalog2', 2, 0, 'code2', 'CODE值2', 0, 1, 1);
insert into dict_info (catalog_code, sn, dict_id, dict_code, dict_value, system_flag, show_flag, enable_flag) values('catalog3', 1, 0, null, '选项1', 1, 0, 1);
insert into dict_info (catalog_code, sn, dict_id, dict_code, dict_value, system_flag, show_flag, enable_flag) values('catalog3', 2, 0, null, '选项2', 1, 0, 1);
insert into dict_access (catalog_code, access_model_name, access_field_name) values('catalog2', 'demo.Module', 'code');

delete from module;
insert into module (code, name, des, num, cnt, create_date, default_module) values('code1', 'name', null, 123456, 1, '2012-12-24', '1');
