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

### Métodos e Comportamentos
**Por que a regra que classifica o risco deve morar dentro de ZonaRestrita, e não espalhada em ifs do main?**
Porque quem conhece o próprio nível e o próprio limite é a zona. Com a regra em um só lugar,
qualquer parte do sistema (o main, a central, os testes) usa a mesma classificação.
Se a faixa de ATENCAO mudar, alteramos um único método, sem caçar ifs espalhados pelo código.