### Smart-Home
Este projeto demonstra um sistema de Smart Home simples utilizando o protocolo MQTT com o broker Mosquitto. O objetivo é permitir a comunicação entre dispositivos inteligentes (sensores e atuadores) e um controlador central, simulando uma casa conectada.

### Objetivos
Controlar dispositivos domésticos (ex.: lâmpadas, ventiladores) via mensagens MQTT.
Demonstrar a arquitetura básica de uma casa inteligente distribuída em Java.

### Tecnologias Utilizadas
- Java 17+ – Linguagem principal.
- Maven – Gerenciador de dependências e build.
- MQTT – Protocolo leve para IoT e sistemas distribuídos.
- Eclipse Mosquitto – Broker MQTT open-source.
- Eclipse Paho Java Client – Biblioteca Java para comunicação MQTT.

### MQTT
Instale e rode um broker MQTT (ex: Mosquitto) com o arquivo de configuração da raiz deste projeto.

   **Arquivo de configuração MQTT (`mqtt.conf`)**
   Este arquivo define as configurações do broker Mosquitto:
   - `listener 1883`: porta padrão para conexões MQTT.
   - `allow_anonymous true`: permite conexões sem autenticação.
   - `log_dest file mosquitto.log`: registra os logs no arquivo `mosquitto.log`.

   **Iniciar o Mosquitto**
   ```bash
   mosquito -c mqtt.conf -v
   ```

### Subscribers
As classes Subscriber são clientes MQTT que se conectam ao broker e inscrevem-se em um tópico específico para receber comandos relacionados a uma dispositivos inteligentes.

   **Funcionamento:**
   1. **Carregamento de configurações**: Lê as propriedades MQTT do arquivo `mqtt.properties` (broker URL, ID do cliente e tópico).
   2. **Conexão ao broker**: Estabelece uma conexão com o broker MQTT usando o cliente Paho.
   3. **Inscrição em tópico**: Inscreve-se no tópico correspondente para ouvir comandos.
   4. **Processamento de mensagens**: Ao receber mensagens no tópico:
      *Exemplo: Lâmpada*
      - Se o comando for `"ON"`: exibe "Lâmpada ligada!"
      - Se o comando for `"OFF"`: exibe "Lâmpada desligada!"
      - Outros comandos são apenas exibidos como recebidos.
   5. **Aguardamento contínuo**: Mantém a conexão aberta, aguardando novos comandos indefinidamente.

   **Exemplo de uso:** Para ligar a lâmpada, publique `"ON"` no tópico configurado usando outro cliente MQTT.
