### MQTT
Instale e rode um broker MQTT (ex: Mosquitto) com o arquivo de configuração da raiz deste projeto.

   ### Arquivo de configuração MQTT (`mqtt.conf`)
   Este arquivo define as configurações do broker Mosquitto:
   - `listener 1883`: porta padrão para conexões MQTT.
   - `allow_anonymous true`: permite conexões sem autenticação.
   - `log_dest file mosquitto.log`: registra os logs no arquivo `mosquitto.log`.

   ### Iniciar o Mosquitto
   ```bash
   mosquito -c mqtt.conf -v
