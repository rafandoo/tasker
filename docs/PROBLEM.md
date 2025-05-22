# 💡 Problema proposto

Imagine que você é um desenvolvedor, e foi-lhe solicitado a criação de um microsserviço, para uma empresa
que necessita organizar as tarefas de seus usuários. A empresa solicitou que essa aplicação deve 
permitir que usuários possam:

- Criar novas tarefas;
- Visualizar uma lista de tarefas;
- Atualizar tarefas existentes;
- Excluir tarefas que não são mais necessárias.

> Nosso objetivo é mostrar como resolver esse problema, utilizando o Java com Quarkus.

## ✅ Requisitos Funcionais

Para que o sistema atenda às expectativas dos usuários, ele deve ser capaz de realizar as seguintes ações:

- **RF1**: O sistema deve permitir a criação, recuperação e atualização de categorias.
- **RF2**: O sistema deve permitir a criação, recuperação, atualização e exclusão de tarefas.
- **RF3**: O sistema deve permitir a listagem de tarefas com suporte a filtros por categoria, status, descrição, prioridade e data de vencimento.
- **RF4**: O sistema deve permitir que o usuário altere o status de uma tarefa para `DOING` (em andamento).
- **RF5**: O sistema deve permitir que o status de uma tarefa seja alterado para `DONE` (concluída), **exclusivamente se seu status anterior for** `DOING`.
- **RF6**: O sistema deve associar cada tarefa a uma categoria previamente cadastrada e válida.

## ⚙️ Requisitos Não Funcionais

Além dos requisitos funcionais, o sistema deve atender aos seguintes requisitos não funcionais:

- **RNF1**: Um usuário só pode acessar ou modificar suas próprias tarefas.
- **RNF2**: A aplicação deve expor uma API RESTful com comunicação baseada em JSON.
- **RNF3**: As exceções da aplicação devem ser mapeadas para respostas HTTP padronizadas.
- **RNF4**: As operações devem ser transacionais para garantir consistência.
- **RNF5**: A documentação da API deve ser gerada automaticamente e disponibilizada por meio do padrão OpenAPI (Swagger).
- **RNF6**: O código deve seguir arquitetura em camadas com divisão clara entre Resource, Service e Repository.
- **RNF7**: As entidades devem empregar anotações de validação (`@Valid`, `@NotBlank`, etc.) para garantir a integridade dos dados desde a camada de entrada.
- **RNF8**: O sistema deve permitir controlar a forma de exibição de entidades, ocultando dados sensíveis.****

## 📏 Regras de Negócio

As regras de negócio são restrições e condições que asseguram a coerência e a integridade dos processos executados no domínio da aplicação, desta forma definimos as seguintes regras:

- **RN1**: Toda tarefa deve pertencer exclusivamente ao usuário autenticado que a criou.
- **RN2**: Uma tarefa só pode ser marcada como `DONE` se estiver atualmente com o status `DOING`.
- **RN3**: A prioridade atribuída a uma tarefa deve ser um valor inteiro no intervalo fechado de 1 a 5.
- **RN4**: A descrição de uma tarefa é obrigatória e não pode ser vazia ou composta apenas por espaços em branco.
- **RN5**: A descrição de uma categoria também é obrigatória e não pode ser vazia ou composta apenas por espaços em branco.
- **RN6**: A categoria associada à tarefa deve ser válida e possuir um ID existente.
- **RN7**: Tentativas de violação de regras devem lançar exceções do tipo `BusinessRuleViolationException`.
- **RN8**: A descrição de cada categoria deve ser única no contexto do sistema.
- **RN9**: Todas as entidades persistidas devem possuir data de criação e última atualização.
