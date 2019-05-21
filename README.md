### Cтартовать vault:

1 терминал: 

	vault server -config vault.conf
	
2 терминал: 
			
	export VAULT_ADDR=http://127.0.0.1:8200
	vault operator init -key-shares=5 -key-threshold=2
	vault unseal <ключ> - столько же, сколько и -key-threshold
	export VAULT_TOKEN=<root token>
	vault secrets enable -path=secret kv