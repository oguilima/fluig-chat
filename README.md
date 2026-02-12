# Projeto Fluig FSW Java

Este repositório contém o código do projeto Fluig FSW Java.

## Requisitos
- Java (versão compatível com o projeto)
- Maven 3.x
- Acesso à Central de Componentes (repositório remoto) e credenciais configuradas em `~/.m2/settings.xml`

## Build
1. Limpar e construir o artefato:

   mvn clean install

2. Para publicar na Central de Componentes (repositório remoto) configure `distributionManagement` no `pom.xml` e as credenciais em `~/.m2/settings.xml`, então execute:

   mvn clean deploy

Observação: se a Central exigir um passo manual (UI do Fluig), gere o artefato em `target/` (por exemplo `*.jar` ou `*.war`) e faça o upload pela interface de administração ou pelo endpoint de publicação que sua organização utiliza.

## Testes
- Executar testes unitários:

  mvn test

- Executar a fase de verificação/integracao (se o projeto tiver profile de integração):

  mvn verify

- Teste manual: após publicar ou copiar o artefato gerado em `target/`, implante-o através da central de componentes do Fluig.
