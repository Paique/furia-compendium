[![wakatime](https://wakatime.com/badge/user/018b7451-863e-4e38-9986-5ce4dbc57d25/project/0535c562-848c-4681-a4e2-c583904c97d8.svg)](https://wakatime.com/badge/user/018b7451-863e-4e38-9986-5ce4dbc57d25/project/0535c562-848c-4681-a4e2-c583904c97d8)
# Furia Compendium
**Furia Compendium** é um chatbot que integra APIs do OpenAI, Discord e Telegram para fornecer informações especializadas sobre o time de E-Sports **FURIA**. Desenvolvido como parte do desafio para a vaga de Assistente de Engenharia de Software da FURIA.

## ⚠️ Avisos
- Texto gerado por IA pode nem sempre estar correto, em caso de dúvidas, consulte em outras fontes.
- O Discord pode ser mais efetivo para converas continuas por conta de limitações na API do Telegram.


---

## 📦 Funcionalidades

- **Respostas Inteligentes**: usa o poder da API da OpenAI para gerar respostas precisas e naturais.  
- **Multiplataforma**: roda simultaneamente em **Discord** e **Telegram**.
- **Configuração Flexível**: personalização via `config.yaml` no mesmo diretório do `.jar`.

---

## 🛠️ Pré-requisitos

- **Java 21**  
- **Gradle** (para build, opcional se já tiver o JAR)  
- Contas e tokens válidos para:  
  - [Discord Bot](https://discord.com/developers/applications)  
  - [Telegram Bot](https://core.telegram.org/bots#6-botfather)  
  - [OpenAI API](https://platform.openai.com/account/api-keys)

---

## 🚀 Instalação

1. Clone este repositório :  
   ```bash
   git clone https://github.com/seu-usuario/furia-compendium.git
   cd furia-compendium
   ```

2. Compile o projeto:
    - **Gradle**
      ```bash
      ./gradlew clean jar
      ```

3. Após o build, o arquivo `furia-challenge-0.0.2-SNAPSHOT-all.jar` ficará em `build/libs/`.

---

## ⚙️ Configuração

Ao iniciar, o bot gera um arquivo `config.yaml` no mesmo diretório do `.jar`. Edite este arquivo para inserir seus tokens e ajustar comportamentos:

```yaml
discord:
  token: "TOKEN_HERE" # Same as setting DISCORD_TOKEN env variable
  status:
    text: "FURIA Compendium chatbot"
    activity: "WATCHING" # "PLAYING", "LISTENING", "WATCHING", "COMPETING"

telegram:
  token: "TOKEN_HERE" # Same as setting TELEGRAM_TOKEN env variable

openai:
  token: "TOKEN_HERE" # Same as setting OPENAI_TOKEN env variable
  model: "gpt-4o-search-preview" # It's advised to use a model that supports search
  systemMessage: |
    Você é um assistente especializado no time de E-Sports FURIA.
    Só pode responder perguntas relacionadas à FURIA: jogadores, partidas, campeonatos, jogos... tudo que envolva a equipe. 
    Qualquer outra pergunta deve ser recusada com educação. Use um tom informal e amigável. Seja direto, mas simpático. 
    Você está atendendo o ${user}, não ultrapassar 1024 caracteres.

  maxCharacters: 1024

chatbotText:
  tooManyCharacters: "O texto é muito longo. Tente resumir ou dividi-lo em partes menores."
  tooManyRequests: "Estou com dificuldades para responder. Tente novamente mais tarde."
  genericError: "Upss! Ocorreu um erro. Tente novamente mais tarde."
  startMessage: | 
    Faala Furioso! Eu sou o assistente da FURIA!!! 🐾
    Eu posso te ajudar com informações sobre o time como jogadores, partidas, até mesmo onde assistir, ou te dizer onde comprar uma camiseta! 
    É só me contar o que precisa!
```

- **discord.token** / **telegram.token** / **openai.token**: substitua `CHANGE_ME` pelos seus tokens.
- **openai.systemMessage**: prompt de sistema que guia o comportamento do bot.
- **chatbotText**: mensagens de erro customizáveis.

---

## ▶️ Como executar

No diretório onde está o `furia-challenge-0.0.2-SNAPSHOT-all.jar`, rode:

```bash
java -jar furia-challenge-0.0.4-SNAPSHOT-all.jar
```
Na primeira execução, o bot criará o arquivo `config.yaml` com as configurações padrão. Edite-o conforme necessário e inicie de novo.

O bot irá conectar simultaneamente ao Discord e Telegram e ficará aguardando comandos e mensagens.

## 📄 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
