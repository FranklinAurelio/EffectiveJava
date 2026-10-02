Aqui está o conteúdo do arquivo **`README.md`** completo, unificado e sem partes quebradas ou cortadas. Basta copiar todo o bloco abaixo e colar diretamente dentro do seu arquivo `README.md` no VS Code:

````markdown
# Sistema de Configuração e Conexão de Banco de Dados (Effective Java - Cap. 2)

Projeto prático desenvolvido para consolidar os conceitos do **Capítulo 2 ("Criar e Destruir Objetos")** do livro _Effective Java_ (3ª Edição) de Joshua Bloch, estruturado sob os princípios da **Clean Architecture** (Arquitetura Limpa).

---

## 🛠️ Tecnologias Utilizadas

- **Java** (Versão 11 ou superior)
- **Maven** (Opcional - Gerenciamento de dependências e build) ou Compilação Nativa via `javac`

---

## 📂 Arquitetura do Projeto

O código está isolado em camadas bem definidas para garantir a separação de responsabilidades:

```text
sistema-db/
└── src/
    └── main/
        └── java/
            └── com/
                └── sistema/
                    └── db/
                        ├── domain/               # Regras de Negócio Puras
                        │   ├── DatabaseConfig.java       (Item 2: Builder Pattern)
                        │   └── QueryResult.java          (Item 1: Static Factory Methods)
                        │
                        ├── usecase/              # Casos de Uso / Aplicação
                        │   └── ExecutarQueryUseCase.java (Orquestra o domínio e infraestrutura)
                        │
                        ├── infrastructure/       # Infraestrutura e Recursos Externos
                        │   ├── DatabaseConnectionPool.java (Item 3: Singleton com Enum)
                        │   └── RecursoBancoDeDados.java    (Item 6, 7 & 8: Cleaners e Gestão de Memória)
                        │
                        ├── util/                 # Utilitários globais
                        │   └── SecurityUtils.java        (Item 4: Noninstantiability)
                        │
                        └── Main.java             # Entrypoint da aplicação
```
````

---

## 📖 Mapeamento dos Conceitos do _Effective Java_ (Capítulo 2)

| Item do Livro  | Conceito Aplicado                                     | Implementação no Projeto                                                                         |
| -------------- | ----------------------------------------------------- | ------------------------------------------------------------------------------------------------ |
| **Item 1**     | Métodos estáticos de fábrica vs. Construtores         | `QueryResult.criarComSucesso()` e `criarVazio()` (nomes descritivos e uso de cache/Flyweight).   |
| **Item 2**     | Padrão Builder para construtores complexos            | `DatabaseConfig.Builder` para parâmetros obrigatórios e opcionais encadeados.                    |
| **Item 3**     | Singleton com Enums                                   | `DatabaseConnectionPool` (garantia de instância única e segurança contra reflexão/serialização). |
| **Item 4**     | Impedir instanciação com construtor privado           | `SecurityUtils` (classe utilitária com construtor `private` e `AssertionError`).                 |
| **Item 6 & 7** | Evitar objetos desnecessários e referências obsoletas | Limpeza explícita de buffers (`dadosBuffer = null`) e reuso de instâncias.                       |
| **Item 8**     | Evitar Finalizers e Cleaners                          | `RecursoBancoDeDados` utilizando a API `Cleaner` em conjunto com `AutoCloseable`.                |

---

## 🚀 Como Executar o Projeto

### Opção 1: Via VS Code (Mais fácil)

1. Abra a pasta do projeto no VS Code.
2. Navegue até o arquivo `src/main/java/com/sistema/db/Main.java`.
3. Clique no botão **Run** ou **Debug** que aparece logo acima do método `main`.

### Opção 2: Via Terminal (Sem Maven / Nativo do Java)

Se o Maven não estiver instalado no seu ambiente, você pode compilar e rodar diretamente pelo terminal na raiz do projeto (`sistema-db`):

1. **Compilar os arquivos para uma pasta `bin`:**

- _No macOS / Linux:_

```bash
javac -d bin $(find src -name "*.java")

```

- _No Windows (PowerShell):_

```bash
javac -d bin (Get-ChildItem -Recurse -Filter *.java).FullName

```

2. **Executar a aplicação:**

```bash
java -cp bin com.sistema.db.Main

```

### Opção 3: Via Maven (Caso tenha instalado)

1. Compile o projeto:

```bash
mvn clean compile

```

2. Execute a aplicação:

```bash
mvn exec:java -Dexec.mainClass="com.sistema.db.Main"

```

---

## 💡 Exemplo de Saída Esperada no Console

```text
=== PROJETO PRÁTICO CLEAN ARCHITECTURE - EFFECTIVE JAVA (CAP. 2) ===

Senha processada: [Item 4] ***12456789***

Iniciando UseCase com a config: Config[host=127.0.0.1, porta=3306, usuario=root_user, timeout=45s, ssl=true]
[Item 3 - Singleton] Conectado ao banco: AppDB
[Item 6] Executando operação com buffer alocado na infraestrutura.
[Item 1] Fábrica: Criando resultado de sucesso.
Dados obtidos: SELECT * FROM tb_usuarios;
[Item 7 & 8] Recurso fechado explicitamente e referências limpas.

=== FIM DA EXECUÇÃO ===

```

```

```
