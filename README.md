### Prerequisitives (enabling TLS for localhost):

1. generate self-signed certificate with localhost in SAN via the article 
https://gist.github.com/jchandra74/36d5f8d0e11960dd8f80260801109ab0#creating-your-self-signed-certificate-with-subject-alternative-name-san
2. add it in jvm keystore

		keytool -import -keystore /usr/lib/jvm/java-8-oracle/jre/lib/security/cacerts 
		-trustcacerts -alias "Self-Signed Certificate for TLS for Vault 1" 
		-file /home/kokokozhina/myCA/server_crt.pem 
    (default password for jvm keystore is ‘changeit’; please also put your own path to jre)
   
    

### Start vault server:

in the first terminal: 

	vault server -config vault.conf
	
in the second terminal: 
			
	export VAULT_ADDR=https://127.0.0.1:8200
	vault operator init -key-shares=5 -key-threshold=2
	vault unseal <key> //execute this command as many times as it's pointed in -key-threshold with the different unseal keys respectively 
	export VAULT_TOKEN=<root token>
	vault secrets enable -path=secret kv
	
### Инициализация базы данных

1. установить mysql с дефолтным пользователем root и паролем root
2. зайти в бд, используя командную строку: mysql -u root -p root 
3. ввести следующие команды:
```
create database if not exists vault_me character set utf8;
create user 'vault_me_admin'@'%' identified by 'vault_me_admin';
grant all privileges on vault_me.* to 'vault_me_admin'@'%';
flush privileges;
```
	