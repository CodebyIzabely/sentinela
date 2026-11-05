# SENTINELA – Sprint 1

Sistema de monitoramento de segurança física de uma instalação nuclear.

## Integrantes
- Izabely Dos santos
- Ana Flavia Chaves 

## Como rodar
- Pela IDE: executar `br.com.sentinela.Main`
- Pelo terminal: `mvn compile exec:java`

## Como testar
`mvn test`

## Respostas de reflexão
### Classes e Objetos
**Por que ZonaRestrita é uma classe e "ZNR-01 - Casa do Reator" é um objeto?**
ZonaRestrita é o molde: define que toda zona tem identificador, descrição e nível de radiação.
"ZNR-01 - Casa do Reator" é uma zona concreta criada a partir desse molde, com valores próprios.
Podemos criar quantas zonas quisermos com a mesma classe, cada uma com seus dados.