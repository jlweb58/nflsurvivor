use nfl;

update teams
    set logo = load_file('/var/lib/mysql-files/buf.png')
    where name = 'Bills';

update teams
set logo = load_file('/var/lib/mysql-files/mia.png')
where name = 'Dolphins';

update teams
set logo = load_file('/var/lib/mysql-files/nyj.png')
where name = 'Jets';

update teams
set logo = load_file('/var/lib/mysql-files/ne.png')
where name = 'Patriots';

update teams
set logo = load_file('/var/lib/mysql-files/pit.png')
where name = 'Steelers';

update teams
set logo = load_file('/var/lib/mysql-files/bal.png')
where name = 'Ravens';

update teams
set logo = load_file('/var/lib/mysql-files/cin.png')
where name = 'Bengals';

update teams
set logo = load_file('/var/lib/mysql-files/cle.png')
where name = 'Browns';

update teams
set logo = load_file('/var/lib/mysql-files/hou.png')
where name = 'Texans';

update teams
set logo = load_file('/var/lib/mysql-files/ind.png')
where name = 'Colts';

update teams
set logo = load_file('/var/lib/mysql-files/jax.png')
where name = 'Jaguars';

update teams
set logo = load_file('/var/lib/mysql-files/ten.png')
where name = 'Titans';

update teams
set logo = load_file('/var/lib/mysql-files/kc.png')
where name = 'Chiefs';

update teams
set logo = load_file('/var/lib/mysql-files/lac.png')
where name = 'Chargers';

update teams
set logo = load_file('/var/lib/mysql-files/den.png')
where name = 'Broncos';

update teams
set logo = load_file('/var/lib/mysql-files/lv.png')
where name = 'Raiders';

update teams
set logo = load_file('/var/lib/mysql-files/phi.png')
where name = 'Eagles';

update teams
set logo = load_file('/var/lib/mysql-files/wsh.png')
where name = 'Commanders';

update teams
set logo = load_file('/var/lib/mysql-files/dal.png')
where name = 'Cowboys';

update teams
set logo = load_file('/var/lib/mysql-files/nyg.png')
where name = 'Giants';

update teams
set logo = load_file('/var/lib/mysql-files/det.png')
where name = 'Lions';


update teams
set logo = load_file('/var/lib/mysql-files/min.png')
where name = 'Vikings';

update teams
set logo = load_file('/var/lib/mysql-files/gb.png')
where name = 'Packers';

update teams
set logo = load_file('/var/lib/mysql-files/chi.png')
where name = 'Bears';

update teams
set logo = load_file('/var/lib/mysql-files/tb.png')
where name = 'Bucs';

update teams
set logo = load_file('/var/lib/mysql-files/atl.png')
where name = 'Falcons';

update teams
set logo = load_file('/var/lib/mysql-files/no.png')
where name = 'Saints';

update teams
set logo = load_file('/var/lib/mysql-files/car.png')
where name = 'Panthers';

update teams
set logo = load_file('/var/lib/mysql-files/lar.png')
where name = 'Rams';

update teams
set logo = load_file('/var/lib/mysql-files/sea.png')
where name = 'Seahawks';

update teams
set logo = load_file('/var/lib/mysql-files/ari.png')
where name = 'Cardinals';

update teams
set logo = load_file('/var/lib/mysql-files/sf.png')
where name = '49ers';