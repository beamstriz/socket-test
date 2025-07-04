## ✅ `README.md`

```markdown
# Teste Técnico - Comunicação via Sockets TCP e UDP

Este projeto contém duas aplicações Java desenvolvidas para fins de teste técnico, simulando comunicação via sockets TCP e UDP com suporte a multi-threading:

- **Receptor**: Servidor que recebe dados via TCP e UDP.
- **Produtor**: Cliente que envia dados (ASCII ou binário) via TCP e UDP.

```

## 🧱 Estrutura do Projeto

```

socket-test/
├── receptor/      # Servidor (Receptor)
└── produtor/      # Cliente (Produtor)

```

---

## 📦 Requisitos

- Java 11+ (Java 17 ou 21 recomendado)
- Maven 3.6+

---

## 🚀 Como compilar

No diretório raiz (onde está o `pom.xml` principal ou no diretório `receptor` ou `produtor`):

```bash
mvn clean install
````

---

## 🏃 Como executar

### Receptor

```bash
java -jar receptor/target/receptor-1.0-SNAPSHOT-jar-with-dependencies.jar --porta=5000
```

### Produtor

```bash
java -jar produtor/target/produtor-1.0-SNAPSHOT-jar-with-dependencies.jar --host=127.0.0.1 --porta=5000 --arquivo=entrada.txt
```

---

## 📝 Formato do arquivo de entrada do Produtor

### Exemplo para ASCII:

```
ascii
Mensagem 1
Mensagem 2
```

### Exemplo para Binário (hexadecimal):

```
binário
4A6F686E
446F65
```

### 📋 **Exemplos Práticos de Arquivos**

#### ✅ **Arquivo ASCII válido** (`arquivoAscii.txt`):
```
ascii
Hello World
Teste de comunicação
Linha final
```

#### ✅ **Arquivo Binário válido** (`arquivoBinario.txt`):
```
binário
48656C6C6F
576F726C64
54657374
```

#### ❌ **Formatos INVÁLIDOS**:
```
# ERRO: Header incorreto
hexadecimal  ← Deve ser "binário"
48656C6C6F

# ERRO: Hexadecimal inválido
binário
48656C6C6G  ← 'G' não é hexadecimal válido

# ERRO: Linhas vazias no meio
ascii
Linha 1

Linha 3  ← Linha vazia anterior causa problemas
```

### 💡 **Dicas**:
- **ASCII**: Qualquer texto UTF-8 válido
- **Binário**: Apenas caracteres 0-9 e A-F (case insensitive)
- **Sem linhas vazias**: O processamento para na primeira linha vazia
- **Header obrigatório**: Primeira linha deve ser exatamente `ascii` ou `binário`

---

## 🔧 Parâmetros esperados

### Receptor:

* `--porta`: Porta onde o servidor escutará conexões TCP e UDP.

### Produtor:

* `--host`: Endereço IP ou hostname do servidor receptor.
* `--porta`: Porta onde será enviado o conteúdo.
* `--arquivo`: Caminho para o arquivo `.txt` com os dados.

---

## 🛠️ Tecnologias e Práticas Utilizadas

* Java + Sockets TCP/UDP
* Concorrência com `Executors` e `ThreadPool`
* Maven como gerenciador de dependências
* Boas práticas de logging (System.out/System.err)
* NitriteDB para persistência de dados (status do produtor)

---

## 📄 Licença

Uso interno para avaliação técnica.



## 🧾 `pom.xml` (multi-módulo Maven)

Este arquivo define um projeto pai com dois módulos: `receptor` e `produtor`.

### `pom.xml` na raiz do projeto (`socket-test/pom.xml`)

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.exemplo</groupId>
  <artifactId>socket-test</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>pom</packaging>

  <modules>
    <module>receptor</module>
    <module>produtor</module>
  </modules>

  <properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
  </properties>
</project>
````

---

### `receptor/pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <parent>
    <groupId>com.exemplo</groupId>
    <artifactId>socket-test</artifactId>
    <version>1.0-SNAPSHOT</version>
  </parent>

  <modelVersion>4.0.0</modelVersion>
  <artifactId>receptor</artifactId>

  <build>
    <plugins>
      <!-- Empacotar como JAR com dependências -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-assembly-plugin</artifactId>
        <version>3.6.0</version>
        <configuration>
          <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
          </descriptorRefs>
          <archive>
            <manifest>
              <mainClass>com.exemplo.receptor.Main</mainClass>
            </manifest>
          </archive>
        </configuration>
        <executions>
          <execution>
            <id>make-assembly</id>
            <phase>package</phase>
            <goals>
              <goal>single</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
