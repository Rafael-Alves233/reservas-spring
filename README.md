# ColabSpace — API de Reserva de Salas

API REST para gestão de salas de reunião, colaboradores e reservas em um espaço de trabalho compartilhado.

## Stack

- Java 25
- Spring Boot 4.1.1 (Web MVC, Data JPA, Validation)
- H2 em memória
- Lombok
- Maven

## Como rodar

O `pom.xml` exige **JDK 25**. Se a IDE compila mas o terminal não, o `java` do PATH provavelmente é outro:

```bash
export JAVA_HOME="/c/Program Files/Java/jdk-25"   # ajuste ao seu caminho
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080` com o perfil `test` ativo.

Console do H2: `http://localhost:8080/h2-console` — JDBC URL `jdbc:h2:mem:testdb`, usuário `sa`, senha em branco.

> O banco é em memória: os dados somem a cada parada da aplicação.

## Endpoints

Todas as rotas sob o prefixo `/api`.

### Salas

| Método | Rota | Descrição | Status |
|---|---|---|---|
| `POST` | `/api/salas` | Cadastra sala | 201 + `Location`, 400, 409 |
| `GET` | `/api/salas` | Lista todas | 200 |
| `GET` | `/api/salas/{id}` | Detalha sala | 200, 404 |
| `PUT` | `/api/salas/{id}` | Substitui a sala | 200, 400, 404, 409 |
| `PATCH` | `/api/salas/{id}/ativar` | Ativa a sala | 200, 404 |
| `PATCH` | `/api/salas/{id}/inativar` | Inativa a sala | 200, 404 |

**Campos:** `id`, `nome` (único), `capacidade`, `andar`, `ativa`, `recursos`.

`recursos` aceita: `AR_CONDICIONADO`, `PROJETOR`, `TV`, `LOUSA`.

### Colaboradores

| Método | Rota | Descrição | Status |
|---|---|---|---|
| `POST` | `/api/colaboradores` | Cadastra colaborador | 201 + `Location`, 400, 409 |
| `GET` | `/api/colaboradores/{id}` | Detalha colaborador | 200, 404 |

**Campos:** `id`, `nome`, `email` (único), `departamento`.

`departamento` aceita: `TECNOLOGIA`, `FINANCEIRO`, `COMERCIAL`, `MARKETING`, `RECURSOS_HUMANOS`, `OPERACOES`, `TERCEIRIZADO`.

## Exemplos

```bash
# cadastra sala
curl -i -X POST localhost:8080/api/salas \
  -H "Content-Type: application/json" \
  -d '{"nome":"Sala Apollo","capacidade":12,"andar":1,"recursos":["PROJETOR","AR_CONDICIONADO"]}'

# lista e detalha
curl localhost:8080/api/salas
curl localhost:8080/api/salas/1

# substitui (corpo completo)
curl -i -X PUT localhost:8080/api/salas/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Sala Apollo","capacidade":20,"andar":1,"recursos":["TV"]}'

# ativa / inativa
curl -i -X PATCH localhost:8080/api/salas/1/inativar
curl -i -X PATCH localhost:8080/api/salas/1/ativar

# cadastra colaborador
curl -i -X POST localhost:8080/api/colaboradores \
  -H "Content-Type: application/json" \
  -d '{"nome":"Ana Souza","email":"ana.souza@colabspace.com","departamento":"TECNOLOGIA"}'
```

## Tratamento de erros

Erros são centralizados em um `@RestControllerAdvice`, sem stack trace no corpo da resposta.

| Situação | Status |
|---|---|
| Recurso criado | 201 + header `Location` |
| Campo inválido, corpo malformado, parâmetro de tipo errado | 400 |
| Recurso não encontrado | 404 |
| Conflito de estado (duplicidade, violação de integridade) | 409 |

Formato padrão:

```json
{
  "timestamp": "2026-09-05T20:05:48.505",
  "status": 404,
  "erro": "Sala nao encontrada. Id: 99",
  "path": "/api/salas/99"
}
```

Erros de validação acrescentam a lista de campos reprovados:

```json
{
  "timestamp": "2026-09-05T20:05:48.505",
  "status": 400,
  "erro": "Requisição inválida",
  "path": "/api/salas",
  "campos": [
    { "campo": "nome", "mensagem": "must not be blank" }
  ]
}
```

## Decisões de design

**`PUT` é substituição completa, não atualização parcial.** O corpo precisa trazer todos os campos; os ausentes são zerados, conforme a semântica da RFC 7231. Não há `PATCH` genérico de sala.

**`ativa` não entra no `PUT`.** Ativar e inativar são transições de estado com regra de negócio própria — uma sala com reservas confirmadas no futuro não deveria simplesmente sumir de circulação. Por isso têm endpoints dedicados, e o `PUT` nunca toca nesse campo. Como consequência, `ativa` aparece na resposta mas não é aceito na entrada.

**Não há `DELETE` de sala.** Apagar uma sala com reservas históricas quebraria a integridade referencial ou destruiria o histórico. O `inativar` cumpre o papel de remoção lógica.

**Unicidade é checada em duas camadas.** O service consulta antes de gravar para produzir uma mensagem de erro útil; a constraint `unique` do banco é a garantia real, e sua violação também é traduzida em 409. As duas são necessárias: entre a consulta e a gravação existe uma janela em que duas requisições simultâneas passam juntas.

## Estado atual

Implementado: Salas (completo) e Colaboradores (cadastro e detalhe).

Pendente:

- Entidade `Reserva`: criação com detecção de conflito de horário, cancelamento com motivo e listagens
- `GET /api/salas/{id}/disponibilidade?data=` — intervalos livres da sala no dia
- `GET /api/colaboradores/{id}/reservas` — histórico de reservas
- Testes automatizados

> A detecção de conflito de horário é verificada em consulta ao banco antes da gravação. Sobreposição de intervalos não é expressável em uma constraint `unique`, e o H2 não suporta constraints de exclusão — portanto duas requisições simultâneas para o mesmo horário podem, em tese, ambas passar. Limitação conhecida e aceita neste escopo.

> Um seed de dados local (`config/SeedDeDados.java`) popula salas e colaboradores na subida da aplicação. 
