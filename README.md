# SENTINELA – Sprint 1

Sistema de monitoramento de segurança física de uma instalação nuclear.

## Integrantes
- Izabely Dos santos
- Ana Flavia Chaves 
- Ana Clara Alcantara
- Natalia Alcantara
- Raissa Marinho

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

### Encapsulamento
**Se o nivelRadiacaoAtual fosse público, que tipo de fraude um programador descuidado (ou malicioso) poderia causar no relatório da central de monitoramento?**
Qualquer parte do código poderia escrever direto no atributo, sem passar pela validação.
Seria possível zerar a radiação de uma zona em estado crítico pouco antes do relatório,
escondendo um vazamento, ou gravar valores negativos ou absurdos que distorcem a análise.
Com o atributo privado, toda alteração passa obrigatoriamente por registrarLeituraRadiacao,
que valida a leitura antes de aceitar.

### Construtores
**Por que o desaparecimento do construtor padrão, assim que você define um customizado, protege o cadastro de zonas da instalação?**
Quando criamos o construtor ZonaRestrita(identificador, descricao, limiteRadiacao), o Java deixa de
oferecer o new ZonaRestrita() vazio. Assim, é impossível criar uma "zona fantasma" sem identificação
ou sem limite de radiação: o próprio compilador bloqueia o código antes de o sistema rodar.
Toda zona já nasce válida, e as validações do construtor impedem dados vazios ou absurdos.

### Associação
**Se o alerta guardasse apenas a String com o código do sensor, o que a central perderia na hora de escalonar o incidente e precisar consultar a localização e a zona afetada depois?**
Com apenas o texto "SRD-01", o alerta não saberia onde o sensor está, se ele continua em operação
nem qual zona ele monitora. A central teria que procurar o sensor de novo em alguma lista, e se
o código estivesse errado ou duplicado, o incidente ficaria sem informação. Guardando o próprio objeto
Sensor e o próprio objeto ZonaRestrita, o alerta já carrega a localização, o status do sensor e o
nível de radiação atual da zona, prontos para quem for atender a ocorrência.

**Observação sobre a regra dos 3 parâmetros:** o construtor de AlertaSeguranca recebe 4 parâmetros
(sensor, zona, severidade e mensagem) porque um alerta sem qualquer um deles seria inválido.