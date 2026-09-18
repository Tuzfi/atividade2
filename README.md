# Atividade 02 — Login e Cadastro com Spring Boot + Thymeleaf

Aplicação web com tela de login, cadastro de usuários e recuperação de senha, construída com Spring Boot, Spring Security e Thymeleaf.

## Tecnologias

- Java 17
- Spring Boot 3.3.5
- Spring Web (MVC)
- Spring Security (autenticação, autorização por papéis, criptografia de senha com BCrypt)
- Thymeleaf (templates)
- Spring Mail (envio de e-mail na recuperação de senha)

## Endpoints

| Método | Endpoint          | Descrição                                             |
|--------|-------------------|--------------------------------------------------------|
| GET    | `/login`          | Exibe a tela de login                                  |
| POST   | `/login`          | Processa o login (tratado automaticamente pelo Spring Security) |
| GET    | `/register`       | Exibe a tela de cadastro                               |
| POST   | `/register`       | Processa o cadastro de um novo usuário                 |
| GET    | `/recoverpassword`| Exibe a tela de recuperação de senha                   |
| POST   | `/recoverpassword`| Envia um e-mail de recuperação de senha                |
| GET    | `/home`           | Área logada de um usuário comum                        |
| GET    | `/admin`          | Área restrita, acessível apenas a usuários com papel ADMIN |
| POST   | `/logout`         | Encerra a sessão do usuário                            |

## Como rodar o projeto

1. Clone o repositório:

   git clone https://github.com/vGustav1/atividade2.git
   cd atividade2

2. Rode a aplicação:

   No Linux/Mac: ./mvnw spring-boot:run

   No Windows (PowerShell): .\mvnw.cmd spring-boot:run

3. Acesse no navegador: http://localhost:8080/login

## Configuração de ambiente e credenciais

Os usuários são mantidos **em memória**: os cadastros feitos pela tela `/register` são perdidos a cada reinício da aplicação.

Além disso, existem dois usuários fixos de demonstração, definidos em `src/main/resources/application.properties`:

| Papel | Usuário | Senha  |
|-------|---------|--------|
| USER  | `joao`  | `4321` |
| ADMIN | `admin` | `1234` |

Para o envio de e-mail de recuperação de senha funcionar, é necessário preencher `spring.mail.username` e `spring.mail.password` (senha de app do Gmail) nesse mesmo arquivo.

**Atenção:** por ser um projeto acadêmico, essas credenciais estão fixas no arquivo apenas para fins de demonstração — não representam dados reais de produção.