</project>
```

---

### `produtor/pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <parent>
    <groupId>com.exemplo</groupId>
    <artifactId>socket-test</artifactId>
    <version>1.0-SNAPSHOT</version>
  </parent>

  <modelVersion>4.0.0</modelVersion>
  <artifactId>produtor</artifactId>

  <dependencies>
    <!-- https://mvnrepository.com/artifact/org.json/json -->
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20250517</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.dizitart/nitrite -->
    <dependency>
        <groupId>org.dizitart</groupId>
        <artifactId>nitrite</artifactId>
        <version>4.3.0</version>
    </dependency>
    <dependency>
      <groupId>org.dizitart</groupId>
      <artifactId>nitrite-mvstore-adapter</artifactId>
      <version>4.3.0</version>
    </dependency>

  </dependencies>

  <build>
    <plugins>
      <!-- Empacotar como JAR com dependências -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-assembly-plugin</artifactId>
        <version>3.6.0</version>
        <configuration>
          <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
          </descriptorRefs>
          <archive>
            <manifest>
              <mainClass>com.exemplo.produtor.Main</mainClass>
            </manifest>
          </archive>
        </configuration>
        <executions>
          <execution>
            <id>make-assembly</id>
            <phase>package</phase>
            <goals>
              <goal>single</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
</project>
```

---
## 📄 Possíveis problemas para se evitar

### 🔧 **Compilação e Build**
- **Incompatibilidade de JDK**: O projeto usa Java 17 nos módulos, mas funciona com JDK 21. Certifique-se de ter Java 11+ instalado
- **Maven não encontrado**: Verifique se o Maven 3.6+ está instalado e no PATH
- **Falha na compilação**: Execute `mvn clean install` na raiz do projeto antes de tentar executar os JARs
- **JARs não gerados**: Os JARs são criados com o sufixo `-jar-with-dependencies.jar`, não apenas `.jar`

### 🌐 **Conectividade e Rede**
- **Porta já em uso**: A porta 5000 pode estar sendo usada por outro processo. Use `netstat -an | grep 5000` para verificar
- **Firewall bloqueando**: Sistemas Linux/Windows podem bloquear a porta. Configure o firewall para permitir conexões na porta escolhida
- **Problemas de localhost**: Se houver problemas com `127.0.0.1`, tente usar `localhost` ou o IP real da máquina
- **Conectividade entre máquinas**: Para testes entre máquinas diferentes, certifique-se que não há bloqueios de rede

### 📁 **Arquivos e Caminhos**
- **Arquivo de entrada não encontrado**: Certifique-se que o caminho do arquivo está correto. Use caminhos relativos a partir do diretório de execução
- **Formato do arquivo inválido**: A primeira linha deve ser exatamente `ascii` ou `binário` (não `hexadecimal`)
- **Encoding de caracteres**: Use UTF-8 para arquivos de texto
- **Permissões de arquivo**: O usuário deve ter permissão de leitura no arquivo de entrada

### 🗄️ **Banco de Dados Nitrite**
- **Arquivo .db corrompido**: Se houver comportamento estranho, delete o arquivo `produtor.db` e execute novamente
- **Configuração do MVStore**: O projeto usa persistência em arquivo. Para banco em memória, remova o módulo MVStore
- **Status de processamento incorreto**: Use a classe `ResetDatabase` para limpar o status se necessário
- **Warnings SLF4J**: Os avisos "No SLF4J providers were found" são normais e não afetam a funcionalidade

### 🔄 **Problemas de Concorrência**
- **Buffer compartilhado UDP**: - O receptor UDP agora cria buffers únicos para cada pacote
- **Race conditions**: - O produtor agora usa cópias defensivas dos bytes para cada thread
- **Deadlocks em threads**: Se o sistema "travar", verifique se há problemas de conectividade que impedem as threads de finalizar

### 🚀 **Execução**
- **Ordem de inicialização**: SEMPRE inicie o **receptor PRIMEIRO**, depois o produtor
- **Parâmetros obrigatórios**:
  - Receptor: `--porta=<numero>`
  - Produtor: `--host=<ip> --porta=<numero> --arquivo=<caminho>`
- **Sintaxe dos parâmetros**: Use `--parametro=valor` (com = e sem espaços)
- **Interrupção do processo**: Use Ctrl+C para encerrar. O shutdown hook garantirá limpeza adequada

### 📊 **Monitoramento e Debug**
- **Logs verbosos**: O sistema exibe logs detalhados. Se não aparecerem dados, verifique conectividade
- **Status do banco**: O produtor mostra status de processamento ao iniciar
- **ACK UDP não recebido**: Se o UDP ficar em loop, verifique se o receptor está rodando e acessível
- **Hexadecimal inválido**: Para arquivos binários, use apenas caracteres hexadecimais válidos (0-9, A-F)

### 🔍 **Troubleshooting**
```bash
# Verificar se a porta está livre
netstat -an | grep 5000

# Verificar processos Java em execução
jps -v

# Resetar banco de dados se necessário
java -cp produtor/target/produtor-1.0-SNAPSHOT-jar-with-dependencies.jar com.exemplo.produtor.ResetDatabase arquivoAscii.txt

# Testar conectividade
telnet 127.0.0.1 5000
```

### ⚠️ **Problemas Possíveis**
- **Repetição da primeira linha no UDP**: Corrige-se tirando o compartilhamento de buffer no receptor (colocando a instância do buffer dentro do loop do receptor)
- **Race conditions entre threads**: Implementadas cópias defensivas no produtor (criando cópias defensivas dos bytes para cada thread)
- **ACK UDP não processado**: Melhorado o tratamento de timeouts e retries (colocando o tratamento de timeouts e retries dentro do loop do receptor)

---